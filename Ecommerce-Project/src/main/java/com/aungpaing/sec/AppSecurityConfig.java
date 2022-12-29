package com.aungpaing.sec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.aungpaing.model.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public MyUserDetailsService myUserDetailsService() {
		return new MyUserDetailsService();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(myUserDetailsService());
		return provider;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests()
				.requestMatchers("/", "/shop", "/login", "/register", "/shop/products/**", "/cart/details", "/css/**",
						"/app/**", "/fonts/**", "/images/**", "/js/**", "/scss/**", "/uploads/**")
				.permitAll().requestMatchers("/admin/**").hasRole("admin")
				.requestMatchers("/product/add").hasAnyRole("admin").anyRequest().authenticated().and()
				.formLogin().loginPage("/login").permitAll().and().logout().logoutSuccessUrl("/").permitAll().and()
				.exceptionHandling().accessDeniedPage("/403");
		return http.build();
	}
}
