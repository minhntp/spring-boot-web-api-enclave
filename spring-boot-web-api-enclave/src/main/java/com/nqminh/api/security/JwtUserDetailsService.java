package com.nqminh.api.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nqminh.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		try {

			com.nqminh.entity.User user = userRepository.findByUsername(username).get();

			return new User(user.getUsername(), user.getEncyptedPassword(), new ArrayList<>());

		} catch (Exception e) { // NoSuchElementException if user is not found
			throw new UsernameNotFoundException("User with username: " + username + " not found.");
		}
	}
}
