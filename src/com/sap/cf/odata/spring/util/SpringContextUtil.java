package com.sap.cf.odata.spring.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * NOTE: This Utility class should only be used to retrieve instances managed by
 * Spring (ex:EMF) in non-spring context, ex: ODataJPAServiceFactory.
 * 
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public SpringContextUtil() {

	}

	public static Object getBean(String beanName) throws BeansException {
		return applicationContext.getBean(beanName);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

}
