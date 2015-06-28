package za101g2.orderBoard.model;

import hibernate.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.*;

import org.hibernate.*;

import com.sun.jmx.snmp.Timestamp;

import za101g2.orderList.model.OrderListDAO;
import za101g2.orderList.model.OrderListVO;
import za101g2.storeCalendar.model.StoreCalendarDAO;
import za101g2.storeCalendar.model.StoreCalendarVO;


public class OrderBoardDAO implements OrderBoardDAO_interface{
	
	
	private static final String GET_ALL_STMT = "from OrderBoardVO order by id";	
	private static final String GET_TOMORROW ="from OrderBoardVO where to_char(startDate,'yyyy-MM-dd') =?";
	private static final String GET_MEMBER_ORDER = "from OrderBoardVO where memberVO.id=? order by id desc";
	private static final String GET_STORE_ORDER = "from OrderBoardVO where storeVO.id=? order by id desc";
	private static final String GET_ALL_REPORT = "from OrderBoardVO where isReport = 'y'";
	private static final String STORE_ORDER_WITH_STATUS = "from OrderBoardVO where storeVO.id=? and status = ? order by id desc";
	@Override
	public void insert(OrderBoardVO orderBoardVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(orderBoardVO);
			session.getTransaction().commit();			
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		
	}

	@Override
	public void update(OrderBoardVO orderBoardVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(orderBoardVO);
			session.getTransaction().commit();			
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		
	}

	@Override
	public void delete(Integer id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
//			Query query = session.createQuery(GET_ONE_STMT);
//			query.setParameter(0, id);
//			OrderBoardVO orderBoardVO = (OrderBoardVO)query.uniqueResult();			
			OrderBoardVO orderBoardVO = (OrderBoardVO) session.get(OrderBoardVO.class,id);		
//			orderBoardVO.getMemberVO().getOrderBoards().remove(orderBoardVO);
//			orderBoardVO.getStoreVO().getOrderBoards().remove(orderBoardVO);
//			orderBoardVO.setMemberVO(null);
//			orderBoardVO.setStoreVO(null);
//			for(OrderListVO o:orderBoardVO.getOrderLists()){
//				o.getServiceListVO().getOrderLists().remove(o);
//				o.setServiceListVO(null);
//			}			
			session.delete(orderBoardVO);
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		
	}

	@Override
	public OrderBoardVO findByPrimaryKey(Integer id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		OrderBoardVO orderBoardVO =null;
		try{
			session.beginTransaction();
			orderBoardVO = (OrderBoardVO) session.get(OrderBoardVO.class,id);
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return orderBoardVO;
	}

	@Override
	public List<OrderBoardVO> getAll() {
		List<OrderBoardVO> list =null;
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_STMT);
			list = query.list();
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public OrderBoardVO insertWithOrderList(OrderBoardVO orderBoardVO,List<StoreCalendarVO> storeCalendarVOList, Integer petNumber) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{			
			
			session.beginTransaction();
			session.saveOrUpdate(orderBoardVO);			
			
			StoreCalendarDAO dao1 = new StoreCalendarDAO();			
			for (StoreCalendarVO aStoreCalendarVO : storeCalendarVOList) {
				Integer curQuantitly = aStoreCalendarVO.getCurQuantitly()+petNumber;
				if(curQuantitly>aStoreCalendarVO.getMaxQuantitly()) throw new ArithmeticException(aStoreCalendarVO.getServiceDate().toString()+"訂位已滿");
				aStoreCalendarVO.setCurQuantitly(curQuantitly);
				dao1.updateByHibernate(aStoreCalendarVO);
			}
			
			session.getTransaction().commit();			
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		
		return orderBoardVO;
	}

	@Override
	public List<OrderBoardVO> getTomorrowList(Calendar calendar) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<OrderBoardVO> list= null;
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		String strCal = dateFormater.format(calendar.getTime());
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_TOMORROW);
			query.setParameter(0, strCal);
			list = query.list();
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public void deleteWithCal(Integer id, List<StoreCalendarVO> list) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			OrderBoardVO orderBoardVO = (OrderBoardVO) session.get(OrderBoardVO.class,id);
			session.delete(orderBoardVO);
			StoreCalendarDAO dao1 = new StoreCalendarDAO();			
			for (StoreCalendarVO aStoreCalendarVO : list) {				
				if(aStoreCalendarVO.getCurQuantitly()<0) throw new ArithmeticException(aStoreCalendarVO.getServiceDate().toString()+"發生異常，無法取消訂單");				
				dao1.updateByHibernate(aStoreCalendarVO);
			}
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		
	}

	@Override
	public List<OrderBoardVO> getMemberOrder(Integer memId) {
		List<OrderBoardVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_MEMBER_ORDER);
			query.setParameter(0, memId);
			list = query.list();
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public List<OrderBoardVO> getStoreOrder(Integer storeId) {
		List<OrderBoardVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_STORE_ORDER);
			query.setParameter(0, storeId);
			list = query.list();
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public List<OrderBoardVO> getAllReport() {
		List<OrderBoardVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_REPORT);
			list = query.list();
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public List<OrderBoardVO> getStoreOrderWithStatus(Integer storeId,String status) {
		List<OrderBoardVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			Query query = session.createQuery(STORE_ORDER_WITH_STATUS);
			query.setParameter(0, storeId);
			query.setParameter(1, status);
			list = query.list();
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	
	

}
