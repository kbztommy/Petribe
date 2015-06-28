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
<form class="form-horizontal col-md-8  col-md-offset-1">
<div class="form-group">
<div class="col-md-9 col-md-offset-2">
<h1><b>手機認證</b></h1>
</div>
</div>
<div class="form-group">
<div class="progress">
  <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
  </div>
</div>
</div>
<div class="form-group">
<h3 class="col-md-9 col-md-offset-2"><b>驗證成功。</b></h3>
</div>
<div class="form-group">
<a href="<%=request.getContextPath()%>/za101g2/front/" class="btn btn-default col-md-2 col-md-offset-2">回首頁</a>
</div>
</form>
<%@ include file="/files/PetribeFoot.file" %>
