package com.nhi.libary.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_item_id")
	private int id;
	private int quantity;
	private double costPrice;
	
	@ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "order_id")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "shoppingCart_id", referencedColumnName = "shoppingCart_id")
	private ShoppingCart shoppingCart;
	
	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "product_id")
	private Product product;
	
	private String size;
	
}
