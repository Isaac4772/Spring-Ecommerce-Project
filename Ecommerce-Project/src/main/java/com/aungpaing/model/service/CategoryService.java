package com.aungpaing.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aungpaing.model.entity.Category;
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

	public void save(Category category) {
		repo.save(category);
	}

}
