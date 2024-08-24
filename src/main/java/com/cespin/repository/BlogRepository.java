package com.cespin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cespin.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long>{

	@Query("SELECT b FROM Blog b " +
		       "WHERE (:category IS NULL OR b.category.name = :category)")
	public List<Blog> filterBlogs(@Param("category") String category);
}
