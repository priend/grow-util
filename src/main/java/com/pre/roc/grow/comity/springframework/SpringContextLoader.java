package com.pre.roc.grow.comity.springframework;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @file SpringContextLoader.java
 * @author CMCC.HPE.Pactera.Zh
 * @dateTime 2018年4月4日 下午5:16:14
 */
public class SpringContextLoader {

	private ApplicationContext initApplicationContext;

	public SpringContextLoader() {

	}

	/**
	 * @param path
	 */
	public void initClassPathXmlApplicationContext(String... path) {
		initApplicationContext = new ClassPathXmlApplicationContext(path);
	}

	/**
	 * @return
	 */
	public ApplicationContext getInitApplicationContext() {
		return initApplicationContext;
	}

	/**
	 * @param initApplicationContext
	 */
	public void setInitApplicationContext(ApplicationContext initApplicationContext) {
		this.initApplicationContext = initApplicationContext;
	}
}
