package com.nqminh.api.requestbody;

import java.io.Serializable;

public class UserLoginRequestBody implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1324579309078276583L;

	private String username;

	private String password;

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

}
