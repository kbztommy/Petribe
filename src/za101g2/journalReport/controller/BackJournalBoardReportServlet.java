package za101g2.journalReport.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import za101g2.journal.model.JournalService;
import za101g2.journal.model.JournalVO;
import za101g2.journalAssess.model.JournalAssessService;
import za101g2.journalAssess.model.JournalAssessVO;
import za101g2.journalBoard.model.JournalBoardService;
import za101g2.journalBoard.model.JournalBoardVO;
import za101g2.journalBoardReport.model.JournalBoardReportService;
import za101g2.journalBoardReport.model.JournalBoardReportVO;
import za101g2.journalReport.model.JournalReportService;
import za101g2.journalReport.model.JournalReportVO;
import za101g2.member.model.MemberService;
import za101g2.member.model.MemberVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class BackJournalBoardReportServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
        if ("listJournalBoardReports".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				JournalBoardReportService journalBoardReportSvc = new JournalBoardReportService();
				List<JournalBoardReportVO> journalBoardReportList = journalBoardReportSvc.getAll();

				req.setAttribute("journalBoardReportList", journalBoardReportList);
				String url = "/za101g2/back/journalBoardReport/journalBoardReport.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/back/homePage/home.jsp");
				failureView.forward(req, res);
			}
		}
        
        if ("delReport".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				
				String str = req.getParameter("journalMsgId");
				Integer journalMsgId = new Integer(str);			
				String str2 = req.getParameter("reportMemId");
				Integer reportMemId = new Integer(str2);
				
				JournalBoardReportService journalBoardReportSvc = new JournalBoardReportService();
				journalBoardReportSvc.deleteJournalBoardReport(journalMsgId, reportMemId);
				
				String url = "/backJournalBoardReport/backJournalBoardReport.do?action=listJournalBoardReports";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backJournalBoardReport/backJournalBoardReport.do?action=listJournalBoardReports");
				failureView.forward(req, res);
			}
		}
        
        if ("lockJournalBoard".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String url = "/backJournalBoardReport/backJournalBoardReport.do?action=listJournalBoardReports";
			try {
				String str = req.getParameter("journalMsgId");
				Integer journalMsgId = new Integer(str);			
				String str2 = req.getParameter("reportMemId");
				Integer reportMemId = new Integer(str2);
				String isDelete = req.getParameter("isDelete");
				
				switch( isDelete ){
					case "y":
						isDelete = "n";
				        break;
				    case "n":
				    	isDelete = "y";
				        break;
				    default:
				        break;
				 }
				
				JournalBoardService journalBoardSvc = new JournalBoardService();
				journalBoardSvc.updateIsDelete(journalMsgId,isDelete);
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}
        
	}
}
