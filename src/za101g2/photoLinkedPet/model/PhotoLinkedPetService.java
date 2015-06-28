package za101g2.photoLinkedPet.model;

import java.util.ArrayList;
import java.util.List;

import za101g2.journal.model.JournalVO;
import za101g2.photo.model.PhotoVO;

public class PhotoLinkedPetService {

	private PhotoLinkedPetDAO_interface dao;

	public PhotoLinkedPetService() {
		dao = new PhotoLinkedPetDAO();
	}

	public PhotoLinkedPetVO addPhotoLinkedPet(Integer photoId, Integer petId) {

		PhotoLinkedPetVO photoLinkedPetVO = new PhotoLinkedPetVO();

		photoLinkedPetVO.setPhotoId(photoId);
		photoLinkedPetVO.setPetId(petId);
		dao.insert(photoLinkedPetVO);

		return photoLinkedPetVO;
	}

	public void deletePhotoLinkedPet(Integer photoId, Integer petId) {
		dao.delete(photoId, petId);
	}
	
	public void deleteByPhoto(Integer photoId) {
		dao.deleteByPhoto(photoId);
	}

	public List<PhotoLinkedPetVO> listByPhotoId(Integer photoId) {
		return dao.listByPhotoId(photoId);
	}

	public List<Integer> listByPetId(Integer petId) {
		List<Integer> photoList = new ArrayList<Integer>();
		List<PhotoLinkedPetVO> voList = dao.listByPetId(petId);
		for(PhotoLinkedPetVO onePhotoLinkedPetVO : voList){
			photoList.add(onePhotoLinkedPetVO.getPhotoId());
		}
		return photoList;
	}
	
	public List<PhotoLinkedPetVO> getAll() {
		return dao.getAll();
	}
}
