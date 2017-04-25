package com.newtouch.serial.thread.confinement;

public class SampleClient {
    
	// 内存中只放入一个对象
	private static final MessageFileDownLoader DOWNLOAD;
    
	static {
	   //只初始化了一个usernam 与 ftpserver的连接    及一个用户对象
		DOWNLOAD=new MessageFileDownLoader("", "192.168.190.190", "", "");
		//调用init()方法 会进去run方法 ，进入dorun（）方法  ，然后阻塞那里；
		DOWNLOAD.init();
	}
	public static void main(String[] args) {
		DOWNLOAD.download("");             
	}
}
