<%@page import="za101g2.store.model.StoreService"%>
<%@page import="za101g2.store.model.StoreVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import ="java.util.*" %> 

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

<form METHOD="post" ACTION="<%=request.getContextPath()%>/StoreServlet" name="memberRegister" class="form-horizontal col-md-offset-1">
<div class="form-group">
<h3 class="form-horizontal col-md-offset-5"><b>商家審核</b></h3>
</div>
	<div class="form-group">
	<label for="name" class="control-label col-md-2">商店名稱</label>
	<div class="col-md-8">
	<input type="text" name="name" id="name" value="${storeVO.name}" class="form-control" readonly>
	</div>
	<div class="text-danger col-md-3"><b></b></div>
	</div>
	<div class="form-group">
	<label for="address" class="control-label col-md-2">地址</label>
	<div class="col-md-8">
	<input type="text" name="address" id="address" value="${storeVO.address}" class="form-control" readonly>
	</div>
	<div class="text-danger col-md-3"><b></b></div>
	</div>
	<div class="form-group">
	<label for="phone" class="control-label col-md-2">手機</label>
	<div class="col-md-8">
	<input type="text" name="phone" id="phone" value="${storeVO.memberVO.phone}" class="form-control" readonly>
	</div>
	<div class="text-danger col-md-3"><b></b></div>
	</div>
	<div class="form-group">
	<label for="info" class="control-label col-md-2">商店資訊</label>
	<div class="col-md-8">
	<textarea rows="4" cols="50" name="info" id="info" class="form-control" readonly>
	${storeVO.info}
	</textarea>
	</div>
	</div>
	<div class="form-group">
	<label for="speciesLimit" class="control-label col-md-2">可接受寵物</label>
	<div class="col-md-8">
	<select name="speciesLimit" id="speciesLimit" class="form-control" disabled>
		<option value="0">請選擇</option>
		<option value="1" ${storeVO.speciesLimit=="1"? "selected":""}>狗</option>
		<option value="2" ${storeVO.speciesLimit=="2"? "selected":""}>貓</option>
		<option value="3" ${storeVO.speciesLimit=="3"? "selected":""}>貓狗皆可</option>
	</select>
	</div>
	</div>
	<div class="form-group">
	<label for="maxQuantitly" class="control-label col-md-2">寵物容納數量</label>
	<div class="col-md-8">
	<select name="maxQuantitly" id="maxQuantitly" class="form-control" disabled>
		<option value="0">請選擇</option>
		<c:forEach var="i" begin="1" end="10">
		<option value="${i}" ${storeVO.maxQuantitly==i? "selected":""}>${i}</option>
		</c:forEach>
	</select>
	</div>
	</div>
	<div class="form-group">
	<label for="comments" class="control-label col-md-2">申請事由</label>
	<div class="col-md-8">
	<textarea rows="4" cols="50" name="comments" id="comments" class="form-control" readonly>
	${storeVO.comments}
	</textarea>
	</div>
	</div>
	<div class="form-group">
	<label for="siteReport" class="control-label col-md-2">站方回覆</label>
	<div class="col-md-8">
	<textarea rows="4" cols="50" name="siteReport" id="siteReport" class="form-control" ${storeVO.status==3||storeVO.status==4?'readonly':''}>
	${storeVO.siteReport}
	</textarea>
	</div>
	</div>
	<div class="form-group">
	<input type="submit" name="accept" value="通過" class="btn btn-primary col-md-2 col-md-offset-2" ${storeVO.status==3||storeVO.status==4?'disabled':''}>
	<input type="submit" name="refuse" value="不通過" class="btn btn-danger col-md-2 col-md-offset-1" ${storeVO.status==3||storeVO.status==4?'disabled':''}>
	<input type="hidden" name="onesmemberid" value="${storeVO.id}">
	<a href="<%=request.getContextPath()%>/za101g2/back/store/storeApply.jsp" class="btn btn-default col-md-2 col-md-offset-1">取消</a>
	</div>
	</form>


</section>
	<div style="clear:both;">
		<footer id="the_footer">
			Copyright 2015 Institute For Information Industry ZA101 Petribe | Private Policy 
		</footer>
	</div>
</body>
</html>