package com.newtouch.thread.pipeline.strategy;
/**
 * ���ڶԴ���׶��׳����쳣���д���
 * @author Administrator
 *
 */
public interface PipeContext {
	/**
	 * ���ڶԴ���׶��׳����쳣���д���
	 * @param exp
	 */
    void handleError(PipeException exp);
}
