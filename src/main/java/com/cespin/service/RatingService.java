package com.cespin.service;

import java.util.List;

import com.cespin.exception.ProductException;
import com.cespin.model.Rating;
import com.cespin.model.User;
import com.cespin.request.RatingRequest;

public interface RatingService {

	public Rating createRating(RatingRequest req, User user) throws ProductException;
	public List<Rating>getProductsRating(Long productId); 
	
}
