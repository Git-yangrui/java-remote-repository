package com.newtouc.semaphore;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

import com.newtouch.productandconsumer.Channel;

public class SemaphoreBasedChannal<P> implements Channel<P>{
    private final BlockingQueue<P> queue;
    //设定访问的县城数  最多只能有flowLimit个线程同时访问 该take方法
    private final Semaphore semaphore;
    
    public SemaphoreBasedChannal(BlockingQueue<P> queue,int flowLimit){
    	this.queue=queue;
    	this.semaphore=new Semaphore(flowLimit);
    }
    
	@Override
	public P take() throws InterruptedException {
		return queue.take();
	}

	@Override
	public void put(P product) throws InterruptedException {
		semaphore.acquire();
		try {
			queue.put(product);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			semaphore.release();
		}
		
	}

}
