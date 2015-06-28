package za101g2.orderList.model;

import hibernate.util.HibernateUtil;

import java.sql.Connection;
import java.util.*;

import org.hibernate.*;

public class OrderListDAO implements OrderListDAO_interface{

	private static final String GET_ONE_STMT = "FROM OrderListVO WHERE orderBoardVO.id = ? and serviceListVO.id = ? and petName = ?";
	private static final String GET_ALL_STMT = "FROM OrderListVO";
	private static final String GET_PETNAMES = "select distinct petName from OrderListVO where orderBoardVO.id = ?";
	private static final String GET_ONE_BY_SRV = "select distinct petName from OrderListVO where serviceListVO.id = ? and orderBoardVO.id = ?";
	@Override
	public void insert(OrderListVO orderListVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(orderListVO);
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}		
	}

	@Override
	public void update(OrderListVO orderListVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(orderListVO);
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}				
	}

	@Override
	public void delete(Integer orderId, Integer serviceId, String petName) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		OrderListVO orderListVO =null;
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_ONE_STMT);
			query.setParameter(0, orderId);
			query.setParameter(1, serviceId);
			query.setParameter(2, petName);				
			orderListVO = (OrderListVO)query.uniqueResult();
			session.delete(orderListVO);				
			
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		
	}

	@Override
	public OrderListVO findByPrimaryKey(Integer orderId, Integer serviceId,
			String petName) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		OrderListVO orderListVO =null;
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_ONE_STMT);
			query.setParameter(0, orderId);
			query.setParameter(1, serviceId);
			query.setParameter(2, petName);			
			orderListVO = (OrderListVO)query.uniqueResult();
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return orderListVO;
	}

	@Override
	public List<OrderListVO> getAll() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<OrderListVO> list =null;
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
	public void insert(Connection con, OrderListVO orderListVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertByHibernate(OrderListVO orderListVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(orderListVO);
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}		
	}

	@Override
	public Set<String> getPetNames(Integer orderId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Set<String> set = null;
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_PETNAMES);
			query.setParameter(0, orderId);
			set = new LinkedHashSet<String>(query.list());
			session.getTransaction().commit();			
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return set;
	}

	@Override
	public List<String> getAllbyServiceId(Integer serviceId,Integer orderId) {
		List<String> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_ONE_BY_SRV);
			query.setParameter(0, serviceId);
			query.setParameter(1, orderId);
			list = query.list();
			session.getTransaction().commit();			
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}		
		return list;
	}

}
