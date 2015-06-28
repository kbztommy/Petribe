<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="za101g2.missingreport.model.*"%>

<%  
	MissingReportService missingReportSvc = new MissingReportService();
	List<MissingReportVO> list = missingReportSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="viewport" content="width=device-width initial-scale =1">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/normalize.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/backHome.css">
   	<script src="http://code.jquery.com/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<title>管理檢舉首頁</title>
</head>
<body>
<%@ include file="/files/fixedNavbar.file" %>
<section class="col-md-10">

<!-- 錯誤列表 -->
<c:if test="${not empty errorMsgs}">
	<font color='tomato'>系統訊息:
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message.value}</li>
		</c:forEach>
	</ul>	
	</font>
</c:if>
<br>

<div class="col-md-12">
<table class="table table-hover">
	<thead>
		<tr class="info">
			<th>檢舉日期:</th>
			<th>申請檢舉會員:</th>
			<th>檢舉原因:</th>
			<th>被檢舉會員:</th>
			<th>被檢舉留言:</th>
			<th>檢舉狀態:</th>		
			<th>取消檢舉:</th>		
			<th>封鎖會員:</th>		
		</tr>
	</thead>
	<c:forEach var="missingReportVO" items="${list}">
		<tbody>
			<tr>
				<td>${missingReportVO.reportDate}</td>
				<td>${missingReportVO.memberVO.lastname} ${missingReportVO.memberVO.firstname}</td>
				<td>${missingReportVO.comments}</td>
				<td>${missingReportVO.missingReplyVO.memberVO.lastname} ${missingReportVO.missingReplyVO.memberVO.firstname}</td>
				<td>${missingReportVO.missingReplyVO.comments}</td>
				<td>${reportStatusMap[missingReportVO.missingReplyVO.isRead]}</td>
				<td>
					<FORM method="post" action="<%= request.getContextPath() %>/za101g2/front/missingReport/missingReport.do">
						<input type="hidden" name="memId" value="${missingReportVO.memberVO.id}">
						<input type="hidden" name="replyId" value="${missingReportVO.missingReplyVO.id}">
						<input type="submit" class="btn btn-success" value="取消檢舉">
						<input type="hidden" name="action" value="manageCancelReport">
					</FORM>
				</td>
				<td>
					<FORM method="post" action="<%= request.getContextPath() %>/za101g2/front/missingReport/missingReport.do">
						<input type="hidden" name="replyId" value="${missingReportVO.missingReplyVO.id}">
						<input type="submit" value=${missingReportVO.missingReplyVO.isRead!="r" ? "'已封鎖' class='btn btn-danger' disabled":"'封鎖會員' class='btn btn-danger'"}>
						<input type="hidden" name="action" value="manageDenyMember">
					</FORM>
				</td>
			</tr>
		</tbody>
	</c:forEach>		
</table>
</div>	

</section>
	<div style="clear:both;">
		<footer id="the_footer">
			Copyright 2015 Institute For Information Industry ZA101 Petribe | Private Policy 
		</footer>
	</div>
</body>
</html>