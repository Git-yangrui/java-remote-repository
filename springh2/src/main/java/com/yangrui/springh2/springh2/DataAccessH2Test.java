package com.yangrui.springh2.springh2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:application-Core.xml")
public class DataAccessH2Test {

	private JdbcTemplate jdbcTemplate;
	private DataSourceTransactionManager transactionManager;
	@Autowired
	private ApplicationContext applicationContext;

	@Before
	public void setUp() {
		jdbcTemplate = (JdbcTemplate) applicationContext.getBean("jdbcTemplate");
		transactionManager=(DataSourceTransactionManager) applicationContext.getBean("txManager");
	}

	@Test
	public void testJdbcTemplate() {
		System.out.println("jdbcTemplate:" + jdbcTemplate);
		assertTrue(jdbcTemplate != null);
	}

	@Test
	public void testTableTest() {
		Object[] args = { 1 };
		String name = jdbcTemplate.queryForObject("select name from test where id= ?", args, String.class);
		System.out.println("=======>name:" + name);

		assertEquals("Hello World", name);
	}

	@Test
	public void testTableUser() {
		Object[] args = { 1 };
		int age = jdbcTemplate.queryForObject("select age from user where id= ?", args, Integer.class).intValue();
		System.out.println("=======>age:" + age);

		assertEquals(1, age);
	}

	@Test
	public void testTableUserLike() {
		Object[] args = { "%Hello World%" };
		int count = jdbcTemplate.queryForObject("select count(1) from user where name like ?", args, Integer.class)
				.intValue();
		System.out.println("=======>count:" + count);

		assertEquals(60, count);
	}

	@Test
	public void testTableUserObject() {

		Object[] args = { "%Hello World%" };
		List<String> list = jdbcTemplate.query("select * from user where name like ?", args, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("name");
			}
		});
		System.out.println("=======>list:" + list);

		assertEquals(60, list.size());
	}
	
	@Test
	public void test_delete() throws Exception, SystemException{
	   String nameupdateString="helllllllllllll";
	   jdbcTemplate.update("update user set name='"+nameupdateString+"' where id=1");
	   Object[] args = { 1 };
	   String name = jdbcTemplate.queryForObject("select name from user where id= ?",args, String.class);
	   System.out.println("=======>name:" + name);

		assertEquals("helllllllllllll", name);
	   
	}

	@After
	public void tearDown() {
		// do nothing
	}

}