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
	
<div class="col-md-8 col-md-offset-1 form-horizontal">
<div class="form-group">
<div class="col-md-8 col-md-offset-2"><h3>密碼修改成功，請重新登入。</h3></div>
</div>
<div class="form-group">
<a href="<%=request.getContextPath()%>/za101g2/back/homePage/" class="btn btn-default col-md-2 col-md-offset-4">回登入頁面</a>
</div>
</div>

	</section>
	<div style="clear: both;">
		<footer id="the_footer"> Copyright 2015 Institute For
		Information Industry ZA101 Petribe | Private Policy </footer>
	</div>
</body>
</html>