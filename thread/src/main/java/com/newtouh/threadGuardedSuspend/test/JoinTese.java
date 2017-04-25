package com.newtouh.threadGuardedSuspend.test;

public class JoinTese {
	public static void main(String[] args) {
		Runnable runa = new Runnable() {
			@Override
			public void run() {
				
				for (int i = 0; i < 100; i++) {
					System.out.println(Thread.currentThread().getName() +"    "+ i);
				}
			}
		};
		
		Thread thread1=new Thread(runa);
		thread1.setName("线程1");
		Thread thread2=new Thread(runa);
		thread2.setName("线程2");
		thread1.start();
		thread2.start();
		try {
			System.out.println(         "                  join()");
			thread1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("mani");
	}
}
