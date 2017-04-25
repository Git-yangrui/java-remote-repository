package com.newtouch.threadGuardedSuspend;

import java.lang.ref.WeakReference;

public class StringWeak {
  public static void main(String[] args) {
	  
	String s=new String("ssss");
	WeakReference<String> reference=new WeakReference<String>(s);
	try {
		Thread.sleep(20000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String string = reference.get();
	System.out.println(string);
}
  
}
