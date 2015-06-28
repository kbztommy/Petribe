<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="za101g2.missingrecord.model.*"%>
<%-- 此頁採用 JSTL 與 EL 取值 --%>

<%  
	List<MissingRecordVO> list = (ArrayList<MissingRecordVO>) request.getAttribute("missingRecordVO");
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
<!--google maps -->
	<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAZkW715YmuqneoWP8WAN5k0qkuYpFMBi4"></script>
    <script type="text/javascript">
<%--     	var geocoder;--%>
    	var map;
		function initialize() {
<%-- 			geocoder = new google.maps.Geocoder();--%>
			var latlng = new google.maps.LatLng(23.968, 120.961);
			
			var mapOptions = {
				zoom: 7,
				center: latlng
			};
			
			map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
		
		<%-- 設定搜尋到的城市為地圖中點，取context的Map --%>		
			var selectedCity = '${param.city}';
			if (selectedCity != 'allCity') {
				<c:forEach var="cityList" items="${applicationScope.cityLatlng}">
				var cityName = '${cityList.key}';
				var cityLatlng = '${cityList.value}';
				if (cityName == selectedCity) {
					var centerLat = parseFloat(cityLatlng.slice(0));
					var centerLng = parseFloat(cityLatlng.slice(8));
					var centerLatlng = new google.maps.LatLng(centerLat, centerLng);
					map.setCenter(centerLatlng);
					map.setZoom(10);
				}
				</c:forEach>
			}			
				
		<%-- 設定搜尋到的城市為地圖中點，用google maps geocode --%>
<%--			var selectedCity = '${param.city}';
			if (selectedCity != 'allCity') {
				geocoder = new google.maps.Geocoder();
				geocoder.geocode( {'address': selectedCity}, function(results, status) {
					
					if (status == google.maps.GeocoderStatus.OK) {
						map.setCenter(results[0].geometry.location);
						alert(results[0].geometry.location);
						map.setZoom(10);
					} else {
						alert("Geocode was not successful for the following reason: " + status);
					}
		
				});
			}--%>
		
		<%-- marker走失地點及加入infowindow，取request的List --%>
		<c:forEach var="missingRecordVO" items="${list}">
		<c:if test="${missingRecordVO.status=='協尋中'}">
		
			var contentString = '<h3>協尋資訊</h3>' +
					'走失日期:' + 
					'${missingRecordVO.missingDate}<br>' +
					'寵物名字:' +
					'${missingRecordVO.petVO.name}'
						
			var missingRecordLatlng = '${missingRecordVO.latlng}';
			var markerLat = parseFloat(missingRecordLatlng.slice(0));
			var markerLng = parseFloat(missingRecordLatlng.slice(7));
			var markerLatlng = new google.maps.LatLng(markerLat, markerLng);
			
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
			
		</c:if>				
		</c:forEach>
		
			function bindInfoWindow(marker, map, infowindow) {
				google.maps.event.addListener(marker, 'click', function() {
					infowindow.close();
					infowindow.open(map,marker);		
				});
			}
		
		<%-- marker走失地點及加入infowindow，用google maps geocode --%>
<%--		<c:forEach var="missingRecordVO" items="${list}">
				
			var address = '${missingRecordVO.location}';
			geocoder.geocode( {'address': address}, function(results, status) {
				if (status == google.maps.GeocoderStatus.OK) {
					var contentString = '<h3>協尋資訊</h3>' +
							'走失日期:' + 
							'${missingRecordVO.missingDate}<br>' +
							'寵物名字:' +
							<c:forEach var="petVO" items="${petSvc.all}">
								<c:if test="${missingRecordVO.petId==petVO.id}">
									'${petVO.name}';
								</c:if>
							</c:forEach>
					
					var image = 'images/pets.png';
					var marker = new google.maps.Marker({
						map: map,
						position: results[0].geometry.location,
						icon: image,
						animation: google.maps.Animation.DROP,
						title: '${missingRecordVO.location}'
					});
					
					var infowindow = new google.maps.InfoWindow({
						content: contentString,
						position: results[0].geometry.location
					});
					
					google.maps.event.addListener(marker, 'click', function() {
						infowindow.open(map,marker);
					});		
				} else {
					alert("Geocode was not successful for the following reason: " + status);
				}
			});
		
		</c:forEach>--%>
		
		}

	google.maps.event.addDomListener(window, 'load', initialize);
	</script>
<title>條件搜尋結果</title>
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>

<div class="row">
    <div class="col-md-8">
		<ol class="breadcrumb myBreadCrumb">
		  <li class="active">全部協尋紀錄</li>
		</ol>
	</div>
</div>  

<div class="row">
<FORM method="post" action="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do" name="form1" role="form" class="col-md-6">
	<div class="form-inline">
		<label>走失地點:</label>
		<select size="1" name="city" class="form-control">
			<option value="allCity">--請選擇縣市--</option>
			<c:forEach var="city" items="${applicationScope.cityList}">
				<option value="${city}" ${(param.city==city) ? 'selected':''}>${city}</option>
			</c:forEach>
		</select>

		<label>有無賞金:</label>
		<select size="1" name="bounty" class="form-control">
			<option value="allBounty">--請選擇有無賞金--</option>
			<option value="bounty" ${(param.bounty=='bounty') ? 'selected':''}>有賞金</option>
			<option value="noBounty" ${(param.bounty=='noBounty') ? 'selected':''}>無賞金</option>
		</select>
			
		<input type="hidden" name="action" value="getAll_Search">
		<input type="submit" value="搜尋" class="btn btn-info">		
	</div>
</FORM>
</div>
<br>

<div class="row">
<div class="col-md-12" id="map-canvas" style="width: 600px; height: 400px;"></div>
</div>
<br>

<!-- 錯誤列表 -->
<c:if test="${not empty errorMsgs}">
	<font color='deeppink'>溫馨提醒:
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message.value}</li>
		</c:forEach>
	</ul>	
	</font>
</c:if>

<div class="row">
<div class="col-md-8">
<table class="table table-hover" >
	<thead>
		<tr class="info">
			<th>寵物名字:</th>
			<th>照片:</th>	
			<th>走失地點:</th>
			<th>走失時間:</th>
			<th>賞金:</th>
			<th>說明:</th>
			<th>內容:</th>
		</tr>
	</thead>
	<c:forEach var="missingRecordVO" items="${list}">
	<c:if test="${missingRecordVO.status=='協尋中'}">
		<tbody>
			<tr>
				<td>${missingRecordVO.petVO.name}</td>			
				<td><img src = "<%= request.getContextPath() %>/MissingRecordGifReader?id=${missingRecordVO.id}" width="140px" height="105px"></td>			
				<td>${missingRecordVO.location}</td>
				<td>${missingRecordVO.missingDate}</td>
				<td>${missingRecordVO.bounty}</td>
				<td><textarea name="comments" rows="4" cols="20" disabled>${missingRecordVO.comments}</textarea></td>
					
				<td>
					<FORM method="post" action="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do">
						<input type="hidden" name="recordId"  value="${missingRecordVO.id}">
						<input type="submit" value="內容" class="btn btn-info">
						<input type="hidden" name="action" value="getOne_For_Display">	
					</FORM>
				</td>
			</tr>
		</tbody>
	</c:if>	
	</c:forEach>
</table>
</div>
</div>

<%@ include file="/files/PetribeFoot.file" %>
