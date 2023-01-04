package com.aungpaing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.aungpaing.model.entity.User;
import com.aungpaing.model.entity.UserRole;
import com.aungpaing.model.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping("/user/save")
	public String saveUser(@ModelAttribute("user") User user) {
		if (user.getRole() == null) {
			user.setRole(UserRole.customer);
		}
		service.save(user);
		return "redirect:/login";
	}

	@PostMapping("admin/user/save")
	public String saveAdminUser(@ModelAttribute("user") User user) {
		service.save(user);
		return "redirect:/admin/user/list";
	}

	@GetMapping("/admin/user/list")
	public String userListPage(ModelMap map) {
		map.put("users", service.findAll());
		map.put("show", "show");
		map.put("pagesActive", "active");
		map.put("userListActive", "active");
		return "user-list";
	}

	@GetMapping("/admin/user/add")
	public String userAddPage(ModelMap map) {
		map.put("user", new User());
		map.put("show", "show");
		map.put("pagesActive", "active");
		map.put("userAddActive", "active");
		map.put("title", "User | Add");
		map.put("heading", "Add User");
		map.put("roles", UserRole.values());
		map.put("delete", "d-none");
		return "user-add";
	}

	@GetMapping("/admin/user/edit/{id}")
	public String userEditPage(@PathVariable("id") int id, ModelMap map) {
		map.put("user", service.findById(id));
		map.put("title", "User | Edit");
		map.put("heading", "Edit User");
		map.put("disable", "disabled");
		map.put("roles", UserRole.values());
		return "user-add";
	}

	@GetMapping("/admin/user/delete/{id}")
	public String deleteProduct(@PathVariable("id") int id) {
		service.deleteById(id);
		return "redirect:/admin/user/list";
	}
}
