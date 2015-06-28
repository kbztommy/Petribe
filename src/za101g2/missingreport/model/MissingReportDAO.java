package za101g2.missingreport.model;

import hibernate.util.HibernateUtil;
import java.util.*;
import org.hibernate.Query;
import org.hibernate.classic.Session;

public class MissingReportDAO implements MissingReportDAO_interface {
	
	private static final String GET_ALL_STMT = "from MissingReportVO order by reportDate desc";
	private static final String GET_ONE_STMT = "from MissingReportVO where memId = ? and replyId = ?";
	private static final String GET_ALL_BY_MEMID = "from MissingReportVO where memId = ? order by reportDate desc";
	private static final String GET_ONE_BY_REPLYID = "from MissingReportVO where replyId = ?";
	private static final String DELETE_ONE = "delete MissingReportVO where memId = ? and replyId = ?";

	@Override
	public void insert(MissingReportVO missingReportVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(missingReportVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void update(MissingReportVO missingReportVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(missingReportVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void delete(Integer memId, Integer replyId) {
//		MissingReportVO missingReportVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(DELETE_ONE);
			query.setParameter(0, memId);
			query.setParameter(1, replyId);
			query.executeUpdate();
//			missingReportVO = (MissingReportVO) query.uniqueResult();
//			session.delete(missingReportVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public MissingReportVO findByPrimaryKey(Integer memId, Integer replyId) {
		MissingReportVO missingReportVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ONE_STMT);
			query.setParameter(0, memId);
			query.setParameter(1, replyId);
			missingReportVO = (MissingReportVO) query.uniqueResult();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return missingReportVO;
	}
	
	@Override
	public MissingReportVO findByReplyId(Integer replyId) {
		MissingReportVO missingReportVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ONE_BY_REPLYID);
			query.setParameter(0, replyId);
			missingReportVO = (MissingReportVO) query.uniqueResult();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return missingReportVO;
	}	
	
	@Override
	public Set<MissingReportVO> findByMemId(Integer memId) {
		Set<MissingReportVO> set = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_BY_MEMID);
			query.setParameter(0, memId);
			List list = query.list();
			set = new LinkedHashSet<MissingReportVO>(list);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return set;
	}

	@Override
	public List<MissingReportVO> getAll() {
		List<MissingReportVO> list = new ArrayList<MissingReportVO>();
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