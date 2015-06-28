package za101g2.store.model;

import java.util.List;

public interface StoreDAO_interface {
	
	
	public void insert (StoreVO storeVO);
	public void update (StoreVO storeVO);
	public void rankUp (StoreVO storeVO);
	public void delete (Integer id);
	public StoreVO findByPrimaryKey(Integer id);
	public List<StoreVO> getAll();
	public List<StoreVO> getApplyAll();
	public  StoreVO findByForeginKey(Integer id);
	//public List<StoreVO> findByPrimaryKeyList(List<Integer> StoreIdList);
	public List<Integer> getAllIdbyCity(String city);
}
