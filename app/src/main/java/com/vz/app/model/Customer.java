package com.vz.app.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
/**
 * Customer Bean
 * @author cparikh
 *
 */
public class Customer {

	@NotNull(message = "Name cannot be null")
	private Name name;

	private String id;

	@NotNull(message = "Email id is required")
	@NotBlank(message = "Email id is required")
	@Email(message = "Email id is not valid")
	private String email;

	private String phoneNumber;

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
