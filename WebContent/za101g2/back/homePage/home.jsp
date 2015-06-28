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
		<%StaffVO staffVO = (StaffVO)session.getAttribute("staffVO");%>
		<header class="col-md-12" id="content_header">
			<h1>
				您好管理員：<b><%= staffVO.getName() %></b>，歡迎登入Petribe後端管理系統。
			</h1>
		</header>
		<%
		AnnouncementService announcementSvc = new AnnouncementService();
    	List<AnnouncementVO> list = announcementSvc.getAll();
    	pageContext.setAttribute("list",list);
	%>
		<div class="col-md-12" id="announcement_wrapper">
			<div class="col-md-12" id="announcement_title">Petribe後端系統佈告欄</div>
			<%@ include file="pages/page1.file"%>
			<table class="col-md-11" id="announcement_table">
				<tr id="table_title">
					<th class="col-md-2" style="text-align: center;">公告日期</th>
					<th class="col-md-5" style="text-align: center;">標 題</th>
					<th class="col-md-2" style="text-align: center;">發佈員工</th>
				</tr>
				<c:forEach var="announcementVO" items="${list}"
					begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
					<tr id="table_content">
						<td style="text-align: center;">${announcementVO.releaseDate}</td>
						<td><a
							href="<%=request.getContextPath()%>/announcement/announcement.do?action=getOne_For_Display&id=${announcementVO.id}">${announcementVO.title}</a></td>
						<td style="text-align: center;"><jsp:useBean id="staffSvc"
								scope="page" class="za101g2.staff.model.StaffService" /> <c:forEach
								var="staffVO" items="${staffSvc.all}">
								<c:if test="${announcementVO.staffId==staffVO.id}">
	                    ${staffVO.name}
                    </c:if>
							</c:forEach></td>
					</tr>
				</c:forEach>
			</table>
			<%@ include file="pages/page2.file"%>
		</div>
	</section>
	<div style="clear: both;"></div>
	<footer id="the_footer"> Copyright 2015 Institute For
		Information Industry ZA101 Petribe | Private Policy </footer>
</body>
</html>