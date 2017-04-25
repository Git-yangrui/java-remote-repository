package com.newtouch.thread.pipeline.strategy;
/**
 * 用于对处理阶段抛出的异常进行处理
 * @author Administrator
 *
 */
public interface PipeContext {
	/**
	 * 用于对处理阶段抛出的异常进行处理
	 * @param exp
	 */
    void handleError(PipeException exp);
}
