package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionVarBlocker implements Blocker {
	private final Lock lock;
	
	private final Condition condition;
	
	public ConditionVarBlocker(Lock lock) {
		this.lock = lock;
		this.condition = lock.newCondition();
	}
	
	public ConditionVarBlocker() {
		this.lock = new ReentrantLock();
		this.condition = lock.newCondition();
	}

	@Override
	public <V> V callWithGuard(GuardedAction<V> guardAction) throws Exception {
		lock.lockInterruptibly();
		V result;
		try {
			final Predicate guard=guardAction.guard;
			while(!guard.evaluate()){
				condition.await();
			}
			result=guardAction.call();
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("cuole");
		}finally{
			lock.unlock();
			
		}
	}

	@Override
	public void signalAfter(Callable<Boolean> stateOperation) throws Exception {
		lock.lockInterruptibly();
		try {
			if(stateOperation.call()){
				condition.signal();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("“Ï≥£");
		}finally{
			lock.unlock();
		}

	}

	@Override
	public void signal() throws InterruptedException {
		lock.lockInterruptibly();
		try {
			condition.signal();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("“Ï≥£");
		}finally{
			lock.unlock();
		}

	}

	@Override
	public void broadcastAfter(Callable<Boolean> stateOperation) throws Exception {
		lock.lockInterruptibly();
		try {
			if(stateOperation.call()){
				condition.signalAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("“Ï≥£");
		}finally{
			lock.unlock();
		}

	}

}
