package za101g2.storeCalendar.model;

import hibernate.util.HibernateUtil;

import java.sql.Connection;
import java.sql.Date;
import java.util.*;

import org.hibernate.*;

public class StoreCalendarDAO implements StoreCalendarDAO_interface{

	private static final String GET_ONE_STMT = "FROM StoreCalendarVO WHERE storeVO.id = ? and serviceDate = ?";
	private static final String GET_ALL_STMT = "FROM StoreCalendarVO ORDER BY storeVO.id";
	private static final String GET_BY_MONTH = "FROM StoreCalendarVO where to_char(serviceDate,'YYYY-MM')=? and storeVO.id=? order by serviceDate";
	
	@Override
	public void insert(StoreCalendarVO storeCalendarVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(storeCalendarVO);
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void update(StoreCalendarVO storeCalendarVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(storeCalendarVO);
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		
	}

	@Override
	public void delete(Integer id, Date serviceDate) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		StoreCalendarVO storeCalendarVO = null;
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_ONE_STMT);
			query.setParameter(1, serviceDate);
			query.setParameter(0, id);			
			storeCalendarVO =(StoreCalendarVO)query.uniqueResult();
			session.delete(storeCalendarVO);			
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}		
	}

	@Override
	public StoreCalendarVO findByPrimaryKey(Integer id, Date serviceDate) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		StoreCalendarVO storeCalendarVO = null;
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_ONE_STMT);
			query.setParameter(1, serviceDate);
			query.setParameter(0, id);				
			storeCalendarVO =(StoreCalendarVO)query.uniqueResult();
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}		
		return storeCalendarVO;
	}

	@Override
	public List<StoreCalendarVO> getAll() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<StoreCalendarVO> list = null;
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
	public void insertMonth(List<StoreCalendarVO> storeCalendarVOList) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			for(StoreCalendarVO storeCalendarVO:storeCalendarVOList){
				session.saveOrUpdate(storeCalendarVO);
			}
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		
	}

	@Override
	public List<StoreCalendarVO> getMonthCalendar(Integer id, int year,
			int month) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<StoreCalendarVO> list = null;
		String strmonth="";
		if(month<10)
			strmonth = year+"-0"+month;
		else
			strmonth += year+"-"+month;	
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_MONTH);
			query.setParameter(0, strmonth);
			query.setParameter(1, id);
			list = query.list();
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public void update(Connection con, StoreCalendarVO storeCalendarVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateByHibernate(StoreCalendarVO storeCalendarVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(storeCalendarVO);
		
		}catch(RuntimeException ex){
//session.getTransaction().rollback();
			throw ex;
		}		
	}

}
