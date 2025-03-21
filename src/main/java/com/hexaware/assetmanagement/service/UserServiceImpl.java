package com.hexaware.assetmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.assetmanagement.model.User;
import com.hexaware.assetmanagement.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public User registerUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public Optional<User> findUserById(Long userId) {
		return userRepository.findById(userId);
	}

	@Override
	public User findUserById1(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}

	@Override
	public Optional<User> findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

}
