package com.company.account.project.db;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserRepositoryTest {

	@Mock
	private JdbcTemplate mockJdbcTemplate;
	private UserRepository userRepository;

	@Before
	public void onceBeforeEachTest(){
		MockitoAnnotations.initMocks(this);
		userRepository = new UserRepository(mockJdbcTemplate);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getAllWorksCorrectly(){
		userRepository.getAll();
		verify(mockJdbcTemplate, times(1)).query(Mockito.eq("select * from User"), Mockito.any(BeanPropertyRowMapper.class));
	}
	
	
	@Test
	public void createUserWorksCorrectly(){
		ArgumentCaptor<Object[]> arg = forClass(Object[].class);
		userRepository.createUser("John", "Doe", new Long(1234));
		verify(mockJdbcTemplate, times(1)).update(Mockito.eq("insert into user (first_name, second_name, account_number) values (?, ?, ?)"), 
				arg.capture());
		List<Object[]> value = arg.getAllValues();
		assertTrue("John".equals(value.get(0)));
		assertTrue("Doe".equals(value.get(1)));
		assertTrue(new Long(1234).equals(value.get(2)));
	}		
	
	@Test
	public void deleteUserWorksCorrectly(){
		Mockito.reset(mockJdbcTemplate);
		Mockito.when(mockJdbcTemplate.update(Mockito.eq("delete user where id = ?"), (Object[])Mockito.any())).thenReturn(1);
		int noOfRecords = userRepository.deleteUser(123L);
		assertTrue(noOfRecords == 1);
	}	
}
