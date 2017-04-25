package com.newtouch.workStealingmodel;

import java.util.concurrent.BlockingDeque;

import com.newtouch.util.ArrayReturnIndex;

public class WorkStealingChannel<P> implements WorkStealingEnableChannel<P> {
	private static final int BlockingDeque = 0;
	private final BlockingDeque<P>[] managedQueues;

	public WorkStealingChannel(BlockingDeque<P>[] managedQueues) {
		this.managedQueues = managedQueues;
	}

	@Override
	public P take(BlockingDeque<P> preferredDeque) throws InterruptedException {
		int queueIndex = -1;
		BlockingDeque<P> targetQueue;
		P product = null;
		if (preferredDeque == null) {
			queueIndex = (queueIndex + 1) % managedQueues.length;
			// 为了让product从指定的数组的指定的索引上的blockingdeque上取product
			targetQueue = managedQueues[queueIndex];
			// 试图从其他的队列的尾部去取数据
			product = targetQueue.pollLast();
			if (null == product) {
				// 随机“窃取”其他受管队列的产品
				queueIndex = (int) (System.currentTimeMillis() % managedQueues.length);
				targetQueue = managedQueues[queueIndex];
				product = targetQueue.takeLast();
				System.out
						.println("stealed from " + queueIndex + ":" + product);
			}

			return product;
		} else {
			// ArrayReturnIndex<BlockingDeque<P>> arrayReturnIndex=new
			// ArrayReturnIndex<BlockingDeque<P>>();
			// 返回当前指定的preferredDeque在数组中的位置
			int returnIndexLocation = ArrayReturnIndex.returnIndexLocation(
					managedQueues, preferredDeque);
			// 优先从制定的受管队列中取“产品”
			targetQueue = preferredDeque;

			// 试图从指定的队列首位置取“产品” 这是对列
			// 虽然
			if (null != targetQueue) {
				product = targetQueue.poll();
			}

			// 虽然target不为空 。但是头顶位置可能数据为空而取出的数据product 为空，所以要去其他的
			// BlockingDeque去取数据
			while (null == product) {

				queueIndex = (queueIndex + 1) % managedQueues.length;
				if (returnIndexLocation == queueIndex) {
					product = targetQueue.pollLast();
				}
				// 为了让product从指定的数组的指定的索引上的blockingdeque上取product
				targetQueue = managedQueues[queueIndex];
				// 试图从其他的队列的尾部去取数据
				product = targetQueue.pollLast();
				if (targetQueue == preferredDeque) {
					break;
				}
			}
			if (null == product) {
				// 随机“窃取”其他受管队列的产品
				queueIndex = (int) (System.currentTimeMillis() % managedQueues.length);
				targetQueue = managedQueues[queueIndex];
				product = targetQueue.takeLast();
				System.out
						.println("stealed from " + queueIndex + ":" + product);

			}
			return product;
		}
	}

	@Override
	public P take() throws InterruptedException {

		return take(null);
	}

	@Override
	public void put(P product) throws InterruptedException {
		// 随机的去放
		int targetIndex = (product.hashCode() % managedQueues.length);
		BlockingDeque<P> preferredDeque = managedQueues[targetIndex];

		preferredDeque.put(product);
	}

}
