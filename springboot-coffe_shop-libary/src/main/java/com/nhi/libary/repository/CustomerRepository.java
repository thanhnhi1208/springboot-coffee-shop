package com.nhi.libary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nhi.libary.model.Customer;
import com.nhi.libary.model.ShoppingCart;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	Customer findByEmail(String email);
	
	Customer findByPhoneNumber(String phoneNumber);
	
	@Query(value =  "SELECT * FROM customers WHERE account_number = ?1 AND account_number != ''", nativeQuery = true)
	Customer findByAccountNumber(String accountNumber);
}
