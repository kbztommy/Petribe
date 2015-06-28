<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     

<%@page import ="za101g2.member.model.*" %>
<%@page import ="za101g2.store.model.*" %>
<%@page import ="java.util.*" %> 
<%@page import ="za101g2.storeCalendar.model.*" %>       
<%
    StoreVO storeVO = (StoreVO)session.getAttribute("storeVO");   
    response.setHeader("Pragma","no-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);
    
    int curMonth;
	int curYear;
	int numberOfMonth;
	int firstDateInMonth;
	int countOfMonth;
    List<StoreCalendarVO> monthCalendarList=null;
    Calendar calendar = GregorianCalendar.getInstance();   
    int thisDate = calendar.get(Calendar.DATE);
    int thisMonth = calendar.get(Calendar.MONTH);
    int thisYear = calendar.get(Calendar.YEAR);
    if(request.getAttribute("monthCalendarList")==null){   
   		curMonth = calendar.get(Calendar.MONTH);
   		pageContext.setAttribute("curMonth",curMonth);
    	curYear = calendar.get(Calendar.YEAR);    
    	numberOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    	calendar.set(Calendar.DATE,1);
    	firstDateInMonth = calendar.get(Calendar.DAY_OF_WEEK);
    	countOfMonth = numberOfMonth + firstDateInMonth-1; 
StoreCalendarService StoreCalendarSrv = new StoreCalendarService();
    	monthCalendarList = StoreCalendarSrv.getMonthCalendar(storeVO.getId(),curYear,curMonth+1);
    	pageContext.setAttribute("monthCalendarList",monthCalendarList);    
    }else{
    	curYear = Integer.parseInt(request.getParameter("year"));
    	curMonth = Integer.parseInt(request.getParameter("month"));
    	pageContext.setAttribute("curMonth",curMonth);
    	calendar.set(curYear,curMonth,1);    	
    	numberOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);    	
	    firstDateInMonth = calendar.get(Calendar.DAY_OF_WEEK);
		countOfMonth = numberOfMonth + firstDateInMonth-1;
		monthCalendarList = (List<StoreCalendarVO>)request.getAttribute("monthCalendarList");
		pageContext.setAttribute("monthCalendarList",monthCalendarList);	
    }    
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
    		$('#btToAdd').click(function(){
    			$('#thisMonth').submit();
    		});
    		
    		$(".blockQty").each(function(){
    			var curQty =parseInt($(this).children('span:first').text());
    			var maxQty =parseInt($(this).children('span:nth-child(2)').text());
    			if(maxQty == 0){
    				$(this).parent().removeClass().addClass('rest');
    			}else if(curQty == maxQty){
    				$(this).parent().removeClass().addClass('notAvailable');
    			}else{
    				$(this).parent().removeClass().addClass('open');
    			}
    				
    		});
    		
    		$(".btEdit").click(function(){
    			$(this).next().toggle();
    			$(this).toggle();
    			$(this).parent().next().show();    			
    		});
    		
    		$(".btOk").click(function(){
    			$(this).prev().toggle();
    			$(this).toggle();
    			$(this).parent().next().hide();
    			var year = <%= curYear %>;
    			var month= <%= curMonth %>;
    			var date = $(this).next('input').val();    			
    			var curQty = $(this).parent().children('span:first').text();
    			var maxQty =$(this).parent().children('span:nth-child(2)').text();
    			var $maxQtyspan = $(this).parent().children('span:nth-child(2)');
    			var queryString = 'year='+year+'&month='+month+'&date='+date+'&curQty='+curQty+'&maxQty='+maxQty+'&action=updateOneCalendar';
    			
    			$.ajax({
    				type:'post',  
    				url:'<%=request.getContextPath()%>/StoreCalendarServlet', 
    				data: queryString,  
    				dataType:'html', 
    				success:function(data){  
    					$maxQtyspan.text(data);
    				}
    			});
    		});
    		
    		$(".bt-up").click(function(){
    			
    			var curQty = parseInt($(this).parent().prev().children('span:first').text());
    			var maxQty = parseInt($(this).parent().prev().children('span:nth-child(2)').text());
    			if(maxQty < ${storeVO.maxQuantitly}){
    				maxQty++;
    				$(this).parent().prev().children('span:nth-child(2)').text(maxQty);
    				$(this).parent().parent().removeClass().addClass('open');
    			}else{
    				alert("已達最大代養數量");
    			}    			
    		}); 
    		
    		$(".bt-down").click(function(){
    			var curQty = parseInt($(this).parent().prev().children('span:first').text());
    			var maxQty = parseInt($(this).parent().prev().children('span:nth-child(2)').text());    			
    			if(maxQty > curQty){    				
    				maxQty--;
    				$(this).parent().prev().children('span:nth-child(2)').text(maxQty);
    			}else{
    				alert('代養數量不可少於0或訂位數');
    			}    			
    			if(maxQty==0){
    				$(this).parent().parent().removeClass().addClass('rest');			
    			}else if(maxQty==curQty){    			
    				$(this).parent().parent().removeClass().addClass('notAvailable');
    			}else{
    				$(this).parent().parent().removeClass().addClass('open');
    			}
    		});   		
    		
    	});    
    </script>
    <title>商店日曆</title>
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>
<div class="row">
  <div class="col-md-8">
	<ul class="nav nav-pills mynav-pills">
	  <li role="presentation"><a href="<%= request.getContextPath()%>/za101g2/front/store/StoreHome.jsp">設定商店</a></li>
	  <li role="presentation"><a href="<%=request.getContextPath() %>/orderBoardServlet?action=StoreOrderList&whichPage=1">商店訂單</a></li>
	  <li role="presentation" class="active"><a href="<%= request.getContextPath()%>/za101g2/front/storeCalendar/oneCalendar.jsp">商店日曆</a></li>
	  <li role="presentation"><a href="<%= request.getContextPath()%>/za101g2/front/store/StorePhotoUpload.jsp">相簿</a></li>
	  <li role="presentation"><a href="<%= request.getContextPath()%>/za101g2/front/storeBoard/StoreBoardForStroe.jsp">留言板</a></li>
	</ul>
  </div>
