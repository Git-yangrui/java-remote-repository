package com.newtouc.twophase_termination;

public abstract class AbstractTerminateableThread 
                  extends Thread implements Teiminatable {
	
     /**
      * �߳���ͣ�Ĵ��� �����
      */
    public  final TerminationToken terminationToken;
	//�ػ��߳̽��м���
    private Thread thread;
    public AbstractTerminateableThread(){
    	
    	this(new TerminationToken());
    	thread=new Thread(new Runnable() {
			
			public void run() {
				for(;;){
					if(terminationToken.reservations.get()==0){
						
						break;
					}
				}
				
			}
		});
    }
//    public void terminat(){
//    	thread.start();
//    }
    public AbstractTerminateableThread(TerminationToken terminationToken){
    	super();
    	this.terminationToken=terminationToken;
    	terminationToken.register(this);
    }
    /**
     * ��������ʵ�����̴߳����߼�
     * @throws Exception
     */
    protected abstract void doRun() throws Exception;
    
     /**
      * ��������ʵ�֣�����ʵ���߳�ֹͣ���һЩ����
      */
    protected void doCleanUp(Exception cause) {
		//ʡ��
	}

	/**
	 * ��������ʵ�֡�����ִ���߳�ֹͣ����Ҫ�Ĳ���
	 */
	protected void doTerminate() {
		
		//ʡ��
	}
	@Override
	public void run() {
		Exception ex=null;
		try {
			for(;;){
				
				if(terminationToken.isToShutDown()
						&&terminationToken.reservations.get()<=0){
				    break;
				}
				doRun();	
			}
		} catch (Exception e) {
			ex=e;
		}finally{
			try {
				doCleanUp(ex);
			} catch (Exception e2) {
				
				e2.printStackTrace();
			}finally{
				terminationToken.notifyThreadTermination(this);
			}
		}
	}
	
	@Override
	public void interrupt() {
		terminate();
	}
	
	public void terminate() {
		terminationToken.setToShutDown(true);
		//System.out.println("terminate����  terminationToken.setToShutDown(true) ��"+System.currentTimeMillis());
		try {
			doTerminate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//�߳�û��������  ǿ��ֹͣ product�߳�һֱû������������������terminationToken.reservations.get()һֱΪ0��
			 if(terminationToken.reservations.get()<=0){
				 //product�̱߳�ǿ����ֹ
				 super.interrupt();
			 }
		}
	}
	
	
	public void terminate(boolean waitUtilThreadTerminated){
		terminate();
		if(waitUtilThreadTerminated){
			try {
				//System.out.println("join ����qian"+System.currentTimeMillis());
				this.join();
				//System.out.println("join ����hou "+System.currentTimeMillis());
			} catch (Exception e) {
				Thread.currentThread().interrupt();
			}
		}
	}

}
