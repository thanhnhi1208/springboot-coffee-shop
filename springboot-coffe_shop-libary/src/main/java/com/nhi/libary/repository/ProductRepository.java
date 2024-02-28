package com.nhi.libary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nhi.libary.model.Product;
import com.nhi.libary.model.ShoppingCart;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	@Query(value = "SELECT * FROM products ORDER BY product_id", nativeQuery = true)
	List<Product> findAllProductSortId();
}
