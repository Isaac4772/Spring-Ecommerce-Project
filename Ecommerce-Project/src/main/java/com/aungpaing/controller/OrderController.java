package com.aungpaing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.aungpaing.model.entity.Order;
import com.aungpaing.model.entity.OrderStatus;
import com.aungpaing.model.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/shop/orders")
	public String myOrdersPage(ModelMap map) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		List<Order> orders = orderService.getOrdersByEmail(email);
		map.put("orders", orders);
		return "my-orders";
	}

	@GetMapping("/my-orders/details/{id}")
	public String myOrderDetails(ModelMap map, @PathVariable("id") long id) {
		map.addAttribute("order", orderService.findById(id));
		return "my-order-details";
	}

	@GetMapping("/admin/order/list")
	public String adminOrderListPage(ModelMap map) {
		map.put("orders", orderService.findAll());
		map.put("show", "show");
		map.put("pagesActive", "active");
		map.put("ordersActive", "active");
		return "order-list";
	}

	@PostMapping("/admin/order/save")
	public String changeStatus(@ModelAttribute("order") Order order) {
		System.out.println(order.getId());
		orderService.save(order);
		return "redirect:/admin/order/details/" + order.getId();
	}

	@GetMapping("/admin/order/details/{id}")
	public String adminOrderPage(@PathVariable("id") long id, ModelMap map) {
		map.put("order", orderService.findById(id));
		map.put("statusList", OrderStatus.values());
		return "admin-order-details";
	}
}
