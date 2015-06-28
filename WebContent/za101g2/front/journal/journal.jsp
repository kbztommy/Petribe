<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="za101g2.member.model.*"%>
<%@ page import="za101g2.journal.model.*"%>
<%@ page import="za101g2.journalBoard.model.*"%>
<%@ page import="za101g2.journalAssess.model.*"%>

<html>

<head>
<meta name="viewport" content="width=device-width initial-scale =1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/mainStyle.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/journal.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<title>${journalVO.title}</title>

<script type="text/javascript">	
		$(function(){
		    $('[rel="popover"]').popover({
		        container: 'body',
		        html: true,
		        content: function () {
		            var clone = $($(this).data('popover-content')).clone(true).removeClass('hide');
		            return clone;
		        }
		    }).click(function(e) {
		        e.preventDefault();
		    });
		    
		    $(".boardReportBtn").hide();
		    
			$(".board").hover(function(){
				$(this).find(".boardReportBtn").show();	
			},function(){
				$(this).find(".boardReportBtn").hide();
			});
		    
		});
	</script>

							<% String hint = (String)request.getAttribute("hint"); %>
							<% if(hint!=null){ %>
							<% if(hint.length() > 0){ %>
							<script type="text/javascript">
							function init(){
								alert("<%=hint%>");
								}	 

								window.onload=init;
							</script>
							<%}%>
							<%}%>

<script>
		function getValue (){
			document.getElementById("journalMsgId").value = document.getElementById("journalMsgId2").value;
		}
</script>

</head>

<body>

			<div class="modal fade" id="report" tabindex="-1" role="dialog" aria-labelledby="reportLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h2 class="modal-title" id="reportLabel">檢舉文章</h2>
						</div>
						<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/journalReport/journalReport.do" name="journalReport">
							<div class="modal-body"><h4>
								<textarea name="comments" class="form-control" placeholder="輸入說明" rows="4" maxlength="66" style="font: normal 20px Tahoma, Arial, Verdana;" required></textarea>
								<input type="hidden" name="journalId" value="${journalVO.id}">
								<input type="hidden" name="action" value="report">
							</h4></div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-primary"><h4>確定送出</h4></button>
								<button type="button" class="btn btn-default" data-dismiss="modal"><h4>取消</h4></button>
							</div>
						</FORM>
					</div>
				</div>
			</div>

			<!-- 未登入 -->
			<div class="modal fade" id="login" tabindex="-1" role="dialog" aria-labelledby="loginLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h2 class="modal-title" id="loginLabel">登入確認</h2>
						</div>
						<div class="modal-body"><h3><b>您需要先登入才能夠使用此項功能</b></h3></div>
						<div class="modal-footer">
							<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/za101g2/front/member/login.jsp" name="journalReport">
							<input type="hidden" name="requestURL" value="/journal/journal.do?action=getOne_For_Display&id=${journalVO.id}">
							<button type="submit" class="btn btn-primary"><h4>確定</h4></button>
							<button type="button" class="btn btn-default" data-dismiss="modal"><h4>取消</h4></button>
							</FORM>
						</div>
					</div>
				</div>
			</div>
			
			<div class="modal fade" id="boardReport" tabindex="-1" role="dialog" aria-labelledby="boardReportLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h2 class="modal-title" id="boardReportLabel">檢舉留言</h2>
						</div>
						<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/journalReport/journalReport.do" name="journalReport">
							<div class="modal-body"><h4>
								<textarea name="comments" class="form-control" placeholder="輸入說明" rows="4" maxlength="66" style="font: normal 20px Tahoma, Arial, Verdana;" required></textarea>
								<input type="hidden" id="journalMsgId" name="journalMsgId">
								<input type="hidden" name="journalId" value="${journalVO.id}">
								<input type="hidden" name="action" value="boardReport">
							</h4></div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-primary"><h4>確定送出</h4></button>
								<button type="button" class="btn btn-default" data-dismiss="modal"><h4>取消</h4></button>
							</div>
						</FORM>
					</div>
				</div>
			</div>
			
