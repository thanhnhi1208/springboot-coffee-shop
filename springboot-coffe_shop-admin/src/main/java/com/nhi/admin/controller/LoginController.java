package com.nhi.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhi.libary.model.CartItem;
import com.nhi.libary.repository.CartItemRepository;
import com.nhi.libary.service.OrderService;

@Controller
public class LoginController {
	
	@Autowired
	private OrderService orderService;

	@GetMapping("/login")
	public String loginPage(Authentication authentication, Model model) {
		if(authentication != null) {
			return "redirect:/index";
		}
		
		model.addAttribute("title", "Trang đăng nhập");
		return "login";
	}
	
	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("title", "Trang chủ");
		return "index";
	}
	
	@GetMapping("/findRevenue")
	@ResponseBody
	public List<Object> findRevenue(Model model) {
		return this.orderService.findRevenue();
	}
}
