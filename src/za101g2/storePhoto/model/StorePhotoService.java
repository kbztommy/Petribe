package za101g2.storePhoto.model;

import java.util.Date;
import java.util.List;

import za101g2.store.model.*;
import za101g2.storePhoto.model.StorePhotoDAO_interface;

public class StorePhotoService {
	StorePhotoDAO_interface dao = null;
	
	public StorePhotoService(){
		dao= new StorePhotoDAO();
	}
	
	public void insert(StorePhotoVO storePhotoVO){
		dao.insert(storePhotoVO);
	}
	public void update(StorePhotoVO storePhotoVO){
		dao.update(storePhotoVO);
	}
	public void delete(Integer id){
		dao.delete(id);
	}
	public StorePhotoVO findByPrimaryKey(Integer id){
		return dao.findByPrimaryKey(id);
	}
	public List<StorePhotoVO> getAll(){
		return dao.getAll();
	}
	
	public StorePhotoVO insertForStore(Integer storeId,byte[] pic){
		StoreService storeSrv = new StoreService();
		StoreVO storeVO = storeSrv.getOneStore(storeId);
		StorePhotoVO storePhotoVO = new StorePhotoVO();
		storePhotoVO.setPhotoFile(pic);
		storePhotoVO.setStoreVO(storeVO);
		storePhotoVO.setFormat("jpg");
		storePhotoVO.setUpdateDate(new java.sql.Timestamp(new Date().getTime()));
		return dao.insert(storePhotoVO);		
	}
	
	public List<StorePhotoVO> getStorePic(Integer storeId){
		return dao.getStorePic(storeId);
	}
}
