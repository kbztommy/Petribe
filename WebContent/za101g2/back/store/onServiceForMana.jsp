<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import ="za101g2.member.model.*" %>
<%@page import ="za101g2.store.model.*" %>
<%@page import ="za101g2.serviceList.model.*" %>
<%@page import ="java.util.*" %>
<%@page import ="za101g2.orderBoard.model.*" %>
<%    
    response.setHeader("Pragma","no-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="viewport" content="width=device-width initial-scale =1">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/normalize.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/backHome.css">
   	<script src="http://code.jquery.com/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
    <title>寄養回報資訊</title>
</head>
<body>
<%@ include file="/files/fixedNavbar.file" %>
	<section class="col-md-10">
		<br>
	
		<div class=col-md-12>
			<div class="well well-lg">
				<h3>代養狀況</h3>
				<h5>${ dateFormater1.format(onServiceVO.releaseDate)}</h5>
				<p>${onServiceVO.comments}</p>
			</div>	
		</div>
	
	
		<div class="col-md-12">
			<c:forEach var="pic" items="${onServiceVO.storePhotos }">
				<div class="storeInfoPanel ">	    		
		      		<img class="img-responsive center-block" src="<%=request.getContextPath() %>/PicForStorePhoto?id=${pic.id}">
				</div>		      
			</c:forEach>	
		</div>
	

	

</section>
	<div style="clear:both;">
		<footer id="the_footer">
			Copyright 2015 Institute For Information Industry ZA101 Petribe | Private Policy 
		</footer>
	</div>
</body>
</html>