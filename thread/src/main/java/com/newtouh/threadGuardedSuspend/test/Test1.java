package com.newtouh.threadGuardedSuspend.test;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Test1 {
  public static void main(String[] args) {
	final BlockingQueue<String> blockingQueue=new ArrayBlockingQueue<String>(10);
	Thread[] arrayThread=new Thread[20];
	for (int i = 0; i < 20; i++) {
		final int j=i;
		arrayThread[i]=new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					Thread.sleep(1000);
					blockingQueue.put("zheshishuzi"+j);
					System.out.println("放入数据完毕：：："+"zheshishuzi"+j);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
	}
	
	Thread[] arrayThread1=new Thread[20];
	for (int i = 0; i < 20; i++) {
		final int j=i;
		arrayThread1[i]=new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					String take = blockingQueue.take();
					System.out.println("取出数据完毕：：："+take);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
	}
	
	for (Thread thread : arrayThread) {
		thread.start();
	}
	
	for (Thread thread : arrayThread1) {
		thread.start();
	}
}
}
