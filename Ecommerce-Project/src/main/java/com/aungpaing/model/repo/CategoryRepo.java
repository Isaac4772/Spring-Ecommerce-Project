package com.aungpaing.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aungpaing.model.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
