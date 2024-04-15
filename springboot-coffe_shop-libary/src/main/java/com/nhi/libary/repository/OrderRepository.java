package com.nhi.libary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhi.libary.model.Customer;
import com.nhi.libary.model.Order;
import com.nhi.libary.model.ShoppingCart;

public interface OrderRepository extends JpaRepository<Order, Integer>{

	List<Order> findByCustomer(Customer customer);
}
