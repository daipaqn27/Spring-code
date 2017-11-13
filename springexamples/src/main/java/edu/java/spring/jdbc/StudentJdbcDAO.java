package edu.java.spring.jdbc;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class StudentJdbcDAO {
	
	private static Logger LOGGER = Logger.getLogger(StudentJdbcDAO.class);;
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	private String insertSQL;
	private String updateAgeByNameSQL;
	private String deleteByIdSQL;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}	
	
	public void setInsertSQL(String insertSQL) {
		this.insertSQL = insertSQL;
	}
	
	public void setUpdateAgeByNameSQL(String updateAgeByNameSQL) {
		this.updateAgeByNameSQL = updateAgeByNameSQL;
	}
	
	public void setDeleteByIdSQL(String deleteByIdSQL) {
		this.deleteByIdSQL = deleteByIdSQL;
	}
	
	private void createTableIfNotExist(String tableName, String createTableSQL){
		try {
			DatabaseMetaData dbmd = dataSource.getConnection().getMetaData();
			ResultSet resultSet = dbmd.getTables(null, null, tableName.toUpperCase(), null);
			if(resultSet.next()){
				LOGGER.info("Table " + resultSet.getString("TABLE_NAME") + " already exits!");
				return;
			}
			jdbcTemplateObject.execute("create table student("
					+ " id bigint primary key generated always as identity(start with 1, increment by 1),"
					+ " name varchar(1000),"
					+ " age integer)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insert(String name, int age){
		jdbcTemplateObject.update(insertSQL, name, age);
		LOGGER.info("Create Record Name= " + name + " Age= " + age);
	}
	
	public int totalRecord(){
//		return jdbcTemplateObject.execute(new StatementCallback<Integer>() {
//			public Integer doInStatement(Statement statement){
//				try {
//					ResultSet rs = statement.executeQuery("select count(*) from student");
//					return new Integer(rs.next()?rs.getInt(1):0);
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//				return 0;
//			}
//		});
		return jdbcTemplateObject.execute((Statement statement)->{
			ResultSet rs = statement.executeQuery("select count(*) from student");
			return new Integer(rs.next()?rs.getInt(1):0);
		});
	}
	
	private final static class StudentRowMapper implements RowMapper<Student>{
		@Override
		public Student mapRow(ResultSet rs, int i) throws SQLException {
			try {
				Student student = new Student();
				student.setId(rs.getInt("id"));
				student.setName(rs.getString("name"));
				student.setAge(rs.getInt("age"));
				return student;
			} catch (Exception e) {
				LOGGER.error(e,e);
				return null;
			}
			
		}
	}
	
	public List<Student> loadStudent(String name){
		return jdbcTemplateObject.query(
				"select * from student where name like '%" + name + "%'", 
//				new RowMapper<Student>(){
//
//					public Student mapRow(ResultSet rs, int i) throws SQLException {
//						Student student = new Student();
//						student.setId(rs.getInt("id"));
//						student.setName(rs.getString("name"));
//						student.setAge(rs.getInt("age"));
//						return student;
//					}
//					
//				}
				new StudentRowMapper());
	}
	
	public void ubdateAgeByName(String name, int age){
		jdbcTemplateObject.update(updateAgeByNameSQL, age, name);
		LOGGER.info("Update age = " + age + " by name = " + name);
	}
	
	public void deleteById(int id){
		jdbcTemplateObject.update(deleteByIdSQL,id);
		LOGGER.info("Delete student id = " + id);
	}
	
	public int[] add(List<Student> students){
		List<Object[]> batch = new ArrayList<>();
		students.forEach(student -> batch.add(new Object[]{student.getName(),student.getAge()}));
		return jdbcTemplateObject.batchUpdate(insertSQL, batch);
	}
	
	public void save (Object name, Object age){
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		String countSQL = "select count(*) from student";
		try{
			int total = jdbcTemplateObject.queryForObject(countSQL, Integer.class);
			LOGGER.info("before save data, total record is " + total);
			
			String sql = "insert into Student(name, age) values(?,?)";
			jdbcTemplateObject.update(sql, name, age);
			
			total = jdbcTemplateObject.queryForObject(countSQL, Integer.class);
			LOGGER.info("after save data, total record is " + total);
			
			String countQuery2 = "select count(* from student";
			total = jdbcTemplateObject.queryForObject(countQuery2, Integer.class);
			
			transactionManager.commit(status);
		}catch(Exception exp){
			transactionManager.rollback(status);
			
			int total = jdbcTemplateObject.queryForObject(countSQL, Integer.class);
			LOGGER.info("after rollback, total record is " + total);
		}
	}
}
