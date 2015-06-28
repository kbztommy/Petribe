package za101g2.orderList.model;
import java.sql.Connection;
import java.util.*;

public interface OrderListDAO_interface {
	void insert(OrderListVO orderListVO);
	void update(OrderListVO orderListVO);
	void delete(Integer orderId,Integer serviceId,String petName);
	OrderListVO findByPrimaryKey(Integer orderId,Integer serviceId,String petName);
	List<OrderListVO> getAll();
	void insert(Connection con,OrderListVO orderListVO);
	void insertByHibernate(OrderListVO orderListVO);
	Set<String> getPetNames(Integer OrderId);
	public List<String> getAllbyServiceId(Integer serviceId,Integer orderId);
}
