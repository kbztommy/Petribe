package za101g2.missingreport.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import za101g2.missingrecord.model.*;
import za101g2.missingreport.model.*;
import za101g2.missingreply.model.*;
import za101g2.member.model.*;
import za101g2.pet.model.*;

public class MissingReportServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("manageCancelReport".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************接收請求參數****************************************/
				Integer memId = new Integer(req.getParameter("memId"));
				Integer replyId = new Integer(req.getParameter("replyId"));
				/**確認回報狀態為等待審核或已封鎖**/
				MissingReplyService missingReplySvc = new MissingReplyService();
				MissingReplyVO missingReplyVO = missingReplySvc.getOneMissingReply(replyId);
				if (!(missingReplyVO.getIsRead().equals("r") || missingReplyVO.getIsRead().equals("d"))) {
					errorMsgs.put("errorStatus", "狀態不正確，無法取消檢舉");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/za101g2/back/missingReport/manageReportIndex.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************開始取消檢舉狀態************************************/
				/**更改回報人數(bountyFor)**/
				if (missingReplyVO.getIsRead().equals("r")) {
					MissingRecordService missingRecordSvc = new MissingRecordService();
					Integer recordId = missingReplyVO.getMissingRecordVO().getId();
					Integer bountyFor = missingReplyVO.getMissingRecordVO().getBountyFor();
					missingRecordSvc.changeBountyFor(recordId, bountyFor - 1);
				}
				
				MissingReportService missingReportSvc = new MissingReportService();
				missingReportSvc.deleteMissingReport(memId, replyId);
				missingReplySvc.deleteMissingReply(replyId);
				
				/***************************修改完成,準備轉交(Send the Success view)*************/
				String url = "/za101g2/back/missingReport/manageReportIndex.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/	
			} catch (Exception e) {
				errorMsgs.put("Exception", "無法完成取消封鎖:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/back/missingReport/manageReportIndex.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("manageDenyMember".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************接收請求參數****************************************/
				Integer replyId = new Integer(req.getParameter("replyId"));
				/**確認回報狀態為等待審核**/
				MissingReplyService missingReplySvc = new MissingReplyService();
				MissingReplyVO missingReplyVO = missingReplySvc.getOneMissingReply(replyId);
				if (!missingReplyVO.getIsRead().equals("r")) {
					errorMsgs.put("errorStatus", "狀態不正確，無法封鎖");
				}
								
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/za101g2/back/missingReport/manageReportIndex.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************開始修改回報狀態************************************/
				missingReplySvc.changeIsread(replyId, "d");
				
				/**更改回報人數(bountyFor)**/				
				MissingRecordService missingRecordSvc = new MissingRecordService();
				Integer recordId = missingReplyVO.getMissingRecordVO().getId();
				Integer bountyFor = missingReplyVO.getMissingRecordVO().getBountyFor();
				missingRecordSvc.changeBountyFor(recordId, bountyFor - 1);
				
				/***************************修改完成,準備轉交(Send the Success view)*************/
				String url = "/za101g2/back/missingReport/manageReportIndex.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/				
			} catch (Exception e) {
				errorMsgs.put("Exception", "無法完成封鎖會員:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/back/missingReport/manageReportIndex.jsp");
				failureView.forward(req, res);
			}			
		}
		
		if ("getMember_MissingReport".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************接收請求參數****************************************/
				Integer memId = new Integer(req.getParameter("memId"));
				
				/***************************開始查詢資料*****************************************/
				MissingReportService missingReportSvc = new MissingReportService();
				Set<MissingReportVO> missingReportVO = missingReportSvc.findByMemId(memId);
				/**如會員沒有寵物帳號送出提醒**/
				if (missingReportVO.isEmpty()) {
					errorMsgs.put("emptyReport", "您目前沒有檢舉紀錄");
				}
				
				/***************************查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("missingReportVO", missingReportVO); // 資料庫取出的missingRecordVO物件,存入req
				String url = "/za101g2/front/missingReport/listMemberMissingReport.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneMissingRecord.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception", "無法取得會員資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/missingRecord/listAllMissingRecord.jsp");
				failureView.forward(req, res);
			}			
		}
		
		
		if ("getReply_For_Report".equals(action)) {
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************接收請求參數****************************************/
				Integer memId = new Integer(req.getParameter("memId"));
				Integer replyId = new Integer(req.getParameter("replyId"));
				
				/***************************開始查詢資料*****************************************/
				/**回報資料**/
				MissingReplyService missingReplySvc = new MissingReplyService();
				MissingReplyVO missingReplyVO = missingReplySvc.getOneMissingReply(replyId);

				/**查詢是否檢舉過，避免重複檢舉**/
				MissingReportService missingReportSvc = new MissingReportService();
				MissingReportVO missingreportVO = missingReportSvc.getOneMissingReport(memId, replyId);
				if (missingreportVO != null) {
					errorMsgs.put("duplicateReport", "您已經檢舉過此筆回報，管理員將盡快為您處理");
				}
				
				/***************************查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("missingReplyVO", missingReplyVO);
				req.setAttribute("memId", memId);
				String url = "/za101g2/front/missingReport/addMissingReport.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.put("Exception", "無法取得回報資料" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/missingRecord/listAllMissingRecord.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("insert".equals(action)) {
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************接收請求參數****************************************/
				Integer memId = new Integer(req.getParameter("memId"));
				Integer replyId = new Integer(req.getParameter("replyId"));

				/**查詢是否檢舉過，避免重複檢舉**/
				MissingReportService missingReportSvc = new MissingReportService();
				MissingReportVO missingreportVO = missingReportSvc.getOneMissingReport(memId, replyId);
				if (missingreportVO != null) {
					errorMsgs.put("duplicateReport", "您已經檢舉過此筆回報，管理員將盡快為您處理");
				}
				
				String comments = req.getParameter("comments").trim();
				if (comments == null || comments.trim().length() == 0) {
					errorMsgs.put("comments", "檢舉內容請勿空白");
				}
				
				java.util.Date now = new java.util.Date();
				java.sql.Date reportDate = new java.sql.Date(now.getTime()); 
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					/**回報資料**/
					MissingReplyService missingReplySvc = new MissingReplyService();
					MissingReplyVO missingReplyVO = missingReplySvc.getOneMissingReply(replyId);
					req.setAttribute("missingReplyVO", missingReplyVO);
					req.setAttribute("memId", memId);
					
					RequestDispatcher failureView = req.getRequestDispatcher("/za101g2/front/missingReport/addMissingReport.jsp");
					failureView.forward(req, res);
					return;
				}	
				
				/*****************************開始新增檢舉資料*********************************/
				missingReportSvc.addMissingReport(memId, replyId, comments, reportDate);
				MissingReplyService missingReplySvc = new MissingReplyService();
				missingReplySvc.changeIsread(replyId, "r");
				
				
				/***************************新增完成,準備轉交(Send the Success view)***********/
				/**會員所有檢舉**/
				Set<MissingReportVO> missingReportVO = missingReportSvc.findByMemId(memId);
				req.setAttribute("missingReportVO", missingReportVO); // 資料庫取出的missingRecordVO物件,存入req
				
				String url = "/za101g2/front/missingReport/listMemberMissingReport.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.put("Exception", "檢舉失敗" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/missingRecord/listAllMissingRecord.jsp");
				failureView.forward(req, res);
			}
		}	
	}
}