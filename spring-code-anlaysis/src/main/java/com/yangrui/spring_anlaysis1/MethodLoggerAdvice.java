package com.yangrui.spring_anlaysis1;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class MethodLoggerAdvice implements MethodBeforeAdvice {

	public void before(Method method, Object[] args, Object target) throws Throwable {
		String name = method.getName();
		System.out.println("MethodLoggerAdvice method name " + name + " now is invoke");
	}

}