package com.request.dataModel;

//to be converted to JSON object

public class Item {
	private String price;
	private String image;
	private String link;
	private String title;
	private String moreResults;
	private int totalPages;
	
		
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public String getMoreResults() {
		return moreResults;
	}
	public void setMoreResults(String moreResults) {
		this.moreResults = moreResults;
	}
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getLink() {
		return this.link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	

}
