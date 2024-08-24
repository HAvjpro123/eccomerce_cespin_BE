package com.cespin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cespin.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
	
	

}
