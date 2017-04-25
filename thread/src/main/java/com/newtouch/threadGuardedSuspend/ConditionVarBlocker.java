package com.newtouch.threadGuardedSuspend;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionVarBlocker implements Blocker {
	private final Lock lock;

	private final Condition condition;

	public ConditionVarBlocker(Lock lock) {
		this.lock=lock;
		this.condition=lock.newCondition();
	}
	
	public ConditionVarBlocker() {
		this.lock=new ReentrantLock();
		this.condition=lock.newCondition();
	}

	@Override
	public <V> V callWithGuard(GuardedAction<V> action) throws Exception {
		lock.lockInterruptibly();
		V result;
		try {
			final Predicate guard=action.agentConnected;
			while(!guard.evaluate()){
				System.out.println("δ����״̬��Ҫ�ȴ�����");
				condition.await();
			}
			System.out.println("�Ѿ������Ϸ�����");
			result=action.call();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("callWithGuard����������");
			
		}finally{
			lock.unlock();
		}
	}

	@Override
	public void signalAfter(Callable<Boolean> callable) throws Exception {
		lock.lockInterruptibly();
		try {
			if(callable.call()){
				condition.signalAll();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("signalAfter����������");
			
		}finally{
			lock.unlock();
		}
		
		

	}

	@Override
	public void signal() throws Exception {
		this.lock.lockInterruptibly();
		try{
			condition.signal();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("signal����������");
			
		}finally{
			lock.unlock();
		}

	}

	@Override
	public void broadcastAfter(Callable<Boolean> callable) throws Exception {
		lock.lockInterruptibly();
		try {
			if(callable.call()){
				condition.signalAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("signalAfter����������");
			
		}finally{
			lock.unlock();
		}

	}

}
