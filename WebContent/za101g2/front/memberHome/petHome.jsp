<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="za101g2.photo.model.*"%>

<html>

<head>
<meta name="viewport" content="width=device-width initial-scale =1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/mainStyle.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/flexslider.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/petHome.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%= request.getContextPath()%>/js/jquery.flexslider-min.js"></script>
<title>${petVO.species}${petVO.name}的家</title>
<script type="text/javascript">
$(window).load(function() {
	  // The slider being synced must be initialized first
	  $('#carousel').flexslider({
	    animation: "slide",
	    controlNav: true,
	    animationLoop: true,
	    slideshow: false,
	    itemWidth: 210,
	    itemMargin: 5,
	    asNavFor: '#slider'
	  });
	 
	  $('#slider').flexslider({
	    animation: "slide",
	    controlNav: false,
	    animationLoop: true,
	    slideshow: true,
	    smoothHeight: false,
	    sync: "#carousel"
	  });
	});
</script>
</head>

<body>

						<c:if test="${not empty memberVO}"><c:if test="${memberVO.id==memberView.id}"><!--已登入換自己的圖像-->
						<div class="modal fade" id="petIcon" tabindex="-1" role="dialog" aria-labelledby="reportLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
										</button>
										<h4 class="modal-title" id="reportLabel">上傳寵物大頭貼</h4>
									</div>
									<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/photo/photo.do" name="photo" enctype="multipart/form-data">
										<div class="modal-body">
											<input type="file" name="photoFile" id="photoFile"> 
											<input type="hidden" name="petId" value="${petVO.id}">
											<input type="hidden" name="action" value="insertPetPhoto">
										</div>
										<div class="modal-footer">
											<button type="submit" class="btn btn-primary">確定上傳</button>
											<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
										</div>
									</FORM>
								</div>
							</div>
						</div>
						</c:if></c:if>

