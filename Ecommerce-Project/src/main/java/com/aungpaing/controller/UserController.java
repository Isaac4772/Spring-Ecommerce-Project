package com.aungpaing.controller;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.aungpaing.model.entity.User;
import com.aungpaing.model.entity.UserRole;
import com.aungpaing.model.service.UserService;

@Controller
public class UserController implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}

	@Autowired
	private UserService service;

	@PostMapping("/user/save")
	public RedirectView saveUser(@ModelAttribute("user") User user, BindingResult bindingResult,
			RedirectAttributes attributes) {
		if (user.getRole() == null) {
			user.setRole(UserRole.customer);
		}
		System.out.println("Error : " + bindingResult.hasErrors());
		if (bindingResult.hasErrors()) {
			return new RedirectView("/register");
		}

		try {
			service.save(user);
		} catch (SQLIntegrityConstraintViolationException e) {
			attributes.addFlashAttribute("email_error", "Email is already taken");
			attributes.addFlashAttribute("user", user);
			return new RedirectView("/register");
		} catch (DataIntegrityViolationException e) {
			attributes.addFlashAttribute("email_error", "Email is already taken");
			attributes.addFlashAttribute("user", user);
			return new RedirectView("/register");
		}
		return new RedirectView("/login");
	}

	@PostMapping("admin/user/save")
	public RedirectView saveAdminUser(@ModelAttribute("user") User user, RedirectAttributes attributes) {
		try {
			service.save(user);
		} catch (SQLIntegrityConstraintViolationException e) {
			attributes.addFlashAttribute("email_error", "Email is already taken");
			attributes.addFlashAttribute("user", user);
			return new RedirectView("/admin/user/add");
		} catch (DataIntegrityViolationException e) {
			attributes.addFlashAttribute("email_error", "Email is already taken");
			attributes.addFlashAttribute("user", user);
			return new RedirectView("/admin/user/add");
		}
		return new RedirectView("/admin/user/list");
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
	public String userAddPage(ModelMap map, @ModelAttribute("email_error") String email_error,
			@ModelAttribute("user") User user) {
		map.put("user", new User());
		map.put("show", "show");
		map.put("pagesActive", "active");
		map.put("userAddActive", "active");
		map.put("title", "User | Add");
		map.put("heading", "Add User");
		map.put("roles", UserRole.values());
		map.put("delete", "d-none");
		map.put("email_error", email_error);
		map.put("user", user);
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
