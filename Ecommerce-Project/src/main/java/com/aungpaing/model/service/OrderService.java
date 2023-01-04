package com.aungpaing.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aungpaing.model.entity.Order;
import com.aungpaing.model.repo.OrderRepo;

@Service
public class OrderService {
	@Autowired
	private OrderRepo repo;

	public Order save(Order newOrder) {
		return repo.save(newOrder);
	}

	public List<Order> getOrdersByEmail(String email) {
		return repo.findOrdersByEmail(email);
	}

	public Order findById(long id) {
		return repo.findById(id).orElse(new Order());
	}

	public List<Order> findAll() {
		return repo.findAll();
	}
}
