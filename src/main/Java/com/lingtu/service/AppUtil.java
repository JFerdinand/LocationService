package com.lingtu.service;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AppUtil implements ApplicationContextAware {

	protected static ApplicationContext context;

	public AppUtil() {

	}
	

	public static ApplicationContext getContext() {

		return context;
	}

	public static Object getBean(String beanname) {

		if (beanname == null || context == null) {
			return null;
		}
		return context.getBean(beanname);
	}

	public void setApplicationContext(ApplicationContext tcontext) throws BeansException {
		System.err.println ("=============setApplicationContext===========");
		context = tcontext;
	}

}
