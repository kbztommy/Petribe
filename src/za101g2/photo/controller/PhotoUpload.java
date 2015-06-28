package za101g2.photo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import za101g2.photo.model.PhotoService;
import za101g2.photo.model.PhotoVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class PhotoUpload extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		res.setContentType("text/html; charset=utf-8");
		PrintWriter out = res.getWriter();
		if ("insert".equals(action)) {
			/*************************** 開始新增資料 ****************************************/
			PhotoVO photoVO = new PhotoVO();
			Part part = req.getPart("photoFile");
			InputStream ins = part.getInputStream();
			byte[] data = new byte[ins.available()];
			ins.read(data);
			try{
				if(ins!=null)
					ins.close();
			}catch(IOException e){
				e.printStackTrace();
			}
			
			String fileName = getFileName(part);
			int dotPos = fileName.indexOf('.');
			String name = fileName.substring(0, dotPos);
			String format = fileName.substring(dotPos + 1);
			Integer memId =Integer.parseInt(req.getParameter("memId").trim());
			
			PhotoService photoSvc = new PhotoService();
			photoSvc.addPhoto(name, format, data, memId);

			String url = "/za101g2/front/photo/photoUpload.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
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
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
