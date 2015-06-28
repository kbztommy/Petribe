package za101g2.pet.model;

import java.util.List;

import za101g2.member.model.MemberService;
import za101g2.member.model.MemberVO;
import za101g2.missingrecord.model.MissingRecordVO;

public class PetService {

	private PetDAO_interface dao;

	public PetService() {
		dao = new PetDAO();
	}

	public PetVO addPet(byte[] icon, String name, String species,
			byte[] qrCode, Integer memId) {

		PetVO petVO = new PetVO();
		
		MemberService memberSvc = new MemberService();
		MemberVO memberVO = memberSvc.getOneMember(memId);

		petVO.setIcon(icon);
		petVO.setName(name);
		petVO.setSpecies(species);
		petVO.setQrCode(qrCode);
		petVO.setMemberVO(memberVO);
		dao.insert(petVO);

		return petVO;
	}

	//預留給 Struts 2 用的
	public void addPet(PetVO petVO) {
		dao.insert(petVO);
	}
	
	public PetVO updatePet(byte[] icon, String name, String species,
			byte[] qrCode, String status, Integer memId, Integer id) {

		PetVO petVO = new PetVO();
		
		MemberService memberSvc = new MemberService();
		MemberVO memberVO = memberSvc.getOneMember(memId);

		petVO.setIcon(icon);
		petVO.setName(name);
		petVO.setSpecies(species);
		petVO.setQrCode(qrCode);
		petVO.setStatus(status);
		petVO.setMemberVO(memberVO);
		petVO.setId(id);
		dao.update(petVO);

		return dao.findByPrimaryKey(id);
	}
	
	//預留給 Struts 2 用的
	public void updatePet(PetVO petVO) {
		dao.update(petVO);
	}
	
	public PetVO changeStatus(Integer id, String status) {
		
		PetVO petVO = dao.findByPrimaryKey(id);
		petVO.setId(id);
		petVO.setStatus(status);
		dao.update(petVO);
		
		return petVO;
	}

	public void deletePet(Integer id) {
		dao.delete(id);
	}

	public PetVO getOnePet(Integer id) {
		return dao.findByPrimaryKey(id);
	}

	public List<PetVO> findIdByMemId(Integer memId) {
		return dao.findIdByMemId(memId);
	}
	
	public List<PetVO> getAll() {
		return dao.getAll();
	}
	
	public void updateIcon(byte[] icon, Integer id) {
		PetVO petVO = new PetVO();
		petVO = dao.findByPrimaryKey(id);
		petVO.setIcon(icon);
		dao.update(petVO);
	}
}
