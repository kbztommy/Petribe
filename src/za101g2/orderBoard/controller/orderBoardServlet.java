package za101g2.orderBoard.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.mail.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import za101g2.member.model.MemberService;
import za101g2.member.model.MemberVO;
import za101g2.orderBoard.model.OrderBoardService;
import za101g2.orderBoard.model.OrderBoardVO;
import za101g2.orderList.model.OrderListService;
import za101g2.orderList.model.OrderListVO;
import za101g2.serviceList.model.*;
import za101g2.store.model.*;
import za101g2.util.OrderRequestListener;
import za101g2.util.OrderSessionListener;
import za101g2.util.Send;

@WebServlet("/orderBoardServlet")
public class orderBoardServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);		
	}

	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setHeader("Pragma","no-cache");
	    res.setHeader("Cache-Control","no-cache");
	    res.setDateHeader("Expires", 0);
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		res.setCharacterEncoding("UTF-8");
		if("startOrder".equals(action)){
			Integer otherStoreId = Integer.parseInt(req.getParameter("otherStoreId"));
			
			Map<String,Object> storeInfoMap = new StoreService().getStoreInfoMap(otherStoreId);
			
			req.setAttribute("otherStoreVO", storeInfoMap.get("otherStoreVO"));
			req.setAttribute("curStoreCalendar", storeInfoMap.get("curStoreCalendar"));
			req.setAttribute("nextStoreCalendar", storeInfoMap.get("nextStoreCalendar"));
			req.setAttribute("afterNextStoreCalendar", storeInfoMap.get("afterNextStoreCalendar"));			
			req.setAttribute("pickSrv", storeInfoMap.get("pickSrv"));
			
			RequestDispatcher successView = req.getRequestDispatcher("/za101g2/front/orderBoard/OrderBoardIndex.jsp"); 
			successView.forward(req, res);			
		}
		
		if("startBook".equals(action)){
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try{
				Integer custPetQty = Integer.parseInt(req.getParameter("custPetQty"));
				String pickSrv = req.getParameter("pickSrv");				
				String startDateStr = req.getParameter("startDate");
				String endDateStr = req.getParameter("endDate");
				if(startDateStr.isEmpty()){
					errorMsgs.put("startDateStr", "請選擇開始日期");
				}else if(endDateStr.isEmpty()){
					errorMsgs.put("endDateStr", "請重新選擇日期");
				}
				Integer otherStoreId =Integer.parseInt(req.getParameter("otherStoreId"));				
				String custAddress =null;
				if("y".equals(pickSrv)){
					custAddress =req.getParameter("custAddress");
					if(custAddress.isEmpty()){
						errorMsgs.put("custAddress", "請輸入接送地點");
					}
				}else{
					custAddress="";
				}
				
				if (!errorMsgs.isEmpty()) {
					Map<String,Object> storeInfoMap = new StoreService().getStoreInfoMap(otherStoreId);
					
					req.setAttribute("otherStoreVO", storeInfoMap.get("otherStoreVO"));
					req.setAttribute("curStoreCalendar", storeInfoMap.get("curStoreCalendar"));
					req.setAttribute("nextStoreCalendar", storeInfoMap.get("nextStoreCalendar"));
					req.setAttribute("afterNextStoreCalendar", storeInfoMap.get("afterNextStoreCalendar"));			
					req.setAttribute("pickSrv", storeInfoMap.get("pickSrv"));
					RequestDispatcher failureView = req.getRequestDispatcher("/za101g2/front/orderBoard/OrderBoardIndex.jsp");
					failureView.forward(req, res);
					return;
				}
				
				
				StoreService storeSrv = new StoreService();
				ServiceListService serviceListSrv = new ServiceListService();
				StoreVO otherStoreVO = storeSrv.getOneStore(otherStoreId);
				
				ServiceListVO requiredDogSrv = serviceListSrv.getService(otherStoreVO.getId(), "dog", "required");
				ServiceListVO requiredCatSrv = serviceListSrv.getService(otherStoreVO.getId(), "cat", "required");
				List<ServiceListVO> allcustService = serviceListSrv.getAllOnsaleCust(otherStoreVO.getId());
				List<String> boardDayList = generateCal(startDateStr,endDateStr);
				
				req.setAttribute("custPetQty", custPetQty);
				req.setAttribute("pickSrv", pickSrv);	
				req.setAttribute("startDateStr", startDateStr);
				req.setAttribute("endDateStr", endDateStr);
				req.setAttribute("custAddress", custAddress);	
				req.setAttribute("otherStoreVO", otherStoreVO);
				req.setAttribute("requiredDogSrv", requiredDogSrv);
				req.setAttribute("requiredCatSrv", requiredCatSrv);
				req.setAttribute("allcustService", allcustService);
				req.setAttribute("startCal", boardDayList);			
				RequestDispatcher successView = req.getRequestDispatcher("/za101g2/front/orderBoard/OrderListIndex.jsp");
				successView.forward(req, res);
			
			}catch(Exception e){
				errorMsgs.put("Exception",e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/za101g2/front/orderBoard/OrderBoardIndex.jsp");
				failureView.forward(req, res);
			}
		}//end of if action
		
		if("insertOrderValidation".equals(action)){
			res.setContentType("application/json");
			JSONObject json = new JSONObject();				
			PrintWriter out = res.getWriter();
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String[] petNames = req.getParameterValues("petName");
			String petNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			Set<String> petNameSet = new LinkedHashSet<String>();
			for(String petName:petNames){
				petNameSet.add(petName);
				petName = petName.trim();
				if (petName.trim().length() == 0) {
					errorMsgs.put("petName","寵物名稱: 請勿空白");
				} else if(!petName.trim().matches(petNameReg)) {
					errorMsgs.put("petName","寵物名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
			}
			if(petNameSet.size()!=petNames.length)
				errorMsgs.put("petName", "寵物名稱不可重複");
			if (!errorMsgs.isEmpty()) {				
				try {								
					json.put("errorMsgs", errorMsgs);
					out.print(json.toString());
					out.flush();
					out.close();						
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return;
			}
			
			out.print(json.toString());
			out.flush();
			out.close();
			return;
		}//end of if action
		
		if("insertOrder".equals(action)){
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try{
				HttpSession session = req.getSession();
				MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
				Integer memId = memberVO.getId();
				Integer otherStoreId = Integer.parseInt(req.getParameter("otherStoreId"));								
				String[] petNames = req.getParameterValues("petName");			
				
				
				String[] petTypes = req.getParameterValues("petType");
				String[] custOrderArray = req.getParameterValues("custOrderList");	
				String startDateStr = req.getParameter("startDateStr");
				String endDateStr = req.getParameter("endDateStr");
				String custAddress = req.getParameter("custAddress");
				List<String> boardDayList = generateCal(startDateStr,endDateStr);
				
				Map<String,String> nameMap = new LinkedHashMap<String,String>();			
				for(int i=0;i<petNames.length;i++){
					String str = "pet"+(i+1);
					nameMap.put(str, petNames[i].trim());				
				}
				
				OrderBoardService orderBoardSrv = new OrderBoardService();
				OrderBoardVO orderBoardVO = orderBoardSrv.insertOrder(memId, otherStoreId, startDateStr, endDateStr, custAddress, custOrderArray, nameMap, petTypes, boardDayList, petNames);
				req.setAttribute("orderBoardVO", orderBoardVO);				
				
				session.setMaxInactiveInterval(60*30);
				session.setAttribute("BoardVOListener", new OrderSessionListener(orderBoardVO,petNames.length,boardDayList));
				
				
				RequestDispatcher successView = req.getRequestDispatcher("/za101g2/front/orderBoard/Payment.jsp");
				successView.forward(req, res);
			}catch(Exception e){
				errorMsgs.put("Exception",e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/za101g2/front/index.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("paymentValidation".equals(action)){			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			OrderBoardService orderBoardSrv = new OrderBoardService();			
			Integer orderId = Integer.parseInt(req.getParameter("orderId"));
			try{
				String[] cridNos = req.getParameterValues("cridNo");
				for(String cridNo : cridNos){
					if(cridNo.trim().length()<4){
						errorMsgs.put("cridNo", "請輸入正確信用卡卡號");
					}
				}
				
				String nameLastNo = req.getParameter("nameLastNo");
				if(nameLastNo.trim().isEmpty()){
					errorMsgs.put("nameLastNo", "請勿空白");					
				}
				String nameLastNoReg = "^[(0-9)]{3}$";
				if(!nameLastNo.trim().matches(nameLastNoReg)){
					errorMsgs.put("nameLastNo", "請輸入數字");
				}
				
				String expireDate = req.getParameter("expireDate");
				if(expireDate ==null || expireDate.trim().isEmpty()){
					errorMsgs.put("expireDate", "請選擇信用卡有效日期");
				}			
				
				
				if(!errorMsgs.isEmpty()){					
					OrderBoardVO orderBoardVO = orderBoardSrv.getOne(orderId);
					req.setAttribute("errorMsgs", errorMsgs);
					req.setAttribute("orderBoardVO", orderBoardVO);
					RequestDispatcher failView = req.getRequestDispatcher("/za101g2/front/orderBoard/Payment.jsp");
					failView.forward(req, res);
					return;
				}
				
				OrderBoardVO orderBoardVO =orderBoardSrv.getOne(orderId);
				orderBoardVO.setStatus("1");
				orderBoardSrv.update(orderBoardVO);
				OrderBoardVO orderBoardVOed = orderBoardSrv.getOne(orderId);
				
//				簡訊通知訂單成立
				if("1".equals(orderBoardVOed.getStatus())){
					Send se = new Send();
					String[] tel ={orderBoardVOed.getMemberVO().getPhone()};//測試時為我的手機，上線時要改成該訂單會員的手機號碼orderBoardVOed.getMemberVO().getPhone();
				 	String message = messageContext(orderBoardVOed);
				 	se.sendMessage(tel , message);
				}
				//簡訊通知住宿服務即將開始，如果明天開始寄養且現在時間超過中午12點				
				Date today = new Date();
				SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(today);
				calendar.add(Calendar.DATE,1);
				int curHour = calendar.get(Calendar.HOUR_OF_DAY);
				String tomorrow =formater.format(calendar.getTime());
				String strStartDate = formater.format(orderBoardVOed.getStartDate());
				if(tomorrow.equals(strStartDate) && curHour>12){
					Send se = new Send();
					String message ="親愛的 "+orderBoardVOed.getMemberVO().getLastname()+orderBoardVOed.getMemberVO().getFirstname()+"，您的寄養服務"
	   	            		 +"即將於明日("+formater.format(orderBoardVOed.getStartDate())+")開始";
					String[] tel ={orderBoardVOed.getMemberVO().getPhone()};//測試時為我的手機，上線時要改成該訂單會員的手機號碼orderBoardVOed.getMemberVO().getPhone();
					se.sendMessage(tel , message);					
				}
				HttpSession session = req.getSession();
				session.setAttribute("memberVO", orderBoardVO.getMemberVO());
				req.setAttribute("orderBoardVO", orderBoardVO);
				RequestDispatcher successView = req.getRequestDispatcher("/za101g2/front/orderBoard/CustOrderList.jsp");
				successView.forward(req, res);
				
			}catch(Exception e){
				OrderBoardVO orderBoardVO = orderBoardSrv.getOne(orderId);
				req.setAttribute("errorMsgs", errorMsgs);
				req.setAttribute("orderBoardVO", orderBoardVO);
				RequestDispatcher failView = req.getRequestDispatcher("/za101g2/front/orderBoard/Payment.jsp");
				failView.forward(req, res);
				return;
			}
		}//end of if action
		
		if("OneCustOrderInfo".equals(action)){
			Integer orderId = Integer.parseInt(req.getParameter("orderId"));
			OrderBoardService orderBoardSrv = new OrderBoardService();
			OrderBoardVO orderBoardVO = orderBoardSrv.getOne(orderId);		
//			Integer dayofBoard =1+(int)((orderBoardVO.getEndDate().getTime()-orderBoardVO.getStartDate().getTime())/(24*60*60*1000));
			req.setAttribute("orderBoardVO", orderBoardVO);	
//			req.setAttribute("dayofBoard", dayofBoard);	
			Integer custSrvPrice = 0;
			for(OrderListVO orderListVO : orderBoardVO.getOrderLists()){
				if("perTime".equals(orderListVO.getServiceListVO().getChargeType()))
				custSrvPrice += orderListVO.getServiceListVO().getPrice();
			}
			req.setAttribute("custSrvPrice", custSrvPrice);
			RequestDispatcher successView = req.getRequestDispatcher("/za101g2/front/orderBoard/OneCustOrderInfo.jsp");
			successView.forward(req, res);
			return;
		}//end of if action
		
		if("OneStoreOrderInfo".equals(action)){
			Integer orderId = Integer.parseInt(req.getParameter("orderId"));
			OrderBoardService orderBoardSrv = new OrderBoardService();
			OrderBoardVO orderBoardVO = orderBoardSrv.getOne(orderId);
			
			Integer custSrvPrice = 0;
			for(OrderListVO orderListVO : orderBoardVO.getOrderLists()){
				if("perTime".equals(orderListVO.getServiceListVO().getChargeType()))
				custSrvPrice += orderListVO.getServiceListVO().getPrice();
			}
			req.setAttribute("custSrvPrice", custSrvPrice);
			req.setAttribute("orderBoardVO", orderBoardVO);			
			RequestDispatcher successView = req.getRequestDispatcher("/za101g2/front/orderBoard/OneStoreOrderInfo.jsp");
			successView.forward(req, res);
			return;
		}//end of if action
		
		if("addOnService".equals(action)){
			Integer orderId = Integer.parseInt(req.getParameter("orderId"));
			OrderBoardService orderBoardSrv = new OrderBoardService();
			OrderBoardVO orderBoardVO = orderBoardSrv.getOne(orderId);
			
			req.setAttribute("orderBoardVO", orderBoardVO);
			
			RequestDispatcher nextView = req.getRequestDispatcher("/za101g2/front/onService/addOnService.jsp");
			nextView.forward(req, res);
		}//end of if action	
		
		if("custUpdateStatus".equals(action)){
			String status = req.getParameter("status");
			Integer memId = Integer.parseInt(req.getParameter("memId"));
			ServletContext context =  getServletContext();
			res.setContentType("utf-8");
			PrintWriter out = res.getWriter();
			Integer orderId = Integer.parseInt(req.getParameter("orderId"));
			OrderBoardService orderBoardSrv = new OrderBoardService();
			OrderBoardVO orderBoardVO = orderBoardSrv.getOne(orderId);
			if(memId.equals(orderBoardVO.getMemberVO().getId())){
				if("4".equals(status))
					status="5";
				if("6".equals(status))
					orderBoardVO.setIsReport("y");
				orderBoardVO.setStatus(status);
				orderBoardSrv.update(orderBoardVO);
				String StrStatus = ((HashMap<String,String>)context.getAttribute("orderBoardMapforC")).get(status);
				out.print(StrStatus);
				out.flush();			
			}
			return;
		}//end of action
		
		if("storeUpdateStatus".equals(action)){
			String status = req.getParameter("status");
			Integer storeId = Integer.parseInt(req.getParameter("storeId"));
			ServletContext context =  getServletContext();
			res.setContentType("utf-8");
			PrintWriter out = res.getWriter();
			Integer orderId = Integer.parseInt(req.getParameter("orderId"));
			OrderBoardService orderBoardSrv = new OrderBoardService();
			OrderBoardVO orderBoardVO = orderBoardSrv.getOne(orderId);
			
			if(storeId.equals(orderBoardVO.getStoreVO().getId())){				
				orderBoardVO.setStatus(status);
				orderBoardSrv.update(orderBoardVO);
				String StrStatus = ((HashMap<String,String>)context.getAttribute("orderBoardMapforS")).get(status);
				out.print(StrStatus);
				out.flush();			
			}
			return;
		}//end of action
		
		if("continuePay".equals(action)){			
			HttpSession session = req.getSession();
			Integer memId = ((MemberVO)session.getAttribute("memberVO")).getId();
			Integer orderId = Integer.parseInt(req.getParameter("orderId"));			
			OrderBoardService orderBoardSrv = new OrderBoardService();
			OrderBoardVO orderBoardVO = orderBoardSrv.getOne(orderId);
			if(orderBoardVO.getMemberVO().getId().equals(memId)){
				req.setAttribute("orderBoardVO", orderBoardVO);			
				RequestDispatcher successView = req.getRequestDispatcher("/za101g2/front/orderBoard/Payment.jsp");
				successView.forward(req, res);
			}else{
				return;
			}
		}//end of action

		
		if("delOrder".equals(action)){
			HttpSession session = req.getSession();
			Integer memId = ((MemberVO)session.getAttribute("memberVO")).getId();
			Integer orderId = Integer.parseInt(req.getParameter("orderId"));			
			OrderBoardService orderBoardSrv = new OrderBoardService();
			OrderListService OrderListSrv = new OrderListService();
			OrderBoardVO orderBoardVO = orderBoardSrv.getOne(orderId);
			Integer petNumber =  OrderListSrv.getPetNames(orderId).size();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String startDateStr = formatter.format(new Date(orderBoardVO.getStartDate().getTime()));
			String endDateStr = formatter.format(new Date(orderBoardVO.getEndDate().getTime()));
			List<String> boardDayList = generateCal(startDateStr,endDateStr);
			if(orderBoardVO.getMemberVO().getId().equals(memId)){
				req.setAttribute("storeId", orderBoardVO.getStoreVO().getId());
				if(session.getAttribute("BoardVOListener")!=null){				
					session.removeAttribute("BoardVOListener");
				}else{
					orderBoardSrv.deleteWithCal(orderId,boardDayList,petNumber);
				}
					
				RequestDispatcher successView = req.getRequestDispatcher("/za101g2/front/orderBoard/cancleOrder.jsp");
				successView.forward(req, res);
			}else{
				return;
			}
		}//end of action
		
		if("listOrderBoardMana".equals(action)){
			OrderBoardService orderBoardSrv = new OrderBoardService();
			List<OrderBoardVO> orderReportedList = orderBoardSrv.getAllReport();
			req.setAttribute("orderReportedList", orderReportedList);
			RequestDispatcher successView = req.getRequestDispatcher("/za101g2/back/store/orderManage.jsp");
			successView.forward(req, res);
			return;
		}//end of action
		
		if("orderInfoForManage".equals(action)){
			Integer orderId = Integer.parseInt(req.getParameter("orderId"));
			OrderBoardService orderBoardSrv = new OrderBoardService();
			OrderBoardVO orderBoardVO = orderBoardSrv.getOne(orderId);
			
			Integer custSrvPrice = 0;
			for(OrderListVO orderListVO : orderBoardVO.getOrderLists()){
				if("perTime".equals(orderListVO.getServiceListVO().getChargeType()))
				custSrvPrice += orderListVO.getServiceListVO().getPrice();
			}
			req.setAttribute("custSrvPrice", custSrvPrice);
			req.setAttribute("orderBoardVO", orderBoardVO);			
			RequestDispatcher successView = req.getRequestDispatcher("/za101g2/back/store/orderInfoForManage.jsp");
			successView.forward(req, res);
			return;
		}//end of action
		
		if("updateBystaff".equals(action)){
			Integer orderId = Integer.parseInt(req.getParameter("orderId"));
			String status = req.getParameter("status");
			OrderBoardService orderBoardSrv = new OrderBoardService();
			OrderBoardVO orderBoardVO = orderBoardSrv.getOne(orderId);
			if("6".equals(orderBoardVO.getStatus()) && "y".equals(orderBoardVO.getIsReport())){
				orderBoardVO.setStatus(status);
				orderBoardSrv.update(orderBoardVO);
			}			
			List<OrderBoardVO> orderReportedList = orderBoardSrv.getAllReport();
			req.setAttribute("orderReportedList", orderReportedList);
			RequestDispatcher successView = req.getRequestDispatcher("/za101g2/back/store/orderManage.jsp");
			successView.forward(req, res);
					
		}//end of action
		
		if("custReportStatus".equals(action)){
			res.setContentType("utf-8");
			PrintWriter out = res.getWriter();
			String errorMsg = null;
			String status = req.getParameter("status");			
			String comments = req.getParameter("comments");
			if(comments == null||comments.trim().isEmpty()){
				errorMsg="檢舉原因不可空白";
				out.print(errorMsg);
				out.flush();
				return;
			}
			
			Integer memId = Integer.parseInt(req.getParameter("memId"));
			ServletContext context =  getServletContext();
			
			Integer orderId = Integer.parseInt(req.getParameter("orderId"));
			OrderBoardService orderBoardSrv = new OrderBoardService();
			OrderBoardVO orderBoardVO = orderBoardSrv.getOne(orderId);
			
			
			if(memId.equals(orderBoardVO.getMemberVO().getId())&&"6".equals(status)){
				orderBoardVO.setComments(comments);
				orderBoardVO.setIsReport("y");
				orderBoardVO.setStatus(status);
				orderBoardSrv.update(orderBoardVO);
				String StrStatus = ((HashMap<String,String>)context.getAttribute("orderBoardMapforC")).get(status);
				out.print(StrStatus);
				out.flush();			
			}
			return;
		}//end of action
		
		if("StoreOrderList".equals(action)){
			HttpSession session = req.getSession();
			Integer storeId =  ((StoreVO)session.getAttribute("storeVO")).getId();
			OrderBoardService orderSrv = new OrderBoardService();
			List<OrderBoardVO> orderBoardList=null;
			String status=req.getParameter("status");
			
			if(status==null||status.isEmpty()){
				orderBoardList = orderSrv.getStoreOrder(storeId);
			}else{				
				orderBoardList = orderSrv.getStoreOrderWithStatus(storeId, status);
			}
		
			req.setAttribute("orderBoardList", orderBoardList);
			req.setAttribute("status", status);
			RequestDispatcher nextView = req.getRequestDispatcher("/za101g2/front/orderBoard/StoreOrderList.jsp");
			nextView.forward(req, res);
		}//end of action
		
	}
	
	private String messageContext(OrderBoardVO orderBoardVOed) {
		SimpleDateFormat dateformater = new SimpleDateFormat("yyyy-MM-dd");
		String memberName= orderBoardVOed.getMemberVO().getFirstname() + orderBoardVOed.getMemberVO().getLastname();
		String orderId = String.valueOf(orderBoardVOed.getId());
		String orderTotal = String.valueOf(orderBoardVOed.getTotal());				
		String startDate = dateformater.format(new Date(orderBoardVOed.getStartDate().getTime()));
		String endDate = dateformater.format(new Date(orderBoardVOed.getStartDate().getTime()+24*60*60*1000));
		String message ="親愛的 "+memberName+"您的訂單(編號"+orderId+")已完成繳費，金額為"+orderTotal+"。入住時間為"
		+startDate+"，結束時間為"+endDate+"。";
		return message;
		
	}

	
	private List<String> generateCal(String startDateStr,String endDateStr)  {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate=null;
		Date endDate=null;
		try {
			startDate = formatter.parse(startDateStr);
			endDate = formatter.parse(endDateStr);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		int countDay = (int)(endDate.getTime()-startDate.getTime())/(24*60*60*1000)+1;
		Calendar startCal = GregorianCalendar.getInstance();
		startCal.setTime(startDate);
		List<String> boardDayList = new ArrayList<String>(); 
		
		for(int i=0;i<countDay;i++){
			boardDayList.add(formatter.format(startCal.getTime()));
			startCal.add(Calendar.DATE, 1);
		}
		return boardDayList;
	}

}
