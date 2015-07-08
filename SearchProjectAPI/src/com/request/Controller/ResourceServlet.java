package com.request.Controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.request.dataModel.Item;
import com.request.dataModel.SearchResultsBean;


@WebServlet("/ResourceServlet")
public class ResourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		SearchResultsBean srb = new SearchResultsBean();
		srb.setKeywords(request.getParameter("keywords"));
		srb.setEndpoint(request.getParameter("endpoint"));
		srb.setSearchIndex(request.getParameter("index"));
		srb.setRequestData();	
		srb.setListOfItems();
			
		List<Item> items = srb.getListOfItems();	
		String json = new Gson().toJson(items);		

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);

	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);	}
	
	
	
	

}
