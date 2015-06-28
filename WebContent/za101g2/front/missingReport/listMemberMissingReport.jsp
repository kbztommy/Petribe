<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="za101g2.missingreport.model.*"%>

<%-- 取出 Controller MissingRecordServlet.java已存入request的missingRecordVO物件--%>
<% 
	Set<MissingReportVO> set = (LinkedHashSet<MissingReportVO>) request.getAttribute("missingReportVO");
	pageContext.setAttribute("set", set);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width initial-scale =1">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/mainStyle.css">    
    <script src="<%= request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
    <script src="<%= request.getContextPath()%>/js/bootstrap.min.js"></script>
<title>會員檢舉紀錄</title>
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>
<div class="col-md-8">
	<ul class="nav nav-pills  mynav-pills">
	  <li role="presentation">
		  <a href="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do?memId=${memberVO.id}&action=getMember_MissingRecord">協尋紀錄</a>
	  </li>
	  <li role="presentation"><a href="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do?id=${memberVO.id}&action=getMember_Pet_Id">刊登協尋</a></li>
	  <li role="presentation"><a href="<%= request.getContextPath() %>/za101g2/front/missingReply/missingReply.do?memId=${memberVO.id }&action=getMember_MissingReply">回報紀錄</a></li>
	  <li role="presentation" class="active"><a href="<%= request.getContextPath() %>/za101g2/front/missingReport/missingReport.do?memId=${memberVO.id}&action=getMember_MissingReport" >檢舉紀錄</a></li>
	</ul>
</div>
<div class="col-md-8">
<!-- 錯誤列表 -->
<c:if test="${not empty errorMsgs}">
	<font color='deeppink'>溫馨提醒:
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message.value}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>
</div>

<div class="col-md-6">
<h4><b>會員檢舉紀錄:</b></h4>
<table class="table table-hover">
	<thead>
		<tr class="info">
			<th>檢舉日期:</th>
			<th>檢舉會員:</th>
			<th>檢舉內容:</th>
			<th>檢舉原因:</th>
		</tr>
	</thead>
	<c:forEach var="missingReportVO" items="${set}">
		<tbody>
			<tr>
				<td>${missingReportVO.reportDate}</td>

				<td>${missingReportVO.missingReplyVO.memberVO.lastname} ${missingReportVO.missingReplyVO.memberVO.firstname}</td>
				<td><textarea name="comments" rows="4" cols="20" disabled>${missingReportVO.missingReplyVO.comments}</textarea></td>
				
				<td>${missingReportVO.comments}</td>
			</tr>
		</tbody>
	</c:forEach>
</table>
</div>

<%@ include file="/files/PetribeFoot.file" %>