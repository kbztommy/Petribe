package za101g2.storePhoto.model;

import hibernate.util.HibernateUtil;

import java.util.List;

import org.hibernate.*;


public class StorePhotoDAO implements StorePhotoDAO_interface{
	
	private static final String STMT_GET_ALL = "from StorePhotoVO order-by id";
	private static final String STMT_GET_STORE = "from StorePhotoVO where onServiceVO.id is null and storeVO.id=? order by id asc";
	@Override
	public StorePhotoVO insert(StorePhotoVO storePhotoVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();		
		try{
			session.beginTransaction();
			session.saveOrUpdate(storePhotoVO);			
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return storePhotoVO;
	}

	@Override
	public void update(StorePhotoVO storePhotoVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(storePhotoVO);
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
			StorePhotoVO storePhotoVO =(StorePhotoVO) session.get(StorePhotoVO.class,id);
			session.delete(storePhotoVO);			
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}		
		
	}

	@Override
	public StorePhotoVO findByPrimaryKey(Integer id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		StorePhotoVO storePhotoVO = null;
		try{
			session.beginTransaction();
			storePhotoVO =(StorePhotoVO) session.get(StorePhotoVO.class,id);				
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}		
		return storePhotoVO;
	}

	@Override
	public List<StorePhotoVO> getAll() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<StorePhotoVO> list = null;
		try{
			session.beginTransaction();
			Query query = session.createQuery(STMT_GET_ALL);
			list = query.list();		
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}		
		return list;
	}

	@Override
	public List<StorePhotoVO> getStorePic(Integer storeId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<StorePhotoVO> list = null;
		try{
			session.beginTransaction();
			Query query = session.createQuery(STMT_GET_STORE);
			query.setParameter(0, storeId);
			list = query.list();		
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}		
		return list;
	}

}
