package com.cespin.service;

import com.cespin.exception.UserException;
import com.cespin.model.User;

public interface UserService {

	public User findUserById(Long userId) throws UserException; 
	
	public User findUserProfileByJwt(String jwt) throws UserException;
	
}
