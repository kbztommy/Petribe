<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     

<%@page import ="za101g2.member.model.*" %>
<%@page import ="za101g2.store.model.*" %>
<%@page import ="za101g2.serviceList.model.*" %>
<%@page import ="java.util.*" %>        
<%    
    response.setHeader("Pragma","no-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);
%>
    
<!DOCTYPE html>
<html lang="en" style="height:calc(100%);">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width initial-scale =1">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/normalize.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/mainStyle.css">
    <script src="<%= request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
    <script src="<%= request.getContextPath()%>/js/bootstrap.min.js"></script>
    <script src="http://maps.googleapis.com/maps/api/js"></script>
    <script>  
    function initialize() {
	   	var latlng = '${cityLatlng[storeSearchMap['city']]}';
	  	var lat = parseFloat(latlng.split(",")[0]);
	  	var lng = parseFloat(latlng.split(",")[1]);	 
	  	geocoder = new google.maps.Geocoder();
	    var mapProp = {
	        center:new google.maps.LatLng(lat,lng),
	        zoom:11,
	        mapTypeId:google.maps.MapTypeId.ROADMAP
	    };
	    var map=new google.maps.Map(document.getElementById("map"),mapProp);
     	
	    for(var i=0;i<addressList.length;i++){
	    	codeAddress(addressList[i]);
	    }
	    
	    function codeAddress(address) {
	      
	        geocoder.geocode({
	            'address': address
	        }, function(results, status) {
	            if (status == google.maps.GeocoderStatus.OK) {	                
	                var marker = new google.maps.Marker({
	                    map: map,
	                    position: results[0].geometry.location
	                });
	            } else {
	                alert("Geocode was not successful for the following reason: " + status);
	            }
	        });
	    }

	    
	    $('.searchPanel').click(function(){
   			$(this).next().submit();
   			
   		});
    }
    
    google.maps.event.addDomListener(window, 'load', initialize);
    
   	$(function(){
   		
   		
   			
   		
   	});
   		
   	

    </script>
    

    <title>Search Boarding Store</title>
</head>
<body style="height:calc(100%);">          
<%@ include file="/files/PetribeHeadPerH.file" %>   
            <div class="row">
		        <div class="col-md-8">
					<ol class="breadcrumb myBreadCrumb">
					  <li class="active">尋找寄養</li>
					</ol>
				</div>
            </div>
			<div class="row" >
				<div class = "col-md-8">
					<form class="form-inline" method="post" action="<%=request.getContextPath()%>/StoreServlet">
						<div class="form-group">
							 <font color="red">*</font>       				 
		        			 <select class="form-control" name="city" id="city">
		        			 	<option value="">地區(必選)</option>
		        			 	<c:forEach var="city" items="${cityList}" >
		        			 		<option value="${city}" ${ (city!= storeSearchMap['city'])?'':'selected' } >${city}</option>
		        			 	</c:forEach>
		        			 </select>
		  			    </div>
		    			<div class="form-group">       				 
		        			 <select class="form-control" name="petType" >
		        			 	<option value="">寵物類型</option>
		        			 	<option value="1" ${'1'==storeSearchMap['petType']?'selected':'' }>代養狗</option>
		        			 	<option value="2" ${'2'==storeSearchMap['petType']?'selected':'' }>代養貓</option>
		        			 	<option value="3" ${'3'==storeSearchMap['petType']?'selected':'' }>貓狗都能代養</option>
		        			 </select>
		  			    </div>   				
<!-- 		  				<div class="form-group"> -->
<!-- 		    				<label class="sr-only" for="focusAddress">這地址附近的...</label> -->
<!-- 		    				<input type="text" class="form-control" id="focusAddress" placeholder="這地址附近的..." > -->
<!-- 		  				</div> -->
		    			<button type="submit" class="btn btn-warning" id="startSearch">開始尋找</button>
		    			<input type="hidden" name="action" value="searchStore">
					</form>		
				</div>
			</div>
			<c:if test="${not empty errorMsgs}">
				<div class="col-md-4" >
					<div class="alert alert-danger" role="alert">
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<strong>${message}</strong>
							</c:forEach>
						</ul>
					</div>		
					
				</div>
			</c:if>
			<% if(request.getAttribute("storeSearchList")!=null){
				List<HashMap<String,Object>> storeSearchList= (List<HashMap<String,Object>>)request.getAttribute("storeSearchList");
			%>
			<div class="row" id ="map-container" style="height:calc(80%);margin-top:15px;">		
				
				
				<div id="map" class="col-md-5 col-md-push-6">			
				</div>
				
				<div class="col-md-6 col-md-pull-5" style="height:calc(100%)">
				
					<%@ include file="SearchStorePage.file" %>			
					<script type="text/javascript">
						var addressList = [];
					</script>
					<c:forEach  var="storeSearchVO" items="${storeSearchList}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
						<div class="col-xs-11 searchPanel storeInfoPanel clearfix" role="button" >					
			  				<div class="row" style="height:calc(100%)"> 
			  					<div class="col-sm-3" style="height:calc(100%)">				
			    					<img src = "<%= request.getContextPath() %>/PicForStrore?id=${storeSearchVO['storeVO'].id}" class="img-autoW" >
			    				</div>    		
			    				
			    				<div class="col-xs-9 text-center" style="height:calc(100%)">
			    					<a href="<%=request.getContextPath()%>/StoreServlet?storeId=${storeSearchVO['storeVO'].id}&action=lookOverStore" role="button"><strong>${storeSearchVO['storeVO'].name}</strong></a><br>
			    					<label>${storeSearchVO['memberNickname']}</label>
			    					<p>${storeSearchVO['price']}<span class="glyphicon glyphicon-usd" aria-hidden="true"></span>起<p>
<%-- 			    					<p>${storeSearchVO['storeVO'].info}<p>    					 --%>
			    				</div>
			    			</div>  				
						</div>
						<form  class="form-inline" method="post" action="<%=request.getContextPath()%>/StoreServlet"> 	 			
			  				<input type="hidden" name="storeId" value="${storeSearchVO['storeVO'].id}">	  				
			  				<input type="hidden" name="action" value="lookOverStore">	  				
			  			</form>	  	
			  			<script type="text/javascript">
			  				addressList.push("${storeSearchVO['storeVO'].address}");
			  			</script>					
					</c:forEach>
					<%@include file="SearchStorePage2.file" %>			
				</div>
				
				
				
			</div> 
			<%} %>

<%@ include file="/files/PetribeFoot.file" %>



 