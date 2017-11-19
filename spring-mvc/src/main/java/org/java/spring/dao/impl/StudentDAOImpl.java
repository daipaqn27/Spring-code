package org.java.spring.dao.impl;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.java.spring.dao.StudentDAO;
import org.java.spring.model.Student;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class StudentDAOImpl implements StudentDAO, DisposableBean{
	
	private static Logger LOGGER = Logger.getLogger(StudentDAOImpl.class);;
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@PostConstruct
	private void createTableIfNotExist(){
		try {
			DatabaseMetaData dbmd = dataSource.getConnection().getMetaData();
			ResultSet resultSet = dbmd.getTables(null, null, "STUDENT", null);
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

	@Override
	public void destroy() throws Exception {
		DriverManager.getConnection("jdbc:derby:D:/Code Spring/dbtemp/sampledb2;create=true");
	}

	@Override
	public List<Student> list() {
		return jdbcTemplate.query("select * from student", new RowMapper<Student>() {

			@Override
			public Student mapRow(ResultSet rs, int i) throws SQLException {
				Student student = new Student();
				student.setId(rs.getInt("id"));
				student.setName(rs.getString("name"));
				student.setAge(rs.getString("age"));
				return student;
			}
		});
	}
	
	public void delete(int id){
		jdbcTemplate.execute("delete from student where id =" + id);
	}

	@Override
	public Student get(int id) {
		return jdbcTemplate.queryForObject("select * from student where id=" + id, 
				new RowMapper<Student>() {

					@Override
					public Student mapRow(ResultSet rs, int i) throws SQLException {
						Student student = new Student();
						student.setId(rs.getInt("id"));
						student.setName(rs.getString("name"));
						student.setAge(rs.getString("age"));
						return student;
					}
				});
	}

	@Override
	public void update(Student student) {
		jdbcTemplate.update("update student set name = ? where id = ?", student.getName(), student.getId());
	}
}
