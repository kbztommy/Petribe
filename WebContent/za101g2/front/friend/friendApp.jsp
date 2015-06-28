<%@page import="za101g2.friends.model.*"%>
<%@ page import="za101g2.member.model.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	//接受和拒絕參數
	final String accept = "1";
	final String apply = "0";

	MemberVO memberVO = (MemberVO) request.getSession().getAttribute(
			"memberVO");
	FriendsService srv = new FriendsService();
	List<FriendsVO> list = srv
			.findOnesFriends(memberVO.getId(), accept);
	pageContext.setAttribute("myFriendsList", list);

	list = srv.findOnesBeFriends(memberVO.getId(), apply);
	pageContext.setAttribute("beMyFriendsList", list);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width initial-scale =1">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/mainStyle.css">
<script src="<%=request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/storeHome.js"></script>
<title>Insert title here</title>
</head>
<body>
	<%@ include file="/files/PetribeHead.file"%>
	<jsp:useBean id="memberSrv2" class="za101g2.member.model.MemberService"
		scope="page" />
	<div class="form-group col-md-8">
		<div class="col-md-12">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4>
						<b>好友管理</b>
					</h4>
				</div>
				<table class="table table-striped table-hover">
					<c:if test="${empty myFriendsList}">
						<tr>
							<td><div class="col-md-12">
									<h4><strong>尚無好友。</strong></h4>
								</div></td>
						</tr>
					</c:if>
					<c:forEach var="friendsVO" items="${myFriendsList}">
						<tr>
							<td>
								<div class="col-md-12">
									<div class="col-md-3">
										<img
											src="<%= request.getContextPath()%>/PicForMember?id=${friendsVO.friendMemId}"
											class="img-responsive">
									</div>
									<h4 class="col-md-6">
										<b>${memberSrv2.getOneMember(friendsVO.friendMemId).nickname}</b>
										<br /> <small>好友加入時間：<br />${friendsVO.timestamp.toLocaleString()}</small>
									</h4>
									<form method="post"
										action="<%=request.getContextPath()%>/za101g2/front/friend/friends.do">
										<input type="submit" value="刪除好友"
											class="btn btn-primary col-md-3"> <input
											type="hidden" name="onesmemberid"
											value="${friendsVO.friendMemId}"> <input
											type="hidden" name="action" value="delete_friend">
									</form>
								</div>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="col-md-12">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4>
						<b>好友申請</b>
					</h4>
				</div>
				<table class="table table-striped table-hover">
					<c:if test="${empty beMyFriendsList}">
						<tr>
							<td><div class="col-md-12">
									<h4><strong>尚無新的好友申請。</strong></h4>
								</div></td>
						</tr>
					</c:if>
					<c:forEach var="friendsVO" items="${beMyFriendsList}">
						<tr>
							<td>
								<div class="col-md-12">
									<div class="col-md-3">
										<img
											src="<%= request.getContextPath()%>/PicForMember?id=${friendsVO.memId}"
											class="img-responsive">
									</div>
									<h4 class="col-md-6">
										<b>${memberSrv2.getOneMember(friendsVO.memId).nickname}</b><br />
										<small>好友申請時間：<br />${friendsVO.timestamp.toLocaleString()}</small>
									</h4>
									<div class="col-md-3">
										<form method="post"
											action="<%=request.getContextPath()%>/za101g2/front/friend/friends.do">

											<input type="submit" value="接受"
												class="btn btn-primary col-md-12"> <input
												type="hidden" name="onesmemberid" value="${friendsVO.memId}">
											<input type="hidden" name="action" value="accept_be_friend">
										</form>
										<form method="post"
											action="<%=request.getContextPath()%>/za101g2/front/friend/friends.do">
											<input type="submit" value="拒絕"
												class="btn btn-danger col-md-12"> <input
												type="hidden" name="onesmemberid" value="${friendsVO.memId}">
											<input type="hidden" name="action" value="refuse_be_friend">
										</form>
									</div>

								</div>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<%@ include file="/files/PetribeFoot.file"%>