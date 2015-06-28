<%@ page import="za101g2.member.model.MemberVO"%>
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
	<div class="row">
		<form method="post" action="<%=request.getContextPath()%>/za101g2/front/member/member.do" class="form-horizontal col-md-6">
		<div class="form-group">
		<h1 class="col-md-8 col-md-offset-4 text-center"><b>忘記密碼？</b></h1>
		</div>
		<div class="form-group">
			<label for="email" class="control-label col-md-4">電子信箱</label>
			<div class="col-md-6">
			<input type="text" name="email" id="email" class="form-control">
						<input type="hidden" name="action" value="forget_password">
			</div>
			<button type="submit" class="btn btn-primary col-md-2">送出</button>
		</div>
			<div class="form-group">
				<div class="alert ${message=='此信箱不存在。' or message==null?'alert-danger':'alert-success'} col-md-8 col-md-offset-4 text-center" role="alert"><b>${message==null?'輸入信箱即可收到密碼信。':message}</b></div>
			</div>
		<input type="hidden" name="requestURL" value="<%= request.getParameter("requestURL") %>">
		</form>
	</div>
	<%@ include file="/files/PetribeFoot.file" %>