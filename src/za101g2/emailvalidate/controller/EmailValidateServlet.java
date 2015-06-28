package za101g2.emailvalidate.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import za101g2.emailvalidate.model.EmailValidateService;

@WebServlet("/emailvalidate")
public class EmailValidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();

		EmailValidateService srv = new EmailValidateService();
		String validateCode = request.getParameter("validateCode");
		String url = "/za101g2/front/member/emailvalidate_success.jsp";

		if (validateCode == null) {

			url = "/za101g2/front/illegalAccess.jsp";
			RequestDispatcher failureView = request.getRequestDispatcher(url);
			failureView.forward(request, response);

		} else {

			try {

				srv.validate(validateCode);

			} catch (Exception ex) {

				url = "/za101g2/front/member/emailvalidate_failure.jsp";
				
			} finally {
				
				// 登出
				session.invalidate();

//				RequestDispatcher successOrFailureView = request
//						.getRequestDispatcher(url);
//				successOrFailureView.forward(request, response);
				response.sendRedirect(request.getContextPath()+url);
			}
		}

	}

}
