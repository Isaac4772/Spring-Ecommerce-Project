package com.aungpaing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.aungpaing.model.entity.Category;
import com.aungpaing.model.entity.Product;
import com.aungpaing.model.service.CategoryService;

@Controller
public class AdminCategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/admin/category/list")
	public String categoryListPage(ModelMap map) {
		map.put("categories", categoryService.findAll());
		map.put("show", "show");
		map.put("pagesActive", "active");
		map.put("categoriesActive", "active");
		return "category-list";
	}
	
	@GetMapping("/admin/category/edit/{id}")
	public String editPage(@PathVariable("id") int id, ModelMap map) {
		map.put("category", categoryService.findById(id));
		map.put("title", "Category | Edit");
		map.put("heading", "Edit Category");
		return "product-add";
	}
}
