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
<title>文章管理</title>
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
			被檢舉日誌管理
		</div></header>
		
		<header class="col-md-11 tableTitle_wrapper"><div class="row">
			<div class="col-md-2 tableTitle">檢舉日期</div>
			<div class="col-md-4 tableTitle">檢舉說明</div>
			<div class="col-md-2 tableTitle">刪除檢舉</div>
			<div class="col-md-2 tableTitle">日誌狀態</div>
			<div class="col-md-2 tableTitle">日誌全文</div>
		</div></header>
		
		<% int count = 0; %>
		<%@ include file="pages/page1.file"%>
		<c:forEach var="journalReportVO" items="${journalReportList}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<section class="col-md-11 tableContent_wrapper"><div class="row">
			<div class="col-md-2 contentDate">${journalReportVO.reportDate}</div>
			<div class="col-md-4 tableContent">
				<button type="button" class="btn btn-default commentsBtn" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="left" data-content="${journalReportVO.comments}">${journalReportVO.comments}</button>
			</div>
			<div class="col-md-2 tableBtn">
				<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/backJournalReport/backJournalReport.do" name="journalReport" enctype="multipart/form-data">
					<input type="hidden" name="reportMemId" value="${journalReportVO.memId}">
					<input type="hidden" name="journalId" value="${journalReportVO.journalId}">
					<input type="hidden" name="action" value="delReport">
					<button type="submit" class="btn btn-default controlBtn">刪除檢舉</button>
				</FORM>
			</div>
			<%
				JournalReportService journalReportSvc = new JournalReportService();
				List<JournalReportVO> journalReportList = journalReportSvc.getAll();
				JournalService journalSvc = new JournalService();
				JournalVO journalVO = journalSvc.getOneJournal(journalReportList.get(count).getJournalId());
			%>
			<div class="col-md-2 status">
				<% String s1 = "1" ;%>
	            <% if(s1.equals(journalVO.getStatus())){ %>
					已鎖定
				<% } else { %>
					正常
				<% } %>
			</div>
			<div class="col-md-2 tableBtn">
				<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/backJournalReport/backJournalReport.do" name="journalReport" enctype="multipart/form-data">
					<input type="hidden" name="reportMemId" value="${journalReportVO.memId}">
					<input type="hidden" name="journalId" value="${journalReportVO.journalId}">
					<input type="hidden" name="action" value="journalDisplay">
					<button type="submit" class="btn btn-default controlBtn">No.${journalReportVO.journalId}查看</button>
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