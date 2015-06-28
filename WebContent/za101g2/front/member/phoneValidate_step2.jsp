<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width initial-scale =1">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/mainStyle.css">
<script src="<%= request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/storeHome.js"></script>
<title>Insert title here</title>
</head>
<body>
	<%@ include file="/files/PetribeHead.file"%>
	<form method="post" action="<%=request.getContextPath()%>/za101g2/front/member/phoneValidate.do" class="form-horizontal col-md-8 col-md-offset-1">
			<div class="form-group">
			<div class="col-md-9 col-md-offset-2">
				<h1>
					<b>手機認證</b>
				</h1>
			</div>
		</div>
		<div class="form-group">
			<div class="progress">
				<div class="progress-bar progress-bar-info progress-bar-striped active"
					role="progressbar" aria-valuenow="55" aria-valuemin="0"
					aria-valuemax="100" style="width: 55%"></div>
			</div>
		</div>
		<div class="form-group">
			<h3 class="col-md-9 col-md-offset-2"><b>請輸入你收到的簡訊驗證碼進行驗證。</b><br /><small class="text-danger">（※若輸入錯誤次數達五次，該驗證碼即失效。）</small></h3>
		</div>
		<div class="form-group">
		<!-- <label for="validateCode">驗證碼</label> -->
		<div class="col-md-4 col-md-offset-2">
		<input type="text" name="validateCode" id="validateCode" value="" class="form-control">
		</div>
		<input type="hidden" name="action" value="validate">
		<input type="submit" value="確認送出" class="btn btn-primary col-md-2"><br />
		</div>
				<div class="form-group">
		<a href="<%=request.getContextPath()%>/za101g2/front/member/phoneValidate_step1.jsp" class="btn btn-default col-md-2 col-md-offset-6">上一步</a>
		</div>
		<c:if test="${not empty validateCodeError}">
		<div class="form-group">
			<div class="alert alert-danger col-md-4 col-md-offset-3 text-center" role="alert"><b>${validateCodeError}</b></div>
		</div>
		</c:if>
	</form>
		<%@ include file="/files/PetribeFoot.file"%>
