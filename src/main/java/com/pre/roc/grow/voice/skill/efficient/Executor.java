package com.pre.roc.grow.voice.skill.efficient;

/**
 * 
 * @file Executor.java
 * @author ROCFLY ZHANGE PENGFEI
 * @dateTime 2014年7月16日 下午4:52:54
 */
public interface Executor {

	/**
	 * 
	 */
	public void start();

	/**
	 * 
	 */
	public void shutdown();

	/**
	 * 
	 * @param task
	 */
	public void execute(Runnable command);

}
