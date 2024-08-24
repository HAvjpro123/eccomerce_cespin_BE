package com.cespin.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cespin.exception.ProductException;
import com.cespin.model.Product;
import com.cespin.model.Rating;
import com.cespin.model.User;
import com.cespin.repository.RatingReponsitory;
import com.cespin.request.RatingRequest;

@Service
public class RatingServiceImplementation implements RatingService{

	private RatingReponsitory ratingRepository;
	private ProductService productService;
	
	public RatingServiceImplementation(RatingReponsitory ratingRepository, ProductService productService) {
		this.ratingRepository=ratingRepository;
		this.productService=productService;
	}
	
	@Override
	public Rating createRating(RatingRequest req, User user) throws ProductException {
		Product product=productService.findProductById(req.getProductId());
		
		Rating rating=new Rating();
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(req.getRating());
		rating.setCreatedAt(LocalDateTime.now());
		
		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getProductsRating(Long productId) {

		
		return ratingRepository.getAllProductsRating(productId);
	}

}
