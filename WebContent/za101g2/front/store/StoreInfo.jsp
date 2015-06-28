<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     

<%@page import ="za101g2.member.model.*" %>
<%@page import ="za101g2.store.model.*" %>
<%@page import ="za101g2.serviceList.model.*" %>
<%@page import ="za101g2.storeCalendar.model.*" %>
<%@page import ="java.util.*" %>        
<%	
	Calendar calendar =  GregorianCalendar.getInstance();
//calendar.set(2015,10,1);
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
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/flexslider.css">
    <script src="<%= request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
    <script src="<%= request.getContextPath()%>/js/bootstrap.min.js"></script>
    <script src="<%= request.getContextPath()%>/js/jquery.flexslider-min.js"></script>
  
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCUsMbG0pRwG9c48pWlVEOBG65opDqLK4A"></script>
    <script>
    var geocoder;
    var directionsDisplay;
    var directionsService = new google.maps.DirectionsService();
    var map;
    var storeAddress;
    var latStore;
    var lngStore;
    function initialize() {
    }
    google.maps.event.addDomListener(window, 'load', initialize);
    
    function codeAddress(address) {	    
        geocoder.geocode({
            'address': address
        }, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {	                
                var marker = new google.maps.Marker({
                    map: map,
                    position: results[0].geometry.location
                });                 
                storeAddress = results[0].geometry.location;                
            } else {
                alert("Geocode was not successful for the following reason: " + status);
            }            
        });  
    }
    
    function calcRoute() {
    	custAddress=$("#navAddress").val();
    	
    	if(custAddress.length==0){
    		alert("請輸入導航位址");
    		return false;
    	}
    		
        var request = {
            origin: new google.maps.LatLng(storeAddress.lat(),storeAddress.lng()),
            destination:custAddress,
            // Note that Javascript allows us to access the constant
            // using square brackets and a string value as its
            // "property."
            travelMode: google.maps.TravelMode.DRIVING
        };
        directionsService.route(request, function(response, status) {
            if (status == google.maps.DirectionsStatus.OK) {
                directionsDisplay.setDirections(response);
            }
        });
    }

    
   	$(function(){   	
   		geocoder = new google.maps.Geocoder();
		codeAddress("${storeInfoMap['otherStoreVO'].address}"); 
		directionsDisplay = new google.maps.DirectionsRenderer();
		<c:if test="${not empty memberVO}">
   			var memberVOId=${memberVO.id};
   		</c:if>
   		var otherStoreVOId=${storeInfoMap['otherStoreVO'].id};
   		//刷新MAP當顯示TAB PANEL後
   	    $('#infoTab li:eq(1) a').on('shown.bs.tab', function(e){  
   	    	codeAddress("${storeInfoMap['otherStoreVO'].address}");    	
   	    	
   		    var mapProp = {
   		        center: new google.maps.LatLng(storeAddress.lat(),storeAddress.lng()),
   		        zoom:16,
   		        mapTypeId:google.maps.MapTypeId.ROADMAP
   		    };
   		    map=new google.maps.Map(document.getElementById("map"),mapProp);
   		    directionsDisplay.setMap(map);
   	    	google.maps.event.trigger(map, 'resize');
   	    });//end of 刷新MAP
   	    
   	   $("#btStoreNav").click(calcRoute);
   		
   	 	$('#infoTab li:eq(3) a').click(function(){
   	 		$(".btCalendar a:first").click();
   	 		
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
  		
  		$(".btCalendar a").click(function(){  			
  			$(".calendarPanel").hide();
  			$(".btCalendar a").removeClass("btn-info");
  			$(this).addClass("btn-info");
  			var panel = $(this).attr("href");
  			$(panel).show();
  			return false;
  		});
  		
  		$('#carousel').flexslider({
  		    animation: "slide",
  		    controlNav: true,
  		    animationLoop: false,
  		    slideshow: false,
  		    itemWidth: 100,
  		    itemMargin: 3,
  		    asNavFor: '#slider'
  		  });
  		 
  		  $('#slider').flexslider({
  		    animation: "slide",
  		    controlNav: false,
  		    animationLoop: false,
  		    slideshow: false,
  		    sync: "#carousel"
  		  });
  		  
  		  $("#btMsgSubmit").click(function(){
  			  $.ajax({
  				  url:'<%= request.getContextPath()%>/StoreBoardServlet',
  				  type:'post',
  				  data:$("#msgForm").serialize(),
  				  dataType:'json',
  				  success:function(data){  					
  					 MsgSubmitSucc(data);
  					 $("textarea").val("");
  					 $("#btMsgSubmit").addClass("disabled");
  				  }
  			  });
  		  });//end of click
  		  
  		  $(".msgDel").click(msgDel);
  		  
  		  function msgDel(){
  			  var bt=$(this);
  			  $.ajax({
  				url:'<%= request.getContextPath()%>/StoreBoardServlet',
  				type:'post',
  				data:$(this).parent().serialize(),
  				dataType:'html',
  				success:function(data){
  					alert(data=="success");
  					if(data=="success")
  						bt.parent().parent().hide();
  				}
  			  });
  			  
  			  return false;
  		  };
  		  
  		  
	  	  $('textarea').bind('input propertychange', function() {
	  		if($(this).val().trim().length==0){
	  			$("#btMsgSubmit").addClass("disabled");	  			
	  		}else{
	  			$("#btMsgSubmit").removeClass("disabled");	  			
	  		}
	  	  	
	      });
  		  
  		  function MsgSubmitSucc(data){
  			  var str;
  			  var str1 ='<div class="row storeInfoPanel" style="border-top:1px solid #F0D19A;">';
  			  var str3 =
				'<div  class="clearfix" style="padding:25px 0px 0px 0px;">'+
				'<div class="col-xs-2" >'+
				'<a href="<%=request.getContextPath()%>/memberHome/memberHome.do?action=memberHome&memId='+data.memberId+'" class="thumbnail text-center">'+
				      '<img src="<%=request.getContextPath() %>/PicForMember?id='+data.memberId+'" alt="...">'+						     
				    '</a>'+							    
			 	'</div>'+	
				'<div class="col-xs-10">'+
					'<h6> <a href="<%=request.getContextPath()%>/memberHome/memberHome.do?action=memberHome&memId='+data.memberId+'">'+data.nickname+'</a>&nbsp&nbsp'+data.boardMsgDate+'</h6>'+
					'<p>'+data.boardMsg+'</p>'+										
				'</div></div></div>	';
			  var str2='<form><input type="hidden" value="'+data.id+'" name="msgId"><input type="hidden" name="action" value="updateMsg">'+											
				'<button class="btn btn-xs msgDel" style="float:right" id="msgDel'+data.id+'">'+
					'<span class="glyphicon glyphicon-remove"></span></button></form>';
// 			  if(data.memberId==memberVOId || data.storeId==otherStoreVOId)
// 				  str=str1+str2+str3;
// 			  else
// 				  str=str1+str3;
			  str=str1+str2+str3;
  			  $("#custMsgPanel").prepend(str);
  			  $("#msgDel"+data.id).click(msgDel);
  		  }//end of MsgSubmitSucc
  		  
  		 $("#btMoreMsg").click(function(){
 			  var $strMsg=$("#custMsgPanel").children().last().attr("id").split("-");
 			  var msgId= $strMsg[1];	
 			
 			  $.ajax({
 				url:'<%= request.getContextPath()%>/StoreBoardServlet',
 				type:'post',
 				data:'action=getMoreMsg&storeId='+otherStoreVOId+'&msgId='+msgId,
 				dataType:'json',	  				
 				success:function(data){
 					for(var i=0;i<data.msgList.length;i++){
 						getMore(data.msgList[i]);			
 					}
 					if(data.msgList.length<5)
 						 $("#btMoreMsg").text("資料到底了").addClass("disabled");
 				}
 			  });
 			  
 			  return false;
 		  });//end of click
 		  
 		 function getMore(data){
 			  var str;
 			  var str1 ='<div class="row storeInfoPanel" style="border-top:1px solid #F0D19A;" id="msg-'+data.msgId+'">';
 			  var str3 =
				'<div  class="clearfix" style="padding:25px 0px 0px 0px;">'+
				'<div class="col-xs-2" >'+
				'<a href="<%=request.getContextPath()%>/memberHome/memberHome.do?action=memberHome&memId='+data.memberId+'" class="thumbnail text-center">'+
				      '<img src="<%=request.getContextPath() %>/PicForMember?id='+data.memberId+'" alt="...">'+						     
				    '</a>'+							    
			 	'</div>'+	
				'<div class="col-xs-10">'+
					'<h6> <a href="<%=request.getContextPath()%>/memberHome/memberHome.do?action=memberHome&memId='+data.memberId+'">'+data.nickname+'</a>&nbsp&nbsp'+data.boardMsgDate+'</h6>'+
					'<p>'+data.boardMsg+'</p>'+										
				'</div></div></div>	';
			  var str2='<form><input type="hidden" value="'+data.id+'" name="msgId"><input type="hidden" name="action" value="updateMsg">'+											
				'<button class="btn btn-xs msgDel" style="float:right" id="msgDel'+data.id+'">'+
					'<span class="glyphicon glyphicon-remove"></span></button></form>';
			  if(data.memberId==memberVOId)
				  str=str1+str2+str3;
			  else
				  str=str1+str3;			
			 
 			  $("#custMsgPanel").append(str);
 			  $("#msgDel"+data.id).click(msgDel);
 		  }//end for function getMore()
  		
 	   
   	});	

    </script>
    <title>商店資訊</title>
</head>
<body>          
<%@ include file="/files/PetribeHead.file" %>   
    <div class="row">
	    <div class="col-md-8">
			<ol class="breadcrumb myBreadCrumb">
			    <li><a href="<%= request.getContextPath()%>/za101g2/front/store/SearchStore.jsp">尋找寄養</a></li>		
			  <li class="active">商店資訊</li>
			</ol>
		</div>
    </div>           
    <div class="row">
		<div class="col-md-8">								
			<h1>${storeInfoMap['otherStoreVO'].name }
				<span class="pull-right">
					<a role="button" class="btn btn-warning btn-lg" href="<%=request.getContextPath() %>/orderBoardServlet?action=startOrder&otherStoreId=${storeInfoMap['otherStoreVO'].id}">我要預訂</a>
				</span>
			</h1>							
		</div>
	</div>
	<br>
	<div class="row" >
		<div class="col-md-8 storeInfoPanel clearfix">
			<div class="col-xs-12"></div>
			<div class="col-xs-3">
				<div ><img src="<%= request.getContextPath() %>/PicForStrore?id=${storeInfoMap['otherStoreVO'].id}"  class="img-circle img-responsive"></div>
				<div class="text-center"></div>								
			</div>
			<div class="col-xs-9">
				<h3>關於${storeInfoMap['otherMemberVO'].nickname}</h3>
				<p> ${storeInfoMap['otherStoreVO'].info}</p>														
			</div>								
		</div>
	</div>	
	
	
	<div class="row">						
		<div class="col-md-8 storeInfoPanel">
			<div role="tabpanel" style="height:550px;" >		
		  	  <!-- Nav tabs -->
			  <ul class="nav nav-tabs" role="tablist" id="infoTab">
			    <li role="presentation" class="active"><a href="#storePic" aria-controls="storePic" role="tab" data-toggle="tab">照片</a></li>
			    <li role="presentation"><a href="#storeMap" aria-controls="storeMap" role="tab" data-toggle="tab">商店地圖</a></li>
			    <li role="presentation"><a href="#srvAndCharge" aria-controls="srvAndCharge" role="tab" data-toggle="tab">服務價目表</a></li>
			    <li role="presentation"><a href="#storeCalendar" aria-controls="storeCalendar" role="tab" data-toggle="tab">商店日曆</a></li>
			  </ul>
			
			  <!-- Tab panes -->
			  <div class="tab-content " style="height:calc(90%);">
			    <div role="tabpanel" class="tab-pane active" id="storePic" style="height:calc(100%);">
			    	<c:if test="${empty storePhotoVOList }">
			    		<h3>尚無商店照片</h3>
			    	</c:if>
			    	<c:if test="${not empty storePhotoVOList}">
			    	
			    	<div id="slider" class="flexslider">
					  <ul class="slides">
					  <c:forEach var="pic" items="${storePhotoVOList}">
					    <li>
					      <img src="<%=request.getContextPath() %>/PicForStorePhoto?id=${pic.id}" style="height:300px;width:auto;"/>
					    </li>									    
					    <!-- items mirrored twice, total of 12 -->
					    </c:forEach>	
					  </ul>
					</div>
					<div id="carousel" class="flexslider">
					  <ul class="slides">
					   <c:forEach var="pic" items="${storePhotoVOList}">
					    <li>
					      <img src="<%=request.getContextPath() %>/PicForStorePhoto?id=${pic.id}" />
					    </li>									    
					    <!-- items mirrored twice, total of 12 -->
					    </c:forEach>	
					  </ul>
					</div>	
					
			    	</c:if>
			    </div>
			    <div role="tabpanel" class="tab-pane" id="storeMap" style="height:calc(100%);padding-top:15px">
			    	<div style="height:10%">
				    	
				    		<label for="nav-map">所在地址</label>
				    		<input type="text" id="navAddress">	
			    			<button type="button" class="btn btn-default btn-sm" id="btStoreNav">導航</button>
			    		
			    	</div>
			    	<div id="map" style="height:80%">	
			    			
					</div>
			    </div>
			    <div role="tabpanel" class="tab-pane" id="srvAndCharge">
			    	<div class="col-md-12">
			    		<h3>接送服務:
			    			<% if(((Map<String,String>)request.getAttribute("storeInfoMap")).get("pickSrv") !=null){ %>
			    				${storeInfoMap["pickSrv"].price}元(${storeInfoMap["pickSrv"].name}公里內) 
			    			<%}else{ %>
			    				尚無接送服務
			    			<%} %>
			    		</h3>
			    	</div>
			    <c:if test="${storeInfoMap['requiredDogSrv']!=null}">
				    	<div class="col-md-6">
				    		<table class="table table-condensed">								    		
				    			<thead>
				    				<tr>
				    				<th>關於狗狗的服務</th>							    				
				    				</tr>
				    			</thead>
				    			<tbody>
				    				<tr>
					    				<td>住宿(每天)</td>
					    				<td>${storeInfoMap['requiredDogSrv'].price}&nbsp&nbsp元</td>
				    				</tr>
				    				
				    				<c:forEach var="custDogSrv" items="${storeInfoMap['custDogSrvList']}">
					    				<tr>
						    				<td>${custDogSrv.name}(${serviceListMap[custDogSrv.chargeType]})</td>
						    				<td>${custDogSrv.price}&nbsp&nbsp元</td>
					    				</tr>
				    				</c:forEach>
				    			</tbody>							    		
				    			
				    		</table>
				    	</div>
				     </c:if>
				     <c:if test="${storeInfoMap['requiredCatSrv']!=null}">
				    	<div class="col-md-6">
				    		<table class="table table-condensed">								    			
				    			<thead>
				    				<tr>
				    				<th>關於貓咪的服務</th>							    				
				    				</tr>
				    			</thead>
				    			<tbody>
				    				<tr>
					    				<td>住宿(每天)</td>
					    				<td>${storeInfoMap['requiredCatSrv'].price}&nbsp&nbsp元</td>
				    				</tr>
				    				
				    				<c:forEach var="custCatSrv" items="${storeInfoMap['custCatSrvList']}">
					    				<tr>
						    				<td>${custCatSrv.name}(${serviceListMap[custCatSrv.chargeType]})</td>
						    				<td>${custCatSrv.price}&nbsp&nbsp元</td>
					    				</tr>
				    				</c:forEach>
				    			</tbody> 		
				    			
				    		</table>
				    	</div>
				      </c:if>	
			    </div>
			    <div role="tabpanel" class="tab-pane" id="storeCalendar">
			    	<div class="col-md-12 btCalendar" >
			    		<div class="btn-group btn-group-justified" role="group">							    		
				    		<div class="btn-group" role="group">
						    	<a role="button" class="btn btn-default" href="#calendarPanelCur"><%= curYear%>年${storeCalendarMap[curMonth]}</a>
						  	</div>
						  	<div class="btn-group" role="group">
						  	  <a role="button" class="btn btn-default" href="#calendarPanelNext"><%= nextYear%>年${storeCalendarMap[nextMonth]}</a>
							  </div>
						  	<div class="btn-group" role="group">
						    	<a role="button" class="btn btn-default" href="#calendarPanelAfterNext"><%= afterNextYear%>年${storeCalendarMap[afterNextMonth]}</a>
						 	</div>
				    	</div>
				    	<div class="calendarPanel" id="calendarPanelCur">
					    	<%
					     		monthCalendarList =(List<StoreCalendarVO>) ((Map<String,Object>)request.getAttribute("storeInfoMap")).get("curStoreCalendar");
								thisYear = curYear;
								thisMonth = curMonth;
							%>
							<%@include file="/za101g2/front/store/CanlendarForCust.file" %>
						</div>
						<div class="calendarPanel" id="calendarPanelNext">
							<%
					     		monthCalendarList =(List<StoreCalendarVO>) ((Map<String,Object>)request.getAttribute("storeInfoMap")).get("nextStoreCalendar");
								thisYear = nextYear;
								thisMonth = nextMonth;
							%>
							<%@include file="/za101g2/front/store/CanlendarForCust.file" %>
						</div>
						<div class="calendarPanel" id="calendarPanelAfterNext">
							<%
					     		monthCalendarList =(List<StoreCalendarVO>) ((Map<String,Object>)request.getAttribute("storeInfoMap")).get("afterNextStoreCalendar");
								thisYear = afterNextYear;
								thisMonth = afterNextMonth;
							%>
							<%@include file="/za101g2/front/store/CanlendarForCust.file" %>	
						</div>									    	
					</div>   	
			    </div>
			  </div>		
			</div>
		</div>
	</div>
	
<!-- 	完成信箱認證才可留言 -->
	<c:if test="${memberVO.status > 0}">
		<div class="row" style="margin-top:15px;">
			<div class="col-md-8 storeInfoPanel clearfix" style="padding:2% 5px 0px 5px;">	
				<div class="col-xs-2" >
				    <a href="#" class="thumbnail text-center">
				      <img src="<%=request.getContextPath() %>/PicForMember?id=${memberVO.id}" alt="...">
				      ${memberVO.nickname }
				    </a>							    
				 </div>	
				<div class="col-xs-8">								
					<form class="form-group" id="msgForm">
						<textarea rows="3" class="form-control" name="comments">
						</textarea>
						<input type="hidden" name="memberId" value="${memberVO.id}">
						<input type="hidden" name="storeId" value="${storeInfoMap['otherStoreVO'].id }">
						<input type="hidden" name="action" value="addMsg">
					</form>		
				</div>
				<div class="col-xs-2" >								
					<button class="btn btn-primary disabled" id="btMsgSubmit">留言</button>		
				</div>									
									
			</div>
		</div>
	</c:if>
	<c:if test="${memberVO.status < 1}">
		<div class="row">
			<div class="col-md-8">
				<div class="alert alert-warning text-center noResut" role="alert">完成信箱認證後才可留言</div>
			</div>
		</div>
	</c:if>
	
	<div class="row" style="margin-top:15px;">
		<div class="col-md-8" id="custMsgPanel">
			
				<c:forEach var="msg" items="${storeBoardList}">
				
				<div class="row storeInfoPanel" style="border-top:1px solid #F0D19A;" id="msg-${msg.id}">
					<c:if test="${memberVO.id==msg.memberVO.id || storeVO.id == msg.storeVO.id}">
						<form>
							<input type="hidden" value="${msg.id}" name="msgId">
							<input type="hidden" name="action" value="updateMsg">											
							<button class="btn btn-xs msgDel" style="float:right">
								<span class="glyphicon glyphicon-remove"></span>							
							</button>
						</form>	
					</c:if>	
					<div  class="clearfix" style="padding:25px 0px 0px 0px;">
						<div class="col-xs-2" >
						    <a href="<%=request.getContextPath()%>/memberHome/memberHome.do?action=memberHome&memId=${msg.memberVO.id}" class="thumbnail text-center">
						      <img src="<%=request.getContextPath() %>/PicForMember?id=${msg.memberVO.id}" alt="...">
						     
						    </a>							    
					 	</div>	
						<div class="col-xs-10">
							<h6 class="text-muted"> <a href="<%=request.getContextPath()%>/memberHome/memberHome.do?action=memberHome&memId=${msg.memberVO.id}">${msg.memberVO.nickname }</a>&nbsp&nbsp${msg.boardMsgDate }</h6>
							<p>${msg.boardMsg}</p>										
						</div>
						
					</div>
					
				</div>									
				</c:forEach>
			
		</div>	
	</div>
	
	<div class="row" style="margin-top:15px;">
		<div class="col-md-8">
			<button class="btn btn-link center-block" id="btMoreMsg">查看更多</button>
		</div>
	</div>
	

<%@ include file="/files/PetribeFoot.file" %>

 