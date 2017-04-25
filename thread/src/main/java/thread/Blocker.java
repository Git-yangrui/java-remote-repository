package thread;

import java.util.concurrent.Callable;

public interface Blocker {
   /**  
    * �ڱ�����������ʱ��ִ��Ŀ�궯��������������ǰ�̣߳�ֱ����������������  
    * @param guardAction ������������Ŀ�궯��
    * @return 
    * @throws Exception
    */
	<V> V callWithGuard(GuardedAction<V> guardAction)throws Exception;
   /**
    * ִ��stateOpreation ��ָ���Ĳ����󣬾����Ƿ��ѱ�Blocker
    * ���ݹҵ������߳��е�һ���߳�
    * @param stateOperation
    * @throws Exception
    *    ����״̬�Ĳ�������call�����ķ���ֵΪtureʱ���÷����Żỽ��
    */
	void signalAfter(Callable<Boolean> stateOperation) throws Exception;
    void signal() throws InterruptedException;
    /**
     * ִ��stateOpreation��ָ���Ĳ����󣬾����Ƿ��ѱ�Blocker
     * ���ݹҵ������߳�
     * @param stateOperation
     * @throws Exception
     *    ����״̬�Ĳ�������call�����ķ���ֵΪtureʱ���÷����Żỽ��
     */
    void broadcastAfter(Callable<Boolean> stateOperation ) throws Exception;
    

}
