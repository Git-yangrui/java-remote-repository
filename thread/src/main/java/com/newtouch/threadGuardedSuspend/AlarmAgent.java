package com.newtouch.threadGuardedSuspend;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

import com.newtouc.twophase_termination.AlarmInfo;

public  class AlarmAgent {
	private volatile boolean connectedToServer = false;
	private final Predicate agentConnected = new Predicate() {
		public boolean evaluate() {
			return connectedToServer;
		}
	};
	private final ConditionVarBlocker blocker = new ConditionVarBlocker();
	private final Timer heratbeatTimer = new Timer(true);

	public void sendAlarm(final AlarmInfo alarm) throws Exception {
		// Void
		GuardedAction<String> guardedAction = new GuardedAction<String>(
				agentConnected) {
			public String call() throws Exception {
				doSendAlarm(alarm);
				return "�澯�ɹ�";
			}
		};
		blocker.callWithGuard(guardedAction);
		//String returnInfo = guardedAction.call();
		//System.out.println(returnInfo);
		//connectedToServer = false;
	}

	private void doSendAlarm(AlarmInfo alarm) {
		System.out.println("sending alarm " + alarm);
		try {
			Thread.sleep(500);
		} catch (Exception e) {

		}
	}

	public void init() {
		Thread connectingThread = new Thread(new ConnectingTask());
		connectingThread.start();
		heratbeatTimer.schedule(new HeartbeatTask(), 600, 2000);
	}

	protected void onConnected() {
		try {
			blocker.signalAfter(new Callable<Boolean>() {
				public Boolean call() throws Exception {
					connectedToServer = true;
					System.out.println("connectedToServer�ɹ�");
					return Boolean.TRUE;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	protected void onDisConnected() {
		connectedToServer = false;
	}
		public void disconnected() {
			connectedToServer = false;
		}
	private class ConnectingTask implements Runnable {

		public void run() {

			try {
				Thread.sleep(500);
			} catch (Exception e) {

			}
			onConnected();
		}

	}

	private class HeartbeatTask extends TimerTask {
		
		public HeartbeatTask() {
			System.out.println("HeartbeatTask");
		}
		
		private boolean testConnection() {
			return connectedToServer;
		}
		private void reConnect() {
			ConnectingTask connectingTask = new ConnectingTask();
			connectingTask.run();
		}
		@Override
		public void run() {
				if (!testConnection()) {
					System.out.println("timer��ʼ�������ӷ�����");
					onDisConnected();
					reConnect();
				}
		}

	}

}
