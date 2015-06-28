
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width initial-scale =1">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/mainStyle.css">
<script src="<%= request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%= request.getContextPath()%>/js/storeHome.js"></script>
<title>Insert title here</title>
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>
<div class="form-group">
<h1 class="text-danger"><b>〈警告〉</b></h1>
<hr />
<div class="text-danger"><h3>此驗證碼已被使用，若您的帳號尚未驗證成功請選擇重新認證發送認證信。<h3></div><br />
</div>
<div class="form-group">
<a href="<%=request.getContextPath()%>/za101g2/front/" class="btn btn-default col-md-1 col-md-offset-1">回首頁</a>
</div>
<%@ include file="/files/PetribeFoot.file" %>
