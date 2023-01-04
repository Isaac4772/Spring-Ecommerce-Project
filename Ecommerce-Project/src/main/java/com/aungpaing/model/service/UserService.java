package com.aungpaing.model.service;

import java.util.List;

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
	private PasswordEncoder passwordEncoder;

	public long countUser() {
		return repo.count();
	}

	public User profile(String email) {
		return repo.findUserByEmail(email);
	}

	public void save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		repo.save(user);
	}

	public List<User> findAll() {
		return repo.findAll();
	}

	public User findById(int id) {
		return repo.findById(id).orElse(new User());
	}

	public void deleteById(int id) {
		repo.deleteById(id);
	}
}
