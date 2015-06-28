package za101g2.phonevalidate.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import za101g2.member.model.MemberService;
import za101g2.member.model.MemberVO;
import za101g2.messagetomember.SendMessage;
import za101g2.phonevalidate.model.PhoneValidateService;
import za101g2.phonevalidate.model.PhoneValidateVO;
import za101g2.tool.RandomCode;

@WebServlet("/za101g2/front/member/phoneValidate.do")
public class PhoneValidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		if ("add_a_validate".equals(action)) {

			final String phoneFormat = "^09[0-9]{8}";
			String phone = request.getParameter("phone");
			boolean hasError = false;
			MemberService memberSrv = new MemberService();
			PhoneValidateService phoneValidateSrv = new PhoneValidateService();
			HttpSession session = request.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			if (phone.trim().length() == 0) {
				hasError = true;
				request.setAttribute("phoneError", "請輸入手機號碼。");
			} else if (!phone.matches(phoneFormat)) {
				hasError = true;
				request.setAttribute("phoneError", "手機格式錯誤。");
			} else if (phoneValidateSrv.findOneByPhone(phone)!=0) {
				hasError = true;
				request.setAttribute("phoneError", "此手機正在驗證中。");
			} else if (memberSrv.getOneByPhone(phone) != 0) {
				hasError = true;
				request.setAttribute("phoneError", "此手機已被使用。");
			} else if(phoneValidateSrv.findOne(memberVO.getId())!=null){
				hasError = true;
				request.setAttribute("phoneError", "此帳號已申請驗證，請至下一步進行驗證。");
			}

			if (hasError) {
				String url = "/za101g2/front/member/phoneValidate_step1.jsp";
				RequestDispatcher failureView = request
						.getRequestDispatcher(url);
				failureView.forward(request, response);
			} else {

				// 先把資料新增進資料庫。

				RandomCode randomCode = new RandomCode();
				String validateCode = randomCode.randomCode(5);

				PhoneValidateService srv = new PhoneValidateService();
				PhoneValidateVO phoneValidateVO = srv.addPhoneValidate(
						memberVO.getId(), phone, validateCode);

				// 送手機簡訊

				String message = "您的 Petribe 認證碼為 "
						+ phoneValidateVO.getValidateCode();

				SendMessage sendMessage = new SendMessage();
				sendMessage.sendMessage(phone, message);

				// 跳轉下一頁

				String url = "/za101g2/front/member/phoneValidate_step2.jsp";
				RequestDispatcher successView = request
						.getRequestDispatcher(url);
				successView.forward(request, response);

			}
		}

		if ("validate".equals(action)) {

			String validateCode = request.getParameter("validateCode");
			HttpSession session = request.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
			PhoneValidateService phoneValidateSrv = new PhoneValidateService();
			PhoneValidateVO phoneValidateVO = phoneValidateSrv.findOne(memberVO
					.getId());
			boolean hasError = false;

			// 沒輸入認證碼跳過此判斷後的全數判斷。
			if (validateCode.length() == 0) {
				hasError = true;
				request.setAttribute("validateCodeError", "請輸入認證碼。");
			} else {
				// 認證碼錯誤時擋下來，並把計數加1。
				if (!phoneValidateVO.getValidateCode().equals(validateCode)) {
					hasError = true;
					phoneValidateVO = phoneValidateSrv
							.errorCountPlus(phoneValidateVO.getMemId());
					request.setAttribute("validateCodeError", "認證碼錯誤。");
				}
				// 當計數達到五次時，刪除該筆認證碼。
				if (phoneValidateVO.getErrorCount() >= 5) {
					hasError = true;
					phoneValidateSrv.deletePhoneValidate(phoneValidateVO
							.getMemId());
					request.setAttribute("validateCodeError",
							"輸入錯誤數量達系統上限，本驗證碼已被刪除。請重新輸入手機獲得新的驗證碼。");
				}
			}

			if (hasError) { // 發生錯誤的狀況。跳回原頁顯示輸入錯誤的訊息，錯誤次數達上限蓋掉輸入錯誤的錯誤，並顯示該錯誤訊息。
				String url = "/za101g2/front/member/phoneValidate_step2.jsp";
				RequestDispatcher failureView = request
						.getRequestDispatcher(url);
				failureView.forward(request, response);
			} else { // 已排除認證碼錯誤的狀況，不再進行判斷。
				phoneValidateSrv.validate(phoneValidateVO);
				MemberService memberSrv = new MemberService();
				memberVO = memberSrv.login(memberVO.getEmail(), memberVO.getPassword());
				session.setAttribute("memberVO", memberVO);
				String url = "/za101g2/front/member/phoneValidate_final.jsp";
				RequestDispatcher successView = request
						.getRequestDispatcher(url);
				successView.forward(request, response);
			}

		}
	}

}
