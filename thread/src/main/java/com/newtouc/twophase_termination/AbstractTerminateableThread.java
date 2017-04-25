package com.newtouc.twophase_termination;

public abstract class AbstractTerminateableThread 
                  extends Thread implements Teiminatable {
	
     /**
      * 线程暂停的代表 标记类
      */
    public  final TerminationToken terminationToken;
	//守护线程进行监听
    private Thread thread;
    public AbstractTerminateableThread(){
    	
    	this(new TerminationToken());
    	thread=new Thread(new Runnable() {
			
			@Override
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
     * 留给子类实现其线程处理逻辑
     * @throws Exception
     */
    protected abstract void doRun() throws Exception;
    
     /**
      * 留给子类实现，用于实现线程停止后的一些清理
      */
    protected void doCleanUp(Exception cause) {
		//省略
	}

	/**
	 * 留给子类实现。用于执行线程停止所需要的操作
	 */
	protected void doTerminate() {
		
		//省略
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
	
	@Override
	public void terminate() {
		terminationToken.setToShutDown(true);
		//System.out.println("terminate方法  terminationToken.setToShutDown(true) 后"+System.currentTimeMillis());
		try {
			doTerminate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//线程没有任务了  强制停止 product线程一直没有往里面塞东西，故terminationToken.reservations.get()一直为0；
			 if(terminationToken.reservations.get()<=0){
				 //product线程被强制终止
				 super.interrupt();
			 }
		}
	}
	
	
	public void terminate(boolean waitUtilThreadTerminated){
		terminate();
		if(waitUtilThreadTerminated){
			try {
				//System.out.println("join 方法qian"+System.currentTimeMillis());
				this.join();
				//System.out.println("join 方法hou "+System.currentTimeMillis());
			} catch (Exception e) {
				Thread.currentThread().interrupt();
			}
		}
	}

}
