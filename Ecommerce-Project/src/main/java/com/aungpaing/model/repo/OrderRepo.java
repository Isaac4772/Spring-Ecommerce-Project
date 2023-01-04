package com.aungpaing.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aungpaing.model.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

	@Query("SELECT o FROM Order o WHERE o.customer.email=:email")
	List<Order> findOrdersByEmail(@Param("email") String email);

}
