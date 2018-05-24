package com.company.account.project.crud;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.company.account.project.db.UserRepository;
import com.company.account.project.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(UserAccountOperationController.class)
public class UserAccountOperationControllerTest {

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private MockMvc accountOperationController;
	
	@Autowired
	private ObjectMapper mapper;	

	@Test
	public void getAllAccounts() throws Exception {
		User user = buildUser();
		given(this.userRepository.getAll()).willReturn(Arrays.asList(user));
		accountOperationController.perform(get("/rest/account/json")).andExpect(status().isOk()).andExpect(
				content().json("[{\"id\":1,\"firstName\":\"John\",\"secondName\":\"Doe\",\"accountNumber\":123}]"));
	}

	@Test
	public void addAccount() throws Exception {
		UserRequest userRequest = new UserRequest();
		userRequest.setAccountNumber(123L);
		userRequest.setFirstName("John");
		userRequest.setSecondName("Doe");
		given(this.userRepository.createUser("John", "Doe", 123L)).willReturn(1);
		accountOperationController
				.perform(post("/rest/account/json", userRequest).content(mapper.writeValueAsString(userRequest))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json("{\"message\":\"Account has been successfully added\"}"));
	}
	
	@Test
	public void deleteAccount() throws Exception {
		given(this.userRepository.deleteUser(123L)).willReturn(1);
		accountOperationController.perform(delete("/rest/account/json/123")).andExpect(status().isOk())
				.andExpect(content().json("{\"message\":\"Account successfully deleted\"}"));
	}	
	
	private User buildUser() {
		User user = new User();
		user.setAccountNumber(123L);
		user.setFirstName("John");
		user.setSecondName("Doe");
		user.setId(1L);
		return user;
	}
}
