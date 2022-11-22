package com.aungpaing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {
	
	@GetMapping("/product/details")
	public String detailPage() {
		
		return "product-detail";
	}
}
