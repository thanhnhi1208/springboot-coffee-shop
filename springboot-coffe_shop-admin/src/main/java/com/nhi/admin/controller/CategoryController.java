package com.nhi.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhi.libary.model.Category;
import com.nhi.libary.service.CategoryService;

@Controller
public class CategoryController { 
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/category")
	public String Category(Model model) {
		model.addAttribute("title", "Loại cà phê");
		model.addAttribute("categoryList", this.categoryService.findAllCategory());
		return "category";
		
	}
	
	@PostMapping("/category/add")
	public String addCategory(Category category) {
		this.categoryService.addCategory(category);
		return "redirect:/category";
	}
	
	@PostMapping("/category/edit")
	public String editCategory(int id, String name) {
		this.categoryService.editCategory(id, name);
		return "redirect:/category";
	}
	
	@GetMapping("/category/findById")
	@ResponseBody
	public Category findCategoryById(int id) {
		return categoryService.findCategoryById(id);
	}
	
	@GetMapping("/category/delete")
	public String deleteCategory(int id) {
		this.categoryService.deleteCategory(id);
		return "redirect:/category";
	}
}
