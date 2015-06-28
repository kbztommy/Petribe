package za101g2.serviceList.model;

import java.util.List;

import za101g2.store.model.*;

public class ServiceListService {
	private ServiceListDAO_interface dao = null;
	
	public ServiceListService(){
		dao = new ServiceListDAO();
	}
	
	public ServiceListVO getOneService(Integer id){
		return dao.findByPrimaryKey(id);
	}
	
	public ServiceListVO getService(Integer storeId,String petType,String chargeType){
		return dao.getService(storeId,petType,chargeType);
	}
	
	public ServiceListVO getOnsaleService(Integer storeId,String petType,String chargeType){
		return dao.getOnsaleService(storeId,petType,chargeType);
	}
	
	public void ServiceListUpdate(Integer storeId,String petType,String chargeType,Integer price,String isOnsale){
		 ServiceListVO serviceListVO= getService(storeId,petType,chargeType);
		 serviceListVO.setPrice(price);
		 serviceListVO.setIsOnsale(isOnsale);
		 dao.update(serviceListVO);
	}
	
	//住宿與接送服務的新增
	public void ServiceListInsert(String name,String info,Integer price,Integer storeId,String isOnsale,String petType,String chargeType){
		ServiceListVO serviceListVO = new ServiceListVO();
		StoreService storeSrv = new StoreService();		
		serviceListVO.setName(name);
		serviceListVO.setInfo(info);
		serviceListVO.setPrice(price);
		serviceListVO.setStoreVO(storeSrv.getOneStore(storeId));
		serviceListVO.setIsOnsale(isOnsale);
		serviceListVO.setChargeType(chargeType);
		serviceListVO.setPetType(petType);
		dao.insert(serviceListVO);
	}
	
	public List<ServiceListVO> getCustService(Integer storeId,String petType){
		return dao.getCustomSrv(storeId, petType);
	}
	
	public void delete(Integer id){
		dao.delete(id);
	}
	
	public void insert(String name,String info,Integer price,Integer storeId,String isOnsale,String petType,String chargeType){
		ServiceListVO serviceListVO = new ServiceListVO();
		StoreService storeSrv = new StoreService();		
		serviceListVO.setStoreVO(storeSrv.getOneStore(storeId));
		serviceListVO.setName(name);
		serviceListVO.setPrice(price);
		serviceListVO.setInfo(info);
		serviceListVO.setIsOnsale(isOnsale);
		serviceListVO.setPetType(petType);
		serviceListVO.setChargeType(chargeType);
		dao.insert(serviceListVO);
	}

	public  List<Integer> storeIdRequired(String petType) {
		return dao.storeIdRequired(petType);	
	}
	
	public List<Integer> getAllStoreId(){
		return dao.getAllStoreId();
	}
	
	public Integer getMinPrice(Integer storeId){
		return dao.getMinPrice(storeId);
	}
	
	public List<ServiceListVO> getAllOnsaleCust(Integer storeId){
		return dao.getAllOnsaleCust(storeId);
	}
	
}
