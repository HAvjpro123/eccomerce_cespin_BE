package com.cespin.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cespin.exception.BlogException;
import com.cespin.model.Blog;
import com.cespin.request.CreateBlogRequest;

public interface BlogService {

	public Blog createBlog(CreateBlogRequest req);
	
	public String deleteBlog(Long blogId) throws BlogException;
	
	public Blog updateBlog(Long blogId, Blog req) throws BlogException;
	
	Blog findBlogById(Long id) throws BlogException;
	
	List<Blog> findAllBlog();
	
	Page<Blog>getAllBlog(String category, Integer pageNumber, Integer pageSize);
}
