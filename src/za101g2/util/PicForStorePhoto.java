package za101g2.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import za101g2.storePhoto.model.*;


@WebServlet("/PicForStorePhoto")
public class PicForStorePhoto extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ServletContext context = getServletContext();
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();
		
		req.setCharacterEncoding("utf-8");
		String id = req.getParameter("id");
		String id2 = new String(id.getBytes("ISO-8859-1"), ("utf-8"));
		StorePhotoService StorePhotoSrv = new StorePhotoService();
		StorePhotoVO storePhotoVO=null;
		try{
			storePhotoVO = StorePhotoSrv.findByPrimaryKey(Integer.parseInt(id2));
		}catch(java.lang.NumberFormatException e){
			return;
		}
		try{
			out.write(storePhotoVO.getPhotoFile());
		}catch (java.lang.NullPointerException e){			
			File f = new File(context.getRealPath("/images/logo.png"));
			FileInputStream fis = new FileInputStream(f);
			byte[] data = new byte[fis.available()];
			fis.read(data);
			out.write(data);	
			try{
				if(fis!=null)
					fis.close();
			}catch(IOException e2){
				e.printStackTrace();
			}
		}finally{
			try{
				if(out!=null)
					out.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

}
