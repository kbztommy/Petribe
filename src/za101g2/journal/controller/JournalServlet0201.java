package za101g2.journal.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import za101g2.journal.model.JournalService;
import za101g2.journal.model.JournalVO;

public class JournalServlet0201 extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入日誌編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/ZA101G2/front/journal/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				Integer id = null;
				try {
					id = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("日誌編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/ZA101G2/front/journal/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				JournalService journalSvc = new JournalService();
				JournalVO journalVO = journalSvc.getOneJournal(id);
				if (journalVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/ZA101G2/front/journal/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("journalVO", journalVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/ZA101G2/front/journal/listOneJournal.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/ZA101G2/front/journal/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				Integer id = new Integer(req.getParameter("id"));
				
				/***************************2.�}�l�d�߸��****************************************/
				JournalService journalSvc = new JournalService();
				JournalVO journalVO = journalSvc.getOneJournal(id);
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("journalVO", journalVO);         // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/ZA101G2/front/journal/update_journal_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_journal_input.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/ZA101G2/front/journal/listAllJournal.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // �Ӧ�update_journal_input.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				Integer id = new Integer(req.getParameter("id").trim());
				String title = req.getParameter("title").trim();
				String article = req.getParameter("article").trim();				
				
				java.sql.Date releaseDate = null;
				try {
					releaseDate = java.sql.Date.valueOf(req.getParameter("releaseDate").trim());
				} catch (IllegalArgumentException e) {
					releaseDate=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入發表時間!");
				}

				java.sql.Date ediltDate = null;
				try {
					ediltDate = java.sql.Date.valueOf(req.getParameter("ediltDate").trim());
				} catch (IllegalArgumentException e) {
					ediltDate=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入最後編輯時間!");
				}

				Integer memId = new Integer(req.getParameter("memId").trim());				
				String status = req.getParameter("status").trim();
				String isPublic = req.getParameter("isPublic").trim();					

				JournalVO journalVO = new JournalVO();
				journalVO.setId(id);
				journalVO.setTitle(title);
				journalVO.setArticle(article);
				journalVO.setReleaseDate(releaseDate);
				journalVO.setEdiltDate(ediltDate);
				journalVO.setMemId(memId);
				journalVO.setStatus(status);
				journalVO.setIsPublic(isPublic);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("journalVO", journalVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/ZA101G2/front/journal/update_journal_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.�}�l�ק���*****************************************/
				JournalService journalSvc = new JournalService();
				journalVO = journalSvc.updateJournal(id, title, article, memId, status, isPublic);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				req.setAttribute("journalVO", journalVO); // ��Ʈwupdate���\��,���T����empVO����,�s�Jreq
				String url = "/ZA101G2/front/journal/listOneJournal.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/ZA101G2/front/journal/update_journal_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
				String title = req.getParameter("title").trim();
				String article = req.getParameter("article").trim();				
				
				java.sql.Date releaseDate = null;
				try {
					releaseDate = java.sql.Date.valueOf(req.getParameter("releaseDate").trim());
				} catch (IllegalArgumentException e) {
					releaseDate=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入發表時間!");
				}

				java.sql.Date ediltDate = null;
				try {
					ediltDate = java.sql.Date.valueOf(req.getParameter("ediltDate").trim());
				} catch (IllegalArgumentException e) {
					ediltDate=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入最後編輯時間!");
				}

				Integer memId = new Integer(req.getParameter("memId").trim());				
				String status = req.getParameter("status").trim();
				String isPublic = req.getParameter("isPublic").trim();					

				JournalVO journalVO = new JournalVO();
				journalVO.setTitle(title);
				journalVO.setArticle(article);
				journalVO.setReleaseDate(releaseDate);
				journalVO.setEdiltDate(ediltDate);
				journalVO.setMemId(memId);
				journalVO.setStatus(status);
				journalVO.setIsPublic(isPublic);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("journalVO", journalVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/ZA101G2/front/journal/addJournal.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�s�W���***************************************/
				JournalService journalSvc = new JournalService();
				journalVO = journalSvc.addJournal(title, article, memId, status, isPublic);

				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/ZA101G2/front/journal/listAllJournal.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/ZA101G2/front/journal/addJournal.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				Integer id = new Integer(req.getParameter("id"));
				
				/***************************2.�}�l�R�����***************************************/
				JournalService journalSvc = new JournalService();
				journalSvc.deleteJournal(id);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/								
				String url = "/ZA101G2/front/journal/listAllJournal.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/ZA101G2/front/journal/listAllJournal.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
