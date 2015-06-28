package za101g2.onService.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import za101g2.onService.model.*;
import za101g2.orderBoard.model.*;

import java.util.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
@WebServlet("/OnServiceServlet")
public class OnServiceServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		res.setHeader("Pragma","no-cache");
	    res.setHeader("Cache-Control","no-cache");
	    res.setDateHeader("Expires", 0);
	    String action = req.getParameter("action");
		if("addOnservice".equals(action)){
			
			String comments = req.getParameter("comments");
			Integer orderId = Integer.parseInt(req.getParameter("orderId"));
			
			List<byte[]> picList = new ArrayList<byte[]>();
			InputStream ins=null;
			try{
				for(Part p : req.getParts()){
					if(p.getName().startsWith("pic")){					
						ins = p.getInputStream();
						byte[] data= new byte[ins.available()];
						if(ins.available()>0){
							ins.read(data);
							picList.add(data);
						}						
					}				
				}
 			}catch(IOException e){
 				e.printStackTrace();
 			}finally{
 				if(ins!=null){
 					try{
 						ins.close();
 					}catch(IOException e){
 						e.printStackTrace();
 					}
 				}
 			}
			OrderBoardService orderBoardSrv = new OrderBoardService();
			OrderBoardVO orderBoardVO = orderBoardSrv.getOne(orderId);	
			OnServiceService onServiceSrv = new OnServiceService();
			onServiceSrv.insertWithPhoto(picList, orderBoardVO, comments);
			orderBoardVO = orderBoardSrv.getOne(orderId);
			req.setAttribute("orderBoardVO", orderBoardVO);
			RequestDispatcher successView = req.getRequestDispatcher("/orderBoardServlet?action=OneStoreOrderInfo&orderId="+orderBoardVO.getId());
			successView.forward(req, res);
		}//end of action
		
		if("OneOnService".equals(action)){
			Integer onServId = Integer.parseInt(req.getParameter("onServId"));
			OnServiceService onServiceSrv = new OnServiceService();
			OnServiceVO onServiceVO = onServiceSrv.findByPrimaryKey(onServId);
			req.setAttribute("onServiceVO", onServiceVO);
			
			RequestDispatcher nextView = req.getRequestDispatcher("/za101g2/front/onService/OneOnServiceInfo.jsp");
			nextView.forward(req, res);
			return;
		}//end of action
		
		if("onServiceForMana".equals(action)){
			Integer onServId = Integer.parseInt(req.getParameter("onServId"));
			OnServiceService onServiceSrv = new OnServiceService();
			OnServiceVO onServiceVO = onServiceSrv.findByPrimaryKey(onServId);
			req.setAttribute("onServiceVO", onServiceVO);
			
			RequestDispatcher nextView = req.getRequestDispatcher("/za101g2/back/store/onServiceForMana.jsp");
			nextView.forward(req, res);
			return;
		}//end of action
	}
}
