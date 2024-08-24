package com.cespin.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Blog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	private String title;
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	private String description;
	
	@Column(name="image_url")
	private String imageUrl;

	private LocalDateTime createdAt;
	
	public Blog() {
		// TODO Auto-generated constructor stub
	}

	
	public Blog(Long id, String title, Category category, String description, String imageUrl,
			LocalDateTime createdAt) {
		super();
		this.id = id;
		this.title = title;
		this.category = category;
		this.description = description;
		this.imageUrl = imageUrl;
		this.createdAt = createdAt;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	


	
	
	
}
