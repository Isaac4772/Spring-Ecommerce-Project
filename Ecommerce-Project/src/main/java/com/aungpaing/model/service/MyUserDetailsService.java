package com.aungpaing.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.aungpaing.model.entity.User;
import com.aungpaing.model.repo.UserRepo;
import com.aungpaing.sec.MyUserDetails;

public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repo.findUserByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("Could not find user");
		}

		MyUserDetails myUserDetails = new MyUserDetails(user);
		System.out.println(myUserDetails.getAuthorities());
		return myUserDetails;
	}

}
