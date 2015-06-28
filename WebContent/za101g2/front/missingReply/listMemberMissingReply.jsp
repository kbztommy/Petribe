<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="za101g2.missingreply.model.*"%>

<% 
	Set<MissingReplyVO> set = (LinkedHashSet<MissingReplyVO>) request.getAttribute("missingReplyVO");
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
<!--for account-->	
	<script src="js/account.js"></script>    
<title>會員回報紀錄</title>
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>
<div class="col-md-8">
	<ul class="nav nav-pills  mynav-pills">
	  <li role="presentation">
		  <a href="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do?memId=${memberVO.id}&action=getMember_MissingRecord">協尋紀錄</a>
	  </li>
	  <li role="presentation"><a href="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do?id=${memberVO.id}&action=getMember_Pet_Id">刊登協尋</a></li>
	  <li role="presentation"  class="active"><a href="<%= request.getContextPath() %>/za101g2/front/missingReply/missingReply.do?memId=${memberVO.id }&action=getMember_MissingReply">回報紀錄</a></li>
	  <li role="presentation"><a href="<%= request.getContextPath() %>/za101g2/front/missingReport/missingReport.do?memId=${memberVO.id}&action=getMember_MissingReport">檢舉紀錄</a></li>
	</ul>
</div>
<div class="col-md-8">
<!-- 錯誤列表 -->
<c:if test="${not empty errorMsgs}">
	<font color='deeppink'>溫馨提醒:
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message.value}${(errorMsgs.emptyRecord!=null)? "<button onclick='history.back()' class='btn btn-info'>返回上一頁</button>":""}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>
</div>

<div class="col-md-8">
<h4><b>會員回報紀錄:</b></h4>
<table class="table table-hover">
	<thead>
		<tr class="info">
			<th>回報日期:</th>
			<th>找到的寵物:</th>
			<th>寵物照片:</th>
			<th>走失地點:</th>
			<th>協尋狀態:</th>			
			<th>賞金:</th>			
			<th>回報狀態:</th>			
			<th>領取賞金:</th>			
		</tr>
	</thead>
	<c:forEach var="missingReplyVO" items="${set}">
		<tbody>
			<tr>
				<td>${missingReplyVO.reportDate}</td>
				<td>${missingReplyVO.missingRecordVO.petVO.name}</td>
				
				<td><img src="<%= request.getContextPath()%>/MissingRecordGifReader?id=${missingReplyVO.missingRecordVO.id}" width="140px" height="105px"></td>
				
				<td>${missingReplyVO.missingRecordVO.location}</td>
				<td>${missingReplyVO.missingRecordVO.status}</td>
				<td>${missingReplyVO.missingRecordVO.bounty}</td>
				<td>${replyStatusMap[missingReplyVO.isRead]}</td>
				<td>
					<FORM method="post" action="<%= request.getContextPath() %>/za101g2/front/missingReply/missingReply.do">
						<input type="hidden" name="memId" value="${memId}">
						<input type="hidden" name="replyId" value="${missingReplyVO.id}">
						<input type="submit" class="btn btn-info" value=${(missingReplyVO.isRead=='y' && missingReplyVO.missingRecordVO.bounty>0) ? '領取賞金':'無法使用 disabled'}>
						<input type="hidden" name="action" value="bankInformation">
					</FORM>
				</td>			
			</tr>	
		</tbody>
	</c:forEach>
	
</table>
</div>

<%if (request.getAttribute("replyVO")!=null){%>
		<jsp:include page="bankInformation.jsp"/>
<%} %>	

<%@ include file="/files/PetribeFoot.file" %>

