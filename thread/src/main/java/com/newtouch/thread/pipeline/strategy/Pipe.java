package com.newtouch.thread.pipeline.strategy;

import java.util.concurrent.TimeUnit;

public interface Pipe<IN, OUT> {
	/**
	 * 设置当前Pipe实例的下一个Pipe实例
	 * 
	 * @param nextPipe
	 *            下一个Pipe实例
	 */
	void setNextPipe(Pipe<?, ?> nextPipe);

	/**
	 * 初始化当前Pipe实例对外提供的服务
	 * 
	 * @param pipeCtx
	 */
	void init(PipeContext pipeCtx);
	/**
	 * 停止当前的Pipe实例对外提供的服务
	 * @param timeout
	 * @param unit
	 */
	void shutdown(long timeout,TimeUnit unit);
	/**
	 * 对输入元素进行处理，并将处理的结果作为下一个Pipe实例的输入
	 * @param put
	 * @throws InterruptedException
	 */
	void process(IN input) throws InterruptedException;
	
	
	
}
