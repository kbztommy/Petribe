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
	<form method="post" action="<%=request.getContextPath()%>/za101g2/front/member/member.do" class="form-horizontal col-md-6">
		<div class="form-group">
		<label for="nickname" class="control-label col-md-4">修改暱稱</label>
		<div class="col-md-6">
		<input type="text" name="nickname" id="nickname"  class="form-control">
		</div>
		<input type="hidden" name="action" value="updateNickname">
		<div>
		<input type="submit" value="修改" class="btn btn-primary col-md-2">
		</div>
		</div>
			<c:if test="${not empty nicknameError}">
		<div class="form-group">
			<div class="alert alert-danger col-md-8 col-md-offset-4 text-center" role="alert"><b>${nicknameError}</b></div>
		</div>
	</c:if>
	</form>
<%@ include file="/files/PetribeFoot.file" %>
