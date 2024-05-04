package com.nhi.libary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nhi.libary.model.CartItem;
import com.nhi.libary.model.Product;
import com.nhi.libary.model.ShoppingCart;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	@Query(value = "SELECT * FROM products ORDER BY product_id", nativeQuery = true)
	List<Product> findAllProductSortId();
	
	@Query(value = "SELECT * FROM products WHERE price = (SELECT MAX(price) FROM products)", nativeQuery = true)
	List<Product> findAllHighestPrice();
	
	@Query(value = "SELECT * FROM products WHERE price = (SELECT MIN(price) FROM products)", nativeQuery = true)
	List<Product> findAllLowestPrice();
	
	Product findByName(String name);
	
	@Query(value = "SELECT * FROM products WHERE LOWER(name) LIKE LOWER(CONCAT('%', ?1, '%'))", nativeQuery = true)
	List<Product> findAllProductName(String productName);
}
