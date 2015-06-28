<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     

<%@page import ="za101g2.member.model.*" %>
<%@page import ="za101g2.store.model.*" %>
<%@page import ="za101g2.storeCalendar.model.*" %>
<%@page import ="java.util.*" %>        
<%
    response.setHeader("Pragma","no-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);
    Calendar calendar = GregorianCalendar.getInstance();   
    int curMonth = Integer.parseInt(request.getParameter("month"));
    pageContext.setAttribute("curMonth",curMonth);
    int curYear = Integer.parseInt(request.getParameter("year"));
    int numberOfMonth = (Integer)request.getAttribute("numberOfMonth");    
    int firstDateInMonth = (Integer)request.getAttribute("firstDateInMonth"); 
    int countOfMonth =(Integer)request.getAttribute("countOfMonth");   
%>
    
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width initial-scale =1">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/normalize.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/mainStyle.css">    
    <script src="<%= request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
    <script src="<%= request.getContextPath()%>/js/bootstrap.min.js"></script>
    <script>
    	$(document).ready(function(){
    		
    		$("td:has(div)").removeClass().addClass('open');
    	
    		$("#btCloseALl").click(function(){    			
    			$("td:has(div)").removeClass().addClass('rest');
    			$("tbody input").val(0);
    			return false;
    		});
    		
    		$("#btResetAll").click(function(){    			
    			$("td:has(div)").removeClass().addClass('open');
    			$("tbody input").val(${storeVO.maxQuantitly});
    			return false;
    		});
    		
    		$(".bt-up").click(function(){    			
    			if($(this).next().val() < ${storeVO.maxQuantitly}){    				
    				$(this).next().val(+$(this).next().val()+1);
    				$(this).parent().removeClass().addClass('open');
    			}    			
    		}); 
    		
    		$(".bt-down").click(function(){
    			if($(this).prev().val()>0){
    				$(this).prev().val(+$(this).prev().val()-1);    				
    			}    			
    			if($(this).prev().val()==0){
    				$(this).parent().removeClass().addClass('rest');			
    			}else{    			
    				$(this).parent().removeClass().addClass('open');
    			}
    		});    		
    	});
    		
    
    </script>
    <title>Document</title>
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>
  <div class="col-md-8">
  <div class="panel panel-info">
  		<div class="panel-heading">
    		<h3 class="panel-title"><%= curYear %>年&nbsp;&nbsp;${storeCalendarMap[curMonth]}&nbsp;&nbsp;商店月曆</h3>
  		</div>
  		<div class="panel-body">
		  <form action="<%=request.getContextPath()%>/StoreCalendarServlet" method="post">
			<table class="table table-bordered text-center">
				<thead>
					<tr>
						<th>星期日</th>
						<th>星期一</th>
						<th>星期二</th>
						<th>星期三</th>
						<th>星期四</th>
						<th>星期五</th>
						<th>星期六</th>				
					</tr>
				</thead>
				<tbody>
					<% for(int w = 0;w < (countOfMonth/7)+1;w++){ %>
						<tr>
							<% for(int d = 0;d<7;d++){ %>
								<td><%if(w*7+d>=firstDateInMonth-1 && w*7+d<countOfMonth){ %>
									<div class="text-left"><font size="2"><b><%= w*7+d-firstDateInMonth+2%>日</b></font></div>
									<button type="button" class="btn btn-default btn-xs bt-up" aria-label="Left Align"
									id="bt-up-<%= w*7+d-firstDateInMonth+2%>" >
		  								<span class="glyphicon glyphicon-menu-up" aria-hidden="true"></span>
									</button>			
																		
		  							<input type="text" name="day<%= w*7+d-firstDateInMonth+2%>"  value="${storeVO.maxQuantitly}" 
		  							id="day<%= w*7+d-firstDateInMonth+2%>" style="height:2em;width:3em" readonly >
		  							<button type="button" class="btn btn-default btn-xs bt-down" aria-label="Left Align" 
		  							id="bt-down-<%= w*7+d-firstDateInMonth+2%>" >
		  								<span class="glyphicon glyphicon-menu-down" aria-hidden="true"></span>
									</button>	  								
									<% }%>
								</td>	
							<%} %>
						</tr>
					<% } %>			
				</tbody>		
				<tfoot>
					<tr><td></td><td></td><td></td><td></td><td></td><td class="rest">休息</td><td class="open">可預訂</td></tr>		
				</tfoot>
			</table>
			<input type="hidden" name="month" value="<%= curMonth %>">
			<input type="hidden" name="year" value="<%= curYear %>">
			<input type="hidden" name="action" value="insertCalendar">
			
			<button class="btn btn-default pull-left btn-lg btn-danger" id="btCloseALl">全部關閉</button>
			<input type="reset" class="btn btn-default pull-left btn-lg btn-info" id="btResetAll">
			<input type ="submit" class="btn btn-default pull-right btn-lg btn-primary" value="確定新增">			
						
		  </form>
  		</div>
  	</div>   		
  </div>	
<%@ include file="/files/PetribeFoot.file" %>

 