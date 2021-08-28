package com.nqminh.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nqminh.api.exception.MyBadRequestException;
import com.nqminh.api.requestbody.UserLoginRequestBody;
import com.nqminh.api.security.JwtTokenUtil;
import com.nqminh.api.security.JwtUserDetailsService;
import com.nqminh.api.security.SecurityConstants;

@RestController
@CrossOrigin
public class LogInController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@PostMapping("/authenticate")
	public ResponseEntity<?> getAuthenticationToken(@RequestBody UserLoginRequestBody userCredentials)
			throws Exception {

		authenticate(userCredentials.getUsername(), userCredentials.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(userCredentials.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		//		return ResponseEntity.ok(new UserLoginResponseBody(token));
		return ResponseEntity
				.ok()
				.header(SecurityConstants.AUTH_HEADER, SecurityConstants.TOKEN_PREFIX + token)
				.build();
	}

	// -------------------------------------------------------------------------

	private void authenticate(String username, String password) throws Exception {

		try {

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		} catch (DisabledException e) {

			throw new Exception("USER_DISABLED", e);

		} catch (LockedException e) {

			throw new Exception("USER_DISABLED", e);

		} catch (BadCredentialsException e) {

			throw new MyBadRequestException("INVALID_CREDENTIALS");
		}
	}
}
