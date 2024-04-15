package com.nhi.libary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhi.libary.model.Customer;
import com.nhi.libary.model.Favorite;
import com.nhi.libary.model.Product;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer >  {

	Favorite findByProductAndCustomer(Product product, Customer customer);
	
	List<Favorite> findByCustomer(Customer customer);
	
}
