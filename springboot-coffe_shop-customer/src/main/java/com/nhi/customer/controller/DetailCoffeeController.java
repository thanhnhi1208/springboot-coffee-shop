package com.nhi.customer.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class DetailCoffeeController {
	@GetMapping("/detailCoffee")
	public String detailCoffee() {
		return "detailCoffee";
	}
}
