<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>      
<%@page import ="za101g2.member.model.*" %>
<%@page import ="za101g2.store.model.*" %>
<%@page import ="za101g2.serviceList.model.*" %>
<%@page import ="java.util.*" %>
<%@page import ="za101g2.orderBoard.model.*" %>
<%    
    response.setHeader("Pragma","no-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);
%>
<jsp:useBean id="today" class="java.util.Date"></jsp:useBean>
<jsp:useBean id="orderListSrv" class="za101g2.orderList.model.OrderListService"/>
<jsp:useBean id="serviceListSrv" class="za101g2.serviceList.model.ServiceListService"/>
<c:set var="dogSrvVO" value="${ serviceListSrv.getOnsaleService(orderBoardVO.storeVO.id,'dog','required')}" scope="page" />
<c:set var="catSrvVO" value="${ serviceListSrv.getOnsaleService(orderBoardVO.storeVO.id,'cat','required')}" scope="page" />
<c:set var="pickSrvVO" value="${ serviceListSrv.getOnsaleService(orderBoardVO.storeVO.id,'dog','pick')}" scope="page" />
<c:set var="dogOrderList" value="${orderListSrv.getAllbyServiceId(dogSrvVO.id,orderBoardVO.id) }"/>
<c:set var="catOrderList" value="${orderListSrv.getAllbyServiceId(catSrvVO.id,orderBoardVO.id) }"/>
<c:set var="pickOrderList" value="${orderListSrv.getAllbyServiceId(pickSrvVO.id,orderBoardVO.id) }"/>
<c:set var="dayOfBoard" value="${1+(orderBoardVO.endDate.getTime()-orderBoardVO.startDate.getTime())/(24*60*60*1000)}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width initial-scale =1">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/normalize.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/mainStyle.css">
    <script src="<%= request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
    <script src="<%= request.getContextPath()%>/js/bootstrap.min.js"></script>   
	<script>
		$(document).ready(function(){		
			var fisrtPic;
			var picCount = 0;
			$("#addPic").click(function(){				
				if(picCount == 0 || $("#pic"+picCount) && $("#pic"+picCount)[0].files[0]){
					picCount++;
					
					$("#inputPanel").append('<input class="hidden" type="file" name="pic'+picCount+'" id="pic'+picCount+'">');
					$("#picPanel").append('<div style="height:100px;float:left"><img class="img-autoW" id="img'+picCount+'"></div>');

					$("#pic"+picCount).change(function(event){						
				    	var input = event.target;
				    	var reader = new FileReader();
				    	reader.onload = function(){
				    		var dataURL = reader.result;		    		
				    		var infoPicResult =document.getElementById('img'+picCount);
				    		infoPicResult.src = dataURL;
				    	};
				    	reader.readAsDataURL(input.files[0]);
				    });					
					$("#pic"+picCount).click();
					if(picCount==1){
						fisrtPic=$("#pic"+picCount);
					}
				}else{
					$("#pic"+picCount).click();
				}
				
			});//end of click
			
			$("#btaddOnService").click(function(){
				if(picCount == 0 ){
					$("#errorMsg").show();					
				}else if(typeof(fisrtPic[0].files[0])=='undefined'){
					
					$("#errorMsg").show();	
					
				}else{
				
					$("#onServiceForm").submit();
				}
			});//end of click
			
			$("#btUpload").click(function(){
				$("#inputPanel").html("");
				$("#picPanel").html("");
				$("textarea").val("");
				$("#errorMsg").hide();		
				picCount=0;
			});
			
			$("#startBoard").click(function(){
				
				$.ajax({
					url:'<%= request.getContextPath()%>/orderBoardServlet',
					type:'post',
					data:'action=storeUpdateStatus&orderId=${orderBoardVO.id}&status=2&storeId=${storeVO.id}',
					dataType:'text',
					success:function(data){
						$("#orderStatus").text(data);
					}					
				});
				$(this).addClass("disabled");
				$("#endBoard").removeClass("disabled")
				$("#btUpload").show();
			});
			
			$("#endBoard").click(function(){
				$.ajax({
					url:'<%= request.getContextPath()%>/orderBoardServlet',
					type:'post',
					data:'action=storeUpdateStatus&orderId=${orderBoardVO.id}&status=3&storeId=${storeVO.id}',
					dataType:'text',
					success:function(data){
						$("#orderStatus").text(data);
					}					
				});
				$(this).addClass("disabled");
			});
			
			$(function () {
				  $('[data-toggle="tooltip"]').tooltip()
			});
			
			
			<% if(Integer.parseInt(((OrderBoardVO)request.getAttribute("orderBoardVO")).getStatus())<2){ %>
				$("#btUpload").hide();
			<% }%>			
			
			<% 
				OrderBoardVO orderBoardVO = (OrderBoardVO)request.getAttribute("orderBoardVO");
			   	long startDate = orderBoardVO.getStartDate().getTime();
			    long endDate = orderBoardVO.getEndDate().getTime()+24*60*60*1000;
			    GregorianCalendar nowdate = new GregorianCalendar();
			    nowdate.set(Calendar.HOUR_OF_DAY,12);
			    nowdate.set(Calendar.MINUTE,0);
			    nowdate.set(Calendar.SECOND,0);
			    long now = nowdate.getTimeInMillis();			    
			    if(now>=startDate && now<=endDate && orderBoardVO.getStatus().equals("1")){
			%>
				$("#startBoard").removeClass("disabled");
			<%}%>
			<% if(now>=startDate && now<=endDate && orderBoardVO.getStatus().equals("2")){%>
			$("#endBoard").removeClass("disabled");
			<%}%>
			
		});//end of ready
	</script>
<title>訂單明細(商家)</title>
</head>
<body>
<%@ include file="addOnServiceModal.file" %>
<%@ include file="/files/PetribeHead.file" %>
	<div class="row">
		<div class="col-md-8">
			<ol class="breadcrumb myBreadCrumb">
			  <li><a href="<%=request.getContextPath() %>/orderBoardServlet?action=StoreOrderList&whichPage=1">商店訂單</a></li>
			  <li class="active">訂單資訊</li>
			</ol>
		</div>
	</div>
	<div class="row">
		<div class="col-md-8">
			<div class="panel panel-primary">
			  <div class="panel-heading">
			    <h3 class="panel-title">訂單明細</h3>
			  </div>
			  <div class="panel-body">
			  		<table class="table table-striped table-hover">
				  		<thead>
				  			<tr>
								<td>訂單編號</td><td>${orderBoardVO.id}</td>
								<td >訂單狀態</td><td id="orderStatus">${orderBoardMapforS[orderBoardVO.status] }</td>			
							</tr>
				  		</thead>
				  		<tbody>
							<tr>	
								<td>寵物主人</td>
								<td>
									<a class="btn-link" href="<%=request.getContextPath()%>/memberHome/memberHome.do?action=memberHome&memId=${orderBoardVO.memberVO.id}">
										${orderBoardVO.memberVO.lastname}${orderBoardVO.memberVO.firstname}(${orderBoardVO.memberVO.nickname })
									</a>				
								</td>	
								<td >主人電話</td><td >${orderBoardVO.memberVO.phone }</td>	
							</tr>
							<tr>
								<td>開始時間</td><td>${dateFormater.format(orderBoardVO.startDate) }</td>
								<td>結束時間</td><td >${dateFormater.format(orderBoardVO.endDate) }</td>
							</tr>
							<tr>
								<td>
									狗的數量
								</td>
								<td>
									${dogOrderList.size()}隻
								</td>
								<td>貓的數量</td>
								<td >
									${catOrderList.size()}隻
								</td>
							</tr>
							<tr>
								<c:choose>
								  <c:when test="${orderBoardVO.custAddress==null ||orderBoardVO.custAddress==''}">
								    <td >接送地址</td><td>無</td>
								  </c:when>
								  <c:otherwise >
								    <td >接送地址</td><td>${orderBoardVO.custAddress}</td>
								  </c:otherwise>
								</c:choose>
							</tr>
							<tr>
								<td>總金額</td><td>${orderBoardVO.total }元</td>			
							</tr>
						</tbody>
					</table>
				</div>
				<c:if test="${orderBoardVO.status >= '1'}">
					<div class="panel-footer clearfix">
						<button class="btn btn-warning pull-left" data-toggle="modal" data-target="#addOnServiceModal" id="btUpload">
							<span class="glyphicon glyphicon-upload"></span>上傳回報
						</button>
						<div class="btn-group pull-right" role="group" >				
						  <button type="button" class="btn btn-info disabled" id="startBoard">寵物入住</button>
						  <button type="button" class="btn btn-info disabled" id="endBoard">已交回寵物</button>
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</div>
	
	<br>
	
	<div class="row">
		<div class="col-md-8">
			<div class="panel panel-warning">
				<div class="panel-heading">
			    	<h3 class="panel-title">住宿與接送服務</h3>
			  	</div>
				<div class="panel-body">
					<table class="table table-striped table-hover">
						<tbody>
							 <c:if test="${!dogOrderList.isEmpty()}">
								  <tr>
									  <td>住宿(狗)</td>
									  <td>${dogSrvVO.price}元</td>
									  <td>${dogOrderList.size()}隻</td>
									  <td><fmt:formatNumber>${dayOfBoard}</fmt:formatNumber>天</td>			 
									  <td >金額:<fmt:formatNumber>${dogSrvVO.price*dogOrderList.size()*dayOfBoard}</fmt:formatNumber>元</td>					  			  
								  </tr>		
								  <c:set var="dogPrice" value="${dogSrvVO.price*dogOrderList.size()*dayOfBoard}"/>		  
							  </c:if>
							  <c:if test="${!catOrderList.isEmpty()}"> 
								  <tr>
								  	  <td>住宿(貓)</td>
									  <td>${catSrvVO.price}元</td>
									  <td>${catOrderList.size()}隻</td>
									  <td><fmt:formatNumber>${dayOfBoard}</fmt:formatNumber>天</td>			 
									  <td>金額:<fmt:formatNumber>${catSrvVO.price*catOrderList.size()*dayOfBoard}</fmt:formatNumber>元</td>	
								  </tr>
								  <c:set var="catPrice" value="${catSrvVO.price*catOrderList.size()*dayOfBoard}"/>	
							  </c:if>	
							    <c:if test="${!pickOrderList.isEmpty()}">  
								  <tr>
								  	<td>接送</td>
								  	<td>${pickSrvVO.price}元</td>
								  	<td >${dogOrderList.size()+catOrderList.size()}隻</td>
								  	<td></td>
								  	<td>金額:${pickSrvVO.price*(dogOrderList.size()+catOrderList.size())}元</td>
								  </tr>
								  <c:set var="pickPrice" value="${pickSrvVO.price*(dogOrderList.size()+catOrderList.size())}"/>		
							  </c:if>
						</tbody>
					</table>
				</div>
				<div class="panel-footer text-right">
					<h4>總共:<fmt:formatNumber>${dogPrice+catPrice+pickPrice}</fmt:formatNumber>元</h4>
				</div>
			</div>
		</div>	
	</div>
	
		
	<div class="row">
		<div class="col-md-8">
			<div class="panel panel-warning">
				<div class="panel-heading">
			    	<h3 class="panel-title">額外服務明細</h3>
			  	</div>
				<div class="panel-body">
					<c:forEach var="pet" items="${orderListSrv.getPetNames(orderBoardVO.id)}">
						<table class="table table-striped table-hover">
							<caption><strong>寵物名稱:${pet}</strong></caption>
							<thead>
								<tr>
									<td>服務名稱</td>
									<td>服務日期</td>
									<td>金額</td>
								</tr>
							</thead>
							<tbody>					
							<c:forEach var="order" items="${orderBoardVO.orderLists}">
								<c:if test="${pet == order.petName && order.serviceListVO.chargeType=='perTime'}">
									<tr>
										<td>${order.serviceListVO.name}</td>
										<td>${dateFormater.format(order.serviceDate)}</td>
										<td>${order.serviceListVO.price}元</td>
									</tr>
								</c:if>
							</c:forEach>
							</tbody>
						</table>
					</c:forEach>
				</div>
				<div class="panel-footer text-right">
					<h4>總共:<fmt:formatNumber>${custSrvPrice}</fmt:formatNumber>元</h4>
				</div>
			</div>
		</div>
	</div>
	
	
	<div class="row" >
		<div class="col-md-8">
			<div class="panel panel-success">
				<div class="panel-heading">
		    		<h3 class="panel-title">回報資訊</h3>
			  	</div>
				<div class="panel-body">
					<c:forEach var="onService" items="${ orderBoardVO.onServices}" varStatus="s">
						<c:if test="${s.index mod 3 == 0}"><div class="row"></c:if>			
							<c:forEach var="pic" items="${onService.storePhotos }" begin="0" end="0">
					 		<div class="col-md-4 text-center">
					 			${dateFormater1.format(onService.releaseDate) }
						    		<a href="<%=request.getContextPath() %>/OnServiceServlet?action=OneOnService&onServId=${onService.id}&from=store" class="thumbnail">			      		
						      			<img class="img-responsive" src="<%=request.getContextPath() %>/PicForStorePhoto?id=${pic.id}">
						      		</a>
				  			</div>
				  			
				  			</c:forEach>
			  			<c:if test="${s.index mod 3 == 2}"></div></c:if>	
		  			</c:forEach>
		  			<c:if test="${orderBoardVO.onServices.size()==0}">
		  				<div class="row">
							<div class="col-md-8">
								<div class="alert alert-success text-center noResut" role="alert">沒有任何回報資訊</div>
							</div>
						</div>	
		  			</c:if>
				</div>
			</div>
 		</div>
	</div>
		
<%@ include file="/files/PetribeFoot.file" %> 
