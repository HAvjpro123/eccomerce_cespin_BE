package com.cespin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cespin.exception.BlogException;
import com.cespin.model.Blog;
import com.cespin.request.CreateBlogRequest;
import com.cespin.response.ApiResponse;
import com.cespin.service.BlogService;

@RestController
@RequestMapping("/api/admin/blogs")
public class AdminBlogController {

	@Autowired
	private BlogService blogService;
	
	@PostMapping("/")
	public ResponseEntity<Blog>createBlog(@RequestBody CreateBlogRequest req) throws BlogException {
		Blog blog=blogService.createBlog(req);
		
		return new ResponseEntity<Blog>(blog, HttpStatus.CREATED);	
	}
	
	@DeleteMapping("/{blogId}/delete")
	public ResponseEntity<ApiResponse>deleteBlog(@PathVariable Long blogId) throws BlogException {
		
		blogService.deleteBlog(blogId);
		ApiResponse res = new ApiResponse();
		res.setMessage("blog deleted successfully");
		res.setStatus(true);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Blog>>findAllBlog() {
		List<Blog>blogs=blogService.findAllBlog();
		return new ResponseEntity<>(blogs, HttpStatus.OK);
	}
	
	@PutMapping("/{blogId}/update")
	public ResponseEntity<Blog> updateBlog(@RequestBody Blog req,
			@PathVariable Long blogId) throws BlogException {
		Blog blog=blogService.updateBlog(blogId, req);
		return new ResponseEntity<>(blog, HttpStatus.OK);
	}
	
	@PostMapping("/creates")
	public ResponseEntity<ApiResponse>createMultipleProduct(@RequestBody CreateBlogRequest[] req ) {
		
		for(CreateBlogRequest blog:req) {
			blogService.createBlog(blog);
		}
		
		ApiResponse res = new ApiResponse();
		res.setMessage("blog created successfully");
		res.setStatus(true);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	
}
