package za101g2.util;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import za101g2.member.model.*;

@WebServlet("/PicForMember")
public class PicForMember extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		ServletContext context = getServletContext();
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();

		req.setCharacterEncoding("utf-8");
		String id = req.getParameter("id");
		String id2 = new String(id.getBytes("ISO-8859-1"), ("utf-8"));
		MemberService memberSrv = new MemberService();
		MemberVO memberVO = memberSrv.getOneMember(Integer.parseInt(id2));
		try{
			out.write(memberVO.getIcon());
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
		}
//		finally{
//			try{
//				if(out!=null)
//					out.close();
//			}catch(IOException e){
//				e.printStackTrace();
//			}
//		}
		
	}

}
