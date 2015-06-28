package za101g2.missingrecord.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;

import za101g2.missingrecord.model.*;
import za101g2.missingreply.model.*;
import za101g2.missingreport.model.*;
import za101g2.pet.model.*;

@MultipartConfig(fileSizeThreshold=1024*1024, maxFileSize=5*1024*1024, maxRequestSize=5*5*1024*1024)
public class MissingRecordServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("getReplyNumber".equals(action)) {
			
			/***************************接收請求參數****************************************/
			Integer recordId = new Integer(req.getParameter("recordId"));
			
			/***************************開始查詢資料*****************************************/
			MissingReplyService missingReplySvc = new MissingReplyService();
			Set<MissingReplyVO> missingReplyVO = missingReplySvc.findByRecordId(recordId);
			Integer replyNumber = missingReplyVO.size();
			
			/*****************************查詢完成,準備回傳***********************************/
			res.setContentType("text/plain");
			PrintWriter out = res.getWriter();
			out.write(replyNumber.toString());
			out.flush();
			out.close();
		}
		
		if ("getOne_Pet_Id".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************接收請求參數****************************************/
				Integer petId = new Integer(req.getParameter("petId"));
				
				/***************************開始查詢資料*****************************************/				
				MissingRecordService missingRecordSvc = new MissingRecordService();
				MissingRecordVO missingRecordVO = missingRecordSvc.findByPetId(petId);
				
				/***************************查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("missingRecordVO", missingRecordVO); // 資料庫取出的missingRecordVO物件,存入req
				String url = "/za101g2/front/missingRecord/listOneMissingRecord.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneMissingRecord.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception", "無法取得協尋資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/back/missingRecord/manageRecordIndex.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("manageBounty_Information".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************接收請求參數****************************************/
				Integer recordId = new Integer(req.getParameter("recordId"));
				
				/***************************開始查詢資料*****************************************/
				/**查詢要領取賞金的協尋紀錄**/
				MissingRecordService missingRecordSvc = new MissingRecordService();
				MissingRecordVO missingRecordVO = missingRecordSvc.getOneMissingRecord(recordId);
				
				/**查詢找到寵物的回報紀錄**/
				MissingReplyService missingReplySvc = new MissingReplyService();
				Set<MissingReplyVO> set = missingReplySvc.findByRecordId(recordId);
				MissingReplyVO missingReplyVO = new MissingReplyVO();
				for (MissingReplyVO replyVO : set) {
					if (replyVO.getIsRead().equals("y") || replyVO.getIsRead().equals("w")) {
						missingReplyVO = replyVO;
					}
				}
				
				/***************************查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("missingRecordVO", missingRecordVO); // 資料庫取出的missingRecordVO物件,存入req
				req.setAttribute("missingReplyVO", missingReplyVO); // 資料庫取出的missingReplyVO物件,存入req
				String url = "/za101g2/back/missingRecord/manageBounty.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception", "無法取得協尋賞金資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/back/missingRecord/manageRecordIndex.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("manageAll_Status".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************接收請求參數****************************************/
				String status = req.getParameter("status");
				String bounty = req.getParameter("bounty");
				
				/***************************開始查詢資料*****************************************/
				MissingRecordService missingRecordSvc = new MissingRecordService();
				List<MissingRecordVO> allMissingRecordVO = new ArrayList<MissingRecordVO>();
				if (bounty.equals("allBounty")) {
					allMissingRecordVO = missingRecordSvc.getAll();
				} else if (bounty.equals("bounty")) {
					allMissingRecordVO = missingRecordSvc.getAllBounty();
				} else if (bounty.equals("noBounty")) {
					allMissingRecordVO = missingRecordSvc.getAllNoBounty();
				}				
				
				List<MissingRecordVO> missingRecordVO = new ArrayList<MissingRecordVO>();
				for (MissingRecordVO record : allMissingRecordVO) {
					if (record.getStatus().equals(status)) {
						missingRecordVO.add(record);
					}
				}
				
				if (missingRecordVO.isEmpty()) {
					errorMsgs.put("emptyResult", "無相關搜尋結果");
				}
				
				/***************************查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("missingRecordVO", missingRecordVO); // 資料庫取出的missingRecordVO物件,存入req
				String url = "/za101g2/back/missingRecord/manageRecordSearch.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception", "無法取得協尋資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/back/missingRecord/manageRecordIndex.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getAll_Search".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************接收請求參數****************************************/
				String city = req.getParameter("city");
				String bounty = req.getParameter("bounty");

				/***************************開始查詢資料*****************************************/
				MissingRecordService missingRecordSvc = new MissingRecordService();
				List<MissingRecordVO> missingRecordVO = new ArrayList<MissingRecordVO>();
				
				if (city.equals("allCity")) {
					if (bounty.equals("allBounty")) {
						missingRecordVO = missingRecordSvc.getAll();
					} else if (bounty.equals("bounty")) {
						missingRecordVO = missingRecordSvc.findByCityBounty("");
					} else if (bounty.equals("noBounty")) {
						missingRecordVO = missingRecordSvc.findByCityNoBounty("");
					}
				} else {
					if (bounty.equals("allBounty")) {
						missingRecordVO = missingRecordSvc.findByCity(city);
					} else if (bounty.equals("bounty")) {
						missingRecordVO = missingRecordSvc.findByCityBounty(city);
					} else if (bounty.equals("noBounty")) {
						missingRecordVO = missingRecordSvc.findByCityNoBounty(city);
					}
				}
				
				if (missingRecordVO.isEmpty()) {
					errorMsgs.put("emptyResult", "無相關搜尋結果");
				}
				
				/***************************查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("missingRecordVO", missingRecordVO); // 資料庫取出的missingRecordVO物件,存入req
				String url = "/za101g2/front/missingRecord/listAllSearch.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception", "無法取得協尋資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/missingRecord/listAllMissingRecord.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("cancelMissingRecord".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************接收請求參數及錯誤處理*******************************/
				Integer recordId = new Integer(req.getParameter("recordId"));
				Integer memId = new Integer(req.getParameter("memId"));
				
				/**查詢協尋是否有回報紀錄**/
				MissingReplyService missingReplySvc = new MissingReplyService();
				Set<MissingReplyVO> missingReplyVO = missingReplySvc.findByRecordId(recordId);
				if (!missingReplyVO.isEmpty()) {
					for (MissingReplyVO replyVO : missingReplyVO) {
						if (!replyVO.getIsRead().equals("d")) {
							errorMsgs.put("haveReply", "協尋有回報紀錄，無法取消");
						}					
					}					
				}
				
				/**有錯誤重新轉回原本頁面**/
				if (!errorMsgs.isEmpty()) {
					/**用會員id找寵物id**/
					PetService petSvc = new PetService();
					List<PetVO> PetVO = petSvc.findIdByMemId(memId);
					/**用寵物id找會員協尋紀錄**/
					MissingRecordService missingRecordSvc = new MissingRecordService();
					List<MissingRecordVO> missingRecordVO = new ArrayList<MissingRecordVO>();				
					for (PetVO petIds : PetVO) {					
						MissingRecordVO amissingRecordVO = missingRecordSvc.findByPetId(petIds.getId());
						if (amissingRecordVO!=null) {
							missingRecordVO.add(amissingRecordVO);	
						}				
					}
					req.setAttribute("missingRecordVO", missingRecordVO); // 資料庫取出的missingRecordVO物件,存入req
					req.setAttribute("memId", memId); // 資料庫取出的memId(會員帳號)物件,存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/za101g2/front/missingRecord/listMemberMissingRecord.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************開始取消協尋***************************************/
				/**修改協尋狀態**/
				MissingRecordService missingRecordSvc = new MissingRecordService();
				missingRecordSvc.changeStatus(recordId, "下架");
						
				/**退還賞金**/
				MissingRecordVO mRecordVO = missingRecordSvc.getOneMissingRecord(recordId);
				Double bounty = new Double(mRecordVO.getBounty());
				if (bounty>0) {
					Integer refundBounty = (int)Math.round(bounty*0.9);
					errorMsgs.put("refundBounty", "賞金" + refundBounty + "元已退還至您的帳戶"); 
				} else {
					errorMsgs.put("refundBounty", "您的協尋紀錄已成功下架");
				}
				
				/**修改走失寵物狀態**/
				PetService petSvc = new PetService();
				petSvc.changeStatus(mRecordVO.getPetVO().getId(), "0");
								
				/***************************查詢完成,準備轉交(Send the Success view)*************/
				/**用會員id找寵物id**/
				List<PetVO> PetVO = petSvc.findIdByMemId(memId);
				/**用寵物id找會員協尋紀錄**/
				List<MissingRecordVO> missingRecordVO = new ArrayList<MissingRecordVO>();				
				for (PetVO petIds : PetVO) {					
					MissingRecordVO amissingRecordVO = missingRecordSvc.findByPetId(petIds.getId());
					if (amissingRecordVO!=null) {
						missingRecordVO.add(amissingRecordVO);	
					}				
				}
				req.setAttribute("missingRecordVO", missingRecordVO); // 資料庫取出的missingRecordVO物件,存入req
				req.setAttribute("memId", memId); // 資料庫取出的memId(會員帳號)物件,存入req
				String url = "/za101g2/front/missingRecord/listMemberMissingRecord.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.put("Exception", "取消失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/missingRecord/listAllMissingRecord.jsp");
				failureView.forward(req, res);
			}		
		}
		
		if ("rejectReply".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************接收請求參數****************************************/
				Integer replyId = new Integer(req.getParameter("replyId"));
				Integer memId = new Integer(req.getParameter("memId"));
				
				/******************開始回應沒找到寵物(修改MissingRecord的協尋狀態、賞金流向)************/
				MissingReplyService missingReplySvc = new MissingReplyService();
				MissingReplyVO missingReplyVO = missingReplySvc.getOneMissingReply(replyId);			

				/**確認回報狀態正確**/
				if (missingReplyVO.getIsRead().equals("r")) {
					errorMsgs.put("reported", "此回報已被您檢舉，無法確認沒有找到寵物");
				} else if (missingReplyVO.getIsRead().equals("y") || missingReplyVO.getIsRead().equals("d")) {
					errorMsgs.put("errorConfirm", "此確認發生錯誤");
				}
				
				if (!errorMsgs.isEmpty()) {
					/**用會員id找寵物id**/
					PetService petSvc = new PetService();
					MissingRecordService missingRecordSvc = new MissingRecordService();
					List<PetVO> PetVO = petSvc.findIdByMemId(memId);
					/**用寵物id找會員協尋紀錄**/			
					List<MissingRecordVO> missingRecordVO = new ArrayList<MissingRecordVO>();				
					for (PetVO petId : PetVO) {					
						MissingRecordVO amissingRecordVO = missingRecordSvc.findByPetId(petId.getId());
						if (amissingRecordVO!=null) {
							missingRecordVO.add(amissingRecordVO);	
						}				
					}
					
					req.setAttribute("missingRecordVO", missingRecordVO); // 資料庫取出的missingRecordVO物件,存入req
					req.setAttribute("memId", memId); // 資料庫取出的memId(會員帳號)物件,存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/za101g2/front/missingRecord/listMemberMissingRecord.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************開始修改資料*****************************************/
				/**刪除沒找到回報**/
				missingReplySvc.deleteMissingReply(replyId);
				
				/**更改回報人數(bountyFor)**/				
				MissingRecordService missingRecordSvc = new MissingRecordService();
				Integer recordId = missingReplyVO.getMissingRecordVO().getId();
				Integer bountyFor = missingReplyVO.getMissingRecordVO().getBountyFor();
				missingRecordSvc.changeBountyFor(recordId, bountyFor - 1);
				
				/***************************修改完成,準備轉交(Send the Success view)*************/
				/**用會員id找寵物id**/
				PetService petSvc = new PetService();
				petSvc = new PetService();
				List<PetVO> PetVO = petSvc.findIdByMemId(memId);
				/**用寵物id找會員協尋紀錄**/
				List<MissingRecordVO> missingRecordVO = new ArrayList<MissingRecordVO>();				
				for (PetVO petId : PetVO) {					
					MissingRecordVO amissingRecordVO = missingRecordSvc.findByPetId(petId.getId());
					if (amissingRecordVO!=null) {
						missingRecordVO.add(amissingRecordVO);	
					}				
				}
				
				errorMsgs.put("rejectReply", "沒有找到的回報已移除");
				req.setAttribute("missingRecordVO", missingRecordVO); // 資料庫取出的missingRecordVO物件,存入req
				req.setAttribute("memId", memId); // 資料庫取出的memId(會員帳號)物件,存入req
				String url = "/za101g2/front/missingRecord/listMemberMissingRecord.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception", "確認失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/missingRecord/listAllMissingRecord.jsp");
				failureView.forward(req, res);
			}	
		}
		
		if ("replyConfirm".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************接收請求參數****************************************/
				Integer replyId = new Integer(req.getParameter("replyId"));
				Integer memId = new Integer(req.getParameter("memId"));
				
				/******************開始確認找到寵物(修改MissingRecord的協尋狀態、賞金流向)************/
				MissingReplyService missingReplySvc = new MissingReplyService();
				MissingReplyVO missingReplyVO = missingReplySvc.getOneMissingReply(replyId);			
				
				Integer id = new Integer(missingReplyVO.getMissingRecordVO().getId());
				
				/**確認回報狀態正確**/
				if (missingReplyVO.getIsRead().equals("r")) {
					errorMsgs.put("reported", "此回報已被您檢舉，無法確認找到寵物");
				} else if (missingReplyVO.getIsRead().equals("y") || missingReplyVO.getIsRead().equals("d")) {
					errorMsgs.put("errorConfirm", "此確認發生錯誤");
				}
				
				if (!errorMsgs.isEmpty()) {
					/**用會員id找寵物id**/
					PetService petSvc = new PetService();
					MissingRecordService missingRecordSvc = new MissingRecordService();
					List<PetVO> PetVO = petSvc.findIdByMemId(memId);
					/**用寵物id找會員協尋紀錄**/			
					List<MissingRecordVO> missingRecordVO = new ArrayList<MissingRecordVO>();				
					for (PetVO petId : PetVO) {					
						MissingRecordVO amissingRecordVO = missingRecordSvc.findByPetId(petId.getId());
						if (amissingRecordVO!=null) {
							missingRecordVO.add(amissingRecordVO);	
						}				
					}
					
					req.setAttribute("missingRecordVO", missingRecordVO); // 資料庫取出的missingRecordVO物件,存入req
					req.setAttribute("memId", memId); // 資料庫取出的memId(會員帳號)物件,存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/za101g2/front/missingRecord/listMemberMissingRecord.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************開始修改資料*****************************************/
				/**修改協尋狀態**/
				MissingRecordService missingRecordSvc = new MissingRecordService();
				missingRecordSvc.changeStatus(id, "已找到");
				
				/**修改回報**/
				missingReplySvc.changeIsread(replyId, "y");
				
				/**修改走失寵物狀態**/
				PetService petSvc = new PetService();
				petSvc.changeStatus(missingReplyVO.getMissingRecordVO().getPetVO().getId(), "0");
				
				/***************************修改完成,準備轉交(Send the Success view)*************/
				/**用會員id找寵物id**/
				petSvc = new PetService();
				List<PetVO> PetVO = petSvc.findIdByMemId(memId);
				/**用寵物id找會員協尋紀錄**/			
				List<MissingRecordVO> missingRecordVO = new ArrayList<MissingRecordVO>();				
				for (PetVO petId : PetVO) {					
					MissingRecordVO amissingRecordVO = missingRecordSvc.findByPetId(petId.getId());
					if (amissingRecordVO!=null) {
						missingRecordVO.add(amissingRecordVO);	
					}				
				}
				
				req.setAttribute("missingRecordVO", missingRecordVO); // 資料庫取出的missingRecordVO物件,存入req
				req.setAttribute("memId", memId); // 資料庫取出的memId(會員帳號)物件,存入req
				String url = "/za101g2/front/missingRecord/listMemberMissingRecord.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/			
			} catch (Exception e) {
				errorMsgs.put("Exception", "確認失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/missingRecord/listAllMissingRecord.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("listMemberMissingReplies".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************接收請求參數****************************************/
				Integer memId = new Integer(req.getParameter("memId"));
				Integer recordId = new Integer(req.getParameter("recordId"));
				
				/***************************開始查詢資料*****************************************/
				/**會員協尋紀錄**/
				PetService petSvc = new PetService();
				List<PetVO> petVO = petSvc.findIdByMemId(memId);
				MissingRecordService missingRecordSvc = new MissingRecordService();
				List<MissingRecordVO> missingRecordVO = new ArrayList<MissingRecordVO>();				
				for (PetVO petId : petVO) {					
					MissingRecordVO amissingRecordVO = missingRecordSvc.findByPetId(petId.getId());
					if (amissingRecordVO!=null) {
						missingRecordVO.add(amissingRecordVO);	
					}				
				}
				
				/**回報紀錄**/
				MissingReplyService missingReplySvc = new MissingReplyService();
				Set<MissingReplyVO> missingReplyVO = missingReplySvc.findByRecordId(recordId);
				MissingReportService missingReportSvc = new MissingReportService();
				Set<MissingReplyVO> set = new HashSet<MissingReplyVO>();
				for (MissingReplyVO replyVO : missingReplyVO) {
					MissingReportVO missingReportVO = missingReportSvc.findByReplyId(replyVO.getId());
					if (missingReportVO==null || replyVO.getIsRead().equals("r")) {
						set.add(replyVO);
					}
				}
				
				if (set.isEmpty()) {
					errorMsgs.put("emptyReply", "此筆協尋沒有回報紀錄");
				}
				
				/***************************查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("missingRecordVO", missingRecordVO); // 資料庫取出的MissingRecordVO(會員協尋紀錄)物件,存入req
				req.setAttribute("listMemberMissingReplies", set); // 資料庫取出的MissingReplyVO(回報紀錄)物件,存入req
				req.setAttribute("memId", memId); // 資料庫取出的memId(會員帳號)物件,存入req
				String url = "/za101g2/front/missingRecord/listMemberMissingRecord.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception", "無法取得回報資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/missingRecord/listAllMissingRecord.jsp");
				failureView.forward(req, res);
			}
		}
		
		/**for_addMissingRecord.jsp**/
		if ("getMember_Pet_Id".equals(action)) {
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************接收請求參數****************************************/
				Integer id = new Integer(req.getParameter("id"));
				
				/***************************開始查詢資料*****************************************/
				PetService petSvc = new PetService();
				List<PetVO> petVO = petSvc.findIdByMemId(id);
				/**如會員沒有寵物帳號送出提醒**/
				if (petVO.isEmpty()) {
					errorMsgs.put("emptyPet", "您目前沒有寵物帳號，請至會員專區新增寵物帳號");
				}
	
				/***************************查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("petVO", petVO); // 資料庫取出的petVO物件,存入req
				String url = "/za101g2/front/missingRecord/addMissingRecord.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception", "無法取得寵物資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/missingRecord/listAllMissingRecord.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getMember_MissingRecord".equals(action)) {
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************接收請求參數****************************************/
				Integer memId = new Integer(req.getParameter("memId"));
				
				/***************************開始查詢資料*****************************************/
				/**用會員id找寵物id**/
				PetService petSvc = new PetService();
				List<PetVO> PetVO = petSvc.findIdByMemId(memId);
				/**用寵物id找會員協尋紀錄**/
				MissingRecordService missingRecordSvc = new MissingRecordService();
				List<MissingRecordVO> missingRecordVO = new ArrayList<MissingRecordVO>();				
				for (PetVO petIds : PetVO) {					
					MissingRecordVO amissingRecordVO = missingRecordSvc.findByPetId(petIds.getId());
					if (amissingRecordVO!=null) {
						missingRecordVO.add(amissingRecordVO);	
					}				
				}				
				/**如會員沒有寵物帳號送出提醒**/
				if (missingRecordVO.isEmpty()) {
					errorMsgs.put("emptyRecord", "您目前沒有刊登中的協尋紀錄");
				}
				
				/***************************查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("missingRecordVO", missingRecordVO); // 資料庫取出的missingRecordVO物件,存入req
				req.setAttribute("memId", memId); // 資料庫取出的memId(會員帳號)物件,存入req
				String url = "/za101g2/front/missingRecord/listMemberMissingRecord.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception", "無法取得會員協尋資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/missingRecord/listAllMissingRecord.jsp");
				failureView.forward(req, res);
			}			
		}
		
		
		if ("getOne_For_Display".equals(action)) {

			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************接收請求參數****************************************/
				Integer id = new Integer(req.getParameter("recordId"));
				
				/***************************開始查詢資料*****************************************/
				MissingRecordService missingRecordSvc = new MissingRecordService();
				MissingRecordVO missingRecordVO = missingRecordSvc.getOneMissingRecord(id);
				
				/***************************查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("missingRecordVO", missingRecordVO); // 資料庫取出的missingRecordVO物件,存入req
				String url = "/za101g2/front/missingRecord/listOneMissingRecord.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneMissingRecord.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception", "無法取得協尋資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/missingRecord/listAllMissingRecord.jsp");
				failureView.forward(req, res);		
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllMissingRecord.jsp 的請求
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************接收請求參數****************************************/
				Integer id = new Integer(req.getParameter("id"));
				
				
				/***************************開始查詢資料*****************************************/
				MissingRecordService missingRecordSvc = new MissingRecordService();
				MissingRecordVO missingRecordVO = missingRecordSvc.getOneMissingRecord(id);
				/**查詢走失縣市及走失地點**/
				String cityLocation = missingRecordVO.getLocation();
				String city = null;
				String location = null;
				if (cityLocation.startsWith("基") || cityLocation.startsWith("台") || cityLocation.startsWith("新北") || cityLocation.startsWith("桃") || cityLocation.startsWith("苗") || cityLocation.startsWith("高")) {
					city = cityLocation.substring(0, 3);
					location = cityLocation.substring(3);
				} else {
					city = cityLocation.substring(0, 4);
					location = cityLocation.substring(4);
				}
				
				/***************************查詢完成,準備轉交(Send the Success view)************/
				Integer memId = new Integer(req.getParameter("memId"));
				req.setAttribute("memId", memId); // memId(會員帳號)物件,存入req
				req.setAttribute("city", city);
				req.setAttribute("location", location);
				String url = "/za101g2/front/missingRecord/update_missingRecord_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 update_missingRecord_input.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception", "無法取得協尋資料:" + e.getMessage());
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
				Integer petId = new Integer(req.getParameter("petId").trim());
				
				/**查詢寵物是否已經在協尋**/
				MissingRecordService amissingRecordSvc = new MissingRecordService();
				MissingRecordVO amissingRecordVO = amissingRecordSvc.findByPetId(petId);
				if (amissingRecordVO != null) {
					switch(amissingRecordVO.getStatus()){
						case "協尋中":
							errorMsgs.put("petId", "這隻寵物正在協尋中");
							break;
						case "已找到":
							errorMsgs.put("petId", "這隻寵物已找到，等待發放賞金");
							break;
					}	
				}
				
				String city = req.getParameter("city");
				if (city.equals("emptyCity")) {
					errorMsgs.put("emptyCity", "您尚未選擇縣市");
				}			
				String partOfLocation = req.getParameter("location").trim();
				if (partOfLocation == null || partOfLocation.trim().length() == 0) {
					errorMsgs.put("location", "走失地點請勿空白");
				}
				String location = city + partOfLocation;
				
				java.sql.Date missingDate = null;
				try {
					missingDate = java.sql.Date.valueOf(req.getParameter("missingDate").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.put("missingDate", "請輸入日期");
				}
				
				/**判斷是否有賞金，賞金與信用卡號驗證**/
				Integer bounty = null;
				String creditCard = req.getParameter("creditCard1") + 
						req.getParameter("creditCard2") +
						req.getParameter("creditCard3") +
						req.getParameter("creditCard4");	
				String checkBounty = req.getParameter("checkBounty");
				if (checkBounty == null) {
					errorMsgs.put("checkBounty", "請選擇是否有賞金");
				} else if (checkBounty.equals("emptyBounty")) {
					bounty = 0;
				} else if (checkBounty.equals("fullBounty")) {
					try {
						bounty = new Integer(req.getParameter("bounty").trim());
					} catch (NumberFormatException e) {
						errorMsgs.put("bounty", "賞金請填數字");
					}

					if (errorMsgs.get("bounty") == null) {
						if (bounty > 0) {
							if (!creditCard.matches("\\d{16}")) {
								errorMsgs.put("creditCard", "信用卡格式範例(1234-1234-1234-1234)");
							}
						} else {
							errorMsgs.put("bounty", "賞金請勿填零或負數");
						}
					}
					
				}
				
				String comments = req.getParameter("comments").trim();
				if (comments == null || comments.trim().length() == 0) {
					errorMsgs.put("comments", "說明請勿空白");
				}
								
				String status = "協尋中";
				
				Integer bountyFor = 0;
				
				byte[] missingPhoto = null;
				Part part = req.getPart("missingPhoto");
				InputStream ins = part.getInputStream();
				missingPhoto = new byte[ins.available()];
				ins.read(missingPhoto);
				if (ins!=null) {
					try {	
						ins.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}		
							
				String latlng = req.getParameter("latlng").trim();
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					Integer id = new Integer(req.getParameter("id"));
					PetService petSvc = new PetService();
					List<PetVO> petVO = petSvc.findIdByMemId(id);
					req.setAttribute("petVO", petVO);

					RequestDispatcher failureView = req.getRequestDispatcher("/za101g2/front/missingRecord/addMissingRecord.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************開始新增資料***************************************/
				MissingRecordService missingRecordSvc = new MissingRecordService();
				missingRecordSvc.addMissingRecord(location, missingDate, bounty, comments, petId, status, bountyFor, missingPhoto, latlng);
				
				/**修改走失寵物狀態**/
				PetService petSvc = new PetService();
				petSvc.changeStatus(petId, "1");
				
				/***************************新增完成,準備轉交(Send the Success view)***********/
				String url = "/za101g2/front/missingRecord/listAllMissingRecord.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllMissingRecord.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.put("Exception", "新增協尋失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/missingRecord/listAllMissingRecord.jsp");
				failureView.forward(req, res);		
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_missingRecord_input.jsp的請求
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer id = new Integer(req.getParameter("id"));
				
				Integer petId = new Integer(req.getParameter("petId").trim());

				String city = req.getParameter("city");
				if (city.equals("emptyCity")) {
					errorMsgs.put("emptyCity", "您尚未選擇縣市");
				}			
				String partOfLocation = req.getParameter("location").trim();
				if (partOfLocation == null || partOfLocation.trim().length() == 0) {
					errorMsgs.put("location", "走失地點請勿空白");
				}
				String location = city + partOfLocation;
				
				java.sql.Date missingDate = null;
				try {
					missingDate = java.sql.Date.valueOf(req.getParameter("missingDate").trim());
				} catch (IllegalArgumentException e) {
					missingDate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.put("missingDate", "請輸入日期");
				}
				
				Integer bounty = null;
				try {
					bounty = new Integer(req.getParameter("bounty").trim());
				} catch (NumberFormatException e) {
					bounty = 0;
					errorMsgs.put("bounty", "賞金請填數字");
				}
	
				String comments = req.getParameter("comments").trim();
				if (comments == null || comments.trim().length() == 0) {
					errorMsgs.put("comments", "說明請勿空白");
				}
				
				MissingRecordService missingRecordSvc = new MissingRecordService();
				MissingRecordVO recordVO = missingRecordSvc.getOneMissingRecord(id);
				
				String status = recordVO.getStatus();
				
				Integer bountyFor = recordVO.getBountyFor();
							
				byte[] missingPhoto = null;
				Part part = req.getPart("missingPhoto");			
				InputStream ins = part.getInputStream();
				if (ins.available() == 0) {
					MissingRecordVO memberMissingRecord = missingRecordSvc.findByPetId(petId);
					missingPhoto = memberMissingRecord.getMissingPhoto();
				} else {
					missingPhoto = new byte[ins.available()];
					ins.read(missingPhoto);
				}
				if (ins!=null) {
					try {
						ins.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}	
				
				String latlng = req.getParameter("latlng").trim();
				
				/**用會員id找寵物id**/
				Integer memId = new Integer(req.getParameter("memId"));
				req.setAttribute("memId", memId); // memId(會員帳號)物件,存入req
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("city", city);
					req.setAttribute("location", partOfLocation);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/za101g2/front/missingRecord/update_missingRecord_input.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************開始修改資料*****************************************/							
				missingRecordSvc.updateMissingRecord(id, location, missingDate, bounty, comments, petId, status, bountyFor, missingPhoto, latlng);
				
				/***************************修改完成,準備轉交(Send the Success view)*************/
				/**用會員id找寵物id**/
				PetService petSvc = new PetService();
				List<PetVO> petVO = petSvc.findIdByMemId(memId);
				/**用寵物id找會員協尋紀錄**/
				List<MissingRecordVO> missingRecordVO = new ArrayList<MissingRecordVO>();				
				for (PetVO petIds : petVO) {					
					MissingRecordVO amissingRecordVO = missingRecordSvc.findByPetId(petIds.getId());
					if (amissingRecordVO!=null) {
						missingRecordVO.add(amissingRecordVO);	
					}				
				}
				
				req.setAttribute("missingRecordVO", missingRecordVO); // 資料庫取出的missingRecordVO物件,存入req
//				req.setAttribute("memId", memId); // memId(會員帳號)物件,存入req
				String url = "/za101g2/front/missingRecord/listMemberMissingRecord.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception", "更新資料失敗:" + e.getMessage() + e.getCause());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/missingRecord/listAllMissingRecord.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
