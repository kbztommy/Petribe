<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="za101g2.missingreply.model.*"%>
<%@ page import="za101g2.member.model.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width initial-scale =1">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/mainStyle.css">    
    <script src="<%= request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
    <script src="<%= request.getContextPath()%>/js/bootstrap.min.js"></script>
<title>檢舉騷擾回報</title>
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>

<!-- 協尋功能buttons -->
<div>
	<a href="<%= request.getContextPath() %>/za101g2/front/missingRecord/listAllMissingRecord.jsp" class="btn btn-info" role="button">協尋名單</a>
	<a href="<%= request.getContextPath() %>/za101g2/front/missingRecord/addMissingRecord.jsp" class="btn btn-info" role="button">刊登協尋</a>
	<a href="<%= request.getContextPath() %>/za101g2/front/missingRecord/select_page.jsp" class="btn btn-info" role="button">管理我的協尋</a>
</div>
<br>

<!-- 錯誤列表 -->
<c:if test="${not empty errorMsgs}">
	<font color='deeppink'>溫馨提醒:
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message.value}${(errorMsgs.duplicateReport!=null)? "<button onclick='history.back()' class='btn btn-info'>返回上一頁</button>":""}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>

<FORM method="post" action="<%= request.getContextPath() %>/za101g2/front/missingReport/missingReport.do" name="form1" role="form" class="col-md-4">
	<div class="form-group">
		<label>檢舉會員:</label>
		<p class="form-control-static">${missingReplyVO.memberVO.lastname} ${missingReplyVO.memberVO.firstname}</p>
	</div>
	<div class="form-group">
		<label for="replyComments">檢舉回應:</label>
		<textarea name="replyComments" rows="4" class="form-control" id="replyComments" disabled>${missingReplyVO.comments}</textarea>
	</div>
	<div class="form-group ${(errorMsgs.comments!=null) ? 'has-error':''}">
		<label for="Comments">檢舉原因:<font color=red><b>*</b></font></label>
		<textarea name="comments" rows="4" class="form-control" id="Comments" placeholder="請填寫檢舉原因" ${(errorMsgs.duplicateReport!=null)? "disabled":""}></textarea>
	</div>

	<input type="hidden" name="action" value="insert">
	<input type="hidden" name="memId" value="${requestScope.memId}">
	<input type="hidden" name="replyId" value="${missingReplyVO.id}">
	<input type="submit" value="檢舉" class="btn btn-info" ${(errorMsgs.duplicateReport!=null)? "disabled":""}>

</FORM>

<%@ include file="/files/PetribeFoot.file" %>