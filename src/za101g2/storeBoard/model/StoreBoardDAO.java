package za101g2.storeBoard.model;

import hibernate.util.HibernateUtil;

import java.util.List;

import org.hibernate.*;

public class StoreBoardDAO implements StoreBoardDAO_interface{

	private static final String GET_ALL_STMT ="from StoreBoardVO order by id";
	private static final String GET_UNDELETE ="from StoreBoardVO where storeVO.id=? and isDelete='n' order by id desc";
	private static final String GET_MoreUNDELETE ="from StoreBoardVO where storeVO.id=? and isDelete='n' and id<? order by id desc";
	@Override
	public StoreBoardVO insert(StoreBoardVO storeBoardVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();		 
		try{
			session.beginTransaction();
			session.saveOrUpdate(storeBoardVO);
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return storeBoardVO; 
	}

	@Override
	public void update(StoreBoardVO storeBoardVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(storeBoardVO);
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
			StoreBoardVO storeBoardVO = (StoreBoardVO) session.get(StoreBoardVO.class, id);
			session.delete(storeBoardVO);
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		
	}

	@Override
	public StoreBoardVO findByPrimaryKey(Integer id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		StoreBoardVO storeBoardVO =null;
		try{
			session.beginTransaction();
			storeBoardVO = (StoreBoardVO) session.get(StoreBoardVO.class, id);			
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return storeBoardVO;
	}

	@Override
	public List<StoreBoardVO> getAll() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<StoreBoardVO> list =null;
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
	
	public List<StoreBoardVO> getUndelete(Integer storeId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<StoreBoardVO> list =null;
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_UNDELETE);
			query.setMaxResults(5);
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
	public List<StoreBoardVO> getMoreUndelete(Integer storeId, Integer from,
			Integer count) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<StoreBoardVO> list =null;
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_MoreUNDELETE);
			query.setParameter(0, storeId);
			query.setParameter(1, from);
			query.setMaxResults(count);
			list = query.list();
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

}
