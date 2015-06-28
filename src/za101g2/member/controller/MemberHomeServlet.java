package za101g2.member.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import za101g2.journal.model.JournalService;
import za101g2.journal.model.JournalVO;
import za101g2.journalAssess.model.JournalAssessService;
import za101g2.journalAssess.model.JournalAssessVO;
import za101g2.journalBoard.model.JournalBoardService;
import za101g2.journalBoard.model.JournalBoardVO;
import za101g2.journalLinkedPet.model.JournalLinkedPetService;
import za101g2.member.model.MemberService;
import za101g2.member.model.MemberVO;
import za101g2.pet.model.PetService;
import za101g2.pet.model.PetVO;
import za101g2.photo.model.PhotoService;
import za101g2.photo.model.PhotoVO;
import za101g2.photoLinkedPet.model.PhotoLinkedPetService;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class MemberHomeServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("myHome".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");

			try {
				//未登入重導 未完成
				if (memberVO == null) {
					String url = "/za101g2/front/會員登入頁面?requestURL="+req.getServletPath()+"?action=myHome";
					RequestDispatcher failureView = req
							.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}				
				
				JournalService journalSvc = new JournalService();
				List<JournalVO> journalList = journalSvc.getJournalsByMemId(memberVO.getId());
				if (journalList.size() == 0) {
					errorMsgs.add("查無日誌資料");
				}
				
				PetService petSvc = new PetService();
				List<PetVO> petList = petSvc.findIdByMemId(memberVO.getId());
				if (petList.size() == 0) {
					errorMsgs.add("查無寵物資料");
				}
				
				PhotoService photoSvc = new PhotoService();
				List<PhotoVO> photoList = photoSvc.getPhotosByMemId(memberVO.getId());
				if (photoList.size() == 0) {
					errorMsgs.add("查無照片資料");
				}
				

				List<String> journalFirstPhoto = new ArrayList<String>();			
				if(journalList.size()>0){
					for(JournalVO oneJournalVO : journalList){
						List<String> oneJournalPhotos = getJournalPhotoId(oneJournalVO.getArticle());
						if(oneJournalPhotos.size()>0){
							journalFirstPhoto.add(oneJournalPhotos.get(0));
						}
						else{
							journalFirstPhoto.add("0");
						}
					}
				}
				
				List<String> articleNonePhoto = new ArrayList<String>();
				if(journalList.size()>0){
					for(JournalVO oneJournalVO : journalList){
						String article=oneJournalVO.getArticle();
//						List<String> oneJournalPhotos = getJournalPhotoId(article);				
//						for(String photo : oneJournalPhotos){
//							article=article.replace("<img src=\"/Petribe/Photo/PhotoDisplay?id="+photo+"\">", "");
//						}
						article=article.replaceAll("<[^>]*>", "");
						if(article.length() >= 100){
							article=article.substring(0, 100);
							article+="‧‧‧‧‧‧";
						}
						articleNonePhoto.add(article);
					}
				}
				
				req.setAttribute("getJournalsByMemId", journalList);
				req.setAttribute("journalFirstPhoto", journalFirstPhoto);
				req.setAttribute("articleNonePhoto", articleNonePhoto);
				req.setAttribute("getPetsByMemId", petList);
				req.setAttribute("getPhotosByMemId", photoList);
				req.setAttribute("memberView", memberVO);
				String url = "/za101g2/front/memberHome/myHome.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/index.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("memberHome".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

//			try {
				
				String str = req.getParameter("memId");
				Integer memId = new Integer(str);						
				
				JournalService journalSvc = new JournalService();
				List<JournalVO> journalList = journalSvc.getJournalsByMemId(memId);
				if (journalList.size() == 0) {
					errorMsgs.add("查無日誌資料");
				}
				
				PetService petSvc = new PetService();
				List<PetVO> petList = petSvc.findIdByMemId(memId);
				if (petList.size() == 0) {
					errorMsgs.add("查無寵物資料");
				}
				
				PhotoService photoSvc = new PhotoService();
				List<PhotoVO> photoList = photoSvc.getPhotosByMemId(memId);
				if (photoList.size() == 0) {
					errorMsgs.add("查無照片資料");
				}
				
				MemberService memberSvc = new MemberService();
				MemberVO memberView = memberSvc.getOneMember(memId);
				if (memberView == null) {
					errorMsgs.add("查無會員資料");
				}

				List<String> journalFirstPhoto = new ArrayList<String>();			
				if(journalList.size()>0){
					for(JournalVO oneJournalVO : journalList){
						List<String> oneJournalPhotos = getJournalPhotoId(oneJournalVO.getArticle());
						if(oneJournalPhotos.size()>0){
							journalFirstPhoto.add(oneJournalPhotos.get(0));
						}
						else{
							journalFirstPhoto.add("0");
						}
					}
				}
				
				List<String> articleNonePhoto = new ArrayList<String>();
				if(journalList.size()>0){
					for(JournalVO oneJournalVO : journalList){
						String article=oneJournalVO.getArticle();
//						List<String> oneJournalPhotos = getJournalPhotoId(article);				
//						for(String photo : oneJournalPhotos){
//							article=article.replace("<img src=\"/Petribe/Photo/PhotoDisplay?id="+photo+"\">", "");
//						}
						article=article.replaceAll("<[^>]*>", "");

						if(article.length() >= 100){
							article=article.substring(0, 100);
							article+="‧‧‧‧‧‧";
						}			
						articleNonePhoto.add(article);
					}
				}
				
				req.setAttribute("getJournalsByMemId", journalList);
				req.setAttribute("journalFirstPhoto", journalFirstPhoto);
				req.setAttribute("articleNonePhoto", articleNonePhoto);
				req.setAttribute("getPetsByMemId", petList);
				req.setAttribute("getPhotosByMemId", photoList);
				req.setAttribute("memberView", memberView);
				String url = "/za101g2/front/memberHome/myHome.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

//			} catch (Exception e) {
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/za101g2/front/index.jsp");
//				failureView.forward(req, res);
//			}
		}
		
		if ("petHome".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
				String str = req.getParameter("petId");
				Integer petId = new Integer(str);
				
				PetService petSvc = new PetService();
				PetVO petVO = petSvc.getOnePet(petId);
				
				MemberVO memberView =petVO.getMemberVO();
				
				PhotoLinkedPetService photoLinkedPetSvc = new PhotoLinkedPetService();
				List<Integer> petPhotoList = photoLinkedPetSvc.listByPetId(petId);

				List<PhotoVO> photoList = new ArrayList<PhotoVO>();
				PhotoService photoSvc = new PhotoService();
				for(Integer petPhotoId : petPhotoList){
					photoList.add(photoSvc.getOnePhoto(petPhotoId));
				}
				Collections.reverse(photoList);

				JournalLinkedPetService journalLinkedPetSvc = new JournalLinkedPetService();
				List<Integer> petJournalList = journalLinkedPetSvc.listByPetId(petId);

				List<JournalVO> journalList = new ArrayList<JournalVO>();
				JournalService JournalSvc = new JournalService();
				for(Integer petJournalId : petJournalList){
					journalList.add(JournalSvc.getOneJournal(petJournalId));
				}

				List<String> journalFirstPhoto = new ArrayList<String>();			
				if(journalList.size()>0){
					for(JournalVO oneJournalVO : journalList){
						List<String> oneJournalPhotos = getJournalPhotoId(oneJournalVO.getArticle());
						if(oneJournalPhotos.size()>0){
							journalFirstPhoto.add(oneJournalPhotos.get(0));
						}
						else{
							journalFirstPhoto.add("0");
						}
					}
				}
				
				List<String> articleNonePhoto = new ArrayList<String>();
				if(journalList.size()>0){
					for(JournalVO oneJournalVO : journalList){
						String article=oneJournalVO.getArticle();
//						List<String> oneJournalPhotos = getJournalPhotoId(article);				
//						for(String photo : oneJournalPhotos){
//							article=article.replace("<img src=\"/Petribe/Photo/PhotoDisplay?id="+photo+"\">", "");
//						}
						article=article.replaceAll("<[^>]*>", "");
						if(article.length() >= 100){
							article=article.substring(0, 100);
							article+="‧‧‧‧‧‧";
						}
						articleNonePhoto.add(article);
					}
				}
				
				req.setAttribute("journalList", journalList);
				req.setAttribute("journalFirstPhoto", journalFirstPhoto);
				req.setAttribute("articleNonePhoto", articleNonePhoto);
				req.setAttribute("petVO", petVO);
				req.setAttribute("photoList", photoList);
				req.setAttribute("memberView", memberView);
				String url = "/za101g2/front/memberHome/petHome.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/homePage/index.jsp");
				failureView.forward(req, res);
			}
		}
		
        if ("insertJournalBoard".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			
			try {
				//未登入重導 未完成
				if (memberVO == null) {
					String url = "/za101g2/front/會員登入頁面?requestURL="+req.getServletPath()+"?action=myHome";
					RequestDispatcher failureView = req
							.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}				
				
				Integer journalId = new Integer(req.getParameter("journalId"));
				String boardMsg = req.getParameter("boardMsg").trim();				
				Integer memId = new Integer(memberVO.getId());				
				String isDelete = "n";					

				JournalBoardVO journalBoardVO = new JournalBoardVO();
				journalBoardVO.setJournalId(journalId);
				journalBoardVO.setMemId(memId);
				journalBoardVO.setBoardMsg(boardMsg);
				journalBoardVO.setIsDelete(isDelete);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("journalBoardVO", journalBoardVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/za101g2/front/journal/journal.jsp");
					failureView.forward(req, res);
					return;
				}
				
				JournalBoardService journalBoardSvc = new JournalBoardService();
				journalBoardVO = journalBoardSvc.addJournalBoard(journalId, memId, boardMsg, isDelete);
				
				//與getOne_For_Display相同
				JournalService journalSvc = new JournalService();
				JournalVO journalVO = journalSvc.getOneJournal(journalId);
				List<JournalBoardVO> boardVOList = journalBoardSvc.findByJournalId(journalId);
				JournalAssessService assessSvc = new JournalAssessService();
				List<JournalAssessVO> assessVOList = assessSvc.getAssessByAJournal(journalId);			
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
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/journal/journal.jsp");
				failureView.forward(req, res);
			}
		}
       
        if ("insertJournalAssess".equals(action)) { // �Ӧ�addEmp.jsp���ШD  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			
			try {
				//未登入重導 未完成
				if (memberVO == null) {
					String url = "/za101g2/front/會員登入頁面?requestURL="+req.getServletPath()+"?action=myHome";
					RequestDispatcher failureView = req
							.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}				
				
				Integer journalId = new Integer(req.getParameter("journalId"));			
				Integer memId = new Integer(memberVO.getId());								
			
				JournalAssessService journalAssessSvc = new JournalAssessService();
				journalAssessSvc.addJournalAssess(journalId, memId);
				
				//與getOne_For_Display相同
				JournalService journalSvc = new JournalService();
				JournalVO journalVO = journalSvc.getOneJournal(journalId);
				JournalBoardService journalBoardSvc = new JournalBoardService();
				List<JournalBoardVO> boardVOList = journalBoardSvc.findByJournalId(journalId);
				JournalAssessService assessSvc = new JournalAssessService();
				List<JournalAssessVO> assessVOList = assessSvc.getAssessByAJournal(journalId);			
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
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/journal/journal.jsp");
				failureView.forward(req, res);
			}
		}
        
		if ("deleteJournalAssess".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			
			try {
				//未登入重導 未完成
				if (memberVO == null) {
					String url = "/za101g2/front/會員登入頁面?requestURL="+req.getServletPath()+"?action=myHome";
					RequestDispatcher failureView = req
							.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}				
				
				Integer journalId = new Integer(req.getParameter("journalId"));			
				Integer memId = new Integer(memberVO.getId());								
			
				JournalAssessService journalAssessSvc = new JournalAssessService();
				journalAssessSvc.deleteJournalAssess(journalId, memId);
				
				//與getOne_For_Display相同
				JournalService journalSvc = new JournalService();
				JournalVO journalVO = journalSvc.getOneJournal(journalId);
				JournalBoardService journalBoardSvc = new JournalBoardService();
				List<JournalBoardVO> boardVOList = journalBoardSvc.findByJournalId(journalId);
				JournalAssessService assessSvc = new JournalAssessService();
				List<JournalAssessVO> assessVOList = assessSvc.getAssessByAJournal(journalId);			
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
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/journal/journal.jsp");
				failureView.forward(req, res);
			}
		}
		
	}
	
	private String getFileName(final Part part) {
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
	
	private List<String> getJournalPhotoId(String article) {
		List<String> list = new ArrayList<String>();
	    for (String content : article.split("src=\"")) {	
	        if (content.trim().startsWith("/Petribe/Photo/PhotoDisplay?id=")) {
	        	String id = content.substring(content.indexOf("?id=")+4,content.indexOf("\">"));
	        	list.add(id);
	        }  
	    }
	    return list;
	}
	
}
