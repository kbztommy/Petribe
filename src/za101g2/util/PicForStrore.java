package za101g2.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import za101g2.store.model.*;


/**
 * Servlet implementation class PicForStrore
 */
@WebServlet("/PicForStrore")
public class PicForStrore extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		ServletContext context = getServletContext();
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();

		req.setCharacterEncoding("utf-8");
		String id = req.getParameter("id");
		String id2 = new String(id.getBytes("ISO-8859-1"), ("utf-8"));
		StoreService StoreSrv = new StoreService();
		StoreVO storeVO = StoreSrv.getOneStore(Integer.parseInt(id2));
		try{
			out.write(storeVO.getIcon());
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
