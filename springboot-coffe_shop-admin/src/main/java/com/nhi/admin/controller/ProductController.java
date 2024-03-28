package com.nhi.admin.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nhi.libary.service.CategoryService;
import com.nhi.libary.service.ProductService;
import com.nhi.libary.model.Category;
import com.nhi.libary.model.Product;
@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/product")
	public String Product(Model model) {
		model.addAttribute("title", "Cà phê");
		model.addAttribute("productList", this.productService.findAllProduct());
		model.addAttribute("categoryList", this.categoryService.findAllCategory());
		return "product";
		
	}
	
	@PostMapping("/product/add")
	public String productAdd(Product product, MultipartFile imageOfProduct) throws IOException {
		this.productService.addProduct(product, imageOfProduct);
		return "redirect:/product";
		
	}
	
	@PostMapping("/product/edit")
	public String editProduct(Product product, MultipartFile imageOfProduct) throws IOException {
		System.out.println(product.getId());
		this.productService.editProduct(product, imageOfProduct);
		return "redirect:/product";
	}
	
	@GetMapping("/product/findById")
	@ResponseBody
	public Product findProductById(int id) {
		return productService.findProductById(id);
	}
	
	@GetMapping("/product/delete")
	public String deleteProduct(int id) {
		this.productService.deleteProduct(id);
		return "redirect:/product";
	}
	
	@GetMapping("/product/expired")
	public String expried(int id) {
		this.productService.expired(id);
		return "redirect:/product";
	}
}
