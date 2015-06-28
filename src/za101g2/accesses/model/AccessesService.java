package za101g2.accesses.model;

import java.util.List;

public class AccessesService {
	
	private AccessesDAO_interface dao;

	public AccessesService() {
		dao = new AccessesDAO();
	}
	
	public List<AccessesVO> getAll() {
		return dao.getAll();
	}
}