<%@ include file="/files/PetribeHead.file" %>					
	<div class="col-md-8">
		<div class="panel panel-default">
  			<div class="panel-heading journalFrameTitle">${memberView.nickname }的${petVO.name}</div>
  			<div class="panel-body">
  					<div class="col-md-4" style="height:270px">
  					<c:if test="${memberVO.id!=memberView.id}">
						<c:if test="${empty petVO.icon}">
								<img  class="petIcon" src="<%=request.getContextPath()%>/images/petIcon1.png" >
						</c:if>
						<c:if test="${not empty petVO.icon}">
								<img class="petIcon" src="<%=request.getContextPath()%>/Pet/PetIconDisplay?id=${petVO.id}" >			
						</c:if>
					</c:if>
					<c:if test="${not empty memberVO}">
						<c:if test="${memberVO.id==memberView.id}">
							<c:if test="${empty petVO.icon}">
								<div style="height: 200px;">
									<a href="#"  data-toggle="modal" data-target="#petIcon">
										<img  class="petIcon" src="<%=request.getContextPath()%>/images/petIcon1.png" >					
									</a>
								</div>
							</c:if>
							<c:if test="${not empty petVO.icon}">
								<div style="height: 200px;" >
									<a href="#"  data-toggle="modal" data-target="#petIcon">
										<img class="petIcon" src="<%=request.getContextPath()%>/Pet/PetIconDisplay?id=${petVO.id}" >
									</a>
								</div>
							</c:if>
						</c:if>
					</c:if>
					
						<c:if test="${petVO.status=='1'}">
							<div>
								<FORM method="post" class="form-inline" action="<%= request.getContextPath() %>/za101g2/front/missingRecord/missingRecord.do">
									&nbsp;&nbsp;&nbsp;<font size='4' color='#01669F'><B>我失蹤了：</B></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="hidden" name="petId"  value="${petVO.id}">
									<input type="submit" value="查看失蹤資料" class="btn btn-info missingBtn">
									<input type="hidden" name="action" value="getOne_Pet_Id">	
								</FORM>
							</div>
						</c:if>
					</div>
  				
					<div class="col-md-6">
						<h4>名字：&nbsp;&nbsp;&nbsp;&nbsp;${petVO.name}</h4>
						<h4>物種：&nbsp;&nbsp;&nbsp;&nbsp;${petVO.species}</h4>
						<h4><a href="<%=request.getContextPath()%>/QRCodeServlet?petId=${petVO.id}"  class="btn btn-success ${memberVO.id==memberView.id?'':'hidden'}">取得寵物QR Code</a></h4>
						<br>
						<c:if test="${not empty memberVO}"><c:if test="${memberVO.id==memberView.id}"><!--已登入看自己的PET頁面-->
							<div class="col-md-6">
								<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/photo/photo.do" name="photo" enctype="multipart/form-data" class="form-horizontal">
								<div class="form-group">
									<label for="photoFile">上傳${petVO.name}的圖片：</label>
									<input type="file" name="photoFile" id="photoFile">
								</div>	 
									<input type="hidden" name="action" value="insertFromPetHome">
									<input type="hidden" name="petId" value="${petVO.id}">
								<div class="form-group">
									<button type="submit" class="btn btn-default">確定上傳</button>
								</div>								
								</FORM>
							</div>
						</c:if></c:if><!--已登入看自己的PET頁面-->	
					</div>
					
					
			</div>
		</div>
		<% List<PhotoVO> photoList = (List<PhotoVO>)request.getAttribute("photoList"); %>
		<%
			List<String> journalFirstPhoto = (List<String>) request.getAttribute("journalFirstPhoto");
			List<String> articleNonePhoto = (List<String>) request.getAttribute("articleNonePhoto");
		%>
		
		<div class="panel panel-default"><!--第一列-->	
  			<div class="panel-body" >			
			<c:if test="${not empty photoList}"><% if(photoList.size()>2){ %>
				<div  id="slider" class="flexslider" ><ul class="slides">
					<c:forEach var="photoVO" items="${photoList}">
						<li>
	  						<img  style="max-height:500px;" src="<%=request.getContextPath()%>/Photo/PhotoDisplay?id=${photoVO.id}" />
	  					</li>
					</c:forEach>
				</ul></div>
				<div id="carousel" class="flexslider"><ul class="slides">
					<c:forEach var="photoVO" items="${photoList}">
						<li>
	  						<img style="max-height:130px;" src="<%=request.getContextPath()%>/Photo/PhotoDisplay?id=${photoVO.id}" />
	  					</li>
					</c:forEach>
				</ul></div>
			<% } %></c:if>
			<c:if test="${not empty photoList}"><% if(photoList.size()<=2){ %>
				<div class="col-md-12 photoTitle">${petVO.species}${petVO.name}的照片</div>
				<div class="col-md-12 photoList">
					<c:forEach var="photoVO" items="${photoList}">
						<div style="text-align: center;">
		  					<img src="<%=request.getContextPath()%>/Photo/PhotoDisplay?id=${photoVO.id}" style="max-height:215px;"/>
						</div>
					</c:forEach>
				</div>
			<% } %></c:if>
			<c:if test="${empty photoList}">
				<div class="alert alert-info noResut" role="alert">尚未擁有${petVO.name}的圖片</div>
			</c:if>
		
		</div></div><!--第一列-->
		
		
			<% int count3 = 0; %>
			<div class="panel panel-default">
				 <div class="panel-heading">
    				<h3 class="panel-title journalFrameTitle">${petVO.name}日誌</h3>
  				</div>
  				<div class="panel-body"><!--第二列日誌-->
			<c:forEach var="journalVO" items="${journalList}"><c:if test="${journalVO.status=='0'}">
				<div class="col-md-6 journalFrame">
					<div class="journalTitle">${journalVO.title}</div>
					<br>
					<%if(journalFirstPhoto.get(count3) != "0"){ %>
					<div>
						<img class="journalPhoto" src="<%=request.getContextPath()%>/Photo/PhotoDisplay?id=<%= journalFirstPhoto.get(count3) %>" />
					</div>
					<% } %>
					<div class="article"><%= articleNonePhoto.get(count3) %>
						<a href="<%=request.getContextPath()%>/journal/journal.do?action=getOne_For_Display&id=${journalVO.id}">&lt;&lt;moreInfo&gt;&gt;</a>
					</div>
					${journalVO.releaseDate }
				</div>
			<% count3++; %>
			</c:if></c:forEach>
			<c:if test="${ empty journalList}">
				<div class="alert alert-info noResut" role="alert">尚未擁有${petVO.name}的日誌</div>
			</c:if>
			</div></div><!--第二列日誌-->
	</div>
		
