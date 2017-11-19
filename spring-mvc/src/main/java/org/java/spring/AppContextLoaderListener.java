package org.java.spring;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

public class AppContextLoaderListener extends ContextLoaderListener{
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("---> Da huy ung dung");
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("---> Da khoi tao ung dung");
	}
}