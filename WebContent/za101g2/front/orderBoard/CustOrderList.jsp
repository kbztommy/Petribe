<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
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
<jsp:useBean id="orderBoardSrv" class="za101g2.orderBoard.model.OrderBoardService" scope="page"/>
<jsp:useBean id="orderListSrv" class="za101g2.orderList.model.OrderListService" scope="page"/>

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
			$(".orderListItem tr:nth-child(3n)").addClass("warning");					
			$(".orderListItem tr:nth-child(3n+1)").addClass("info");
			$(".orderListItem tr:nth-child(3n+2)").addClass("active");						
			
		});//end of ready
	</script>
<title>查詢訂單</title>
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>
	
	<div class="row">
		<div class="col-md-8">
			<ol class="breadcrumb myBreadCrumb">
			  <li class="active">我的寄養</li>
			</ol>
		</div>
	</div>
	
	
	<c:if test="${not empty orderBoardSrv.getMemberOrder(memberVO.id) }">
	
	
	<div class="row">
			<div class="col-md-8">
				<%@ include file="forCustPage1.file" %>
				<div class="panel panel-default">				
			    	<div class="panel-heading">
			    		<h3 class="panel-title"><b>所有寄養清單: 共<font color=red><%=rowNumber%></font>筆</b></h3>
			    	</div>
			    	<div class="panel-body">
					    <table class="table table-striped table-hover text-center">
						<thead>
							<tr>
								<td>#</td>
								<td>訂單編號</td>
								<td>寄養商店</td>						
								<td>開始日期</td>
								<td>結束日期</td>
								<td>寵物數量</td>
								<td>金額</td>
								<td>狀態</td>
								<td></td>
							</tr>
						</thead>					
						<tbody class="orderListItem">
							
							<c:forEach var="order" items="${orderBoardSrv.getMemberOrder(memberVO.id)}" varStatus="s" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
							<tr>
								<td>${s.count}</td>
								<td>${order.id}</td>
								<td>${order.storeVO.name}</td>
								<td>${dateFormater.format(order.startDate)}</td>
								<td>${dateFormater.format(order.endDate)}</td>
								<td>${orderListSrv.getPetNames(order.id).size() }</td>
								<td>${order.total}元</td>
								<td>${orderBoardMapforC[order.status] }</td>
								<td>
									<a class="btn btn-link" href="<%=request.getContextPath()%>/orderBoardServlet?action=OneCustOrderInfo&orderId=${order.id}">詳細資訊</a>
								</td>
							</tr>	
							</c:forEach>
						</tbody>
					</table>
					</div>
					<div class="panel-footer clearfix">
						<%@ include file="forCustPage2.file" %>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${empty orderBoardSrv.getMemberOrder(memberVO.id)}">
		<div class="row">
			<div class="col-md-8">
				<div class="alert alert-info text-center noResut" role="alert">沒有任何訂單</div>
			</div>
		</div>
	</c:if>
<%@ include file="/files/PetribeFoot.file" %> 
