package com.nhi.libary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nhi.libary.model.Customer;
import com.nhi.libary.model.Order;
import com.nhi.libary.model.ShoppingCart;

public interface OrderRepository extends JpaRepository<Order, Integer>{

	@Query(value =  "SELECT * FROM orders WHERE customer_id = ?1 ORDER BY order_id DESC", nativeQuery = true)
	List<Order> findByCustomer(int id);
	
	@Query(value = "SELECT * FROM orders ORDER BY order_id DESC", nativeQuery = true)
	List<Order> findAllOrderByDate();
}
