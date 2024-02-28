package com.nhi.libary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhi.libary.model.Admin;
import com.nhi.libary.model.ShoppingCart;

public interface AdminRepository extends JpaRepository<Admin, Integer>{

	Admin findByEmail(String email);
}
