<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="za101g2.announcement.model.*"%>
<%@ page import="za101g2.staff.model.*"%>
<%
	AnnouncementVO announcementVO = (AnnouncementVO) request.getAttribute("announcementVO");
%>
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
<title>新增公告</title>
</head>
<body>
	<%@ include file="/files/fixedNavbar.file"%>
	<section class="col-md-10">
		<div class="col-md-12"
			style="margin-top: 10px; text-align: center; font: bold 28px Tahoma, Arial, Verdana; color: #0297EC">新增公告</div>
		<%-- 錯誤表列 --%>
		<c:if test="${not empty errorMsgs}">
			<font color='red'>請修正以下錯誤:
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li>${message}</li>
					</c:forEach>
				</ul>
			</font>
		</c:if>

		<form METHOD="post"
			ACTION="<%=request.getContextPath()%>/announcement/announcement.do"
			name="form1" class="form-horizontal" role="form"
			style="font: normal 20px Tahoma, Arial, Verdana;">
			<div class="form-group">
				<label for="inputTitle" class="col-md-1 control-label">標題:</label>
				<div class="col-md-11" style="margin-top: 10px;">
					<input type="TEXT" name="title" class="form-control"
						id="inputTitle" placeholder="輸入標題"
						style="font: normal 20px Tahoma, Arial, Verdana;"
						value="<%= (announcementVO==null)? "" : announcementVO.getTitle()%>">
				</div>
			</div>
			<div class="form-group">
				<label for="inputTextarea" class="col-md-1 control-label">說明:</label>
				<div class="col-md-11" style="margin-top: 10px;">
					<textarea name="comments" class="form-control" id="inputTextarea"
						placeholder="輸入說明" rows="25"
						style="font: normal 20px Tahoma, Arial, Verdana;"
						value="<%= (announcementVO==null)? "" : announcementVO.getComments()%>"></textarea>
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-offset-1 col-md-11">
					<input type="hidden" name="action" value="insert">
					<button type="submit" class="btn btn-default"
						style="font: normal 18px Tahoma, Arial, Verdana;">送出新增</button>
				</div>
			</div>
		</form>

	</section>
	<div style="clear: both;"></div>
	<footer id="the_footer"> Copyright 2015 Institute For
		Information Industry ZA101 Petribe | Private Policy </footer>
	</div>
</body>
</html>