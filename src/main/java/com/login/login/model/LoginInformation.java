package com.login.login.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * This class holds different logins 
 * A login contains a email, username and password 
 */
public class LoginInformation {
	
	private String email;
	private String username;
	private String password;
	
	public LoginInformation(@JsonProperty("email") String email, 
							@JsonProperty("username") String username,
							@JsonProperty("password") String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "{\n"
				+ email + ",\n"
				+ username + ",\n"
				+ password + "\n}";
	}
}
