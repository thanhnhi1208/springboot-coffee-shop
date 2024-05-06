package com.nhi.libary.service;

import java.time.LocalDate;
import java.util.ArrayList;
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

		if (cartItems == null || cartItems.isEmpty()) {
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
			cartItem.setOrder(order);
			this.cartItemRepository.save(cartItem);
		}

		customer.getShoppingCart().setTotalItem(0);
		customer.getShoppingCart().setTotalPrice(0);

		this.customerRepository.save(customer);

		return "Thành công";
	}

	public List<Order> findByCustomer(String email) {
		Customer customer = this.customerRepository.findByEmail(email);
		List<Order> newOrderList = new ArrayList<>();
		
		List<Order> orderList = this.orderRepository.findByCustomer(customer.getId());
		for (Order order : orderList) {
			if(order.getDeliveryDate().isEqual(LocalDate.now())) {
				newOrderList.add(order);
			}
		}
		return newOrderList;
	}

	public List<CartItem> findCartItemByOrder(String email) {
		Customer customer = this.customerRepository.findByEmail(email);
		List<Order> orderList = this.orderRepository.findByCustomer(customer.getId());

		List<CartItem> cartItemList = new ArrayList<>();
		for (Order order : orderList) {
			List<CartItem> cartItems = cartItemRepository.findCartItemByOrder(order.getId());
			if (cartItems != null && cartItems.isEmpty() == false) {
				for (CartItem cartItem : cartItems) {
					cartItemList.add(cartItem);
				}
			}
		}

		return cartItemList;
	}

	public List<Object> findRevenue() {
//		for (Object obj : this.cartItemRepository.findRevenue()) {
//			Object[] row = (Object[]) obj;
//			for (int i = 0; i < row.length; i++) {
//				System.out.println(row[i]);
//			}
//		}
		return this.cartItemRepository.findRevenue();
	}
	
	public List<CartItem> findAllCartItemByOrder(){
		List<Order> orderList=  this.orderRepository.findAllOrderByDate();
		List<CartItem> cartItemList = new ArrayList<>();
		for (Order order : orderList) {
			for (CartItem cartItem : this.cartItemRepository.findByOrder(order)) {
				cartItemList.add(cartItem);
			}
		}
		
		return cartItemList;
	}
	
	public List<Order> findOrderSortDate(){
		List<Order> newOrderList = new ArrayList<>();
		
		List<Order> orderList = this.orderRepository.findAllOrderByDate();
		for (Order order : orderList) {
			if(order.getDeliveryDate().isEqual(LocalDate.now()) == true) {
				newOrderList.add(order);
			}
		}
		return newOrderList;
	}

	public List<CartItem> findById(int id) {
		Order order = this.orderRepository.findById(id).get();
		
		return this.cartItemRepository.findByOrder(order);
	}

	public List<Order> shipped(String email) {
		Customer customer = this.customerRepository.findByEmail(email);
		List<Order> newOrderList = new ArrayList<>();
		
		List<Order> orderList = this.orderRepository.findByCustomer(customer.getId());
		for (Order order : orderList) {
			if(order.getDeliveryDate().isEqual(LocalDate.now()) == false) {
				newOrderList.add(order);
			}
		}
		return newOrderList;
	}
}
