package com.nqminh.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nqminh.api.exception.MyBadRequestException;
import com.nqminh.api.exception.MyNoDataException;
import com.nqminh.api.exception.MyNotFoundException;
import com.nqminh.api.requestbody.UserDetailsRequestBody;
import com.nqminh.api.responsebody.UserDetailsResponseBody;
import com.nqminh.dto.UserDto;
import com.nqminh.entity.Role;
import com.nqminh.entity.User;
import com.nqminh.repository.RoleRepository;
import com.nqminh.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<UserDetailsResponseBody> getAllUsers() {

		List<User> users = userRepository.findAll();

		if (users.isEmpty()) {
			throw new MyNoDataException();
		}

		List<UserDetailsResponseBody> response = new ArrayList<>();

		for (User user : users) {
			response.add(toResponseBody(user));
		}

		return response;
	}

	@Override
	public List<UserDetailsResponseBody> getUsersByRoleId(int roleId) {

		Optional<Role> foundRole = roleRepository.findById(roleId);

		if (foundRole.isEmpty()) {
			throw new MyNotFoundException("Role id: " + roleId + " not found");
		}

		List<User> users = userRepository.findAllByRole(foundRole.get());

		if (users.isEmpty()) {
			throw new MyNoDataException();
		}

		List<UserDetailsResponseBody> response = new ArrayList<>();

		for (User user : users) {
			response.add(toResponseBody(user));
		}

		return response;
	}

	@Override
	public UserDetailsResponseBody getUserById(int id) {

		try {

			User user = userRepository.findById(id).get();

			return toResponseBody(user);

		} catch (Exception e) { // NoSuchElementException when not found

			throw new MyNotFoundException("User id: " + id + " not found");
		}
	}

	@Override
	public UserDetailsResponseBody getUserByUsername(String username) {

		try {

			Optional<User> foundUser = userRepository.findByUsername(username);

			return toResponseBody(foundUser.get());

		} catch (Exception e) { // NoSuchElementException if user is null

			throw new MyNotFoundException("Username: " + username + " not found");
		}
	}

	@Override
	public UserDto createUser(UserDetailsRequestBody request) {

		// input: username, fullName, encyptedPassword, roleId

		// check username
		Optional<User> foundUser = userRepository.findByUsername(request.getUsername());

		if (!foundUser.isEmpty()) {
			throw new MyBadRequestException("Username: " + request.getUsername() + " already exists");
		}

		// check role id
		Optional<Role> foundRole = roleRepository.findById(request.getRoleId());

		if (foundRole.isEmpty()) {
			throw new MyBadRequestException("Invalid role id: " + request.getRoleId());
		}

		User user = toUser(request);

		user.setRole(foundRole.get());

		User savedUser = userRepository.save(user);

		return toUserDto(savedUser);
	}

	@Override
	public UserDetailsResponseBody updateUser(UserDetailsRequestBody request, int userId) {

		// check user id
		Optional<User> foundUser = userRepository.findById(userId);

		if (foundUser.isEmpty()) {
			throw new MyBadRequestException("User id: " + userId + " not found");
		}

		// check role id
		Optional<Role> foundRole = roleRepository.findById(request.getRoleId());

		if (foundRole.isEmpty()) {
			throw new MyBadRequestException("Invalid role id: " + request.getRoleId());
		}

		// check username
		Optional<User> checkNewUserName = userRepository.findByUsername(request.getUsername());

		if (!checkNewUserName.isEmpty()) {

			if (checkNewUserName.get().getId() != userId) {

				throw new MyBadRequestException("Username: " + request.getUsername() + " already exists");
			}
		}

		User user = foundUser.get();

		user.setUsername(request.getUsername().trim());
		user.setEncyptedPassword(passwordEncoder.encode(request.getPassword()));
		user.setFullName(request.getFullName().trim());

		user.setRole(foundRole.get());

		User savedUser = userRepository.save(user);

		return toResponseBody(savedUser);
	}

	@Override
	public void deleteUser(int id) {

		// check user id
		Optional<User> foundUser = userRepository.findById(id);

		if (foundUser.isEmpty()) {
			throw new MyBadRequestException("User id: " + id + " not found");
		}

		if (foundUser.get().getRole().getId() == 1) {
			throw new MyBadRequestException("Can not delete an admin");
		}

		userRepository.delete(foundUser.get());

	}

	// -------------------------------------------------------------------------

	private UserDto toUserDto(User user) {

		// User: id, username, encyptedPassword, fullName, Role, Articles
		// UserDto: id, username, fullName, roleName

		UserDto userDto = new UserDto();

		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		userDto.setFullName(user.getFullName());

		userDto.setRoleName(user.getRole().getName());

		return userDto;

	}

	private User toUser(UserDetailsRequestBody request) {

		// UserDetailsRequestBody: username, password (plain), fullName, roleId
		// User: id, username, encyptedPassword, fullName, Role, Articles

		User user = new User();

		user.setUsername(request.getUsername().trim());
		user.setEncyptedPassword(passwordEncoder.encode(request.getPassword()));
		user.setFullName(request.getFullName().trim());

		return user;
	}

	private UserDetailsResponseBody toResponseBody(User user) {

		// User: id, username, encyptedPassword, fullName, Role, Articles
		// UserDetailsResponseBody: username, fullName, roleName

		return new UserDetailsResponseBody(
				user.getUsername(), user.getFullName(), user.getRole().getName());
	}

}
