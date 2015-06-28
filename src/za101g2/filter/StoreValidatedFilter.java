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
import za101g2.store.model.StoreVO;



public class StoreValidatedFilter implements Filter{

	private FilterConfig fig = null;
	@Override
	public void destroy() {
		fig=null;
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=  (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
		StoreVO storeVO = (StoreVO)session.getAttribute("storeVO");
		
		if(storeVO == null){
			res.sendRedirect(req.getContextPath()+"/za101g2/front/member/applyStore_newer.jsp");
			return;
		}else if(!"3".equals(memberVO.getStatus())){
			//應該要有一個等待申請中的頁面，顯示商家尚未通過認證。
			res.sendRedirect(req.getContextPath()+"/za101g2/front/member/applyStore_wait.jsp");
			return;
		}else{
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void init(FilterConfig fig) throws ServletException {
		this.fig= fig;
		
	}

}
