package za101g2.store.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.websocket.Session;

import org.json.JSONException;
import org.json.JSONObject;

import za101g2.member.model.MemberService;
import za101g2.member.model.MemberVO;
import za101g2.store.model.*;
import za101g2.store.model.*;
import za101g2.storeBoard.model.*;
import za101g2.storePhoto.model.*;

@WebServlet("/StoreServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class StoreServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		res.setHeader("Pragma", "no-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setDateHeader("Expires", 0);
		req.setCharacterEncoding("utf-8");

		String action = req.getParameter("action");

		if ("setInfoForm".equals(action)) {

			res.setContentType("application/json");
			res.setCharacterEncoding("utf-8");
			JSONObject json = new JSONObject();
			PrintWriter out = res.getWriter();

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			HttpSession session = req.getSession();
			StoreVO storeVO = (StoreVO) session.getAttribute("storeVO");
			Part part = req.getPart("icon");
			InputStream ins = part.getInputStream();
			String storeNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";

			String name = req.getParameter("name");
			if (name == null || name.isEmpty()) {
				errorMsgs.put("name", "請輸入商店名稱");
			}else if(!name.trim().matches(storeNameReg)) {
				errorMsgs.put("name","商店名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
            }

			String info = req.getParameter("info");
			if (info == null || info.isEmpty()) {
				errorMsgs.put("info", "請輸入 關於我:");
			}

			if (!errorMsgs.isEmpty()) {
				try {
					json.put("hasError", "y");
					json.put("errorMsgs", errorMsgs);
					out.print(json.toString());
					out.flush();
					out.close();
					return;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			byte[] icon = null;
			if (ins.available() == 0) {

				icon = storeVO.getIcon();

			} else {
				icon = new byte[ins.available()];
				ins.read(icon);
			}
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			storeVO.setInfo(info);
			storeVO.setName(name);
			storeVO.setIcon(icon);
			StoreService storeSrv = new StoreService();
			storeSrv.Update(storeVO);
			String iconURL = req.getContextPath() + "/PicForStrore?id="
					+ storeVO.getId().toString();
			try {
				json.put("name", name);
				json.put("info", info);
				json.put("iconURL", iconURL);

			} catch (JSONException e) {
				e.printStackTrace();
			}

			out.print(json.toString());
			out.flush();
			out.close();
			return;
		}

		if ("searchStore".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {

				HttpSession session = req.getSession();
				String city, petType;

				if (req.getParameter("whichPage") == null) {

					if (req.getParameter("city").isEmpty()) {
						errorMsgs.add("請選擇地區，地區為必選");
					}

					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/za101g2/front/store/SearchStore.jsp");
						failureView.forward(req, res);
						return;
					}
					city = req.getParameter("city");
					petType = req.getParameter("petType");
					System.out.println("petType" + petType);
					Map<String, String> map1 = new HashMap<String, String>();
					map1.put("city", city);
					map1.put("petType", petType);
					session.setAttribute("storeSearchMap", map1);
				} else {

					Map<String, String> map = (Map<String, String>) session
							.getAttribute("storeSearchMap");
					city = map.get("city");
					petType = map.get("petType");

				}

				StoreService storeSrv = new StoreService();
				List<HashMap<String, Object>> storeSearchList = storeSrv
						.searchStoreVOList(city, petType);

				req.setAttribute("storeSearchList", storeSearchList);
				RequestDispatcher successView = req
						.getRequestDispatcher("/za101g2/front/store/SearchStore.jsp");
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/store/SearchStore.jsp");
				failureView.forward(req, res);
			}
		}

		if ("lookOverStore".equals(action)) {

			Integer storeId = Integer.parseInt(req.getParameter("storeId"));
			StoreService storeSrv = new StoreService();
			Map<String, Object> storeInfoMap = storeSrv
					.getStoreInfoMap(storeId);
			StorePhotoService storePhotoSrv = new StorePhotoService();
			List<StorePhotoVO> storePhotoVOList = storePhotoSrv
					.getStorePic(storeId);
			StoreBoardService storeBoardSrv = new StoreBoardService();
			List<StoreBoardVO> storeBoardList = storeBoardSrv
					.getUndelete(storeId);
			req.setAttribute("storeInfoMap", storeInfoMap);
			req.setAttribute("storePhotoVOList", storePhotoVOList);
			req.setAttribute("storeBoardList", storeBoardList);
			RequestDispatcher successView = req
					.getRequestDispatcher("/za101g2/front/store/StoreInfo.jsp");
			successView.forward(req, res);
		}

		if ("apply".equals(action)) {

			boolean hasError = false;

			String name = req.getParameter("name").trim();
			String address = req.getParameter("address").trim();
			String info = req.getParameter("info").trim();
			Integer speciesLimit = Integer.parseInt(req
					.getParameter("speciesLimit"));
			String comments = req.getParameter("comments").trim();
			Integer maxQuantitly = Integer.parseInt(req
					.getParameter("maxQuantitly"));

			if (name.length() == 0) {
				hasError = true;
				req.setAttribute("nameError", "請確實填寫商店名稱。");
			}

			if (address.length() == 0) {
				hasError = true;
				req.setAttribute("addressError", "請確實填寫商店住址。");
			}

			if (info.length() == 0) {
				hasError = true;
				req.setAttribute("infoError", "請確實填寫商店簡介。");
			}

			if (speciesLimit == 0) {
				hasError = true;
				req.setAttribute("speciesLimitError", "請確實選擇你可寄養的寵物類型。");
			}

			if (comments.length() == 0) {
				hasError = true;
				req.setAttribute("commentsError", "請確實填寫你欲寄養的事由。");
			}

			if (maxQuantitly == 0) {
				hasError = true;
				req.setAttribute("maxQuantitlyError", "請確實選擇你可寄養的寵物數量。");
			}

			if (hasError) {
				
				StoreVO storeVO = new StoreVO();
				storeVO.setName(name);
				storeVO.setAddress(address);
				storeVO.setInfo(info);
				storeVO.setSpeciesLimit(speciesLimit);
				storeVO.setComments(comments);
				storeVO.setMaxQuantitly(maxQuantitly);
				req.setAttribute("tempStoreVO", storeVO);
				
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/member/applyStore_newer.jsp");
				failureView.forward(req, res);
				
			} else {

				HttpSession session = req.getSession();
				MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");

				StoreService srv = new StoreService();
				srv.Insert(name, address, memberVO, info, speciesLimit,
						comments, maxQuantitly);

				req.setAttribute("successMsg", "successMsg");

				RequestDispatcher successView = req
						.getRequestDispatcher("/za101g2/front/member/applyStore_wait.jsp");
				successView.forward(req, res);
			}
		}

		if ("re_apply".equals(action)) {

			boolean hasError = false;
			
			String name = req.getParameter("name").trim();
			String address = req.getParameter("address").trim();
			String info = req.getParameter("info").trim();
			Integer speciesLimit = Integer.parseInt(req
					.getParameter("speciesLimit"));
			String comments = req.getParameter("comments").trim();
			Integer maxQuantitly = Integer.parseInt(req
					.getParameter("maxQuantitly"));
			String siteReport = req.getParameter("siteReport").trim();
			String status = req.getParameter("status");

			if (name.length() == 0) {
				hasError = true;
				req.setAttribute("nameError", "請確實填寫商店名稱。");
			}

			if (address.length() == 0) {
				hasError = true;
				req.setAttribute("addressError", "請確實填寫商店住址。");
			}

			if (info.length() == 0) {
				hasError = true;
				req.setAttribute("infoError", "請確實填寫商店簡介。");
			}

			if (speciesLimit == 0) {
				hasError = true;
				req.setAttribute("speciesLimitError", "請確實選擇你可寄養的寵物類型。");
			}

			if (comments.length() == 0) {
				hasError = true;
				req.setAttribute("commentsError", "請確實填寫你欲寄養的事由。");
			}

			if (maxQuantitly == 0) {
				hasError = true;
				req.setAttribute("maxQuantitlyError", "請確實選擇你可寄養的寵物數量。");
			}
			
			if (hasError) {
				
				StoreVO storeVO = new StoreVO();
				storeVO.setName(name);
				storeVO.setAddress(address);
				storeVO.setInfo(info);
				storeVO.setSpeciesLimit(speciesLimit);
				storeVO.setComments(comments);
				storeVO.setMaxQuantitly(maxQuantitly);
				storeVO.setSiteReport(siteReport);
				storeVO.setStatus(status);
				req.setAttribute("tempStoreVO", storeVO);
				
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/member/applyStore_wait.jsp");
				failureView.forward(req, res);
				
			} else {
			
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");

			StoreService srv = new StoreService();
			StoreVO storeVO = srv.getStoreByFK(memberVO.getId());
			storeVO.setName(name);
			storeVO.setAddress(address);
			storeVO.setInfo(info);
			storeVO.setSpeciesLimit(speciesLimit);
			storeVO.setComments(comments);
			storeVO.setMaxQuantitly(maxQuantitly);
			storeVO.setStatus("2");
			srv.Update(storeVO);
			
			req.setAttribute("successAgainMsg", "successAgainMsg");

			RequestDispatcher successView = req
					.getRequestDispatcher("/za101g2/front/member/applyStore_wait.jsp");
			successView.forward(req, res);
			
			}

		}

		if ("get_a_apply".equals(action)) {
			Integer onesstoreid = Integer.parseInt(req
					.getParameter("onesstoreid"));

			StoreService srv = new StoreService();
			StoreVO storeVO = srv.getOneStore(onesstoreid);
			req.setAttribute("storeVO", storeVO);

			RequestDispatcher successView = req
					.getRequestDispatcher("/za101g2/back/store/onesApply.jsp");
			successView.forward(req, res);
		}

		if (req.getParameter("accept") != null) {
			String siteReport = req.getParameter("siteReport");
			Integer onesmemberid = Integer.parseInt(req
					.getParameter("onesmemberid"));
			MemberService memberSrv = new MemberService();
			StoreService srv = new StoreService();
			StoreVO storeVO = srv.getOneStore(onesmemberid);
			storeVO.setSiteReport(siteReport);
			storeVO.setStatus("4");
			storeVO.setReportDate(new Timestamp(new Date().getTime()));
			srv.rankUp(storeVO);

			RequestDispatcher successView = req
					.getRequestDispatcher("/za101g2/back/store/storeApply.jsp");
			successView.forward(req, res);

		}
		if (req.getParameter("refuse") != null) {
			String siteReport = req.getParameter("siteReport");
			Integer onesmemberid = Integer.parseInt(req
					.getParameter("onesmemberid"));
			StoreService srv = new StoreService();
			StoreVO storeVO = srv.getOneStore(onesmemberid);
			storeVO.setSiteReport(siteReport);
			storeVO.setStatus("3");
			storeVO.setReportDate(new Timestamp(new Date().getTime()));
			srv.Update(storeVO);

			RequestDispatcher successView = req
					.getRequestDispatcher("/za101g2/back/store/storeApply.jsp");
			successView.forward(req, res);
		}

	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

}
