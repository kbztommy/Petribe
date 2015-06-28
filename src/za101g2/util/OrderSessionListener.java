package za101g2.util;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import za101g2.orderBoard.model.*;

public class OrderSessionListener implements HttpSessionBindingListener{

	OrderBoardVO orderBoardVO = null;
	Integer petNumber =null;
	List<String> boardDayList=null;
	
	public OrderSessionListener(OrderBoardVO orderBoardVO,Integer petNumber,List<String> boardDayList){
		this.orderBoardVO = orderBoardVO;
		this.petNumber = petNumber;
		this.boardDayList= boardDayList;
	}
	
	@Override
	public void valueBound(HttpSessionBindingEvent e) {
		System.out.println("[" + new Date() + "] BOUND as " + e.getName() +
                " to " + e.getSession().getId());
		
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent e){	
		Integer orderId = orderBoardVO.getId();
		OrderBoardService orderBoardSrv = new OrderBoardService();
		if(orderBoardSrv.getOne(orderId)==null){
			return;
		}else{
			String  status = orderBoardSrv.getOne(orderId).getStatus();	
			System.out.println("orderId="+orderId+",boardDayList="+boardDayList.size()+"petNumber="+petNumber);
			if("0".equals(status)){
				orderBoardSrv.deleteWithCal(orderId,boardDayList,petNumber);
				System.out.println("[" + new Date() + "] UNBOUND as " + e.getName() +
		                " from " + e.getSession().getId());
				System.out.println("訂單已移除");
			}
		}
	}

}
