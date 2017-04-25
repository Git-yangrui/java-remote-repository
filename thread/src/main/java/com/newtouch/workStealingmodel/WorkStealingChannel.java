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
			// Ϊ����product��ָ���������ָ���������ϵ�blockingdeque��ȡproduct
			targetQueue = managedQueues[queueIndex];
			// ��ͼ�������Ķ��е�β��ȥȡ����
			product = targetQueue.pollLast();
			if (null == product) {
				// �������ȡ�������ܹܶ��еĲ�Ʒ
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
			// ���ص�ǰָ����preferredDeque�������е�λ��
			int returnIndexLocation = ArrayReturnIndex.returnIndexLocation(
					managedQueues, preferredDeque);
			// ���ȴ��ƶ����ܹܶ�����ȡ����Ʒ��
			targetQueue = preferredDeque;

			// ��ͼ��ָ���Ķ�����λ��ȡ����Ʒ�� ���Ƕ���
			// ��Ȼ
			if (null != targetQueue) {
				product = targetQueue.poll();
			}

			// ��Ȼtarget��Ϊ�� ������ͷ��λ�ÿ�������Ϊ�ն�ȡ��������product Ϊ�գ�����Ҫȥ������
			// BlockingDequeȥȡ����
			while (null == product) {

				queueIndex = (queueIndex + 1) % managedQueues.length;
				if (returnIndexLocation == queueIndex) {
					product = targetQueue.pollLast();
				}
				// Ϊ����product��ָ���������ָ���������ϵ�blockingdeque��ȡproduct
				targetQueue = managedQueues[queueIndex];
				// ��ͼ�������Ķ��е�β��ȥȡ����
				product = targetQueue.pollLast();
				if (targetQueue == preferredDeque) {
					break;
				}
			}
			if (null == product) {
				// �������ȡ�������ܹܶ��еĲ�Ʒ
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
		// �����ȥ��
		int targetIndex = (product.hashCode() % managedQueues.length);
		BlockingDeque<P> preferredDeque = managedQueues[targetIndex];

		preferredDeque.put(product);
	}

}
