package com.yangrui.spring_anlaysis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
     ApplicationContext applicationContext=new ClassPathXmlApplicationContext("bean.xml");
     MyController bean = (MyController)applicationContext.getBean("myController");
     bean.getUsers();
	}
}
