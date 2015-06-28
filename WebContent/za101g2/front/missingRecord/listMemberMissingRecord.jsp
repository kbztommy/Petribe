<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="za101g2.missingrecord.model.*"%>

<%-- 取出 Controller MissingRecordServlet.java已存入request的missingRecordVO物件--%>
<%
	List<MissingRecordVO> list = (ArrayList<MissingRecordVO>) request.getAttribute("missingRecordVO");
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
<title>會員協尋紀錄</title>
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>
<div class="row">
<div class="col-md-8">
	<ul class="nav nav-pills  mynav-pills">
	  <li role="presentation" class="active">
		  <a href="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do?memId=${memberVO.id}&action=getMember_MissingRecord">協尋紀錄</a>
	  </li>
	  <li role="presentation"><a href="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do?id=${memberVO.id}&action=getMember_Pet_Id">刊登協尋</a></li>
	  <li role="presentation"><a href="<%= request.getContextPath() %>/za101g2/front/missingReply/missingReply.do?memId=${memberVO.id }&action=getMember_MissingReply">回報紀錄</a></li>
	  <li role="presentation"><a href="<%= request.getContextPath() %>/za101g2/front/missingReport/missingReport.do?memId=${memberVO.id}&action=getMember_MissingReport">檢舉紀錄</a></li>
	</ul>
</div>
</div>
<!-- 錯誤列表 -->
<c:if test="${not empty errorMsgs}">
<div class="row">
<div class="col-md-8">
	<font color='deeppink'>溫馨提醒:
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message.value}</li>
		</c:forEach>
	</ul>
	</font>
</div>
</div>
</c:if>

<div class="row">
	<div class="col-md-8">
	
		<div class="panel panel-primary">
			<div class="panel-heading"><b>會員協尋紀錄:</b></div>
			<div class="panel-body">
				<table class="table table-hover">
					<thead>
						<tr class="info">
							<th>寵物名字:</th>
							<th>照片:</th>	
							
							<th>走失時間:</th>
							<th>賞金:</th>
							
							<th>狀態:</th>
							<th>回報數:</th>
							<th>查詢回報:</th>
							<th>修改資訊:</th>
							<th>取消協尋:</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="missingRecordVO" items="${list}">
					
							<tr align='center' valign='middle'>
								<td>${missingRecordVO.petVO.name}</td>
								
								<td><img src="<%= request.getContextPath()%>/MissingRecordGifReader?id=${missingRecordVO.id}" width="140px" height="105px"></td>
								
								<td>${missingRecordVO.missingDate}</td>
								<td>${missingRecordVO.bounty}</td>
								<td>${missingRecordVO.status}</td>
								<td>${missingRecordVO.bountyFor}</td>
								<td>
									<FORM method="post" action="<%=request.getContextPath()%>/za101g2/front/missingRecord/missingRecord.do">
										<input type="submit" class="btn btn-info" value=${(missingRecordVO.status!="協尋中")? "查詢 disabled":"查詢"}>
										<input type="hidden" name="memId" value="${memId}">
										<input type="hidden" name="recordId" value="${missingRecordVO.id}">
										<input type="hidden" name="action" value="listMemberMissingReplies">
									</FORM>
								</td>
								
				<!-- 			修改協尋資訊 -->
								<td>
									<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/za101g2/front/missingRecord/missingRecord.do">
										<input type="submit" class="btn btn-info" value=${(missingRecordVO.status!="協尋中")? "修改 disabled":"修改"}>
										<input type="hidden" name="id" value="${missingRecordVO.id}">
										<input type="hidden" name="location" value="${missingRecordVO.location}">
										<input type="hidden" name="missingDate" value="${missingRecordVO.missingDate}">
										<input type="hidden" name="bounty" value="${missingRecordVO.bounty}">
										<input type="hidden" name="comments" value="${missingRecordVO.comments}">
										<input type="hidden" name="petId" value="${missingRecordVO.petVO.id}">
										<input type="hidden" name="memId" value="${memId}">
										<input type="hidden" name="action" value="getOne_For_Update">
									</FORM>
								</td>
				
								<td>
									<FORM method="post" action="<%=request.getContextPath()%>/za101g2/front/missingRecord/missingRecord.do">
										<input type="submit" class="btn btn-info" value=${(missingRecordVO.status!="協尋中")? "取消 disabled":"取消"}>
										<input type="hidden" name="memId" value="${memId}">
										<input type="hidden" name="recordId" value="${missingRecordVO.id}">
										<input type="hidden" name="action" value="cancelMissingRecord">
									</FORM>
								</td>
							</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		
		</div>
	
	</div>
</div>
<%if (request.getAttribute("listMemberMissingReplies")!=null){%>
		<jsp:include page="listMemberMissingReplies.jsp"/>
<%} %>

<%@ include file="/files/PetribeFoot.file" %>