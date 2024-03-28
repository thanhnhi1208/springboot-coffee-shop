package com.nhi.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhi.libary.model.CartItem;
import com.nhi.libary.model.ShoppingCart;
import com.nhi.libary.repository.CustomerRepository;
import com.nhi.libary.service.CustomerService;
import com.nhi.libary.service.ShoppingCartService;

@Controller
public class ShoppingCartController {
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping("/shoppingCart")
	public String shoppingCart(Authentication authentication, Model model) {
		List<CartItem> cartItemList = shoppingCartService.findAllCartItemInShoppingCart(authentication.getName());
		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("shoppingCart", customerRepository.findByEmail(authentication.getName()).getShoppingCart());
		model.addAttribute("customer", customerRepository.findByEmail(authentication.getName()));
		
		int mechandiseqQuantity = 0;
		for (CartItem cartItem : cartItemList) {
			mechandiseqQuantity += cartItem.getQuantity();
		}
		
		model.addAttribute("mechandiseqQuantity", mechandiseqQuantity);
		return "cart-product";
	}
	
	@PostMapping("/addShoppingCart")
	public String addShoppingCart(CartItem cartItem, Authentication authentication, double sale_price) {
		this.shoppingCartService.addShoppingCart(cartItem, authentication.getName(), sale_price);
		return "redirect:/shoppingCart";
	}
	
	@GetMapping("/shoppingCart/increaseQuantity")
	@ResponseBody
	public CartItem increaseQuantity(int id) {
		return this.shoppingCartService.increaseQuantity(id);
	}
	
	@GetMapping("/shoppingCart/decreaseQuantity")
	@ResponseBody
	public CartItem decreaseQuantity(int id) {
		return this.shoppingCartService.decreaseQuantity(id);
	}
	
	@GetMapping("/shoppingCart/deleteCartItemById")
	@ResponseBody
	public ShoppingCart deleteCartItemById(int id, Authentication authentication) {
		this.shoppingCartService.deleteCartItemById(id);
		return this.customerRepository.findByEmail(authentication.getName()).getShoppingCart();
	}
}