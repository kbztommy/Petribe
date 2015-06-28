package za101g2.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import za101g2.orderBoard.model.OrderBoardService;
import za101g2.orderBoard.model.OrderBoardVO;



@WebServlet(urlPatterns="/BoardingScheduleServlet",loadOnStartup=2)
public class BoardingScheduleServlet extends HttpServlet {
	
	Timer timer ; 
   
	private final long  fONCE_PER_DAY = 1000*60*60*24;
	
	private final int fONE_DAY = 1;
	private final int fFOUR_AM = 12;
	private final int fZERO_MINUTES = 0;
	
    public void destroy(){
        timer.cancel();
    }    
 
    public void init(){        
        timer = new Timer();  
        
        TimerTask task = new TimerTask(){
            
            public void run(){
            	Calendar c = Calendar.getInstance();
            	c.setTime(getTomorrowMorning12am());
            	OrderBoardService orderBoardSrv = new OrderBoardService();
                List<OrderBoardVO> list = orderBoardSrv.getTomorrowList(c);
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                if(!list.isEmpty()){
	                for(OrderBoardVO order :list){
	   	             Send se = new Send();
	   	             String[] tel ={order.getMemberVO().getPhone()};//測試時為我的手機，上線時要改成該訂單會員的手機號碼order.getMemberVO().getPhone();
	   	             String message ="親愛的 "+order.getMemberVO().getLastname()+order.getMemberVO().getFirstname()+"，您的寄養服務"
	   	            		 +"即將於明日("+formater.format(order.getStartDate())+")開始";
	   	           	 se.sendMessage(tel , message);
	                }
                }
            }
            private Date getTomorrowMorning12am(){
        	    Calendar tomorrow = new GregorianCalendar();        	   
        	    tomorrow.add(Calendar.DATE, fONE_DAY);        	   
        	    Calendar result = new GregorianCalendar(
        	      tomorrow.get(Calendar.YEAR),
        	      tomorrow.get(Calendar.MONTH),
        	      tomorrow.get(Calendar.DATE),
        	      fFOUR_AM,
        	      fZERO_MINUTES
        	    );
        	    return result.getTime();
        	}
            
        };
        
        timer.scheduleAtFixedRate(task, getTomorrowMorning12am(), fONCE_PER_DAY); 
    }
    
    private Date getTomorrowMorning12am(){
	    Calendar tomorrow = new GregorianCalendar();
	    //過12點就算明天
	    if(tomorrow.get(Calendar.HOUR_OF_DAY)>=12){
	    	tomorrow.add(Calendar.DATE, fONE_DAY);
	    }
	    Calendar result = new GregorianCalendar(
	      tomorrow.get(Calendar.YEAR),
	      tomorrow.get(Calendar.MONTH),
	      tomorrow.get(Calendar.DATE),
	      fFOUR_AM,
	      fZERO_MINUTES
	    );
	    return result.getTime();
	}
    
}
