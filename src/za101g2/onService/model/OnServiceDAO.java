package za101g2.onService.model;

import hibernate.util.HibernateUtil;
import java.util.*;

import org.hibernate.*;

public class OnServiceDAO implements OnServiceDAO_interface{

	private static final String STMT_GET_ALL="from OnservcieVO order by id";
	
	@Override
	public void insert(OnServiceVO onServiceVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(onServiceVO);
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		
	}

	@Override
	public void update(OnServiceVO onServiceVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(onServiceVO);
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
			OnServiceVO onServiceVO =(OnServiceVO)session.get(OnServiceVO.class, id);
			session.delete(onServiceVO);
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		
	}

	@Override
	public OnServiceVO findByPrimaryKey(Integer id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		OnServiceVO onServiceVO = null;
		try{
			session.beginTransaction();
			onServiceVO =(OnServiceVO)session.get(OnServiceVO.class, id);			
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return onServiceVO;
	}
	
	@Override
	public List<OnServiceVO> getAll() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<OnServiceVO> list = null;
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

}
