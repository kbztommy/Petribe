<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="za101g2.missingrecord.model.*"%>

<%  
	List<MissingRecordVO> list = (ArrayList<MissingRecordVO>) request.getAttribute("missingRecordVO");
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
<title>搜尋協尋紀錄</title>
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

<FORM method="post" action="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do" name="form1" role="form" class="col-md-8">
	<div class="form-inline">
		<label>協尋狀態:</label>
		<select name="status" class="form-control">
			<option value="協尋中">協尋中</option>
			<option value="已找到" ${(param.status=='已找到') ? 'selected':''}>已找到</option>
			<option value="下架" ${(param.status=='下架') ? 'selected':''}>下架</option>
			<option value="已發放" ${(param.status=='已發放') ? 'selected':''}>已發放</option>
		</select>
			
		<label>有無賞金:</label>
		<select size="1" name="bounty" class="form-control">
			<option value="allBounty">--請選擇有無賞金--</option>
			<option value="bounty" ${(param.bounty=='bounty') ? 'selected':''}>有賞金</option>
			<option value="noBounty" ${(param.bounty=='noBounty') ? 'selected':''}>無賞金</option>
		</select>
		
		<input type="hidden" name="action" value="manageAll_Status">
		<input type="submit" value="搜尋" class="btn btn-warning">	
	</div>
</FORM>
<br>
<br>
<br>

<div class="col-md-12">
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
			<th>發放賞金:</th>
		</tr>
	</thead>
	<%@ include file="pages/page1.file" %>
	<c:forEach var="missingRecordVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tbody>
			<tr>
				<td>${missingRecordVO.status}</td>			
				<td>${missingRecordVO.petVO.name}</td>			
				<td><img src = "<%= request.getContextPath() %>/MissingRecordGifReader?id=${missingRecordVO.id}" width="100px" height="75px"></td>				
				<td>${missingRecordVO.location}</td>
				<td>${missingRecordVO.missingDate}</td>
				<td>${missingRecordVO.bounty}</td>
				<td><textarea name="comments" rows="3" cols="15" disabled>${missingRecordVO.comments}</textarea></td>
					
				<td>
					<FORM method="post" action="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do">
						<input type="hidden" name="recordId"  value="${missingRecordVO.id}">
					<c:if test="${missingRecordVO.status=='已發放'}">	
						<input type='button' value='賞金已發放' class='btn btn-success' disabled>
					</c:if>
					<c:if test="${missingRecordVO.status!='已發放'}">		
						<input type="submit" value=${(missingRecordVO.status!="已找到" || missingRecordVO.bounty==0) ? "'無法發放' class='btn btn-danger' disabled":"'發放賞金' class='btn btn-warning'"}>
					</c:if>
						<input type="hidden" name="action" value="manageBounty_Information">	
					</FORM>
				</td>
			</tr>
		</tbody>
	</c:forEach>
</table>
<%@ include file="pages/page2.file" %>
</div>

</section>
	<div style="clear:both;">
		<footer id="the_footer">
			Copyright 2015 Institute For Information Industry ZA101 Petribe | Private Policy 
		</footer>
	</div>
</body>
</html>