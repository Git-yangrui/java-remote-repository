package com.yangrui.spring_anlaysis;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class MyPersonalAdvice implements MethodBeforeAdvice {

	@Override
	public void before(Method arg0, Object[] arg1, Object arg2) throws Throwable {
		System.out.println("1222222222222");
		
	}
  
}
