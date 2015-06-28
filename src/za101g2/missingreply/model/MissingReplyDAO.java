package za101g2.missingreply.model;

import hibernate.util.HibernateUtil;

import java.util.*;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import za101g2.missingrecord.model.MissingRecordVO;

public class MissingReplyDAO implements MissingReplyDAO_interface {
	
	private static final String GET_ALL_STMT = "from MissingReplyVO order by id";
	private static final String GET_ALL_BY_RECORDID = "from MissingReplyVO where recordId = ? order by reportDate desc";
	private static final String GET_ALL_BY_MEMID = "from MissingReplyVO where memId = ? order by reportDate desc";
	private static final String GET_ONE_BY_RECORDID_MEMID = "from MissingReplyVO where recordId = ? and memId = ?";
	private static final String DELETE_ONE = "delete MissingReplyVO where id = ?";
	
	@Override
	public void insert(MissingReplyVO missingReplyVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(missingReplyVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void update(MissingReplyVO missingReplyVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(missingReplyVO);
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
			Query query = session.createQuery(DELETE_ONE);
			query.setParameter(0, id);
			query.executeUpdate();
//			MissingReplyVO missingReplyVO = (MissingReplyVO) session.get(MissingReplyVO.class, id);
//			session.delete(missingReplyVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}
	
	@Override
	public MissingReplyVO findByPrimaryKey(Integer id) {
		MissingReplyVO missingReplyVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			missingReplyVO = (MissingReplyVO) session.get(MissingReplyVO.class, id);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return missingReplyVO;
	}
	
	@Override
	public MissingReplyVO findByRecordIdMemId(Integer recordId, Integer memId) {
		MissingReplyVO missingReplyVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ONE_BY_RECORDID_MEMID);
			query.setParameter(0, recordId);
			query.setParameter(1, memId);
			missingReplyVO = (MissingReplyVO) query.uniqueResult();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return missingReplyVO;
	}

	@Override
	public Set<MissingReplyVO> findByRecordId(Integer recordId) {
		Set<MissingReplyVO> set = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_BY_RECORDID);
			query.setParameter(0, recordId);
			List list = query.list();
			set = new LinkedHashSet<MissingReplyVO>(list);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return set;
	}
	
	@Override
	public Set<MissingReplyVO> findByMemId(Integer memId) {
		Set<MissingReplyVO> set = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_BY_MEMID);
			query.setParameter(0, memId);
			List list = query.list();
			set = new LinkedHashSet<MissingReplyVO>(list);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return set;
	}

	@Override
	public List<MissingReplyVO> getAll() {
		List<MissingReplyVO> list = null;
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