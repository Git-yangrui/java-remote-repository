package com.newtouch.serial.thread.confinement;
/**
 * ��������ĳ���
 * @author Administrator
 *
 * @param <K>  ��ʾ���������
 * @param <V>  ��ʾ��������������
 */
public interface TaskProcessor<K, V> {
   /**
    * 
    * @param task ����
    * @return  ����Ĵ�����
    * @throws Exception
    */
   V doProcess(K task) throws Exception;
}
