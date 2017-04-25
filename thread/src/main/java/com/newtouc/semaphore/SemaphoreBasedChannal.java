package com.newtouc.semaphore;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

import com.newtouch.productandconsumer.Channel;

public class SemaphoreBasedChannal<P> implements Channel<P>{
    private final BlockingQueue<P> queue;
    private final Semaphore semaphore;
    
    public SemaphoreBasedChannal(BlockingQueue<P> queue,int flowLimit){
    	this.queue=queue;
    	this.semaphore=new Semaphore(flowLimit);
    }
    
	public P take() throws InterruptedException {
		return queue.take();
	}

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
