package com.yangrui.spring_anlaysis;

public class MyAdvice {
	public MyAdvice() {
		System.out.println("myAdvice0000000000");
	}
 public void before(){
	 System.out.println("before --------------");
 }
 public void after(){
	 System.out.println("after --------------");
 }
}
