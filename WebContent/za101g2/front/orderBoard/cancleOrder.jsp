<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     

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
<title>訂單已取消</title>
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>
<br><br>
	<div class="col-md-8">
		<div class="jumbotron" style="background-color:white;">
		  <h1>訂單已取消</h1>
		  <br>
		 
		 	<a class="btn btn-info btn-lg" href="<%=request.getContextPath()%>/StoreServlet?action=lookOverStore&storeId=${storeId}" role="button">回到商家</a>
		
			 <a class="btn btn-primary btn-lg" href="<%=request.getContextPath() %>/za101g2/front/index.jsp" role="button">回到首頁</a>
		 
		</div>
	</div>
<%@ include file="/files/PetribeFoot.file" %> 
