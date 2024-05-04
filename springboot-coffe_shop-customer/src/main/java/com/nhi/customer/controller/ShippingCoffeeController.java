package com.nhi.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhi.libary.model.CartItem;
import com.nhi.libary.model.Order;
import com.nhi.libary.repository.CustomerRepository;
import com.nhi.libary.service.OrderService;

@Controller
public class ShippingCoffeeController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private OrderService orderService;
	


	@GetMapping("/shipCoffee")
	public String shippingCoffee(Model model, Authentication authentication) {
		model.addAttribute("shoppingCart", customerRepository.findByEmail(authentication.getName()).getShoppingCart());
		model.addAttribute("customer", customerRepository.findByEmail(authentication.getName()));
		model.addAttribute("orderList", orderService.findByCustomer(authentication.getName()));
		model.addAttribute("cartItemList", orderService.findCartItemByOrder(authentication.getName()));
		
		return "shipCoffee";
	}
	
	@GetMapping("/shipped")
	public String shipped(Model model, Authentication authentication) {
		model.addAttribute("shoppingCart", customerRepository.findByEmail(authentication.getName()).getShoppingCart());
		model.addAttribute("customer", customerRepository.findByEmail(authentication.getName()));
		model.addAttribute("orderList", orderService.shipped(authentication.getName()));
		model.addAttribute("cartItemList", orderService.findCartItemByOrder(authentication.getName()));
		
		return "shipped";
	}
}
