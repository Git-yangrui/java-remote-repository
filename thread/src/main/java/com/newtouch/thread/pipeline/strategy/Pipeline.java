package com.newtouch.thread.pipeline.strategy;

public interface Pipeline<IN,OUT> extends Pipe<IN, OUT>
{
	/**
	 * ����Pipelineʵ�������һ��pipeʵ��
	 * @param pipe
	 *           pipeʵ��
	 */
   void addPipe(Pipe<?,?> pipe);
   
}
