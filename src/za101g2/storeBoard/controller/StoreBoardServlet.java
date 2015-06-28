package za101g2.storeBoard.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import za101g2.filter.HTMLFilter;
import za101g2.storeBoard.model.StoreBoardService;
import za101g2.storeBoard.model.StoreBoardVO;

@WebServlet("/StoreBoardServlet")
public class StoreBoardServlet extends HttpServlet {
	
   
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setHeader("Pragma","no-cache");
	    res.setHeader("Cache-Control","no-cache");
	    res.setDateHeader("Expires", 0);
	   
	    String action = req.getParameter("action");
	   
	    if("addMsg".equals(action)){
	    	res.setCharacterEncoding("UTF-8");
	    	PrintWriter out = res.getWriter();
	    	res.setContentType("application/json");			
			JSONObject json = new JSONObject();
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");			
	    	Integer memberId = Integer.parseInt(req.getParameter("memberId"));
	    	Integer storeId = Integer.parseInt(req.getParameter("storeId"));	    	
	    	String comments = req.getParameter("comments");
	    	
	    	if(comments.trim().isEmpty()){
	    		return;
	    	}
	    	
	    	comments = HTMLFilter.filter(comments);
	    	
	    	StoreBoardService storeBoardSrv = new StoreBoardService();	    	
	    	StoreBoardVO storeBoardVO = storeBoardSrv.insert(memberId,storeId,comments);	    	
	    	try {
	    		json.put("id", storeBoardVO.getId());
				json.put("memberId", storeBoardVO.getMemberVO().getId());
				json.put("boardMsg", storeBoardVO.getBoardMsg());
				json.put("nickname", storeBoardVO.getMemberVO().getNickname());
				json.put("boardMsgDate",formater.format(storeBoardVO.getBoardMsgDate().getTime()));
			} catch (JSONException e) {
				e.printStackTrace();
			}
	    	out.print(json.toString());
			out.flush();
			out.close();
			return;
	    	
	    }//end of action
	    
	    if("updateMsg".equals(action)){
	    	res.setCharacterEncoding("utf-8");
	    	PrintWriter out = res.getWriter();
	    	Integer msgId = Integer.parseInt(req.getParameter("msgId"));
	    	StoreBoardService storeBoardSrv = new StoreBoardService();
	    	StoreBoardVO storeBoardVO =storeBoardSrv.findByPrimaryKey(msgId);
	    	storeBoardVO.setIsDelete("y");
	    	storeBoardSrv.update(storeBoardVO);
	    	out.print("success");
	    	out.flush();
	    	return;
	    }
	    
	    if("getMoreMsg".equals(action)){
	    	
	    	res.setCharacterEncoding("UTF-8");
	    	PrintWriter out = res.getWriter();
	    	res.setContentType("application/json");			
			JSONObject json = new JSONObject();
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
	    	Integer storeId = Integer.parseInt(req.getParameter("storeId"));	    	
	    	Integer msgId = Integer.parseInt(req.getParameter("msgId"));
	    	
	    	StoreBoardService storeBoardSrv = new StoreBoardService();
	    	List<StoreBoardVO> list = storeBoardSrv.getMoreUndelete(storeId, msgId, 5);
	    	Object[] o = new Object[list.size()];
	    	for(int i =0;i<list.size();i++){
	    		Map StoreBoardMap = new HashMap();
	    		StoreBoardMap.put("msgId", list.get(i).getId());
	    		StoreBoardMap.put("memberId", list.get(i).getMemberVO().getId());
	    		StoreBoardMap.put("boardMsg", list.get(i).getBoardMsg());
	    		StoreBoardMap.put("nickname", list.get(i).getMemberVO().getNickname());
	    		StoreBoardMap.put("boardMsgDate", formater.format(list.get(i).getBoardMsgDate()));
	    		o[i]= StoreBoardMap;
	    	}
	    	try {
				json.put("msgList",o);
			
			} catch (JSONException e) {				
				e.printStackTrace();
			}
	    	out.print(json.toString());
			out.flush();
			out.close();
	    	return;
	    }
	}//end of doPost
}
