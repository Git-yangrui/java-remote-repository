package com.newtouch.thread.specific.storage1;

import java.util.concurrent.atomic.AtomicInteger;

public class ImplicitParameterPassing {
	public static void main(String[] args)  throws Exception {
          CilentThread cilentThread;
          BusinessService bs=new BusinessService();
          for (int i = 0; i < 20; i++) {
        	  cilentThread=new CilentThread("test", bs);
        	  cilentThread.start();
        	  cilentThread.join();
			
		}
	}

	static class CilentThread extends Thread {
		
		private final String message;

		private final BusinessService bs;
           
		private static final AtomicInteger SEQ=new AtomicInteger(3);
		public CilentThread(String message, BusinessService bs) {
			this.message = message;
			this.bs = bs;
		}

		@Override
		public void run() {
			Context.INSTANCE.setTransactionId(SEQ.incrementAndGet());
			//System.out.println("SEQ"+SEQ.get());
			//Context.INSTANCE.setTransactionId(1);
			bs.servie(message);
		}

	}

	static class Context {
		private static final ThreadLocal<Integer> TS_OBJECT_PROXY = new ThreadLocal<Integer>(){
			@Override
			protected Integer initialValue() {
				// TODO Auto-generated method stub
				return 0;
			}
		};
		public static final Context INSTANCE = new Context();
		

		private Context() {
            
		}

		public Integer getTransactionId() {
			return TS_OBJECT_PROXY.get();
		}
		
		public void setTransactionId(Integer transactionId){
			TS_OBJECT_PROXY.set(transactionId);
		}
		
		public void reset(){
			TS_OBJECT_PROXY.remove();
		}
		
		
	}

	static class BusinessService {
		public BusinessService() {
			// TODO Auto-generated constructor stub
		}
		public void servie(String message) {
			int tractionId=Context.INSTANCE.getTransactionId();
			System.out.println("procssing transaction"+
			"   's message    "+message+"  "+tractionId);
		}
	}
}
