package com.pre.roc.grow.voice.skill.efficient;

/**
 * 
 * @file EventExecute.java
 * @author ROCFLY ZHANGE PENGFEI
 * @dateTime 2014年7月16日 下午4:11:03
 */
public interface Generant {

	/**
	 * 
	 * 方法说明：增加任务
	 * 
	 */
	public void run();

	/**
	 * 
	 * 方法说明：停止
	 * 
	 */
	public void interrupt();

	/**
	 * 
	 * 方法说明：设置暂停
	 * 
	 */
	public void suspend();

	/**
	 * 
	 * 方法说明：是否暂停
	 * 
	 * @return Boolean
	 */
	public Boolean isInterrupted();

	/**
	 * 
	 * 方法说明：外部结束时调用
	 * 
	 */
	public void disclose();

	/**
	 * 方法说明：线程活动时间 (ms)
	 * 
	 * @param frequencyMillis
	 *            时间
	 */
	public void setFrequencyMillis(Long frequencyMillis);
}
