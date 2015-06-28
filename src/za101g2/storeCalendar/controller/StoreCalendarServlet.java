package za101g2.storeCalendar.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;

import za101g2.store.model.StoreVO;
import za101g2.storeCalendar.model.*;

@WebServlet("/StoreCalendarServlet")
public class StoreCalendarServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setHeader("Pragma","no-cache");
	    res.setHeader("Cache-Control","no-cache");
	    res.setDateHeader("Expires", 0);
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		PrintWriter out = res.getWriter();
		String action = req.getParameter("action");		
		
		if("insertCalendar".equals(action)){
			HttpSession session = req.getSession();
			List<StoreCalendarVO> storeCalendarVOList = new ArrayList<StoreCalendarVO>();
			
			StoreVO storeVO = (StoreVO) session.getAttribute("storeVO");			
			int year = Integer.parseInt(req.getParameter("year"));
			String month = String.valueOf((Integer.parseInt(req.getParameter("month"))+1));
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.set(year,Integer.parseInt(month)-1,1);
			if (month.length()<2)
				month = "0"+month;
			String date = year+"-"+month+"-";
			int numberOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			StoreCalendarService storeCalendarSrv = new StoreCalendarService();				
			for(int i=1;i<=numberOfMonth;i++){
				String day="";
				if(i<10)
					day = "0"+i;
				else
					day += i;
				Integer maxQuantitly = Integer.parseInt(req.getParameter("day"+i));
				StoreCalendarVO storeCalendarVO = new StoreCalendarVO();
				storeCalendarVO.setStoreVO(storeVO);				
				storeCalendarVO.setServiceDate(java.sql.Date.valueOf(date+day));
				storeCalendarVO.setCurQuantitly(0);
				storeCalendarVO.setMaxQuantitly(maxQuantitly);
				storeCalendarVOList.add(storeCalendarVO);			
			}
			storeCalendarSrv.insertMonth(storeCalendarVOList);
			List<StoreCalendarVO> monthCalendarList = storeCalendarSrv.getMonthCalendar(storeVO.getId(),year,Integer.parseInt(month));
		    req.setAttribute("monthCalendarList",monthCalendarList);
			
			String url = "/za101g2/front/storeCalendar/oneCalendar.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}//end of insertCalendar
		
		if("toAddCalendar".equals(action)){
			int year = Integer.parseInt(req.getParameter("year"));
			int month = Integer.parseInt(req.getParameter("month"));			
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.set(year,month,1);
			int numberOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			int firstDateInMonth = calendar.get(Calendar.DAY_OF_WEEK);
			int countOfMonth = numberOfMonth + firstDateInMonth-1;			
			req.setAttribute("numberOfMonth", numberOfMonth);
			req.setAttribute("firstDateInMonth", firstDateInMonth);
			req.setAttribute("countOfMonth", countOfMonth);
			String url = "/za101g2/front/storeCalendar/addCalendar.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		
		if("updateOneCalendar".equals(action)){
			StoreVO storeVO = (StoreVO) req.getSession().getAttribute("storeVO");
			int year = Integer.parseInt(req.getParameter("year"));
			int month = Integer.parseInt(req.getParameter("month"))+1;
			int date = Integer.parseInt(req.getParameter("date"));
			int maxQty = Integer.parseInt(req.getParameter("maxQty"));	
			int curQty = Integer.parseInt(req.getParameter("curQty"));			
			String day = ""+year+"-"+month+"-"+date;
						
			StoreCalendarService storeCalendarSrv = new StoreCalendarService();
			storeCalendarSrv.update(storeVO.getId(), day, maxQty, curQty);
			out.print(maxQty);
		}
		
		if("listOneCalendar".equals(action)){
			int year = Integer.parseInt(req.getParameter("year"));
			int month = Integer.parseInt(req.getParameter("month"));
			Integer storeId = Integer.parseInt(req.getParameter("storeId"));
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.set(year,month,1);
			int numberOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			int firstDateInMonth = calendar.get(Calendar.DAY_OF_WEEK);
			
			StoreCalendarService StoreCalendarSrv = new StoreCalendarService();
		    List<StoreCalendarVO> monthCalendarList = StoreCalendarSrv.getMonthCalendar(storeId,year,month+1);
		    req.setAttribute("monthCalendarList",monthCalendarList);
		    String url = "/za101g2/front/storeCalendar/oneCalendar.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			
		}
		
	}

}
