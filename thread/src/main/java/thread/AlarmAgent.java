package thread;

import java.util.Timer;
import java.util.concurrent.Callable;

public class AlarmAgent {
	// ���ڼ�¼AlarmAgent�Ƿ������ϸ澯������
	private volatile boolean connectedToServer = false;
	//ģʽ��ɫ Predicate
	private final Predicate agentConnected = new Predicate() {
		@Override
		public boolean evaluate() {
			return connectedToServer;
		}
	};
//	public static void main(String[] args) {
//		System.out.println(new AlarmAgent().agentConnected.evaluate());	
//	}
	//ģʽ��ɫ Blocker
	private final Blocker blocker=new ConditionVarBlocker();
	//������ʱ��
    private final Timer heartbeatTimer=new Timer(true);
    
    
	public void sendAlarm(final AlarmInfo alarm) throws Exception{
		
	}
}
