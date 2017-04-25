package com.newtouch.thread.pipeline.strategy;
/**
 * ������쳣��extends  Exception
 * @author Administrator
 *
 */
public class PipeException extends Exception{
	
	private static final long serialVersionUID = 1L;
	/**
	 * �׳��쳣��pipe��ʵ��
	 */
	public final Pipe<?,?> sourcePipe;
	/**
	 * �׳��쳣��Pipeʵ�����׳��쳣ʱ���������Ԫ��
	 */
	public final Object input;
	
	//���췽��
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
