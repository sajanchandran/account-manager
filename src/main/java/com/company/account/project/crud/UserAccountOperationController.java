package com.company.account.project.crud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.account.project.db.UserRepository;
import com.company.account.project.user.User;

/** Allowed Operations on Accounts
 * 
 * @author chandrans1
 *
 */
@RestController
public class UserAccountOperationController {
	
	@Autowired
	private UserRepository userRepository;
	
	/** Returns the complete list of users.
	 * 
	 * @return List<User>
	 */
	@GetMapping("/rest/account/json")
    public List<User> getAllAccounts() {
        return userRepository.getAll(); 
    }

	/** Adding a new account
	 * 
	 * @param userRequest
	 * @return response
	 */
	@PostMapping(value="/rest/account/json", consumes="application/json", produces="application/json")
	public Response addAccount(@RequestBody UserRequest userRequest){
		int noOfRecordsAffected = userRepository.createUser(userRequest.getFirstName(), userRequest.getSecondName(), userRequest.getAccountNumber());
		if(noOfRecordsAffected > 0){
			return new Response("Account has been successfully added");
		}return new Response("Contact Administrator - Problem occured");
	}
	
	/** Method for deleting the user.
	 * 
	 * @param id
	 * @return response
	 */
	@DeleteMapping(value="/rest/account/json/{id}", produces="application/json")
	public Response deleteAccount(@PathVariable("id") Long id){
		int noOfRecordsAffected = userRepository.deleteUser(id);
		if(noOfRecordsAffected == 1){
			return new Response("Account successfully deleted");
		}return new Response("Contact Administrator - Problem occured");
	}	
}
