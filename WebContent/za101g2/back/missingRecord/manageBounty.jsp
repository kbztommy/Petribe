<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="za101g2.missingrecord.model.*"%>
<%@ page import="za101g2.missingreply.model.*"%>

<%
	MissingRecordVO missingRecordVO = (MissingRecordVO) request.getAttribute("missingRecordVO");
	pageContext.setAttribute("missingRecordVO", missingRecordVO);
	MissingReplyVO missingReplyVO = (MissingReplyVO) request.getAttribute("missingReplyVO");
	pageContext.setAttribute("missingReplyVO", missingReplyVO);
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>發放賞金頁面</title>
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
<h4><b>協尋資訊:</b></h4>
<table class="table table-hover">
	<thead>
		<tr class="info">	
			<th>協尋狀態:</th>
			<th>寵物名字:</th>
			<th>照片:</th>	
			<th>走失地點:</th>
			<th>走失時間:</th>
			<th>賞金:</th>
			<th>說明:</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>${missingRecordVO.status}</td>
			<td>${missingRecordVO.petVO.name}</td>
			<td><img src = "<%= request.getContextPath() %>/MissingRecordGifReader?id=${missingRecordVO.id}" width="100px" height="75px"></td>
			<td>${missingRecordVO.location}</td>
			<td>${missingRecordVO.missingDate}</td>
			<td>${missingRecordVO.bounty}</td>
			<td><textarea name="comments" rows="3" cols="15" disabled>${missingRecordVO.comments}</textarea></td>		
		</tr>
	</tbody>
</table>
</div>		

<FORM method="post" action="<%= request.getContextPath() %>/za101g2/front/missingReply/missingReply.do" name="form1" role="form" class="col-md-6">
	<h4><b>回報者資訊:</b></h4>
	<div class="form-group">
		<label>回報者姓名:</label>
		<p class="form-control-static">${missingReplyVO.memberVO.lastname} ${missingReplyVO.memberVO.firstname}</p>
	</div>
	<div class="form-group">
		<label>回報者手機:</label>
		<p class="form-control-static">${missingReplyVO.memberVO.phone}</p>
	</div>
	<div class="form-group">
		<label>回報者e-mail:</label>
		<p class="form-control-static">${missingReplyVO.memberVO.email}</p>
	</div>
	<div class="form-group">
		<label>回報時間:</label>
		<p class="form-control-static">${missingReplyVO.reportDate}</p>
	</div>
	<div class="form-group">
		<label>回報內容:</label>
		<p class="form-control-static">${missingReplyVO.comments}</p>
	</div>
	<div class="form-group">
		<label>回報者帳戶資訊:</label>
		<p class="form-control-static">${managerReplyStatusMap[missingReplyVO.isRead]}</p>
	</div>
	
	<input type="hidden" name="action" value="payBounty">
	<input type="hidden" name="replyId" value="${missingReplyVO.id}">
	<input type="submit" value=${missingReplyVO.isRead!="w" ? "'無法發放' class='btn btn-danger' disabled":"'發放賞金' class='btn btn-warning'"}>	
</FORM>

</section>
	<div style="clear:both;">
		<footer id="the_footer">
			Copyright 2015 Institute For Information Industry ZA101 Petribe | Private Policy 
		</footer>
	</div>
</body>
</html>