package za101g2.store.model;

import hibernate.util.HibernateUtil;

import java.util.*;

import org.hibernate.*;

import za101g2.member.model.MemberDAO;

public class StoreDAO implements StoreDAO_interface{

	private static final String GET_ALL_STMT = "FROM StoreVO where status='4' ORDER BY id";
	private static final String GET_ALL_APPLYING = "FROM StoreVO ORDER BY status,applydate";
	private static final String GET_ID_BY_FK = "FROM StoreVO WHERE memberVO.id = ?";
	private static final String GET_ALLID_CITY = "SELECT id FROM StoreVO WHERE address like ? and status='4' ORDER BY id";
	private static final String UPDATE_REPORT = "UPDATE store set siteReport=?, status=?, reportDate=sysdate WHERE id=?";
	
	@Override
	public void insert(StoreVO storeVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();			
			session.saveOrUpdate(storeVO);
			session.getTransaction().commit();			
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}		
	}

	@Override
	public void update(StoreVO storeVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(storeVO);
			session.getTransaction().commit();			
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}		
	}
	
	@Override
	public void rankUp(StoreVO storeVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(storeVO);
			MemberDAO dao = new MemberDAO();
			dao.setStatus(storeVO.getMemberVO().getId(), "3");
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
			StoreVO storeVO = (StoreVO) session.get(StoreVO.class, id);
			session.delete(storeVO);
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}		
	}

	@Override
	public StoreVO findByPrimaryKey(Integer id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		StoreVO storeVO = null;
		try{
			session.beginTransaction();
			storeVO = (StoreVO)session.get(StoreVO.class, id);
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}	
		return storeVO;
	}

	@Override
	public List<StoreVO> getAll() {
		List<StoreVO> list =null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
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
	public List<StoreVO> getApplyAll() {
		List<StoreVO> list =null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_APPLYING);
			list = query.list();
			session.getTransaction().commit();			
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public StoreVO findByForeginKey(Integer id) {
		StoreVO storeVO=null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_ID_BY_FK);
			query.setParameter(0, id);			
			storeVO =(StoreVO) query.uniqueResult();
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return storeVO;
	}

	@Override
	public List<Integer> getAllIdbyCity(String city) {
		List<Integer> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_ALLID_CITY);
			query.setParameter(0, "%"+city+"%");
			list =(List<Integer>)query.list();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}


}
