package za101g2.storePhoto.model;
import java.util.*;

public interface StorePhotoDAO_interface {
	StorePhotoVO insert(StorePhotoVO storePhotoVO);
	void update(StorePhotoVO storePhotoVO);
	void delete(Integer id);
	StorePhotoVO findByPrimaryKey(Integer id);
	List<StorePhotoVO> getAll();
	List<StorePhotoVO> getStorePic(Integer storeId);
}
