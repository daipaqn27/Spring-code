package edu.java.spring.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JdbcApp {
	private static Logger LOGGER = Logger.getLogger(JdbcApp.class);
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context =
				new ClassPathXmlApplicationContext("beanjdbc.xml");
		StudentJdbcDAO jdbcDAO = (StudentJdbcDAO) context.getBean("studentJdbcDAO");
//		jdbcDAO.insert("Tran Van A", 24);
		
//		jdbcDAO.loadStudent("Van").forEach(student -> System.out.println(student));
		
//		jdbcDAO.ubdateAgeByName("Tran Van A", 25);
//		jdbcDAO.deleteById(2);
		
//		List<Student> students = new ArrayList<>();
//		students.add(new Student("Tran Thi A",17));
//		students.add(new Student("Le Van L",20));
//		students.add(new Student("Phan THi Z",25));
		
//		int[] values = jdbcDAO.add(students);
//		
//		for (int i = 0; i < values.length; i++) {
//			LOGGER.info("add record " + i + ":" + (values[i] == 0 ? "failed" : "success"));
//		}
//		
//		System.out.println("Total student: " + jdbcDAO.totalRecord());
		
		jdbcDAO.save("Tran Thi A", 23);
		
		context.close();
	}
}
