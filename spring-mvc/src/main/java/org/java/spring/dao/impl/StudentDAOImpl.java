package org.java.spring.dao.impl;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.java.spring.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class StudentDAOImpl {
	
	private static Logger LOGGER = Logger.getLogger(StudentDAOImpl.class);;
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	@PostConstruct
	private void createTableIfNotExist(String tableName, String createTableSQL){
		try {
			DatabaseMetaData dbmd = dataSource.getConnection().getMetaData();
			ResultSet resultSet = dbmd.getTables(null, null, tableName.toUpperCase(), null);
			if(resultSet.next()){
				LOGGER.info("Table " + resultSet.getString("TABLE_NAME") + " already exits!");
				return;
			}
			jdbcTemplate.execute("create table student("
					+ " id bigint primary key generated always as identity(start with 1, increment by 1),"
					+ " name varchar(1000),"
					+ " age integer)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insert(Student student){
		jdbcTemplate.update("insert into student(name, age) values(?,?)", 
				student.getName(), student.getAge());
		LOGGER.info("Created Record Name = " + student.getName());
	}
}
