package com.nhi.libary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nhi.libary.model.CartItem;
import com.nhi.libary.model.ShoppingCart;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{

	@Query(value = "SELECT * FROM cart_items WHERE shopping_cart_id = ?1 ORDER BY cart_item_id", nativeQuery = true)
	List<CartItem> findByShoppingCart(int id);
	
	@Query(value = "SELECT * FROM cart_items WHERE shopping_cart_id = ?3 AND size = ?1 AND product_id = ?2", nativeQuery = true)
	CartItem findBySizeAndProductAndShoppingCart(String size, int productId, int shoppindCartId);
	
	@Query(value = "SELECT * FROM cart_items WHERE order_id= ?1", nativeQuery = true)
	List<CartItem> findCartItemByOrder(int idOrder);
	
	@Query(value = "SELECT product_id FROM cart_items GROUP BY product_id ORDER BY SUM(quantity) DESC LIMIT 5", nativeQuery = true)
	List<Integer> findAllCartItemTopSeller();

}
