package com.yangrui.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
public class Main11 {
public static void main(String[] args) {
	 SpringApplication.run(Main11.class, args);
}
}
