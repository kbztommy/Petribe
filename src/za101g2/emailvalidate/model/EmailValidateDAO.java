package za101g2.emailvalidate.model;

import hibernate.util.HibernateUtil;

import org.hibernate.Session;

import za101g2.member.model.MemberDAO;

public class EmailValidateDAO implements EmailValidateDAO_interface {

	private static final String DELETE_CODE = 
			"DELETE FROM EmailValidateVO WHERE validatecode=:validateCode";
	private static final String GET_ONE = 
			"FROM EmailValidateVO WHERE validatecode=:validateCode";
	private static final String EMAILVALIDATE_LEVEL =
			"1";
	
	@Override
	public EmailValidateVO insert(EmailValidateVO emailValidateVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(emailValidateVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return emailValidateVO;		
	}

	@Override
	public void delete(String validateCode) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			org.hibernate.Query query = session.createQuery(DELETE_CODE);
			query.setParameter("validateCode", validateCode);
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		
	}

	@Override
	public void validate(String validateCode) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			
			//先把驗證資料取出。
			org.hibernate.Query query = session.createQuery(GET_ONE);
			query.setParameter("validateCode", validateCode);
			EmailValidateVO emailValidateVO = (EmailValidateVO) query.uniqueResult();
			
			//提高會員權限。
			MemberDAO dao = new MemberDAO();
			dao.setStatus(emailValidateVO.getMemId(), EMAILVALIDATE_LEVEL);
			
			//將資料庫的驗證資料刪除。
			query = session.createQuery(DELETE_CODE);
			query.setParameter("validateCode", validateCode);
			query.executeUpdate();
			
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public EmailValidateVO findByMemId(Integer memId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		EmailValidateVO emailValidateVO = null;
		try {
			session.beginTransaction();
			emailValidateVO = (EmailValidateVO) session.get(EmailValidateVO.class, memId);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return emailValidateVO;	
	}

}
