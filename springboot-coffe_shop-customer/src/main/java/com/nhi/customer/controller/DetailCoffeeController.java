package com.nhi.customer.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nhi.libary.model.FeedBack;
import com.nhi.libary.repository.CustomerRepository;
import com.nhi.libary.service.FeedbackService;
import com.nhi.libary.service.ProductService;

@Controller
public class DetailCoffeeController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private FeedbackService feedbackService;
	
	@GetMapping("/detailCoffee")
	public String detailCoffee(int id, Model model, Authentication authentication) {
		model.addAttribute("product", productService.findProductById(id));
		if(authentication != null ) {
			model.addAttribute("shoppingCart", customerRepository.findByEmail(authentication.getName()).getShoppingCart());
			model.addAttribute("customer", customerRepository.findByEmail(authentication.getName()));
			model.addAttribute("feedbackList", this.feedbackService.findAllFeedBack(id, authentication.getName()));

		}else {
			model.addAttribute("feedbackList", this.feedbackService.findAllFeedBack(id, null));

		}
		
		
		return "detailCoffee";
	}
	
	@PostMapping("/detailCoffee/feedback/add")
	public String addFeedback(FeedBack feedBack, MultipartFile hinhAnh1, MultipartFile hinhAnh2, RedirectAttributes redirectAttributes) throws IOException {
		this.feedbackService.addFeedback(feedBack, hinhAnh1, hinhAnh2);
		redirectAttributes.addAttribute("id", feedBack.getProduct().getId());
		return "redirect:/detailCoffee";
	}
}
