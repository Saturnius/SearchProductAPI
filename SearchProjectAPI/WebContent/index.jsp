<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="styles.css" rel="stylesheet" type="text/css"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script> 
<script src="pagination.js"></script>
<script src="ajax_function.js"></script>

<title>Search Amazon</title>

</head>
<body>
<h1>Search on Amazon:</h1>
<div id="formDiv">
<form id="searchForm" action="ResourceServlet" method="post">   
  <select id="endpoint" name="endpoint">
  <option value="ecs.amazonaws.com">Amazon.com</option>  
  <option value="ecs.amazonaws.ca">Amazon.ca</option>
  <option value="ecs.amazonaws.co.uk">Amazon.co.uk</option>
  <option value="ecs.amazonaws.de">Amazon.de</option>
  <option value="ecs.amazonaws.fr">Amazon.fr</option>
  <option value="ecs.amazonaws.jp">Amazon.jp</option>
  
</select>

<select id="index" name="searchindex">
<option value="All">All Departments</option>
<option value="Apparel">Clothing</option>
<option value="Baby">Baby</option>
<option value="Beauty">Beauty</option>
<option value="Books">Books</option>
<option value="Automotive">Car &amp; Motorbike</option>
<option value="Classical">Classical</option>
<option value="DVD">DVD &amp; Blu-ray</option>
<option value="Electronics">Electronics &amp; Photo</option>
<option value="HealthPersonalCare">Health &amp; Personal Care</option>
<option value="Grocery">Grocery</option>

</select>
  <input id="keywords" type="search" name="amazonsearch"> 
  <input type="submit" value="Search">
</form>

</div>
<div id="progressbar"></div>
<div id="content1"> 
<div id="row1" ></div>
<div id="row2" ></div>
<div id="row3"></div>

</div>
<div id="finalMessage"> 
<div></div><button id="close" type="button">x</button></div>
</div>

<div id="pagination"></div>

<script type="text/javascript">
var listOf;
var totalPages = 1;
var itemsPerPage = 13;
$(document).ready(function(){ 
	
	
	var keywords = $("#keywords").val(); 
    var endpoint = $("#endpoint").val(); 
    var index = $("#index").val();    
	$( "#row1, #row2, #row3, #btn, #content2, #previous" ).hide();	
	
	//on submit
    $("#searchForm").on("submit",function(event){
    	newSearch(); 
    	
    	// reset page to #1
    	$(function() {	    	   
    	    $("#pagination").pagination("drawPage", 1);
    	    $("#pagination").hide();
    	});
    	
    	//get user search details
         event.preventDefault();
         keywords = $("#keywords").val(); 
         endpoint = $("#endpoint").val(); 
         index = $("#index").val();
        
        if(keywords.length >= 2){     
        	var moreResults = "";   	
        	
        //call ajax function from ajax_function.js
        	fetchResults(keywords, endpoint, index);   	
             
        }else{  
        	$( "#progressbar" ).hide();
            $("<div class='itemDetail'></div>").html("<font color=red>Request should be at least <b>2</b> characters long.</font>").appendTo("#content1");  
        } 
               
    });  
   
   //pagination
     $("#pagination").pagination({        	
            items: itemsPerPage,
            itemsOnPage: itemsPerPage,                 
            onPageClick: function(nextPage){ 
            $( "#row1, #row2, #row3, #btn" ).hide("puff", 180); 
            $("#finalMessage").hide();             
            $("#content1").children().children().empty(); 
            setTimeout(function(){ 
            	displayResults(listOf, $("#pagination").pagination("getCurrentPage")); }, 180);        
                         
             var current = $("#pagination").pagination("getCurrentPage");
             if(current === 4){
            	 $("#finalMessage").show("puff", 700); }          
             
            },                           
            
        });      
                  
 //reset view before new search             
    function newSearch(){   
    	$( "#row1, #row2, #row3, #btn, #pagination").hide();
    	$(".itemDetail, #message").remove(); 
    	$("#finalMessage").hide();
    	$( "#progressbar" ).show();
    	$( "#progressbar" ).progressbar({
    		  value: false
    		});
    };
      
    //fill and display divs (for "preloaded" pages)
    function displayResults(listOf, pageNumber){    		
    		var startIndex = setLimitTo(pageNumber, itemsPerPage);
    		if(listOf.length == 0){
    			   $("<div class='itemDetail'></div>").html("<p>Could not find any results</p>").appendTo("#content1");    			   
    			   return;
    		   } else {
    			   var count = 1;
    			   
    		   for(var i = startIndex; i < listOf.length && i < startIndex + itemsPerPage; i++){
    			   
    			   var item = listOf[i]; 			       			   
    		       
    		       var sTitle = item.title;			         
    		       var details = item.link;
    		       var smallImage = item.image;
    		       var itemContent = $("<div class='itemDetail'></div>").html("<p> <img src ='" 
    		    		   + smallImage + "'/></p> "   + "<p class='title'>  <a href='" + details + "'>" + sTitle + "</a></p>" );
    		       if(count < 6){
    		    	   itemContent.appendTo("#row1");
    		       } else if(count < 11){
    		    	   itemContent.appendTo("#row2");
    		       }else{
    		    	   itemContent.appendTo("#row3");
    		       }
    		       
    		    	   count++;
    		       } 
    		   }
    		$( "#row1, #row2, #row3, #btn, #pagination" ).show("blind", 1000); 
   
    	};
    	
    //calculate index based on pageNumber	
    	function setLimitTo(pageNumber, itemsPerPage){
    		var from;		
    		
    		switch (pageNumber) {

    		case 2:
    			from = itemsPerPage * 1;
    			break;
    		case 3:
    			from = itemsPerPage * 2;
    			break;
    		
    		case 4:
    			from = itemsPerPage * 3;
    			break;
    		default:
    			from = itemsPerPage * 0;
    			break;

    		}
    		return from;
    	};
    	$("#progressbar").css("border", "0px solid white"); 
    	$("#progressbar").css("width", "35%");
    	
    	$( "#close" ).click(function() {
    		  $("#finalMessage").hide("puff");
    		});
});		              
</script>         


</body>
</html>