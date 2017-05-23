package com.yangrui.spring_anlaysis;

import org.springframework.aop.framework.ProxyFactory;

import com.yangrui.spring_anlaysis1.BussinessServiceImpl;
import com.yangrui.spring_anlaysis1.IBussinessService;
import com.yangrui.spring_anlaysis1.MethodLoggerAdvice;
import com.yangrui.spring_anlaysis1.MyMethodIntter;

public class TestProxyFactory {
	public static void main(String[] args) {
		MyMethodIntter advice = new MyMethodIntter();
		MethodLoggerAdvice dAdvice=new MethodLoggerAdvice();
		IBussinessService dBussinessService=new BussinessServiceImpl();
		Class clazz[] =new Class[]{IBussinessService.class};
		ProxyFactory proxyFactory1=new ProxyFactory(clazz);
		proxyFactory1.setTarget(dBussinessService);
		proxyFactory1.addAdvice(dAdvice);
		proxyFactory1.addAdvice(advice);
		IBussinessService proxy = (IBussinessService)proxyFactory1.getProxy(TestProxyFactory.class.getClassLoader());
		proxy.bussiness();
	}
}
