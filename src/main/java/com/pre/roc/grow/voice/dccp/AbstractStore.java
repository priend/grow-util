package com.pre.roc.grow.voice.dccp;

import java.util.Map;

/**
 * 简单实现
 *
 * @file AbstractStore.java
 * @dateTime 2017年7月19日 下午3:55:39
 */
public abstract class AbstractStore implements Store {

	protected Map<Object, Element> map;

	public AbstractStore() {
	}

	public AbstractStore(Map<Object, Element> map) {
		this.map = map;
	}

	@Override
	public Element get(Object key) {
		return map.get(key);
	}

	public Map<Object, Element> getAll() {
		return map;
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Element put(Element e) {
		return map.put(e.getKey(), e);
	}

	@Override
	public void remove(Object key) {
		map.remove(key);
	}

	@Override
	public Integer size() {
		return map.size();
	}

	@Override
	public void removeAll(Object[] keys) {
		for (int i = 0; i < keys.length; i++) {
			remove(keys[i]);
		}
	}
}
