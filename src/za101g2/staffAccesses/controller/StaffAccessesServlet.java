package za101g2.staffAccesses.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import za101g2.staff.model.StaffService;
import za101g2.staff.model.StaffVO;
import za101g2.staffAccesses.model.StaffAccessesService;

@WebServlet("/za101g2/back/staff/staffAccesses.do")
public class StaffAccessesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		String getAccesses = (String) request.getAttribute("getAccesses");
		
		if("getYourAccesses".equals(getAccesses)){
			
			HttpSession session = request.getSession();
			StaffVO staffVO = (StaffVO) session.getAttribute("staffVO");
			
			StaffAccessesService staffAccessesSrv = new StaffAccessesService();
			
			List list = staffAccessesSrv.findAccessesById(staffVO.getId());
			
			session.setAttribute("yourAccessesList", list);
			
			String url = "/za101g2/back/staff/index.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);	
			
		}
		
		if("getOnesAccesses".equals(getAccesses)){
			
			StaffVO onesStaffVO = (StaffVO) request.getAttribute("onesStaffVO");
			
			StaffAccessesService staffAccessesSrv = new StaffAccessesService();
			List list = staffAccessesSrv.findAccessesById(onesStaffVO.getId());
			
			request.setAttribute("onesAccessesList", list);
			
			String url = "/za101g2/back/staff/staffManager/onesAccessesList.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);	
			
		}
		
		if("updateAccesses".equals(action)){
			String[] accesses = request.getParameterValues("accesses");
			List<Integer> intList = new ArrayList<Integer>();
			if(accesses!=null){
				for(String access :accesses){
					intList.add(Integer.parseInt(access));
				}
			}
			Integer staffId = Integer.parseInt(request.getParameter("staffId"));
			
			StaffAccessesService staffAccessesSrv = new StaffAccessesService();
			staffAccessesSrv.changeStaffAccesses(staffId,intList);
			
			StaffService staffSrv = new StaffService();
			StaffVO onesStaffVO = staffSrv.getOneStaff(staffId);
			request.setAttribute("onesStaffVO", onesStaffVO);

			List<Integer> list = staffAccessesSrv.findAccessesById(onesStaffVO
					.getId());

			request.setAttribute("onesAccessesList", list);
			
			Timestamp timestamp = new Timestamp(new Date().getTime());
			
			request.setAttribute("updateTime", timestamp);
			
			String url = "/za101g2/back/staff/onesStaffInfo.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
			
			}
		}
	}

