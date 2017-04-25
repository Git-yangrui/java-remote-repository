package thread;

import java.util.Timer;
import java.util.concurrent.Callable;

public class AlarmAgent {
	// 用于记录AlarmAgent是否连接上告警服务器
	private volatile boolean connectedToServer = false;
	//模式角色 Predicate
	private final Predicate agentConnected = new Predicate() {
		@Override
		public boolean evaluate() {
			return connectedToServer;
		}
	};
//	public static void main(String[] args) {
//		System.out.println(new AlarmAgent().agentConnected.evaluate());	
//	}
	//模式角色 Blocker
	private final Blocker blocker=new ConditionVarBlocker();
	//心跳计时器
    private final Timer heartbeatTimer=new Timer(true);
    
    
	public void sendAlarm(final AlarmInfo alarm) throws Exception{
		
	}
}
