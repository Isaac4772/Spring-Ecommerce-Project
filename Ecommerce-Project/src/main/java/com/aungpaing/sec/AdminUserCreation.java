package com.aungpaing.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aungpaing.model.entity.User;
import com.aungpaing.model.entity.UserRole;
import com.aungpaing.model.service.UserService;

@Configuration
public class AdminUserCreation {

	@Autowired
	private UserService service;

	@Bean
	public CommandLineRunner runner() {
		return args -> {
			long count = service.countUser();
			if (count == 0) {
				User user = new User();
				user.setEmail("admin@gmail.com");
				user.setPassword("admin");
				user.setRole(UserRole.admin);
				user.setAddress("Yangon");
				user.setPhone("0912345678");
				user.setName("admin");
				System.out.println(user.getEmail() + ", " + user.getPassword());
				service.save(user);
			}
		};
	}
}
