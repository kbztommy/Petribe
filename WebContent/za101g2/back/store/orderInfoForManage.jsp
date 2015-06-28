<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		<br>
		<div class="col-md-12">
			<div class="panel panel-danger">
				  <div class="panel-heading">
				    <h3 class="panel-title">檢舉事由</h3>
				  </div>
				  <div class="panel-body">
				  	<p class="bg-warning">${orderBoardVO.comments}</p>
				  </div>
				  <div class="panel-footer clearfix">
				  <div class="col-md-8 col-md-offset-2">
				  <a class="btn btn-danger pull-left ${orderBoardVO.status=='6'?'':'disabled'}" href="<%=request.getContextPath() %>/orderBoardServlet?action=updateBystaff&orderId=${orderBoardVO.id}&status=7">檢舉不成立</a>
				  <a class="btn btn-success pull-right ${orderBoardVO.status=='6'?'':'disabled'}" href="<%=request.getContextPath() %>/orderBoardServlet?action=updateBystaff&orderId=${orderBoardVO.id}&status=8" >檢舉成立</a>
				  </div>
				  </div>
			  </div>
		</div>
			  
		<br>
		<div class="col-md-12">
			<div class="panel panel-primary">
			  <div class="panel-heading">
			    <h3 class="panel-title">訂單明細</h3>
			  </div>
			  <div class="panel-body">
			  		<table class="table table-striped table-hover">
				  		<thead>
				  			<tr>
								<td>訂單編號</td><td>${orderBoardVO.id}</td>
								<td>訂單狀態</td><td id="orderStatus">${orderBoardMapforP[orderBoardVO.status]}
								</td>			
							</tr>
				  		</thead>
				  		<tbody>
				  			<tr>	
								<td>商店名稱</td>
								<td>${orderBoardVO.storeVO.name}</td>	
								<td>商店地址</td>
								<td>${orderBoardVO.storeVO.address}</td>
							</tr>
							<tr>	
								<td>商店主人</td>
								<td>
									${orderBoardVO.storeVO.memberVO.lastname}${orderBoardVO.storeVO.memberVO.firstname}				
								</td>	
								<td >商店電話</td><td >${orderBoardVO.storeVO.memberVO.phone }</td>	
							</tr>
							<tr>
								<td>寄養人</td>
								<td>${orderBoardVO.memberVO.lastname}${orderBoardVO.memberVO.firstname}</td>
								<td>寄養人電話</td>
								<td>${orderBoardVO.memberVO.phone }</td>
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
			</div>
		</div>
		
		
		<div class="col-md-12">
			<div class="panel panel-info">
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
		
		
		<div class="col-md-12">
			<div class="panel panel-info">
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
		
		
		<div class="col-md-12">
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
						    		<a href="<%=request.getContextPath() %>/OnServiceServlet?action=onServiceForMana&onServId=${onService.id}" class="thumbnail">				      		
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

	

</section>
	<div style="clear:both;">
		<footer id="the_footer">
			Copyright 2015 Institute For Information Industry ZA101 Petribe | Private Policy 
		</footer>
	</div>
</body>
</html>