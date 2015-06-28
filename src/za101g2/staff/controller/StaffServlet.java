package za101g2.staff.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import za101g2.mailtomember.SentEmail;
import za101g2.staff.model.StaffService;
import za101g2.staff.model.StaffVO;
import za101g2.staffAccesses.model.StaffAccessesService;

@WebServlet("/za101g2/back/staff/staff.do")
public class StaffServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		if ("login".equals(action)) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");

			StaffService srv = new StaffService();

			StaffVO staffVO = srv.login(email, password);
			HttpSession session = request.getSession();

			session.setAttribute("staffVO", staffVO);

			request.setAttribute("getAccesses", "getYourAccesses");

			String url = "/za101g2/back/homePage/home.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
		}

		if ("getOne_For_Update".equals(action)) {

			Integer id = Integer.parseInt(request.getParameter("staffid"));

			StaffService srv = new StaffService();

			StaffVO onesStaffVO = srv.getOneStaff(id);
			request.setAttribute("onesStaffVO", onesStaffVO);

			StaffAccessesService staffAccessesSrv = new StaffAccessesService();
			List<Integer> list = staffAccessesSrv.findAccessesById(onesStaffVO
					.getId());

			request.setAttribute("onesAccessesList", list);

			String url = "/za101g2/back/staff/onesStaffInfo.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);

		}

		if ("update_status".equals(action)) {
			String status = request.getParameter("status");
			Integer onesStaffId = Integer.parseInt(request
					.getParameter("onesStaffId"));

			StaffService srv = new StaffService();
			srv.updateStaffStatus(onesStaffId, status);
			
			StaffVO onesStaffVO = srv.getOneStaff(onesStaffId);
			request.setAttribute("onesStaffVO", onesStaffVO);

			StaffAccessesService staffAccessesSrv = new StaffAccessesService();
			List<Integer> list = staffAccessesSrv.findAccessesById(onesStaffVO
					.getId());

			request.setAttribute("onesAccessesList", list);

			Timestamp timestamp = new Timestamp(new Date().getTime());
			
			request.setAttribute("updateTime", timestamp);
			
			String url = "/za101g2/back/staff/onesStaffInfo.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);

		}

		if ("add_new_staff".equals(action)) {
			String email = request.getParameter("email").toLowerCase();
			String name = request.getParameter("name");
			String status = request.getParameter("status");
			String subject = "Petribe 員工通知信";
			String message = "你好，以下是你後台登入時使用的密碼，請妥善保管。";
			boolean hasError = false;
			String emailFormat = "^[_a-z0-9-\\+]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9]+)*(\\.[a-z]{2,})$";
			String key = null;
			StaffService srv = new StaffService();
			if (email.length() == 0) {
				hasError = true;
				request.setAttribute("emailError", "※請輸入電子信箱。");
			} else if (!email.matches(emailFormat)) {
				hasError = true;
				request.setAttribute("emailError", "※信箱格式錯誤。");
			} else if (srv.emailBeUsed(email) != 0) {
				hasError = true;
				request.setAttribute("emailError", "※此電子信箱已被其他員工使用。");
			} if (name.length() == 0) {
				hasError = true;
				request.setAttribute("nameError", "※請輸入名字。");
			} if ("-1".equals(status)) {
				hasError = true;
				request.setAttribute("statusError", "※請選擇員工狀態。");
			}

			if (hasError) {
				
				StaffVO staffVO = new StaffVO();
				staffVO.setEmail(email);
				staffVO.setName(name);
				staffVO.setStatus(status);
				request.setAttribute("tempStaffVO", staffVO);
				
				String url = "/za101g2/back/staff/staffManager.jsp";
				RequestDispatcher failureView = request
						.getRequestDispatcher(url);
				failureView.forward(request, response);
			} else {

				key = srv.addStaff(email, name, status);

				StaffVO staffVO = srv.getOneStaff(Integer.parseInt(key));

				SentEmail sendEmail = new SentEmail();
				sendEmail.sendEmail(staffVO.getEmail(), subject, message
						+ (char) 10 + (char) 10 + staffVO.getPassword());

				request.setAttribute("updateSuccess", "新增成功！");
				
				String url = "/za101g2/back/staff/staffManager.jsp";
				RequestDispatcher successView = request
						.getRequestDispatcher(url);
				successView.forward(request, response);

			}

		}
		if ("update_password".equals(action)) {
			HttpSession session = request.getSession();
			StaffService srv = new StaffService();

			boolean hasError = false;
			String passwordFormat = "^[A-Za-z0-9]{8,}$";
			StaffVO staffVO = (StaffVO) session.getAttribute("staffVO");

			String password = request.getParameter("password");
			String updatePassword = request.getParameter("updatePassword");
			String reUpdatePassword = request.getParameter("reUpdatePassword");

			if (password.trim().length() == 0) {
				hasError = true;
				request.setAttribute("passwordError", "請輸入原密碼。");
			} else if (!password.matches(passwordFormat)) {
				hasError = true;
				request.setAttribute("passwordError", "原密碼格式錯誤。");
			} else if (!password.equals(srv.getOneStaff(staffVO.getId())
					.getPassword())) {
				hasError = true;
				request.setAttribute("passwordError", "與原密碼不一致。");
			} else if (!updatePassword.matches(passwordFormat)) {
				hasError = true;
				request.setAttribute("passwordError", "欲修改的密碼格式錯誤。");
			} else if (!updatePassword.equals(reUpdatePassword)) {
				hasError = true;
				request.setAttribute("passwordError", "欲修改的密碼與再確認密碼不一致。");
			}

			if (hasError) {
				String url = "/za101g2/back/password/";
				RequestDispatcher failureView = request
						.getRequestDispatcher(url);
				failureView.forward(request, response);
			} else {
				srv.updateStaffPassword(staffVO.getId(), updatePassword);
				// 修改成功後，系統自動登出。
				session.invalidate();

				String url = "/za101g2/back/password/password_success.jsp";
				RequestDispatcher successView = request
						.getRequestDispatcher(url);
				successView.forward(request, response);
			}
		}
		
		if("search_staff".equals(action)){
			String search = request.getParameter("search");
			
			StaffService srv = new StaffService();
			List<StaffVO> list = srv.getSome(search);
			
			request.setAttribute("searchedList", list);
			request.setAttribute("hasSearched", "hasSearched");
			String url = "/za101g2/back/staff/staffManager.jsp";
			RequestDispatcher successView = request
					.getRequestDispatcher(url);
			successView.forward(request, response);
		}
		
		if("logout".equals(action)){
			HttpSession session = request.getSession();
			session.invalidate();
			
			String url = "/za101g2/back/homePage/";
			RequestDispatcher successView = request
					.getRequestDispatcher(url);
			successView.forward(request, response);
		}
	}

}
