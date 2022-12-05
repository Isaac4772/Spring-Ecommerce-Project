package com.aungpaing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

	@GetMapping("/cart/details")
	public String home() {

		return "cart";
	}

	@GetMapping("/cart/checkout")
	public String checkoutPage(ModelMap map) {
		map.put("name", "Aung Aung");
		map.put("phone", "0912345567");
		map.put("email", "aungaung@gmail.com");
		map.put("address", "Yangon");
		return "checkout";
	}

}
