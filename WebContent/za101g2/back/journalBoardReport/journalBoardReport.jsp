<%@page import="za101g2.journalBoard.model.JournalBoardVO"%>
<%@page import="za101g2.journalBoard.model.JournalBoardService"%>
<%@page import="za101g2.journalBoardReport.model.JournalBoardReportVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="za101g2.journal.model.*"%>

<html>
<head>
<meta name="viewport" content="width=device-width initial-scale =1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/normalize.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/backHome.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/backJournalReport.css">
<script src="http://code.jquery.com/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<title>留言管理</title>
<script>
$(function () {
	$('[data-toggle="popover"]').popover()
})
</script>
</head>
<body>
	<%@ include file="/files/fixedNavbar.file"%>
	<section class="col-md-10"><div class="col-md-12" id="journalReport_wrapper">
	
		<header class="col-md-11 title"><div class="row">
			被檢舉留言管理
		</div></header>
		
		<header class="col-md-11 tableTitle_wrapper"><div class="row">
			<div class="col-md-2 tableTitle">檢舉日期</div>
			<div class="col-md-3 tableTitle">檢舉說明</div>
			<div class="col-md-2 tableTitle">刪除檢舉</div>
			<div class="col-md-3 tableTitle">留言全文</div>
			<div class="col-md-2 tableTitle">鎖定留言</div>
		</div></header>
		
		<% int count = 0; %>
		<%@ include file="pages/page1.file"%>
		<c:forEach var="journalBoardReportVO" items="${journalBoardReportList}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<section class="col-md-11 tableContent_wrapper"><div class="row">
			<div class="col-md-2 contentDate">${journalBoardReportVO.reportDate}</div>
			<div class="col-md-3 tableContent">
				<button type="button" class="btn btn-default commentsBtn" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="left" data-content="${journalBoardReportVO.comments}">${journalBoardReportVO.comments}</button>
			</div>
			<div class="col-md-2 tableBtn">
				<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/backJournalBoardReport/backJournalBoardReport.do" name="journalBoardReport" enctype="multipart/form-data">
					<input type="hidden" name="reportMemId" value="${journalBoardReportVO.memId}">
					<input type="hidden" name="journalMsgId" value="${journalBoardReportVO.journalMsgId}">
					<input type="hidden" name="action" value="delReport">
					<button type="submit" class="btn btn-default controlBtn">刪除檢舉</button>
				</FORM>
			</div>
			<%
				List<JournalBoardReportVO> journalBoardReportList = (List<JournalBoardReportVO>) request.getAttribute("journalBoardReportList");
				JournalBoardService journalSvc = new JournalBoardService();
				JournalBoardVO journalBoardVO = journalSvc.getOneJournalBoard(journalBoardReportList.get(count).getJournalMsgId());
			%>
			<div class="col-md-3 tableContent">
				<button type="button" class="btn btn-default commentsBtn" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="left" data-content="<%= journalBoardVO.getBoardMsg() %>"><%= journalBoardVO.getBoardMsg() %></button>
			</div>
			<div class="col-md-2 tableBtn">
				<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/backJournalBoardReport/backJournalBoardReport.do" name="journalBoardReport" enctype="multipart/form-data">
					<input type="hidden" name="reportMemId" value="${journalBoardReportVO.memId}">
					<input type="hidden" name="journalMsgId" value="${journalBoardReportVO.journalMsgId}">
					<input type="hidden" name="isDelete" value="<%= journalBoardVO.getIsDelete() %>">
					<input type="hidden" name="action" value="lockJournalBoard">
					<button type="submit" class="btn btn-default controlBtn">
						<% String s1 = "y" ;%>
	          			<% if(s1.equals(journalBoardVO.getIsDelete())){ %>
						已鎖定
						<% } else { %>
						正常
						<% } %>
					</button>
				</FORM>
			</div>
		<% count++; %>
		</div></section>
		</c:forEach>
		<%@ include file="pages/page2.file"%>
		
	</div></section>
	<div style="clear: both;"></div>
	<footer id="the_footer"> Copyright 2015 Institute For Information Industry ZA101 Petribe | Private Policy </footer>
</body>
</html>