package com.nhi.libary.service;

import java.util.List;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhi.libary.model.CartItem;
import com.nhi.libary.model.Customer;
import com.nhi.libary.model.ShoppingCart;
import com.nhi.libary.repository.CartItemRepository;
import com.nhi.libary.repository.CustomerRepository;
import com.nhi.libary.repository.ShoppingCartRepository;

@Service
public class ShoppingCartService {
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;

	public void addShoppingCart(CartItem cartItem, String email, double sale_price) {
		Customer customer = this.customerRepository.findByEmail(email);
		if(customer.getShoppingCart() == null) {
			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setTotalItem(1);
			
			if(sale_price != 0) {
				cartItem.setCostPrice(sale_price);
				shoppingCart.setTotalPrice(sale_price);
			}else {
				shoppingCart.setTotalPrice(cartItem.getCostPrice());
			}
			
			
			cartItem.setShoppingCart(shoppingCart);

			customer.setShoppingCart(shoppingCart);
			shoppingCartRepository.save(shoppingCart);
			customerRepository.save(customer);
			cartItemRepository.save(cartItem);
		}else {
			CartItem cartItemFormDatabase = this.cartItemRepository.findBySizeAndProductAndShoppingCart(cartItem.getSize(), 
					cartItem.getProduct().getId(), customer.getShoppingCart().getId());
			if(cartItemFormDatabase != null) {
				cartItem.setId(cartItemFormDatabase.getId());
				cartItem.setQuantity(cartItemFormDatabase.getQuantity() + cartItem.getQuantity());
				if(sale_price != 0) {
					customer.getShoppingCart().setTotalPrice(customer.getShoppingCart().getTotalPrice() + sale_price);
					cartItem.setCostPrice(cartItemFormDatabase.getCostPrice() + sale_price);
				}else {
					customer.getShoppingCart().setTotalPrice(customer.getShoppingCart().getTotalPrice() + cartItem.getCostPrice());
					cartItem.setCostPrice(cartItemFormDatabase.getCostPrice() + cartItem.getCostPrice());
				}
			}else {
				if(sale_price != 0) {
					cartItem.setCostPrice(sale_price);
					customer.getShoppingCart().setTotalPrice(customer.getShoppingCart().getTotalPrice() + sale_price);
				}else {
					cartItem.setCostPrice(cartItem.getCostPrice());
					customer.getShoppingCart().setTotalPrice(customer.getShoppingCart().getTotalPrice() + cartItem.getCostPrice());
				}
			}
			
			cartItem.setShoppingCart(customer.getShoppingCart());
			
			boolean haveItem = false;
			List<CartItem> cartItems = cartItemRepository.findByShoppingCart(customer.getShoppingCart().getId());
			for (CartItem citem : cartItems) {
				if(citem.getProduct().getId() == cartItem.getProduct().getId()) {
					haveItem = true;
				}
			}
			
			if(haveItem) {
				customer.getShoppingCart().setTotalItem(customer.getShoppingCart().getTotalItem());
			}else {
				customer.getShoppingCart().setTotalItem(customer.getShoppingCart().getTotalItem() +1);
			}
	
			customerRepository.save(customer);
			cartItemRepository.save(cartItem);
		}
	}

	public List<CartItem> findAllCartItemInShoppingCart(String email) {
		Customer customer = customerRepository.findByEmail(email);
		
		List<CartItem> cartItems = null;
		if(customer.getShoppingCart() != null) {
			cartItems = cartItemRepository.findByShoppingCart(customer.getShoppingCart().getId());
		}
		
		return cartItems;
	}

	public CartItem increaseQuantity(int id) {
		CartItem cartItem= this.cartItemRepository.findById(id).get();
		double price = cartItem.getCostPrice() / cartItem.getQuantity();
		
		cartItem.setQuantity(cartItem.getQuantity() + 1);
		cartItem.setCostPrice(price + cartItem.getCostPrice());
		
		cartItem.getShoppingCart().setTotalPrice(price+ cartItem.getShoppingCart().getTotalPrice() );
		
		
		return this.cartItemRepository.save(cartItem);
	}

	public CartItem decreaseQuantity(int id) {
		CartItem cartItem= this.cartItemRepository.findById(id).get();
		double price = cartItem.getCostPrice() / cartItem.getQuantity();
		
		cartItem.setQuantity(cartItem.getQuantity() - 1 );
		cartItem.setCostPrice(cartItem.getCostPrice() - price);
		
		cartItem.getShoppingCart().setTotalPrice(cartItem.getShoppingCart().getTotalPrice() - price );
		
		
		return this.cartItemRepository.save(cartItem);
	}

	public void deleteCartItemById(int id) {
		CartItem cartItem= this.cartItemRepository.findById(id).get();
		double price = cartItem.getCostPrice() / cartItem.getQuantity();
		
		cartItem.getShoppingCart().setTotalPrice(cartItem.getShoppingCart().getTotalPrice() - price );
		
		
		int checkProduct =0;
		int idOfCartItem = cartItem.getProduct().getId();
		List<CartItem> cartItems = cartItemRepository.findAll();
		for (CartItem item : cartItems) {
			if(item.getProduct().getId() == idOfCartItem) {
				checkProduct ++;
			}
		}
		
		if(checkProduct <=1 ) {
			cartItem.getShoppingCart().setTotalItem(cartItem.getShoppingCart().getTotalItem() - 1);
		}
		
		cartItemRepository.save(cartItem);
		cartItemRepository.deleteById(id);
	}


}
