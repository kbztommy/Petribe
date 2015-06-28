<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     

<%@page import ="za101g2.member.model.*" %>
<%@page import ="za101g2.store.model.*" %>
<%@page import ="za101g2.serviceList.model.*" %>
<%@page import ="java.util.*" %>        
<%
	StoreVO storeVO = (StoreVO)session.getAttribute("storeVO");
    ServiceListService serviceListSrv = new ServiceListService();
    ServiceListVO dogServiceVO = serviceListSrv.getService(storeVO.getId(),"dog", "required");
    ServiceListVO catServiceVO = serviceListSrv.getService(storeVO.getId(),"cat", "required");
    ServiceListVO dogPickServiceVO = serviceListSrv.getService(storeVO.getId(),"dog", "pick");
    
    pageContext.setAttribute("dogServiceVO",dogServiceVO);
    pageContext.setAttribute("catServiceVO",catServiceVO);
    pageContext.setAttribute("dogPickServiceVO",dogPickServiceVO);

    List<ServiceListVO> dogCustServiceVO = serviceListSrv.getCustService(storeVO.getId(), "dog");
    List<ServiceListVO> catCustServiceVO = serviceListSrv.getCustService(storeVO.getId(), "cat");
    pageContext.setAttribute("dogCustServiceVO",dogCustServiceVO);
    pageContext.setAttribute("catCustServiceVO",catCustServiceVO);
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
    <script src="<%= request.getContextPath()%>/js/storeHome.js"></script>
    <title>Document</title>
</head>
<body>
<%@ include file="StoreHomeModalCharge.file" %> 
<%@ include file="StoreHomeModalCustSrv.file" %> 
<%@ include file="StroeHomeModalPickSrv.file" %>
<%@ include file="StoreHomeModalIntro.file" %>           
<%@ include file="/files/PetribeHead.file" %>
<div class="row">
	<div class="col-md-8">
		<ul class="nav nav-pills mynav-pills">
		  <li role="presentation" class="active"><a href="<%= request.getContextPath()%>/za101g2/front/store/StoreHome.jsp">設定商店</a></li>
		  <li role="presentation"><a href="<%=request.getContextPath() %>/orderBoardServlet?action=StoreOrderList&whichPage=1">商店訂單</a></li>
		  <li role="presentation"><a href="<%= request.getContextPath()%>/za101g2/front/storeCalendar/oneCalendar.jsp">商店日曆</a></li>
		  <li role="presentation"><a href="<%= request.getContextPath()%>/za101g2/front/store/StorePhotoUpload.jsp">相簿</a></li>
		  <li role="presentation"><a href="<%= request.getContextPath()%>/za101g2/front/storeBoard/StoreBoardForStroe.jsp">留言板</a></li>
		</ul>
	</div>
