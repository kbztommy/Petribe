package za101g2.photo.model;

import java.util.List;
import java.util.Map;

public interface PhotoDAO_interface {
	
	
	public void insert (PhotoVO photoVO);
	public void update_name (PhotoVO photoVO);
	public void delete (Integer id);
	public PhotoVO findByPrimaryKey(Integer id);
	public List<PhotoVO> getAll();
	public List<PhotoVO> getAll(Map<String, String[]> map);
	public List<PhotoVO> findByForeginKey(Integer id);
	public String insertReKey (PhotoVO photoVO);
}
