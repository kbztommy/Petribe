package za101g2.journalBoard.model;

import java.sql.Date;
import java.util.List;

import za101g2.journalLinkedPet.model.JournalLinkedPetVO;

public class JournalBoardService {

	private JournalBoardDAO_interface dao;

	public JournalBoardService() {
		dao = new JournalBoardDAO();
	}

	public JournalBoardVO addJournalBoard(Integer journalId, Integer memId, String boardMsg, String isDelete) {

		JournalBoardVO journalBoardVO = new JournalBoardVO();

		journalBoardVO.setJournalId(journalId);
		journalBoardVO.setMemId(memId);
		journalBoardVO.setBoardMsg(boardMsg);
		journalBoardVO.setIsDelete(isDelete);
		dao.insert(journalBoardVO);

		return journalBoardVO;
	}

	public JournalBoardVO updateJournalBoard(Integer id, Integer journalId, Integer memId, String boardMsg, Date boardMsgDate, String isDelete) {

		JournalBoardVO journalBoardVO = new JournalBoardVO();

		journalBoardVO.setId(id);
		journalBoardVO.setJournalId(journalId);
		journalBoardVO.setMemId(memId);
		journalBoardVO.setBoardMsg(boardMsg);
		journalBoardVO.setBoardMsgDate(boardMsgDate);
		journalBoardVO.setIsDelete(isDelete);
		dao.update(journalBoardVO);

		return journalBoardVO;
	}
	
	public JournalBoardVO updateIsDelete(Integer id, String isDelete) {

		JournalBoardVO journalBoardVO = new JournalBoardVO();

		journalBoardVO.setId(id);
		journalBoardVO.setIsDelete(isDelete);
		dao.updateIsDelete(journalBoardVO);

		return journalBoardVO;
	}

	public void deleteJournalBoard(Integer id) {
		dao.delete(id);
	}

	public JournalBoardVO getOneJournalBoard(Integer id) {
		return dao.findByPrimaryKey(id);
	}

	public List<JournalBoardVO> getAll() {
		return dao.getAll();
	}
	
	public List<JournalBoardVO> findByJournalId(Integer journalId) {
		return dao.findByJournalId(journalId);
	}
	
	public void deleteByJournalId(Integer journalId) {
		List<JournalBoardVO> JournalBoardList = dao.findByJournalId(journalId);
		for(JournalBoardVO oneJournalBoardVO : JournalBoardList){
			dao.delete(oneJournalBoardVO.getId());
		}
	}
	
}
