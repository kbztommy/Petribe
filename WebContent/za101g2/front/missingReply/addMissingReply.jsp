<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="za101g2.missingreply.model.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width initial-scale =1">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/mainStyle.css">    
    <script src="<%= request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
    <script src="<%= request.getContextPath()%>/js/bootstrap.min.js"></script>
<title>協尋回報</title>
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>

<div class="row">
    <div class="col-md-8">
		<ol class="breadcrumb myBreadCrumb">
		    <li><a href="<%=request.getContextPath() %>/za101g2/front/missingRecord/listAllMissingRecord.jsp">全部協尋紀錄</a></li>
		    <li><a href="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do?action=getOne_For_Display&recordId=${param.recordId}">單筆協尋資訊</a></li>			
		    <li class="active">協尋回報</li>
		</ol>
	</div>
</div>   

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font color='deeppink'>溫馨提醒:
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message.value}</li>
		</c:forEach> 
	</ul>
	</font>
</c:if>

<FORM method="post" action="<%= request.getContextPath() %>/za101g2/front/missingReply/missingReply.do" name="form1" role="form" class="col-md-4">
	<div class="form-group ${(errorMsgs.comments!=null) ? 'has-error':''}">
		<label for="comments">回報內容:<font color="red"><b>*</b></font></label>
		<textarea name="comments" rows="5" class="form-control" id="comments" placeholder="請填寫回報內容">${param.comments}</textarea>
	</div>

	<input type="hidden" name="action" value="insert">
	<input type="hidden" name="memId" value="${memberVO.id}">	
	<input type="hidden" name="recordId" value="${(param.recordId==null) ? requestScope.aRecordId:param.recordId}">
	<input type="hidden" name="aRecordId" value="${param.recordId}">
	<input type="submit" value="回報" class="btn btn-info">
</FORM>

<%@ include file="/files/PetribeFoot.file" %>