package edu.java.spring;

import java.util.List;

import javax.management.loading.PrivateClassLoader;

import org.springframework.beans.factory.DisposableBean;

public class HelloClazz implements DisposableBean{
	String message;
	private List<JavaClazz> clazzes;
	
	public HelloClazz(){
		this.message = "From Contructor: Say Hello";
	}
	
	public HelloClazz(int peson){
		this.message = "from contructor person: say hello " + peson + " person";
	}
	
	public HelloClazz(HelloClazz clazz){
		this.message = clazz.message;
	}
	
	public List<JavaClazz> getClazzes() {
		return clazzes;
	}

	public void setClazzes(List<JavaClazz> clazzes) {
		this.clazzes = clazzes;
	}

	public void setMessage(String message){this.message = "Call from setter " + message;}
	
	public void printMessage(){
		System.out.println("Your message: " + message);
	}
	
	public void initMessage(){
		System.out.println("Calling init method...");
		message = "From init method: Say hello";
	}
	
	public void destroy(){
		message = null;
		System.out.println("Message is null");
	}
	
	
}
