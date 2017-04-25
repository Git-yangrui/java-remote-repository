package com.newtouc.threadGuardedSuspend.test;

import java.util.Random;

public class RadomTest {
  public static void main(String[] args) {
	

	Random random=new Random()
	;
    int nextInt = random.nextInt(10000);
    
    
    System.out.println(nextInt);
}
}
