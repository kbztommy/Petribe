package za101g2.journalReport.model;

import java.util.List;

public class JournalReportService {

	private JournalReportDAO_interface dao;

	public JournalReportService() {
		dao = new JournalReportDAO();
	}

	public JournalReportVO addJournalReport(Integer memId, Integer journalId, String comments) {

		JournalReportVO journalReportVO = new JournalReportVO();

		journalReportVO.setMemId(memId);
		journalReportVO.setJournalId(journalId);
		journalReportVO.setComments(comments);
		dao.insert(journalReportVO);

		return journalReportVO;
	}

	public void deleteJournalReport(Integer memId, Integer journalId) {
		dao.delete(memId, journalId);
	}
	
	public void deleteByJournal(Integer journalId) {
		dao.deleteByJournal(journalId);
	}

	public JournalReportVO getOneJournalReport(Integer memId, Integer journalId) {
		return dao.findByPrimaryKey(memId, journalId);
	}

	public List<JournalReportVO> getAll() {
		return dao.getAll();
	}
	
	public List<JournalReportVO> getByJournalId(Integer journalId) {
		return dao.getByJournalId(journalId);
	}
}
