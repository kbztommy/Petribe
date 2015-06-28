package za101g2.announcement.model;

import java.util.List;

public class AnnouncementService {

	private AnnouncementDAO_interface dao;

	public AnnouncementService() {
		dao = new AnnouncementDAO();
	}

	public AnnouncementVO addAnnouncement(String title, String comments, Integer staffId) {

		AnnouncementVO announcementVO = new AnnouncementVO();

		announcementVO.setTitle(title);
		announcementVO.setComments(comments);
		announcementVO.setStaffId(staffId);
		dao.insert(announcementVO);

		return announcementVO;
	}

	public void deleteAnnouncement(Integer id) {
		dao.delete(id);
	}

	public AnnouncementVO getOneAnnouncement(Integer id) {
		return dao.findByPrimaryKey(id);
	}

	public List<AnnouncementVO> getAll() {
		return dao.getAll();
	}
	
	public List<AnnouncementVO> getByStaffId(Integer staffId) {
		return dao.findByForeginKey(staffId);
	}
}
