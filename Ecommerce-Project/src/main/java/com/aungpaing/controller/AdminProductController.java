package com.aungpaing.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
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
import com.aungpaing.model.service.ProductService;

@Controller
public class AdminProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService cateService;

	@GetMapping("/admin/product/list")
	public String productListPage(ModelMap map) {
		map.put("products", productService.findAll());
		map.put("show", "show");
		map.put("pagesActive", "active");
		map.put("productsActive", "active");
		return "product-list";
	}

	@GetMapping("/admin/product/add")
	public String productAddPage(ModelMap map) {
		map.put("product", new Product());
		map.put("show", "show");
		map.put("pagesActive", "active");
		map.put("productAddActive", "active");
		map.put("title", "Product | Add");
		map.put("heading", "Add Product");
		map.put("categories", cateService.findAll());
		map.put("hideSearchBar", "d-none");
		map.put("delete", "d-none");
		return "product-add";
	}

	@PostMapping("/admin/product/save")
	public String saveProduct(@ModelAttribute("products") Product product,
			@RequestParam("photoName") MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if ((product.getId() == 0 || product.getId() != 0) && !fileName.equals("")) {
			product.setPhoto(fileName);
		}
		if (fileName.equals("")) {
			Product product2 = productService.findById(product.getId());
			if (product2.getId() != 0) {
				product.setPhoto(product2.getPhoto());
				addNewCategory(cateService, product);
				productService.save(product);
				return "redirect:/admin/product/list";
			}
		}
		addNewCategory(cateService, product);
		var saveProduct = productService.save(product);
		if (!"".equals(fileName)) {
			String uploadDir = "uploads/" + saveProduct.getId();
			FileUploadUtil.savePhoto(uploadDir, fileName, file);
		}
		return "redirect:/admin/product/list";
	}

	public static void addNewCategory(CategoryService cateService, Product product) {
		long count = cateService.count();
		if (count < product.getCategory().getId()) {
			Category category = new Category();
			category.setName(product.getCategory().getName());
			cateService.save(category);
		}
	}

	@GetMapping("/admin/product/edit/{id}")
	public String editPage(@PathVariable("id") int id, ModelMap map) {
		map.put("product", productService.findById(id));
		map.put("categories", cateService.findAll());
		map.put("title", "Product | Edit");
		map.put("heading", "Edit Product");
		map.put("hideSearchBar", "d-none");
		return "product-add";
	}

	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable("id") int id) {
		productService.deleteById(id);
		return "redirect:/admin/product/list";
	}
}
