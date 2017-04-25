package com.newtouch.thread.pipeline.strategy;

import java.util.concurrent.TimeUnit;

public interface Pipe<IN, OUT> {
	/**
	 * ���õ�ǰPipeʵ������һ��Pipeʵ��
	 * 
	 * @param nextPipe
	 *            ��һ��Pipeʵ��
	 */
	void setNextPipe(Pipe<?, ?> nextPipe);

	/**
	 * ��ʼ����ǰPipeʵ�������ṩ�ķ���
	 * 
	 * @param pipeCtx
	 */
	void init(PipeContext pipeCtx);
	/**
	 * ֹͣ��ǰ��Pipeʵ�������ṩ�ķ���
	 * @param timeout
	 * @param unit
	 */
	void shutdown(long timeout,TimeUnit unit);
	/**
	 * ������Ԫ�ؽ��д�����������Ľ����Ϊ��һ��Pipeʵ��������
	 * @param put
	 * @throws InterruptedException
	 */
	void process(IN input) throws InterruptedException;
	
	
	
}
