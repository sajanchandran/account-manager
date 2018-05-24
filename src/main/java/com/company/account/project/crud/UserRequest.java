package com.company.account.project.crud;

/** The web user request object 
 * 
 * @author chandrans1
 *
 */
public class UserRequest {

	private String firstName;
	
	private String secondName;
	
	private Long accountNumber;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	
}
