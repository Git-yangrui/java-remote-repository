package com.newtouch.serial.thread.confinement;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author Administrator
 * 
 * @param <K>
 *            serializer��workThread���ύ�������Ӧ������
 * @param <V>
 *            service ��������ֵ������
 */
public abstract class AbstractSerilizer<K, V> {
	private final TerminatableWorkerThread<K, V> workerThread;

	public AbstractSerilizer(BlockingQueue<Runnable> queue,
			TaskProcessor<K, V> processor) {
		workerThread = new TerminatableWorkerThread<K, V>(queue, processor);
	}
	
	/**
	 * ��������ȥʵ�֣����ڸ���ָ���Ĳ���������Ӧ������ʵ��
	 * @param args  �����б�
	 * @return  ����ʵ��  �����ύ��workThread
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
