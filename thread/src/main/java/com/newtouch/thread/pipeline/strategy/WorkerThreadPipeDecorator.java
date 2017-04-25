package com.newtouch.thread.pipeline.strategy;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import com.newtouc.twophase_termination.AbstractTerminateableThread;
import com.newtouc.twophase_termination.TerminationToken;

/**
 * ���ڹ������̵߳�Pipeʵ����
 * �ύ����Pipe��������ָ�������Ĺ������̹߳�ͬ����
 * 
 * ����ʹ��Two_phase  Termination ģʽ
 * @author Administrator
 *
 * @param <IN>
 *            ��������
 * @param <OUT>
 *           �������
 */
public class WorkerThreadPipeDecorator<IN, OUT> implements Pipe<IN, OUT> {
    private final BlockingQueue<IN> workQueue;
    private final Set<AbstractTerminateableThread> workThreads=
    		new HashSet<AbstractTerminateableThread>();
    		
    private final TerminationToken terminationToken=
    		                           new TerminationToken();
    
	private final Pipe<IN, OUT> delegate;
	
	public WorkerThreadPipeDecorator(Pipe<IN, OUT> delegate,int workCount){
		this(new SynchronousQueue<IN>(),delegate,workCount);
	}
	
	public WorkerThreadPipeDecorator(BlockingQueue<IN> workQueue,
			Pipe<IN, OUT> delegate,int workCount){
		if(workCount<=0){
			throw new IllegalArgumentException("workCount should be positive");
		}
		this.workQueue=workQueue;
		this.delegate=delegate;
		for (int i = 0; i < workCount; i++) {
			workThreads.add(new AbstractTerminateableThread() {
				@Override
				protected void doRun() throws Exception {
					try{
					  dispath();
					}finally{
						terminationToken.reservations.decrementAndGet();
					}
				}
			});
		}
	}
	
    protected void dispath() throws Exception {
		IN input=workQueue.take();
		delegate.process(input);
		
	}

	@Override
	public void setNextPipe(Pipe<?, ?> nextPipe) {
		delegate.setNextPipe(nextPipe);
	}

	@Override
	public void init(PipeContext pipeCtx) {
		delegate.init(pipeCtx);
		for(AbstractTerminateableThread thread:workThreads){
			thread.start();
		}
	}

	@Override
	public void shutdown(long timeout, TimeUnit unit) {
		for(AbstractTerminateableThread thread:workThreads){
		    thread.terminate();
		    try {
		    	thread.join(TimeUnit.MICROSECONDS.convert(timeout, unit));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		delegate.shutdown(timeout, unit);
	
	}
    
	@Override
	public void process(IN input) throws InterruptedException {
		workQueue.put(input);
		terminationToken.reservations.incrementAndGet();

	}

}
