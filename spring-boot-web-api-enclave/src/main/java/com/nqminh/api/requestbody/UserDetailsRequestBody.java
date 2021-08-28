package com.nqminh.api.requestbody;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDetailsRequestBody {

	@Size(min = 5, message = "Username must have at least 5 characters")
	@NotBlank
	private String username;

	@Size(min = 6, message = "Password must have at least 6 characters")
	@NotNull
	private String password;

	private String fullName;

	@NotNull
	private Integer roleId;

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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
