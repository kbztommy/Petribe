package za101g2.onService.model;

import java.sql.Timestamp;
import java.util.*;

import za101g2.orderBoard.model.OrderBoardVO;
import za101g2.store.model.StoreVO;
import za101g2.storePhoto.model.StorePhotoVO;

public class OnServiceService {
	OnServiceDAO_interface dao = null;
	public OnServiceService(){
		dao = new OnServiceDAO();
	}
	
	public void insert(OnServiceVO onServiceVO){
		dao.insert(onServiceVO);
	}

	public void update(OnServiceVO onServiceVO){
		dao.update(onServiceVO);
	}

	public void delete(Integer id){
		dao.delete(id);
	}

	public OnServiceVO findByPrimaryKey(Integer id){
		return dao.findByPrimaryKey(id);
	}

	public List<OnServiceVO> getAll(){
		return dao.getAll();
	}
	
	public void insertWithPhoto(List<byte[]> picList,OrderBoardVO orderBoardVO,String comments){		
		
		OnServiceVO onServiceVO = new OnServiceVO();
		StoreVO storeVO = orderBoardVO.getStoreVO();
		Timestamp updateDate = new java.sql.Timestamp(new Date().getTime());
		Set<StorePhotoVO> storePhotos = new HashSet<StorePhotoVO>();		
		for(byte[] pic : picList){
			StorePhotoVO storePhotoVO = new StorePhotoVO();
			storePhotoVO.setFormat("jpg");
			storePhotoVO.setOnServiceVO(onServiceVO);
			storePhotoVO.setPhotoFile(pic);
			storePhotoVO.setStoreVO(storeVO);
			storePhotoVO.setUpdateDate(updateDate);
			storePhotos.add(storePhotoVO);
		}		
		
		onServiceVO.setComments(comments);
		onServiceVO.setOrderBoardVO(orderBoardVO);
		onServiceVO.setReleaseDate(updateDate);
		onServiceVO.setStorePhotos(storePhotos);
		
		dao.insert(onServiceVO);
	}
}
