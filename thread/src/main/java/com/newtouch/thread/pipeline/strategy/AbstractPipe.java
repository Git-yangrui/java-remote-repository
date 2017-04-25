package com.newtouch.thread.pipeline.strategy;

import java.util.concurrent.TimeUnit;

public abstract class AbstractPipe<IN, OUT> implements Pipe<IN, OUT> {
	/**
	 * Pipe实例
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
		//什么也不做

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
	 * 留给子类去实现，用于子类实现其任务处理逻辑
	 * @param input
	 *          输入元素（任务）
	 * @return     任务处理的结果
	 * @throws PipeException
	 */
	protected abstract OUT doProcess(IN input) throws PipeException;
	
}
