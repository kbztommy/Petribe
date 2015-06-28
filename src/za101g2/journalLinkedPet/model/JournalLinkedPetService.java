package za101g2.journalLinkedPet.model;

import java.util.ArrayList;
import java.util.List;

import za101g2.photoLinkedPet.model.PhotoLinkedPetVO;

public class JournalLinkedPetService {

	private JournalLinkedPetDAO_interface dao;

	public JournalLinkedPetService() {
		dao = new JournalLinkedPetDAO();
	}

	public JournalLinkedPetVO addJournalLinkedPet(Integer journalId, Integer petId) {

		JournalLinkedPetVO journalLinkedPetVO = new JournalLinkedPetVO();

		journalLinkedPetVO.setJournalId(journalId);
		journalLinkedPetVO.setPetId(petId);
		dao.insert(journalLinkedPetVO);
		
		return journalLinkedPetVO;
	}

	public void deleteJournalLinkedPet(Integer journalId, Integer petId) {
		dao.delete(journalId, petId);
	}

	public List<JournalLinkedPetVO> getJournalsByPetId(Integer petId) {
		return dao.findByPrimaryKey(petId);
	}

	public List<Integer> listByPetId(Integer petId) {
		List<Integer> journalList = new ArrayList<Integer>();
		List<JournalLinkedPetVO> voList = dao.findByPrimaryKey(petId);
		for(JournalLinkedPetVO oneJournalLinkedPetVO : voList){
			journalList.add(oneJournalLinkedPetVO.getJournalId());
		}
		return journalList;
	}
	
	public List<JournalLinkedPetVO> findByJournalId(Integer journalId) {
		return dao.findByJournalId(journalId);
	}
	
	public List<JournalLinkedPetVO> getAll() {
		return dao.getAll();
	}
	
	public void deleteByJournal(Integer journalId) {
		dao.deleteByJournal(journalId);
	}
}
