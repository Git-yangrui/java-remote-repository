package com.newtouc.twophase_termination;

import java.lang.ref.WeakReference;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * �߳���ͣ�Ĵ���  �����
 * @author Administrator
 *
 */
public class TerminationToken {
	
	
    protected volatile boolean toShutDown=false;
    //ԭ���Բ�����  ��++1  
    /**
     * ����������
     */
    public final AtomicInteger reservations =new AtomicInteger();
    //�ڶ����ֹͣ�߳�ʵ������һ��TerminationTokenʵ��������£��ö������ڼ�¼��Щ����
	//TerminationTokenʵ����ֹͣ�̣߳��Ա㾡���ܼ�������ʹ������£�ʵ����Щ�̵߳�ֹͣ
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
	 * ֪ͨTeiminationTokenʵ���������ʵ�������п�ֹͣ�߳��е�һ���߳�ֹͣ��
	 * �Ա���ֹͣ����δ��ֹͣ���߳�
	 * @param thread
	 */
	protected void register(Teiminatable thread) {
		coordinatedThreads.add(new WeakReference<Teiminatable>(thread));
	}
	
	/**
	 * ��ֹͣ���߳�
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
