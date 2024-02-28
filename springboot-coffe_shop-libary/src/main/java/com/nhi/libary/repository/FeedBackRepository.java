package com.nhi.libary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhi.libary.model.FeedBack;
import com.nhi.libary.model.ShoppingCart;

public interface FeedBackRepository extends JpaRepository<FeedBack, Integer>{

}
