package com.nhi.libary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhi.libary.model.Category;
import com.nhi.libary.model.ShoppingCart;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
