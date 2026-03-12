package com.example.session.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SignupApiData {


	@NotNull(message = "name required")
	@Size(min = 2, message = "min 2 character required")
	private String name;
	@NotNull(message = "email required")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "invalid email")
	private String email;
	@NotNull(message = "password required")
	@Size(min = 8, message = "min 8 characters required")
	private String password;
	
	@NotNull(message = "mobile required")
	@Size(min = 10, message = "min 10 characters required")
	
	private String mobile;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	
}
