package za101g2.journalReport.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import za101g2.journalBoardReport.model.JournalBoardReportService;
import za101g2.journalReport.model.JournalReportService;
import za101g2.member.model.MemberVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class JournalReportServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
        if ("report".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String hint ="";
			
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			Integer journalId = new Integer(req.getParameter("journalId"));
			String url = "/journal/journal.do?action=getOne_For_Display&id="+journalId;
			try {			
				
				String comments = req.getParameter("comments").trim();
				if(comments.length()==0){
					hint+="請輸入說明,";
				}
				
				JournalReportService journalReportSvc = new JournalReportService();
				journalReportSvc.addJournalReport(memberVO.getId(), journalId, comments);
				
				if(hint.length()==0){
					hint+="檢舉資訊已送出";
				}
				req.setAttribute("hint", hint);
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				hint+="無法重複檢舉";
				req.setAttribute("hint", hint);
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}
        
        if ("boardReport".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String hint ="";
			
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			Integer journalId = new Integer(req.getParameter("journalId"));
			Integer journalMsgId = new Integer(req.getParameter("journalMsgId"));
			String url = "/journal/journal.do?action=getOne_For_Display&id="+journalId;
			try {			
				
				String comments = req.getParameter("comments").trim();
				if(comments.length()==0){
					hint+="請輸入說明,";
				}

				JournalBoardReportService journalBoardReportSvc = new JournalBoardReportService();
				journalBoardReportSvc.addJournalBoardReport(journalMsgId, memberVO.getId(), comments);
				
				if(hint.length()==0){
					hint+="留言檢舉資訊已送出";
				}
				req.setAttribute("hint", hint);
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				hint+="無法重複留言檢舉";
				req.setAttribute("hint", hint);
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}
        
	}
}
