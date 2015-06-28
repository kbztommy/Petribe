<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.imageio.ImageIO"%>
<%@ page import="java.io.ByteArrayInputStream"%>
<%@ page import="java.awt.image.BufferedImage"%>

<html>

<head>
<meta name="viewport" content="width=device-width initial-scale =1">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/mainStyle.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/listPhotosByMemId.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/jquery.fancybox.css?v=2.1.5" type="text/css" media="screen" />
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/jquery.fancybox-thumbs.css" type="text/css" media="screen" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%= request.getContextPath()%>/js/storeHome.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery.fancybox.pack.js?v=2.1.5"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery.fancybox-thumbs.js"></script>  
<title>${memberView.nickname}的相簿</title>

<c:if test="${not empty memberVO}">
	<c:if test="${memberVO.id==memberView.id}">
		<script type="text/javascript">
		$(document).ready(function() {
			$(".originName").click(function(){
				$(this).hide();
				$(this).siblings(".changeName").show();
			});
			
			var temp;
			$(".delPop").click(function(){
				temp = $(this).siblings(".temp").val();
				$(".delId").val(temp);
			});
		});
		
		$(function(){
		    $('[rel="popover"]').popover({
		        container: 'body',
		        html: true,
		        content: function () {
		            var clone = $($(this).data('popover-content')).clone(true).removeClass('hide');
		            return clone;
		        }
		    }).click(function(e) {
		        e.preventDefault();
		    });   
		});
		
		</script>
	</c:if>
</c:if>

	<script>
		$(document).ready(function(){
		 $(".fancybox-thumb").fancybox({
				
				prevEffect	: 'fade',
				nextEffect	: 'fade',
				helpers	: {
					title	: {
						type: 'outside'
					},
					thumbs	: {
						width	: 50,
						height	: 50
					}
				},

				fitToView	: false,
				width		: '100%',
				height		: '100%',
				autoSize	: false,
				closeClick	: false,
				openEffect	: 'fade',
				closeEffect	: 'none',
				afterLoad: function(){
					this.width = $(this.element).data("width");
					this.height = $(this.element).data("height");}
			});
		 
			
		 
		 
		 
		});//end of ready
	</script>

</head>

