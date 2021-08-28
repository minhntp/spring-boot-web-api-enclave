package com.nqminh.api.responsebody;

public class UserDetailsResponseBody {

	private String username;
	private String fullName;
	private String roleName;

	public UserDetailsResponseBody() {
	}

	public UserDetailsResponseBody(String username, String fullName, String roleName) {
		this.username = username;
		this.fullName = fullName;
		this.roleName = roleName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
