package com.newtouch.productandconsumer;

public interface Channel<P> {
  /**
   * 从通道中取出一个产品
   * @return “产品”
   * @throws InterruptedException
   */
   P take() throws InterruptedException;
  /**
   * 往通道中放产品
   * @param product  “产品”
   * @throws InterruptedException
   */
   void put(P product) throws InterruptedException;
}
