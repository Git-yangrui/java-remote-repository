package com.newtouch.thread.pipeline.strategy;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
/**
 * pipeline的实现类  
 * @author Administrator
 *
 * @param <T>
 * @param <OUT>
 */
public class SimplePipeline<T,OUT> extends AbstractPipe<T, OUT> 
                              implements Pipeline<T, OUT>{
    private final Queue<Pipe<?, ?>> pipes=new LinkedList<Pipe<?,?>>();
    private final ExecutorService helperExecutor;
    
    public SimplePipeline(){
    	this(Executors.newSingleThreadExecutor(new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t=new Thread(r,"SimplePipeline_helper");
				t.setDaemon(true);
				return t;
			}
		}));
    }

    public SimplePipeline(final ExecutorService helperExecutor){
    	  super();
          this.helperExecutor=helperExecutor;
    }
    
	@Override
	public void addPipe(Pipe<?, ?> pipe) {
		pipes.add(pipe);
	}

	@Override
	protected OUT doProcess(T input) throws PipeException {
		//什么也不做
		return null;
	}
	public <INPUT,OUTPUT> void addAsWorkerThreadBased(
			Pipe<INPUT, OUTPUT> delegate,int workCount){
		addPipe(new WorkerThreadPipeDecorator<INPUT, OUTPUT>(delegate, workCount));
	}
	
 
}
