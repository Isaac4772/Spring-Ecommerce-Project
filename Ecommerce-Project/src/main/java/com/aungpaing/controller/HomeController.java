package com.aungpaing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.aungpaing.model.service.CategoryService;
import com.aungpaing.model.service.ProductService;

@Controller
public class HomeController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/")
	public String home() {
		return "redirect:/shop";
	}

	@GetMapping("/shop")
	public String goShop(ModelMap map) {

		map.put("productList", productService.findAll());
		map.put("categoryList", categoryService.findAll());
		return "index";
	}

	@GetMapping("/shop/products/{id}")
	public String singleProduct(@PathVariable("id") long productId, ModelMap map) {

		map.put("product", productService.findById(productId));
		return "product-detail";
	}
}
