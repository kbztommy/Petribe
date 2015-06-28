<%@page import="za101g2.staff.model.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.*"%>

<%
	StaffService staffSvc = new StaffService();
	List<StaffVO> staffList = staffSvc.getAll();
	pageContext.setAttribute("staffList", staffList);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width initial-scale =1">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/normalize.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/backHome.css">
<script src="http://code.jquery.com/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
</head>
<body>
	<%@ include file="/files/fixedNavbar.file"%>
	<section class="col-md-10">
	<form method="post" action="<%=request.getContextPath()%>/za101g2/back/staff/staff.do" class="form-horizontal col-md-10">
		<div class="form-group">
	<h1 class="col-md-4 col-md-offset-7"><b>修改密碼</b></h1>
	</div>
	<div class="form-group">
		<label for="password" class="control-label col-md-5">原密碼</label>
	<div class="col-md-7">
		<input type="password" name="password" id="password" value="" class="form-control">
	</div>
	</div>
	<div class="form-group">
		<label for="updatePassword" class="control-label col-md-5">修改密碼</label>
	<div class="col-md-7">
		<input type="password" name="updatePassword" id="updatePassword" value="" class="form-control">
	</div>
	</div>
	<div class="form-group">
		<label for="reUpdatePassword" class="control-label col-md-5">確認修改密碼</label>
	<div class="col-md-7">
		<input type="password" name="reUpdatePassword" id="reUpdatePassword" value="" class="form-control">
	</div>
	</div>
	<div class="form-group">
		<input type="hidden" name="action" value="update_password">
		<input type="submit" value="修改" class="btn btn-primary col-md-2 col-md-offset-6">
		<a href="<%=request.getContextPath()%>/za101g2/back/homePage/home.jsp" class="btn btn-default col-md-2 col-md-offset-1">取消</a>
	</div>
		<c:if test="${not empty passwordError}">
		<div class="form-group">
			<div class="alert alert-danger col-md-7 col-md-offset-5 text-center" role="alert"><b>${passwordError}</b></div>
		</div>
		</c:if>
	</form>

	</section>
	<div style="clear: both;">
		<footer id="the_footer"> Copyright 2015 Institute For
		Information Industry ZA101 Petribe | Private Policy </footer>
	</div>
</body>
</html>