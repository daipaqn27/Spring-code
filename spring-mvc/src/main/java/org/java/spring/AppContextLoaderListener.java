package org.java.spring;

import org.springframework.web.context.ContextLoaderListener;

public class AppContextLoaderListener extends ContextLoaderListener{
	
	public void contextDestroyed(){
		System.out.println("---> Da huy ung dung");
	}
	
	public void contextInitialized(){
		System.out.println("---> Da khoi tao ung dung");
	}
}