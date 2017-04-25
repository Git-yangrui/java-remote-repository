package com.newtouch.threadGuardedSuspend;

  
import java.util.concurrent.TimeUnit;  
import java.util.concurrent.locks.Lock;  
import java.util.concurrent.locks.ReentrantLock;  
  
public class TTTT {  
    public static void main(String[] args) throws Exception{  
  
        Thread i1 = new Thread(new RunIt3());  
        Thread i2 = new Thread(new RunIt3());  
        i1.start(); 
        i2.start();  
        //Thread.sleep(50);
        
        i2.interrupt();  
        System.out.println("main");
    }  
  
}  
  
class RunIt3 implements Runnable{  
  
    private static Lock lock = new ReentrantLock();  
    public void run(){  
        try{  
            //---------------------------------a  
            lock.lock();  
            //lock.lockInterruptibly();   
              
              
            System.out.println(Thread.currentThread().getName() + " running");  
            TimeUnit.SECONDS.sleep(20);  
            lock.unlock();  
            System.out.println(Thread.currentThread().getName() + " finished");  
        }  
        catch (Exception e){  
        	e.printStackTrace();
            System.out.println(Thread.currentThread().getName() + " interrupted");  
  
        }  finally{
        	lock.unlock();
        }
  
    }  
}  