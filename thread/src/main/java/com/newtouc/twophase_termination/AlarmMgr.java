package com.newtouc.twophase_termination;
/**
 * �澯��Ϣ��Ҫ���浽��������ר�ŵķ����̷߳��ͣ����͵�ʱ��ȥ���ø澯������AlarmAgent
 * @author Administrator
 *
 */
public class AlarmMgr {
	//AlarmMgrΨһʵ��
    private static final AlarmMgr INSTANCE=new AlarmMgr();
    //��ֹ��¼
    private volatile boolean shutdownRequested=false;
    
    private final AlarmSendingThread alarmSendingThread;
    
    /**
     * ˽�й���
     */
    private AlarmMgr(){
    	alarmSendingThread=new AlarmSendingThread();
    }
    
    public static AlarmMgr getInstance(){
    	return INSTANCE;
    }
    
    /**
     * ���ͱ�����������
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
