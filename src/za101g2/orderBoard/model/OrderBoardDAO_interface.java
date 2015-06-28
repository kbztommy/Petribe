package za101g2.orderBoard.model;
import java.util.*;

import za101g2.orderList.model.OrderListVO;
import za101g2.storeCalendar.model.StoreCalendarVO;

public interface OrderBoardDAO_interface {
	void insert(OrderBoardVO orderBoardVO);
	void update(OrderBoardVO orderBoardVO);
	void delete(Integer id);
	OrderBoardVO findByPrimaryKey(Integer id);
	List<OrderBoardVO> getAll();
	OrderBoardVO insertWithOrderList(OrderBoardVO orderBoardVO,List<StoreCalendarVO> storeCalendarVOList,Integer petNumber);
	public List<OrderBoardVO> getTomorrowList(Calendar calendar);
	public void deleteWithCal(Integer id,List<StoreCalendarVO> list);
	public List<OrderBoardVO> getMemberOrder(Integer memId);
	public List<OrderBoardVO> getStoreOrder(Integer storeId);
	public List<OrderBoardVO> getAllReport();
	public List<OrderBoardVO> getStoreOrderWithStatus(Integer storeId,String status);
}
