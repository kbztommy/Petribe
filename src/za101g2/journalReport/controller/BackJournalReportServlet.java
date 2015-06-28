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
import za101g2.journalReport.model.JournalReportService;
import za101g2.journalReport.model.JournalReportVO;
import za101g2.member.model.MemberService;
import za101g2.member.model.MemberVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class BackJournalReportServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
        if ("listJournalReports".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				JournalReportService journalReportSvc = new JournalReportService();
				List<JournalReportVO> journalReportList = journalReportSvc.getAll();
				
				req.setAttribute("journalReportList", journalReportList);
				String url = "/za101g2/back/journalReport/journalReport.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/back/homePage/home.jsp");
				failureView.forward(req, res);
			}
		}
        
		if ("journalDisplay".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String str = req.getParameter("journalId");
				Integer journalId = new Integer(str);
				JournalService journalSvc = new JournalService();
				JournalVO journalVO = journalSvc.getOneJournal(journalId);
				
				String str2 = req.getParameter("reportMemId");
				Integer memId = new Integer(str2);
				JournalReportService journalReportSvc = new JournalReportService();
				JournalReportVO journalReportVO = journalReportSvc.getOneJournalReport(memId, journalId);				
				
				req.setAttribute("journalVO", journalVO);
				req.setAttribute("journalReportVO", journalReportVO);
				String url = "/za101g2/back/journalReport/journalCheck.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backJournalReport/backJournalReport.do?action=listJournalReports");
				failureView.forward(req, res);
			}
		}
        
        if ("delReport".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				
				String str = req.getParameter("journalId");
				Integer journalId = new Integer(str);			
				String str2 = req.getParameter("reportMemId");
				Integer reportMemId = new Integer(str2);
				
				JournalReportService journalReportSvc = new JournalReportService();
				journalReportSvc.deleteJournalReport(reportMemId, journalId);
				
				String url = "/backJournalReport/backJournalReport.do?action=listJournalReports";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backJournalReport/backJournalReport.do?action=listJournalReports");
				failureView.forward(req, res);
			}
		}
        
        if ("lockJournal".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String url = "/backJournalReport/backJournalReport.do?action=journalDisplay";
			try {
				String str = req.getParameter("journalId");
				Integer journalId = new Integer(str);			
				String str2 = req.getParameter("reportMemId");
				Integer reportMemId = new Integer(str2);
				url += "&reportMemId="+reportMemId+"&journalId="+journalId;
				
				String str3 = req.getParameter("status");
				Integer status = new Integer(str3);
				
				Integer updateStatus = Math.abs(status-1);
				
				JournalService journalSvc = new JournalService();
				journalSvc.updateJournalStatus(journalId,String.valueOf(updateStatus));
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("url");
				failureView.forward(req, res);
			}
		}
        
	}
}
