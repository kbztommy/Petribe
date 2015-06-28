<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="za101g2.missingrecord.model.*"%>
<%@ page import="za101g2.pet.model.*"%>

<%-- 取出 Controller MissingRecordServlet.java已存入request的petVO物件--%>
<%
	List<PetVO> list = (ArrayList<PetVO>) request.getAttribute("petVO");
	pageContext.setAttribute("list", list);
%>

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
<!--for bounty-->	
	<script src="js/bounty.js"></script>
<!--for 神奇小按鈕 -->
	<script src="js/miraculousButton.js"></script>		
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
<title>刊登協尋</title>
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>
<div class="col-md-8">
	<ul class="nav nav-pills  mynav-pills">
	  <li role="presentation">
		  <a href="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do?memId=${memberVO.id}&action=getMember_MissingRecord">協尋紀錄</a>
	  </li>
	  <li role="presentation" class="active"><a href="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do?id=${memberVO.id}&action=getMember_Pet_Id" >刊登協尋</a></li>
	  <li role="presentation"><a href="<%= request.getContextPath() %>/za101g2/front/missingReply/missingReply.do?memId=${memberVO.id }&action=getMember_MissingReply">回報紀錄</a></li>
	  <li role="presentation"><a href="<%= request.getContextPath() %>/za101g2/front/missingReport/missingReport.do?memId=${memberVO.id}&action=getMember_MissingReport">檢舉紀錄</a></li>
	</ul>
</div>
<div class="col-md-8">
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font color='deeppink'>溫馨提醒:
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message.value}${(errorMsgs.emptyPet!=null)? "<button onclick='history.back()' class='btn btn-info'>返回上一頁</button>":""}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>
</div>

<FORM method="post" action="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do" name="form1" id="form1" enctype="multipart/form-data" role="form" class="col-md-6">
	<div class="form-group ${(errorMsgs.petId!=null) ? 'has-error':''}">
 		<label>寵物名字:</label>
		<select size="1" name="petId" class="form-control" ${(errorMsgs.emptyPet!=null)? "disabled":""}>
			<c:forEach var="petVO" items="${list}">
				<option value="${petVO.id}" ${(param.petId==petVO.id) ? 'selected':''}>${petVO.name}</option>
			</c:forEach> 	 
		</select>
	</div>
	<div class="form-group ${(errorMsgs.emptyCity!=null) ? 'has-error':''}">
		<label>走失地點縣市:<font color=red><b>*</b></font></label>
		<select size="1" name="city" id="city" class="form-control" ${(errorMsgs.emptyPet!=null)? "disabled":""}>
			<option value="emptyCity">--請選擇縣市--</option>
			<c:forEach var="city" items="${applicationScope.cityList}">
				<option value="${city}" ${(param.city==city) ? 'selected':''}>${city}</option>
			</c:forEach>
		</select>
	</div>
 	<div class="form-group ${(errorMsgs.location!=null) ? 'has-error':''}">
		<label for="location">走失地點:<font color=red><b>*</b></font></label>		
		<input type="text" name="location" id="location" value="${param.location}" class="form-control" placeholder="請填寫走失地點" ${(errorMsgs.emptyPet!=null)? "disabled":""}>
	</div>
	
	<%java.sql.Date date_SQL = new java.sql.Date(System.currentTimeMillis());%>
	<c:set scope="page" var="missingDate" value="<%=date_SQL%>"></c:set>
	<div class="form-group ${(errorMsgs.missingDate!=null) ? 'has-error':''}">
		<label for="datepicker">走失日期:<font color=red><b>*</b></font></label>
		<input onfocus="this.blur()" type="text" name="missingDate" id="datepicker" class="form-control" value="${(param.missingDate==null or param.missingDate=='')? missingDate : param.missingDate}" ${(errorMsgs.emptyPet!=null)? "disabled":""}/>		
	</div>
	
	<div class="form-group ${(errorMsgs.bounty!=null) ? 'has-error':''}">
		<label for="bounty">賞金:<font color=red><b>*</b></font></label>
		<input type="radio" name="checkBounty" id="emptyBounty" value="emptyBounty" ${(param.checkBounty=='emptyBounty') ? "checked":""} ${(errorMsgs.emptyPet!=null)? "disabled":""}>無賞金
		<input type="radio" name="checkBounty" id="fullBounty" value="fullBounty" ${(param.checkBounty=='fullBounty') ? "checked":""} ${(errorMsgs.emptyPet!=null)? "disabled":""}>有賞金
		<font color='deeppink'>${errorMsgs.checkBounty}</font>
		<p>
			<input type="text" name="bounty" value="${param.bounty}" id="bounty" class="form-control" placeholder="請填寫賞金" ${(errorMsgs.emptyPet!=null)? "disabled":""}/>
				
			<b>信用卡號碼:</b>
			<input type="text" name="creditCard1" id="creditCard1" maxlength="4" size="4" value="${param.creditCard1}" class="creditCard" ${(errorMsgs.emptyPet!=null)? "disabled":""}/>
			-<input type="text" name="creditCard2" id="creditCard2" maxlength="4" size="4" value="${param.creditCard2}" class="creditCard" ${(errorMsgs.emptyPet!=null)? "disabled":""}/>
			-<input type="text" name="creditCard3" id="creditCard3" maxlength="4" size="4" value="${param.creditCard3}" class="creditCard" ${(errorMsgs.emptyPet!=null)? "disabled":""}/>
			-<input type="text" name="creditCard4" id="creditCard4" maxlength="4" size="4" value="${param.creditCard4}" class="creditCard" ${(errorMsgs.emptyPet!=null)? "disabled":""}/>
			<img src="./images/creditCard.gif">
			<br>
			<font color='deeppink'>${errorMsgs.creditCard}</font>
		</p>		
	</div>
	<div class="form-group ${(errorMsgs.comments!=null) ? 'has-error':''}">
		<label for="comments">說明:<font color=red><b>*</b></font></label>
	 	<textarea name="comments" rows="5" class="form-control" id="comments" placeholder="請填寫說明內容" ${(errorMsgs.emptyPet!=null)? "disabled":""}>${param.comments}</textarea>
	</div>	
 	<div class="form-group">
 		<label for="bounty">寵物照片:</label>
 		<input type="file" name="missingPhoto" id="missingPhoto" onchange="change()" ${(errorMsgs.emptyPet!=null)? "disabled":""}>
 		<img id="preview" name="pic" width=140px height=105px>
 	</div>

	<input type="hidden" name="action" value="insert">
	<input type="hidden" name="id" value="${param.id}">
	<%-- <input type="submit" id="submit" value="刊登協尋" ${(errorMsgs.emptyPet!=null)? "disabled":""}> --%>
	<input type="button" value="刊登協尋" id="geocode" class="btn btn-info" ${(errorMsgs.emptyPet!=null)? "disabled":""}>
	<input type="hidden" name="latlng" id="latlng">
		
</FORM>

<div class="col-md-8">
	<br>
	<br>
	<br>
	<button type="button" name="miracle" id="miracle" onclick="miracle()">神奇小按鈕</button>
</div>

<%@ include file="/files/PetribeFoot.file" %>
