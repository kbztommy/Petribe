<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="za101g2.journal.model.*"%>
<%@ page import="za101g2.member.model.*"%>
<%@ page import="za101g2.pet.model.*"%>
<%
JournalVO journalVO = (JournalVO) request.getAttribute("journalVO");
%>
<%
	MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
	PetService petSvc = new PetService();
	List<PetVO> petList = petSvc.findIdByMemId(memberVO.getId());
	pageContext.setAttribute("petList",petList);
%>
<html>

<head>
<meta name="viewport" content="width=device-width initial-scale =1">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/mainStyle.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/journal.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/za101g2/front/journal/CLEditor1_4_5/jquery.cleditor.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%= request.getContextPath()%>/js/storeHome.js"></script>
<script
	src="<%=request.getContextPath()%>/za101g2/front/journal/CLEditor1_4_5/jquery.cleditor.js"></script>
<title>日誌修改</title>
<script>
		$(document).ready(function () {
			$("#editor").cleditor();
		});
		function getValue (){
			document.getElementById("journalId2").value = document.getElementById("journalId").value;
			document.getElementById("title2").value = document.getElementById("title").value;
			document.getElementById("article2").value = document.getElementById("editor").value;
		}
		
		function shortcut (){
			document.getElementById("editor").value = document.getElementById("editor").value + "<h3>她當天做了我們家一整晚的模特兒，我們挖空了心思想著怎樣給她擺POSE。</h3>";
		}
	</script>
</head>

<body>
<%@ include file="/files/PetribeHead.file" %>		
	<div class="col-md-8">
		<%-- 錯誤表列 --%>
		<c:if test="${not empty errorMsgs}">
			<font color='red'>請修正以下錯誤:
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li>${message}</li>
					</c:forEach>
				</ul>
			</font>
		</c:if>
		<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/journal/journal.do" name="journal"  class="form-horizontal">
			<div class="form-group">
				<label for="title" class="col-md-2 control-label"><font size="4" color="red">*</font><font size="3">標題:</font></label>
				<div class="col-md-10">
					<input type="TEXT" id="title" name="title" class="form-control" value="${journalVO.title}" required>
				</div>
			</div>
			<div class="form-group">
				<label for="editor" class="col-md-2 control-label"><font size="4" color="red">*</font><font size="3">內文:</font></label>
				<div class="col-md-10">
					<textarea id="editor" name="article" class="form-control" required>${journalVO.article}</textarea>
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-12">
					<input type="hidden" name="action" value="update"> 
					<input type="hidden" id="journalId" name="journalId" value="${journalVO.id}">
					<button type="submit" class="btn btn-warning pull-right">確定修改</button>
				</div>
			</div>
		</FORM>
		<div class="col-md-11 col-md-offset-1">
			<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/photo/photo.do" name="photo" class="form-inline" role="form" enctype="multipart/form-data">
			<font size="3">上傳圖片：</font>
				<input type="file" style="width:250px;" name="photoFile" class="form-control" id="photoFile" > 
				<input type="hidden" id="title2" name="title"> 
				<input type="hidden" id="article2" name="article">
				<input type="hidden" id="journalId2" name="journalId">
				<input type="hidden" name="action" value="updateFromJournal">
				<font size="3">圖片寵物關聯：</font>
				<select size="1" name="petId" class="form-control">
					<option value="0">無關聯</option>
					<c:forEach var="petVO" items="${petList}">
						<option value="${petVO.id}">${petVO.name}</option>
					</c:forEach>
				</select>
				<button type="submit" id="photoUpload" class="btn btn-default" onmousedown="getValue()">上傳圖片</button>
			</FORM>
			<button id="shortcut" class="btn btn-default" onmousedown="shortcut()">神奇小按鈕</button>
		</div>
	</div>
<%@ include file="/files/PetribeFoot.file" %>	