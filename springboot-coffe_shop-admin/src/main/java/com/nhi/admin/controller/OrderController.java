package com.nhi.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhi.libary.model.CartItem;
import com.nhi.libary.model.Order;
import com.nhi.libary.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	public String removeDecimalIfNeeded(double number) {
		
	    long longValue = (long) number;
	    if (number == longValue) {
	        return String.valueOf(longValue);
	    } else {
	        return String.valueOf(number);
	    }
	}
	
	@GetMapping("/order")
	public String findAllCartItemByOrder(Model model) {
		model.addAttribute("title", "Trang đơn hàng");
		model.addAttribute("orderList", this.orderService.findOrderSortDate());
	
		return "order";
	}
	
	@GetMapping("/order/findById")
	@ResponseBody
	public List<CartItem> findById(int id) {
		return this.orderService.findById(id);
	}
}
