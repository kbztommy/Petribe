package za101g2.photo.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import za101g2.journal.model.JournalVO;
import za101g2.journalLinkedPet.model.JournalLinkedPetService;
import za101g2.member.model.MemberService;
import za101g2.member.model.MemberVO;
import za101g2.pet.model.PetService;
import za101g2.pet.model.PetVO;
import za101g2.photo.model.PhotoService;
import za101g2.photo.model.PhotoVO;
import za101g2.photoLinkedPet.model.PhotoLinkedPetService;
import za101g2.photoLinkedPet.model.PhotoLinkedPetVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class PhotoServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

        if ("insertMemberPhoto".equals(action)) {
        	
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Part part = req.getPart("photoFile");
				InputStream ins = part.getInputStream();
				byte[] data = new byte[ins.available()];
				ins.read(data);
				try{
					if(ins!=null)
						ins.close();
				}catch(IOException e){
					errorMsgs.add("InputStream關閉異常:"+e.getMessage());
				}
	
				HttpSession session = req.getSession();
				MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
				Integer memId = memberVO.getId();
				
				MemberService memberSvc = new MemberService();
				MemberVO member =  memberSvc.updateIcon(data, memId);
				
				session.setAttribute("memberVO", member);
				
				String requestURL = "/memberHome/memberHome.do?action=myHome";
				req.setAttribute("requestURL", requestURL);
				
				String url = "/za101g2/front/memberHome/redirect.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);				
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/memberHome/memberHome.do?action=myHome");
				failureView.forward(req, res);
			}
		}
		
        if ("insertPetPhoto".equals(action)) {
        	
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = "";
			
			try {
				String str = req.getParameter("petId");
				Integer petId = new Integer(str);
				
				requestURL = "/memberHome/memberHome.do?action=petHome&petId="+petId;
				
				Part part = req.getPart("photoFile");
				InputStream ins = part.getInputStream();
				byte[] data = new byte[ins.available()];
				ins.read(data);
				try{
					if(ins!=null)
						ins.close();
				}catch(IOException e){
					errorMsgs.add("InputStream關閉異常:"+e.getMessage());
				}
				
				PetService petSvc = new PetService();
				petSvc.updateIcon(data, petId);

				req.setAttribute("requestURL", requestURL);
				
				String url = "/za101g2/front/memberHome/redirect.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);				
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
        
        if ("updateFromJournal".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				PhotoVO photoVO = new PhotoVO();
				Part part = req.getPart("photoFile");
				InputStream ins = part.getInputStream();
				byte[] data = new byte[ins.available()];
				ins.read(data);
				try{
					if(ins!=null)
						ins.close();
				}catch(IOException e){
					errorMsgs.add("InputStream關閉異常:"+e.getMessage());
				}
				
				String fileName = getFileName(part);
				int dotPos = fileName.indexOf('.');
				String name = fileName.substring(0, dotPos);
				String format = fileName.substring(dotPos + 1);
	
				HttpSession session = req.getSession();
				MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
				Integer memId = memberVO.getId();
				
				String id = req.getParameter("journalId");
				Integer journalId = new Integer(id);
				String title = req.getParameter("title").trim();		
				String article = req.getParameter("article").trim();
				JournalVO journalVO = new JournalVO();
				journalVO.setId(journalId);
				journalVO.setTitle(title);
				journalVO.setArticle(article);
				req.setAttribute("journalVO", journalVO);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("photoVO", photoVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/za101g2/front/journal/update_journal_input.jsp");
					failureView.forward(req, res);
					return;
				}

				PhotoService photoSvc = new PhotoService();
				Integer photoId =photoSvc.addPhotoReId(name, format, data, memId);
				article+="<img src=\"/Petribe/Photo/PhotoDisplay?id="+photoId+"\">";
				journalVO.setArticle(article);
				req.setAttribute("journalVO", journalVO);
				
				String str = req.getParameter("petId");
				Integer petId = new Integer(str);
				if(petId != 0){
					PhotoLinkedPetService PhotoLinkSvc = new PhotoLinkedPetService();
					PhotoLinkSvc.addPhotoLinkedPet(photoId, petId);
				}
				
				String url = "/za101g2/front/journal/update_journal_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);				
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/journal/update_journal_input.jsp");
				failureView.forward(req, res);
			}
		}
		
        if ("insertFromJournal".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				PhotoVO photoVO = new PhotoVO();
				Part part = req.getPart("photoFile");
				InputStream ins = part.getInputStream();
				byte[] data = new byte[ins.available()];
				ins.read(data);
				try{
					if(ins!=null)
						ins.close();
				}catch(IOException e){
					errorMsgs.add("InputStream關閉異常:"+e.getMessage());
				}
				
				String fileName = getFileName(part);
				int dotPos = fileName.indexOf('.');
				String name = fileName.substring(0, dotPos);
				String format = fileName.substring(dotPos + 1);
	
				HttpSession session = req.getSession();
				MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
				Integer memId = memberVO.getId();

				String title = req.getParameter("title").trim();		
				String article = req.getParameter("article").trim();
				JournalVO journalVO = new JournalVO();
				journalVO.setTitle(title);
				journalVO.setArticle(article);
				req.setAttribute("journalVO", journalVO);
				
				String journalPetId = req.getParameter("journalPetId");
				req.setAttribute("journalPetId", journalPetId);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("photoVO", photoVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/za101g2/front/journal/addJournal.jsp");
					failureView.forward(req, res);
					return;
				}
				
				PhotoService photoSvc = new PhotoService();
				Integer photoId =photoSvc.addPhotoReId(name, format, data, memId);
				article+="<img src=\"/Petribe/Photo/PhotoDisplay?id="+photoId+"\">";
				journalVO.setArticle(article);
				req.setAttribute("journalVO", journalVO);
				
				String str = req.getParameter("petId");
				Integer petId = new Integer(str);
				if(petId != 0){
					PhotoLinkedPetService PhotoLinkSvc = new PhotoLinkedPetService();
					PhotoLinkSvc.addPhotoLinkedPet(photoId, petId);
				}
				
				String url = "/za101g2/front/journal/addJournal.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);				
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/journal/addJournal.jsp");
				failureView.forward(req, res);
			}
		}
        
        if ("insertFromAlbum".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			Integer memId = memberVO.getId();
			String whichPage = req.getParameter("whichPage");
			
			String url = "/photo/photo.do?action=getPhotosByMemId&memId="+memId+"&whichPage="+whichPage;
			try {
				Part part = req.getPart("photoFile");
				InputStream ins = part.getInputStream();
				byte[] data = new byte[ins.available()];
				ins.read(data);
				try{
					if(ins!=null)
						ins.close();
				}catch(IOException e){
					errorMsgs.add("InputStream關閉異常:"+e.getMessage());
				}
				
				String fileName = getFileName(part);
				int dotPos = fileName.indexOf('.');
				String name = fileName.substring(0, dotPos);
				String format = fileName.substring(dotPos + 1);
				
				PhotoService photoSvc = new PhotoService();
				Integer photoId =photoSvc.addPhotoReId(name, format, data, memId);
				
				String str = req.getParameter("petId");
				Integer petId = new Integer(str);
				if(petId != 0){
					PhotoLinkedPetService PhotoLinkSvc = new PhotoLinkedPetService();
					PhotoLinkSvc.addPhotoLinkedPet(photoId, petId);
				}
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);				
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}
		
        if ("insertFromPetHome".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			Integer memId = memberVO.getId();
			
			String url = "";
			try {
				String str = req.getParameter("petId");
				Integer petId = new Integer(str);
				url="/memberHome/memberHome.do?action=petHome&petId="+petId;
				
				Part part = req.getPart("photoFile");
				InputStream ins = part.getInputStream();
				byte[] data = new byte[ins.available()];
				ins.read(data);
				try{
					if(ins!=null)
						ins.close();
				}catch(IOException e){
					errorMsgs.add("InputStream關閉異常:"+e.getMessage());
				}
				
				String fileName = getFileName(part);
				int dotPos = fileName.indexOf('.');
				String name = fileName.substring(0, dotPos);
				String format = fileName.substring(dotPos + 1);
				
				PhotoService photoSvc = new PhotoService();
				Integer photoId =photoSvc.addPhotoReId(name, format, data, memId);
				
				if(petId != 0){
					PhotoLinkedPetService PhotoLinkSvc = new PhotoLinkedPetService();
					PhotoLinkSvc.addPhotoLinkedPet(photoId, petId);
				}
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);				
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}
        
		if ("getPhotosByMemId".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String whichPage = req.getParameter("whichPage");
				
				String str = req.getParameter("memId");
				req.setAttribute("memId", str);
				
				Integer memId = new Integer(str);
				MemberService memberSvc = new MemberService();
				MemberVO memberView = memberSvc.getOneMember(memId);
				
				PhotoService photoSvc = new PhotoService();
				List<PhotoVO> photoList = photoSvc.getPhotosByMemId(memId);
				Collections.reverse(photoList);
				PetService petSvc = new PetService();
				List<PetVO> petList = petSvc.findIdByMemId(memId);
				
				req.setAttribute("memberView", memberView);
				req.setAttribute("petList", petList);
				req.setAttribute("photoList", photoList);
				req.setAttribute("action", "getPhotosByMemId");
				req.setAttribute("whichPage", whichPage);
				String url = "/za101g2/front/photo/listPhotosByMemId.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				req.setAttribute("action", "memberHome");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/memberHome/memberHome.do");
				failureView.forward(req, res);
			}
		}	

		if ("getPhotosByPetId".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String whichPage = req.getParameter("whichPage");
				
				String str = req.getParameter("memId");
				req.setAttribute("memId", str);
				
				String str2 = req.getParameter("petId");
				Integer petId = new Integer(str2);

				Integer memId = new Integer(str);
				MemberService memberSvc = new MemberService();
				MemberVO memberView = memberSvc.getOneMember(memId);
				
				PhotoLinkedPetService photoLinkedPetSvc = new PhotoLinkedPetService();
				List<Integer> petPhotoList = photoLinkedPetSvc.listByPetId(petId);

				List<PhotoVO> photoList = new ArrayList<PhotoVO>();
				PhotoService photoSvc = new PhotoService();
				for(Integer petPhotoId : petPhotoList){
					photoList.add(photoSvc.getOnePhoto(petPhotoId));
				}
				Collections.reverse(photoList);		
				PetService petSvc = new PetService();
				List<PetVO> petList = petSvc.findIdByMemId(memId);
				
				req.setAttribute("memberView", memberView);
				req.setAttribute("petList", petList);
				req.setAttribute("photoList", photoList);
				req.setAttribute("action", "getPhotosByPetId");
				req.setAttribute("petId", petId);
				req.setAttribute("whichPage", whichPage);
				String url = "/za101g2/front/photo/listPhotosByMemId.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				req.setAttribute("action", "memberHome");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/memberHome/memberHome.do");
				failureView.forward(req, res);
			}
		}	
		
		if ("getOneForUpdate".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);	
			
			String url = "/za101g2/front/photo/listPhotosByMemId.jsp";
			
			try {			
				String secondAction = req.getParameter("secondAction");
				String whichPage = req.getParameter("whichPage");
				
				String str = req.getParameter("memId");
				req.setAttribute("memId", str);
				Integer memId = new Integer(str);
				
				if ("getPhotosByMemId".equals(secondAction)) {				
					MemberService memberSvc = new MemberService();
					MemberVO memberView = memberSvc.getOneMember(memId);
					
					PhotoService photoSvc = new PhotoService();
					List<PhotoVO> photoList = photoSvc.getPhotosByMemId(memId);
					Collections.reverse(photoList);	
					
					PetService petSvc = new PetService();
					List<PetVO> petList = petSvc.findIdByMemId(memId);
					
					req.setAttribute("memberView", memberView);
					req.setAttribute("petList", petList);
					req.setAttribute("photoList", photoList);
					req.setAttribute("action", "getPhotosByMemId");
					req.setAttribute("whichPage", whichPage);
				}
				
				if ("getPhotosByPetId".equals(secondAction)) {		
					String str2 = req.getParameter("petId");
					Integer petId = new Integer(str2);

					MemberService memberSvc = new MemberService();
					MemberVO memberView = memberSvc.getOneMember(memId);
					
					PhotoLinkedPetService photoLinkedPetSvc = new PhotoLinkedPetService();
					List<Integer> petPhotoList = photoLinkedPetSvc.listByPetId(petId);

					List<PhotoVO> photoList = new ArrayList<PhotoVO>();
					PhotoService photoSvc = new PhotoService();
					for(Integer petPhotoId : petPhotoList){
						photoList.add(photoSvc.getOnePhoto(petPhotoId));
					}
					Collections.reverse(photoList);	
					
					PetService petSvc = new PetService();
					List<PetVO> petList = petSvc.findIdByMemId(memId);
					
					req.setAttribute("memberView", memberView);
					req.setAttribute("petList", petList);
					req.setAttribute("photoList", photoList);
					req.setAttribute("action", "getPhotosByPetId");
					req.setAttribute("petId", petId);
					req.setAttribute("whichPage", whichPage);
				}	
				
				Integer photoId = new Integer(req.getParameter("photoId"));
				String photoName = req.getParameter("name");
				if (photoName.length() == 0) {
					errorMsgs.add("請輸入圖片名稱");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}
				
				PhotoService photoSvc = new PhotoService();
				photoSvc.updatePhotoName(photoId,photoName);
					
				if ("getPhotosByMemId".equals(secondAction)) {
					photoSvc = new PhotoService();
					List<PhotoVO> photoList = photoSvc.getPhotosByMemId(memId);
					Collections.reverse(photoList);	
					req.setAttribute("photoList", photoList);
				}
				if ("getPhotosByPetId".equals(secondAction)) {		
					String str2 = req.getParameter("petId");
					Integer petId = new Integer(str2);
					
					PhotoLinkedPetService photoLinkedPetSvc = new PhotoLinkedPetService();
					List<Integer> petPhotoList = photoLinkedPetSvc.listByPetId(petId);

					List<PhotoVO> photoList = new ArrayList<PhotoVO>();
					photoSvc = new PhotoService();
					for(Integer petPhotoId : petPhotoList){
						photoList.add(photoSvc.getOnePhoto(petPhotoId));
					}
					Collections.reverse(photoList);	
					req.setAttribute("photoList", photoList);
				}	
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);	
			
			String url = "/za101g2/front/photo/listPhotosByMemId.jsp";
			
			try {			
				String secondAction = req.getParameter("secondAction");
				String whichPage = req.getParameter("whichPage");
				
				String str = req.getParameter("memId");
				req.setAttribute("memId", str);
				Integer memId = new Integer(str);
				
				if ("getPhotosByMemId".equals(secondAction)) {				
					MemberService memberSvc = new MemberService();
					MemberVO memberView = memberSvc.getOneMember(memId);
					
					PhotoService photoSvc = new PhotoService();
					List<PhotoVO> photoList = photoSvc.getPhotosByMemId(memId);
					Collections.reverse(photoList);	
					PetService petSvc = new PetService();
					List<PetVO> petList = petSvc.findIdByMemId(memId);
					
					req.setAttribute("memberView", memberView);
					req.setAttribute("petList", petList);
					req.setAttribute("photoList", photoList);
					req.setAttribute("action", "getPhotosByMemId");
					req.setAttribute("whichPage", whichPage);
				}
				
				if ("getPhotosByPetId".equals(secondAction)) {		
					String str2 = req.getParameter("petId");
					Integer petId = new Integer(str2);

					MemberService memberSvc = new MemberService();
					MemberVO memberView = memberSvc.getOneMember(memId);
					
					PhotoLinkedPetService photoLinkedPetSvc = new PhotoLinkedPetService();
					List<Integer> petPhotoList = photoLinkedPetSvc.listByPetId(petId);

					List<PhotoVO> photoList = new ArrayList<PhotoVO>();
					PhotoService photoSvc = new PhotoService();
					for(Integer petPhotoId : petPhotoList){
						photoList.add(photoSvc.getOnePhoto(petPhotoId));
					}
					Collections.reverse(photoList);	
					PetService petSvc = new PetService();
					List<PetVO> petList = petSvc.findIdByMemId(memId);
					
					req.setAttribute("memberView", memberView);
					req.setAttribute("petList", petList);
					req.setAttribute("photoList", photoList);
					req.setAttribute("action", "getPhotosByPetId");
					req.setAttribute("petId", petId);
					req.setAttribute("whichPage", whichPage);
				}	
				
				Integer photoId = new Integer(req.getParameter("photoId"));
				
				PhotoLinkedPetService photoLinkedPetSvc = new PhotoLinkedPetService();
				photoLinkedPetSvc.deleteByPhoto(photoId);
				
				PhotoService photoSvc = new PhotoService();
				photoSvc.deletePhoto(photoId);
				
				if ("getPhotosByMemId".equals(secondAction)) {
					photoSvc = new PhotoService();
					List<PhotoVO> photoList = photoSvc.getPhotosByMemId(memId);
					Collections.reverse(photoList);	
					req.setAttribute("photoList", photoList);
				}
				if ("getPhotosByPetId".equals(secondAction)) {		
					String str2 = req.getParameter("petId");
					Integer petId = new Integer(str2);
					
					photoLinkedPetSvc = new PhotoLinkedPetService();
					List<Integer> petPhotoList = photoLinkedPetSvc.listByPetId(petId);

					List<PhotoVO> photoList = new ArrayList<PhotoVO>();
					photoSvc = new PhotoService();
					for(Integer petPhotoId : petPhotoList){
						photoList.add(photoSvc.getOnePhoto(petPhotoId));
					}
					Collections.reverse(photoList);	
					req.setAttribute("photoList", photoList);
				}	
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				System.out.print(e.getMessage());
				errorMsgs.add("無法刪除資料:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}
		
		if ("listPhotos_ByCompositeQuery".equals(action)) { // �Ӧ�select_page.jsp���ƦX�d�߽ШD
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				
				/***************************1.�N��J����ରMap**********************************/ 
				//�ĥ�Map<String,String[]> getParameterMap()����k 
				//�`�N:an immutable java.util.Map 
				//Map<String, String[]> map = req.getParameterMap();
				HttpSession session = req.getSession();
				Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
				if (req.getParameter("whichPage") == null){
					HashMap<String, String[]> map1 = (HashMap<String, String[]>)req.getParameterMap();
					HashMap<String, String[]> map2 = new HashMap<String, String[]>();
					map2 = (HashMap<String, String[]>)map1.clone();
					session.setAttribute("map",map2);
					map = (HashMap<String, String[]>)req.getParameterMap();
				} 
				
				/***************************2.�}�l�ƦX�d��***************************************/
				PhotoService photoSvc = new PhotoService();
				List<PhotoVO> list  = photoSvc.getAll(map);
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("listPhotos_ByCompositeQuery", list); // ��Ʈw��X��list����,�s�Jrequest
				RequestDispatcher successView = req.getRequestDispatcher("/za101g2/front/photo/listPhotos_ByCompositeQuery.jsp"); // ���\���listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);
				
				/***************************��L�i�઺��~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/za101g2/front/photo/select_page.jsp");
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
	
}
