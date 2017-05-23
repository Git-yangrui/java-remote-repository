package com.yangrui.springboot.control;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan
public class MyController {
@RequestMapping(value="/wwww/wwweee")
  public String get(){
	  return "ssss";
  }
}
