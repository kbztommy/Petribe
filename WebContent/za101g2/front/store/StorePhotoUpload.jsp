<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@page import ="za101g2.member.model.*" %>
<%@page import ="za101g2.store.model.*" %>
<%@page import ="za101g2.orderList.model.*" %>
<%@page import ="java.util.*" %>
<%@page import ="za101g2.storePhoto.model.*" %>
<%    
	StorePhotoService StorePhotoSrv = new StorePhotoService();
	pageContext.setAttribute("StorePhotoSrv", StorePhotoSrv);
	
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
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/jquery.fancybox.css?v=2.1.5" type="text/css" media="screen" />
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/jquery.fancybox-thumbs.css" type="text/css" media="screen" />
    <script src="<%= request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
    <script src="<%= request.getContextPath()%>/js/bootstrap.min.js"></script>   
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery.fancybox.pack.js?v=2.1.5"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery.fancybox-thumbs.js"></script>    
	<script>
		$(document).ready(function(){
			$(function () {
				  $('[data-toggle="tooltip"]').tooltip()
			});					
			
			$("#uploadPic").click(function(){
				$("#inputPic").click();
			});			
			
		 $("#inputPic").change(function(event){
			    var formDOM = document.getElementById('formPic');
		    	var formData = new FormData(formDOM);
		    	
		    	$.ajax({
		    		url:"<%= request.getContextPath()%>/StorePhotoServlet",
		    		type:'post',
		    		data:formData,
		    		dataType:'html',
		    		contentType:false,
		    		processData:false,
		    		success:function(data){
		    			if(data==""){
		    				return;
		    			}
		    			$("#storePicPanel").prepend('<div class="col-md-3">'
					+'<a class="fancybox-thumb fancybox.iframe thumbnail" rel="fancybox-thumb" href="<%=request.getContextPath() %>/PicForStorePhoto?id='+data+'" title="${s.index} ">'
						+'<img class="img-responsive" src="<%=request.getContextPath() %>/PicForStorePhoto?id='+data+'" alt="" /></a></div>' );
		    		}
		    	});
		  });//end of file change
		 
	 
		 $(".fancybox-thumb").fancybox({
				
				prevEffect	: 'none',
				nextEffect	: 'none',
				helpers	: {
					title	: {
						type: 'outside'
					},
					thumbs	: {
						width	: 50,
						height	: 50
					}
				},
				maxWidth	: 800,
				maxHeight	: 600,
				fitToView	: false,
				width		: '70%',
				height		: '70%',
				autoSize	: true,
				closeClick	: false,
				openEffect	: 'none',
				closeEffect	: 'none'
			});
		
		  
		  
		});//end of ready
	</script>
<title>新增商店照片</title>
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>
	<div class="row">
		<div class="col-md-8">
			<ul class="nav nav-pills mynav-pills">
			  <li role="presentation"><a href="<%= request.getContextPath()%>/za101g2/front/store/StoreHome.jsp">設定商店</a></li>
			  <li role="presentation"><a href="<%=request.getContextPath() %>/orderBoardServlet?action=StoreOrderList&whichPage=1">商店訂單</a></li>
			  <li role="presentation"><a href="<%= request.getContextPath()%>/za101g2/front/storeCalendar/oneCalendar.jsp">商店日曆</a></li>
			  <li role="presentation" class="active"><a href="<%= request.getContextPath()%>/za101g2/front/store/StorePhotoUpload.jsp">相簿</a></li>
			  <li role="presentation"><a href="<%= request.getContextPath()%>/za101g2/front/storeBoard/StoreBoardForStroe.jsp">留言板</a></li>
			</ul>
		</div>
	</div>
	<div class="row">		
		<div class="col-md-8">
			<label for="uploadPic">上傳照片</label>
			<button class="btn btn-lg"  data-toggle="tooltip" data-placement="right" title="上傳照片" id="uploadPic">
				<span class="glyphicon glyphicon-picture"></span>
			</button>
		</div>
		<form class="form-group"  id="formPic"  method="post">
	    	<input type="file" class="hidden" name="pic" id="inputPic">
	    	<input type="hidden" name="storeId" value="${storeVO.id }">
	    	<input type="hidden" name="action" value="addPic">	  	            	
	    </form>            	
	</div>
	<br>
	<div class="row">
		<div class="col-md-8" id="storePicPanel">
			<c:forEach var="pic" items="${StorePhotoSrv.getStorePic(storeVO.id)}" varStatus="s">
				<div class="col-md-3">
					<a class="fancybox-thumb fancybox.iframe thumbnail" rel="fancybox-thumb" href="<%=request.getContextPath() %>/PicForStorePhoto?id=${pic.id}" title="${s.index} ">
						<img class="img-responsive" src="<%=request.getContextPath() %>/PicForStorePhoto?id=${pic.id}" alt="" />
					</a>
				</div>				
			</c:forEach>			
		</div>	
	</div>
<%@ include file="/files/PetribeFoot.file" %> 
