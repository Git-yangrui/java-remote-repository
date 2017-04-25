package com.newtouch.threadpool;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SmsVerficationCodeSender {
    private static ExecutorService EXECUTOR=new ThreadPoolExecutor(
    		1, Runtime.getRuntime().availableProcessors(), 60, TimeUnit.SECONDS, 
    		                new SynchronousQueue<Runnable>(),new ThreadFactory(){
											@Override
											public Thread newThread(Runnable r) {
												Thread t=new Thread(r,"VerfCodeSender");
												t.setDaemon(true);
												return t;
											}
								},new ThreadPoolExecutor.DiscardOldestPolicy());
    /**
     * 生成并下发验证码短信给指定的手机号码；
     */
    public void sendVerficationSms(final String msisdn){
    	Runnable task=new Runnable() {
			
			@Override
			public void run() {
				//生成强随机的数据验证码
				int  verificationCode=new Random().nextInt(10000);
				DecimalFormat df=new DecimalFormat("0000");
				String txtVerCode=df.format(verificationCode);
				
				
				sendSms(msisdn,txtVerCode);
			}
		};
		
		EXECUTOR.submit(task);
//		Future<?> submit = EXECUTOR.submit(task);
//		submit.get();
		
    }
    private void sendSms(String msisdn, String txtVerCode) {
		System.out.println("sending        -----"+txtVerCode+"to   "+msisdn);
		//省略----------------------------------------------
	}
    
}
