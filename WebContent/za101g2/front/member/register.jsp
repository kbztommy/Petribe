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
<script src="<%= request.getContextPath()%>/js/storeHome.js"></script>
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript">
$(document).ready(function(){
$( "#lite" ).click(function() {
	  $("#email").val("za101g2@gmail.com");
	  $("#password").val("11111111");
	  $("#rePassword").val("11111111");
	  $("#nickname").val("小彬彬");
	  $("#lastname").val("黃");
	  $("#firstname").val("曉彬");
	  $("#sex").val("1");
	});
});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>
	<form METHOD="post" ACTION="<%=request.getContextPath()%>/za101g2/front/member/member.do" name="memberRegister" class="form-horizontal col-md-9">
	<div class="form-group">
	<h1 class="col-md-7 col-md-offset-2 text-center"><b>會員註冊</b></h1>
	</div>
	<div class="form-group">
	<label for="email" class="control-label col-md-2">電子信箱<span class="text-danger">*</span></label>
	<div class="col-md-7">
	<input type="text" name="email" id="email" value="${tempMemberVO.email}" class="form-control">
	</div>
	<div class="text-danger col-md-3"><b>${emailError}</b></div>
	</div>
	<div class="form-group">
	<label for="password" class="control-label col-md-2">密碼<span class="text-danger">*</span></label>
	<div class="col-md-7">
	<input type="password" name="password" id="password" value="" class="form-control">
	</div>
	<div class="text-danger col-md-3"><b>${passwordError}</b></div>
	</div>
	<div class="form-group">
	<div class="text-danger col-md-10 col-md-offset-2">（請輸入至少八位以上的英數密碼）</div>
	</div>
	<div class="form-group">
	<label for="rePassword" class="control-label col-md-2">確認密碼<span class="text-danger">*</span></label>
	<div class="col-md-7">
	<input type="password" name="rePassword" id="rePassword" value="" class="form-control">
	</div>
	</div>
	<div class="form-group">
	<label for="nickname" class="control-label col-md-2">暱稱<span class="text-danger">*</span></label>
	<div class="col-md-7">
	<input type="text" name="nickname" id="nickname" value="${tempMemberVO.nickname}" class="form-control">
	</div>
	<div class="text-danger col-md-3"><b>${nicknameError}</b></div>
	</div>
	<div class="form-group">
	<label for="lastname" class="control-label col-md-2">姓<span class="text-danger">*</span></label>
	<div class="col-md-3">
	<input type="text" name="lastname" id="lastname" value="${tempMemberVO.lastname}" class="form-control">
	</div>
	<label for="firstname" class="control-label col-md-1">名<span class="text-danger">*</span></label>
	<div class="col-md-3">
	<input type="text" name="firstname" id="firstname" value="${tempMemberVO.firstname}" class="form-control">
	</div>
	<div class="text-danger col-md-3"><b>${nameError}</b></div>
	</div>
	<div class="form-group">
	<label for="sex" class="control-label col-md-2">性別<span class="text-danger">*</span></label>
	<div class="col-md-7">
	<select name="sex" id="sex" class="form-control col-md-8">
		<option value="-1">請選擇</option>
		<option value="1" ${tempMemberVO.sex=="1"? "selected":""}>男性</option>
		<option value="0" ${tempMemberVO.sex=="0"? "selected":""}>女性</option>
		<option value="2" ${tempMemberVO.sex=="2"? "selected":""}>不公開</option>
	</select>
	</div>
	<div class="text-danger col-md-3"><b>${sexError}</b></div>
	</div>
	<div class="form-group">
	<input type="hidden" name="action" value="register">
	<input type="submit" value="註冊" class="btn btn-primary col-md-2 col-md-offset-3">
	<a href="<%=request.getContextPath()%>/za101g2/front/" class="btn btn-default col-md-2 col-md-offset-1">取消</a>
	</div>
	</form>
		<div class="form-group">
	<button class="btn btn-info col-md-2 col-md-offset-3" id="lite">^_^</button>
	</div>
	<%@ include file="/files/PetribeFoot.file" %>
