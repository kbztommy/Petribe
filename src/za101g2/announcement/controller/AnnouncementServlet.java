package za101g2.announcement.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import za101g2.announcement.model.AnnouncementService;
import za101g2.announcement.model.AnnouncementVO;
import za101g2.staff.model.StaffVO;

public class AnnouncementServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { 


			try {
				Integer id = new Integer(req.getParameter("id"));
				AnnouncementService announcementSvc = new AnnouncementService();
				AnnouncementVO announcementVO = announcementSvc.getOneAnnouncement(id);

				req.setAttribute("announcementVO", announcementVO);
				String url = "/za101g2/back/announcement/listOneAnnouncement.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/back/homePage/home.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String title = req.getParameter("title").trim();
				if(title.length()==0){
					errorMsgs.add("請輸入標題<br>");
				}
				String comments = req.getParameter("comments").trim();
				if(comments.length()==0){
					errorMsgs.add("請輸入說明<br>");
				}
				HttpSession session = req.getSession();
				StaffVO staffVO = (StaffVO)session.getAttribute("staffVO");

				AnnouncementVO announcementVO = new AnnouncementVO();
				announcementVO.setTitle(title);
				announcementVO.setComments(comments);
								
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("announcementVO", announcementVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/za101g2/back/announcement/addAnnouncement.jsp");
					failureView.forward(req, res);
					return;
				}
				
				AnnouncementService announcementSvc = new AnnouncementService();
				announcementVO = announcementSvc.addAnnouncement(title, comments, staffVO.getId());

				String url = "/za101g2/back/homePage/home.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/back/announcement/addAnnouncement.jsp");
				failureView.forward(req, res);
			}
		}
		
	}
}
