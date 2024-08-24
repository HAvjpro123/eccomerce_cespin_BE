package com.cespin.request;


public class CreateBlogRequest {

	private String title;
	
	private String description;
	
	private String imageUrl;

	private String category;
	
	private String topLavelCategory;
	private String secondLavelCategory;
	private String thirdLavelCategory;
	
	
	public CreateBlogRequest() {
		// TODO Auto-generated constructor stub
	}


	public CreateBlogRequest(String title, String description, String imageUrl, String category,
			String topLavelCategory, String secondLavelCategory, String thirdLavelCategory) {
		super();
		this.title = title;
		this.description = description;
		this.imageUrl = imageUrl;
		this.category = category;
		this.topLavelCategory = topLavelCategory;
		this.secondLavelCategory = secondLavelCategory;
		this.thirdLavelCategory = thirdLavelCategory;
	}



	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
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


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getTopLavelCategory() {
		return topLavelCategory;
	}


	public void setTopLavelCategory(String topLavelCategory) {
		this.topLavelCategory = topLavelCategory;
	}


	public String getSecondLavelCategory() {
		return secondLavelCategory;
	}


	public void setSecondLavelCategory(String secondLavelCategory) {
		this.secondLavelCategory = secondLavelCategory;
	}


	public String getThirdLavelCategory() {
		return thirdLavelCategory;
	}


	public void setThirdLavelCategory(String thirdLavelCategory) {
		this.thirdLavelCategory = thirdLavelCategory;
	}
	
	
}
