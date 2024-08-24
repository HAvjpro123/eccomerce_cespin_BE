package com.cespin.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cespin.exception.BlogException;
import com.cespin.model.Blog;
import com.cespin.model.Category;
import com.cespin.repository.BlogRepository;
import com.cespin.repository.CategoryRepository;
import com.cespin.request.CreateBlogRequest;

@Service
public class BlogServiceImplementation implements BlogService{
	
	private BlogRepository blogRepository;
	private UserService userService;
	private CategoryRepository categoryRepository;
	
	public BlogServiceImplementation(CategoryRepository categoryRepository, BlogRepository blogRepository, UserService userService) {
		this.blogRepository=blogRepository;
		this.userService=userService;
		this.categoryRepository=categoryRepository;
	}

	@Override
	public Blog createBlog(CreateBlogRequest req) {
Category topLevel=categoryRepository.findByName(req.getTopLavelCategory());
		
		if(topLevel==null) {
			Category topLavelCategory=new Category();
			topLavelCategory.setName(req.getTopLavelCategory());
			topLavelCategory.setLevel(1);
			
			topLevel=categoryRepository.save(topLavelCategory);
		} 
		
		Category secondLevel=categoryRepository.findByNameAndParant(req.getSecondLavelCategory(), topLevel.getName());
		
		if(secondLevel==null) {
			
			Category secondLavelCategory=new Category();
			secondLavelCategory.setName(req.getSecondLavelCategory());
			secondLavelCategory.setParentCategory(topLevel);
			secondLavelCategory.setLevel(2);
			
			secondLevel=categoryRepository.save(secondLavelCategory);
			
		}
		
		Category thridLevel=categoryRepository.findByNameAndParant(req.getThirdLavelCategory(), secondLevel.getName());
		
		if(thridLevel==null) {
			
			Category thridLavelCategory=new Category();
			thridLavelCategory.setName(req.getThirdLavelCategory());
			thridLavelCategory.setParentCategory(secondLevel);
			thridLavelCategory.setLevel(3);
			
			thridLevel=categoryRepository.save(thridLavelCategory);
			
		}
		
		Blog blog=new Blog();
		blog.setTitle(req.getTitle());
		blog.setDescription(req.getDescription());
		blog.setImageUrl(req.getImageUrl());
		blog.setCreatedAt(LocalDateTime.now());
		blog.setCategory(thridLevel);
		Blog savedBlog=blogRepository.save(blog);
		return savedBlog;
	}

	@Override
	public String deleteBlog(Long blogId) throws BlogException {
		Blog blog=findBlogById(blogId);
		blogRepository.delete(blog);
		return "Delete Blog Successfully";
	}

	@Override
	public Blog updateBlog(Long blogId, Blog req) throws BlogException {
		Blog blog=findBlogById(blogId);
		
		if(req.getDescription()!= "") {
			blog.setDescription(req.getDescription());
		}
		
		return blogRepository.save(blog);
	}

	@Override
	public Blog findBlogById(Long id) throws BlogException {
		Optional<Blog>opt=blogRepository.findById(id);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new BlogException("Blog not found with id-"+id);
	}




	@Override
	public Page<Blog> getAllBlog(String category, Integer pageNumber, Integer pageSize) {
		
		Pageable pageble=PageRequest.of(pageNumber, pageSize);		
	
		List<Blog>blogs=blogRepository.filterBlogs(category);
		
		int startIndex=(int) pageble.getOffset();
		int endIndex=Math.min(startIndex + pageble.getPageSize(), blogs.size());
	
		List<Blog> pageContent=blogs.subList(startIndex, endIndex);
		
		Page<Blog>filteredBlogs=new PageImpl<>(pageContent, pageble, blogs.size());
		
		return filteredBlogs;
		
	}

	@Override
	public List<Blog> findAllBlog() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
