package za101g2.orderList.model;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

public class OrderListService {
	OrderListDAO_interface dao = null;

	public OrderListService() {
		dao = new OrderListDAO();
	}
	
	public void insert(OrderListVO orderListVO){
		dao.insert(orderListVO);
	}
	public void update(OrderListVO orderListVO){
		dao.update(orderListVO);
	}
	public void delete(Integer orderId,Integer serviceId,String petName){
		dao.delete(orderId, serviceId, petName);
	}
	public OrderListVO findByPrimaryKey(Integer orderId,Integer serviceId,String petName){
		return dao.findByPrimaryKey(orderId, serviceId, petName);
	}
	
	public List<OrderListVO> getAll(){
		return dao.getAll();
	}
	public void insert(Connection con,OrderListVO orderListVO){
		
	}
	public void insertByHibernate(OrderListVO orderListVO){
		dao.insertByHibernate(orderListVO);
	}
	public Set<String> getPetNames(Integer orderId){
		return dao.getPetNames(orderId);
	}
	
	public List<String> getAllbyServiceId(Integer serviceId,Integer orderId){
		return dao.getAllbyServiceId(serviceId,orderId);
	}
	
}
