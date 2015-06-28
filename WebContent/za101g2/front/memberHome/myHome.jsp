<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="za101g2.member.model.*"%>
<%@ page import="za101g2.journal.model.*"%>
<%@ page import="za101g2.pet.model.*"%>
<%@ page import="za101g2.photo.model.*"%>
<jsp:useBean id="storeSrv" class="za101g2.store.model.StoreService" />
<html>

<head>
<meta name="viewport" content="width=device-width initial-scale =1">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/mainStyle.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/myHome.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/flexslider.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.flexslider-min.js"></script>
<title>${memberView.nickname}</title>
<script>
	$(document).ready(function() {
		$(window).load(function() {
			$('.flexslider').flexslider({
				animation : "slide",
				animationLoop : false,
				itemWidth : 210,
				itemMargin : 10,
				minItems : 0,
				maxItems : 4
			});
		});
	});
</script>
</head>

<body>
	<c:if test="${not empty memberVO}">
		<c:if test="${memberVO.id==memberView.id}">
			<!--已登入換自己的圖像-->
			<div class="modal fade" id="memberIcon" tabindex="-1" role="dialog"
				aria-labelledby="reportLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" id="reportLabel">上傳會員頭像</h4>
						</div>
						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/photo/photo.do"
							name="photo" enctype="multipart/form-data">
							<div class="modal-body">
								<input type="file" name="photoFile" id="photoFile"> <input
									type="hidden" name="action" value="insertMemberPhoto">
							</div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-primary">確定上傳</button>
								<button type="button" class="btn btn-default"
									data-dismiss="modal">取消</button>
							</div>
						</FORM>
					</div>
				</div>
			</div>
		</c:if>
	</c:if>
	<%@ include file="/files/PetribeHead.file"%>
	<div class="col-md-8">
		<div class="row">
			<div class="panel panel-default">
				<div class="panel-body">
					<section class="col-xs-3">
						<c:if test="${memberVO.id!=memberView.id}">
							<c:if test="${empty memberView.icon}">
								<div style="height: 200px;">
									<img src="<%=request.getContextPath()%>/images/memberIcon.jpg"
										class="img-circle memberIcon">
								</div>
							</c:if>
							<c:if test="${not empty memberView.icon}">
								<div style="height: 200px;">
									<img
										src="<%=request.getContextPath()%>/Member/MemberIconDisplay?id=${memberView.id}"
										class="img-circle memberIcon">
								</div>
							</c:if>
						</c:if>

						<c:if test="${not empty memberVO}">
							<c:if test="${memberVO.id==memberView.id}">
								<c:if test="${empty memberView.icon}">
									<div style="height: 200px;">
										<a href="#" data-toggle="modal" data-target="#memberIcon">
											<img
											src="<%=request.getContextPath()%>/images/memberIcon.jpg"
											class="img-circle memberIcon">
										</a>
									</div>
								</c:if>
								<c:if test="${not empty memberView.icon}">
									<div style="height: 200px;">
										<a href="#" data-toggle="modal" data-target="#memberIcon">
											<img
											src="<%=request.getContextPath()%>/Member/MemberIconDisplay?id=${memberView.id}"
											class="img-circle memberIcon">
										</a>
									</div>
								</c:if>
							</c:if>
						</c:if>

					</section>

					<section class="col-xs-4">
						<h4>
							暱稱:${memberView.nickname} &nbsp;&nbsp;&nbsp;&nbsp;
							<c:if test="${not empty memberVO}">
							<c:if test="${memberView.id != memberVO.id}">
								<c:if
									test="${friendsSrv.hasApplied(memberView.id,memberVO.id,'0')==null && friendsSrv.hasApplied(memberView.id,memberVO.id,'1')==null && friendsSrv.hasApplied(memberVO.id,memberView.id,'0')==null}">
									<a type="button" class="btn btn-warning"
										href="<%=request.getContextPath()%>/za101g2/front/friend/friends.do?action=be_friend&onesmemberid=${memberView.id}">
										<span class="glyphicon glyphicon-plus"></span>申請好友
									</a>
								</c:if>
								<c:if
									test="${friendsSrv.hasApplied(memberVO.id,memberView.id,'0').memId==memberVO.id}">
									<a type="button" class="btn btn-success" href="#" disabled>
										<span class="glyphicon glyphicon-ok"></span>已申請
									</a>
								</c:if>
								<c:if
									test="${friendsSrv.hasApplied(memberView.id,memberVO.id,'0').friendMemId==memberVO.id}">
									<a type="button" class="btn btn-info"
										href="<%=request.getContextPath()%>/za101g2/front/friend/friends.do?action=accept_be_friend&onesmemberid=${memberView.id}">
										<span class="glyphicon glyphicon-ok"></span>成為好友
									</a>
								</c:if>
								<c:if
									test="${friendsSrv.hasApplied(memberView.id,memberVO.id,'1')!=null}">
									<a type="button" class="btn btn-danger"
										href="<%=request.getContextPath()%>/za101g2/front/friend/friends.do?action=delete_friend&onesmemberid=${memberView.id}">
										<span class="glyphicon glyphicon-remove"></span>刪除好友
									</a>
								</c:if>
							</c:if>
							</c:if>
						</h4>
						<br>
						<c:if
							test="${not empty storeSrv.getOneStore(memberView.id) && storeSrv.getOneStore(memberView.id).status != '0'}">
							<h4>
								商店:<a class="btn btn-link btn-lg"
									href="<%=request.getContextPath()%>/StoreServlet?storeId=${storeSrv.getOneStore(memberView.id).id}&action=lookOverStore">
									${storeSrv.getOneStore(memberView.id).name} </a>
							</h4>
							<br>
						</c:if>
						<h4>
							性別:
							<c:if test="${memberView.sex=='0'}">女</c:if>
							<c:if test="${memberView.sex=='1'}">男</c:if>
						</h4>



					</section>


					<%
						List<PhotoVO> getPhotosByMemId = (List<PhotoVO>) request
								.getAttribute("getPhotosByMemId");
					%>
					<section class="col-xs-4">
						<div class="albumPhotoFrame pull-right text-center">
							<b><a
								href="<%=request.getContextPath()%>/photo/photo.do?action=getPhotosByMemId&memId=${memberView.id}">${memberView.nickname}的相片</a></b>
							<a
								href="<%=request.getContextPath()%>/photo/photo.do?action=getPhotosByMemId&memId=${memberView.id}">
								<%
									if (getPhotosByMemId.size() >= 4) {
								%>
								<div>
									<img class="albumPhoto" style="height: 85px; width: 85px;"
										src="<%=request.getContextPath()%>/Photo/PhotoDisplay?id=<%=getPhotosByMemId.get(0).getId()%>">
									<img class="albumPhoto" style="height: 85px; width: 85px;"
										src="<%=request.getContextPath()%>/Photo/PhotoDisplay?id=<%=getPhotosByMemId.get(1).getId()%>">
								</div>
								<div>
									<img class="albumPhoto" style="height: 85px; width: 85px;"
										src="<%=request.getContextPath()%>/Photo/PhotoDisplay?id=<%=getPhotosByMemId.get(2).getId()%>">
									<img class="albumPhoto" style="height: 85px; width: 85px;"
										src="<%=request.getContextPath()%>/Photo/PhotoDisplay?id=<%=getPhotosByMemId.get(3).getId()%>">
								</div> <%
 	} else if (getPhotosByMemId.size() >= 1) {
 %> <img class="albumPhoto" style="height: 180px"
								src="<%=request.getContextPath()%>/Photo/PhotoDisplay?id=<%=getPhotosByMemId.get(0).getId()%>">
								<%
									} else if (getPhotosByMemId.size() == 0) {
								%> <img class="albumPhoto" style="height: 180px"
								src="<%=request.getContextPath()%>/images/photoAlbum.jpg">
								<%
									}
								%>
							</a>
						</div>
					</section>
				</div>
			</div>
		</div>
		<c:if test="${not empty memberVO}">
			<c:if test="${memberView.id == memberVO.id}">
				<div class="row">
					<a class="btn btn-block btn-primary  btn-lg"
						href="<%=request.getContextPath()%>/za101g2/front/journal/addJournal.jsp">
						<span class="glyphicon glyphicon-pencil pull-left"></span>發表文章
					</a>
				</div>
			</c:if>
		</c:if>
		<br> <br>
		<div class="row">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">${memberView.nickname}的寵物</h3>
				</div>
				<div class="panel-body">
					<div class="flexslider">
						<ul class="slides">
							<c:forEach var="petVO" items="${getPetsByMemId}">
								<li><c:if test="${empty petVO.icon}">
										<a
											href="<%=request.getContextPath()%>/memberHome/memberHome.do?action=petHome&petId=${petVO.id}">
											<img src="<%=request.getContextPath()%>/images/petIcon1.png"
											alt="${petVO.name}">
										</a>
									</c:if> <c:if test="${not empty petVO.icon}">
										<a
											href="<%=request.getContextPath()%>/memberHome/memberHome.do?action=petHome&petId=${petVO.id}">
											<img
											src="<%=request.getContextPath()%>/Pet/PetIconDisplay?id=${petVO.id}"
											alt="${petVO.name}">
										</a>
									</c:if></li>
							</c:forEach>
						</ul>
					</div>
					<!-- 					揚富新增新增寵物表單   -->
					<c:if test="${memberView.id == memberVO.id}">
						<form enctype="multipart/form-data" METHOD="post"
							ACTION="<%=request.getContextPath()%>/PetServlet" name=""
							class="form-horizontal col-md-12 btn btn-primary">
							<div class="col-md-2">
								<input type="text" name="name" id="name" value=""
									class="form-control"
									placeholder="${nameError!=null?nameError:'寵物名字'}">
							</div>
							<div class="col-md-2">
								<input type="text" name="species" id=species value=""
									class="form-control"
									placeholder="${speciesError!=null?speciesError:'寵物種類'}">
							</div>
							<div class="col-md-1">
								<h5 class="text-right">大頭照</h5>
							</div>
							<div class="col-md-3">
								<input type="file" name="icon" id="icon" value=""
									class="form-control">
							</div>
							<div class="col-md-2">
								<input type="hidden" name="action" value="add_new_pet">
								<input type="submit" value="新增寵物" class="btn btn-default">
							</div>
							<div class="col-md-1">${petError}</div>
						</form>
					</c:if>
				</div>
			</div>
		</div>

		<%
			List<String> journalFirstPhoto = (List<String>) request
					.getAttribute("journalFirstPhoto");
			List<String> articleNonePhoto = (List<String>) request
					.getAttribute("articleNonePhoto");
			int count = 0;
		%>
		<div class="row">
			<c:forEach var="journalVO" items="${getJournalsByMemId}"
				varStatus="status">
				<div class="panel panel-default panel-info">
					<div class="panel-heading">${journalVO.title}</div>
					<div class="panel-body">
						<%
							if (journalFirstPhoto.get(count) == "0") {
						%>
						<div class="col-md-6"
							style="float:${(status.count%2) == 1 ? 'left' : 'right'};background-color: #e9e9e9;height: 290px;">

						</div>
						<%
							} else {
						%>
						<div class="col-md-6 journalPhotoFrame"
							style="float:${(status.count%2) == 1 ? 'left' : 'right'};">
							<img class="journalPhoto"
								src="<%=request.getContextPath()%>/Photo/PhotoDisplay?id=<%=journalFirstPhoto.get(count)%>">
						</div>
						<%
							}
						%>
						<div class="col-md-6">
							<div class="article"><%=articleNonePhoto.get(count)%></div>
							<div class="articleInfo"
								style="text-align:${(status.count%2) == 1 ? 'right' : 'left'};">
								<a
									href="<%=request.getContextPath()%>/journal/journal.do?action=getOne_For_Display&id=${journalVO.id}">moreInfo</a>
							</div>
						</div>
						<%
							count++;
						%>
					</div>
					<div class="panel-footer">發表於${journalVO.releaseDate}</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<%@ include file="/files/PetribeFoot.file"%>