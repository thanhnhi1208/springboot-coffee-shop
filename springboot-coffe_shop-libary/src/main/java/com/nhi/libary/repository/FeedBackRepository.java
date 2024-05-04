package com.nhi.libary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nhi.libary.model.FeedBack;
import com.nhi.libary.model.ShoppingCart;

import lombok.val;

public interface FeedBackRepository extends JpaRepository<FeedBack, Integer>{

	@Query(value = "SELECT * FROM feedbacks WHERE customer_id = ?1 AND  product_id = ?2 ORDER BY feedback_id DESC", nativeQuery = true)
	List<FeedBack> findByCustomerAndProduct(int idCustomer, int product_id);
	
	@Query(value = "SELECT * FROM feedbacks WHERE product_id = ?1 AND customer_id != ?2 ORDER BY feedback_id DESC" , nativeQuery = true)
	List<FeedBack> findAllSortByProduct(int productId, int idCustomer);
}
