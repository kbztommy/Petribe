package za101g2.journal.model;


import java.util.List;


public class JournalService{

	private JournalDAO_interface dao;

	public JournalService() {
		dao = new JournalDAO();
	}

	public JournalVO addJournal(String title, String article, Integer memId, String status, String isPublic) {

		JournalVO journalVO = new JournalVO();

		journalVO.setTitle(title);
		journalVO.setArticle(article);
		journalVO.setMemId(memId);
		journalVO.setStatus(status);
		journalVO.setIsPublic(isPublic);
		dao.insert(journalVO);

		return journalVO;
	}

	public int addJournalReId(String title, String article, Integer memId, String status, String isPublic) {

		JournalVO journalVO = new JournalVO();

		journalVO.setTitle(title);
		journalVO.setArticle(article);
		journalVO.setMemId(memId);
		journalVO.setStatus(status);
		journalVO.setIsPublic(isPublic);
		String key = dao.insertReKey(journalVO);
		Integer id = new Integer(key);
		return id;
	}
	
	public JournalVO updateJournal(Integer id, String title, String article, Integer memId, String status, String isPublic) {

		JournalVO journalVO = new JournalVO();

		journalVO.setId(id);
		journalVO.setTitle(title);
		journalVO.setArticle(article);
		journalVO.setMemId(memId);
		journalVO.setStatus(status);
		journalVO.setIsPublic(isPublic);
		dao.update(journalVO);

		return journalVO;
	}

	public JournalVO updateJournalStatus(Integer id, String status) {

		JournalVO journalVO = new JournalVO();

		journalVO.setId(id);
		journalVO.setStatus(status);
		dao.updateStatus(journalVO);

		return journalVO;
	}
	
	public void deleteJournal(Integer id) {
		dao.delete(id);
	}

	public JournalVO getOneJournal(Integer id) {
		return dao.findByPrimaryKey(id);
	}

	public List<JournalVO> getAll() {
		return dao.getAll();
	}
	
	public List<JournalVO> getJournalsByMemId(Integer memId) {
		return dao.findByForeginKey(memId);
	}
	
	public List<JournalVO> getMore(Integer id){
		return dao.getMore(id);
	}
	
	public List<JournalVO> getNewest(){
		return dao.getNewest();
	}
}
