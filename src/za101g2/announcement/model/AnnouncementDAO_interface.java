package za101g2.announcement.model;
import java.util.*;

import za101g2.photo.model.PhotoVO;

public interface AnnouncementDAO_interface {
          public int insert(AnnouncementVO VO);
          public int update(AnnouncementVO VO);
          public int delete(Integer id);
          public AnnouncementVO findByPrimaryKey(Integer id);
          public List<AnnouncementVO> getAll();
          public List<AnnouncementVO> findByForeginKey(Integer staffId);
}
