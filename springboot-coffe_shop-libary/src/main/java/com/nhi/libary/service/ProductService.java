package com.nhi.libary.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhi.libary.model.Category;
import com.nhi.libary.model.Product;
import com.nhi.libary.repository.ProductRepository;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	public List<Product> findAllProduct() {
		return productRepository.findAllProductSortId();
	}

	public void addProduct(Product product, MultipartFile imageOfProduct) throws IOException {
		product.setImage(Base64.getEncoder().encodeToString(imageOfProduct.getBytes()));
		productRepository.save(product);
	}

	public void deleteProduct(int id) {
		this.productRepository.deleteById(id);
		
	}

	public void editProduct(Product product, MultipartFile imageOfProduct) throws IOException {
		Product product_database= productRepository.findById(product.getId()).get();
		
		
		if(!imageOfProduct.isEmpty()) {
			product_database.setImage(Base64.getEncoder().encodeToString(imageOfProduct.getBytes()));
		}
		
		product_database.setName(product.getName());
		product_database.setCategory(product.getCategory());
		product_database.setDiscription(product.getDiscription());
		
		product_database.setPrice(product.getPrice());
		product_database.setQuantity(product.getQuantity());
		product_database.setSale_price(product.getSale_price());
		this.productRepository.save(product_database);
	}

	public Product findProductById(int id) {
		return productRepository.findById(id).get();
	}
}
