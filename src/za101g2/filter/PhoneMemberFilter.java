package za101g2.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import za101g2.member.model.MemberVO;

public class PhoneMemberFilter implements Filter{

	private FilterConfig fig = null;
	
	@Override
	public void destroy() {
		fig=null;
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
		if("1".equals(memberVO.getStatus())){
			res.sendRedirect(req.getContextPath()+"/za101g2/front/member/phoneValidate_step1.jsp");
			return;
		}else{
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig fig) throws ServletException {
		this.fig = fig;
		
	}

}
