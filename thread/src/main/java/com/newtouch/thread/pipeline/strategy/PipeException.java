package com.newtouch.thread.pipeline.strategy;
/**
 * 处理的异常类extends  Exception
 * @author Administrator
 *
 */
public class PipeException extends Exception{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 抛出异常的pipe的实例
	 */
	public final Pipe<?,?> sourcePipe;
	/**
	 * 抛出异常的Pipe实例在抛出异常时处理的输入元素
	 */
	public final Object input;
	
	//构造方法
	public PipeException(Pipe<?,?> sourcePipe,Object input,String message){
		super(message);
		this.sourcePipe=sourcePipe;
		this.input=input;
	}
	
	public PipeException(Pipe<?,?> sourcePipe,Object input,String message,
			Throwable cause){
		super(message,cause);
		this.sourcePipe=sourcePipe;
		this.input=input;
	}
}
