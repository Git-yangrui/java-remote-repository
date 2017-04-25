package com.newtouc.threadGuardedSuspend.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.newtouc.semaphore.SemaphoreBasedChannal;

public class TeseSemaphore {
	public static void main(String[] args) {

		BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
		final SemaphoreBasedChannal<String> basedChannal = new SemaphoreBasedChannal<String>(
				queue, 5);

		Thread treThread = new Thread(new Runnable() {

			public void run() {
				while (true) {
					try {
						basedChannal.put("ssss");
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		Thread treThread1 = new Thread(new Runnable() {

			public void run() {
				while (true) {
					try {
						System.out.println("zhengzaiqu");
						String take = basedChannal.take();
						System.out.println(take);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		
		
		treThread.start();
		
		treThread1.start();
	}
	
}
