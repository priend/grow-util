package com.pre.roc.grow.voice.dccp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 循环检查元素
 *
 * @file CacheListener.java
 * @dateTime 2017年7月19日 下午3:34:53
 */
public class CacheListener extends Thread {

	private Cache cache;

	private volatile boolean stop = false;
	private volatile long ONE_SECOND = 1000;

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public CacheListener(Cache cache) {
		this.cache = cache;
	}

	@Override
	public void run() {
		long time = cache.getConfigure().getDiskExpiryThreadIntervalSeconds();
		try {
			while (!stop) {
				sleep(time * ONE_SECOND);
				threadCheckElement();
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 销毁
	 */
	public void destory() {
		ONE_SECOND = 0;
		stop = true;
	}

	/**
	 * 线程移除
	 */
	public void threadCheckElement() {
		List<Object> keys = new ArrayList<Object>();
		Map<Object, Element> map = cache.getAll();
		if (map != null && map.size() > 0) {
			for (Entry<Object, Element> e0 : map.entrySet()) {
				Element e = e0.getValue();
				if (e != null && e.isExpired()) {
					keys.add(e0.getKey());
				}
			}
		}
		cache.removeAll(keys.toArray());
	}

}
