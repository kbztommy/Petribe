package za101g2.serviceList.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import za101g2.serviceList.model.*;
import za101g2.store.model.*;

@WebServlet("/ServiceListServlet")
public class ServiceListServlet extends HttpServlet {

	public static final String DOG_SERVICE_NAME = "住宿";
	public static final String CAT_SERVICE_NAME = "住宿";
	public static final String REQUIRED_TYPE = "required";
	public static final String PICK_TYPE = "pick";
	public static final String DOG_PETTYPE = "dog";
	public static final String CAT_PETTYPE = "cat";

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		res.setHeader("Pragma","no-cache");
	    res.setHeader("Cache-Control","no-cache");
	    res.setDateHeader("Expires", 0);
		System.out.print("am i in? test");
		req.setCharacterEncoding("utf-8");
		String action = req.getParameter("action");

		// 設定住宿價錢與上下架。
		if ("setChargeForm".equals(action)) {
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			res.setContentType("application/json");
			res.setCharacterEncoding("utf-8");
			JSONObject json = new JSONObject();
			PrintWriter out = res.getWriter();

			HttpSession session = req.getSession();
			ServiceListService serviceSrv = new ServiceListService();
			StoreVO storeVO = (StoreVO) session.getAttribute("storeVO");
			

			int speciesLimit = storeVO.getSpeciesLimit();

			if (speciesLimit == 1 || speciesLimit == 3) {

				Integer dogPrice = null;
				try {
					dogPrice = new Integer(req.getParameter("dogPrice").trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("dogPrice", "住宿費用請填數字");
				} catch (NullPointerException e) {
					errorMsgs.put("dogPrice", "請填費用");
				}

				String dogIsOnsale = req.getParameter("dogIsOnsale").trim();

				if (!errorMsgs.isEmpty()) {
					errorMsgs.put("isError", "y");
					json = new JSONObject(errorMsgs);
					out.print(json.toString());
					out.flush();
					out.close();
					return;
				}

				// 修改
				try {
					serviceSrv.ServiceListUpdate(storeVO.getId(), "dog",
							"required", dogPrice, dogIsOnsale);
					// 新增
				} catch (NullPointerException e) {
					serviceSrv.ServiceListInsert(DOG_SERVICE_NAME, "",
							dogPrice, storeVO.getId(), dogIsOnsale,
							DOG_PETTYPE, REQUIRED_TYPE);
				}

				try {
					json.put("dogPrice", dogPrice);
					json.put("dogIsOnsale", dogIsOnsale);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			if (speciesLimit == 3 || speciesLimit == 2) {

				Integer catPrice = null;

				try {
					catPrice = new Integer(req.getParameter("catPrice").trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("catPrice", "住宿費用請填數字");
				} catch (NullPointerException e) {
					errorMsgs.put("catPrice", "請填費用");
				}

				String catIsOnsale = req.getParameter("catIsOnsale").trim();

				if (!errorMsgs.isEmpty()) {
					errorMsgs.put("isError", "y");
					json = new JSONObject(errorMsgs);
					out.print(json.toString());
					out.flush();
					out.close();
					return;
				}

				// 修改
				try {
					serviceSrv.ServiceListUpdate(storeVO.getId(), "cat",
							"required", catPrice, catIsOnsale);
					// 新增
				} catch (NullPointerException e) {
					serviceSrv.ServiceListInsert(CAT_SERVICE_NAME, "",
							catPrice, storeVO.getId(), catIsOnsale,
							CAT_PETTYPE, REQUIRED_TYPE);

				}
				try {
					json.put("catPrice", catPrice);
					json.put("catIsOnsale", catIsOnsale);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			try {
				json.put("success", "success");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			out.print(json.toString());
			out.flush();
			out.close();
			return;
		}// End of 設定住宿價錢與上下架。

		// 新增自訂服務
		if ("addCustSrvForm".equals(action)) {
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			res.setContentType("application/json");
			res.setCharacterEncoding("utf-8");
			JSONObject json = new JSONObject();
			PrintWriter out = res.getWriter();

			String name = req.getParameter("name").trim();
			String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,15}$";
			if (name == null || name.isEmpty()) {
				errorMsgs.put("name", "名稱請勿空白");
			} else if (!name.matches(nameReg)) {
				errorMsgs.put("name", "名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到15之間");
			}

			Integer price = null;
			try {
				String strprice = req.getParameter("price").trim();
				price = new Integer(strprice);
			} catch (NumberFormatException e) {
				errorMsgs.put("price", "價錢必須是數字");
			} catch (NullPointerException e) {
				errorMsgs.put("price", "請填價錢");
			}

			String info = req.getParameter("info").trim();
			String isOnsale = req.getParameter("isOnsale").trim();
			String petType = req.getParameter("petType").trim();
			String chargeType = req.getParameter("chargeType").trim();

			if (!errorMsgs.isEmpty()) {
				errorMsgs.put("isError", "y");
				json = new JSONObject(errorMsgs);
				out.print(json.toString());
				out.flush();
				out.close();
				return;
			}

			HttpSession session = req.getSession();
			StoreVO storeVO = (StoreVO) session.getAttribute("storeVO");
			ServiceListService serviceListSrv = new ServiceListService();			
			serviceListSrv.insert(name,info,price,storeVO.getId(),isOnsale,petType,chargeType);

			try {
				json.put("name", name);
				json.put("price", price);
				json.put("isOnsale", isOnsale);
				json.put("chargeType", chargeType);
				json.put("petType", petType);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			out.print(json.toString());
			out.flush();
			out.close();
			return;
		}

		// 設定接送價錢與上下架。
		if ("setPickSrvForm".equals(action)) {
			res.setContentType("application/json");
			res.setCharacterEncoding("utf-8");
			JSONObject json = new JSONObject();
			PrintWriter out = res.getWriter();

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			HttpSession session = req.getSession();
			ServiceListService serviceSrv = new ServiceListService();
			StoreVO storeVO = (StoreVO) session.getAttribute("storeVO");	
			
			
			Integer dogPrice = null;
			try {
				dogPrice = new Integer(req.getParameter("dogPrice").trim());
			} catch (NumberFormatException e) {
				errorMsgs.put("dogPrice", "價錢請填數字");
			}
			

			String dogIsOnsale = req.getParameter("dogIsOnsale").trim();

			String dogName = req.getParameter("dogName").trim();
			try {
				new Double(dogName);
			} catch (NumberFormatException e) {
				errorMsgs.put("dogName", "範圍請填數字");
			}
		

			if (!errorMsgs.isEmpty()) {
				errorMsgs.put("isError", "y");
				json = new JSONObject(errorMsgs);
				out.print(json.toString());
				out.flush();
				out.close();
				return;
			}

			// 開始修改新增
			// 修改
			try {
				serviceSrv.ServiceListUpdate(storeVO.getId(), "dog",
						"pick", dogPrice, dogIsOnsale);
				// 新增
			} catch (NullPointerException e) {				

				serviceSrv.ServiceListInsert(dogName, "",
						dogPrice, storeVO.getId(), dogIsOnsale,
						DOG_PETTYPE, PICK_TYPE);
				
			}
			try {
				json.put("dogPrice", dogPrice);
				json.put("dogIsOnsale", dogIsOnsale);
				json.put("dogName", dogName);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			try {
				json.put("success", "success");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			out.print(json.toString());
			out.flush();
			out.close();
			return;
		}// End of 設定接送價錢與上下架。

	}// End of doPost

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

}
