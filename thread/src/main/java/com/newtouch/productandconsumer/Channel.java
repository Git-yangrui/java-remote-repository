package com.newtouch.productandconsumer;

public interface Channel<P> {
  /**
   * ��ͨ����ȡ��һ����Ʒ
   * @return ����Ʒ��
   * @throws InterruptedException
   */
   P take() throws InterruptedException;
  /**
   * ��ͨ���зŲ�Ʒ
   * @param product  ����Ʒ��
   * @throws InterruptedException
   */
   void put(P product) throws InterruptedException;
}
