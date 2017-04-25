package com.newtouch.productandconsumer;

import java.util.concurrent.BlockingQueue;

public class BlockingQueueChannel<P> implements Channel<P>{
    private final BlockingQueue<P> queue;
    
    
    public BlockingQueueChannel(BlockingQueue<P> queue){
    	this.queue=queue;
    }
    
	@Override
	public P take() throws InterruptedException {
		// TODO Auto-generated method stub
		return queue.take();
	}

	@Override
	public void put(P product) throws InterruptedException {
		queue.put(product);
		
	}
   
}
