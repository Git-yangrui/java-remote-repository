package com.newtouc.twophase_termination;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import com.newtouch.threadGuardedSuspend.AlarmAgent;
/**
 * 消息调用发送线程
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
		// 开启了守护线程Timer和开启了和服务器连接
		alarmAgent.init();
	}

	@Override
	protected void doRun() throws Exception {
		AlarmInfo alarm;
		//在没有拿到数据之前此方法一直阻塞
		alarm = alarmQueue.take();
		// AtomicInteger 中减去1
		terminationToken.reservations.decrementAndGet();
		try {
			// 将告警信息发送至服务器上
			alarmAgent.sendAlarm(alarm);

		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * 处理恢复警告，将相应的故障告警从注册表中删除，使得相应故障恢复后若再次出现相同的故障 该故障信息能够上报到服务器
		 */
		// alarmType.toString() + ":" + id + "@" + extraInfo
         String values=alarm.alarmType.toString()+":"+alarm.id+"@"+alarm.extraInfo;
         subimittedAlarmRegistry.remove(values);
	}

	/**
	 * 此方法是发送 并且判断是否有二次重复发送的判断
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
			// 记录告警
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
				// 故障未恢复，不用再重复发送告警信息，故紧增加计数
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




















