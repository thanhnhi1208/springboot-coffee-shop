package com.nhi.libary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhi.libary.model.CartItem;
import com.nhi.libary.model.ShoppingCart;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{

}
