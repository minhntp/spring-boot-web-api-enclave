package com.nqminh.api.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.nqminh.entity.User;
import com.nqminh.repository.UserRepository;

@Component
public class AuthorizationUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> foundUser = userRepository.findByUsername(username);

		if (foundUser.isEmpty()) {
			throw new UsernameNotFoundException("User with username: " + username + " not found.");
		}

		return new AuthorizationUserDetails(foundUser.get());
	}

}
