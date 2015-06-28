<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="za101g2.announcement.model.*"%>
<%@ page import="za101g2.staff.model.*"%>
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
<title>公告瀏覽</title>
</head>
<body>
	<%@ include file="/files/fixedNavbar.file"%>
	<section class="col-md-10">

		<%AnnouncementVO announcementVO = (AnnouncementVO) request.getAttribute("announcementVO");%>
		<%
	StaffService staffSvc = new StaffService();
	StaffVO staffVO = staffSvc.getOneStaff(announcementVO.getStaffId());
%>
		<div class="col-md-12"
			style="margin-top: 10px; text-align: center; font: bold 26px Tahoma, Arial, Verdana; color: #0297EC">Petribe後端系統佈告欄</div>
		<table class="col-md-12"
			style="background-color: #fff; margin-top: 10px; margin-left: 10px; border: 2px solid #e6e6e6;">
			<tr
				style="border: 2px solid #e6e6e6; height: 50px; font: bold 24px Tahoma, Arial, Verdana;">
				<th style="text-align: center;"><%=announcementVO.getTitle()%></th>
			</tr>
			<tr
				style="border: 2px solid #e6e6e6; height: 600px; font: normal 22px Tahoma, Arial, Verdana;">
				<td valign="top" style="padding: 10px;"><%=announcementVO.getComments()%></td>
			</tr>
			<tr>
				<td align="right"
					style="padding-top: 10px; font: normal 16px Tahoma, Arial, Verdana;">管理員
					<%=staffVO.getName()%> 於<%=announcementVO.getReleaseDate()%>發佈
				</td>
			</tr>
		</table>

	</section>
	<div style="clear: both;"></div>
	<footer id="the_footer"> Copyright 2015 Institute For
		Information Industry ZA101 Petribe | Private Policy </footer>
	</div>
</body>
</html>