package za101g2.taglib;
import java.io.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;



public class GetFirstImg extends BodyTagSupport{
	
	 public int doAfterBody() throws JspException {
	        BodyContent bc = getBodyContent();
	        JspWriter out = getPreviousOut();
	        try {
	            out.write("FKKKKKKKKKKK,forget can't read img again");
	        }
	        catch (IOException e) {} // Ignore
	        return SKIP_BODY;
	    }
}
