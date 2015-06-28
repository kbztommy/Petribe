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
				<b>員工管理</b>
			</h3>
		</div>
		<div class="row">
			<form METHOD="post"
				ACTION="<%=request.getContextPath()%>/za101g2/back/staff/staff.do"
				name="memberRegister"
				class="form-horizontal col-md-12 btn btn-primary">
				<div class="col-md-4">
					<input type="text" name="email" id="email"
						value="${tempStaffVO.email}" class="form-control"
						placeholder="E-mail" />
				</div>
				<div class="col-md-3">
					<input type="text" name="name" id="name"
						value="${tempStaffVO.name}" class="form-control" placeholder="名字" />
				</div>
				<div class="col-md-3">
					<select class="form-control" name="status">
						<option value="-1">選擇員工狀態</option>
						<option value="0" ${tempStaffVO.status=="0"? "selected":""}>${staffStatus['0']}</option>
						<option value="1" ${tempStaffVO.status=="1"? "selected":""}>${staffStatus['1']}</option>
						<option value="2" ${tempStaffVO.status=="2"? "selected":""}>${staffStatus['2']}</option>
					</select>
				</div>
				<div class="col-md-2">
					<input type="hidden" name="action" value="add_new_staff"> <input
						type="submit" value="新增員工" class="btn btn-default">
				</div>
			</form>
		</div>
		<div class="row">
			<div class="form-horizontal col-md-12">
				<div class="col-md-4 text-danger">
					<strong>${emailError}</strong>
				</div>
				<div class="col-md-3 text-danger">
					<strong>${nameError}</strong>
				</div>
				<div class="col-md-3 text-danger">
					<strong>${statusError}</strong>
				</div>
				<div class="col-md-2 text-success text-center">
					<strong>${updateSuccess}</strong>
				</div>
				<div class="panel"></div>
			</div>
		</div>
		<div class="row col-md-12">
			<hr />
		</div>
		<div class="row">
			<form method="post"
				action="<%=request.getContextPath()%>/za101g2/back/staff/staff.do">
				<div class="col-lg-6 col-md-offset-3">
					<div class="input-group">
						<input type="text" name="search" placeholder="輸入關鍵字搜尋員工……"
							class="form-control"> <span class="input-group-btn">
							<button type="submit" class="btn btn-default" type="button">搜尋</button>
							<input type="hidden" name="action" value="search_staff">
						</span>
					</div>
				</div>
			</form>
		</div>
		<div class="media"></div>
		<table class="table table-hover table-striped">
			<tr>
				<th class="text-center">員工編號</th>
				<th class="text-center">電子信箱</th>
				<th class="text-center">密碼</th>
				<th class="text-center">名字</th>
				<th class="text-center">修改權限</th>
				<th class="text-center">狀態</th>
			</tr>
			<c:if test="${empty hasSearched}">
			<tr><td class="text-center" colspan="6">請輸入任關鍵字查詢。</td></tr>
			</c:if>
			<c:if test="${not empty hasSearched}">
			<c:if test="${empty searchedList}">
				<tr>
					<td class="text-center" colspan="6">查無此會員。</td>
				</tr>
			</c:if>
			<c:forEach var="staffVO" items="${searchedList}">
				<tr>
					<td class="text-center">${staffVO.id}</td>
					<td class="text-center">${staffVO.email}</td>
					<td class="text-center">****************</td>
					<td class="text-center">${staffVO.name}</td>
					<td class="text-center">
						<form method="post"
							action="<%=request.getContextPath()%>/za101g2/back/staff/staff.do">
							<input type="submit" value="修改" class="btn btn-info"> <input
								type="hidden" name="staffid" value="${staffVO.id}"> <input
								type="hidden" name="action" value="getOne_For_Update">
						</form>
					</td>
					<td class="text-center"><input type="submit"
						value="${staffStatus[staffVO.status]}"
						class="btn ${staffStatusButton[staffVO.status]}"></td>
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