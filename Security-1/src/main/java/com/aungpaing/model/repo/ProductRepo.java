package com.aungpaing.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aungpaing.model.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {

}
