<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List"%>
<%@ page import="za101g2.missingrecord.model.*"%>
<%-- 此頁採用 JSTL 與 EL 取值 --%>

<%  
	MissingRecordService missingRecordSvc = new MissingRecordService();
	List<MissingRecordVO> list = missingRecordSvc.getAll();
	pageContext.setAttribute("list", list);
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
<title>全部協尋紀錄</title>
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>

<div class="row">
    <div class="col-md-8">
		<ol class="breadcrumb myBreadCrumb">
		  <li class="active">全部協尋紀錄</li>
		</ol>
	</div>
</div>   


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

<FORM method="post" action="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do" name="form1" role="form" class="col-md-6">
	<div class="form-inline">
		<label>走失地點:</label>
		<select size="1" name="city" class="form-control">
			<option value="allCity">--請選擇縣市--</option>
			<c:forEach var="city" items="${applicationScope.cityList}">
				<option value="${city}">${city}</option>
			</c:forEach>
		</select>
		
		<label>有無賞金:</label>
		<select name="bounty" class="form-control">
			<option value="allBounty">--請選擇有無賞金--</option>
			<option value="bounty">有賞金</option>
			<option value="noBounty">無賞金</option>
		</select>
		
		<input type="hidden" name="action" value="getAll_Search">
		<input type="submit" value="搜尋" class="btn btn-info">	
	</div>	
</FORM>
<br>
<br>

<div class="col-md-8">
<table class="table table-hover">
	<thead>
		<tr class="info">
			<th>寵物名字:</th>
			<th>照片:</th>	
			<th>走失地點:</th>
			<th>走失時間:</th>
			<th>賞金:</th>
			<th>說明:</th>
			<th>內容:</th>
		</tr>
	</thead>
	<c:forEach var="missingRecordVO" items="${list}">
	<c:if test="${missingRecordVO.status=='協尋中'}">
		<tbody>
			<tr>
				<td>${missingRecordVO.petVO.name}</td>			
				<td><img src = "<%= request.getContextPath() %>/MissingRecordGifReader?id=${missingRecordVO.id}" width="140px" height="105px"></td>			
				<td>${missingRecordVO.location}</td>
				<td>${missingRecordVO.missingDate}</td>
				<td>${missingRecordVO.bounty}</td>
				<td><textarea name="comments" rows="4" cols="20" disabled>${missingRecordVO.comments}</textarea></td>
					
				<td>
					<FORM method="post" action="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do">
						<input type="hidden" name="recordId"  value="${missingRecordVO.id}">
						<input type="submit" value="內容" class="btn btn-info">
						<input type="hidden" name="action" value="getOne_For_Display">	
					</FORM>
				</td>
			</tr>
		</tbody>
	</c:if>	
	</c:forEach>
</table>
</div>

<%@ include file="/files/PetribeFoot.file" %>