</div>
  <br>	
<div class="row">
  <div class="col-md-8">
 	<div class="btn-group btn-group-justified" role="group" aria-label="select month">
 	 	<div class="btn-group" role="group">
 	 		<form method="post" action="<%=request.getContextPath()%>/StoreCalendarServlet">
 	 			<input type="hidden" name="year" value="<%= thisYear%>">
 	 			<input type="hidden" name="month" value="<%= thisMonth%>">
 	 			<input type="hidden" name="action" value="listOneCalendar">
 	 			<input type="hidden" name="storeId" value="<%= storeVO.getId()%>">	 				
  	 			<button role="group" class="btn btn-info queryMonth" type="submit">
  	 				<%= thisYear %>年&nbsp;&nbsp;<%= ((HashMap<Integer,String>)application.getAttribute("storeCalendarMap")).get(thisMonth) %>&nbsp;&nbsp;商店月曆
  	 			</button>
  	 		</form>
	 	</div>
 		<div class="btn-group" role="group">
   			<form method="post" action="<%=request.getContextPath()%>/StoreCalendarServlet">
 	 			<input type="hidden" name="year" value="<%= thisMonth+1<12?thisYear:thisYear+1%>">
 	 			<input type="hidden" name="month" value="<%= (thisMonth+1)%12%>">
 	 			<input type="hidden" name="action" value="listOneCalendar">
 	 			<input type="hidden" name="storeId" value="<%= storeVO.getId()%>">	 				
  	 			<button role="group" class="btn btn-info queryMonth" type="submit">
  	 				<%= thisMonth+1<12?thisYear:thisYear+1 %>年&nbsp;&nbsp;<%= ((HashMap<Integer,String>)application.getAttribute("storeCalendarMap")).get((thisMonth+1)%12) %>&nbsp;&nbsp;商店月曆
  	 			</button>
  	 		</form>
  		</div>
  		<div class="btn-group" role="group">
    		<form method="post" action="<%=request.getContextPath()%>/StoreCalendarServlet">
 	 			<input type="hidden" name="year" value="<%= thisMonth+2<12?thisYear:thisYear+1%>">
 	 			<input type="hidden" name="month" value="<%= (thisMonth+2)%12%>">
 	 			<input type="hidden" name="action" value="listOneCalendar">
 	 			<input type="hidden" name="storeId" value="<%= storeVO.getId()%>">	 				
  	 			<button role="group" class="btn btn-info queryMonth" type="submit">
  	 				<%= thisMonth+2<12?thisYear:thisYear+1 %>年&nbsp;&nbsp;<%= ((HashMap<Integer,String>)application.getAttribute("storeCalendarMap")).get((thisMonth+2)%12) %>&nbsp;&nbsp;商店月曆
  	 			</button>
  	 		</form>
 		</div>
    </div>
   	<br>
  	<%if(monthCalendarList.size()>0){ %>
  	<div class="panel panel-info">
  		<div class="panel-heading">
    		<h3 class="panel-title"><%= curYear %>年&nbsp&nbsp${storeCalendarMap[curMonth]}&nbsp&nbsp商店月曆</h3>
  		</div>
  		<div class="panel-body">  	
			<table class="table  table-bordered">
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
					<% for(int w = 0;w < (int)(Math.ceil(countOfMonth/7.0));w++){ %>
						<tr >
							<% for(int d = 0;d<7;d++){ %>
								<td><%if(w*7+d>=firstDateInMonth-1 && w*7+d<countOfMonth){ %>
									<div>
										<font size="2"><b><%= w*7+d-firstDateInMonth+2%>日</b></font>
									</div>
									<div class="blockQty text-center">
										<span ><%= monthCalendarList.get(w*7+d-firstDateInMonth+1).getCurQuantitly() %></span>		
											/								
		  								<span class="maxQty"><%= monthCalendarList.get(w*7+d-firstDateInMonth+1).getMaxQuantitly() %></span>
		  								<%if(thisDate<=w*7+d-firstDateInMonth+2 || thisMonth<curMonth){ %>
		  								<button type="button" class="btn btn-default btn-xs pull-right btEdit" >
		  									<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
										</button>
										<button type="button" class="btn btn-default btn-xs pull-right btOk" style="display: none" >
		  									<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
										</button>
										<input type=hidden value="<%= w*7+d-firstDateInMonth+2%>" name="day">
										<%} %>									
		  							</div>
		  							<div style="display: none" class="text-center">  								
		  								<button type="button" class="btn btn-default btn-xs bt-up" aria-label="Left Align"
										id="bt-up-<%= w*7+d-firstDateInMonth+2%>" >
		  									<span class="glyphicon glyphicon-menu-up" aria-hidden="true"></span>
										</button>															
		  								 							
		  								<button type="button" class="btn btn-default btn-xs bt-down" aria-label="Left Align" 
		  								id="bt-down-<%= w*7+d-firstDateInMonth+2%>" >
		  									<span class="glyphicon glyphicon-menu-down" aria-hidden="true"></span>
										</button>									
		  							</div>  								
									<%}%>
								</td>	
							<%}%>
						</tr>
					<%}%>					
				</tbody>			
				<tfoot>
					<tr><td></td><td></td><td></td><td></td><td class="notAvailable">已滿</td><td class="rest">休息</td><td class="open">可預訂</td></tr>		
				</tfoot>								
			</table>
		</div>
  	</div>		
	<%}else{ %>
	<div class="panel panel-info">
  		<div class="panel-heading">
    		<h3 class="panel-title">尚無<%= ((HashMap<Integer,String>)application.getAttribute("storeCalendarMap")).get(curMonth%12) %>商店日曆</h3>
  		</div>
  		<div class="panel-body clearfix">
  			<button type="button" class="btn btn-default btn-warning pull-right" aria-label="Left Align" id="btToAdd">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
					 新增&nbsp&nbsp<%= ((HashMap<Integer,String>)application.getAttribute("storeCalendarMap")).get(curMonth%12) %>&nbsp&nbsp商店日曆    	
		  	</button> 
  		</div>
  	</div> 	
	<%} %>	
	
	<form method="post" action="<%=request.getContextPath()%>/StoreCalendarServlet" id="thisMonth">		
		<input type="hidden" name="month" value="<%= curMonth%12 %>">
		<input type="hidden" name="year" value="<%=  curMonth<12?curYear:curYear+1 %>">		
		<input type="hidden" name="action" value="toAddCalendar">		
	</form>  		
  </div>
</div>	
<%@ include file="/files/PetribeFoot.file" %> 

