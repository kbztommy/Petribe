<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import ="za101g2.orderBoard.model.*" %>
<%@page import ="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="viewport" content="width=device-width initial-scale =1">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/normalize.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/backHome.css">
   	<script src="http://code.jquery.com/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
    <script>
    	$(document).ready(function(){
    		$(".orderListItem tr:nth-child(3n)").addClass("warning");					
			$(".orderListItem tr:nth-child(3n+1)").addClass("success");
			$(".orderListItem tr:nth-child(3n+2)").addClass("active");	
    	});
    </script>
</head>
<body>
<%@ include file="/files/fixedNavbar.file" %>
<section class="col-md-10">
	<br>
	<div class="col-md-12">
		<%@ include file="forManaPage1.file" %>
		<div class="panel panel-success">
			<div class="panel-heading">
				<h3 class="panel-title"><b>所有寄養清單: 共<font color=red><%=rowNumber%></font>筆</b></h3>
			</div>
			<div class="panel-body">
				<table class="table table-hover">
					<thead>
						<tr class="info">
							<td>#</td>
							<td>訂單編號</td>
							<td>商店名稱</td>
							<td>商店主人</td>
							<td>寄養人</td>
							<td>金額</td>
							<td>訂單狀態</td>		
							<td></td>					
						</tr>
					</thead>
					<tbody>
						<c:forEach var="orderBoardReport" items="${orderReportedList }" varStatus="s" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
						<tr class="orderListItem">
							<td>${s.count}</td>
							<td>${orderBoardReport.id}</td>
							<td>${orderBoardReport.storeVO.name}</td>
							<td>${orderBoardReport.storeVO.memberVO.firstname}${orderBoardReport.storeVO.memberVO.lastname}</td>
							<td>${orderBoardReport.memberVO.firstname}${orderBoardReport.memberVO.lastname}</td>
							<td>${orderBoardReport.total}元</td>
							<td>${orderBoardMapforP[orderBoardReport.status]}</td>
							<td><a href="<%=request.getContextPath() %>/orderBoardServlet?orderId=${orderBoardReport.id}&action=orderInfoForManage">詳情</a></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:if test="${empty orderReportedList}">
					<div class="row">
						<div class="col-md-8">
							<div class="alert alert-info text-center noResut" role="alert">沒有符合的訂單</div>
						</div>
					</div>
				</c:if>
			</div>
			<div class="panel-footer clearfix">
				<%@ include file="forManaPage2.file" %>
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