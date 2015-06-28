<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>俺是首頁</h1>
	<hr />
	<a href="<%=request.getContextPath()%>/za101g2/front/member/login.jsp">登入</a>(登入後應該看不到)
	<br />
	<a href="<%=request.getContextPath()%>/za101g2/front/member/register.jsp">註冊</a>(登入後應該看不到)
	<br />
	<a href="<%=request.getContextPath()%>/za101g2/front/member/memberManager.jsp">會員中心</a>(應該要登入才看得到)
	<br />
	<a href="<%=request.getContextPath()%>/za101g2/front/friend/friendApp.jsp">好友測試</a>(應該要登入才看得到)
	<br />
	<a
		href="<%=request.getContextPath()%>/za101g2/front/member/phoneValidate_step1.jsp">手機認證</a>(應該要登入才看得到)
	<br /> hello, ${memberVO.nickname} .
	<br />
	<form method="post"
		action="<%=request.getContextPath()%>/za101g2/front/member/member.do">
		<input type="hidden" name="action" value="logout"> <input
			type="submit" value="登出" class="btn" id="logout">(應該要登入才看得到)
	</form>
</body>
</html>