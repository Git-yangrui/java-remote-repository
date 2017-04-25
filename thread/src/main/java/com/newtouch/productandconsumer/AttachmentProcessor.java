package com.newtouch.productandconsumer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

import com.newtouc.twophase_termination.AbstractTerminateableThread;

public class AttachmentProcessor {
	private final String ATTACHMENT_STORE_BASE_DIR = "/home/yangrui/tmp/attachments/";
	
	// 模式角色 channel
	private final Channel<File> channel = new BlockingQueueChannel<File>(
			new ArrayBlockingQueue<File>(200));
	
	// 模式角色 consumer
	private final AbstractTerminateableThread indexingThread = new AbstractTerminateableThread() {
		@Override
		protected void doRun() throws Exception {
			File file = null;
			file = channel.take();
			try {
				indexFile(file);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				terminationToken.reservations.decrementAndGet();
			}
		}

		private void indexFile(File file) {
			// shenglu-----------
			// ------------
			// 模拟生成索引文件的时间消耗
			Random random = new Random();
			try {
				Thread.sleep(random.nextInt(100));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	public void init() {
		indexingThread.start();
	}

	public void shutdown() {
		indexingThread.terminate();
	}

	public void saveAttachment(InputStream in, String documentID,
			String originalFileName)  {
		try {
		     File file = saveAsFile(in, documentID, originalFileName);
		
			channel.put(file);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		} finally {
			indexingThread.terminationToken.reservations.incrementAndGet();
		}
	}

	private synchronized File saveAsFile(InputStream in, String documentID,
			String originalFileName) throws IOException {
		String dirName = ATTACHMENT_STORE_BASE_DIR + documentID;
		File dir = new File(dirName);
		dir.mkdirs();
		File file = new File(dirName + '/'
				+ Normalizer.normalize(originalFileName, Normalizer.Form.NFC));
		BufferedOutputStream bufferedOutputStream = null;

		bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(
				new File("")));
		byte[] bytes = new byte[1024];
		int len = 0;
		while ((len = in.read(bytes)) != -1) {
			bufferedOutputStream.write(bytes, 0, len);
			bufferedOutputStream.flush();
		}

		in.close();
		bufferedOutputStream.close();
        return file;
	}

}
