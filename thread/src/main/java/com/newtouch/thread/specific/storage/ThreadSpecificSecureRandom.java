package com.newtouch.thread.specific.storage;

import java.security.SecureRandom;

public class ThreadSpecificSecureRandom {
	public static final ThreadSpecificSecureRandom INSTANCE
	     =new ThreadSpecificSecureRandom();
	
	public static final ThreadLocal<SecureRandom> 
	
	          SECURE_RADOM= new ThreadLocal<SecureRandom>(){
		@Override
		protected SecureRandom initialValue() {
			SecureRandom srnd;
			try{
				srnd=SecureRandom.getInstance("SHA1PRNG");
			}catch(Exception e){
				e.printStackTrace();
				throw new RuntimeException("sdd");	
			}
			return srnd;
		}
	};
	
	
	private ThreadSpecificSecureRandom(){
		
	}
	
	
	public int nextInt(int upperBound){
		SecureRandom secureRnd=SECURE_RADOM.get();
		return secureRnd.nextInt(upperBound);
		
	}
	public void setSeed(long seed){
		//复用SecureRandom 只是设置种子
		SecureRandom secureRnd=SECURE_RADOM.get();
		secureRnd.setSeed(seed);
	}
   
public static void main(String[] args) {
	SecureRandom secureRandom;
	long nextLong;
	ThreadLocal<SecureRandom> secureRadom = ThreadSpecificSecureRandom.SECURE_RADOM;
	secureRadom.set(new SecureRandom());
	try {
		secureRandom =new SecureRandom();
				
		secureRandom.setSeed(1000000000);		
				//SecureRandom.getInstance("SHA1PRNG");
	nextLong = secureRandom.nextInt(10);
	
	} catch (Exception e) {
		e.printStackTrace();
		throw new RuntimeException("sdd");
	}
	System.out.println(nextLong);
}
	
}
