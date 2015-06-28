package za101g2.staffAccesses.model;

import java.util.List;

public class StaffAccessesService {
	private StaffAccessesDAO_interface dao;
	
	public StaffAccessesService(){
		dao = new StaffAccessesDAO();
	}
	
	public void changeStaffAccesses(Integer id,List<Integer> accessesList){		
		 dao.changeAccesses(id, accessesList);
	}
	
	public List<Integer> findAccessesById(Integer id){		
		return dao.findAccessesById(id);
	}
}