<%-- 		<c:if test="${empty memberVO || (memberVO.id != memberView.id)}"><!--訪客觀看PET頁面--> --%>
<%-- 			<c:if test="${empty photoList}"> --%>
<!-- 			<div class="col-md-8"> -->
<%-- 			<% int count2 = 0; %> --%>
<%-- 				<c:forEach var="journalVO" items="${journalList}"> --%>
<!-- 					<div class="col-md-12 journalFrame"><div class="row">第二列日誌 -->
<!-- 						<div class="col-md-4"> -->
<%-- 							<%if(journalFirstPhoto.get(count2) != "0"){ %> --%>
<%-- 							<img class="journalPhotoSD" src="<%=request.getContextPath()%>/Photo/PhotoDisplay?id=<%= journalFirstPhoto.get(count2) %>" /> --%>
<%-- 							<% } %> --%>
<!-- 						</div> -->
<!-- 						<div class="col-md-6 journalFrame"> -->
<%-- 							<div class="journalTitle">${journalVO.title}</div> --%>
<%-- 							<div class="article"><%= articleNonePhoto.get(count2) %></div> --%>
<!-- 						</div> -->
<!-- 					</div></div>第二列日誌 -->
<%-- 				<% count2++; %> --%>
<%-- 				</c:forEach> --%>
<!-- 			</div> -->
<!-- 			<div class="col-md-4"> -->
<!-- 				<div style="text-align: center; padding:20px 40px 0px 40px"> -->
<%-- 					<h4>我的名子：</h4><h4>${petVO.name}</h4> --%>
<%-- 					<h4>種族：</h4><h4>${petVO.species}</h4> --%>
<%-- 					<c:if test="${empty petVO.icon}"> --%>
<%-- 							<img style="height: 120px" src="<%=request.getContextPath()%>/images/petIcon1.png"> --%>
<%-- 					</c:if> --%>
<%-- 					<c:if test="${not empty petVO.icon}"> --%>
<%-- 							<img style="height: 120px" src="<%=request.getContextPath()%>/Pet/PetIconDisplay?id=${petVO.id}">			 --%>
<%-- 					</c:if> --%>
<%-- 							<c:if test="${petVO.status=='1'}"><div><a href="#"> --%>
<!-- 					我失蹤了 -->
<!-- 					查看失蹤資料 -->
<%-- 					</a></div></c:if> --%>
<!-- 				</div> -->
<!-- 			</div> -->
<%-- 			</c:if> --%>
<%-- 			<c:if test="${not empty photoList}"><% if(photoList.size()>2){ %> --%>
			
<!-- 			<div class="col-md-11"><div class="row">第一列		 -->
<!-- 			<section class="col-md-6" style="max-height:700px;overflow:hidden;"> -->
<!-- 				<div id="slider" class="flexslider"><ul class="slides"> -->
<%-- 					<c:forEach var="photoVO" items="${photoList}"> --%>
<!-- 						<li> -->
<%--   									<img style="height:400px;" src="<%=request.getContextPath()%>/Photo/PhotoDisplay?id=${photoVO.id}" /> --%>
<!--   								</li> -->
<%-- 					</c:forEach> --%>
<!-- 					</ul></div> -->
<!-- 					<div id="carousel" class="flexslider"><ul class="slides"> -->
<%-- 					<c:forEach var="photoVO" items="${photoList}"> --%>
<!-- 						<li> -->
<%--   									<img style="max-height:130px;" src="<%=request.getContextPath()%>/Photo/PhotoDisplay?id=${photoVO.id}" /> --%>
<!--   								</li> -->
<%-- 					</c:forEach> --%>
<!-- 				</ul></div> -->
<!-- 			</section> -->
<!-- 			<aside class="col-md-6"> -->
<!-- 				<div style="text-align: center; padding:20px 40px 0px 40px"> -->
<%-- 					<h4>我的名子：</h4><h4>${petVO.name}</h4> --%>
<%-- 					<h4>種族：</h4><h4>${petVO.species}</h4> --%>
<%-- 					<c:if test="${empty petVO.icon}"> --%>
<%-- 							<img style="height: 120px" src="<%=request.getContextPath()%>/images/petIcon1.png"> --%>
<%-- 					</c:if> --%>
<%-- 					<c:if test="${not empty petVO.icon}"> --%>
<%-- 							<img style="height: 120px" src="<%=request.getContextPath()%>/Pet/PetIconDisplay?id=${petVO.id}">			 --%>
<%-- 					</c:if> --%>
<%-- 					<c:if test="${petVO.status=='1'}"><div><a href="#"> --%>
<!-- 					我失蹤了 -->
<!-- 					查看失蹤資料 -->
<%-- 					</a></div></c:if> --%>
<!-- 				</div> -->
				
<!-- 			</aside> -->
<!-- 			</div></div> -->
<%-- 			<header class="col-md-9"><div class="row journalFrameTitle">${petVO.species}${petVO.name}日誌</div></header> --%>
<%-- 			<% int count3 = 0; %> --%>
<!-- 			<div class="col-md-9"><div class="row">第二列日誌 -->
<%-- 			<c:forEach var="journalVO" items="${journalList}"> --%>
<!-- 				<div class="col-md-6 journalFrame"> -->
<%-- 					<%if(journalFirstPhoto.get(count3) != "0"){ %> --%>
<!-- 					<div> -->
<%-- 						<img class="journalPhoto" src="<%=request.getContextPath()%>/Photo/PhotoDisplay?id=<%= journalFirstPhoto.get(count3) %>" /> --%>
<!-- 					</div> -->
<%-- 					<% } %> --%>
<%-- 					<div class="journalTitle">${journalVO.title}</div> --%>
<%-- 					<div class="article"><%= articleNonePhoto.get(count3) %></div> --%>
<!-- 				</div> -->
<%-- 			<% count3++; %> --%>
<%-- 			</c:forEach> --%>
<!-- 			</div></div>第二列日誌 -->
					
<%-- 			<% } %></c:if> --%>
			
<%-- 		</c:if><!--訪客觀看PET頁面--> --%>
<!-- 	</div> -->
<%@ include file="/files/PetribeFoot.file" %>			
			

