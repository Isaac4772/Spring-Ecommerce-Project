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

	public List<Product> findAll() {
		return repo.findAll();
	}

	public Product save(Product prod) {
		return repo.save(prod);
	}

	public void deleteById(long id) {
		repo.deleteById(id);
	}

	public Product findById(long id) {
		return repo.findById(id).orElse(new Product());
	}
	
}