<%@ include file="/files/PetribeHead.file"%>	
	<div class="col-md-8 journalFrame">
		<div class="row">
			<header class="col-md-12 title">${journalVO.title}</header>
			<section class="col-md-4" id="member">
				<div>
					<h4>
						<a href="<%=request.getContextPath()%>/memberHome/memberHome.do?action=memberHome&memId=${journalMemberVO.id}">作者：${journalMemberVO.nickname}</a>
					</h4>
				</div>
			</section>
			<div class="pull-right"><header class="releaseDate">發表日期${journalVO.releaseDate}</header></div>
		</div>
		<div class="row">
		
			<c:if test="${not empty memberVO}">
				<c:if test="${memberVO.id!=journalMemberVO.id}">
					<header class="col-md-1 journalPop pull-right"></header>
				</c:if>

				<c:if test="${memberVO.id==journalMemberVO.id}">
					<div class="col-md-1 journalPop pull-right">
						<a href="#" rel="popover" data-popover-content="#myPopover" data-trigger="focus" data-placement="bottom" class="btn">
							<span class="glyphicon glyphicon-pencil"></span>
						</a>
					</div>
					<div id="myPopover" class="hide">
						<div class="editJournal">
							<a href="<%= request.getContextPath() %>/journal/journal.do?action=getOneForUpdate&journalId=${journalVO.id}">編輯</a>
						</div>
						<div>
							<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/journal/journal.do" name="journalChange">
								<button type="submit" class="delBtn">刪除</button>
								<input type="hidden" name="journalId" value="${journalVO.id}">
								<input type="hidden" name="action" value="delete">
							</FORM>
						</div>
					</div>
				</c:if>
			</c:if>

			<c:if test="${empty memberVO}">
				<header class="col-md-1 journalPop pull-right"></header>
			</c:if>

			
			<aside class="col-xs-2 assess pull-left">
				<% JournalVO journalVO = (JournalVO) request.getAttribute("journalVO"); %>
				<c:if test="${empty memberVO}">
					<span class="glyphicon glyphicon-thumbs-up"></span>
					<button type="button" class="assessBtn" data-toggle="modal" data-target="#login">讚</button>
				</c:if>
				<c:if test="${not empty memberVO}">
					<% JournalAssessService assessSVC = new JournalAssessService(); %>
					<% MemberVO memberVO = (MemberVO)session.getAttribute("memberVO"); %>
					<% if(assessSVC.judgeAssess(journalVO.getId(), memberVO.getId())){ %>
					<a href="<%= request.getContextPath() %>/memberHome/memberHome.do?action=deleteJournalAssess&journalId=<%= journalVO.getId() %>">
						<span class="glyphicon glyphicon-thumbs-up"></span>
						<button type="button" class="assessBtn">收回讚</button>
					</a>
					<% } else { %>
					<a href="<%= request.getContextPath() %>/memberHome/memberHome.do?action=insertJournalAssess&journalId=<%= journalVO.getId() %>">
						<span class="glyphicon glyphicon-thumbs-up"></span>
						<button type="button" class="assessBtn">讚</button>
					</a>
					<% } %>
				</c:if>
				<% List<JournalAssessVO> assessVOList = (List<JournalAssessVO>)request.getAttribute("assessVOList"); %>
				<span class="badge"><%= assessVOList.size() %></span>
			</aside>
		</div>
		<div class="row article">
		<c:if test="${journalVO.status=='0'}">
		${journalVO.article}
		</c:if>
		<c:if test="${journalVO.status=='1'}">
		<h1><font color="#F29327"><b>文章已鎖定</b></font></h1>
		</c:if>
		</div>
		<header class="row" style="text-align: left; padding: 0px;">
			<!-- Button trigger modal -->
			<button type="button" class="btn btn-primary btn-lg reportBtn pull-right" data-toggle="modal" data-target="<c:if test="${not empty memberVO}">#report</c:if><c:if test="${empty memberVO}">#login</c:if>">檢舉</button>
		</header>
		<div class="row">
			<section class="col-xs-2 boardIcon">
				<c:if test="${not empty memberVO.icon}">
					<img style="height: 75px" src="<%=request.getContextPath()%>/Member/MemberIconDisplay?id=${memberVO.id}">
				</c:if>
				<c:if test="${empty memberVO.icon}">
					<img style="height: 75px" src="<%=request.getContextPath()%>/images/memberIcon.jpg">
				</c:if>
			</section>
			<aside class="col-xs-10">
				<div >
					<c:if test="${empty memberVO}">
						<div class="boardHead " >發表留言</div>
					</c:if>
					<c:if test="${not empty memberVO}">
						<div class="boardHead ">以${memberVO.nickname}發表留言</div>
					</c:if>
					<div class="sendBoardFrame">
						<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/memberHome/memberHome.do" name="addJournalBoard" >
						<c:if test="${empty memberVO}">
							<button type="button" class="boardTextBtn" data-toggle="modal" data-target="#login" >留言...</button>
						</c:if>
						<c:if test="${not empty memberVO}">
							<textarea name="boardMsg" rows="1" cols="68" maxlength="46" placeholder="留言..." required class="form-control sendBoard"></textarea>
						</c:if>
							<input type="hidden" name="journalId" value="${journalVO.id}" />
							<input type="hidden" name="action" value="insertJournalBoard">
							<c:if test="${empty memberVO}">
								<input type="button" class="btn btn-default boardBtn" data-toggle="modal" data-target="#login" value="送出留言">
							</c:if>
							<c:if test="${not empty memberVO}">
								<input type="submit" class="btn btn-default boardBtn" value="送出留言">
							</c:if>
						</FORM>
					</div>
				</div>
			</aside>
		</div>
		
			<c:forEach var="boardVO" items="${boardVOList}"><c:if test="${boardVO.isDelete == 'n'}"><div class="row board">
				<c:set var="boardMemId" value="${boardVO.memId}" scope="request" />
					<%
             				MemberService boardMemberSvc = new MemberService();
             				Integer memId = (Integer) request.getAttribute("boardMemId");
     						MemberVO boardMemberVO = boardMemberSvc.getOneMember(memId);
					%>
				<section class="col-md-4 boardIcon">
					<% if(boardMemberVO.getIcon() != null){ %>
					<img style="height: 50px" src="<%=request.getContextPath()%>/Member/MemberIconDisplay?id=<%= boardMemberVO.getId() %>">
					<% } else { %>
					<img style="height: 50px" src="<%=request.getContextPath()%>/images/memberIcon.jpg">
					<% } %>
				</section>
				<aside class="col-md-6">
					<div class="boardHead"><%= boardMemberVO.getNickname() %>
						於 ${boardVO.boardMsgDate}
					</div>
					<div class="boardMsg">${boardVO.boardMsg}</div>
				</aside>				
				<aside class="col-md-2 boardReport">
					<input type="hidden" id="journalMsgId2" value="${boardVO.id}">
					<button type="button" class="btn btn-info boardReportBtn" onmousedown="getValue()" data-toggle="modal" data-target="<c:if test="${not empty memberVO}">#boardReport</c:if><c:if test="${empty memberVO}">#login</c:if>">檢舉留言</button>
				</aside>
			</div></c:if></c:forEach>
		
		
	</div>
	
<%@ include file="/files/PetribeFoot.file"%>