package com.aungpaing.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aungpaing.model.entity.Category;
import com.aungpaing.model.entity.Product;
import com.aungpaing.model.repo.CategoryRepo;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepo repo;

	public List<Category> findAll() {
		return repo.findAll();
	}

	public Category findById(int id) {
		return repo.findById(id).orElse(new Category());
	}

	public long count() {
		return repo.count();
	}

	public Category save(Category cate) {
		return repo.save(cate);
	}

	public void deleteById(int id) {
		repo.deleteById(id);
	}

}
