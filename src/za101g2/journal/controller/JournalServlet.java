package za101g2.journal.controller;

import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import org.json.JSONException;
import org.json.JSONObject;

import za101g2.journal.model.JournalService;
import za101g2.journal.model.JournalVO;
import za101g2.journalAssess.model.JournalAssessService;
import za101g2.journalAssess.model.JournalAssessVO;
import za101g2.journalBoard.model.JournalBoardService;
import za101g2.journalBoard.model.JournalBoardVO;
import za101g2.journalLinkedPet.model.JournalLinkedPetService;
import za101g2.journalLinkedPet.model.JournalLinkedPetVO;
import za101g2.journalReport.model.JournalReportService;
import za101g2.member.model.MemberService;
import za101g2.member.model.MemberVO;
import za101g2.photo.model.PhotoService;
import za101g2.photo.model.PhotoVO;
import za101g2.photoLinkedPet.model.PhotoLinkedPetService;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class JournalServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

        if ("insert".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");

			try {
				String title = req.getParameter("title").trim();
				if(title.length()==0){
					errorMsgs.add("請輸入標題<br>");
				}
				
				String article = req.getParameter("article").trim();
				if(article.length()==0){
					errorMsgs.add("請輸入內文<br>");
				}
				
				String str2 = req.getParameter("petId");
				Integer journalPetId = new Integer(str2);

				Integer memId = new Integer(memberVO.getId());
				String status = "0";
				String isPublic = "y";

				JournalVO journalVO = new JournalVO();
				journalVO.setTitle(title);
				journalVO.setArticle(article);
				journalVO.setMemId(memId);
				journalVO.setStatus(status);
				journalVO.setIsPublic(isPublic);
				req.setAttribute("journalVO", journalVO);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("journalPetId", journalPetId);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/za101g2/front/journal/addJournal.jsp");
					failureView.forward(req, res);
					return;
				}
				
				JournalService journalSvc = new JournalService();
				Integer journalId = journalSvc.addJournalReId(title, article, memId, status, isPublic);
				
				if(journalPetId != 0){
					JournalLinkedPetService journalLinkedPetSvc = new JournalLinkedPetService();
					journalLinkedPetSvc.addJournalLinkedPet(journalId, journalPetId);
				}
				
				JournalVO journalVO2 = journalSvc.getOneJournal(journalId);

				JournalBoardService journalBoardSvc = new JournalBoardService();
				List<JournalBoardVO> boardVOList = journalBoardSvc.findByJournalId(journalId);

				JournalAssessService assessSvc = new JournalAssessService();
				List<JournalAssessVO> assessVOList = assessSvc.getAssessByAJournal(journalId);
				
				MemberService memberSvc = new MemberService();
				MemberVO journalMemberVO = memberSvc.getOneMember(journalVO.getMemId());
				
				req.setAttribute("journalVO", journalVO2);
				req.setAttribute("boardVOList", boardVOList);
				req.setAttribute("assessVOList", assessVOList);
				req.setAttribute("journalMemberVO", journalMemberVO);
				
				String url = "/za101g2/front/journal/journal.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);				
				
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("文章文字超過上限(含HTML標籤)");
//				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/journal/addJournal.jsp");
				failureView.forward(req, res);
			}
		}
       
		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String str = req.getParameter("id");
				Integer id = new Integer(str);

				JournalService journalSvc = new JournalService();
				JournalVO journalVO = journalSvc.getOneJournal(id);

				JournalBoardService journalBoardSvc = new JournalBoardService();
				List<JournalBoardVO> boardVOList = journalBoardSvc.findByJournalId(id);

				JournalAssessService assessSvc = new JournalAssessService();
				List<JournalAssessVO> assessVOList = assessSvc.getAssessByAJournal(id);
				
				MemberService memberSvc = new MemberService();
				MemberVO journalMemberVO = memberSvc.getOneMember(journalVO.getMemId());
				
				req.setAttribute("journalVO", journalVO);
				req.setAttribute("boardVOList", boardVOList);
				req.setAttribute("assessVOList", assessVOList);
				req.setAttribute("journalMemberVO", journalMemberVO);
				String url = "/za101g2/front/journal/journal.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/memberHome/myHome.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOneForUpdate".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				Integer journalId = new Integer(req.getParameter("journalId"));
				
				JournalService journalSvc = new JournalService();
				JournalVO journalVO = journalSvc.getOneJournal(journalId);
								
				req.setAttribute("journalVO", journalVO);
				String url = "/za101g2/front/journal/update_journal_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/journal/journal.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			
			JournalVO errJournalVO = new JournalVO();
			try {
				String journalId = req.getParameter("journalId").trim();
				Integer id = new Integer(journalId);
				
				String title = req.getParameter("title").trim();
				if(title.length()==0){
					errorMsgs.add("請輸入標題<br>");
				}
				
				String article = req.getParameter("article").trim();
				if(article.length()==0){
					errorMsgs.add("請輸入內文<br>");
				}
				
				Integer memId = new Integer(memberVO.getId());
				String status = "0";
				String isPublic = "y";

				JournalVO journalVO = new JournalVO();
				journalVO.setId(id);
				journalVO.setTitle(title);
				journalVO.setArticle(article);
				journalVO.setMemId(memId);
				journalVO.setStatus(status);
				journalVO.setIsPublic(isPublic);
				errJournalVO = journalVO;
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("journalVO", journalVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/za101g2/front/journal/update_journal_input.jsp");
					failureView.forward(req, res);
					return;
				}
				
				JournalService journalSvc = new JournalService();
				journalVO = journalSvc.updateJournal(id, title, article, memId, status, isPublic);
				
				JournalVO journalVO2 = journalSvc.getOneJournal(id);

				JournalBoardService journalBoardSvc = new JournalBoardService();
				List<JournalBoardVO> boardVOList = journalBoardSvc.findByJournalId(id);

				JournalAssessService assessSvc = new JournalAssessService();
				List<JournalAssessVO> assessVOList = assessSvc.getAssessByAJournal(id);
				
				MemberService memberSvc = new MemberService();
				MemberVO journalMemberVO = memberSvc.getOneMember(journalVO.getMemId());
				
				req.setAttribute("journalVO", journalVO2);
				req.setAttribute("boardVOList", boardVOList);
				req.setAttribute("assessVOList", assessVOList);
				req.setAttribute("journalMemberVO", journalMemberVO);
				
				String url = "/za101g2/front/journal/journal.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);				
				
			} catch (Exception e) {
				req.setAttribute("journalVO", errJournalVO);
				errorMsgs.add("文章文字超過上限(含HTML標籤)");
//				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/journal/update_journal_input.jsp");
				failureView.forward(req, res);
			}
		}
		
        if ("delete".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			
			String errURL = "/journal/journal.do?action=getOne_For_Display&id=";

			try {			
				String journalId = req.getParameter("journalId");
				Integer id = new Integer(journalId);
				
				errURL += journalId;

				JournalReportService journalReportSvc = new JournalReportService();
				journalReportSvc.deleteByJournal(id);
				
				JournalLinkedPetService journalLinkedPetSvc = new JournalLinkedPetService();
				journalLinkedPetSvc.deleteByJournal(id);
				
				JournalAssessService journalAssessSvc = new JournalAssessService();
				journalAssessSvc.deleteByJournalId(id);
				
				JournalBoardService journalBoardSvc = new JournalBoardService();
				journalBoardSvc.deleteByJournalId(id);
					
				JournalService journalSvc = new JournalService();
				journalSvc.deleteJournal(id);
						
				String url = "/memberHome/memberHome.do?action=myHome";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);				

			} catch (Exception e) {

				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(errURL);
				failureView.forward(req, res);
			}
		}
        
      //0614柏儒新增for首頁
        if("getMoreJour".equals(action)){
        	
        	res.setCharacterEncoding("UTF-8");
	    	PrintWriter out = res.getWriter();
	    	res.setContentType("application/json");
        	SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        	Integer jourId = Integer.parseInt(req.getParameter("jourId"));
        	
        	JournalService jourSrv = new JournalService();
        	MemberService memberSrv = new MemberService();
        	List<JournalVO> list = jourSrv.getMore(jourId);
        	Object[] o = new Object[list.size()];
        	for(int i =0;i<list.size();i++){
	    		Map JournalVOMap = new HashMap();
	    		JournalVOMap.put("id", list.get(i).getId().toString());
	    		JournalVOMap.put("memId",list.get(i).getMemId().toString());
	    		JournalVOMap.put("releaseDate",formater.format(list.get(i).getReleaseDate()));
	    		JournalVOMap.put("img",list.get(i).getFirstImg());
	    		JournalVOMap.put("shortArticle",list.get(i).getShortArticle());
	    		JournalVOMap.put("title",list.get(i).getTitle());
	    		JournalVOMap.put("nickname", memberSrv.getOneMember(list.get(i).getMemId()).getNickname());
	    		o[i]= JournalVOMap;
	    	}
        	System.out.println(o.length);
        	JSONObject json = new JSONObject();
	    	try {
				json.put("jourList",o);
			
			} catch (JSONException e) {				
				e.printStackTrace();
			}
	    	out.print(json.toString());
			out.flush();
			out.close();
	    	return;
        }

        
	}
}
