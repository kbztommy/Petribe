package za101g2.photo.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import za101g2.photo.model.PhotoService;
import za101g2.photo.model.PhotoVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class PhotoServlet0403 extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡����~�B�z**********************/
				String str = req.getParameter("id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入圖片編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/ZA101G2/front/photo/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				Integer id = null;
				try {
					id = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("圖片編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/ZA101G2/front/photo/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				PhotoService photoSvc = new PhotoService();
				PhotoVO photoVO = photoSvc.getOnePhoto(id);
				if (photoVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/ZA101G2/front/photo/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("photoVO", photoVO); // ��Ʈw��X��empVO����,�s�Jreq
				String url = "/ZA101G2/front/photo/listOnePhoto.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺��~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/ZA101G2/front/photo/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("listPhotos_ByMemId".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡����~�B�z**********************/
				String str = req.getParameter("memId");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入會員編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/ZA101G2/front/photo/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				Integer memId = null;
				try {
					memId = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("會員編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/ZA101G2/front/photo/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				PhotoService photoSvc = new PhotoService();
				List<PhotoVO> list = photoSvc.getPhotosByMemId(memId);
				if (list.size() == 0) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/ZA101G2/front/photo/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("memId", str);
				req.setAttribute("listPhotos_ByMemId", list); // ��Ʈw��X��empVO����,�s�Jreq
				String url = "/ZA101G2/front/photo/listPhotos_ByMemId.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺��~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/ZA101G2/front/photo/select_page.jsp");
				failureView.forward(req, res);
			}
		}	
		
		if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllEmp.jsp ��  /dept/listEmps_ByDeptno.jsp ���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|: �i�ର�i/emp/listAllEmp.jsp�j ��  �i/dept/listEmps_ByDeptno.jsp�j �� �i /dept/listAllDept.jsp�j		
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				Integer id = new Integer(req.getParameter("id"));
				
				/***************************2.�}�l�d�߸��****************************************/
				PhotoService photoSvc = new PhotoService();
				PhotoVO photoVO = photoSvc.getOnePhoto(id);
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("photoVO", photoVO); // ��Ʈw��X��empVO����,�s�Jreq
				String url = "/ZA101G2/front/photo/update_photo_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���update_emp_input.jsp
				successView.forward(req, res);

				/***************************��L�i�઺��~�B�z************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // �Ӧ�update_emp_input.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|: �i�ର�i/emp/listAllEmp.jsp�j ��  �i/dept/listEmps_ByDeptno.jsp�j �� �i /dept/listAllDept.jsp�j �� �i /emp/listEmps_ByCompositeQuery.jsp�j
		
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡����~�B�z**********************/
				Integer id = new Integer(req.getParameter("id").trim());
				String name = req.getParameter("name").trim();
			
				PhotoVO photoVO = new PhotoVO();
				photoVO.setId(id);
				photoVO.setName(name);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("photoVO", photoVO); // �t����J�榡��~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/ZA101G2/front/photo/update_photo_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.�}�l�ק���*****************************************/
				PhotoService photoSvc = new PhotoService();
				photoSvc.updatePhotoName(id, name);
				photoVO = photoSvc.getOnePhoto(id);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/				
				if(requestURL.equals("/ZA101G2/front/photo/listPhotos_ByMemId.jsp") || requestURL.equals("/ZA101G2/front/member/listAllMember.jsp")){
					List<PhotoVO> list1 = photoSvc.getPhotosByMemId(photoVO.getMemId());
					req.setAttribute("listPhotos_ByMemId",list1); // ��Ʈw��X��list����,�s�Jrequest
				}
				
				if(requestURL.equals("/ZA101G2/front/photo/listPhotos_ByCompositeQuery.jsp")){
					HttpSession session = req.getSession();
					Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
					List<PhotoVO> list2  = photoSvc.getAll(map);
					req.setAttribute("listPhotos_ByCompositeQuery",list2); //  �ƦX�d��, ��Ʈw��X��list����,�s�Jrequest
				}
                
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);   // �ק令�\��,���^�e�X�ק諸�ӷ�����
				successView.forward(req, res);

				/***************************��L�i�઺��~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/ZA101G2/front/photo/update_photo_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡����~�B�z*************************/

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
				Integer memId =Integer.parseInt(req.getParameter("memId").trim());

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("photoVO", photoVO); // �t����J�榡��~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/ZA101G2/front/photo/addPhoto.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�s�W���***************************************/
				PhotoService photoSvc = new PhotoService();
				photoSvc.addPhoto(name, format, data, memId);
				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/ZA101G2/front/photo/listAllPhoto.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************��L�i�઺��~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/ZA101G2/front/photo/addPhoto.jsp");
				failureView.forward(req, res);
			}
		}
       
		if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp ��  /dept/listEmps_ByDeptno.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // �e�X�R�����ӷ��������|: �i�ର�i/emp/listAllEmp.jsp�j ��  �i/dept/listEmps_ByDeptno.jsp�j �� �i /dept/listAllDept.jsp�j �� �i /emp/listEmps_ByCompositeQuery.jsp�j

			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				Integer id = new Integer(req.getParameter("id"));
				
				/***************************2.�}�l�R�����***************************************/
				PhotoService photoSvc = new PhotoService();
				PhotoVO photoVO = photoSvc.getOnePhoto(id);
				photoSvc.deletePhoto(id);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/
				PhotoService photoSvc2 = new PhotoService();
				if(requestURL.equals("/ZA101G2/front/photo/listPhotos_ByMemId.jsp") || requestURL.equals("/ZA101G2/front/member/listAllMember.jsp")){
					List<PhotoVO> list1 = photoSvc2.getPhotosByMemId(photoVO.getMemId());
					req.setAttribute("listPhotos_ByMemId",list1); // ��Ʈw��X��list����,�s�Jrequest
				}
				
				if(requestURL.equals("/ZA101G2/front/photo/listPhotos_ByCompositeQuery.jsp")){
					HttpSession session = req.getSession();
					Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
					List<PhotoVO> list2  = photoSvc2.getAll(map);
					req.setAttribute("listPhotos_ByCompositeQuery",list2); //  �ƦX�d��, ��Ʈw��X��list����,�s�Jrequest
				}
				
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				
				/***************************��L�i�઺��~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
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
				RequestDispatcher successView = req.getRequestDispatcher("/ZA101G2/front/photo/listPhotos_ByCompositeQuery.jsp"); // ���\���listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);
				
				/***************************��L�i�઺��~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/ZA101G2/front/photo/select_page.jsp");
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
