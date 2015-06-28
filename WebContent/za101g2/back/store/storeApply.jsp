<%@page import="za101g2.store.model.StoreService"%>
<%@page import="za101g2.store.model.StoreVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import ="java.util.*" %> 

<%  
	StoreService srv = new StoreService();
	List<StoreVO> list = srv.getApplyAll();
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
</head>
<body>
<%@ include file="/files/fixedNavbar.file" %>
<section class="col-md-10">

<div class="form-horizontal col-md-offset-1">
<div class="form-group">
<h3 class="col-md-offset-5"><b>商家審核</b></h3>
</div>
<table class="table table-hover table-striped">
<tr>
<th class="text-center">商店名稱</th>
<th class="text-center">地址</th>
<th class="text-center">老闆</th>
<th class="text-center">申請日期</th>
<th class="text-center">審核狀況</th>
</tr>
<c:forEach var="storeVO" items="${list}">
<tr>
<td class="text-center">${storeVO.name}</td>
<td class="text-center">${storeVO.address}</td>
<td class="text-center">${storeVO.memberVO.lastname}&nbsp;${storeVO.memberVO.firstname}</td>
<td class="text-center">${storeVO.applyDate.toLocaleString()}</td>
<td>
<form  method="post" action="<%=request.getContextPath()%>/StoreServlet">
<input type="submit" value="${storeApply[storeVO.status]}" class="btn ${storeApplyButton[storeVO.status]} col-md-12">
<input type="hidden" name="onesstoreid" value="${storeVO.id}">
<input type="hidden" name="action" value="get_a_apply">
</form>
</td>
</tr>
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