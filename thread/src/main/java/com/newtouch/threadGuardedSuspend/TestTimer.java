package com.newtouch.threadGuardedSuspend;

import java.util.Timer;
import java.util.TimerTask;

public class TestTimer {
  public static void main(String[] args) {
	Timer timer=new Timer();
	timer.schedule(new TimerTask() {
		
		@Override
		public void run() {
			System.out.println("xianshi");
			
		}
	}, 10, 1000);
}
}
