package za101g2.member.model;
import java.util.*;

public interface MemberDAO_interface {
	MemberVO insert(MemberVO memberVO);
	MemberVO update(MemberVO memberVO);
	void delete(Integer id);
    MemberVO findByPrimaryKey(Integer id);
	List<MemberVO> getAll();
	public MemberVO login(String email, String password);
	public MemberVO updateBySystem(MemberVO memberVO);
	public void setStatus(Integer id, String status);
	public void setStatusByStaff(Integer id, String status);
	public Long getOneByEmail(String email);
	public Long getOneByPhone(String phone);
	public MemberVO findByPrimaryKeyBySystem(Integer id);
	public MemberVO findOneByEmail(String email);
	public MemberVO findByPrimaryKeyForSystemReLogin(String email, String password);	//暫無使用
	public List<MemberVO> getSome(String email);
}
