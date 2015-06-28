package za101g2.util;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;



import za101g2.missingrecord.model.*;

@WebServlet("/MissingRecordGifReader")
public class MissingRecordGifReader extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		ServletContext context = getServletContext();
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();
		
		req.setCharacterEncoding("UTF-8");
		String id = req.getParameter("id");
		String id2 = new String(id.getBytes("ISO-8859-1"), ("UTF-8"));
		MissingRecordService missingRecordSrv = new MissingRecordService();
		MissingRecordVO missingRecordVO = missingRecordSrv.getOneMissingRecord(Integer.parseInt(id2));
		try {
			out.write(missingRecordVO.getMissingPhoto());
		} catch (java.lang.NullPointerException e) {
			File file = new File(context.getRealPath("za101g2/front/missingRecord/images/logo.png"));
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[fis.available()];
			fis.read(data);
			out.write(data);
			try {
				if (fis!=null)
					fis.close();
			} catch (IOException se) {
				se.printStackTrace();
			}
		} finally {
			try {
				if(out!=null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
