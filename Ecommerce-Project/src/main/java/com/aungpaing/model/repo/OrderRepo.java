package com.aungpaing.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aungpaing.model.entity.Orders;

public interface OrderRepo extends JpaRepository<Orders, Long> {

}
