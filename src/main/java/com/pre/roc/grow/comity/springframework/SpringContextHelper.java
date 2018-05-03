package com.pre.roc.grow.comity.springframework;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @file SpringContextHelper.java
 * @dateTime 2017年5月23日 上午11:35:03
 */
public class SpringContextHelper implements ApplicationContextAware {

	private static final ThreadLocal<ApplicationContext> currentContext;

	static {
		currentContext = new ThreadLocal<ApplicationContext>();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		currentContext.set(applicationContext);
	}

	/**
	 * @param t
	 * @return
	 */
	public static <T> T getBean(Class<T> t) {
		return currentContext.get().getBean(t);
	}

	/**
	 * @param beanName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		return (T) currentContext.get().getBean(beanName);
	}

}
