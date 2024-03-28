package com.nhi.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhi.libary.repository.CustomerRepository;
import com.nhi.libary.service.ProductService;

@Controller
public class DetailCoffeeController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping("/detailCoffee")
	public String detailCoffee(int id, Model model, Authentication authentication) {
		model.addAttribute("product", productService.findProductById(id));
		model.addAttribute("shoppingCart", customerRepository.findByEmail(authentication.getName()).getShoppingCart());
		model.addAttribute("customer", customerRepository.findByEmail(authentication.getName()));
		return "detailCoffee";
	}
	
	
}
