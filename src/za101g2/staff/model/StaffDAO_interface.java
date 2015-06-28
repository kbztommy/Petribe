package za101g2.staff.model;

import java.util.*;

public interface StaffDAO_interface {
	public String insertStaff(StaffVO staffVO);
	public void updateStaffName(Integer id, String name);
	public void updateStaffStatus(Integer id, String status);
	public void updateStaffPassword(Integer id, String password);
    public StaffVO findByPrimaryKey(Integer id);
    public Long findByEmail(String email);
    public List<StaffVO> getAll();
    public List<StaffVO> getSome(String keyword);
	public StaffVO login(String email, String password);
}
