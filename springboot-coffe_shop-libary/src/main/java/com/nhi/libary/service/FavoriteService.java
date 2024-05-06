package com.nhi.libary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhi.libary.model.Customer;
import com.nhi.libary.model.Favorite;
import com.nhi.libary.model.Product;
import com.nhi.libary.repository.CustomerRepository;
import com.nhi.libary.repository.FavoriteRepository;
import com.nhi.libary.repository.ProductRepository;

@Service
public class FavoriteService {

	@Autowired
	private FavoriteRepository favoriteRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CustomerRepository customerRepository;

	public void favoriteAdd(String tenSanPham, String email) {
		Product product = this.productRepository.findByName(tenSanPham);
		Customer customer = this.customerRepository.findByEmail(email);
		
		Favorite favorite = new Favorite();
		favorite.setCustomer(customer);
		favorite.setProduct(product);
		this.favoriteRepository.save(favorite);
	}
	
	public void favoriteDelete(String tenSanPham, String email) {
		Product product = this.productRepository.findByName(tenSanPham);
		Customer customer = this.customerRepository.findByEmail(email);
		
		Favorite favorite = this.favoriteRepository.findByProductAndCustomer(product, customer);
		if(favorite!= null) {
			this.favoriteRepository.deleteById(favorite.getId());
		}
		
	}

	public String findByCustomerAndProduct(String name, String tenSanPham) {
		Product product = this.productRepository.findByName(tenSanPham);
		Customer customer = this.customerRepository.findByEmail(name);
		Favorite favorite = this.favoriteRepository.findByProductAndCustomer(product, customer);
		if(favorite != null) {
			return "true";
		}
		
		return "false";
	}
	
	public List<Favorite> findByCustomer(String email){
		Customer customer = this.customerRepository.findByEmail(email);
		return this.favoriteRepository.findByCustomer(customer);
	}
}
