package com.pre.roc.grow.voice.skill.produce;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.pre.roc.grow.voice.skill.efficient.EventContext;
import com.pre.roc.grow.voice.skill.efficient.EventExecutor;
import com.pre.roc.grow.voice.skill.efficient.GenerantSingle;

/**
 * 
 * @file Reconstructor.java
 * @author ROCFLY ZHANGE PENGFEI
 * @dateTime 2014年7月16日 下午3:37:56
 */
public class Reconstructor {

	private static Reconstructor instance;
	private Queue<Resource<?>> resources;

	/**
	 * 
	 */
	private Reconstructor()
	{
		resources = new ConcurrentLinkedQueue<Resource<?>>();
	}

	/**
	 * @return
	 */
	public static Reconstructor getInstance() {
		if (null == instance)
		{
			instance = new Reconstructor().worker();
		}
		return instance;
	}

	/**
	 * @param resource
	 */
	public <T extends Resource<T>> void cleanResource(T resource) {
		resources.add(resource);
	}

	/**
	 * @return
	 */
	private Reconstructor worker() {
		new GenerantSingle(new EventExecutor(), new Reconstructor.EventTask()).run();
		return this;
	}

	/**
	 * 
	 * @file Reconstructor.java
	 * @author ROCFLY ZHANGE PENGFEI
	 * @dateTime 2014年7月17日 下午6:19:56
	 */
	private class EventTask implements EventContext {

		@Override
		public Boolean task() {
			if (resources.size() >= 1)
			{
				resources.poll().detach();
				return true;
			}
			return false;
		}

		@Override
		public void close() {
			System.out.println("线程结束: " + new Date(System.currentTimeMillis()));
		}
	}
}
