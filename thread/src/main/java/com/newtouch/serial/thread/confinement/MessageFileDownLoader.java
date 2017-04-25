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
 * 串行线程封闭的实现类    串行线程运行
 * @author yangrui
 * 
 */
public class MessageFileDownLoader {
	private final WorkerThread workThread;
	public MessageFileDownLoader(String outputDir,final String ftpServer,final String
			username,final String password) {
		workThread=new WorkerThread(outputDir, ftpServer, username, password);
	}
	
	//启动
	public void init(){
		workThread.start();
	}
	
	//下载文件会加入queue队列中   这里只是但一个链接  因为用户名和密码决定一个对象，
	//这里实现的串行线程封闭是指的下载的任务  为任务1   任务2  任务3  ，，，，，，，，现在只是去实现1  2  3 的串行实现
	public void download(String file){
		workThread.download(file);
	}
   
	
	
	public void shutdown(){
		workThread.terminate();
	}
	private static class WorkerThread extends AbstractTerminateableThread {
		// 角色对列
		private final BlockingQueue<String> workQueue;
		// promise
		private final Future<FTPClient> ftpClientPromise;
         //输出路径
		private final String outputDir;

		@SuppressWarnings("unused")
		public WorkerThread(String outputDir, final String ftpServer,
				final String username, final String password) {
			this.workQueue = new ArrayBlockingQueue<String>(100);
			this.outputDir = outputDir;
			this.ftpClientPromise = new FutureTask<FTPClient>(
					new Callable<FTPClient>() {
						@Override
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
         //dorun 方法会在父类的run 中for(;;)调用   当该用户没有调用download方法时候 及没有任务下载这时候take()一直处于阻塞状态
	     //当该用户向里面添加download的任务时候及调用download()方法的事  向queue 中加入一个任务，这时候take（）可以拿到任务  往下执行
		//走到get()方法时候 这个是是个promise代理，取出ftpClient对象  然后调用下载方法      因为在父类的  run方法是 for（;;）无限循环的
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