package za101g2.missingreply.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.json.JSONException;
import org.json.JSONObject;

import za101g2.member.model.*;
import za101g2.missingrecord.model.*;
import za101g2.missingreply.model.*;

public class MissingReplyServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("insertReply".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			
			/***********************接收請求參數 - 輸入格式的錯誤處理*************************/
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			JSONObject json = new JSONObject();
			
			Integer memId = null;
			try {
				memId = new Integer(req.getParameter("memId").trim());
			} catch (NumberFormatException e) {
				errorMsgs.put("memId", "您尚未登入，無法使用回報功能");
				try {
					json.put("errorMsgs", errorMsgs);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				out.print(json.toString());
				out.flush();
				out.close();
				return;
			}
			Integer recordId = new Integer(req.getParameter("recordId"));
			
			/**查詢會員是否通過信箱或手機驗證**/
			MemberService memberSvc = new MemberService();
			MemberVO memberVO = memberSvc.findOneByPrimaryKey(memId);
			switch (memberVO.getStatus()) {
				case "0":
					errorMsgs.put("email", "您尚未通過信箱驗證，無法使用回報功能");
					break;
				case "1":
					errorMsgs.put("phone", "您尚未通過手機驗證，無法使用回報功能");
					break;
			}
			
			if (!errorMsgs.isEmpty()) {
				try {
					json.put("errorMsgs", errorMsgs);					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				out.print(json.toString());
				out.flush();
				out.close();
				return;
			}
						
			/**查詢回報者是否被封鎖**/
			MissingReplyService missingReplySvc = new MissingReplyService();
			Set<MissingReplyVO> missingReplyVO = missingReplySvc.findByMemId(memId);
			for (MissingReplyVO reply : missingReplyVO) {
				if (reply.getIsRead().equals("d")) {
					errorMsgs.put("deny", "您已被封鎖，無法使用回報功能");
				}
			}
			
			/**查詢不能回報自己的協尋**/
			MissingRecordService missingRecordSvc = new MissingRecordService();
			MissingRecordVO missingRecordVO = missingRecordSvc.getOneMissingRecord(recordId);
			Integer recordMemId = missingRecordVO.getPetVO().getMemberVO().getId();
			if (memId.equals(recordMemId)) {
				errorMsgs.put("illegalReply", "您無法回報自己的協尋紀錄");
			}
			
			/**查詢是否重複回報**/
			MissingReplyVO replyVO = missingReplySvc.findByRecordIdMemId(recordId, memId);
			if (replyVO != null) {
				errorMsgs.put("duplicateReply", "您已經回報過此筆協尋");
			}
			
			String comments = req.getParameter("comments").trim();
			if (comments == null || comments.trim().length() == 0) {
				errorMsgs.put("comments", "回報內容請勿空白");
			}
			
			String isRead = "n";
			
			java.util.Date now = new java.util.Date();
			java.sql.Date reportDate = new java.sql.Date(now.getTime());
			
			if (!errorMsgs.isEmpty()) {
				try {
					json.put("errorMsgs", errorMsgs);					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				out.print(json.toString());
				out.flush();
				out.close();
			} else {
				/****************************開始新增協尋回報資料*******************************/
				missingReplySvc.addMissingReply(comments, recordId, memId, isRead, reportDate);
				
				/**更改回報人數(bountyFor)**/								
				Integer bountyFor = missingRecordVO.getBountyFor();
				missingRecordSvc.changeBountyFor(recordId, bountyFor + 1);
				
				try {
					json.put("success", "您已成功新增回報");					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				out.print(json.toString());
				out.flush();
				out.close();
			}
		}
		
		if ("payBounty".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************接收請求參數****************************************/
				Integer replyId = new Integer(req.getParameter("replyId"));
				MissingReplyService missingReplySvc = new MissingReplyService();
				MissingRecordService missingRecordSvc = new MissingRecordService();
				MissingReplyVO missingReplyVO = missingReplySvc.getOneMissingReply(replyId);
				MissingRecordVO missingRecordVO = missingRecordSvc.getOneMissingRecord(missingReplyVO.getMissingRecordVO().getId());
				/**確認回報狀態是等待發放賞金及協尋狀態是已找到**/
				if (!(missingReplyVO.getIsRead().equals("w") && missingRecordVO.getStatus().equals("已找到"))) {
					RequestDispatcher failureView = req.getRequestDispatcher("/za101g2/back/missingRecord/manageRecordIndex.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************開始修改回報狀態************************************/
				missingReplySvc.changeIsread(replyId, "b");
				missingRecordSvc.changeStatus(missingReplyVO.getMissingRecordVO().getId(), "已發放");
				
				/***************************修改完成,準備轉交(Send the Success view)*************/
				errorMsgs.put("bounty", "賞金已完成發放");
				String url = "/za101g2/back/missingRecord/manageRecordIndex.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception", "支付賞金失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/back/missingRecord/manageRecordIndex.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("bankInformationCheck".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***********************接收請求參數 - 輸入格式的錯誤處理*************************/
				Integer memId = new Integer(req.getParameter("memId"));
				Integer replyId = new Integer(req.getParameter("replyId"));
				
				String bank = req.getParameter("bank").trim();
				if (bank == null || bank.trim().length() == 0) {
					errorMsgs.put("bank", "銀行欄位請勿空白");
				} else if (!bank.matches("[\u4e00-\u9fa5]{2,}銀行$")) {
					errorMsgs.put("bank", "請填寫正確銀行名稱");
				}
				
				String account = req.getParameter("account").trim();
				if (account == null || account.trim().length() == 0) {
					errorMsgs.put("account", "銀行帳號欄位請勿空白");
				} else if (!account.matches("\\d{14,16}")) {
					errorMsgs.put("account", "請填寫正確銀行帳號");
				}
				
				String name = req.getParameter("name").trim();
				if (name == null || name.trim().length() == 0) {
					errorMsgs.put("name", "戶名欄位請勿空白");
				} else if (!name.matches("[\u4e00-\u9fa5]{2,}")) {
					errorMsgs.put("name", "請填寫正確戶名");
				}
				/**確認回報狀態正確**/
				MissingReplyService missingReplySvc = new MissingReplyService();
				if (!missingReplySvc.getOneMissingReply(replyId).getIsRead().equals("y")) {
//					errorMsgs.put("errorStatus", "請重新整理頁面");
					Set<MissingReplyVO> missingReplyVO = missingReplySvc.findByMemId(memId);
					req.setAttribute("memId", memId);
					req.setAttribute("missingReplyVO", missingReplyVO); // 資料庫取出的MissingReplyVO(回報紀錄)物件,存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/za101g2/front/missingReply/listMemberMissingReply.jsp");
					failureView.forward(req, res);
					return;
				}
				
				if (!errorMsgs.isEmpty()) {
					missingReplySvc = new MissingReplyService();
					Set<MissingReplyVO> missingReplyVO = missingReplySvc.findByMemId(memId);
					MissingReplyVO replyVO = missingReplySvc.getOneMissingReply(replyId);
					req.setAttribute("memId", memId);
					req.setAttribute("missingReplyVO", missingReplyVO); // 資料庫取出的MissingReplyVO(回報紀錄)物件,存入req
					req.setAttribute("replyVO", replyVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/za101g2/front/missingReply/listMemberMissingReply.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************開始修改回報狀態************************************/
				missingReplySvc.changeIsread(replyId, "w");
				
				/***************************修改完成,準備轉交(Send the Success view)*************/
				Set<MissingReplyVO> missingReplyVO = missingReplySvc.findByMemId(memId);
				req.setAttribute("memId", memId);
				req.setAttribute("missingReplyVO", missingReplyVO); // 資料庫取出的MissingReplyVO(回報紀錄)物件,存入req
				String url = "/za101g2/front/missingReply/listMemberMissingReply.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception", "無法確認帳號:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/missingRecord/listAllMissingRecord.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("bankInformation".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************接收請求參數****************************************/
				Integer memId = new Integer(req.getParameter("memId"));
				Integer replyId = new Integer(req.getParameter("replyId"));
				
				/***************************開始查詢資料*****************************************/
				MissingReplyService missingReplySvc = new MissingReplyService();
				MissingReplyVO replyVO = missingReplySvc.getOneMissingReply(replyId);
				
				Boolean missingReplyCheck = replyVO.getMemberVO().getId().equals(memId) && replyVO.getIsRead().equals("y");
				Boolean missingRecordCheck = replyVO.getMissingRecordVO().getBounty() > 0 && replyVO.getMissingRecordVO().getStatus().equals("已找到");
				if (missingReplyCheck && missingRecordCheck) {
					req.setAttribute("replyVO", replyVO);
				} else {
					errorMsgs.put("noBounty", "領取賞金失敗");
				}
				
				if (!errorMsgs.isEmpty()) {
					Set<MissingReplyVO> missingReplyVO = missingReplySvc.findByMemId(memId);
					req.setAttribute("memId", memId);
					req.setAttribute("missingReplyVO", missingReplyVO); // 資料庫取出的MissingReplyVO(回報紀錄)物件,存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/za101g2/front/missingReply/listMemberMissingReply.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************查詢完成,準備轉交(Send the Success view)*************/
				Set<MissingReplyVO> missingReplyVO = missingReplySvc.findByMemId(memId);
				req.setAttribute("memId", memId);
				req.setAttribute("missingReplyVO", missingReplyVO); // 資料庫取出的MissingReplyVO(回報紀錄)物件,存入req
				String url = "/za101g2/front/missingReply/listMemberMissingReply.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception", "無法領取賞金:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/missingRecord/listAllMissingRecord.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getMember_MissingReply".equals(action)) {		
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************接收請求參數****************************************/
				Integer memId = new Integer(req.getParameter("memId"));
				
				/***************************開始查詢資料*****************************************/
				MissingReplyService missingReplySvc = new MissingReplyService();
				Set<MissingReplyVO> missingReplyVO = missingReplySvc.findByMemId(memId);
				
				if (missingReplyVO.isEmpty()) {
					errorMsgs.put("emptyReply", "您沒有回報紀錄");
				}
				
				/***************************查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("memId", memId);
				req.setAttribute("missingReplyVO", missingReplyVO); // 資料庫取出的MissingReplyVO(回報紀錄)物件,存入req
				String url = "/za101g2/front/missingReply/listMemberMissingReply.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/				
			} catch (Exception e) {
				errorMsgs.put("Exception", "無法取得會員回報資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/missingRecord/listAllMissingRecord.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("insert".equals(action)) {			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***********************接收請求參數 - 輸入格式的錯誤處理*************************/
				Integer memId = null;
				try {
					memId = new Integer(req.getParameter("memId").trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("memId", "會員編號錯誤");
				}
				
				/**查詢回報者是否被封鎖**/
				MissingReplyService missingReplySvc = new MissingReplyService();
				Set<MissingReplyVO> missingReplyVO = missingReplySvc.findByMemId(memId);
				for (MissingReplyVO reply : missingReplyVO) {
					if (reply.getIsRead().equals("d")) {
						errorMsgs.put("deny", "您無法使用回報功能");
						RequestDispatcher failureView = req.getRequestDispatcher("/za101g2/front/missingRecord/listAllMissingRecord.jsp");
						failureView.forward(req, res);
						return;
					}
				}
				
				Integer recordId = new Integer(req.getParameter("recordId"));
				
				/**查詢不能回報自己的協尋**/
				MissingRecordService missingRecordSvc = new MissingRecordService();
				MissingRecordVO missingRecordVO = missingRecordSvc.getOneMissingRecord(recordId);
				Integer recordMemId = missingRecordVO.getPetVO().getMemberVO().getId();
				if (memId.equals(recordMemId)) {
					errorMsgs.put("illegalReply", "您無法回報自己的協尋紀錄");
					RequestDispatcher failureView = req.getRequestDispatcher("/za101g2/front/missingRecord/listAllMissingRecord.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/**查詢是否重複回報**/
				MissingReplyVO replyVO = missingReplySvc.findByRecordIdMemId(recordId, memId);
				if (replyVO != null) {
					errorMsgs.put("duplicateReply", "您已經回報過此筆協尋");
					RequestDispatcher failureView = req.getRequestDispatcher("/za101g2/front/missingRecord/listAllMissingRecord.jsp");
					failureView.forward(req, res);
					return;
				}
				
				String comments = req.getParameter("comments").trim();
				if (comments == null || comments.trim().length() == 0) {
					errorMsgs.put("comments", "回報內容請勿空白");
				}
				
				String isRead = "n";
				
				java.util.Date now = new java.util.Date();
				java.sql.Date reportDate = new java.sql.Date(now.getTime());
										
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					Integer aRecordId = new Integer(req.getParameter("aRecordId"));
					req.setAttribute("aRecordId", aRecordId);
					
					RequestDispatcher failureView = req.getRequestDispatcher("/za101g2/front/missingReply/addMissingReply.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/****************************開始新增協尋回報資料*******************************/
				missingReplySvc.addMissingReply(comments, recordId, memId, isRead, reportDate);
				
				Set<MissingReplyVO> newMissingReplyVO = missingReplySvc.findByMemId(memId);
				/**更改回報人數(bountyFor)**/								
				Integer bountyFor = missingRecordVO.getBountyFor();
				missingRecordSvc.changeBountyFor(recordId, bountyFor + 1);

				
				/***************************新增完成,準備轉交(Send the Success view)***********/
				req.setAttribute("missingReplyVO", newMissingReplyVO); // 資料庫取出的MissingReplyVO(回報紀錄)物件,存入req
				String url = "/za101g2/front/missingReply/listMemberMissingReply.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.put("Exception", "新增回報失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/missingReply/addMissingReply.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
