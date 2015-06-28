package za101g2.staff.model;

import java.util.List;

public class StaffService {
	
	private StaffDAO_interface dao;
	
	public StaffService(){
		dao = new StaffDAO();
	}
	
	public String addStaff(String email, String name,String status){
		
		StaffVO staffVO = new StaffVO();
		staffVO.setEmail(email);
		staffVO.setName(name);
		staffVO.setStatus(status);
		String key = dao.insertStaff(staffVO);
		
		return key;
		
	}
	
	public void updateStaffName(Integer id,String name){
		
		StaffVO staffVO = new StaffVO();
		staffVO.setName(name);
		staffVO.setId(id);
		dao.updateStaffName(id, name);
		
	}
	
	public void updateStaffStatus(Integer id,String status){
		
		StaffVO staffVO = new StaffVO();
		staffVO.setStatus(status);
		staffVO.setId(id);
		dao.updateStaffStatus(id, status);
	}
	
	public void updateStaffPassword(Integer id,String password){
		
		StaffVO staffVO = new StaffVO();
		staffVO.setStatus(password);
		staffVO.setId(id);
		dao.updateStaffPassword(id, password);
	}
	
	public StaffVO getOneStaff(Integer id) {
		return dao.findByPrimaryKey(id);
	}
	
	public Long emailBeUsed(String email){
		return dao.findByEmail(email);
	}
	
	public List<StaffVO> getAll(){
		return dao.getAll();
	}
	
	public List<StaffVO> getSome(String keyword){
		return dao.getSome(keyword);
	}
	
	public StaffVO login(String email, String password){
		return dao.login(email, password);
	}

}
