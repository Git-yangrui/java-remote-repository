package com.yangrui.spring_anlaysis1;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.aop.MethodBeforeAdvice;

public class MethodInvokeCountAdvice implements MethodBeforeAdvice {

	private Map<String, Integer> maps = new HashMap<String, Integer>();

	public void before(Method method, Object[] args, Object target)  
            throws Throwable {  
        String name = method.getName();  
        System.out.println("MethodInvokeCountAdvice method name =  "+name);
        Integer count = maps.get(name);  
        count = count == null ? 1 : count+1;  
        maps.put(name, count);  
        System.out.println("MethodInvokeCountAdvice 调用了："+maps.get(name)+"次");  
    }
}