package com.nhi.libary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.nhi.libary.model.CartItem;
import com.nhi.libary.model.Order;
import com.nhi.libary.model.Product;
import com.nhi.libary.model.ShoppingCart;

import jakarta.transaction.Transactional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{

	@Query(value = "SELECT * FROM cart_items WHERE shopping_cart_id = ?1 ORDER BY cart_item_id", nativeQuery = true)
	List<CartItem> findByShoppingCart(int id);
	
	@Query(value = "SELECT * FROM cart_items WHERE shopping_cart_id = ?3 AND size = ?1 AND product_id = ?2", nativeQuery = true)
	CartItem findBySizeAndProductAndShoppingCart(String size, int productId, int shoppindCartId);
	
	@Query(value = "SELECT * FROM cart_items WHERE order_id= ?1", nativeQuery = true)
	List<CartItem> findCartItemByOrder(int idOrder);
	
	@Query(value = "SELECT product_id FROM cart_items GROUP BY product_id ORDER BY SUM(quantity) DESC LIMIT 5", nativeQuery = true)
	List<Integer> findAllCartItemTopSeller();
	
	@Transactional
	@Modifying
	void deleteAllByProduct(Product product);
	
	@Query(value = "SELECT EXTRACT(MONTH FROM o.order_date), SUM(ci.cost_price * ci.quantity) FROM orders o INNER JOIN cart_items ci ON o.order_id = ci.order_id "
			+ "WHERE  EXTRACT(YEAR FROM o.order_date) = EXTRACT(YEAR FROM CURRENT_DATE) "
			+ "GROUP BY EXTRACT(MONTH FROM o.order_date) "
			+ "ORDER BY EXTRACT(MONTH FROM o.order_date)", nativeQuery = true)
	List<Object> findRevenue();
	
	List<CartItem> findByOrder(Order order);

}
