package com.newtouch.serial.thread.confinement;
/**
 * 对任务处理的抽象
 * @author Administrator
 *
 * @param <K>  表示任务的类型
 * @param <V>  表示任务处理结果的类型
 */
public interface TaskProcessor<K, V> {
   /**
    * 
    * @param task 任务
    * @return  任务的处理结果
    * @throws Exception
    */
   V doProcess(K task) throws Exception;
}
