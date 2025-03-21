package com.hexaware.assetmanagement.service;

import java.util.List;
import java.util.Optional;

import com.hexaware.assetmanagement.model.User;

public interface IUserService {
    User registerUser(User user);
    Optional<User> findUserById(Long userId);
    Optional<User> findUserByEmail(String email);
	List<User> getAllUsers();
	User findUserById1(Long userId);
	

}
