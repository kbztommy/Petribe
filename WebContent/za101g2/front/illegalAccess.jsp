<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width initial-scale =1">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/normalize.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/mainStyle.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="<%= request.getContextPath()%>/js/bootstrap.min.js"></script>     
	<script>
	
	</script>
<title>Petribe</title>
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>
<h1>〈警告〉</h1>
<hr />
<div>您正嘗試非正當路徑瀏覽此網頁。</div><br />
<a href="<%=request.getContextPath()%>/za101g2/front/">回首頁</a>
<%@ include file="/files/PetribeFoot.file" %>