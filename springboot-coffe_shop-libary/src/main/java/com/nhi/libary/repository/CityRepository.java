package com.nhi.libary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhi.libary.model.City;
import com.nhi.libary.model.ShoppingCart;

public interface CityRepository extends JpaRepository<City, Integer>{

}
