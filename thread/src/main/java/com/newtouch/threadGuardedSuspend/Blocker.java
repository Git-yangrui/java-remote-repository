package com.newtouch.threadGuardedSuspend;

import java.util.concurrent.Callable;

public  interface Blocker {
	
  <V> V callWithGuard(GuardedAction<V> action) throws Exception;
  
  void signalAfter(Callable<Boolean> callable) throws Exception;
  
  void signal() throws Exception;
  
  void broadcastAfter(Callable<Boolean> callable) throws Exception;
}
