package com.yangrui.spring_anlaysis;

public class MyController {
	private MyService myService;

	public MyController() {
		System.out.println("control00000000000000");
	}

	public void setMyService(MyService myService) {
		this.myService = myService;
	}

	public void getUsers() {
		myService.getUsers();
	}
}
