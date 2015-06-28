package za101g2.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import za101g2.staff.model.StaffVO;
import za101g2.staffAccesses.model.StaffAccessesService;

public class BackMemberManagerFilter implements Filter {

	private ServletContext context;
	
	public void destroy() {
		
		context=null;
		
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		StaffVO staffVO = (StaffVO) ((HttpServletRequest) request).getSession().getAttribute("staffVO");
		StaffAccessesService srv = new StaffAccessesService();
		List<Integer> accessesList = srv.findAccessesById(staffVO.getId());
		if(accessesList.contains(2)){
			chain.doFilter(request,response);
		} else {
			String url = "/za101g2/back/error/";
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
			requestDispatcher.forward(request, response);
		}
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
	}

}
