package com.nhi.libary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhi.libary.model.Category;
import com.nhi.libary.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public void addCategory(Category category) {
		this.categoryRepository.save(category);
	}
	
	public List<Category> findAllCategory() {
		return categoryRepository.findAll();
	}

	public void editCategory(int id, String name) {
		Category category= categoryRepository.findById(id).get();
		category.setName(name);
		this.categoryRepository.save(category);
	}

	public Category findCategoryById(int id) {
		return categoryRepository.findById(id).get();
	}

	public void deleteCategory(int id) {
		this.categoryRepository.deleteById(id);
	}
}
