package za101g2.member.model;

import java.util.List;

public class MemberService {
	private MemberDAO_interface dao;
	
	public MemberService(){
		dao = new MemberDAO();
	}
	
	public List<MemberVO> getAll(){
		return dao.getAll();
	}
	
	public List<MemberVO> getSome(String email){
		return dao.getSome(email);
	}
	
	public MemberVO getOneMember(Integer id){
		return dao.findByPrimaryKey(id);
	}
	
	public MemberVO addMember(String email,String password,String nickname,
			String lastname,String firstname,String sex){
		
		MemberVO memberVO = new MemberVO();
		
		memberVO.setEmail(email);
		memberVO.setPassword(password);
		memberVO.setNickname(nickname);
		memberVO.setLastname(lastname);
		memberVO.setFirstname(firstname);
		memberVO.setSex(sex);
		memberVO = dao.insert(memberVO);
		
		return memberVO;
	}
	
	public MemberVO login(String email, String password){
		return dao.login(email, password);
	}
	
	public MemberVO systemReLogin(String email, String password){
		return dao.findByPrimaryKeyForSystemReLogin(email, password);
	}
	
	public MemberVO updateNickname(String nickname, Integer id) {
		MemberVO memberVO = new MemberVO();
		memberVO = dao.findByPrimaryKey(id);
		memberVO.setNickname(nickname);
		return dao.update(memberVO);
	}
	
	public MemberVO updateIcon(byte[] icon, Integer id) {
		MemberVO memberVO = new MemberVO();
		memberVO = dao.findByPrimaryKey(id);
		memberVO.setIcon(icon);
		return dao.update(memberVO);
	}
	
	public MemberVO updatePassword(String password, Integer id) {
		MemberVO memberVO = new MemberVO();
		memberVO = dao.findByPrimaryKey(id);
		memberVO.setPassword(password);
		return dao.update(memberVO);
	}
	
	public MemberVO findOneByPrimaryKey(Integer id){
		return dao.findByPrimaryKey(id);
		
	}
	
	public MemberVO findOneByEmail(String email){
		return dao.findOneByEmail(email);
	}
	
	public Long getOneByEmail(String email){
		return dao.getOneByEmail(email);
	}
	
	public Long getOneByPhone(String phone){
		return dao.getOneByPhone(phone);
	}
	
	public void setStatus(Integer id, String status){
		dao.setStatusByStaff(id, status);
	}
}
