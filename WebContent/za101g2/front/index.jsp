<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@page import ="za101g2.member.model.*" %>
<%@page import ="za101g2.store.model.*" %> 
<%@page import ="za101g2.journal.model.*" %> 
<%@ taglib uri="/Taglib2" prefix="forJour" %>
<% 
	response.setHeader("Pragma","no-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%>
<jsp:useBean id="journalSrv" class="za101g2.journal.model.JournalService"  scope="page"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width initial-scale =1">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/normalize.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/mainStyle.css">
    <script src="<%= request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
    <script src="<%= request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script>
	
	</script>
<title>Petribe</title>
</head>
<body>

<%@ include file="/files/PetribeHead.file" %>
	<div id="journalPanel" class="col-md-10">
		<c:forEach var="journalVO" items="${journalSrv.getNewest()}">
			<div class="row" id="journalindex-${journalVO.id }">
				<div class="col-md-8">
					<div class="panel panel-default">
						<div class="panel-heading">
						<div class="panel-title clearfix">
					    	<div class="col-xs-8 ">${journalVO.title}</div>
					    	<div class="col-xs-4 text-right text-muted">${journalVO.releaseDate }</div>
					    </div>
					 	</div>
					  	<div class="panel-body">
					  		<c:if test="${journalVO. getFirstImg()!=''}">
						    	<div class="col-md-10 col-md-offset-1">
						    		${journalVO.getFirstImg()}
						    	</div>
					    	</c:if>
					    	<c:if test="${journalVO. getFirstImg()==''}">
					    		<div class="col-md-10 col-md-offset-1">
					    			${journalVO.getShortArticle() }
					    		</div>
					    	</c:if>
					    	<div class="col-md-10 col-md-offset-1">
					    		<a class="btn btn-link pull-right" href="<%=request.getContextPath()%>/journal/journal.do?action=getOne_For_Display&id=${journalVO.id}">詳細閱讀</a>
					    	</div>
					  	</div>
					  	<div class="panel-footer">
					  		<div class="clearfix">
					  			<div class="col-xs-8 pull-left text-left">
					  				about<a class="btn btn-link" href="<%=request.getContextPath()%>/memberHome/memberHome.do?action=memberHome&memId=${journalVO.memId}">${memberSrv.getOneMember(journalVO.memId).nickname}</a>
					  			</div>
					  		</div>
					  	</div>
					</div>
				</div>
			</div>	
		</c:forEach>
	</div>
	<div class="col-md-6">
		<a role="botton" class="btn btn-link center-block" id="btMoreJoun">查看更多</a>
	</div>
	<script>
		$(document).ready(function(){
			$("#btMoreJoun").click(function(){
				var strJourId = $("#journalPanel").children().last().attr("id").split("-"); 
				var jourId = strJourId[1];
				
				$.ajax({
					type:"post",
					url:"<%= request.getContextPath()%>/journal/journal.do",
					data:"action=getMoreJour&jourId="+jourId,
					dataType:'json',
					success:function(data){
						for(var i=0;i<data.jourList.length;i++){
							getMoreJour(data.jourList[i]);
						}
						if(data.jourList.length<4){
							$("#btMoreJoun").text("資料到底了..");
							$("#btMoreJoun").addClass("disabled");
						}
					}
				})
				return false;
			});//end of click
			
			function getMoreJour(data){
				var imgSrc;
				if(data.img!='')
					imgSrc=data.img;
				else
					imgSrc=data.shortArticle;
				$("#journalPanel").append(
						'<div class="row" id="journalindex-'+data.id+'">'+
						'<div class="col-md-8">'+
							'<div class="panel panel-default">'+
								'<div class="panel-heading">'+
								'<div class="panel-title clearfix">'+
							    	'<div class="col-xs-8 ">'+data.title+'</div>'+
							    	'<div class="col-xs-4 text-right text-muted">'+data.releaseDate+'</div>'+
							    '</div>'+
							 	'</div>'+
							  	'<div class="panel-body">'+
						    		'<div class="col-md-10 col-md-offset-1">'+imgSrc+'</div>'+
							    	'<div class="col-md-10 col-md-offset-1">'+
							    		'<a class="btn btn-link pull-right" href="<%=request.getContextPath()%>/journal/journal.do?action=getOne_For_Display&id='+data.id+'">詳細閱讀</a>'+
							    	'</div>'+
							  	'</div>'+
							  	'<div class="panel-footer">'+
							  		'<div class="clearfix">'+
							  			'<div class="col-xs-8 pull-left text-left">'+
							  				'about<a class="btn btn-link" href="<%=request.getContextPath()%>/memberHome/memberHome.do?action=memberHome&memId='+data.memId+'">'+data.nickname+'</a>'+
							  			'</div></div></div></div></div>'
				);
			}//end of getMoreJour(data)
		});//end of ready
	</script>
	
<%@ include file="/files/PetribeFoot.file" %>             
   