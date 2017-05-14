package com.citi.thread;

import java.util.HashSet;
import java.util.Set;

public class NoVisibility {
	private static boolean ready;
	private static int number;

	private static class ReaderThread extends Thread {
		public void run() {
			while (!ready)
				Thread.yield();
			System.out.println(number);
		}
	}

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 100; i++) {
			test();
		}
	}

	private static void test() {
		new ReaderThread().start();
		number = 42;
		ready = true;
	}

	private static final String[] states = new String[] {

	};

	public String[] getstates() {
		return states;
	}
	private static Set set;
	
	private  void get(){
		set= new HashSet();;
	}

}
