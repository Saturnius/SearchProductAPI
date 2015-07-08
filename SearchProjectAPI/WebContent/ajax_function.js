/**
 * defines ajax request;
 */
function fetchResults(keywords, endpoint, index){
	$.ajax({              	 
        type: "POST",  
        url: "ResourceServlet",  
        dataType : "json",
        data: {"keywords": keywords, "endpoint":endpoint, "index":index},  
        success: function(responseJson){ 
        	
        	successFunctionInit(responseJson);        	
        	
        	
        	
        }
    });  
};



function successFunctionInit(responseJson){
	listOf =  JSON.parse(JSON.stringify(responseJson));
	
	$( "#progressbar" ).hide();
	          	
	var count = 1;  	
	
	var count = 1;
	              	
   var $itemsJ = $(responseJson);
   
   if($itemsJ.length == 0){
	   $("<div class='itemDetail'></div>").html("<p>Could not find any results</p>").appendTo("#content1");
	   return;
	   
   } else {
	   
   for(var i = 0; i < $itemsJ.length && i < 13; i++){
	  
	   var item = $itemsJ[i];
	   var moreResults = item.moreResults;
	   
	   if(item.totalPages > 4){
		   
	   totalPages = 4;
	   
	   }
	   else{
		   totalPages = item.totalPages - 1;
	   }  	   
       
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
   $("<p id='message'></p>").html("This service returns up to 50 results. Click " + "<a href='" + moreResults + "'>here</a> to continue your search directly at Amazon.").appendTo("#finalMessage");
   	   
   } $( "#row1, #row2, #row3, #btn, #pagination" ).show("blind", 700); 
   
   $(function() {	
	   
	    $("#pagination").pagination("updateItems", totalPages * 13);
	    
	});
   };
   
   
   
   

	




