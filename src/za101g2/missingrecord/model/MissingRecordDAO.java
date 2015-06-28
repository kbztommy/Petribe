package za101g2.missingrecord.model;

import hibernate.util.HibernateUtil;

import java.util.*;

import org.hibernate.Query;
import org.hibernate.classic.Session;

public class MissingRecordDAO implements MissingRecordDAO_interface {

	private static final String GET_ALL_STMT = "from MissingRecordVO order by missingDate desc";
	private static final String GET_ONE_BY_PETID = "from MissingRecordVO where petId = ?";
	private static final String GET_ALL_BY_CITY = "from MissingRecordVO where location like ? order by missingDate desc";
	private static final String GET_ALL_BY_CITY_BOUNTY = "from MissingRecordVO where bounty > 0 and location like ? order by missingDate desc";
	private static final String GET_ALL_BY_CITY_NO_BOUNTY = "from MissingRecordVO where bounty = 0 and location like ? order by missingDate desc";
	private static final String GET_ALL_BOUNTY = "from MissingRecordVO where bounty > 0 order by missingDate desc";
	private static final String GET_ALL_NO_BOUNTY = "from MissingRecordVO where bounty = 0 order by missingDate desc";

	@Override
	public void insert(MissingRecordVO missingRecordVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(missingRecordVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void update(MissingRecordVO missingRecordVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(missingRecordVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void delete(Integer id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			MissingRecordVO missingRecordVO = (MissingRecordVO) session.get(MissingRecordVO.class, id);
			session.delete(missingRecordVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public MissingRecordVO findByPrimaryKey(Integer id) {
		MissingRecordVO missingRecordVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			missingRecordVO = (MissingRecordVO) session.get(MissingRecordVO.class, id);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return missingRecordVO;
	}
	
	@Override
	public MissingRecordVO findByPetId(Integer petId) {
		MissingRecordVO missingRecordVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ONE_BY_PETID);
			query.setParameter(0, petId);
			missingRecordVO = (MissingRecordVO) query.uniqueResult();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return missingRecordVO;
	}
	
	@Override
	public List<MissingRecordVO> findByCity(String city) {
		List<MissingRecordVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_BY_CITY);
			query.setParameter(0, city + "%");
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	
	@Override
	public List<MissingRecordVO> findByCityBounty(String city) {
		List<MissingRecordVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_BY_CITY_BOUNTY);
			query.setParameter(0, city + "%");
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	
	@Override
	public List<MissingRecordVO> findByCityNoBounty(String city) {
		List<MissingRecordVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_BY_CITY_NO_BOUNTY);
			query.setParameter(0, city + "%");
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public List<MissingRecordVO> getAllBounty() {
		List<MissingRecordVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_BOUNTY);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	
	@Override
	public List<MissingRecordVO> getAllNoBounty() {
		List<MissingRecordVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_NO_BOUNTY);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	
	@Override
	public List<MissingRecordVO> getAll() {
		List<MissingRecordVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_STMT);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
}
