<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="za101g2.announcement.model.AnnouncementService"%>
<%@ page import="za101g2.announcement.model.AnnouncementVO"%>
<%@ page import="za101g2.staff.model.StaffVO"%>

<html>
<head>
<meta name="viewport" content="width=device-width initial-scale =1">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/normalize.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/backHome.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/backHome2.css">
<script src="http://code.jquery.com/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<title>首頁</title>
</head>
<body>
	<%@ include file="/files/fixedNavbar.file"%>
	<section class="col-md-10">
	<h1>＜警告＞</h1>
	<hr />
	<strong>你沒有操作此網頁的權限。</strong>
	</section>
	<div style="clear: both;"></div>
	<footer id="the_footer"> Copyright 2015 Institute For
		Information Industry ZA101 Petribe | Private Policy </footer>
</body>
</html>