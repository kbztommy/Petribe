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
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%= request.getContextPath()%>/js/storeHome.js"></script>
<title>Insert title here</title>
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>
	<form method="post" action="<%=request.getContextPath()%>/za101g2/front/member/member.do" class="form-horizontal col-md-8">
		<div class="form-group">
	<h1 class="col-md-8 col-md-offset-4 text-center"><b>修改密碼</b></h1>
	</div>
	<div class="form-group">
		<label for="password" class="control-label col-md-4">原密碼</label>
	<div class="col-md-8">
		<input type="password" name="password" id="password" value="" class="form-control">
	</div>
	</div>
	<div class="form-group">
		<label for="updatePassword" class="control-label col-md-4">修改密碼</label>
	<div class="col-md-8">
		<input type="password" name="updatePassword" id="updatePassword" value="" class="form-control">
	</div>
	</div>
	<div class="form-group">
		<label for="reUpdatePassword" class="control-label col-md-4">確認修改密碼</label>
	<div class="col-md-8">
		<input type="password" name="reUpdatePassword" id="reUpdatePassword" value="" class="form-control">
	</div>
	</div>
	<div class="form-group">
		<input type="hidden" name="action" value="update_password">
		<input type="submit" value="修改" class="btn btn-primary col-md-2 col-md-offset-5">
		<a href="<%=request.getContextPath()%>/za101g2/front/member/memberManager.jsp" class="btn btn-default col-md-2 col-md-offset-1">取消</a>
	</div>
		<c:if test="${not empty passwordError}">
		<div class="form-group">
			<div class="alert alert-danger col-md-8 col-md-offset-4 text-center" role="alert"><b>${passwordError}</b></div>
		</div>
		</c:if>
	</form>
<%@ include file="/files/PetribeFoot.file" %>
</body>
</html>