package edu.java.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;

public class ContextStartEventHandler implements ApplicationListener<ContextStoppedEvent>{

	public void onApplicationEvent(ContextStartedEvent event) {
		System.out.println("Context started at " + event.getTimestamp());
	}

	public void onApplicationEvent(ContextStoppedEvent event) {
		System.out.println("Context stopped at " + event.getTimestamp());
	}

}
