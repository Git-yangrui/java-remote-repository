package com.newtouch.thread.specific.storage;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SmsVerificationCodeSender {
  private static final ExecutorService EXCUTOR=
		  new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(), 60, TimeUnit.SECONDS,
				  new SynchronousQueue<Runnable>(), new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						Thread t=new Thread(r,"VerfCodeSender");
						t.setDaemon(true);
						return t;
					}
				},new ThreadPoolExecutor.DiscardPolicy());
  
  public static void main(String[] args){ 
	  SmsVerificationCodeSender client=new SmsVerificationCodeSender();
	  client.sendVerficationSms("18232323323");
	  client.sendVerficationSms("18232323232");
	  
  }
  
  public void sendVerficationSms(final String msisdn){
	  Runnable task=new Runnable() {
		@Override
		public void run() {
			int verficationCode=ThreadSpecificSecureRandom.INSTANCE.nextInt(999999);
			DecimalFormat decimalFormat=new DecimalFormat("000000");
			String txtVerCode=decimalFormat.format(verficationCode);
			sendSMs(msisdn,txtVerCode);
		}
	
	};
	EXCUTOR.submit(task);
		
  }
/**
 * 调用其它线程去发送信息
 * @param msisdn
 * @param txtVerCode
 */
protected void sendSMs(String msisdn, String txtVerCode) {
     System.out.println("fasongcengou");
}
}
