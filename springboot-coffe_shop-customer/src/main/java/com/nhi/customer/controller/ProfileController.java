package com.nhi.customer.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.nhi.libary.model.Customer;
import com.nhi.libary.repository.CityRepository;
import com.nhi.libary.repository.CustomerRepository;
import com.nhi.libary.service.CustomerService;

@Controller
public class ProfileController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired 
	private CityRepository cityRepository;
	
	@Autowired 
	private CustomerService customerService;
	

	@GetMapping("/profile")
	public String profile(Model model, Authentication authentication) {
		model.addAttribute("shoppingCart", customerRepository.findByEmail(authentication.getName()).getShoppingCart());
		model.addAttribute("cityList", cityRepository.findAll());
		model.addAttribute("customer", customerRepository.findByEmail(authentication.getName()));
		return "profile";
	}
	
	@PostMapping("/profile/add")
	public String addProfile(Customer customer, MultipartFile imageOfCustomer) throws IOException {
		this.customerService.addProfile(customer, imageOfCustomer);
		return "redirect:/profile";
	}
	
	
}
