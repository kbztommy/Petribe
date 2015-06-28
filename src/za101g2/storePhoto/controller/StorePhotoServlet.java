package za101g2.storePhoto.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import za101g2.storePhoto.model.StorePhotoService;
import za101g2.storePhoto.model.StorePhotoVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
@WebServlet("/StorePhotoServlet")
public class StorePhotoServlet extends HttpServlet {


	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");
		req.setCharacterEncoding("utf-8");
		res.setHeader("Pragma","no-cache");
	    res.setHeader("Cache-Control","no-cache");
	    res.setDateHeader("Expires", 0);	
		
		if("addPic".equals(action)){			
			PrintWriter out = res.getWriter();
			res.setCharacterEncoding("utf-8");
			Integer storeId = Integer.parseInt(req.getParameter("storeId"));
			Part pic = req.getPart("pic");
			if(pic.getSize()==0)
				return;
			InputStream ins = pic.getInputStream();
			byte[] data = new byte[ins.available()];			
			ins.read(data);
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			StorePhotoService storePhotoSrv = new StorePhotoService();
			StorePhotoVO storePhotoVO = storePhotoSrv.insertForStore(storeId, data);
			out.print(storePhotoVO.getId());		
			out.flush();
			return;
		}
	}

}
