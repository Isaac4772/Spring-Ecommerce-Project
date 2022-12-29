package com.aungpaing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminHomeController {

	@GetMapping("/admin")
	public String homePage(ModelMap map) {
		map.put("dashboardActive", "active");
		return "admin-dashboard";
	}

//	@GetMapping("/login")
//	public String loginPage() {
//
//		return "login";
//	}

	@GetMapping("/login/forgot-password")
	public String forgotPasswordPage() {

		return "forgot-password";
	}

//	@GetMapping("register")
//	public String registerPage() {
//
//		return "register";
//	}
	

}
