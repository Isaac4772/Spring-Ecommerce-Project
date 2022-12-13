package com.aungpaing.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aungpaing.model.entity.Orders;
import com.aungpaing.model.repo.OrderRepo;

@Service
public class OrderService {
	@Autowired
	private OrderRepo repo;

	public Orders save(Orders newOrder) {
		return repo.save(newOrder);
	}
}
