package com.nhi.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhi.libary.repository.CustomerRepository;

@Controller
public class ShippingCoffeeController {
	
	@Autowired
	private CustomerRepository customerRepository;


	@GetMapping("/shipCoffee")
	public String shippingCoffee(Model model, Authentication authentication) {
		model.addAttribute("shoppingCart", customerRepository.findByEmail(authentication.getName()).getShoppingCart());
		model.addAttribute("customer", customerRepository.findByEmail(authentication.getName()));
		return "shipCoffee";
	}
}
