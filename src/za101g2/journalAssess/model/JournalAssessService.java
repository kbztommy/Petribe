package za101g2.journalAssess.model;

import java.util.List;

public class JournalAssessService {

	private JournalAssessDAO_interface dao;

	public JournalAssessService() {
		dao = new JournalAssessDAO();
	}

	public JournalAssessVO addJournalAssess(Integer journalId, Integer memId) {

		JournalAssessVO journalAssessVO = new JournalAssessVO();

		journalAssessVO.setJournalId(journalId);
		journalAssessVO.setMemId(memId);
		dao.insert(journalAssessVO);

		return journalAssessVO;
	}

	public void deleteJournalAssess(Integer journalId, Integer memId) {
		dao.delete(journalId, memId);
	}
	
	public void deleteByJournalId(Integer journalId) {
		dao.deleteByJournalId(journalId);
	}

	public List<JournalAssessVO> getAssessByAJournal(Integer journalId) {
		return dao.findByPrimaryKey(journalId);
	}

	public List<JournalAssessVO> getAll() {
		return dao.getAll();
	}
	
	public boolean judgeAssess(Integer journalId, Integer memId) {
		return dao.judgeAssess(journalId, memId);
	}
}
