package za101g2.pet.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import za101g2.member.model.MemberVO;
import za101g2.pet.model.PetService;

/**
 * Servlet implementation class PetServlet
 */
@WebServlet("/PetServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class PetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		if ("add_new_pet".equals(action)) {
			String name = request.getParameter("name").trim();
			String species = request.getParameter("species").trim();
			byte[] qrCode = null;
			Part file = request.getPart("icon");
			InputStream in = file.getInputStream();
			byte[] icon = new byte[in.available()];
			in.read(icon);
			boolean hasError = false;

			if (name.length() == 0) {
				hasError = true;
				request.setAttribute("nameError", "請輸入名字。");
			}
			if (species.length() == 0) {
				hasError = true;
				request.setAttribute("speciesError", "請輸入種類。");
			}

			if (hasError) {
				request.setAttribute("petError", "※請確實填寫資料新增");
				
				String url = "/memberHome/memberHome.do?action=myHome";
				RequestDispatcher successView = request
						.getRequestDispatcher(url);
				successView.forward(request, response);
			} else {
				HttpSession session = request.getSession();
				MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");

				PetService srv = new PetService();
				srv.addPet(icon, name, species, qrCode, memberVO.getId());

				String url = "/memberHome/memberHome.do?action=myHome";
				RequestDispatcher successView = request
						.getRequestDispatcher(url);
				successView.forward(request, response);

			}

		}
	}

}
