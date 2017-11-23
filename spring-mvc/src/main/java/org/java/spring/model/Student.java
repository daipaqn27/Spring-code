package org.java.spring.model;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@XmlRootElement(name = "item")
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
	
	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
}