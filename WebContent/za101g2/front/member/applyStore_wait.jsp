<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="za101g2.store.model.*"%>
<%@page import="za101g2.member.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	MemberVO memberVO = (MemberVO) request.getSession().getAttribute(
			"memberVO");
	System.out.print(memberVO.getNickname());
	
	StoreVO tempStoreVO = (StoreVO) request.getAttribute("tempStoreVO");
	
	if(tempStoreVO==null)
	{
	StoreService srv = new StoreService();
	StoreVO storeVO = srv.getStoreByFK(memberVO.getId());
	pageContext.setAttribute("tempStoreVO", storeVO);
	}
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
<script src="<%=request.getContextPath()%>/js/storeHome.js"></script>
<title>Insert title here</title>
</head>
<body>
	<%@ include file="/files/PetribeHead.file"%>
	<form METHOD="post" ACTION="<%=request.getContextPath()%>/StoreServlet"
		name="" class="form-horizontal col-md-9">
		<div class="form-group">
			<c:if test="${not empty successMsg}">
				<h3 class="col-md-7 col-md-offset-2 text-center">
					<b>申請成功！以下是你的申請單。<br />審核時間會需要兩至三天的工作天，請靜候通知。
					</b>
				</h3>
			</c:if>
			<c:if test="${not empty successAgainMsg}">
				<h3 class="col-md-7 col-md-offset-2 text-center">
					<b>申請成功！以下是你新的申請單。<br />複審時間同樣需要兩至三天的工作天，請靜候通知。
					</b>
				</h3>
			</c:if>
			<c:if test="${empty successMsg and empty successAgainMsg}">
				<h1 class="col-md-7 col-md-offset-2 text-center">
					<b>你的商家申請單</b>
				</h1>
			</c:if>
		</div>
		<div class="form-group">
			<label for="name" class="control-label col-md-2">商店名稱<span class="text-danger">*</span></label>
			<div class="col-md-7">
				<input type="text" name="name" id="name" value="${tempStoreVO.name}"
					class="form-control" ${tempStoreVO.status==3?'':'readonly'}>
			</div>
			<div class="text-danger col-md-3">
				<b>${nameError}</b>
			</div>
		</div>
		<div class="form-group">
			<label for="address" class="control-label col-md-2">地址<span class="text-danger">*</span></label>
			<div class="col-md-7">
				<input type="text" name="address" id="address"
					value="${tempStoreVO.address}" class="form-control"
					${tempStoreVO.status==3?'':'readonly'}>
			</div>
			<div class="text-danger col-md-3">
				<b>${addressError}</b>
			</div>
		</div>
		<div class="form-group">
			<label for="info" class="control-label col-md-2">商店資訊<span class="text-danger">*</span></label>
			<div class="col-md-7">
				<textarea rows="4" cols="50" name="info" id="info"
					class="form-control" ${tempStoreVO.status==3?'':'readonly'}>
	${tempStoreVO.info}
	</textarea>
			</div>
			<div class="text-danger col-md-3">
				<b>${infoError}</b>
			</div>
		</div>
		<div class="form-group">
			<label for="speciesLimit" class="control-label col-md-2">可接受寵物<span class="text-danger">*</span></label>
			<div class="col-md-7">
				<select name="speciesLimit" id="speciesLimit" class="form-control"
					${tempStoreVO.status==3?'':'disabled'}>
					<option value="0">請選擇</option>
					<option value="1" ${tempStoreVO.speciesLimit=="1"? "selected":""}>狗</option>
					<option value="2" ${tempStoreVO.speciesLimit=="2"? "selected":""}>貓</option>
					<option value="3" ${tempStoreVO.speciesLimit=="3"? "selected":""}>貓狗皆可</option>
				</select>
			</div>
			<div class="text-danger col-md-3">
				<b>${speciesLimitError}</b>
			</div>
		</div>
		<div class="form-group">
			<label for="maxQuantitly" class="control-label col-md-2">寵物容納數量<span class="text-danger">*</span></label>
			<div class="col-md-7">
				<select name="maxQuantitly" id="maxQuantitly" class="form-control"
					${tempStoreVO.status==3?'':'disabled'}>
					<option value="0">請選擇</option>
					<c:forEach var="i" begin="1" end="10">
						<option value="${i}" ${tempStoreVO.maxQuantitly==i? "selected":""}>${i}</option>
					</c:forEach>
				</select>
			</div>
			<div class="text-danger col-md-3">
				<b>${maxQuantitlyError}</b>
			</div>
		</div>
		<div class="form-group">
			<label for="comments" class="control-label col-md-2">申請事由<span class="text-danger">*</span></label>
			<div class="col-md-7">
				<textarea rows="4" cols="50" name="comments" id="comments"
					class="form-control" ${tempStoreVO.status==3?'':'readonly'}>
	${tempStoreVO.comments}
	</textarea>
			</div>
			<div class="text-danger col-md-3">
				<b>${commentsError}</b>
			</div>
		</div>
		<c:if test="${not empty tempStoreVO.siteReport}">
			<hr />
			<div class="form-group">
				<label for="siteReport" class="control-label col-md-2 text-danger">站方回覆</label>
				<div class="col-md-7">
					<textarea rows="4" cols="50" name="siteReport" id="siteReport"
						class="form-control" readonly>
	${tempStoreVO.siteReport}
	</textarea>
				</div>
			</div>
			<div class="form-group">
				<input type="hidden" name="action" value="re_apply">
				<input type="hidden" name="status" value="${tempStoreVO.status}"> <input
					type="submit" value="複審"
					class="btn btn-primary col-md-2 col-md-offset-3"
					${tempStoreVO.status==3?'':'disabled'}> <a
					href="<%=request.getContextPath()%>/za101g2/front/"
					class="btn btn-default col-md-2 col-md-offset-1">取消</a>
			</div>
		</c:if>
	</form>
	<%@ include file="/files/PetribeFoot.file"%>