package com.nhi.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhi.libary.repository.CustomerRepository;
import com.nhi.libary.service.CategoryService;
import com.nhi.libary.service.CustomerService;
import com.nhi.libary.service.ProductService;

@Controller
public class MenuController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping("/menu")
	public String menu(Model model, Authentication authentication) {
		if(authentication != null) {
			model.addAttribute("shoppingCart", customerRepository.findByEmail(authentication.getName()).getShoppingCart());
			model.addAttribute("customer", customerRepository.findByEmail(authentication.getName()));
		}
		
		model.addAttribute("productList", productService.findAllProduct()) ;
		model.addAttribute("categoryList", categoryService.findAllCategory()) ;
		return "menuCoffee";
	}

}

