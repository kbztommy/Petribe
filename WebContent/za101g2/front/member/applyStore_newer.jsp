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
<script src="<%=request.getContextPath()%>/js/storeHome.js"></script>
<title>Insert title here</title>
</head>
<body>
	<%@ include file="/files/PetribeHead.file"%>
	<form METHOD="post" ACTION="<%=request.getContextPath()%>/StoreServlet"
		name="" class="form-horizontal col-md-9">
		<div class="form-group">
			<h1 class="col-md-7 col-md-offset-2 text-center">
				<b>商家申請</b>
			</h1>
		</div>
		<div class="form-group">
			<label for="name" class="control-label col-md-2">商店名稱<span class="text-danger">*</span></label>
			<div class="col-md-7">
				<input type="text" name="name" id="name" value="${tempStoreVO.name}"
					class="form-control">
			</div>
			<div class="text-danger col-md-3">
				<b>${nameError}</b>
			</div>
		</div>
		<div class="form-group">
			<label for="address" class="control-label col-md-2">地址<span class="text-danger">*</span></label>
			<div class="col-md-7">
				<input type="text" name="address" id="address"
					value="${tempStoreVO.address}" class="form-control">
			</div>
			<div class="text-danger col-md-3">
				<b>${addressError}</b>
			</div>
		</div>
		<div class="form-group">
			<label for="info" class="control-label col-md-2">商店資訊<span class="text-danger">*</span></label>
			<div class="col-md-7">
				<textarea rows="4" cols="50" name="info" id="info"
					class="form-control">
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
				<select name="speciesLimit" id="speciesLimit" class="form-control">
					<option value="0">請選擇</option>
					<option value="1" ${tempStoreVO.speciesLimit==1? "selected" : ""}>狗</option>
					<option value="2" ${tempStoreVO.speciesLimit==2? "selected" : ""}>貓</option>
					<option value="3" ${tempStoreVO.speciesLimit==3? "selected" : ""}>貓狗皆可</option>
				</select>
			</div>
			<div class="text-danger col-md-3">
				<b>${speciesLimitError}</b>
			</div>
		</div>
		<div class="form-group">
			<label for="maxQuantitly" class="control-label col-md-2">寵物容納數量<span class="text-danger">*</span></label>
			<div class="col-md-7">
				<select name="maxQuantitly" id="maxQuantitly" class="form-control">
					<option value="0">請選擇</option>
					<c:forEach var="i" begin="1" end="10">
						<option value="${i}"
							${tempStoreVO.maxQuantitly==i? "selected" : ""}>${i}</option>
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
					class="form-control">
	${tempStoreVO.comments}
	</textarea>
			</div>
			<div class="text-danger col-md-3">
				<b>${commentsError}</b>
			</div>
		</div>
		<div class="form-group">
			<input type="hidden" name="action" value="apply"> <input
				type="submit" value="申請"
				class="btn btn-primary col-md-2 col-md-offset-3"> <a
				href="<%=request.getContextPath()%>/za101g2/front/"
				class="btn btn-default col-md-2 col-md-offset-1">取消</a>
		</div>
	</form>
	<%@ include file="/files/PetribeFoot.file"%>