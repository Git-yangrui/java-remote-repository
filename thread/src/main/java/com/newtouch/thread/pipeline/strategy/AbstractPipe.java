package com.newtouch.thread.pipeline.strategy;

import java.util.concurrent.TimeUnit;

public abstract class AbstractPipe<IN, OUT> implements Pipe<IN, OUT> {
	/**
	 * Pipeʵ��
	 */
	protected volatile Pipe<?, ?> nextPipe = null;
	protected volatile PipeContext pipeCtx;

	@Override
	public void init(PipeContext pipeCtx) {
		this.pipeCtx=pipeCtx;

	}

	@Override
	public void setNextPipe(Pipe<?, ?> nextPipe) {
		this.nextPipe=nextPipe;

	}

	@Override
	public void shutdown(long timeout, TimeUnit unit) {
		//ʲôҲ����

	}
	/**
	 * 
	 */
	@Override
	public void process(IN input) throws InterruptedException {
		try {
			OUT out=doProcess(input);
			if(null!=nextPipe){
			    if(null!=out){
			    	((Pipe<OUT, ?>)nextPipe).process(out);
			    }
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (PipeException e) {
			pipeCtx.handleError(e);
		}
		
	}
	/**
	 * ��������ȥʵ�֣���������ʵ�����������߼�
	 * @param input
	 *          ����Ԫ�أ�����
	 * @return     ������Ľ��
	 * @throws PipeException
	 */
	protected abstract OUT doProcess(IN input) throws PipeException;
	
}
