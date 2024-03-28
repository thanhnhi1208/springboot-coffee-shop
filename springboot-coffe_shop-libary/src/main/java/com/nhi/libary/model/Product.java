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
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private int id;
	private String name;
	private double price;
	
	@Column(columnDefinition = "text")
	private String discription;
	
	@Column(columnDefinition = "text")
	private String image;
	private double sale_price;
	
	private boolean expired;
	
	@ManyToOne
	@JoinColumn(name = "caterogy_id", referencedColumnName = "category_id")
	private Category category;
}
