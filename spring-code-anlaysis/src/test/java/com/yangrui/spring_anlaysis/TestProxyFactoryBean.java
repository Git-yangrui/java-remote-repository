package com.yangrui.spring_anlaysis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yangrui.spring_anlaysis1.IBussinessService;

public class TestProxyFactoryBean {  
      
    @Test  
    public void test(){  
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-bean.xml");  
        IBussinessService bussinessServiceImpl =  applicationContext.getBean("methodProxy",IBussinessService.class);  
        bussinessServiceImpl.bussiness();  
           
    }  
  
}  