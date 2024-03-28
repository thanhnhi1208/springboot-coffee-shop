package com.nhi.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhi.libary.model.Customer;
import com.nhi.libary.service.CustomerService;

@Controller
public class IndexController {
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/index")
	public String index() {
		return "homeCoffee";
	}
	
	@GetMapping("/findCurrentCustomer")
	@ResponseBody
	public Customer findCurrentCustomer(Authentication authentication) {
		return customerService.findByEmail(authentication.getName());
	}
}
