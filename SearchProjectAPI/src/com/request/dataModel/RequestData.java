package com.request.dataModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//gets signed link and parses through XML to retrieve needed data
public class RequestData {

	private static final String ACCESS_KEY_ID = "YOUR ID HERE";
	private static final String SECRET_KEY = "YOUR SECRET KEY HERE";	
	private String endpoint;
	private String version = this.versionDate();
	private String keywords;
	private String searchIndex;
	

	public RequestData(String endpoint, String keywords, String searchIndex) {		
		this.endpoint = endpoint;
		this.keywords = keywords;
		this.searchIndex = searchIndex;
		
		
	}
	
	public String getSignedLink(int pageNr) {
		String requestUrl = null;
		String pageNrStr = String.valueOf(pageNr);

		SignedRequestsHelper helper;
		try {
			helper = SignedRequestsHelper.getInstance(endpoint, ACCESS_KEY_ID,
					SECRET_KEY);
		} catch (Exception e) {
			e.printStackTrace();
			return requestUrl;
		}

		// set parameters
		Map<String, String> params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("Version", version);
		params.put("Operation", "ItemSearch");
		params.put("SearchIndex", searchIndex);
		params.put("Keywords", keywords);
		params.put("AssociateTag", "vanitygoods-20");
		params.put("XMLEscaping", "Double");
		params.put("ResponseGroup", "Images,Small,Offers");
		params.put("ItemPage", pageNrStr);

		requestUrl = helper.sign(params);

		return requestUrl;
	}
	
	public String versionDate() {
		Date currentDate = new Date();
		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
		String str = form.format(currentDate);
		return str;
	}

	public List <Item> getListOfSearchResults() {
		List<Item> allItems = new ArrayList<Item>();
		String moreResults = "";
		String requestUrl;

		int j = 1;

		
		// amazon returns max 5 pages when searching in 'All'
		
		while (j < 6) {
			requestUrl = this.getSignedLink(j);

			try {
				Document doc = getDocument(requestUrl);

				NodeList ndList = doc.getElementsByTagName("Item");

				if (ndList.getLength() < 1) {
					break;
				}

				Node moreSearchResults = doc.getElementsByTagName(
						"MoreSearchResultsUrl").item(0);
				if (moreSearchResults != null) {

					moreResults = moreSearchResults.getTextContent();
				}

				int totalPages = Integer.parseInt(doc
						.getElementsByTagName("TotalPages").item(0)
						.getTextContent());

				if (j > totalPages) {

					break;
				}

				// fill list with needed data
				
				allItems.addAll(addItems(doc, ndList.getLength(), moreResults, totalPages));				

			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			j++;
		}
		return allItems;
		
	}

	public Document getDocument(String requestUrl) {
		Document doc;

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(requestUrl);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return doc;
	}

	
	
	public List<Item> addItems(Document doc, int length, String moreResults, int totalPages){
		List<Item> allItems = new ArrayList<Item>();
		for (int i = 0; i < length; i++) {			

			Item item = new Item();
			Node itemNode = doc.getElementsByTagName("Item").item(i);
			if (itemNode instanceof Element) {
				Element docElement = (Element) itemNode;
				Node imageNode = docElement.getElementsByTagName(
						"MediumImage").item(0);
				if (imageNode != null) {
					item.setImage(imageNode.getFirstChild()
							.getTextContent());
				} else {
					item.setImage("https://placehold.it/160x160");
				}
			}
			Node titleNode = doc.getElementsByTagName("Title").item(i);
			item.setTitle(titleNode.getTextContent());

			Node linkNode = doc.getElementsByTagName("DetailPageURL")
					.item(i);
			item.setLink(linkNode.getTextContent());
			item.setMoreResults(moreResults);
			item.setTotalPages(totalPages);

			allItems.add(item);
		}
		
		return allItems;
		
	}
	
	

}
