<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="za101g2.missingrecord.model.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width initial-scale =1">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/mainStyle.css">    
    <script src="<%= request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
    <script src="<%= request.getContextPath()%>/js/bootstrap.min.js"></script>
<!--for fileReader -->
	<script src="js/fileReader.js"></script>
<!--for calendar-->    
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<script src="js/calendar.js"></script>
<!--google maps -->
	<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCUsMbG0pRwG9c48pWlVEOBG65opDqLK4A"></script>
	<script type="text/javascript">	
	$(document).ready(function() {
		
		function form1Submit(latlng) {
			$("#latlng").val(latlng);	
			$("#form1").submit();
		}
		
		$("#geocode").click(function(){
			var geocoder = new google.maps.Geocoder();
			var city = $("#city").val();
			var location = $("#location").val();
			var address = city + location;
			var regex = /\S+/;
			if ((location == '') || (location.search(regex) == -1) || city == 'emptyCity') {
				form1Submit();
			} else {
				geocoder.geocode( { 'address': address}, function(results, status) {
					if (status == google.maps.GeocoderStatus.OK) {
						var lat = parseFloat(results[0].geometry.location.toString().slice(1)).toFixed(3);
						var lng = parseFloat(results[0].geometry.location.toString().slice(12)).toFixed(3);
						var latlng = lat + ',' + lng;
						form1Submit(latlng);
					} else {
						alert("請勿填寫無效的走失地點");
					}
				});
			}
		});//end of click		
	});//end of ready
	</script>	
<title>修改協尋資訊</title>
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>

<div class="row">
    <div class="col-md-8">
		<ol class="breadcrumb myBreadCrumb">
		  <li><a href="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do?memId=${memberVO.id}&action=getMember_MissingRecord" class="btSidebar">協尋紀錄</a></li>		
		  <li class="active">修改協尋資訊</li>
		</ol>
	</div>
</div>   

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font color='deeppink'>溫馨提醒:
	<ui>
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message.value}</li>
		</c:forEach>
	</ui>
	</font>
</c:if>

<FORM method="post" action="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do" name="form1" id="form1" enctype="multipart/form-data" role="form" class="col-md-8">
	
	<jsp:useBean id="petSvc" scope="page" class="za101g2.pet.model.PetService" />
	<c:set var="petVO" value="${petSvc.getOnePet(param.petId)}" scope="page"></c:set>
	<div class="form-group">
		<label>寵物名字:</label>
		<p class="form-control-static">${petVO.name}</p>
	</div>
	
	<div class="form-group">
		<label>賞金:</label>
		<p class="form-control-static">${param.bounty}元</p>
	</div>
	<div class="form-group ${(errorMsgs.emptyCity!=null) ? 'has-error':''}">
		<label>走失地點縣市:<font color=red><b>*</b></font></label>
		<select size="1" name="city" id="city" class="form-control">
			<option value="emptyCity">--請選擇縣市--</option>
			<c:forEach var="city" items="${applicationScope.cityList}">
				<option value="${city}" ${(requestScope.city==city) ? 'selected':''}>${city}</option>
			</c:forEach>
		</select>
	</div>
	<div class="form-group ${(errorMsgs.location!=null) ? 'has-error':''}">
		<label for="location">走失地點:</label>
		<input type="text" name="location" value="${requestScope.location}" class="form-control" id="location">
	</div>
	<div class="form-group  ${(errorMsgs.missingDate!=null) ? 'has-error':''}">
		<label for="datepicker">走失日期:</label>
		<input onfocus="this.blur()" type="text" name="missingDate" value="${param.missingDate}" class="form-control" id="datepicker">
	</div>
	<div class="form-group ${(errorMsgs.comments!=null) ? 'has-error':''}">
		<label for="comments">說明:</label>
		<textarea name="comments" rows="5" class="form-control" id="comments">${param.comments}</textarea>
	</div>
	<div class="form-group">
		<label for="missingPhoto">寵物照片:</label>	
		<input type="file" name="missingPhoto" id="missingPhoto" onchange="change()">
		<img src = "<%= request.getContextPath() %>/MissingRecordGifReader?id=${param.id}" width="140px" height="105px" id="preview" name="pic">
	</div>

	<input type="hidden" name="action" value="update">
	<input type="hidden" name="id" value="${param.id}">
	<input type="hidden" name="petId" value="${param.petId}">
	<input type="hidden" name="bounty" value="${param.bounty}">
	<input type="hidden" name="memId" value="${memId}">
	<!-- <input type="submit" value="修改協尋" class="btn btn-info"> -->
	<input type="button" value="修改協尋" id="geocode" class="btn btn-info">
	<input type="hidden" name="latlng" id="latlng">
</FORM>

<%@ include file="/files/PetribeFoot.file" %>
