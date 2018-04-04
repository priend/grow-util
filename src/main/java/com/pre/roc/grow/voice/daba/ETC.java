package com.pre.roc.grow.voice.daba;

/**
 *
 * @file ETC.java
 * @author rocfly.zhang
 * @dateTime 2017年10月30日 下午5:49:17
 */
public abstract class ETC implements ExecuteCall {

	public ETC() throws Exception {

		new ProxyTransaction().getProxyInstance(this);
	}
}
