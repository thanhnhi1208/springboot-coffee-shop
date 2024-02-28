package com.nhi.libary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhi.libary.model.Customer;
import com.nhi.libary.model.ShoppingCart;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	Customer findByEmail(String email);
}
