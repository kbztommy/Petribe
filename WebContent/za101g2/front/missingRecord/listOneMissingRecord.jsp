<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List" %>
<%@ page import="za101g2.missingrecord.model.*"%>
<%@ page import="za101g2.pet.model.*"%>
<%@ page import="za101g2.missingreply.model.*"%>
<%-- 採用 Script 的寫法取值 --%>

<%-- 取出 Controller MissingRecordServlet.java已存入request的MissingRecordVO物件--%>
<%MissingRecordVO missingRecordVO = (MissingRecordVO) request.getAttribute("missingRecordVO");%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width initial-scale =1">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/mainStyle.css">    
    <script src="<%= request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
    <script src="<%= request.getContextPath()%>/js/bootstrap.min.js"></script>
<!--for replyNumber-->	
	<script src="js/replyNumber.js"></script>   
<!--google maps -->
	<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCUsMbG0pRwG9c48pWlVEOBG65opDqLK4A"></script>
    <script type="text/javascript">
<%--     	var geocoder;--%>
		var directionsDisplay;
		var directionsService = new google.maps.DirectionsService();
    	var map;
		function initialize() {
<%-- 			geocoder = new google.maps.Geocoder();--%>
			directionsDisplay = new google.maps.DirectionsRenderer();
			
			var latlng = new google.maps.LatLng(23.961, 120.972);
			var mapOptions = {
				zoom: 15,
				center: latlng
			};
			
			map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
			
			directionsDisplay.setMap(map);
				
		<%-- marker走失地點及加入infowindow，取request的missingRecordVO --%>	
			var contentString = '<h3>協尋資訊</h3>' +
				'走失日期:' + 
				'${missingRecordVO.missingDate}<br>' +
				'寵物名字:' +
				'<%=missingRecordVO.getPetVO().getName()%>';
				
			var infowindow = new google.maps.InfoWindow({
				content: contentString
			});	
			
			var missingRecordLatlng = '${missingRecordVO.latlng}';
			var markerLat = parseFloat(missingRecordLatlng.slice(0));
			var markerLng = parseFloat(missingRecordLatlng.slice(7));
			var markerLatlng = new google.maps.LatLng(markerLat, markerLng);
			
			map.setCenter(markerLatlng);
			var image = 'images/pets.png';
			var marker = new google.maps.Marker({
				map: map,
				position: markerLatlng,
				icon: image,
				animation: google.maps.Animation.DROP,
				title: '${missingRecordVO.location}'
			});
			
			var infowindow = new google.maps.InfoWindow({
				content: contentString
			});
			
			bindInfoWindow(marker, map, infowindow);
			
			function bindInfoWindow(marker, map, infowindow) {
				google.maps.event.addListener(marker, 'click', function() {
					infowindow.open(map,marker);		
				});
			}
		
			<%-- marker走失地點及加入infowindow，用google maps geocode --%>
<%--			var address = '${missingRecordVO.location}';
			geocoder.geocode( { 'address': address}, function(results, status) {
				
				if (status == google.maps.GeocoderStatus.OK) {
					map.setCenter(results[0].geometry.location);
					var image = 'images/pets.png';
					var marker = new google.maps.Marker({
						map: map,
						position: results[0].geometry.location,
						icon: image,
						animation: google.maps.Animation.DROP,
						title: '${missingRecordVO.location}'
					});
					
					google.maps.event.addListener(marker, 'click', function() {
						infowindow.open(map,marker);
					});			
				} else {
					alert("Geocode was not successful for the following reason: " + status);
				}
			});--%>

		}
		
		function calcRoute() {
			var missingRecordLatlng = '${missingRecordVO.latlng}';
			var markerLat = parseFloat(missingRecordLatlng.slice(0));
			var markerLng = parseFloat(missingRecordLatlng.slice(7));
			var markerLatlng = new google.maps.LatLng(markerLat, markerLng);
			
			var selectedMode = document.getElementById('mode').value;
			var request = {
				origin: document.getElementById("start").value,
				destination: markerLatlng,
				// Note that Javascript allows us to access the constant
				// using square brackets and a string value as its
				// "property."
				travelMode: google.maps.TravelMode[selectedMode]
			};
			directionsService.route(request, function(response, status) {
				if (status == google.maps.DirectionsStatus.OK) {
					directionsDisplay.setDirections(response);
				}
			});
		}
		
	google.maps.event.addDomListener(window, 'load', initialize);
    </script>
