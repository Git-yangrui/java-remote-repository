package com.newtouch.serial.thread.confinement;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import com.newtouc.twophase_termination.AbstractTerminateableThread;


/**
 * �����̷߳�յ�ʵ����    �����߳�����
 * @author yangrui
 * 
 */
public class MessageFileDownLoader {
	private final WorkerThread workThread;
	public MessageFileDownLoader(String outputDir,final String ftpServer,final String
			username,final String password) {
		workThread=new WorkerThread(outputDir, ftpServer, username, password);
	}
	
	//����
	public void init(){
		workThread.start();
	}
	
	public void download(String file){
		workThread.download(file);
	}
   
	
	
	public void shutdown(){
		workThread.terminate();
	}
	private static class WorkerThread extends AbstractTerminateableThread {
		// ��ɫ����
		private final BlockingQueue<String> workQueue;
		// promise
		private final Future<FTPClient> ftpClientPromise;
         //���·��
		private final String outputDir;

		@SuppressWarnings("unused")
		public WorkerThread(String outputDir, final String ftpServer,
				final String username, final String password) {
			this.workQueue = new ArrayBlockingQueue<String>(100);
			this.outputDir = outputDir;
			this.ftpClientPromise = new FutureTask<FTPClient>(
					new Callable<FTPClient>() {
						
						public FTPClient call() throws Exception {
							FTPClient ftpClient = initFTPClient(ftpServer,
									username, password);
							return ftpClient;
						}
					});
			new Thread((FutureTask<FTPClient>) ftpClientPromise).start();
			// ftpClientPromise.get();
		}

		public void download(String file) {
			try {
				workQueue.put(file);
				terminationToken.reservations.incrementAndGet();
			} catch (Exception e) {
				;
			}
		}

		private FTPClient initFTPClient(String ftpServer, String username,
				String password) throws Exception {
			FTPClient ftpClient = new FTPClient();
			FTPClientConfig config = new FTPClientConfig();
			ftpClient.configure(config);
			int reply;
			ftpClient.connect(ftpServer);
			reply = ftpClient.getReplyCode();
			System.out.println(ftpClient.getReplyCode());
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				throw new RuntimeException("ftp server refused connect");

			}
			boolean isOK = ftpClient.login(username, password);
			if (isOK) {
				System.out.println(ftpClient.getReplyCode());
			} else {
				throw new RuntimeException("failed to login to server"
						+ ftpClient.getReplyCode());
			}

			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

			return ftpClient;
		}
         //dorun �������ڸ����run ��for(;;)����   �����û�û�е���download����ʱ�� ��û������������ʱ��take()һֱ��������״̬
	     //�����û����������download������ʱ�򼰵���download()��������  ��queue �м���һ��������ʱ��take���������õ�����  ����ִ��
		//�ߵ�get()����ʱ�� ������Ǹ�promise����ȡ��ftpClient����  Ȼ��������ط���      ��Ϊ�ڸ����  run������ for��;;������ѭ����
		@Override
		protected void doRun() throws Exception {
			String file = workQueue.take();         
			OutputStream os=null;
			try {
                os=new BufferedOutputStream(new FileOutputStream(outputDir+file));
                FTPClient ftpClient = ftpClientPromise.get();
                ftpClient.retrieveFile(file, os);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				os.close();
				terminationToken.reservations.decrementAndGet();
			}

		}
		
		@Override
		protected void doCleanUp(Exception cause) {
			try {
				ftpClientPromise.get().disconnect();
			} catch (Exception e) {
			    e.printStackTrace();
			}
			
		}
	}

}