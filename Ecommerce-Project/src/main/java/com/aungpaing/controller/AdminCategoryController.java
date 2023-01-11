package com.aungpaing.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.aungpaing.FileUploadUtil;
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
		map.put("category", new Category());
		map.put("show", "show");
		map.put("pagesActive", "active");
		map.put("categoriesActive", "active");
		return "category-list";
	}

	@GetMapping("/admin/category/add")
	public String categoryAddPage(ModelMap map) {
		map.put("category", new Category());
		map.put("show", "show");
		map.put("title", "Category | Add");
		map.put("heading", "Add Category");
		map.put("pagesActive", "active");
		map.put("categoryAddActive", "active");
		map.put("delete", "d-none");
		return "category-add";
	}

	@PostMapping("/admin/category/save")
	public String saveProduct(@ModelAttribute("category") Category category,
			@RequestParam("categoryName") MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if ((category.getId() == 0 || category.getId() != 0) && !fileName.equals("")) {
			category.setPhoto(fileName);
		}
		if (fileName.equals("")) {
			Category category2 = categoryService.findById(category.getId());
			if (category2.getId() != 0) {
				category.setPhoto(category2.getPhoto());
				categoryService.save(category);
				return "redirect:/admin/product/list";
			}
		}
		var saveCategory = categoryService.save(category);
		if (!"".equals(fileName)) {
			String uploadDir = "uploads_category/" + saveCategory.getId();
			FileUploadUtil.savePhoto(uploadDir, fileName, file);
		}
		return "redirect:/admin/category/list";
	}

	@GetMapping("/admin/category/edit/{id}")
	public String editPage(@PathVariable("id") int id, ModelMap map) {
		map.put("category", categoryService.findById(id));
		map.put("title", "Category | Edit");
		map.put("heading", "Edit Category");
		System.out.println(categoryService.findById(id).getPhotoUrl());
		return "category-add";
	}

	@GetMapping("/admin/category/delete/{id}")
	public String deleteProduct(@PathVariable("id") int id) throws IOException {
		categoryService.deleteById(id);
		File dir = new File("uploads_category/" + id); // path to the directory
		deleteDir(dir);
		return "redirect:/admin/category/list";
	}

	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}
}
