package com.nhi.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhi.libary.model.Favorite;
import com.nhi.libary.repository.CustomerRepository;
import com.nhi.libary.service.FavoriteService;

@Controller
public class FavoriteController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	@Autowired
	private FavoriteService favoriteService;
	
	@GetMapping("/favorite")
	public String favorite(Model model, Authentication authentication) {
		model.addAttribute("shoppingCart", customerRepository.findByEmail(authentication.getName()).getShoppingCart());
		model.addAttribute("customer", customerRepository.findByEmail(authentication.getName()));
		model.addAttribute("favoriteList", this.favoriteService.findByCustomer(authentication.getName()));
		return "favoriteCoffee";
	}
	
	@GetMapping("/favorite/add")
	@ResponseBody
	public void favoriteAdd(Model model, Authentication authentication, String tenSanPham) {
		this.favoriteService.favoriteAdd(tenSanPham, authentication.getName());
	}
	
	@GetMapping("/favorite/delete")
	@ResponseBody
	public void favoriteDelete(Model model, Authentication authentication, String tenSanPham) {
		this.favoriteService.favoriteDelete(tenSanPham, authentication.getName());
	}
	
	@GetMapping("/favorite/findByCustomeAndProduct")
	@ResponseBody
	public String findByCustomeAndProduct(Authentication authentication, String tenSanPham) {
		return this.favoriteService.findByCustomerAndProduct(authentication.getName(), tenSanPham);
	}
}
