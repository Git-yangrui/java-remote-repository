package com.yangrui.spring_anlaysis1;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MyMethodIntter implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("sdsdsdsds");
		invocation.proceed();
		return null;
	}

}
