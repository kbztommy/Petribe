package za101g2.journal.model;
import java.util.*;

import za101g2.photo.model.PhotoVO;

public interface JournalDAO_interface {
          public int insert(JournalVO VO);
          public int update(JournalVO VO);
          public int delete(Integer id);
          public JournalVO findByPrimaryKey(Integer id);
          public List<JournalVO> getAll();
      	  public List<JournalVO> findByForeginKey(Integer memId);
      	  public String insertReKey(JournalVO VO);
      	  public int updateStatus(JournalVO VO);
      	  public List<JournalVO> getMore(Integer id);
      	  public List<JournalVO> getNewest();
}
