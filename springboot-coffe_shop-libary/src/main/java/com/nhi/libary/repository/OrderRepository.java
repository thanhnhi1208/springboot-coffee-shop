package com.nhi.libary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhi.libary.model.Order;
import com.nhi.libary.model.ShoppingCart;

public interface OrderRepository extends JpaRepository<Order, Integer>{

}
