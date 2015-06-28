<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@page import ="za101g2.member.model.*" %>
<%@page import ="za101g2.store.model.*" %>
<%@page import ="za101g2.orderList.model.*" %>
<%@page import ="java.util.*" %>
<%@page import ="za101g2.storeBoard.model.*" %>
<%    
	StoreBoardService storeBoardSrv = new StoreBoardService();
	pageContext.setAttribute("storeBoardSrv", storeBoardSrv);
    response.setHeader("Pragma","no-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);
    
%>
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
		$(document).ready(function(){
			var memberVOId=${memberVO.id};
	   		var storeVOId=${storeVO.id};
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
	  					if(data=="success")
	  						bt.parent().parent().hide();
	  				}
	  			  });
	  			  return false;
	  		  };//end for function msgDel
	  		  
	  		  
		  	  $('textarea').bind('input propertychange', function() {
		  		if($(this).val().trim().length==0){
		  			$("#btMsgSubmit").addClass("disabled");	  			
		  		}else{
		  			$("#btMsgSubmit").removeClass("disabled");	  			
		  		}
		      });//end for bind
	  		  
	  		  function MsgSubmitSucc(data){
	  			  var str;
	  			  var str1 ='<div class="row storeInfoPanel" style="border-top:1px solid #F0D19A;" id="msg-'+data.id+'">';
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
//	 			  if(data.memberId==memberVOId || data.storeId==otherStoreVOId)
//	 				  str=str1+str2+str3;
//	 			  else
//	 				  str=str1+str3;
				  str=str1+str2+str3;
	  			  $("#custMsgPanel").prepend(str);
	  			  $("#msgDel"+data.id).click(msgDel);
	  		  }//end for function MsgSubmitSucc()
	  		  
	  		  $("#btMoreMsg").click(function(){
	  			  if($("#custMsgPanel").children().last().attr("id")){
		  			  var $strMsg=$("#custMsgPanel").children().last().attr("id").split("-");
		  			  var msgId= $strMsg[1];	
		  			
		  			  $.ajax({
		  				url:'<%= request.getContextPath()%>/StoreBoardServlet',
		  				type:'post',
		  				data:'action=getMoreMsg&storeId='+storeVOId+'&msgId='+msgId,
		  				dataType:'json',	  				
		  				success:function(data){
		  					for(var i=0;i<data.msgList.length;i++){
		  						getMore(data.msgList[i]);			
		  					}
		  					if(data.msgList.length<5)
		  						 $("#btMoreMsg").text("資料到底了...").addClass("disabled");
		  				}
		  			  });
	  			  }else{
	  				 $("#btMoreMsg").text("資料到底了...").addClass("disabled");
	  			  }
	  			  
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
//	 			  if(data.memberId==memberVOId || data.storeId==otherStoreVOId)
//	 				  str=str1+str2+str3;
//	 			  else
//	 				  str=str1+str3;
				  str=str1+str2+str3;
	  			  $("#custMsgPanel").append(str);
	  			  $("#msgDel"+data.id).click(msgDel);
	  		  }//end for function MsgSubmitSucc()
	  		  
		  
		});//end of ready
	</script>
<title>商家留言板</title>
</head>
<body>
<%@ include file="/files/PetribeHead.file" %>
<div class="row">
	<div class="col-md-8">
		<ul class="nav nav-pills mynav-pills">
		  <li role="presentation"><a href="<%= request.getContextPath()%>/za101g2/front/store/StoreHome.jsp">設定商店</a></li>
		  <li role="presentation"><a href="<%=request.getContextPath() %>/orderBoardServlet?action=StoreOrderList&whichPage=1">商店訂單</a></li>
		  <li role="presentation"><a href="<%= request.getContextPath()%>/za101g2/front/storeCalendar/oneCalendar.jsp">商店日曆</a></li>
		  <li role="presentation"><a href="<%= request.getContextPath()%>/za101g2/front/store/StorePhotoUpload.jsp">相簿</a></li>
		  <li role="presentation" class="active"><a href="<%= request.getContextPath()%>/za101g2/front/storeBoard/StoreBoardForStroe.jsp">留言板</a></li>
		</ul>
	</div>
</div>
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
				<input type="hidden" name="storeId" value="${storeVO.id }">
				<input type="hidden" name="action" value="addMsg">
			</form>		
		</div>
		<div class="col-xs-2" >								
			<button class="btn btn-primary disabled" id="btMsgSubmit">留言</button>		
		</div>			
	</div>
</div>
				
				
					
<div class="row" style="margin-top:15px;">
	<div class="col-md-8" id="custMsgPanel">
		
			<c:forEach var="msg" items="${storeBoardSrv.getUndelete(storeVO.id)}">
			
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
						<h6 class="text-muted"> <a href="<%=request.getContextPath()%>/memberHome/memberHome.do?action=memberHome&memId=${msg.memberVO.id}">${msg.memberVO.nickname }</a>&nbsp;&nbsp;${msg.boardMsgDate }</h6>
						<p>${msg.boardMsg}</p>										
					</div>
				</div>
			</div>									
			</c:forEach>
			
	</div>
	<div class="row" style="margin-top:15px;">
		<div class="col-md-8">
			<button class="btn btn-link center-block" id="btMoreMsg">查看更多</button>
		</div>
	</div>
		
</div>	
<%@ include file="/files/PetribeFoot.file" %> 
