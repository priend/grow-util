package com.pre.roc.grow.voice.skill.efficient;

/**
 * 
 * @file EventTask.java
 * @author ROCFLY ZHANGE PENGFEI
 * @dateTime 2014年7月16日 下午5:58:09
 * @param <T>
 */
public interface EventTask<T> {

	/**
	 * @param ctx
	 * @return
	 * @throws Exception
	 */
	T doInContext(EventContext ctx) throws Exception;
}
