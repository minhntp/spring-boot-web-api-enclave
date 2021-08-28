package com.nqminh.api.controller;

import java.net.URI;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nqminh.api.requestbody.UserDetailsRequestBody;
import com.nqminh.api.responsebody.UserDetailsResponseBody;
import com.nqminh.api.service.UserService;
import com.nqminh.dto.UserDto;
import com.nqminh.util.RestUtils;

@RestController
@RequestMapping("users")
public class UserController {

	@Resource
	private UserService userService;

	@Autowired
	private RestUtils restUtils;

	// -------------------------------------------------------------------------

	// Get all users
	@GetMapping
	public ResponseEntity<Object> getAll() {

		List<UserDetailsResponseBody> responseBody = userService.getAllUsers();

		return ResponseEntity
				.ok()
				.headers(restUtils.getHeaders(200, restUtils.SUCCESS))
				.body(responseBody);

	}

	// Get users by role id
	@GetMapping("/role/{roleId}")
	public ResponseEntity<Object> getByRole(@PathVariable int roleId) {

		List<UserDetailsResponseBody> responseBody = userService.getUsersByRoleId(roleId);

		return ResponseEntity
				.ok()
				.headers(restUtils.getHeaders(200, restUtils.SUCCESS))
				.body(responseBody);
	}

	// Find user by id or username
	@GetMapping("/{input}")
	public ResponseEntity<Object> findByIdOrUsername(@PathVariable String input) {
		//		System.out.println(getClass() + ": input = |" + input + "|");

		UserDetailsResponseBody responseBody;

		try {
			// find by id if input can be parsed to Integer
			int id = Integer.parseInt(input);

			responseBody = userService.getUserById(id);

		} catch (NumberFormatException e) {

			responseBody = userService.getUserByUsername(input);

		}

		return ResponseEntity
				.ok()
				.headers(restUtils.getHeaders(200, restUtils.SUCCESS))
				.body(responseBody);
	}

	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody UserDetailsRequestBody userRequestBody) {

		UserDto userDto = userService.createUser(userRequestBody);

		// uri to access saved user
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(userDto.getId())
				.toUri();

		// response body
		UserDetailsResponseBody responseBody = toUserDetailsResponse(userDto);

		return ResponseEntity
				.created(location)
				.headers(restUtils.getHeaders(201, restUtils.CREATED))
				.body(responseBody);
	}

	// Update user: username, password, fullName, roleId
	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable int id,
			@Valid @RequestBody UserDetailsRequestBody userRequestBody) {

		UserDetailsResponseBody responseBody = userService.updateUser(userRequestBody, id);

		return ResponseEntity
				.ok()
				.headers(restUtils.getHeaders(200, restUtils.SUCCESS))
				.body(responseBody);
	}

	// Delete user by id.
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable int id) {

		userService.deleteUser(id);

		return ResponseEntity
				.ok()
				.headers(restUtils.getHeaders(200, restUtils.SUCCESS))
				.build();
	}

	// -------------------------------------------------------------------------

	private UserDetailsResponseBody toUserDetailsResponse(UserDto userDto) {

		// input: UderDto: id, username, fullName, roleId, roleName, encryptedPassword
		// output: UserDetailsResponseBody: username|, fullName|, roleName|

		UserDetailsResponseBody response = new UserDetailsResponseBody();

		BeanUtils.copyProperties(userDto, response);

		return response;
	}

}
