package com.vz.app.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Name bean
 * @author cparikh
 *
 */
public class Name {

	@NotNull(message = "First Name is Mandatory")
	@NotBlank(message = "First Name is Mandatory")
	@Pattern(regexp = "[a-z-A-Z]*", message = "First name has invalid characters")
	private String first;

	@NotNull(message = "Last Name is Mandatory")
	@NotBlank(message = "Last Name is Mandatory")
	@Pattern(regexp = "[a-z-A-Z]*", message = "Last name has invalid characters")
	private String last;

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

}
