package za101g2.missingrecord.model;

import java.util.List;

import za101g2.pet.model.*;

public class MissingRecordService {
	
	private MissingRecordDAO_interface dao;
	
	public MissingRecordService() {
		dao = new MissingRecordDAO();
	}
	
	public MissingRecordVO addMissingRecord(String location, java.sql.Date missingDate,  
			Integer bounty, String comments, Integer petId, String status, Integer bountyFor, byte[] missingPhoto, String latlng) {
		
		MissingRecordVO missingRecordVO = new MissingRecordVO();
		
		PetService petSvc = new PetService();
		PetVO petVO = petSvc.getOnePet(petId);
		
		missingRecordVO.setLocation(location);
		missingRecordVO.setMissingDate(missingDate);
		missingRecordVO.setBounty(bounty);
		missingRecordVO.setComments(comments);
		missingRecordVO.setPetVO(petVO);
		missingRecordVO.setStatus(status);
		missingRecordVO.setBountyFor(bountyFor);
		missingRecordVO.setMissingPhoto(missingPhoto);
		missingRecordVO.setLatlng(latlng);
		dao.insert(missingRecordVO);
		
		return missingRecordVO;
	}
	
	// 預留給 Struts 2 用的
	public void addMissingRecord(MissingRecordVO missingRecordVO) {
		dao.insert(missingRecordVO);
	}
	
	public MissingRecordVO updateMissingRecord(Integer id, String location, java.sql.Date missingDate,  
			Integer bounty, String comments, Integer petId, String status, Integer bountyFor, byte[] missingPhoto, String latlng) {
		
		MissingRecordVO missingRecordVO = new MissingRecordVO();
		
		PetService petSvc = new PetService();
		PetVO petVO = petSvc.getOnePet(petId);

		missingRecordVO.setId(id);
		missingRecordVO.setLocation(location);
		missingRecordVO.setMissingDate(missingDate);
		missingRecordVO.setBounty(bounty);
		missingRecordVO.setComments(comments);
		missingRecordVO.setPetVO(petVO);
		missingRecordVO.setStatus(status);
		missingRecordVO.setBountyFor(bountyFor);
		missingRecordVO.setMissingPhoto(missingPhoto);
		missingRecordVO.setLatlng(latlng);
		dao.update(missingRecordVO);
		
		return missingRecordVO;
	}
	
	//預留給 Struts 2 用的
	public void updateMissingRecord(MissingRecordVO missingRecordVO) {
		dao.update(missingRecordVO);
	}
	
	public MissingRecordVO changeStatus(Integer id, String status) {
		
		MissingRecordVO missingRecordVO = dao.findByPrimaryKey(id);
		missingRecordVO.setId(id);
		missingRecordVO.setStatus(status);
		dao.update(missingRecordVO);
		
		return missingRecordVO;
	}
	
	public MissingRecordVO changeBountyFor(Integer id, Integer bountyFor) {
		
		MissingRecordVO missingRecordVO = dao.findByPrimaryKey(id);
		missingRecordVO.setId(id);
		missingRecordVO.setBountyFor(bountyFor);
		dao.update(missingRecordVO);
		
		return missingRecordVO;
	}

	public void deleteMissingRecord(Integer id) {
		dao.delete(id);
	}

	public MissingRecordVO getOneMissingRecord(Integer id) {
		return dao.findByPrimaryKey(id);
	}

	public MissingRecordVO findByPetId(Integer petId) {
		return dao.findByPetId(petId);
	}
	
	public List<MissingRecordVO> findByCity(String city) {
		return dao.findByCity(city);
	}
	
	public List<MissingRecordVO> findByCityBounty(String city) {
		return dao.findByCityBounty(city);
	}
	
	public List<MissingRecordVO> findByCityNoBounty(String city) {
		return dao.findByCityNoBounty(city);
	}
	
	public List<MissingRecordVO> getAllBounty() {
		return dao.getAllBounty();
	}
	
	public List<MissingRecordVO> getAllNoBounty() {
		return dao.getAllNoBounty();
	}
	
	public List<MissingRecordVO> getAll() {
		return dao.getAll();
	}
}

