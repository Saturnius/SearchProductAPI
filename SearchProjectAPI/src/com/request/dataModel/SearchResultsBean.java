package com.request.dataModel;
import java.util.List;

//passing data between UI and model

public class SearchResultsBean {
	
	private String keywords;
	private String searchIndex;
	private String endpoint;
	private String XMLString;	
	private List<Item> listOfItems;
	private RequestData requestData;
	
	
	
	public RequestData getRequestData() {
		return requestData;
	}
	public void setRequestData() {
		this.requestData = new RequestData(endpoint, keywords, searchIndex);
	}
	public List<Item> getListOfItems() {
		return listOfItems;
	}
	public void setListOfItems() {
		this.listOfItems = requestData.getListOfSearchResults();	}	
		
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		keywords = keywords.trim();
		this.keywords = keywords;
	}
	public String getSearchIndex() {
		return searchIndex;
	}
	public void setSearchIndex(String searchIndex) {
		this.searchIndex = searchIndex;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getXMLString() {
		return this.XMLString;
	}
	public void setXMLString(String XMLString) {
		this.XMLString = XMLString;
	}
	
	

}
