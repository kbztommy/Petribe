package za101g2.message.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import za101g2.member.model.MemberVO;
import za101g2.message.model.MessageService;

/**
 * Servlet implementation class MessageServlet
 */
@WebServlet("/front/message/message.do")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		if("sent_message".equals(action)){
			
			HttpSession session = request.getSession();
			
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");			
			Integer onesmemberid = Integer.parseInt(request.getParameter("onesmemberid"));
			String msg = request.getParameter("msg");
			
			MessageService srv = new MessageService();
			srv.insertMessage(memberVO.getId(), onesmemberid, msg);
			
			String url = "/front/message/message.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);	
			
		}
	}

}
