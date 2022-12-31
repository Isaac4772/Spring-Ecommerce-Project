package com.aungpaing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.aungpaing.model.entity.Category;
import com.aungpaing.model.service.CategoryService;

@Controller
public class AdminCategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/admin/category/list")
	public String categoryListPage(ModelMap map) {
		map.put("categories", categoryService.findAll());
		map.put("category", new Category());
		map.put("show", "show");
		map.put("pagesActive", "active");
		map.put("categoriesActive", "active");
		return "category-list";
	}

	@PostMapping("/admin/category/save")
	public String saveCategory(@ModelAttribute("category") Category category) {
		categoryService.save(category);
		return "redirect:/admin/category/list";
	}

	@GetMapping("/admin/category/delete/{id}")
	public String deleteProduct(@PathVariable("id") int id) {
		System.out.println("ID : " + id);
		categoryService.deleteById(id);
		return "redirect:/admin/category/list";
	}
}
