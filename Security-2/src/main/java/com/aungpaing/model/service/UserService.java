package com.aungpaing.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aungpaing.model.entity.User;
import com.aungpaing.model.repo.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo repo;
	
	@Autowired
	private PasswordEncoder encoder;

	public long countUser() {
		return repo.count();
	}

	public void save(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		repo.save(user);
	}
}
