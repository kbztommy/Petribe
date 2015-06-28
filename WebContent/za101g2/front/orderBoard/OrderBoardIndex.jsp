<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     

<%@page import ="za101g2.member.model.*" %>
<%@page import ="za101g2.store.model.*" %>
<%@page import ="za101g2.serviceList.model.*" %>
<%@page import ="java.util.*" %>
<%@page import ="za101g2.storeCalendar.model.*" %>        
<%
    Calendar calendar =  GregorianCalendar.getInstance();
  	int curMonth = calendar.get(Calendar.MONTH);
  	int curYear = calendar.get(Calendar.YEAR);
  	int nextMonth = (curMonth+1)%12;
  	int nextYear = curMonth+1<12?curYear:curYear+1;
  	int afterNextMonth = (curMonth+2)%12;
  	int afterNextYear = curMonth+2<12?curYear:curYear+1;
  	int	thisYear=0;
  	int thisMonth=0;
  	int firstDateInMonth=0;
  	int countOfMonth=0;
  	List<StoreCalendarVO> monthCalendarList=null;
  	pageContext.setAttribute("curMonth", curMonth);
  	pageContext.setAttribute("nextMonth", nextMonth);
  	pageContext.setAttribute("afterNextMonth", afterNextMonth);  	
  	
  	 response.setHeader("Pragma","no-cache");
     response.setHeader("Cache-Control","no-cache");
     response.setDateHeader("Expires", 0);
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
    		var radioValue;
    		var today = new Date();
    		var thisDate =  parseInt(today.getDate());
			var thisYear =  parseInt(today.getFullYear());
			var thisMonth =  parseInt(today.getMonth());
			
			$("#btBookSubmit").click(function(){
				$("#bookForm").submit();
			});
			
    		$(".petRadio").click(function(){
    			radioValue=$(this).val();
    			$("#endDate").val("");
    			$("#startDate").val("");
    			$("#orederCalendar").hide();
    			$("#navCalPenal").hide();    			
    		});
    		
    		$(".petRadio:first").click();
    		
    		$("#pickRadio input:first").click();
    		
    		
    		$("#lbPickSrv").click(function(){
    			$("#divCustAddress").show();
    		});
    		
    		<c:if test="${not empty errorMsgs['custAddress']}">
				$("#pickRadio input:last").click();
			</c:if>
    		
    		$("#lbNoPickSrv").click(function(){
    			$("#divCustAddress").hide();
    		});
    		
    		var whichBt;    	
    		
    		$("#btStartDate").click(function(){
    			$("#errorMsg").hide();
    			$("#endDate").val("");
    			$("#startDate").val("");
    			$('#calOrderAlert').text("請選擇開始日期");
    			whichBt = "startDate";
    			$("#orederCalendar").fadeIn(500);
    			$("#navCalPenal").fadeIn(500);
    			$(".blockQty").each(function(){
    				var curQty =parseInt($(this).children('span:first').text());
    				var maxQty =parseInt($(this).children('span:nth-child(2)').text());
    				var blockday = $(this).parent().parent().attr("id");
    				
    				
    				var availableQty = maxQty-curQty;
    				//console.log(compareday(blockday));
    				if(availableQty >= radioValue && compareday(blockday)){
    					$(this).parent().parent().removeClass('rest').addClass('open');				
    				}else{ 
    					$(this).parent().parent().removeClass('open').addClass('rest');    	
    				
    				}					
    			});
    			
    			   			
    			
    			
    			
    		});
    		
    		function compareday(blockday){
    			var block=blockday.split("-");
    			var blockYear =  parseInt(block[0]);
				var blockMonth = parseInt( block[1]);
				var blockDate =  parseInt(block[2]);		
				console.log(blockMonth < thisMonth);
				console.log(blockMonth +"<"+ thisMonth);
				if(blockYear > thisYear){
					
					return true;
				}else if(blockMonth > thisMonth){
					
					return true;
				}else if(blockDate > thisDate){
					
					return true;
				}else{
					return false;
				}
    		}	
    		
    		$("#btEndDate").click(function(){
    			whichBt = "endDate";
    			$("#orederCalendar").fadeOut(500);
    			$("#navCalPenal").fadeOut(500);
    			$(".blockQty").each(function(){
    				var curQty =parseInt($(this).children('span:first').text());
    				var maxQty =parseInt($(this).children('span:nth-child(2)').text());
    				var availableQty = maxQty-curQty;
    							
    			});
    			$("#orederCalendar").fadeIn(500);
    			$("#navCalPenal").fadeIn(500);
    		});
    		
    		$(".btOrderOk").click(function(){
    			
    			if($(this).parent().attr('class')=='rest')
    				return false;
    			$("#orederCalendar").fadeOut(500);
    			$("#navCalPenal").fadeOut(500);
    			var date1= $(this).parent().attr('id');    						
    			$("#"+whichBt).val(dateformat(date1));    			
    			if(whichBt=='startDate'){    				
    				$("#btEndDate").click();
    				var curIndex=$('td[id]').index($(this).parent());    				  				
    				var tdsBefore ="td[id]:lt("+ curIndex+")";     				
    				
    				var tdsAfter ="td[id]:gt("+ curIndex+")";
    				var firstRest=$('td[id]').index($(tdsAfter).filter(".rest").first());    				
    				var tdsAfterFirstRest ="td[id]:gt("+ firstRest+")";
    				$(tdsBefore).removeClass("open").addClass("rest");
    				$(tdsAfterFirstRest).removeClass("open").addClass("rest");
    				$('#calOrderAlert').text("請選擇結束日期");
    				if($(this).attr("class").indexOf("monthCount1")>0 && $(".monthCount2").length==0){
    					$(".monthCount3").parent().removeClass('open').addClass('rest');    	
    				} 
    			}    			
    		});
    		
    		var calArray=['calendarPanelCur','calendarPanelNext','calendarPanelAfterNext'];
    		var calShowCount =0;
    		$('#backCal').click(function(){
    			$('#orederCalendar > div').hide();
    			$('#navCal > span').hide();
    			calShowCount--;
    			if(calShowCount<0)
    				calShowCount=0;
    			$("."+calArray[calShowCount]).show();
    			return false;
    		});
    	
			$('#nextCal').click(function(){
				$('#orederCalendar > div').hide();
				$('#navCal > span').hide();
    			calShowCount++;
    			if(calShowCount>2)
    				calShowCount=2;
    			$("."+calArray[calShowCount]).show();
    			return false;
    		});
			
			$('#backCal').click();
			
    		function dateformat(nonFormat){
    			var nonArray = nonFormat.split("-");
    			var format = nonArray[0]+"-"+(+nonArray[1]+1<10?"0"+(+nonArray[1]+1):+nonArray[1]+1)+"-"
    						+(nonArray[2]<10?"0"+(nonArray[2]):nonArray[2]);
    			return format;
    		}    		
			
    		<% if(request.getAttribute("pickSrv")==null){%>
    			$("#divPick").hide();
    		<%}%>
    	});
    </script>    
    <title>訂購寄養</title>    
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>
	<div class="row">
	    <div class="col-md-8">
			<ol class="breadcrumb myBreadCrumb">
			  <li><a href="<%= request.getContextPath()%>/za101g2/front/store/SearchStore.jsp">尋找寄養</a></li>	
			   <li><a href="<%=request.getContextPath()%>/StoreServlet?action=lookOverStore&storeId=${otherStoreVO.id}">寄養商店資訊</a></li>			
			  <li class="active">訂購寄養</li>
			</ol>
		</div>
    </div> 
	<div class="row">
		<div class ="col-md-6">
			<form method="post" action="<%= request.getContextPath() %>/orderBoardServlet" id="bookForm">
			<div class="panel panel-default">
				<div class="panel-body">
				    <div class="form-group" id="divPetRadio">
				        <label>寄養數量:<font color="red">*</font></label>
				        <div class="radio">			            
			            	<c:forEach var="count" begin="1" end="${otherStoreVO.maxQuantitly}">
			            		<label  class="radio-inline"  for="petRadio${count}">
			            			<input type="radio" name="custPetQty" id="petRadio${count}" value="${count}" class="petRadio">
			            			${count}隻
			            		</label> 
			            	</c:forEach>					            
				        </div>
				 	</div>
			  	</div>
			  </div>			   	
			   	<div id ="divPick">
				   	<div class="panel panel-default">
						<div class="panel-body">
					    	<div class="form-group">			    
					    	<label>接送服務:${pickSrv.price}元(${pickSrv.name }公里內)<font color="red">*</font></label>			    		         
					        <div class="radio" id="pickRadio">	
			            		<label  class="radio-inline" for="nopickSrv" id="lbNoPickSrv">
			            			<input type="radio" name="pickSrv" id="nopickSrv" value="n">不需要
			            		</label>
			            		<label  class="radio-inline" for="pickSrv" id="lbPickSrv">
			            			<input type="radio" name="pickSrv" id="pickSrv" value="y">需要
			            		</label>  		            				            
					        </div>
					        </div>
						    <div class="form-group" id="divCustAddress" style="display:none;">					    			  		
					        	<label for="custAddress" >接送地點:<font color="red">*</font></label>			        	
					        	<input type="text" name="custAddress" id="custAddress" class="form-control" >			    				    
						    </div>
				         </div>			        
					</div>
			    </div>
			    <div class="panel panel-default">
					<div class="panel-body">
					     <div class="form-group">			  		
				        	<label for="startDate" >開始日期:<font color="red">*</font></label>			        	
				        	<input type="date" name="startDate" id="startDate" readonly >
				        	
				        			        		    				    
					    </div>
					    <div class="form-group">
					    	<label for="endDate" >結束日期:<font color="red">*</font></label>			        	
				        	<input type="date" name="endDate" id="endDate"  readonly>
					    </div>
					    <div>
					    	<input type="button" id="btStartDate" value="選擇日期" class="btn btn-info">
					    </div>
					 </div>
				</div>	    
			    <div class="form-group">		        	
		        	<input type="button" id="btEndDate" value="選擇" style="display:none">				    				    
			    <c:if test="${not empty errorMsgs}">
					<div class="alert alert-danger" role="alert" id="errorMsg">						
						<ul>
							<c:forEach var="message" items="${errorMsgs.values()}">
								<strong>${message}</strong><br>
							</c:forEach>
						</ul>
					</div>
				</c:if>
			    </div>
			    <div class="form-group">			    	
			    	<input type="hidden" name="action" value="startBook">
			    	<input type="hidden" name="otherStoreId" value="${otherStoreVO.id}">
			    </div>			    
			</form>			
		</div>
	</div>
	<div class="row" id="navCalPenal">
		<div class="col-md-6">
			<div class="alert alert-info col-md-12" role="alert" id="calOrderAlert">請選擇開始日期</div>
			
			<div class="col-md-12 ">
				<div class="col-xs-4"><button class="btn center-block  btn-info" id="backCal"><span class="glyphicon glyphicon-triangle-left" aria-hidden="true"></span></button></div>
				<div class="col-xs-4" id="navCal">
					<span class="calendarPanelCur"><%= curYear %>年&nbsp;&nbsp;${storeCalendarMap[curMonth] }</span>
					<span  class="calendarPanelNext"><%= curMonth+1<12?curYear:curYear+1 %>年&nbsp;&nbsp;${storeCalendarMap[nextMonth] }</span>
					<span  class="calendarPanelAfterNext"><%= curMonth+2<12?curYear:curYear+1 %>年&nbsp;&nbsp;${storeCalendarMap[afterNextMonth] }</span>
				</div>
				<div class="col-xs-4"><button class="btn center-block btn-info" id="nextCal"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></button></div>
			</div>
		</div>
	</div>
	<div class="row" style="display:none;" id="orederCalendar">				
		<div class="calendarPanel col-md-6 calendarPanelCur">
			<% int monthCount = 1; %>
	    	<%
	     		monthCalendarList =(List<StoreCalendarVO>) request.getAttribute("curStoreCalendar");
				thisYear = curYear;
				thisMonth = curMonth;
			%>
			<%@include file="/za101g2/front/orderBoard/CanlendarForCustOrder.file" %>
		</div>
		<div class="calendarPanel col-md-6 calendarPanelNext" style="display:none;">
			<%
	     		monthCalendarList =(List<StoreCalendarVO>)request.getAttribute("nextStoreCalendar");
				thisYear = nextYear;
				thisMonth = nextMonth;
			%>
			<%@include file="/za101g2/front/orderBoard/CanlendarForCustOrder.file" %>
		</div>
		<div class="calendarPanel col-md-6 calendarPanelAfterNext"  style="display:none;">
			<%
	     		monthCalendarList =(List<StoreCalendarVO>)request.getAttribute("afterNextStoreCalendar");
				thisYear = afterNextYear;
				thisMonth = afterNextMonth;
			%>
			<%@include file="/za101g2/front/orderBoard/CanlendarForCustOrder.file" %>	
		</div>
		
	</div>
	<div class="row">
		<div class="col-md-6">
			<button type="button" class="btn btn-warning center-block" id="btBookSubmit">下一步</button>
		</div>
	</div>
<%@ include file="/files/PetribeFoot.file" %>  

 