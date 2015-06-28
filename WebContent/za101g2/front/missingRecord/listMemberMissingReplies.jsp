<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>	
<title>所有回報紀錄</title>
</head>
<body>

<div class="col-md-10">
<h4><b>所有回報紀錄:</b></h4>
<table class="table table-hover">
	<thead>
		<tr class="info">
			<th>回報時間:</th>
			<th>回報者姓名:</th>
			<th>回報者手機:</th>
			<th>回報者e-mail:</th>	
			<th>回報內容:</th>
			<th>確認是否找到寵物:</th>
			<th>檢舉回報:</th>			
		</tr>
	<thead>
	<c:forEach var="missingReplyVO" items="${listMemberMissingReplies}">
		<tbody>
			<tr align='center' valign='middle'>
				<td>${missingReplyVO.reportDate}</td>
				<td>${missingReplyVO.memberVO.lastname} ${missingReplyVO.memberVO.firstname}</td>
				<td>${missingReplyVO.memberVO.phone}</td>
				<td>${missingReplyVO.memberVO.email}</td>			
				
				<td><textarea name="comments" rows="4" cols="20" disabled>${missingReplyVO.comments}</textarea></td>
				
				<td>
					<FORM method="post" action="<%=request.getContextPath()%>/za101g2/front/missingRecord/missingRecord.do">
						<input type="submit" class="btn btn-success" value="確認找到">
						<input type="hidden" name="memId" value="${memId}">
						<input type="hidden" name="replyId" value="${missingReplyVO.id}">
						<input type="hidden" name="action" value="replyConfirm">
					</FORM>
				<br>
					<FORM method="post" action="<%=request.getContextPath()%>/za101g2/front/missingRecord/missingRecord.do">
						<input type="submit" class="btn btn-warning" value="沒有找到">
						<input type="hidden" name="memId" value="${memId}">
						<input type="hidden" name="replyId" value="${missingReplyVO.id}">
						<input type="hidden" name="action" value="rejectReply">
					</FORM>
				</td>
				
				<td>
					<FORM method="post" action="<%=request.getContextPath()%>/za101g2/front/missingReport/missingReport.do">
						<input type="submit" class="btn btn-danger" value="檢舉回報">
						<input type="hidden" name="memId" value="${memId}">
						<input type="hidden" name="replyId" value="${missingReplyVO.id}">
						<input type="hidden" name="action" value="getReply_For_Report">
					</FORM>				
				</td>			
			</tr>
		<tbody>
	</c:forEach>
</table>
</div>

</body>
</html>