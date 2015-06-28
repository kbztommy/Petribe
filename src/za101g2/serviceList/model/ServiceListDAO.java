package za101g2.serviceList.model;

import hibernate.util.HibernateUtil;

import java.util.List;

import org.hibernate.*;

public class ServiceListDAO implements ServiceListDAO_interface{
	
	private static final String GET_ALL_STMT = "FROM ServiceListVO ORDER BY chargeType";
	private static final String GET_REQUIRED = "FROM ServiceListVO WHERE storeVO.id =? AND petType =? AND chargeType =?";
	private static final String GET_CUSTOM_SERVICE = "FROM ServiceListVO WHERE storeVO.id=? AND petType=? AND chargeTYPE in ('perDay','perTime')";
	private static final String GET_STOREID_ONE = "SELECT distinct storeVO.id FROM ServiceListVO WHERE chargeType='required' AND PETTYPE =? AND isOnsale='y' ORDER BY storeVO.id";
	private static final String GET_STOREID_MIX = "select storeVO.id from ServiceListVO where chargeType='required' and isOnsale='y' group by storeVO.id having count(*)>1 order by storeVO.id";
	private static final String GET_STOREID_ALL = "SELECT distinct storeVO.id FROM ServiceListVO WHERE chargeType='required' AND isOnsale='y' ORDER BY storeVO.id";
	private static final String GET_MIN_PRICE = "SELECT MIN(price) FROM ServiceListVO WHERE chargeType='required' and storeVO.id=?";
	private static final String GET_ALL_ONSALE_CUST = "FROM ServiceListVO WHERE chargeType IN ('perTime','perDay') AND isOnsale = 'y' AND storeVO.id = ?";
	private static final String GET_ONSALE_SERVICE="FROM ServiceListVO WHERE storeVO.id=? AND petType=? AND chargeType=? AND isOnsale='y'";
	@Override
	public void insert(ServiceListVO serviceListVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(serviceListVO);
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void update(ServiceListVO serviceListVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(serviceListVO);
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
			ServiceListVO serviceListVO = (ServiceListVO) session.get(ServiceListVO.class, id);
			session.delete(serviceListVO);
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}		
	}

	@Override
	public ServiceListVO findByPrimaryKey(Integer id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		ServiceListVO serviceListVO = null;
			try{
				session.beginTransaction();
				serviceListVO = (ServiceListVO) session.get(ServiceListVO.class, id);			
				session.getTransaction().commit();
			}catch(RuntimeException ex){
				session.getTransaction().rollback();
				throw ex;
			}	
		return serviceListVO;
	}

	@Override
	public List<ServiceListVO> getAll() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<ServiceListVO> list =null;
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
	public ServiceListVO getService(Integer storeId, String petType,
			String chargeType) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		ServiceListVO serviceListVO = null;
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_REQUIRED);
			query.setParameter(0,storeId);
			query.setParameter(1,petType);
			query.setParameter(2,chargeType);
			serviceListVO = (ServiceListVO) query.uniqueResult();			
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}	
		return serviceListVO;
	}

	@Override
	public List<ServiceListVO> getCustomSrv(Integer storeId, String petType) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<ServiceListVO> list = null;
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_CUSTOM_SERVICE);
			query.setParameter(0, storeId);
			query.setParameter(1, petType);
			list =  query.list();			
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}	
		return list;
	}

	@Override
	public List<Integer> storeIdRequired(String petType) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<Integer> list = null;		
		
		try{
			Query query =null;
			session.beginTransaction();
			if ("3".equals(petType)) {				
				query = session.createQuery(GET_STOREID_MIX);	
			} else if ("2".equals(petType)) {				
				query = session.createQuery(GET_STOREID_ONE);	
				query.setParameter(0, "cat");
			} else {				
				query = session.createQuery(GET_STOREID_ONE);	
				query.setParameter(0, "dog");
			}
			
			list =  query.list();
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}	
		return list;
	}

	@Override
	public List<Integer> getAllStoreId() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<Integer> list = null;
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_STOREID_ALL);
			list =  query.list();
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public Integer getMinPrice(Integer storeId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Integer price =null;
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_MIN_PRICE);
			query.setParameter(0,storeId);			
			price = (Integer) query.uniqueResult();
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return price;
	}

	@Override
	public List<ServiceListVO> getAllOnsaleCust(Integer storeId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<ServiceListVO> list = null;
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_ONSALE_CUST);
			query.setParameter(0,storeId);
			list = query.list();
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public ServiceListVO getOnsaleService(Integer storeId, String petType,
			String chargeType) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		ServiceListVO serviceListVO = null;
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_ONSALE_SERVICE);
			query.setParameter(0,storeId);
			query.setParameter(1,petType);
			query.setParameter(2,chargeType);			
			serviceListVO = (ServiceListVO) query.uniqueResult();
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return serviceListVO;
	}
	
}
