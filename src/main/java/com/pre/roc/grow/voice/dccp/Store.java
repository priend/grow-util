package com.pre.roc.grow.voice.dccp;

import java.util.Collection;

public interface Store {

	/**
	 * 
	 * @param elements
	 * @return
	 */
	public Collection<Element> putAll(Collection<Element> elements);

	/**
	 * 获得缓存名字
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 存放
	 * 
	 * @param e
	 * @return
	 */
	public Element put(Element e);

	/**
	 * 获取
	 * 
	 * @param key
	 * @return
	 */
	public Element get(Object key);

	/**
	 * 移除
	 * 
	 * @param key
	 */
	public void remove(Object key);

	/**
	 * @param keys
	 */
	public void removeAll(Object[] keys);

	/**
	 * 清除
	 */
	public void clear();

	/**
	 * 获得的元素
	 * 
	 * @return
	 */
	public Integer size();
}
