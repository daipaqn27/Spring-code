package org.java.spring.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class Student {
	private int id;
	
	@NotBlank
	@Size(min=2, max=100)
	private String name;
	
	@Range(min=1, max=150)
	private String age;
	
	public Student(String name, String age){
		this.name = name;
		this.age = age;
	}

	public Student(){}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
}