package za101g2.util;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import za101g2.orderBoard.model.OrderBoardService;
import za101g2.orderBoard.model.OrderBoardVO;

public class OrderRequestListener implements  ServletRequestListener{
	
	OrderBoardVO orderBoardVO = null;
	Integer petNumber =null;
	List<String> boardDayList=null;
	
	public OrderRequestListener(OrderBoardVO orderBoardVO,Integer petNumber,List<String> boardDayList){
		this.orderBoardVO = orderBoardVO;
		this.petNumber = petNumber;
		this.boardDayList= boardDayList;
	}
	
	@Override
	public void requestDestroyed(ServletRequestEvent e) {
		System.out.println("[" + new Date() + "] BOUND" );
               
	}

	@Override
	public void requestInitialized(ServletRequestEvent arg0) {
		Integer orderId = orderBoardVO.getId();
		OrderBoardService orderBoardSrv = new OrderBoardService();
		String  status = orderBoardSrv.getOne(orderId).getStatus();	
		System.out.println("orderId="+orderId+",boardDayList="+boardDayList.size()+"petNumber="+petNumber);
		if("0".equals(status)){
			orderBoardSrv.deleteWithCal(orderId,boardDayList,petNumber);
			System.out.println("訂單已移除");
		}
		
	}

}
