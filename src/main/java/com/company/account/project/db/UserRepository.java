package com.company.account.project.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.company.account.project.user.User;

/** Repository for handling user entity
 * 
 * @author chandrans1
 *
 */
@Component
public class UserRepository {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public UserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<User> getAll(){
		return jdbcTemplate.query("select * from User", new BeanPropertyRowMapper<User>(User.class));
	}
	

	public int createUser(String firstName, String secondName, Long accountNumber) {
		return jdbcTemplate.update("insert into user (first_name, second_name, account_number) values (?, ?, ?)", new Object[]{firstName, secondName, accountNumber});
	}

	public int deleteUser(Long id) {
		return jdbcTemplate.update("delete user where id = ?", new Object[]{id});
	}	
}