<title>單筆協尋資訊</title>
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>
<div class="row">
    <div class="col-md-8">
		<ol class="breadcrumb myBreadCrumb">
		    <li><a href="<%=request.getContextPath() %>/za101g2/front/missingRecord/listAllMissingRecord.jsp">全部協尋紀錄</a></li>		
		  <li class="active">單筆協尋資訊</li>
		</ol>
	</div>
</div>   

<div class="row">
<div class="col-md-8">
<table class="table table-bordered">
	<thead>
		<tr class="info">
			<th>寵物名字:</th>
			<th>照片:</th>
			<th>走失地點:</th>
			<th>走失日期:</th>
			<th>賞金:</th>
			<th>說明:</th>
			<th>取得回報人數:</th>
			<th>回報人數:</th>
<!-- 			<th>回報:</th> -->
		</tr>
	</thead>
	<tbody>
		<tr align='center' valign='middle'>
			<td><%=missingRecordVO.getPetVO().getName()%></td>
			<td><img src = "<%= request.getContextPath() %>/MissingRecordGifReader?id=${missingRecordVO.id}" width="140px" height="105px"></td>
			<td><%=missingRecordVO.getLocation()%></td>
			<td><%=missingRecordVO.getMissingDate()%></td>
			<td><%=missingRecordVO.getBounty()%></td>
			<td><textarea name="comments" rows="4" cols="20" disabled><%=missingRecordVO.getComments()%></textarea></td>
			<td>
				<input type="hidden" name="recordId" id="recordId" value="${missingRecordVO.id}">
				<button type="button" onclick="loadReplyNumber()" class="btn btn-default">取得回報人數</button>
			</td>
			<td id="replyNumber"></td>
<!-- 			<td> -->
<%-- 				<FORM method="post" action="<%= request.getContextPath() %>/za101g2/front/missingReply/addMissingReply.jsp"> --%>
<%-- 					<input type="hidden" name="recordId" value="${missingRecordVO.id}" id="recordId"> --%>
<!-- 					<input type="submit" value="回報" class="btn btn-info"> -->
<!-- 				</FORM> -->
<!-- 			</td> -->
		</tr>
	</tbody>
</table>
</div>
</div>

	<div class="row">
	<div id="panel" class="col-md-8">
	<font color=deeppink><b>請先輸入出發地點，選擇交通方式後即可導航至走失地點。</b></font>
	<br>
	<b>請輸入出發地點: </b><input type="text" name="start" id="start">
    <b>請選擇交通方式: </b>
    <select id="mode" onchange="calcRoute();">
		<option value="DRIVING">開車</option>
		<option value="TRANSIT">大眾運輸</option>
		<option value="WALKING">走路</option>
		<option value="BICYCLING">腳踏車</option>		
    </select>
    </div>
    </div>
    <br>
    
    <div class="row">
	<div class="col-md-8" id="map-canvas" style="width: 600px; height: 400px;"></div>
<%-- 	<c:if test="${not empty memberVO}"> --%>
		<div class="col-md-4">
			<table class="table table-bordered">
				<thead>
					<tr class="info">
						<th>回報內容:</th>
						<th>回報:</th>
					</tr>
				</thead>
				<tbody>
					<tr align='center' valign='middle'>
						<td><textarea name="comments" rows="4" class="form-control" id="comments" placeholder="請填寫回報內容">${param.comments}</textarea></td>	
						<td>
							<input type="hidden" name="memId" id="memId" value="${memberVO.id}">	
							<button type="button" onclick="insertReply()" id="insertButton" class="btn btn-info">回報</button>
						</td>	
					</tr>
				</tbody>	
			</table>
			
			<div id="showResult"></div>
			<div id="showCountDown"></div>	
		</div>
<%-- 	</c:if>	 --%>
	</div>

<%@ include file="/files/PetribeFoot.file" %>