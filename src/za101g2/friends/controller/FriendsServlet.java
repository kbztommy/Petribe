package za101g2.friends.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import za101g2.friends.model.FriendsService;
import za101g2.member.model.MemberVO;

/**
 * Servlet implementation class FriendsServlet
 */
@WebServlet("/za101g2/front/friend/friends.do")
public class FriendsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		final String accept = "1";
		final String apply = "0";
		
		if("be_friend".equals(action)){
			
			HttpSession session = request.getSession();
			
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer onesmemberid = Integer.parseInt(request.getParameter("onesmemberid"));
			
			FriendsService srv = new FriendsService();
			try{
				srv.addFriend(memberVO.getId(), onesmemberid, apply);
			}catch(Exception e){
				
			}finally{
				String url = "/memberHome/memberHome.do?action=memberHome&memId="+onesmemberid;
				RequestDispatcher successView = request.getRequestDispatcher(url);
				successView.forward(request, response);
			}
		}

		if("accept_be_friend".equals(action)){
			
			HttpSession session = request.getSession();
			
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer onesmemberid = Integer.parseInt(request.getParameter("onesmemberid"));
			
			FriendsService srv = new FriendsService();
			//添加自己一個好友，並改對面的狀態為接受。
			srv.acceptBeFriend(memberVO.getId(), onesmemberid, accept);
			
			String url = "/memberHome/memberHome.do?action=memberHome&memId="+onesmemberid;
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);	
		}
		
		if("refuse_be_friend".equals(action)){
			
			HttpSession session = request.getSession();
			
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer onesmemberid = Integer.parseInt(request.getParameter("onesmemberid"));
			
			FriendsService srv = new FriendsService();
			//刪除需反過來填，以刪除對方對你的邀請。
			srv.refuseBeFriend(onesmemberid, memberVO.getId());
			
			String url = "/za101g2/front/friend/friendApp.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);	
			
		}
		
		if("delete_friend".equals(action)){
			
			HttpSession session = request.getSession();
			
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			Integer onesmemberid = Integer.parseInt(request.getParameter("onesmemberid"));
			
			FriendsService srv = new FriendsService();
			//填正填反沒差別，正反都要刪。
			srv.deleteFriend(memberVO.getId(), onesmemberid);
			
			String url = "/memberHome/memberHome.do?action=memberHome&memId="+onesmemberid;
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);	
		}
	}

}
