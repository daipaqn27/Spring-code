package edu.java.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;

public class HelloWorld {
	private String message;
	@Autowired(required = true)
	@Qualifier("helloJavaClazz2")
	private HelloClazz clazz;
	
	public HelloWorld(){}
	
	public HelloWorld(String name, HelloClazz clazz){
		message = "From Hello constructor: " + name + ":" + clazz.message;
	}
	
	public void sayHello(){
		clazz.printMessage();
		System.out.println("From helloworld: " + message);
	}
	
	public HelloClazz getClazz() {
		return clazz;
	}

	public void setClazz(HelloClazz clazz) {
		this.clazz = clazz;
	}

	public String getMessage() {
		return message;
	}
	
	@Required
	public void setMessage(String message) {
		this.message = message;
	}
	
}
