package com.cespin.service;

import java.util.List;

import com.cespin.exception.ProductException;
import com.cespin.model.Review;
import com.cespin.model.User;
import com.cespin.request.ReviewRequest;

public interface ReviewService {

	public Review createReview(ReviewRequest req, User user) throws ProductException;
	public List<Review>getAllReview(Long productId);
	
}
