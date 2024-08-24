package com.cespin.service;

import com.cespin.exception.ProductException;
import com.cespin.model.Cart;
import com.cespin.model.User;
import com.cespin.request.AddItemRequest;

public interface CartService {

	public Cart createCart(User user);
	
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException;
	
	public Cart findUserCart(Long userId);
	
}
