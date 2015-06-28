<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<html>
<head>
<meta name="viewport" content="width=device-width initial-scale =1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/normalize.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/backHome.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/backJournalCheck.css">
<script src="http://code.jquery.com/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<title>文章檢查</title>
<script>
$(function () {
	$('[data-toggle="popover"]').popover()
})
</script>
</head>
<body>
	<%@ include file="/files/fixedNavbar.file"%>
	<section class="col-md-10"><div class="col-md-12" id="journalCheck_wrapper">
	
		<header class="col-md-12 title"><div class="row">
			
			<div class="col-md-11">被檢舉日誌確認</div>
			<div class="col-md-1" style="text-align:right;"><a href="<%=request.getContextPath()%>/backJournalReport/backJournalReport.do?action=listJournalReports"><button class="btn btn-primary returnBtn">回列表</button></a></div>
		</div></header>
		
		<header class="col-md-11 tableTitle_wrapper"><div class="row">
			<div class="col-md-6 tableTitle">檢舉說明</div>
			<div class="col-md-2 tableTitle">刪除檢舉</div>
			<div class="col-md-2 tableTitle">日誌狀態</div>
			<div class="col-md-2 tableTitle">日誌鎖定</div>
		</div></header>
		
		<section class="col-md-11 tableContent_wrapper"><div class="row">
			<div class="col-md-6 tableContent">
				<button type="button" class="btn btn-default commentsBtn" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="bottom" data-content="${journalReportVO.comments}">${journalReportVO.comments}</button>
			</div>
			<div class="col-md-2 tableBtn">
				<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/backJournalReport/backJournalReport.do" name="journalReport" enctype="multipart/form-data">
					<input type="hidden" name="reportMemId" value="${journalReportVO.memId}">
					<input type="hidden" name="journalId" value="${journalReportVO.journalId}">
					<input type="hidden" name="action" value="delReport">
					<button type="submit" class="btn btn-default controlBtn">刪除檢舉</button>
				</FORM>
			</div>
			<div class="col-md-2 status">
				<c:if test="${journalVO.status=='0'}">
	            	正常
                </c:if>
                <c:if test="${journalVO.status=='1'}">
	            	已鎖定
                </c:if>
			</div>
			<div class="col-md-2 tableBtn">
				<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/backJournalReport/backJournalReport.do" name="journalReport" enctype="multipart/form-data">
					<input type="hidden" name="reportMemId" value="${journalReportVO.memId}">
					<input type="hidden" name="journalId" value="${journalReportVO.journalId}">
					<input type="hidden" name="status" value="${journalVO.status}">
					<input type="hidden" name="action" value="lockJournal">
					<c:if test="${journalVO.status=='0'}">
	            		<button type="submit" class="btn btn-default controlBtn">鎖定日誌</button>
               		 </c:if>
               		 <c:if test="${journalVO.status=='1'}">
	            		<button type="submit" class="btn btn-default controlBtn">解除鎖定</button>
               		 </c:if>
				</FORM>
			</div>
		</div></section>
		
		<section class="col-md-11 journalTitle"><div class="row" style="padding:10px;">
		${journalVO.title}
		</div></section>
		<section class="col-md-11 journal"><div class="row" style="padding:10px;">
		${journalVO.article}
		</div></section>
		
	</div></section>
	<div style="clear: both;"></div>
	<footer id="the_footer"> Copyright 2015 Institute For Information Industry ZA101 Petribe | Private Policy </footer>
</body>
</html>