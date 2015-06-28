<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@page import ="za101g2.member.model.*" %>
<%@page import ="za101g2.store.model.*" %>
<%@page import ="za101g2.orderList.model.*" %>
<%@page import ="java.util.*" %>
<%@page import ="za101g2.orderBoard.model.*" %>
<%    
    response.setHeader("Pragma","no-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);
    
%>
<jsp:useBean id="orderBoardSrv" class="za101g2.orderBoard.model.OrderBoardService"/>
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
			<ul class="nav nav-pills mynav-pills">
			  <li role="presentation"><a href="<%= request.getContextPath()%>/za101g2/front/store/StoreHome.jsp">設定商店</a></li>
			  <li role="presentation" class="active"><a href="<%=request.getContextPath() %>/orderBoardServlet?action=StoreOrderList&whichPage=1">商店訂單</a></li>
			  <li role="presentation"><a href="<%= request.getContextPath()%>/za101g2/front/storeCalendar/oneCalendar.jsp">商店日曆</a></li>
			  <li role="presentation"><a href="<%= request.getContextPath()%>/za101g2/front/store/StorePhotoUpload.jsp">相簿</a></li>
			  <li role="presentation"><a href="<%= request.getContextPath()%>/za101g2/front/storeBoard/StoreBoardForStroe.jsp">留言板</a></li>
			</ul>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-8">
			<div class="btn-group btn-group-justified" role="group" aria-label="Order List Button">
				  <div class="btn-group" role="group">
				    <a type="button" class="btn btn-warning" href="<%=request.getContextPath() %>/orderBoardServlet?action=StoreOrderList&whichPage=1" >所有的寄養</a>
				  </div>
				  <div class="btn-group" role="group">
				    <a type="button" class="btn btn-warning" href="<%=request.getContextPath() %>/orderBoardServlet?action=StoreOrderList&whichPage=1&status=2">進行中寄養</a>
				  </div>
				  <div class="btn-group" role="group">
				    <a type="button" class="btn btn-warning"  href="<%=request.getContextPath() %>/orderBoardServlet?action=StoreOrderList&whichPage=1&status=1">準備中的寄養</a>
				  </div>
		  	</div>
		</div>	
	</div>
	
	<c:if test="${not empty orderBoardList}">		
		<div class="row">
			<div class="col-md-8">
				<%@ include file="forStorePage1.file" %>
				<div class="panel panel-default">
			    	<div class="panel-heading"><b>符 合 查 詢 條 件 如 下 所 示: 共<font color=red><%=rowNumber%></font>筆</b></div>
			    		<div class="panel-body">
						    <table class="table table-striped table-hover text-center">
							<thead>
								<tr>
									<td>#</td>
									<td>訂單編號</td>
									<td>寄養人</td>						
									<td>開始日期</td>
									<td>結束日期</td>
									<td>寵物數量</td>
									<td>金額</td>
									<td>狀態</td>
									<td></td>
								</tr>
							</thead>					
							<tbody class="orderListItem">
								<c:forEach var="order" items="${orderBoardList}" varStatus="s" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
								<tr>
									<td>${s.count}</td>
									<td>${order.id}</td>
									<td>${order.memberVO.lastname}${order.memberVO.firstname}</td>
									<td>${dateFormater.format(order.startDate)}</td>
									<td>${dateFormater.format(order.endDate)}</td>
									<td>${orderListSrv.getPetNames(order.id).size() }</td>
									<td>${order.total}元</td>
									<td>${orderBoardMapforS[order.status] }</td>
									<td>
										<a class="btn btn-link" href="<%=request.getContextPath()%>/orderBoardServlet?action=OneStoreOrderInfo&orderId=${order.id}">詳細資訊</a>
									</td>
								</tr>	
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="panel-footer clearfix">
						<%@ include file="forStorePage2.file" %>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${empty orderBoardList}">
		<div class="row">
			<div class="col-md-8">
				<div class="alert alert-info text-center noResut" role="alert">沒有任何訂單</div>
			</div>
		</div>
	</c:if>
<%@ include file="/files/PetribeFoot.file" %> 
