package za101g2.member.controller;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import za101g2.emailvalidate.model.EmailValidateService;
import za101g2.emailvalidate.model.EmailValidateVO;
import za101g2.friends.model.FriendsService;
import za101g2.friends.model.FriendsVO;
import za101g2.mailtomember.SentEmail;
import za101g2.member.model.*;
import za101g2.store.model.*;
import za101g2.tool.RandomCode;

@WebServlet("/za101g2/front/member/member.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		if ("register".equals(action)) {

			MemberService memberSrv = new MemberService();
			boolean hasError = false;
			String emailFormat = "^[_a-z0-9-\\+]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9]+)*(\\.[a-z]{2,})$";
			String passwordFormat = "^[A-Za-z0-9]{8,}$";

			String email = request.getParameter("email").toLowerCase();

			if (!email.matches(emailFormat)) {
				hasError = true;
				request.setAttribute("emailError", "信箱格式錯誤。");
			} else if (memberSrv.getOneByEmail(email) != 0) {
				hasError = true;
				request.setAttribute("emailError", "此電子信箱已被使用。");
			}

			String password = request.getParameter("password");
			String rePassword = request.getParameter("rePassword");

			if (!password.equals(rePassword)) {
				hasError = true;
				request.setAttribute("passwordError", "密碼與再確認密碼不相同。");
			} else if (!password.matches(passwordFormat)) {
				hasError = true;
				request.setAttribute("passwordError", "密碼格式錯誤。");
			}

			String nickname = request.getParameter("nickname").trim();

			if (nickname.length() == 0) {
				hasError = true;
				request.setAttribute("nicknameError", "請輸入暱稱。");
			}

			String lastname = request.getParameter("lastname").trim();
			String firstname = request.getParameter("firstname").trim();

			if (lastname.length() == 0 || firstname.length() == 0) {
				hasError = true;
				request.setAttribute("nameError", "請確實填寫姓名。");
			}

			String sex = request.getParameter("sex");

			if (sex.equals("-1")) {
				hasError = true;
				request.setAttribute("sexError", "請選擇性別。");
			}

			if (hasError) {

				MemberVO memberVO = new MemberVO();
				memberVO.setEmail(email);
				memberVO.setNickname(nickname);
				memberVO.setLastname(lastname);
				memberVO.setFirstname(firstname);
				memberVO.setSex(sex);

				request.setAttribute("tempMemberVO", memberVO);

				String url = "/za101g2/front/member/register.jsp";
				RequestDispatcher failureView = request
						.getRequestDispatcher(url);
				failureView.forward(request, response);
			} else {

				memberSrv.addMember(email, password, nickname, lastname, firstname, sex);

				// 註冊成功直接幫會員進行登入(把memberVO加入session)
				
				MemberVO memberVO = memberSrv.login(email, password);
				
				HttpSession session = request.getSession();
				session.setAttribute("memberVO", memberVO);

				// 在資料新增一筆新註冊會員的認證碼。				
				RandomCode randomCode = new RandomCode();
				String validateCode = randomCode.randomCode(100);
				EmailValidateService emailValidateSrv = new EmailValidateService();
				EmailValidateVO emailValidateVO = emailValidateSrv.addEmailValidate(memberVO.getId(), memberVO.getEmail(), validateCode);

				// 寄送認證碼的認證信。

				SentEmail sentEmail = new SentEmail();
				String subject = "Petribe 認證通知信";
				// 把新增的認證碼再撈出來...啊、啊咧？
				String textText = "您好，以下是您的 Petribe 的認證網址；若您沒有加入 Petribe 的會員請忽略本信件。";
				String urlText = request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort() + request.getContextPath()+"/emailvalidate?validateCode="
						+ emailValidateVO.getValidateCode();
				sentEmail.sendEmail(emailValidateVO.getEmail(), subject,
						textText + (char)10 + (char)10 + urlText);

				String url = "/za101g2/front/member/register_success.jsp";
				RequestDispatcher successView = request
						.getRequestDispatcher(url);
				successView.forward(request, response);
			}
		}

		if ("login".equals(action)) {

			String email = request.getParameter("email").toLowerCase();
			String password = request.getParameter("password");

			MemberService srv = new MemberService();
			StoreService storeSrv = new StoreService();
			FriendsService friebdsSrv = new FriendsService();
			Long count = srv.getOneByEmail(email);
			if (count == 0) {

				String url = "/za101g2/front/member/login.jsp";
				request.setAttribute("errorMessage", "帳號不存在。");
				RequestDispatcher failureView = request
						.getRequestDispatcher(url);
				failureView.forward(request, response);

			} else {

				MemberVO memberVO = srv.login(email, password);
				if (memberVO == null) {
					String url = "/za101g2/front/member/login.jsp";
					request.setAttribute("errorMessage", "密碼錯誤。");
					RequestDispatcher failureView = request
							.getRequestDispatcher(url);
					failureView.forward(request, response);
				} else {

					HttpSession session = request.getSession();
					StoreVO storeVO = storeSrv.getStoreByFK(memberVO.getId());
					List<FriendsVO> friendsList = friebdsSrv.findOnesFriends(memberVO.getId(), "1");
					session.setAttribute("memberVO", memberVO);
					session.setAttribute("storeVO", storeVO);
					session.setAttribute("friendsList", friendsList);
					String url = "/za101g2/front/index.jsp";
					
					String requestURL = request.getParameter("requestURL");
					if(!"null".equals(requestURL.toLowerCase()))
						url = requestURL;
					RequestDispatcher successView = request
							.getRequestDispatcher(url);
					successView.forward(request, response);
				}
			}

		}

		if ("updateNickname".equals(action)) {
			MemberService srv = new MemberService();
			HttpSession session = request.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			String nickname = request.getParameter("nickname").trim();
			boolean hasError = false;
			
			if (nickname.length() == 0) {
				hasError = true;
				request.setAttribute("nicknameError", "請輸入新暱稱。");
			}
			
			if(hasError){
				String url = "/za101g2/front/member/updateNickname.jsp";
				RequestDispatcher failureView = request
						.getRequestDispatcher(url);
				failureView.forward(request, response);
			} else {
			
			memberVO = srv.updateNickname(nickname, memberVO.getId());
			//偷吃步式更新memberVO
			session.setAttribute("memberVO", memberVO);

			String url = "/za101g2/front/index.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
			}
		}

		if ("updateIcon".equals(action)) {

			Part file = request.getPart("icon");
			InputStream in = file.getInputStream();
			byte[] icon = new byte[in.available()];
			in.read(icon);

			HttpSession session = request.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			MemberService srv = new MemberService();

			memberVO = srv.updateIcon(icon, memberVO.getId());
			session.setAttribute("memberVO", memberVO);
			String url = "/za101g2/front/member/updateIcon_success.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);

		}

		if ("logout".equals(action)) {
			HttpSession session = request.getSession();
			session.invalidate();
			String url = "/za101g2/front/index.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
		}
		
		if("update_password".equals(action)){
			
			HttpSession session = request.getSession();
			MemberService srv = new MemberService();
			
			boolean hasError = false;
			String passwordFormat = "^[A-Za-z0-9]{8,}$";
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			
			
			String password = request.getParameter("password");
			String updatePassword = request.getParameter("updatePassword");
			String reUpdatePassword = request.getParameter("reUpdatePassword");
			
			if(password.trim().length()==0){
				hasError = true;
				request.setAttribute("passwordError", "請輸入原密碼。");
			}else if (!password.matches(passwordFormat)) {
				hasError = true;
				request.setAttribute("passwordError", "原密碼格式錯誤。");
			}else if(!password.equals(srv.findOneByPrimaryKey(memberVO.getId()).getPassword())){
				hasError = true;
				request.setAttribute("passwordError", "與原密碼不一致。");
			}else if (!updatePassword.matches(passwordFormat)) {
				hasError = true;
				request.setAttribute("passwordError", "欲修改的密碼格式錯誤。");
			} else if (!updatePassword.equals(reUpdatePassword)) {
				hasError = true;
				request.setAttribute("passwordError", "欲修改的密碼與再確認密碼不一致。");
			}
			
			if(hasError){
				String url = "/za101g2/front/member/updatePassword.jsp";
				RequestDispatcher failureView = request
						.getRequestDispatcher(url);
				failureView.forward(request, response);
			} else {
				srv.updatePassword(updatePassword, memberVO.getId());
				//修改成功後，系統自動登出。
				session.invalidate();
				
				String url = "/za101g2/front/member/updatePassword_success.jsp";
				RequestDispatcher successView = request
						.getRequestDispatcher(url);
				successView.forward(request, response);
			}
			
		}
		
		if("search_member".equals(action)){
			String search = request.getParameter("search").toLowerCase();
			
			MemberService srv = new MemberService();
			List<MemberVO> list = srv.getSome(search);
			request.setAttribute("searchedList", list);
			request.setAttribute("hasSearched", "hasSearched");
			String url = "/za101g2/back/member/memberManager.jsp";
			RequestDispatcher successView = request
					.getRequestDispatcher(url);
			successView.forward(request, response);
		}
		
		if("update_status".equals(action)){
			Integer onesmemberid = Integer.parseInt(request.getParameter("onesmemberid"));
			
			MemberService srv = new MemberService();
			MemberVO memberVO = srv.getOneMember(onesmemberid);
			
			String changed_status = String.valueOf(Integer.parseInt(memberVO.getStatus())*-1);
			
			srv.setStatus(memberVO.getId(), changed_status);
			
			String url = "/za101g2/back/member/memberManager.jsp";
			RequestDispatcher successView = request
					.getRequestDispatcher(url);
			successView.forward(request, response);
		}
		
		if("forget_password".equals(action)){
			String email = request.getParameter("email").toLowerCase();
			MemberService srv = new MemberService();
			boolean hasMessage = false;
			if(srv.getOneByEmail(email)==0){
				request.setAttribute("message", "此信箱不存在。");
			} else if(srv.findOneByEmail(email).getEmail().equals(email)){
				
				// 寄送密碼。

				SentEmail sentEmail = new SentEmail();
				String subject = "Petribe 密碼通知信";
				// 把新增的認證碼再撈出來...啊、啊咧？
				String textText = "您好，以下是您 Petribe 的密碼，請妥善保管。";
				String passwordText = srv.findOneByEmail(email).getPassword();
				sentEmail.sendEmail(email, subject,
						textText + (char)10 + (char)10 + passwordText);
				
				hasMessage = true;
				request.setAttribute("message", "密碼已寄送，請至此信箱接收。");
			}
			
			String url = "/za101g2/front/member/forget_password.jsp";
			RequestDispatcher successView = request
					.getRequestDispatcher(url);
			successView.forward(request, response);
		}
		
		if("resent".equals(action)){
			Integer memId = Integer.parseInt(request.getParameter("memid"));
			MemberService srv = new MemberService();
			MemberVO memberVO = srv.findOneByPrimaryKey(memId);
			EmailValidateService emailValidateSrv = new EmailValidateService();
			
			EmailValidateVO emailValidateVO =  emailValidateSrv.findByMemId(memId);
			if(emailValidateVO==null){
				RandomCode randomCode = new RandomCode();
				String validateCode = randomCode.randomCode(100);

				emailValidateVO = emailValidateSrv.addEmailValidate(memberVO.getId(), memberVO.getEmail(), validateCode);
				
			} 
			
			// 寄送認證碼的認證信。

			SentEmail sentEmail = new SentEmail();
			String subject = "Petribe 認證通知信";
			// 把新增的認證碼再撈出來...啊、啊咧？
			String textText = "您好，以下是您的 Petribe 的認證網址；若您沒有加入 Petribe 的會員請忽略本信件。";
			String urlText = request.getScheme() + "://"
					+ request.getServerName() + ":"
					+ request.getServerPort() + request.getContextPath()+"/emailvalidate?validateCode="
					+ emailValidateVO.getValidateCode();
			sentEmail.sendEmail(emailValidateVO.getEmail(), subject,
					textText + (char)10 + (char)10 + urlText);
			
			request.setAttribute("send", "已送出");
			
			String url = "/za101g2/front/member/memberManager.jsp";
			RequestDispatcher successView = request
					.getRequestDispatcher(url);
			successView.forward(request, response);
			
		}

	}

}