</div>
	<div class="col-md-8">
		<div class="panel panel-primary">
        	<div class="panel-heading">
            	<h3 class="panel-title">商店資料</h3>                         
            </div>
            <div class="panel-body">             	
          		<div class="col-md-3 col-xs-3" >                        
             	  <img src = "<%= request.getContextPath() %>/PicForStrore?id=${storeVO.id}" id="storeInfoImg">
          		</div>
          		<div class="col-md-9 col-xs-9">
	          		<table  class="table clearTable">
		          		 <tbody>
		           		  <tr><td>商店名稱 :</td><td id="storeInfoName">${storeVO.name}</td></tr>                        
		       			  <tr><td>商店擁有者 :</td> <td>${memberVO.nickname}</td></tr>
		       			  <tr><td>商店寵物容納數量</td><td>${storeVO.maxQuantitly}</td></tr>   			  
		           		 </tbody>	
			        </table>
                </div>
                <div class="col-md-12">                        
             	  <h5><b>關於我:</b></h5><span id="storeInfoInfo" class="wordForBoard">&nbsp;&nbsp;&nbsp;&nbsp;${storeVO.info}</span>
          		</div>
            </div>
            <div class="panel-footer">
              	<label><a href="#"  data-toggle="modal" data-target="#setInfoModal"  class="modalOpen">修改基本資料</a></label>	                                                                      
            </div>                                         
        </div>
	</div>
	
	<div class="col-md-8">
		<div class="panel panel-info">
        	<div class="panel-heading">
            	<h3 class="panel-title">基本服務</h3>                         
            </div>
            <div class="panel-body">            	
          		
          		
          		<table  class="table clearTable" >   		
           		  <tbody>
           		  <% if(storeVO.getSpeciesLimit() == 1 ||storeVO.getSpeciesLimit()==3){      		
			      %>					
			  		  <tr id="dogCharge"><td>  
					   狗的住宿費用:</td>
					  <c:if test="${dogServiceVO==null}">
					      	<td>尚未新增住宿服務</td>
					  </c:if>
					  <c:if test="${dogServiceVO!=null}">
							<td>${dogServiceVO.price}元/一天</td><td>狀態:${serviceListMap.get(dogServiceVO.isOnsale)}</td>
					  </c:if>						                                           	
			  		  </tr>
			  		 		  		  						
		          <%}%>
		    
		          <% if(storeVO.getSpeciesLimit() == 2 ||storeVO.getSpeciesLimit()==3){
                  %>				
			      
			       	  <tr id="catCharge"><td>
					   貓的住宿費用:</td>
					  <c:if test="${catServiceVO==null}">
					      	<td>尚未新增住宿服務</td>
					  </c:if>
					  <c:if test="${catServiceVO!=null}">
					   		<td>${catServiceVO.price}元/一天</td><td>狀態:${serviceListMap.get(catServiceVO.isOnsale)}</td>
					  </c:if>						                                           	
			  		  </tr>				  		   		  
		  		
		          <%}%>	
		          
		          	  <tr id="dogPickSrv"><td>接送費用:</td>
					  <c:if test="${dogPickServiceVO==null}">
					      	<td>尚未新增接送服務</td>
					  </c:if>
					  <c:if test="${dogPickServiceVO!=null}">		  
							<td>${dogPickServiceVO.price}元(${dogPickServiceVO.name}公里內)</td><td>狀態:${serviceListMap[dogPickServiceVO.isOnsale]}</td>
					  </c:if>						                                           	
			  		  </tr>	
		          
		          </tbody>       			  
		          </table>
               
            </div>
            <div class="panel-footer">              
                <label><a href="#"  data-toggle="modal" data-target="#setChargeModal" class="modalOpen">設定住宿服務</a></label>
                <label><a href="#"  data-toggle="modal" data-target="#setPickSrvModal"  class="modalOpen">設定接送服務</a></label>                                                              
            </div>                                         
        </div>
	</div>		
	
	
	<div class="col-md-8">
		<div class="panel panel-info">
        	<div class="panel-heading">
            	<h3 class="panel-title">自訂服務項目</h3>                         
            </div>
            <div class="panel-body">
            	  <% if(storeVO.getSpeciesLimit() == 1 ||storeVO.getSpeciesLimit()==3){             		
			      %>
			     			
				  <div class="col-md-12"> 
				  	<table class="table clearTable">
				  	 	<caption><b>服務對象:狗</b></caption>
				  	 	
				  		<thead>
				  			<tr>
				  			<th>服務名稱</th>
							<th>價格</th>
							<th>計價方式</th>
							<th>狀態</th>									
				  			</tr>
				  		</thead>
				  		<tbody  id="dogTable">
				  <% if(dogCustServiceVO.size() > 0){ %>	
				  			<c:forEach var="aDogService" items="${dogCustServiceVO}">
				  				 <tr id="aDogService${aDogService.id}">	
 								 <td>${aDogService.name}</td>
 								 <td>${aDogService.price}</td>
 								 <td>${serviceListMap.get(aDogService.chargeType)}</td>
 								 <td>${serviceListMap.get(aDogService.isOnsale)}</td>
 								 </tr>
							</c:forEach>
					<%}else{ %>				  		
				  		</tbody>
				  	
            	    <tr  id=dognosrv><td>尚無自訂的服務</td><td></td><td></td><td></td></tr>
            	    <%}%>	    
				  	</table> 
				    
            	  </div>            	 				
		          <%}%>
		          
		          <% if(storeVO.getSpeciesLimit() == 2 ||storeVO.getSpeciesLimit()==3){             		
			      %>			     		     				
				  <div class="col-md-12"> 
				  	<table class="table clearTable table-hover">				  	 
				  	 	<caption><b>服務對象:貓</b></caption>
				  	 	
				  		<thead>
				  			<tr>
				  			<th>服務名稱</th>
							<th>價格</th>
							<th>計價方式</th>
							<th>狀態</th>									
				  			</tr>
				  		</thead>
				  		
				  		<tbody  id="catTable">
				  		<% if(catCustServiceVO.size() > 0){ %>	
				  			<c:forEach var="aCatService" items="${catCustServiceVO}">
				  				 <tr id="aCatService${aCatService.id}">	
 								 <td>${aCatService.name}</td>
 								 <td>${aCatService.price}</td>
 								 <td>${serviceListMap.get(aCatService.chargeType)}</td>
 								 <td>${serviceListMap.get(aCatService.isOnsale)}</td>
 								 </tr>
							</c:forEach>
						<%}else{ %>				  		
				  		</tbody>
				    
            	    <tr id="catnosrv"><td>尚無自訂的服務</td><td></td><td></td><td></td><tr>
            	    <%}%>		
				  	</table> 				  					        
            	  </div>            	  				
		          <%}%>          
            </div>
            <div class="panel-footer">
            	 <label><a href="#"  data-toggle="modal" data-target="#addCustSrvModal"  class="modalOpen" >新增自訂服務</a></label>                                                                  
            </div>                                         
        </div>
	</div>
	

<%@ include file="/files/PetribeFoot.file" %> 

 