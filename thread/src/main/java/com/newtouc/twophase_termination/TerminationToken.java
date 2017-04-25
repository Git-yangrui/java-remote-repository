package com.newtouc.twophase_termination;

import java.lang.ref.WeakReference;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * 线程暂停的代表  标记类
 * @author Administrator
 *
 */
public class TerminationToken {
	
	
    protected volatile boolean toShutDown=false;
    //原子性操作类  如++1  
    /**
     * 任务自增长
     */
    public final AtomicInteger reservations =new AtomicInteger();
    //在多个可停止线程实例共享一个TerminationToken实例的情况下，该队列用于记录那些共享
	//TerminationToken实例的停止线程，以便尽可能减少锁的使用情况下，实现这些线程的停止
	private final Queue<WeakReference<Teiminatable>> coordinatedThreads;
	
	public TerminationToken(){
		coordinatedThreads=new ConcurrentLinkedQueue<WeakReference<Teiminatable>>();
	}
	public boolean isToShutDown() {
		return toShutDown;
	}

	protected void setToShutDown(boolean toShutDown) {
		this.toShutDown = toShutDown;
	}
	
	/**
	 * 通知TeiminationToken实例，共享该实例的所有课停止线程中的一个线程停止了
	 * 以便其停止其他未被停止的线程
	 * @param thread
	 */
	protected void register(Teiminatable thread) {
		coordinatedThreads.add(new WeakReference<Teiminatable>(thread));
	}
	
	/**
	 * 已停止的线程
	 * @param thread
	 */
	protected void notifyThreadTermination(Teiminatable thread){
		WeakReference<Teiminatable> wrThread;
		Teiminatable otherThread;
		while(null!=(wrThread=coordinatedThreads.poll())){
			otherThread=wrThread.get();
			if(null!=otherThread&&otherThread!=thread){
				otherThread.terminate();
			}
		}
	}
    
}
