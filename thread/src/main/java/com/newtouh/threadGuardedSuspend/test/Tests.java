package com.newtouh.threadGuardedSuspend.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Tests {
   public static void main(String[] args) throws Exception {

		 BlockingQueue<String> queue =

				new ArrayBlockingQueue<String>(10);
		 int i=1;
		 queue.put(String.valueOf(i++));
		 
		 
		 String take = queue.take();
		 System.out.println(take);
}
	
}
