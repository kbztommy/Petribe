<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@page import ="za101g2.member.model.*" %>
<%@page import ="za101g2.store.model.*" %>
<%@page import ="za101g2.orderList.model.*" %>
<%@page import ="java.util.*" %>
<%@page import ="za101g2.orderBoard.model.*" %>
<%    


    response.setHeader("Pragma","no-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);
    
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width initial-scale =1">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/normalize.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/mainStyle.css">
    <script src="<%= request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
    <script src="<%= request.getContextPath()%>/js/bootstrap.min.js"></script>   
	<script>
		$(document).ready(function(){
							
			
		});//end of ready
	</script>
<title>新增回報</title>
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>
	<div class="row">
		<div class="col-md-8">
			<c:if test="${param.from =='store'}">
				<ol class="breadcrumb myBreadCrumb">
				  <li><a href="<%=request.getContextPath() %>/orderBoardServlet?action=StoreOrderList&whichPage=1">商店訂單</a></li>
				  <li><a href="<%= request.getContextPath()%>/orderBoardServlet?action=OneStoreOrderInfo&orderId=${onServiceVO.orderBoardVO.id}">訂單資訊</a></li>
				  <li class="active">回報資訊</li>
				</ol>
			</c:if>
			<c:if test="${param.from =='cust'}">
				<ol class="breadcrumb myBreadCrumb">
				  <li><a href="<%= request.getContextPath()%>/za101g2/front/orderBoard/CustOrderList.jsp">我的訂單</a></li>
				  <li><a href="<%= request.getContextPath()%>/orderBoardServlet?action=OneCustOrderInfo&orderId=${onServiceVO.orderBoardVO.id}">訂單資訊</a></li>
				  <li class="active">回報資訊</li>
				</ol>
			</c:if>
		</div>
	</div>
	<div class="row">
		<div class=col-md-8>
			<div class="well well-lg wordForBoard">
				<h3>代養狀況</h3>
				<h5>${ dateFormater1.format(onServiceVO.releaseDate)}</h5>
				<p>${onServiceVO.comments}</p>
			</div>	
		</div>
	</div>
	<div class="row">
		<div class="col-md-8">
			<c:forEach var="pic" items="${onServiceVO.storePhotos }">
				<div class="storeInfoPanel ">	    		
		      		<img class="img-responsive center-block" src="<%=request.getContextPath() %>/PicForStorePhoto?id=${pic.id}">
				</div>		      
			</c:forEach>	
		</div>
	</div>
<%@ include file="/files/PetribeFoot.file" %> 
