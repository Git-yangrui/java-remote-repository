package com.newtouc.twophase_termination;
/**
 * 告警信息需要缓存到队列中由专门的发送线程发送，发送的时候去调用告警发送类AlarmAgent
 * @author Administrator
 *
 */
public class AlarmMgr {
	//AlarmMgr唯一实例
    private static final AlarmMgr INSTANCE=new AlarmMgr();
    //终止记录
    private volatile boolean shutdownRequested=false;
    
    private final AlarmSendingThread alarmSendingThread;
    
    /**
     * 私有构造
     */
    private AlarmMgr(){
    	alarmSendingThread=new AlarmSendingThread();
    }
    
    public static AlarmMgr getInstance(){
    	return INSTANCE;
    }
    
    /**
     * 发送报警到队列中
     * @param type
     * @param id
     * @param extraInfo
     * @return
     */
    public int sendAlarm(AlarmType type,String id,String extraInfo){
    	System.out.println("--------------------------------");
    	int duplicateSubmissionCount=0;
        try {
			AlarmInfo alarmInfo=new AlarmInfo(type, id, extraInfo);
		    duplicateSubmissionCount = alarmSendingThread.sendAlarm(alarmInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return duplicateSubmissionCount;
    }
    
    public void init(){
    	alarmSendingThread.start();	
    }
    
    public synchronized void shutdown(){
    	if(shutdownRequested){
    		throw new IllegalStateException("shutdown");
    		
    	}
    	alarmSendingThread.terminate(true);
    	shutdownRequested=true;
    }
}
