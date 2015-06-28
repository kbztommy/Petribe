<%@page import="za101g2.member.model.MemberService"%>
<%@ page import="za101g2.member.model.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% 
	MemberVO memberVO = (MemberVO) request.getSession().getAttribute("memberVO");
	MemberService srv = new MemberService();
	memberVO = srv.getOneMember(memberVO.getId());
	pageContext.setAttribute("memberVO", memberVO);
	

%>
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
	<div class="form-horizontal col-md-9">
	<div class="form-group">
	<h1 class="col-md-4 col-md-offset-4 text-center"><b>個人資料</b></h1>
	</div>
	<hr />
	<div class="form-group">
	<div class="col-md-5">
	<img src="<%=request.getContextPath()%>/PicForMember?id=${memberVO.id}" class="col-md-8 col-md-offset-4"><br />
	<a href="<%=request.getContextPath()%>/za101g2/front/member/updateIcon.jsp" class="btn btn-primary col-md-8 col-md-offset-4">修改頭像</a>
	</div>
	<div class="col-md-7">
	<table class="table table-condensed table-hover">
	<tr>
	<td class="col-md-4">
	<h4 class="text-left">暱稱</h4>
	</td>
	<td class="col-md-6">
	<h4  class="text-left">${memberVO.nickname}</h4>
	</td>
	<td class="col-md-2">
	<a href="<%=request.getContextPath()%>/za101g2/front/member/updateNickname.jsp" class="btn btn-primary">修改暱稱</a>
	</td>
	</tr>
	<tr>
	<td class="col-md-3">
	<h4 class="text-left">手機號碼</h4>
	</td>
	<td class="col-md-9 col-md-offset-1">
	<h4  class="text-left">${memberVO.phone==null?'尚未通過手機驗證':memberVO.phone}</h4>
	</td>
	<td class="col-md-3">
	<a href="<%=request.getContextPath()%>/za101g2/front/member/phoneValidate_step1.jsp" class="btn btn-primary">${memberVO.phone==null?'手機驗證':'修改號碼'}</a>
	</td>
	</tr>
	<tr>
	<td class="col-md-3">
	<h4 class="text-left">姓名</h4>
	</td>
	<td class="col-md-9 col-md-offset-1">
	<h4  class="text-left">${memberVO.lastname}${memberVO.firstname}</h4>
	</td>
	<td class="col-md-3">
	</td>
	</tr>
	<tr>
	<td class="col-md-3">
	<h4 class="text-left">性別</h4>
	</td>
	<td class="col-md-9 col-md-offset-1">
	<h4  class="text-left">${sex[memberVO.sex]}</h4>
	</td>
	<td class="col-md-3">
	</td>
	</tr>
	<tr>
	<td class="col-md-3">
	<h4 class="text-left">密碼</h4>
	</td>
	<td class="col-md-9 col-md-offset-1">
	<h4  class="text-left">****************</h4>
	</td>
	<td class="col-md-3">
	<a href="<%=request.getContextPath()%>/za101g2/front/member/updatePassword.jsp" class="btn btn-danger">修改密碼</a>
	</td>
	</tr>
	<tr>
	<td class="col-md-3">
	<h4 class="text-left">會員等級</h4>
	</td>
	<td class="col-md-9 col-md-offset-1">
	<h4  class="text-left">${memberStatus[memberVO.status]}</h4>
	</td>
	<td class="col-md-3">
	<c:if test="${send==null}">
	<a href="<%=request.getContextPath()%>/za101g2/front/member/member.do?action=resent&memid=${memberVO.id}" class="btn btn-info ${memberVO.status=='0'?'':'hidden'}">沒收到認證信？</a>
	</c:if>
	<c:if test="${not empty send}">
	<a href="" class="btn btn-success disabled">已送出E-Mail</a>
	</c:if>
	</td>
	</tr>
	</table>
	</div>
	</div>
	</div>
	<%@ include file="/files/PetribeFoot.file" %>
</body>
</html>