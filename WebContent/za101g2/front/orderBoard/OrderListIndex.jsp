<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@page import ="za101g2.member.model.*" %>
<%@page import ="za101g2.store.model.*" %>
<%@page import ="za101g2.serviceList.model.*" %>
<%@page import ="java.util.*" %>
<%@page import ="za101g2.storeCalendar.model.*" %>
<%    
    response.setHeader("Pragma","no-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);
%>
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
			var petFlag; 
			var srvName;
			var srvId;
			var srvPetType;
			$("#errorMsgs").text("");
			
			$(".btModal").click(function(){
				$(".selectDate").hide();
				$(".selectDate").removeClass("rest open");
				$(".selectDate").addClass("rest");
				petFlag = $(this).parent().parent().parent().attr("id");
				srvName = $(this).parent().siblings().first().text();
				srvId = $(this).parent().siblings().first().attr("class");
				srvPetType = $(this).parent().parent().attr("class");
				
				$(".selectDate").each(function(){
					dateStr = $(this).text();
					var liStr = "#"+petFlag+"-ul [id="+dateStr+"]";
					var spanStr = 'span[class="'+srvId+'"]';
					var liStrChild = $(liStr).find(spanStr);
					if(liStrChild.length==0){
						$(this).show();
					}
				});
			});
				
			$(".selectDate").click(function(){
				$(this).toggleClass("rest").toggleClass("open");
			});
			
			$("#setSrvInDate").click(function(){
				
				var count = $(".selectDate").filter(".open");				
				for(i=0;i<count.length;i++){
					var y = count.eq(i).text();
					var liStr = "#"+petFlag+"-ul [id="+y+"]";
					//var formStr = "#"+petFlag+"-form";
					var spanStr = 'span[class="'+srvId+'"]';
					var liStrChild = $(liStr).find(spanStr);
					
					if(liStrChild.length==0){
						//更新已新增的服務
						$(liStr).append('<li class="list-group-item"><span class="'+srvId+'"></span>'+srvName+'</li>');
						//更新form表單					
						$('#orderForm').append('<input type="hidden" name="custOrderList" value="'+srvId+'#$%'+petFlag+'#$%'+y+'" class="'+petFlag+srvPetType+'">');
					}
				}			
				
				 $('#selectSrvDateModal').modal('hide');
			});
			
			$(".petType").change(function(){				
				var strclass = $(this).attr("id").split("-");
				var tbody = "#"+strclass[0]+">tr[class='"+$(this).val()+"']";
				var liStr =  "#"+strclass[0]+"-ul>div>ul"
				var formStr = '#orderForm>input[class^="'+strclass[0]+'"]';
				$("#"+strclass[0]+">tr").hide();
				$(liStr).text("");
				$(formStr).remove();
				$(tbody).show();				
			});
			$(".petType").change();			

			
			$("#btSubmitOrd").click(function(){				
				$("#errorMsgs").text("");								
				$.ajax({
					type:'post',
					url:$("#orderForm").attr("action"),
					data:$("#orderForm").serialize(),
					dataType:'json',
					success:function(data){
						//alert(jQuery.isEmptyObject(data));
						if( jQuery.isEmptyObject(data)){	
							$("#action").val("insertOrder");
								$("#orderForm").submit();
						}else{
							$("#errorMsgs").addClass('alert alert-danger');
							$.each(data,function(key,value){									
									$.each(value,function(k,v){
										$("#errorMsgs").append(v+'<br>');
									});									
							});//end of each								
						}				
					}				
				});	//end of ajax;					
			});//end of click
				
			
		});
	</script>
<title>填寫資訊</title>
</head>
<body>
<%@ include file="/za101g2/front/orderBoard/SelectSrvDateModal.file"  %>
<%@ include file="/files/PetribeHead.file" %>

	<div class="row">
	    <div class="col-md-8">
			<ol class="breadcrumb myBreadCrumb">
			  <li><a href="<%= request.getContextPath()%>/za101g2/front/store/SearchStore.jsp">尋找寄養</a></li>	
			  <li><a href="<%=request.getContextPath()%>/StoreServlet?action=lookOverStore&storeId=${otherStoreVO.id}">商店資訊</a></li>
			  <li><a href="<%=request.getContextPath() %>/orderBoardServlet?action=startOrder&otherStoreId=${otherStoreVO.id}">訂購寄養</a></li>					
			  <li class="active">填寫資訊</li>
			</ol>
		</div>
    </div> 
	
	<div class="row">
		<div class="col-md-8">
			<div class="custInfoForOrder">
			</div>
		</div>
	</div>
	<div class="row">
	<div class="col-md-8">
		<div id="errorMsgs"></div>
		<form id="orderForm" action="<%=request.getContextPath() %>/orderBoardServlet" method="post">
			<c:forEach var="pet" begin="1" end="${custPetQty}"> 
				<div class="panel panel-warning">
			 		<div class="panel-heading"><h3>寵物編號${pet}</h3></div>
			  		<div class="panel-body">
				  		<div class="form-group">
				  			<label for="petType${pet}">寵物類別:<font color="red">*</font></label>
				    		<select name="petType" class="form-control petType" id="pet${pet}-type">
								<c:if test="${requiredDogSrv!=null}">
									<option value="dog">狗</option>
								</c:if>
								<c:if test="${requiredCatSrv!=null}">
									<option value="cat">貓</option>
								</c:if>
							</select>
						</div>	
						<div class="form-group">
							<label for="petname${pet}">寵物名稱:<font color="red">*</font></label>
							<input type="text" name="petName" class="form-control" id="petname${pet}" maxlength="10">
						</div>
						<table class="table">
							<caption><h3>可新增的額外服務</h3></caption>
							<thead>
								<tr>
									<th>名稱</th>	
									<th>計價方式</th>							
									<th>費用</th>								
									<th></th>
								</tr>
							</thead>
							<tbody id="pet${pet}">
								<c:forEach var="custSrv" items="${allcustService}">
									<tr class="${custSrv.petType}">
										<td class="${custSrv.id}">${custSrv.name}</td>
										<td>${serviceListMap[custSrv.chargeType]}</td>
										<td>${custSrv.price}</td>									
										<td><button type="button" class="btn btn-info btn-sm btModal" data-toggle="modal" data-target="#selectSrvDateModal"><span class="glyphicon glyphicon-plus">新增</span></button></td>
									</tr>
								</c:forEach>							
							</tbody>						
						</table>
						<h3>住宿日期</h3>
						<div id="pet${pet}-ul">               			
		                	<c:forEach var="selectDate" items="${startCal}">
								<div class="col-xs-3">
									${selectDate}
									<ul class="list-group" id="${selectDate}">
																	 
									</ul>
								</div>
							</c:forEach>
						</div> 					
			  		</div>
				</div>					
			</c:forEach>
			<input type="hidden" name="action" value="insertOrderValidation" id="action">
			<input type="hidden" name="otherStoreId" value="${otherStoreVO.id}"> 
			<input type="hidden" name="startDateStr" value="${startDateStr}">
			<input type="hidden" name="endDateStr" value="${endDateStr}">
			<input type="hidden" name="custAddress" value="${custAddress}">					
			</form >
		</div>		
	</div>
	<div class="row">
		<div class="col-md-8">
			<button class="btn btn-warning btn-lg center-block" id="btSubmitOrd">下一步</button>
		</div>
	</div>
	

<%@ include file="/files/PetribeFoot.file" %> 
