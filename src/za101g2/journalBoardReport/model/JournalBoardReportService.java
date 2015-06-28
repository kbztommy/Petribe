package za101g2.journalBoardReport.model;

import java.util.List;

public class JournalBoardReportService {

	private JournalBoardReportDAO_interface dao;

	public JournalBoardReportService() {
		dao = new JournalBoardReportDAO();
	}

	public JournalBoardReportVO addJournalBoardReport(Integer journalMsgId, Integer memId, String comments) {

		JournalBoardReportVO journalBoardReportVO = new JournalBoardReportVO();

		journalBoardReportVO.setJournalMsgId(journalMsgId);
		journalBoardReportVO.setMemId(memId);
		journalBoardReportVO.setComments(comments);
		dao.insert(journalBoardReportVO);

		return journalBoardReportVO;
	}

	public void deleteJournalBoardReport(Integer journalMsgId, Integer memId) {
		dao.delete(journalMsgId, memId);
	}

	public JournalBoardReportVO getOneJournalBoardReport(Integer journalMsgId, Integer memId) {
		return dao.findByPrimaryKey(journalMsgId, memId);
	}

	public List<JournalBoardReportVO> getAll() {
		return dao.getAll();
	}
}
