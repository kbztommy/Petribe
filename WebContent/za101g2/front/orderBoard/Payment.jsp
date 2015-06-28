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
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width initial-scale =1">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/normalize.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap-datepicker.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap-datepicker.standalone.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap-datepicker3.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap-datepicker3.standalone.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/mainStyle.css">    
    <script src="<%= request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
    <script src="<%= request.getContextPath()%>/js/bootstrap.min.js"></script>
    <script src="<%= request.getContextPath()%>/js/bootstrap-datepicker.min.js"></script>
    <script src="<%= request.getContextPath()%>/locales/bootstrap-datepicker.zh-TW.min.js"></script>
    <script>
    $(function(){
    	$('#expireDate').datepicker({
		    format: "mm/yyyy",
		    startDate: "today",
		    minViewMode: 1,
		    language: "zh-TW"		   
    	});
    })
    	
    </script>
    <title>付款</title>    
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>
<div class="row">
    <div class="col-md-8">
		<ol class="breadcrumb myBreadCrumb">
		  <li><a href="<%= request.getContextPath()%>/za101g2/front/store/SearchStore.jsp">尋找寄養</a></li>	
		  <li><a href="<%=request.getContextPath()%>/StoreServlet?action=lookOverStore&storeId=${orderBoardVO.storeVO.id}">商店資訊</a></li>
		  <li><a href="<%=request.getContextPath() %>/orderBoardServlet?action=startOrder&otherStoreId=${orderBoardVO.storeVO.id}">訂購寄養</a></li>					
		  <li class="active">明細與付費</li>
		</ol>
	</div>
</div> 

<div class="row">
	<div class="col-md-8">
		<div class="panel panel-primary">
		  <div class="panel-heading">
		    <h3 class="panel-title">訂單資訊</h3>
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
							<td>商店名稱</td>
							<td>
								${orderBoardVO.storeVO.name}				
							</td>	
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
							<td>開始時間</td><td>${dateFormater.format(orderBoardVO.startDate) }</td>
							<td>結束時間</td><td >${dateFormater.format(orderBoardVO.endDate) }</td>
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
</div>	


<br><br>

<div class="row">
<c:if test="${not empty errorMsgs}">
	<div class="col-md-4" >
		<div class="alert alert-danger" role="alert">
			<ul>
				<c:forEach var="message" items="${errorMsgs.values()}">
					<strong>${message}</strong>
					<br>
				</c:forEach>
			</ul>
		</div>		
	</div>
</c:if>
</div>
<div class="row">
	<div class="col-md-8">
		<div class="panel panel-success">
			  <div class="panel-heading">
			    <h3 class="panel-title">信用卡繳費</h3>
			  </div>
			  <div class="panel-body">
					<form class="form-horizontal" method="post" action="<%=request.getContextPath() %>/orderBoardServlet">
					  <div class="form-group">
					    <label class="col-sm-2 control-label">信用卡卡號<font color="red">*</font></label>
					    <div class="col-sm-2">
					      <input type="text" class="form-control" id="cridno1" maxlength="4" name="cridNo">
					    </div>
					    <div class="col-sm-2">
					      <input type="text" class="form-control" id="cridno2" maxlength="4" name="cridNo">
					    </div>
					    <div class="col-sm-2">
					      <input type="text" class="form-control" id="cridno3" maxlength="4" name="cridNo">
					    </div>
					    <div class="col-sm-2">
					      <input type="text" class="form-control" id="cridno4" maxlength="4" name="cridNo">
					    </div>
					  </div>
					  <div class="form-group">	  	
					    <label  class="col-sm-2 control-label">卡片有效日期<font color="red">*</font></label>
					  <div class="input-group date col-sm-4" id="expireDate" >
			  		  	<input type="text" class="form-control" name="expireDate" readonly><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
					  </div>
					  
					  </div>
					  <div class="form-group">
					     <label  class="col-sm-2 control-label">簽名欄末三碼<font color="red">*</font></label>
					     <div class="col-sm-2">
					      <input type="text" class="form-control" id="nameCheckNo" maxlength="3" name="nameLastNo">
					    </div>
					  </div>
					  <div class="form-group">
					    <div class="col-sm-offset-6 col-sm-6">
					      <a type="button" class="btn btn-default" href="<%=request.getContextPath() %>/orderBoardServlet?orderId=${orderBoardVO.id}&action=delOrder">取消</a>
					      &nbsp;
					      <button type="submit" class="btn btn-warning" >確定繳費</button>
					    </div>
					  </div>
				
					  <input type="hidden" name ="orderId" value="${orderBoardVO.id}">
					  <input type="hidden" name ="action" value="paymentValidation" >
					</form>
			</div>
		</div>
	</div>
</div>


<br><br>
<div class="row">
	<div class=col-md-8>
		<div class="panel panel-warning">
			  <div class="panel-heading">
			    <h3 class="panel-title">訂單明細</h3>
			  </div>
			  <div class="panel-body">
				<table class="table table-striped">
					<thead>				
						<tr>
							<th>服務名稱</th>
							<th>寵物</th>
							<th>服務日期</th>
							<th>金額</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="orderListVO" items="${orderBoardVO.orderLists}">
							<tr>						
								<c:if test="${orderListVO.serviceListVO.chargeType =='pick'}">
									<td>接送(${orderListVO.serviceListVO.name}公里內)</td>
								</c:if>
								<c:if test="${orderListVO.serviceListVO.chargeType !='pick'}">
									<td>${orderListVO.serviceListVO.name}</td>
								</c:if>
								<td>${orderListVO.petName}</td>
								<td>${orderListVO.serviceDate }</td>
								<td>${orderListVO.serviceListVO.price }元</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="panel-footer clearfix">
				<div class="col-md-6 pull-right text-right"><h4>總金額:${orderBoardVO.total }元</h4></div>
			</div>
		</div>	
	</div>		
</div>





<%@ include file="/files/PetribeFoot.file" %>   