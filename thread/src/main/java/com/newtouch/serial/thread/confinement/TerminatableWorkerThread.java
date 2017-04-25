package com.newtouch.serial.thread.confinement;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.newtouc.twophase_termination.AbstractTerminateableThread;

public class TerminatableWorkerThread<K, V> extends AbstractTerminateableThread {
	
	private final BlockingQueue<Runnable> workQueue;
	//��������ִ������Ķ���
	private final TaskProcessor<K,V> taskProcessor;
	
	public TerminatableWorkerThread(BlockingQueue<Runnable> workQueue,
			TaskProcessor<K,V> taskProcessor){
		this.workQueue=workQueue;
		this.taskProcessor=taskProcessor;
	}
	
	public Future<V> submit(final K task) throws InterruptedException{
		Callable<V> callable=new Callable<V>(){
			@Override
			public V call() throws Exception {
				//��ʵ����ȥʵ��doProcess()
				return taskProcessor.doProcess(task);
			}
		};
		FutureTask<V> ft=new FutureTask<V>(callable);
		workQueue.put(ft);
		terminationToken.reservations.incrementAndGet();
		return ft;
		
	}
	
	/**
	 * �ڸ����run�ᱻѭ������
	 */
	@Override
	protected void doRun() throws Exception {
		Runnable runnable=workQueue.take();
		try{
			runnable.run();
		}finally{
			terminationToken.reservations.decrementAndGet();
		}
		
		

	}

}
