package com.newtouch.thread.master_slaver;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import com.newtouc.twophase_termination.AbstractTerminateableThread;

public class TPSStat {
	
	
	@SuppressWarnings("unused")
	private static class Master{
		private final String logFileBaseDir;
		private final String excluedOperationNames;
		private final String includedOperationNames;
		private final String destinationSysName;
		
		//ÿ���ɷ���ĳ��slaver�̵߳ĸ���
		private static final int NUMBER_OF_FILES_FOR_EACH_DISPATH=5;
	    private static final int WORKER_COUNT=Runtime.getRuntime().availableProcessors();
		
	    public Master(String logFileBaseDir,String excluedOperationNames,
	    		String includedOperationNames,String destinationSysName){
	    	this.logFileBaseDir=logFileBaseDir;
	    	this.excluedOperationNames=excluedOperationNames;
	    	this.includedOperationNames=includedOperationNames;
	    	this.destinationSysName=destinationSysName;
	    }
	    public ConcurrentMap<String, AtomicInteger> calculate(
	    		BufferedReader reader) throws Exception{
	    	//repository�������̹߳����
	    	ConcurrentMap<String, AtomicInteger> repository=new ConcurrentSkipListMap<String, AtomicInteger>();
	    	Worker[] workers=createAndStartWorkers(repository);
	    	dispatchTask(reader,workers);
	    	return null;
	    }
		private void dispatchTask(BufferedReader reader, Worker[] workers) throws IOException {
			String line;
			int fileCount=0;
			int workerIndex=-1;
			Set<String> ste=new LinkedHashSet<String>();
			String redeRecord=null;
			while((redeRecord=reader.readLine())!=null){
				ste.add(redeRecord);
				fileCount++;
				//��ʼ���ݶ�ȡ�����ݷַ� ���ַ��Ĺ���ÿ5�����ݸ�ÿ�����߳�ȥ����
				if(0==(fileCount%NUMBER_OF_FILES_FOR_EACH_DISPATH)){
					workerIndex=(workerIndex+1)%WORKER_COUNT;
					workers[workerIndex].submitWorkLoad(ste);
					ste=new LinkedHashSet<String>();
					fileCount=0;
				}
			}
			
			
		}
		private Worker[] createAndStartWorkers(
				ConcurrentMap<String, AtomicInteger> repository) {
			Worker[] workers=new Worker[WORKER_COUNT];
			Worker worker;
			UncaughtExceptionHandler eh =new UncaughtExceptionHandler() {
				
				public void uncaughtException(Thread t, Throwable e) {
					e.printStackTrace();
				}
			};
			
			for (int i = 0; i < WORKER_COUNT; i++) {
				worker=new Worker(repository, excluedOperationNames, includedOperationNames, destinationSysName);
			   workers[i]=worker;
			   worker.setUncaughtExceptionHandler(eh);
			   worker.start();
			}
			return workers;
		}
	}
	
	
	@SuppressWarnings("unused")
	private static class Worker extends AbstractTerminateableThread{
        private static final Pattern SPLIT_PATTERN=Pattern.compile("\\|");
		private final ConcurrentMap<String, AtomicInteger> resposity;
		private final BlockingQueue<Set<String>> workQueue;
		
		private final String selfDevice="ESB";
		private final String excluedOperationNames;
		private final String includedOperationNames;
		private final String destinationSysName;
		
		public  Worker(ConcurrentMap<String, AtomicInteger> resposity,
				String excluedOperationNames,String includedOperationNames,
				String destinationSysName){
			this.resposity=resposity;
			workQueue=new ArrayBlockingQueue<Set<String>>(100);
			this.excluedOperationNames=excluedOperationNames;
	    	this.includedOperationNames=includedOperationNames;
	    	this.destinationSysName=destinationSysName;
		}
		
//		public void submitWorkLoad(BufferedReader taskWorkLoad){
//			try {
//				 workQueue.put(taskWorkLoad);
//				 terminationToken.reservations.incrementAndGet();
//			} catch (Exception e) {
//				;
//			}
//		}
		public void submitWorkLoad(Set<String> taskWorkLoad){
			try {
				 workQueue.put(taskWorkLoad);
				 terminationToken.reservations.incrementAndGet();
			} catch (Exception e) {
				;
			}
		}
		
		@Override
		protected void doRun() throws Exception {
			//BufferedReader log
			Set<String> take = workQueue.take();
			Iterator<String> iterator = take.iterator();
			String interfacLogRecord;
			String[] recordParts;
			String timeStmp;
			AtomicInteger reqCounter;
			AtomicInteger existingReqConuter;
			int i=0;
			while(iterator.hasNext()){
				interfacLogRecord=iterator.next();
				recordParts=SPLIT_PATTERN.split(interfacLogRecord,0);
				//������Ч�ļ�¼
				if(recordParts.length<7){
				    continue;	
				}
				if(("request".equals(recordParts[2]))&&
						(recordParts[6]/*�����豸��ESB*/.startsWith(selfDevice))){
					timeStmp=recordParts[0];//ʱ���
					timeStmp=new String(timeStmp.substring(0,19).toCharArray());
					String operName=recordParts[4];
					reqCounter=resposity.get(timeStmp);
					if(null==reqCounter){
						reqCounter=new AtomicInteger(0);
						existingReqConuter=resposity.putIfAbsent(timeStmp, reqCounter);
						if(null!=existingReqConuter){
							 reqCounter=existingReqConuter;
						}
					}
					//��������ǰ�߳�����
					if(isSrcDeviceEEligible(recordParts[5])){
						reqCounter.incrementAndGet();
					}
				}
			}
		}

		private boolean isSrcDeviceEEligible(String string) {
			boolean result=false;
			if("*".equals(destinationSysName)){
				result=true;
			}else if(destinationSysName.equals(string)){
				result=true;
				
			}
			return result;
		}
		
	}
	
  
	

}

