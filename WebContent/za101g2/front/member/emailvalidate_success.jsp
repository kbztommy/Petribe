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
<div class="col-md-10 form-horizontal">
<div class="form-group">
<div class="col-md-10  col-md-offset-1"><h3>電子信箱驗證成功，請重新登入。</h3></div>
</div>
<div class="form-group">
<a href="<%=request.getContextPath()%>/za101g2/front/member/login.jsp" class="btn btn-primary col-md-2 col-md-offset-2">登入</a>
<a href="<%=request.getContextPath()%>/za101g2/front/" class="btn btn-default col-md-2 col-md-offset-1">回首頁</a>
</div>
</div>
<%@ include file="/files/PetribeFoot.file" %>
