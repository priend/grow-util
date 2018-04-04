package com.pre.roc.grow.voice.dccp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 缓存的基本方法
 *
 * @file MemoryCache.java
 * @dateTime 2017年7月19日 下午3:49:27
 */
public class MemoryCache extends AbstractStore implements Store {

	private final static Map<Object, Element> map = new HashMap<Object, Element>();
	private final CacheConfiguration configure;
	private final CheckManager checkManager;

	/**
	 * 暂时仅弄一个构造
	 * 
	 * @param configure
	 */
	public MemoryCache(CacheConfiguration configure) {
		super(map);
		this.configure = configure;
		this.checkManager = new CheckManager(configure, map);
	}

	@Override
	public String getName() {
		return configure.getCacheName();
	}

	@Override
	public Collection<Element> putAll(Collection<Element> elements) {
		if (elements == null) {
			throw new NullPointerException("elements can't be null");
		}
		check(elements.size());
		for (Element e : elements) {
			putElementStatus(e);
			super.put(e);
		}
		return elements;
	}

	@Override
	public synchronized Element put(Element e) {
		check(1);
		putElementStatus(e);
		return super.put(e);
	}

	/**
	 * 使用一次之后刷新使用过期时间，以及使用次数 <br/>
	 * 并检查该元素是否过期
	 * 
	 * @param e
	 */
	public void changeElement(Element e) {
		e.addHitCount();
		if (!configure.getEternal()) {
			e.refreshLastAccessTime();
		}
	}

	/**
	 * 如果eternal 为true，表示元素永不过期,默认忽略最小元素控制
	 * 
	 * @param e
	 */
	public void putElementStatus(Element e) {
		if (!configure.getEternal() && !e.getIsOpen()) {
			e.setTimeToIdle(configure.getTimeToIdleSeconds());
			e.setTimeToLive(configure.getTimeToLiveSeconds());
		} else {
			e.setTimeToIdle(0);
			e.setTimeToLive(0);
		}
	}

	@Override
	public Element get(Object key) {
		Element e = super.get(key);
		if (e != null) {
			if (!e.isExpired()) {
				changeElement(e);
			} else {
				synchronized (this) {
					remove(e.getKey());
					e = null;
				}
			}
		}
		return e;
	}

	/**
	 * 检查元素 是否为空
	 * 
	 * @param e
	 * @return
	 */
	public boolean checkElement(Element e) {
		if (e == null) {
			throw new NullPointerException("Element can't be  null ");
		}
		if (e.getKey() == null) {
			throw new NullPointerException("Element key can't be  null ");
		}
		return true;
	}

	@Override
	public synchronized void removeAll(Object[] keys) {
		super.removeAll(keys);
	}

	/**
	 * 检查元素是否超过了
	 * 
	 * @param checkSize
	 */
	public void check(int checkSize) {
		if (checkSize <= 0) {
			return;
		}
		Object[] keys = checkManager.checkConfigure(checkSize);
		if (keys != null) {
			removeAll(keys);
		}
	}
}
