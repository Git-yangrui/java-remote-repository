package com.newtouc.twophase_termination;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import com.newtouch.threadGuardedSuspend.AlarmAgent;
/**
 * ��Ϣ���÷����߳�
 * 
 * @author Administrator
 * 
 */
public class AlarmSendingThread extends AbstractTerminateableThread {

	private final AlarmAgent alarmAgent = new AlarmAgent();
	private final BlockingQueue<AlarmInfo> alarmQueue;
	private final ConcurrentMap<String, AtomicInteger> subimittedAlarmRegistry;

	public AlarmSendingThread() {
		alarmQueue = new ArrayBlockingQueue<AlarmInfo>(100);
		subimittedAlarmRegistry = new ConcurrentHashMap<String, AtomicInteger>();
		// �������ػ��߳�Timer�Ϳ����˺ͷ���������
		alarmAgent.init();
	}

	@Override
	protected void doRun() throws Exception {
		AlarmInfo alarm;
		//��û���õ�����֮ǰ�˷���һֱ����
		alarm = alarmQueue.take();
		// AtomicInteger �м�ȥ1
		terminationToken.reservations.decrementAndGet();
		try {
			// ���澯��Ϣ��������������
			alarmAgent.sendAlarm(alarm);

		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * ����ָ����棬����Ӧ�Ĺ��ϸ澯��ע�����ɾ����ʹ����Ӧ���ϻָ������ٴγ�����ͬ�Ĺ��� �ù�����Ϣ�ܹ��ϱ���������
		 */
		// alarmType.toString() + ":" + id + "@" + extraInfo
         String values=alarm.alarmType.toString()+":"+alarm.id+"@"+alarm.extraInfo;
         subimittedAlarmRegistry.remove(values);
	}

	/**
	 * �˷����Ƿ��� �����ж��Ƿ��ж����ظ����͵��ж�
	 * 
	 * @param alarmInfo
	 * @return
	 */
	@SuppressWarnings("finally")
	public synchronized int sendAlarm(final AlarmInfo alarmInfo) {
		AlarmType alarmType = alarmInfo.alarmType;
		String id = alarmInfo.id;
		String extraInfo = alarmInfo.extraInfo;
		if (terminationToken.isToShutDown()) {
			// ��¼�澯
			System.err.println("rejected alarm:" + id + "," + extraInfo);
			// return -1;
		}
		int duplicateSubmissionCount = 0;
		try {
			AtomicInteger atomicInteger;
			atomicInteger = subimittedAlarmRegistry.putIfAbsent(
					alarmType.toString() + ":" + id + "@" + extraInfo,
					new AtomicInteger(0));
			if (null == atomicInteger) {
				terminationToken.reservations.incrementAndGet();
				alarmQueue.put(alarmInfo);

			} else {
				// ����δ�ָ����������ظ����͸澯��Ϣ���ʽ����Ӽ���
				duplicateSubmissionCount = atomicInteger.incrementAndGet();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("aaaaaacuole");
		} finally {
			return duplicateSubmissionCount;
		}

	}

	@Override
	public void doCleanUp(Exception cause) {
		if(null!=cause&&(cause instanceof InterruptedException)){
			cause.printStackTrace();
		}
		alarmAgent.disconnected();
	}
	
	
	
}




















