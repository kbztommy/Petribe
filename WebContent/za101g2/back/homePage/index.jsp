<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="za101g2.staff.model.*"%>


<html>
<head>
<meta name="viewport" content="width=device-width initial-scale =1">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/normalize.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/backIndex.css">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<title>登入</title>
</head>
<body>
	<div id="big_wrapper" class="container"
		style="background-image:url(<%=request.getContextPath()%>/images/backLogin.jpg);">
		<form
			action="<%=request.getContextPath()%>/za101g2/back/staff/staff.do"
			method="post" class="col-md-12">
			<section id="input_section">
				<img src="<%=request.getContextPath()%>/images/Logo_orange.png"
					id="logo"><br> <br> <br>
					<div class="row">
				<h2 >Petribe back-end system</h2>
				</div>
				<div class="row">
				<input type="text"
					name="email" id="email" class="form-control" placeholder="E-mail">
					</div>
					<div class="row">
					<input type="password"
					name="password" class="form-control" placeholder="Password">
					</div>
					<input type="hidden" name="action" value="login"><br>
				<button id="login_btn">Login</button>
			</section>
		</form>
		<div style="clear: both;"></div>
		<footer id="the_footer"> Copyright 2015 Institute For
			Information Industry ZA101 Petribe | Private Policy </footer>
	</div>
</body>
</html>