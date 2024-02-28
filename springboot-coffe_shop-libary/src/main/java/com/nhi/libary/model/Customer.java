package com.nhi.libary.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private int id;
	private String fullName;
	private String email;
	private String password;
	private String phoneNumber;
	private LocalDate birthDay;
	private String gender;
	private String address;
	@Column(columnDefinition = "text")
	private String image;
	private String accountNumber;
	private String bankName;
	
	@ManyToOne
	@JoinColumn(name = "city_id", referencedColumnName = "city_id")
	private City city;
	
	@OneToOne
	@JoinColumn(name = "shoppingCart_id", referencedColumnName = "shoppingCart_id")
	private ShoppingCart shoppingCart;

}
