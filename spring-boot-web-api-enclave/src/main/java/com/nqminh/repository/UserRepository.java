package com.nqminh.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nqminh.entity.Role;
import com.nqminh.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	//	User findByUsername(String username);

	Optional<User> findByUsername(String username);

	List<User> findAllByRole(Role role);

}
