package com.newtouch.threadGuardedSuspend;

import java.util.concurrent.Callable;

public abstract class GuardedAction<V> implements Callable<V> {
	protected final Predicate agentConnected;
	public GuardedAction(Predicate guard) {
		this.agentConnected = guard;
	}
}
