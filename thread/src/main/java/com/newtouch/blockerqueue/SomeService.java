package com.newtouch.blockerqueue;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.newtouc.twophase_termination.AbstractTerminateableThread;

public class SomeService {
	
	public Producer getProducer() {
		return producer;
	}

	public Consumer getConsumer() {
		return consumer;
	}

	private final BlockingQueue<String> queue =

			new ArrayBlockingQueue<String>(10);
	private final Producer producer = new Producer();

	private final Consumer consumer = new Consumer();

	public void shutdown() {
		producer.terminate(true);
		consumer.terminate();

	}

	public void init() {
		producer.start();
		consumer.start();

	}
	


	private class Producer extends AbstractTerminateableThread {
		private int i = 0;

		@Override
		protected void doRun() throws Exception {
			queue.put(String.valueOf(i++));
			//System.out.println("producer producted  ----------------"+i);
			consumer.terminationToken.reservations.incrementAndGet();
			//System.out.println("Producer doRun()"+producer.terminationToken.reservations.get());
		}
		
		@Override
		protected void doCleanUp(Exception cause) {
			if(null!=cause&&(cause instanceof InterruptedException)){
				System.out.println(Thread.currentThread().getName()+"   被强行终止了");
				cause.printStackTrace();
			}
		}

	}

	private class Consumer extends AbstractTerminateableThread {
		@Override
		protected void doRun() throws Exception {
			String take = queue.take();
			//System.out.println("Consumer doRun()"+consumer.terminationToken.reservations.get());
			System.out.println("processing product-" + take);
			try {
				Thread.sleep(new Random().nextInt(100));
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				consumer.terminationToken.reservations.decrementAndGet();
			}
		}

	}

	public static void main(String[] args) throws Exception {
        SomeService service=new SomeService();
        service.init();
        
        Thread.sleep(1000);
        
       service.shutdown();
	}

}
