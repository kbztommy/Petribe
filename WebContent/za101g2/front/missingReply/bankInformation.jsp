<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>領取賞金資訊</title>
</head>
<body>

<FORM method="post" action="<%= request.getContextPath() %>/za101g2/front/missingReply/missingReply.do" name="form1" role="form" class="col-md-8">
	<h4><b>請填寫您領取賞金的帳戶資訊:</b></h4>
	<div class="form-group ${(errorMsgs.bank!=null) ? 'has-error':''}">
		<label for="bank">銀行:<font color="red"><b>*</b></font></label>
		<input type="text" name="bank" value="${param.bank}" class="form-control" id="bank" placeholder="請填寫銀行(如第一銀行)">
	</div>
	<div class="form-group ${(errorMsgs.account!=null) ? 'has-error':''}">
		<label for="account">帳號:<font color="red"><b>*</b></font></label>
		<input type="text" name="account" value="${param.account}" class="form-control" id="account" placeholder="請填寫帳號(14~16碼)">
	</div>
	<div class="form-group ${(errorMsgs.name!=null) ? 'has-error':''}">
		<label for="name">戶名:<font color="red"><b>*</b></font></label>
		<input type="text" name="name" value="${param.name}" class="form-control" id="name" placeholder="請填寫戶名">
	</div>

	<input type="hidden" name="memId" value="${memId}">
	<input type="hidden" name="replyId" value="${replyVO.id}">
	<input type="submit" value="確認" class="btn btn-info">
	<input type="hidden" name="action" value="bankInformationCheck">
</FORM>

</body>
</html>