package za101g2.staffAccesses.model;

import java.util.*;

public interface StaffAccessesDAO_interface {
	public void changeAccesses(Integer id,List<Integer> accessesList);
	public List<Integer> findAccessesById(Integer id);
}
