<%@page import="za101g2.accesses.model.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import ="java.util.*" %> 

<%
    AccessesService accessesSvc = new AccessesService();
    List<AccessesVO> accessesList = accessesSvc.getAll();
    pageContext.setAttribute("accessesList",accessesList);
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
</head>
<body>
<%@ include file="/files/fixedNavbar.file" %>
<section class="col-md-10">
<div class="form-horizontal col-md-offset-1">
<div class="form-group">
<h3 class="col-md-offset-5"><b>員工管理</b></h3>
</div>
<div class="panel panel-default">
<div class="panel-heading">
<strong>員工[${onesStaffVO.name}]目前的狀況：</strong>
<button class="btn ${staffStatusButton[onesStaffVO.status]}">${staffStatus[onesStaffVO.status]}</button>
</div>
<div class="panel-body">
<form method="post" action="<%=request.getContextPath()%>/za101g2/back/staff/staff.do">
<input type="submit" value="修改${staffStatus['0']}" class="btn ${staffStatusButton['0']} col-md-4 col-md-offset-1 ${onesStaffVO.status=='0'?'hidden':''}">
<input type="hidden" name="status" value="0">
<input type="hidden" name="onesStaffId" value="${onesStaffVO.id}">
<input type="hidden" name="action" value="update_status">
</form>
<c:if test="${onesStaffVO.status eq 2}"><div class="col-md-1"></div></c:if>
<form method="post" action="<%=request.getContextPath()%>/za101g2/back/staff/staff.do">
<input type="submit" value="修改${staffStatus['1']}" class="btn ${staffStatusButton['1']} col-md-4 col-md-offset-1 ${onesStaffVO.status=='1'?'hidden':''}">
<input type="hidden" name="status" value="1">
<input type="hidden" name="onesStaffId" value="${onesStaffVO.id}">
<input type="hidden" name="action" value="update_status">
</form>
<c:if test="${onesStaffVO.status eq 1 or onesStaffVO.status eq 0}"><div class="col-md-1"></div></c:if>
<form method="post" action="<%=request.getContextPath()%>/za101g2/back/staff/staff.do">
<input type="submit" value="修改${staffStatus['2']}" class="btn ${staffStatusButton['2']} col-md-4 col-md-offset-1 ${onesStaffVO.status=='2'?'hidden':''}">
<input type="hidden" name="status" value="2">
<input type="hidden" name="onesStaffId" value="${onesStaffVO.id}">
<input type="hidden" name="action" value="update_status">
</form>
</div>
</div>
</div>
	<form method="post" action="<%=request.getContextPath()%>/za101g2/back/staff/staffAccesses.do" class="col-md-offset-1">
	<table class="table table-hover table-striped">
	<tr><th>員工[${onesStaffVO.name}]目前擁有以下權限：</th></tr>
	<c:forEach var="accessesVO" items="${accessesList}">
	<tr>
	<td>
	<input type="checkbox" name="accesses" value="${accessesVO.id}" ${onesAccessesList.contains(accessesVO.id)? "checked" : ""}>${accessesVO.name}<br>
	</td>
	</tr>
	</c:forEach>
	<tr>
	</table>
	<input type="hidden" name="action" value="updateAccesses">
	<input type="hidden" name="staffId" value="${onesStaffVO.id}">
	<input type="submit" value="更新權限" class="btn btn-primary">
	<c:if test="${not empty updateTime}">
	<div class="form-group alert alert-success media" role="alert"><strong>你已於 ${updateTime.toLocaleString()} 進行更新。</strong></div>
	</c:if>
	</form>

</section>
	<div style="clear:both;">
		<footer id="the_footer">
			Copyright 2015 Institute For Information Industry ZA101 Petribe | Private Policy 
		</footer>
	</div>
</body>
</html>