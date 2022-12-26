package com.aungpaing.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.aungpaing.model.entity.User;
import com.aungpaing.model.repo.UserRepo;

public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repo.findUserByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(email);
		}
		MyUserDetail userDetail = new MyUserDetail(user);
		return userDetail;
	}

}
