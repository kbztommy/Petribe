<%@page import="za101g2.staff.model.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width initial-scale =1">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/normalize.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/backHome.css">
<script src="http://code.jquery.com/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
</head>
<body>
	<%@ include file="/files/fixedNavbar.file"%>
	<section class="col-md-10">

	<div class="form-horizontal col-md-offset-1">
		<div class="form-group">
			<h3 class="col-md-offset-5">
				<b>會員管理</b>
			</h3>
		</div>

		<div class="row">
			<form method="post"
				action="<%=request.getContextPath()%>/za101g2/front/member/member.do">
				<div class="col-lg-6 col-md-offset-3">
					<div class="input-group">
						<input type="text" name="search" placeholder="請輸入會員的電子信箱……"
							class="form-control"> <span class="input-group-btn">
							<button type="submit" class="btn btn-default" type="button">搜尋</button>
							<input type="hidden" name="action" value="search_member">
						</span>
					</div>
				</div>
			</form>
		</div>
		<div class="row col-md-12">
			<hr />
		</div>
		<table class="table table-hover table-striped">
			<tr>
				<th class="text-center col-lg-1">會員編號</th>
				<th class="text-center col-lg-2">電子信箱</th>
				<th class="text-center col-lg-1">密碼</th>
				<th class="text-center col-lg-2">暱稱</th>
				<th class="text-center col-lg-3">等級</th>
				<th class="text-center col-lg-1">狀態修改</th>
			</tr>
			<c:if test="${empty hasSearched}">
			<tr><td class="text-center" colspan="6">請輸入電子信箱查詢。</td></tr>
			</c:if>
			<c:if test="${not empty hasSearched}">
			<c:if test="${empty searchedList}">
			<tr><td class="text-center text-danger" colspan="6">查無此電子信箱。</td></tr>
			</c:if>
			<c:forEach var="memberVO" items="${searchedList}">
				<tr>
					<td class="text-center">${memberVO.id}</td>
					<td class="text-center">${memberVO.email}</td>
					<td class="text-center">****************</td>
					<td class="text-center">${memberVO.nickname}</td>
					<td class="text-center">${memberStatus[memberVO.status]}</td>
					<td class="text-center">
					<form method="post"
				action="<%=request.getContextPath()%>/za101g2/front/member/member.do">
					<input type="submit" value="${memberStatus2Way[memberVO.status]}" class="form-control btn ${memberStatus2WayClass[memberVO.status]}">
					<input type="hidden" name="action" value="update_status">
					<input type="hidden" name="onesmemberid" value="${memberVO.id}">
					</form>
					</td>
				</tr>
			</c:forEach>
			</c:if>
		</table>
	</div>


	</section>
	<div style="clear: both;">
		<footer id="the_footer"> Copyright 2015 Institute For
		Information Industry ZA101 Petribe | Private Policy </footer>
	</div>
</body>
</html>