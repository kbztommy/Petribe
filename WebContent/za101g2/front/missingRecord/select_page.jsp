<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="za101g2.missingrecord.model.*"%>
<%@ page import="za101g2.pet.model.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width initial-scale =1">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/mainStyle.css">   
    <script src="<%= request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
    <script src="<%= request.getContextPath()%>/js/bootstrap.min.js"></script>
	<title>測試首頁</title>
</head>
<body>
<%@ include file="/files/CalendarHead.file" %>

<!-- 協尋功能buttons -->
<div>
	<a href="<%= request.getContextPath() %>/za101g2/front/missingRecord/listAllMissingRecord.jsp" class="btn btn-info" role="button">協尋名單</a>
	<a href="<%= request.getContextPath() %>/za101g2/front/missingRecord/addMissingRecord.jsp" class="btn btn-info" role="button">刊登協尋</a>
	<a href="<%= request.getContextPath() %>/za101g2/front/missingRecord/select_page.jsp" class="btn btn-info" role="button">管理我的協尋</a>
</div>
<br>

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

<!-- 送出會員id給listMemberMissingRecord.jsp(會員的管理協尋頁面) -->
<h3>送出會員id給(會員的管理協尋頁面):</h3>
<FORM method="post" action="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do">
	<input type="text" name="memId">
	<input type="submit" value="送出">
	<input type="hidden" name="action" value="getMember_MissingRecord">
</FORM>

<br>
<!-- 送出會員id給addMissingRecord.jsp(會員刊登協尋) -->
<h3>送出會員id給(會員刊登協尋):</h3>
<FORM method="post" action="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do">
	<input type="text" name="id">
	<input type="submit" value="送出">
	<input type="hidden" name="action" value="getMember_Pet_Id">
</FORM>

<br>
<!-- 送出會員id給listMemberMissingReport.jsp(會員的檢舉頁面) -->
<h3>送出會員id給(會員的檢舉頁面):</h3>
<FORM method="post" action="<%= request.getContextPath() %>/za101g2/front/missingReport/missingReport.do">
	<input type="text" name="memId">
	<input type="submit" value="送出">
	<input type="hidden" name="action" value="getMember_MissingReport">
</FORM>

<br>
<!-- 送出會員id給listMemberMissingReply.jsp(會員的回報頁面) -->
<h3>送出會員id給(會員的回報頁面):</h3>
<FORM method="post" action="<%= request.getContextPath() %>/za101g2/front/missingReply/missingReply.do">
	<input type="text" name="memId">
	<input type="submit" value="送出">
	<input type="hidden" name="action" value="getMember_MissingReply">
</FORM>

<br>
<!-- 查詢一筆協尋 -->
<h3>單筆協尋紀錄:</h3>
<FORM method="post" action="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do">
	<input type="text" name="recordId">
	<input type="submit" value="送出">
	<input type="hidden" name="action" value="getOne_For_Display">	
</FORM>

<%@ include file="/files/CalenderFoot.file" %>
</body>
</html>