package com.nqminh.api.service;

import java.util.List;

import com.nqminh.api.requestbody.UserDetailsRequestBody;
import com.nqminh.api.responsebody.UserDetailsResponseBody;
import com.nqminh.dto.UserDto;

public interface UserService {

	List<UserDetailsResponseBody> getAllUsers();

	List<UserDetailsResponseBody> getUsersByRoleId(int roleId);

	UserDetailsResponseBody getUserById(int id);

	UserDetailsResponseBody getUserByUsername(String input);

	UserDto createUser(UserDetailsRequestBody requestBody);

	UserDetailsResponseBody updateUser(UserDetailsRequestBody requestBody, int userId);

	void deleteUser(int id);
}
