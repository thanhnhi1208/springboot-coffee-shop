package com.nhi.customer.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.nhi.libary.model.Customer;
import com.nhi.libary.repository.CustomerRepository;
import com.nhi.libary.service.OrderService;
import com.nhi.libary.service.ProductService;

@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping("/order")
	public String order() {
		return "login";
	}
	
	public String removeDecimalIfNeeded(double number) {
		
	    long longValue = (long) number;
	    if (number == longValue) {
	        return String.valueOf(longValue);
	    } else {
	        return String.valueOf(number);
	    }
	}
	
	@PostMapping("/order/add")
	public String orderAdd(String paymentMethod, String fullName, String phoneNumber, String address, Authentication authentication) {
		if(paymentMethod.equals(("Chuyển Khoản"))){
			String encodedAddreess = URLEncoder.encode(address, StandardCharsets.UTF_8);
			String encodedFullName = URLEncoder.encode(fullName, StandardCharsets.UTF_8);
			String encodedPhoneNumber = URLEncoder.encode(phoneNumber, StandardCharsets.UTF_8);
			String price = removeDecimalIfNeeded(this.customerRepository.findByEmail(authentication.getName()).getShoppingCart().getTotalPrice() + 20000);
			
			if(price.equals("20000")) {
				return "redirect:/shoppingCart";
			}
			
			return "redirect:/momo?address=" +encodedAddreess +"&fullName=+" +encodedFullName +"&phoneNumber="+encodedPhoneNumber+"&price="+price;
		}
		
		 if(this.orderService.addOrder(authentication.getName(), paymentMethod, fullName, phoneNumber, address) == null) {
			 return "redirect:/shoppingCart";
		 }
		 
		 return "redirect:/shipCoffee";
	}
	
	@GetMapping("/order/addFromMoMo")
	public String orderAddFromMoMo(String paymentMethod, String fullName, String phoneNumber, String address, Authentication authentication) {
		 paymentMethod  = "Chuyển Khoản";
		 if(this.orderService.addOrder(authentication.getName(), paymentMethod, fullName, phoneNumber, address) == null) {
			 return "redirect:/shoppingCart";
		 }
		 
		 return "redirect:/shipCoffee";
	}
	
	
}
