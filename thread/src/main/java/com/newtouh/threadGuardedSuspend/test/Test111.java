package com.newtouh.threadGuardedSuspend.test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test111 {
//  public static void main(String[] args) {
//	ConcurrentHashMap<String, AtomicInteger> concurrentHashMap
//	    =new ConcurrentHashMap<String, AtomicInteger>();
////	AtomicInteger put = concurrentHashMap.put("aa", new AtomicInteger(1));
////	AtomicInteger put1 = concurrentHashMap.put("aa1", new AtomicInteger(2));
//	AtomicInteger putIfAbsent = concurrentHashMap.putIfAbsent("aa", new AtomicInteger(0));
//	AtomicInteger putIfAbsent1 = concurrentHashMap.putIfAbsent("aa", new AtomicInteger(1));
//	
//	
//    System.out.println();
//}
	static class MyThread implements  Runnable {
		Lock lock=new ReentrantLock();
		Condition con=lock.newCondition();
		int count=0;
		public void run() {
			System.out.println(Thread.currentThread().getName());
			lock.lock();
			try {
				for (int i = 0; i <1000; i++) {
					count++;
					System.out.println(Thread.currentThread().getName()+"----"+count);
				}
				//con.await(); 
				
				System.out.println("wodachulaile ");
//			} catch (InterruptedException e) {
//				System.out.println(Thread.currentThread().getName()+"baocuole");
//				e.printStackTrace();
			}finally{
				lock.unlock();
				System.out.println(Thread.currentThread().getName()+"ÊÍ·ÅËø");
				
			}
		}
	}
	public static void main(String[] args)  {
		MyThread myThread=new MyThread();
		Thread thread1=new Thread(myThread);
		Thread thread2=new Thread(myThread);
		thread1.start();
		thread1.interrupt();
		System.out.println("----------------------------------");
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		thread2.start();
		
		
		
	}
}
