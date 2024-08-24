package com.cespin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cespin.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByEmail(String email);

}
