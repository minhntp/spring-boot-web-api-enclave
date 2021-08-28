package com.nqminh.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Size(min = 5, message = "Username must have at least 5 characters")
	@Column(name = "username", unique = true)
	@NotBlank
	private String username;

	@Size(min = 6, message = "Password must have at least 6 characters")
	@JsonIgnore
	@Column(name = "password")
	private String encyptedPassword;

	private String fullName;

	@ManyToOne
	@JoinColumn(name = "role_id")
	@NotNull
	private Role role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Article> articles = new ArrayList<>();

	public User() {
	}

	public User(String fullName, @NotNull Role role) {
		this.fullName = fullName;
		this.role = role;
	}

	public User(@Size(min = 5, message = "Username must have at least 5 characters") String username,
			@Size(min = 6, message = "Password must have at least 6 characters") String encyptedPassword,
			String fullName,
			Role role) {
		this.username = username;
		this.encyptedPassword = encyptedPassword;
		this.fullName = fullName;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEncyptedPassword() {
		return encyptedPassword;
	}

	public void setEncyptedPassword(String encyptedPassword) {
		this.encyptedPassword = encyptedPassword;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User {id=" + id + ", username=" + username + ", encyptedPassword=" + encyptedPassword + ", fullName="
				+ fullName + ", role=" + role + "}";
	}

}
