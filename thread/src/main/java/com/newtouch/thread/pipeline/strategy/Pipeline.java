package com.newtouch.thread.pipeline.strategy;

public interface Pipeline<IN,OUT> extends Pipe<IN, OUT>
{
	/**
	 * 往该Pipeline实例中添加一个pipe实例
	 * @param pipe
	 *           pipe实例
	 */
   void addPipe(Pipe<?,?> pipe);
   
}
