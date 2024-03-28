package com.nhi.libary.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhi.libary.model.CartItem;
import com.nhi.libary.model.Customer;
import com.nhi.libary.model.Order;
import com.nhi.libary.repository.CartItemRepository;
import com.nhi.libary.repository.CustomerRepository;
import com.nhi.libary.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired 
	private CustomerRepository customerRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	

	public String addOrder(String email, String paymentMethod, String fullName, String phoneNumber, String address) {
		
		Customer customer = this.customerRepository.findByEmail(email);
		
		List<CartItem> cartItems = cartItemRepository.findByShoppingCart(customer.getShoppingCart().getId());

		if(cartItems == null || cartItems.isEmpty()) {
			return null;
		}
		
		int quantity = 0;
		for (CartItem cartItem : cartItems) {
			quantity += cartItem.getQuantity();
		}
		
		Order order = new Order();
		order.setDeliveryDate(LocalDate.now());
		order.setOrderDate(LocalDate.now());
		order.setPaymentMethod(paymentMethod);
		order.setQuantity(quantity);
		order.setTotalPrice(customer.getShoppingCart().getTotalPrice() + 20000);
		order.setFullName(fullName);
		order.setPhoneNumber(phoneNumber);
		order.setAddress(address);
		order.setCustomer(customer);
		this.orderRepository.save(order);
		
		for (CartItem cartItem : cartItems) {
			cartItem.setShoppingCart(null);
			this.cartItemRepository.save(cartItem);
		}
		
		customer.getShoppingCart().setTotalItem(0);
		customer.getShoppingCart().setTotalPrice(0);
		
		this.customerRepository.save(customer);
		
		return "Thành công";
	}
}
