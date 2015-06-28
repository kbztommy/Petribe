package za101g2.photo.model;

import java.util.List;
import java.util.Map;

public class PhotoService {

	private PhotoDAO_interface dao;

	public PhotoService() {
		dao = new PhotoDAO();
	}

	public PhotoVO addPhoto(String name, String format, byte[] photoFile, Integer memId) {

		PhotoVO photoVO = new PhotoVO();

		photoVO.setName(name);
		photoVO.setFormat(format);
		photoVO.setPhotoFile(photoFile);
		photoVO.setMemId(memId);
		dao.insert(photoVO);

		return photoVO;
	}

	public int addPhotoReId(String name, String format, byte[] photoFile, Integer memId) {

		PhotoVO photoVO = new PhotoVO();

		photoVO.setName(name);
		photoVO.setFormat(format);
		photoVO.setPhotoFile(photoFile);
		photoVO.setMemId(memId);
		String key = dao.insertReKey(photoVO);
		Integer id = new Integer(key);
		return id;
	}
	
	public PhotoVO updatePhotoName(Integer id, String name) {

		PhotoVO photoVO = new PhotoVO();

		photoVO.setId(id);
		photoVO.setName(name);
		dao.update_name(photoVO);

		return photoVO;
	}

	public void deletePhoto(Integer id) {
		dao.delete(id);
	}

	public PhotoVO getOnePhoto(Integer id) {
		return dao.findByPrimaryKey(id);
	}

	public List<PhotoVO> getAll() {
		return dao.getAll();
	}
	
	public List<PhotoVO> getAll(Map<String, String[]> map) {
		return dao.getAll(map);
	}
	
	public List<PhotoVO> getPhotosByMemId(Integer memId) {
		return dao.findByForeginKey(memId);
	}
}
