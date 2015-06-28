package za101g2.serviceList.model;

import java.util.*;

public interface ServiceListDAO_interface {
	void insert(ServiceListVO serviceListVO);
	void update(ServiceListVO serviceListVO);
	void delete(Integer id);
	ServiceListVO findByPrimaryKey(Integer id);
	List<ServiceListVO> getAll();
	ServiceListVO getService(Integer storeId, String petType,String chargeType);
	List<ServiceListVO> getCustomSrv(Integer storeId,String petType);
	List<Integer> storeIdRequired(String petType);
	List<Integer> getAllStoreId();
	Integer getMinPrice(Integer storeId);
	List<ServiceListVO> getAllOnsaleCust(Integer storeId);
	ServiceListVO getOnsaleService(Integer storeId,String petType,String chargeType);	
}
