package com.pre.roc.grow.voice.dccp;

/**
 * 缓存 级的控制判断
 *
 * @file Cache.java
 * @dateTime 2017年7月19日 下午3:48:39
 */
public class Cache extends MemoryCache {

	private CacheConfiguration configure;
	private CacheListener listener;

	public Cache(CacheConfiguration configure) {
		super(configure);
		this.configure = configure;
		if (!configure.getEternal() && configure.getIsNeedCacheCheckListener()) {
			listener = new CacheListener(this);
			listener.start();
		}
	}

	/**
	 * @return
	 */
	public CacheConfiguration getConfigure() {
		return configure;
	}

	/**
	 * 销毁
	 */
	public void destory() {
		try {
			super.clear();
			if (listener != null) {
				listener.interrupt();
				listener = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
