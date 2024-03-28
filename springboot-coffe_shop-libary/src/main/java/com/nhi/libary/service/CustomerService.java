package com.nhi.libary.service;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nhi.libary.model.Customer;
import com.nhi.libary.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;

	public Customer findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

	public Customer addProfile(Customer customer, MultipartFile imageOfCustomer) throws IOException {
		Customer checkPhone = this.customerRepository.findByPhoneNumber(customer.getPhoneNumber());
		Customer checkAccountNumber = this.customerRepository.findByAccountNumber(customer.getAccountNumber());
		if((checkPhone != null && checkPhone.getId() != customer.getId() )  
				|| checkAccountNumber != null && checkAccountNumber.getId() != customer.getId()) {
			return null;
		}
		
		if(imageOfCustomer.isEmpty()) {
			Customer customerTemp =  customerRepository.findById(customer.getId()).get();
			customer.setImage(customerTemp.getImage());
		}else {
			customer.setImage(Base64.getEncoder().encodeToString(imageOfCustomer.getBytes()));
		}
		
		return customerRepository.save(customer);
	}
}
