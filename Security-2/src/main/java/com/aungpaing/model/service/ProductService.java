package com.aungpaing.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aungpaing.model.entity.Product;
import com.aungpaing.model.repo.ProductRepo;

@Service
public class ProductService {

	@Autowired
	private ProductRepo repo;

	public void save(Product product) {
		repo.save(product);
	}

	public List<Product> findAll() {
		return repo.findAll();
	}

	public Product findById(int id) {
		return repo.findById(id).orElse(new Product());
	}

	public void deleteById(int id) {
		repo.deleteById(id);
	}
}
