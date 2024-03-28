package com.nhi.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhi.libary.model.Customer;
import com.nhi.libary.service.CustomerService;

@Controller
public class LoginController {
	@Autowired
	private CustomerService customerService;
	
	
	@GetMapping("/login")
	public String login(Authentication authentication) {
		if(authentication != null) {
			return "redirect:/index";
		}
		return "login";
	}
	

}
