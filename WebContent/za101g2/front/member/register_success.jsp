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
<script src="<%=request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/storeHome.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="/files/PetribeHead.file"%>
	<div>
		<h1>〈註冊成功〉</h1>
	</div>
	<hr />
	<div>
		<h3>　　請至電子信箱收取信件獲得完整權限。</h3>
	</div>
	<hr />
	<a href="<%=request.getContextPath()%>/za101g2/front/"
		class="btn btn-default col-md-1 col-md-offset-3">回首頁</a>
	<%@ include file="/files/PetribeFoot.file"%>