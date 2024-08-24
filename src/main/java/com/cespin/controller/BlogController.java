package com.cespin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cespin.exception.BlogException;
import com.cespin.model.Blog;
import com.cespin.service.BlogService;

@RestController
@RequestMapping("/api")
public class BlogController {

	@Autowired
	private BlogService blogService;

	@GetMapping("/blogs")
	public ResponseEntity<Page<Blog>> findBlogByCategoryHandler(@RequestParam String category,
			@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		Page<Blog> res = blogService.getAllBlog(category, pageNumber, pageSize);
		
		System.out.println("complete blogs");
		
		return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
		
	} 
	
	@GetMapping("/blogs/id/{blogId}")
	public ResponseEntity<Blog> findBlogByidHandler(@PathVariable Long blogId) throws BlogException {
		Blog blog = blogService.findBlogById(blogId);
		return new ResponseEntity<Blog>(blog, HttpStatus.ACCEPTED);
	}
	
	
	
	
}