<body>
<%@ include file="/files/PetribeHead.file" %>
	<div class="col-md-8">
				<div class="row" style="margin-bottom:15px;">
					<section class="col-md-12 albumTitle">
						<h3>
							<a href="<%=request.getContextPath()%>/photo/photo.do?action=getPhotosByMemId&memId=${memberView.id}">
								<span class="glyphicon glyphicon-picture"></span>
								${memberView.nickname}的相片
							</a>
						</h3>
					</section>
					<aside class="col-md-10 ">
						<c:forEach var="petVO" items="${petList}">
							<a href="<%=request.getContextPath()%>/photo/photo.do?action=getPhotosByPetId&memId=${memberView.id}&petId=${petVO.id}">
								<c:if test="${empty petVO.icon}">
									<img style="height: 60px" src="<%=request.getContextPath()%>/images/petIcon1.png">
								</c:if> <c:if test="${not empty petVO.icon}">
									<img style="height: 60px" src="<%=request.getContextPath()%>/Pet/PetIconDisplay?id=${petVO.id}">
								</c:if> ${petVO.name}
							</a>
						</c:forEach>
					</aside>
					<aside class="col-md-2 searchByName">
						<input type="TEXT" name="name" class="form-control" maxlength="18" size="18" placeholder="名稱搜尋" />
					</aside>
				</div>
				<div class="row">
				<%
					List<PhotoVO> photolist = (List<PhotoVO>)request.getAttribute("photoList");
					int imgCount = 0;
				%>		
					<%@ include file="pages/page1.file"%>
					<c:catch var="e">
					<c:forEach var="photoVO" items="${photoList}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
					<% 		
						byte[] data = photolist.get(((whichPage-1)*12)+imgCount).getPhotoFile();
						ByteArrayInputStream ins = new ByteArrayInputStream(data); 		
						BufferedImage bufImage = ImageIO.read(ins);
					%>
						<div class="col-md-3" style="padding: 0px;">
							<div class="col-md-12 row imgFrame">
								<a class="fancybox-thumb fancybox.iframe" rel="fancybox-thumb" data-width="<%= bufImage.getWidth() %>" data-height="<%= bufImage.getHeight() %>" href="<%=request.getContextPath()%>/Photo/PhotoDisplay?id=${photoVO.id}" title="${photoVO.name} ">
									<img style="
									<% if(bufImage.getHeight() != bufImage.getWidth()){ %>
										<%= bufImage.getHeight() > bufImage.getWidth() ? "width:190px;margin-top:-"+(bufImage.getHeight()-bufImage.getWidth())/(2*bufImage.getWidth()/190)+"px;" : "height:190px;margin-left:-"+(bufImage.getWidth()-bufImage.getHeight())/(2*bufImage.getHeight()/190)+"px;" %>
									<% } %>
									<% if(bufImage.getHeight() == bufImage.getWidth()){ %>
										<%= "width:190px;height:190px;" %>
									<% } %>
									" 
									src="<%=request.getContextPath()%>/Photo/PhotoDisplay?id=${photoVO.id}">
								</a>
							</div>
							<div class="col-md-12 row" style="padding: 0px;">
					<%
						if(ins!=null){ins.close();};
						imgCount++;
					%>

								<div class="col-md-10 imgName">
									<div class="col-md-12 originName">${photoVO.name}</div>
									<c:if test="${not empty memberVO}">
										<c:if test="${memberVO.id==memberView.id}">
											<div class="col-md-12 changeName" style="display: none;">
												<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/photo/photo.do" name="photoChange">
													<input type="TEXT" class="changeNameIn" name="name" value="${photoVO.name}" required> 
													<input type="hidden" name="photoId" value="${photoVO.id}">
													<input type="hidden" name="action" value="getOneForUpdate">
													<input type="hidden" name="secondAction" value="<%=action%>"> 
													<input type="hidden" name="memId" value="<%=memId%>"> 
													<input type="hidden" name="petId" value="<%=petId%>"> 
													<input type="hidden" name="whichPage" value="<%=whichPage%>">
												</FORM>
											</div>
										</c:if>
									</c:if>
								</div>
								<c:if test="${not empty memberVO}">
									<c:if test="${memberVO.id==memberView.id}">
										<div class="col-md-1 imgPopup">
											<a href="#" rel="popover" data-popover-content="#myPopover" data-trigger="focus" data-placement="left" class="delPop">x</a>
											<input type="hidden" class="temp" name="temp" value="${photoVO.id}">
										</div>
										<div id="myPopover" class="hide">
											<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/photo/photo.do" name="photoChange">
												<button type="submit" class="delBtn">確定刪除</button>
												<input type="hidden" name="photoId" class="delId" value="${photoVO.id}"> 
												<input type="hidden" name="action" value="delete"> 
												<input type="hidden" name="secondAction" value="<%=action%>"> 
												<input type="hidden" name="memId" value="<%=memId%>"> 
												<input type="hidden" name="petId" value="<%=petId%>"> 
												<input type="hidden" name="whichPage" value="<%=whichPage%>">
											</FORM>
										</div>
									</c:if>
								</c:if>
							</div>
						</div>
					</c:forEach>
					</c:catch>
					<c:if test="${e != null}">
						例外：${e.class.name}，原因：${e.message}
					</c:if>
					<%@ include file="pages/page2.file"%>

				</div>
			
			<c:if test="${not empty memberVO}"><c:if test="${memberVO.id==memberView.id}">	
			<div class="col-md-12">
				<h4>上傳圖片：</h4>
					<div>
						<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/photo/photo.do" name="photo" enctype="multipart/form-data" class="form-inline">
							<div class= "form-group">
								<input type="file" name="photoFile" id="photoFile" class="form-control"> 
							</div>
							<input type="hidden" name="whichPage" value="1">
							<input type="hidden" name="action" value="insertFromAlbum">
							<div class= "form-group">						
								寵物相關：
								<select size="1" name="petId" class="form-control">
									<option value="0">無關聯</option>
									<c:forEach var="petVO" items="${petList}">
										<option value="${petVO.id}">${petVO.name}</option>
									</c:forEach>
								</select>
							</div>
						<div class= "form-group">
							<button type="submit" class="btn btn-default">確定上傳</button>
						</div>	
						</FORM>
					</div>
			</div>
			</c:if></c:if>
	</div>			
<%@ include file="/files/PetribeFoot.file" %>	
