package za101g2.phonevalidate.model;

import hibernate.util.HibernateUtil;

import org.hibernate.Session;

import za101g2.member.model.MemberDAO;
import za101g2.member.model.MemberVO;

public class PhoneValidateDAO implements PhoneValidateDAO_interface {

	private static final String EMAILVALIDATE_LEVEL =
			"1";
	private static final String PHONEVALIDATE_LEVEL =
			"2";
	private static final String GET_ONE_BY_PHONE = 
			"SELECT count(*) FROM PhoneValidateVO WHERE phone=:phone";
	
	@Override
	public PhoneValidateVO insert(PhoneValidateVO phoneValidateVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(phoneValidateVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return phoneValidateVO;
	}
	
	@Override
	public PhoneValidateVO update(Integer memId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		PhoneValidateVO phoneValidateVO = null;
		try {
			session.beginTransaction();
			phoneValidateVO = (PhoneValidateVO) session.get(PhoneValidateVO.class, memId);
			phoneValidateVO.setErrorCount(phoneValidateVO.getErrorCount()+1);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return phoneValidateVO;
	}

	@Override
	public void delete(Integer memId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			PhoneValidateVO phoneValidateVO = (PhoneValidateVO) session.get(PhoneValidateVO.class, memId);
			session.delete(phoneValidateVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}
	
	@Override
	public PhoneValidateVO findByPrimaryKey(Integer memId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		PhoneValidateVO phoneValidateVO = null;
		try {
			session.beginTransaction();
			phoneValidateVO = (PhoneValidateVO) session.get(PhoneValidateVO.class, memId);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return phoneValidateVO;
	}
	
	@Override
	public Long findByPhone(String phone) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Long count;
		try {
			session.beginTransaction();
			org.hibernate.Query query = session.createQuery(GET_ONE_BY_PHONE);
			query.setParameter("phone", phone);
			count = (Long) query.uniqueResult();
			session.beginTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}		
		return count;
	}

	@Override
	public void validate(PhoneValidateVO phoneValidateVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		MemberVO memberVO = null;
		try {
			session.beginTransaction();
			
			//先把驗證資料取出。
			//※傳來過來的phoneValidateVO只有memId和validateCode，利用memId把完整資料撈出來。
			phoneValidateVO = (PhoneValidateVO) session.get(PhoneValidateVO.class, phoneValidateVO.getMemId());
			
			
			//權限等級修改及手機資料修改。
			
			//1、需要先把該會員的會員資料取出。
			MemberDAO dao = new MemberDAO();
			
			memberVO = dao.findByPrimaryKeyBySystem(phoneValidateVO.getMemId());
			//2、確認權限等級，如果是通過E-Mail(Status==1)的情況才有可能進行等級提升，其他則無修改問題。
			if(memberVO.getStatus().equals(EMAILVALIDATE_LEVEL)){
			memberVO.setStatus(PHONEVALIDATE_LEVEL);
			}
			//3、修改手機資料。把存入的手機號碼丟進取出來的memberVO，然後進行更新。
			memberVO.setPhone(phoneValidateVO.getPhone());
			//3、更新。
			dao.updateBySystem(memberVO);
			
			//將資料庫的驗證資料刪除。
			session.delete(phoneValidateVO);
			
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		
	}

}
