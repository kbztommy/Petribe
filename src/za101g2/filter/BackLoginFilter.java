package za101g2.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import za101g2.staff.model.StaffVO;

public class BackLoginFilter implements Filter {

	private ServletContext context;

	public void destroy() {
		
		context=null;
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		
		HttpSession session = ((HttpServletRequest)request).getSession();
		StaffVO staffVO = (StaffVO) session.getAttribute("staffVO");
		if(staffVO==null){
			String url = "/za101g2/back/homePage/";
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
			requestDispatcher.forward(request, response);
		} else {
		chain.doFilter(request, response);
		}
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
	}

}
