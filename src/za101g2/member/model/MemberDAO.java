package za101g2.member.model;



import hibernate.util.HibernateUtil;

import java.util.List;

import org.hibernate.*;

public class MemberDAO implements MemberDAO_interface{

	private static final String GET_ALL_STMT = "From MemberVO order by id";
	private static final String GET_SOME = "From MemberVO WHERE email LIKE :email order by id";
	private static final String GET_ONE_BY_EMAIL = 
			"SELECT count(*) FROM MemberVO WHERE email=:email";
	private static final String GET_ONE_BY_PHONE = 
			"SELECT count(*) FROM MemberVO WHERE phone=:phone";
	private static final String LOGIN = 
			"FROM MemberVO WHERE email=:email AND password=:password";
	private static final String SET_STATUS = 
			"UPDATE MemberVO SET status=:status WHERE id=:id";
	private static final String GET_MEMBER_BY_EMAIL = 
			"FROM MemberVO WHERE email=:email";
	
	@Override
	public MemberVO insert(MemberVO memberVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(memberVO);
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}	
		return memberVO;
	}

	@Override
	public MemberVO update(MemberVO memberVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(memberVO);
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}	
		return memberVO;
	}

	@Override
	public void delete(Integer id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			MemberVO memberVO = (MemberVO)session.get(MemberVO.class, id);
			session.delete(memberVO);
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		
	}

	@Override
	public MemberVO findByPrimaryKey(Integer id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		MemberVO memberVO = null;
		try{
			session.beginTransaction();
			memberVO = (MemberVO)session.get(MemberVO.class, id);			
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return memberVO;
	}
	
	@Override
	public MemberVO findOneByEmail(String email) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		MemberVO memberVO = null;
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_MEMBER_BY_EMAIL);
			query.setParameter("email", email);
			memberVO = (MemberVO) query.uniqueResult();
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return memberVO;
	}

	@Override
	public List<MemberVO> getAll() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<MemberVO> list = null;
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
	public List<MemberVO> getSome(String email) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<MemberVO> list = null;
		try{
			session.beginTransaction();
			Query query = session.createQuery(GET_SOME);
			query.setParameter("email", '%' + email + '%');
			list = query.list();
			session.getTransaction().commit();
		}catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	public MemberVO updateBySystem(MemberVO memberVO) { //由其他DAO呼叫
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.saveOrUpdate(memberVO);
		} catch (RuntimeException ex) {
			throw ex;
		}
		return memberVO;
	}
	
	@Override
	public void setStatus(Integer id, String status) {	// 必定尤其他DAO呼叫
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			//修改權限。不做commit及rollback，此段通常由其他dao呼叫使用之。
			org.hibernate.Query query = session.createQuery(SET_STATUS);
			query.setParameter("status", status);
			query.setParameter("id", id);
			query.executeUpdate();
			
		} catch (RuntimeException ex) {
			throw ex;
		}
	}
	
	@Override
	public void setStatusByStaff(Integer id, String status) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			//修改權限。不做commit及rollback，此段通常由其他dao呼叫使用之。
			session.beginTransaction();
			org.hibernate.Query query = session.createQuery(SET_STATUS);
			query.setParameter("status", status);
			query.setParameter("id", id);
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}	
	}

	@Override
	public MemberVO login(String email, String password) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		MemberVO memberVO = null;
		try {
			session.beginTransaction();
			org.hibernate.Query query = session.createQuery(LOGIN);
			query.setParameter("email", email);
			query.setParameter("password", password);
			memberVO = (MemberVO) query.uniqueResult();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}		
		return memberVO;
	}
	
	@Override
	public Long getOneByEmail(String email) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Long count;
		try {
			session.beginTransaction();
			org.hibernate.Query query = session.createQuery(GET_ONE_BY_EMAIL);
			query.setParameter("email", email);
			count = (Long) query.uniqueResult();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}		
		return count;
	}
	
	@Override
	public Long getOneByPhone(String phone) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Long count;
		try {
			session.beginTransaction();
			org.hibernate.Query query = session.createQuery(GET_ONE_BY_PHONE);
			query.setParameter("phone", phone);
			count = (Long) query.uniqueResult();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}		
		return count;
	}
	
	@Override
	public MemberVO findByPrimaryKeyBySystem(Integer id) {	// 必定尤其他DAO呼叫
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		MemberVO memberVO = null;
		try {
			memberVO = (MemberVO) session.get(MemberVO.class, id);
		} catch (RuntimeException ex) {
			throw ex;
		}
		return memberVO;
	}
	
	@Override
	public MemberVO findByPrimaryKeyForSystemReLogin(String email, String password) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		MemberVO memberVO = null;
		try {
			session.beginTransaction();
			org.hibernate.Query query = session.createQuery(LOGIN);
			query.setParameter("email", email);
			query.setParameter("password", password);
			memberVO = (MemberVO) query.uniqueResult();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return memberVO;
	}

}
