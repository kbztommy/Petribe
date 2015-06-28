package za101g2.member.controller;

import java.io.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

import za101g2.member.model.MemberService;
import za101g2.member.model.MemberVO;

public class MemberIconDisplay extends HttpServlet {

	Connection con;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("BIG5");
		String id = req.getParameter("id");
		String id2 = new String(id.getBytes("ISO-8859-1"),"BIG5");		
		
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();

		try {		
			MemberService memberSvc = new MemberService();
			MemberVO memberVO = memberSvc.getOneMember(Integer.parseInt(id2));

			byte[] data = memberVO.getIcon();
			ByteArrayInputStream ins = new ByteArrayInputStream(data); 			
			BufferedInputStream in = new BufferedInputStream(ins);
			byte[] buf = new byte[data.length];
			in.read(buf);
			out.write(buf, 0, data.length);
			if(ins!=null){ins.close();}
			if(in!=null){in.close();}						

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
