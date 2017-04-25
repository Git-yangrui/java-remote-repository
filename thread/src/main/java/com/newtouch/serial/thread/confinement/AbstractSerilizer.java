package com.newtouch.serial.thread.confinement;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author Administrator
 * 
 * @param <K>
 *            serializer向workThread所提交的任务对应的类型
 * @param <V>
 *            service 方法返回值得类型
 */
public abstract class AbstractSerilizer<K, V> {
	private final TerminatableWorkerThread<K, V> workerThread;

	public AbstractSerilizer(BlockingQueue<Runnable> queue,
			TaskProcessor<K, V> processor) {
		workerThread = new TerminatableWorkerThread<K, V>(queue, processor);
	}
	
	/**
	 * 留给子类去实现，用于根据指定的参数生成相应的任务实例
	 * @param args  参数列表
	 * @return  任务实例  用于提交给workThread
	 */
	protected abstract K makeTask(Object...args);
	
	protected Future<V> service(Object...args) throws Exception{
		K task=makeTask(args);
		Future<V> resultPromis=workerThread.submit(task);
		return resultPromis;
	}
	
	public void init(){
		workerThread.start();
	}
	
	public void shutdown(){
		
		workerThread.terminate();
	}
	
}
