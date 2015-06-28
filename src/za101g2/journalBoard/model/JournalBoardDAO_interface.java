package za101g2.journalBoard.model;
import java.util.*;

public interface JournalBoardDAO_interface {
          public int insert(JournalBoardVO VO);
          public int update(JournalBoardVO VO);
          public int delete(Integer id);
          public JournalBoardVO findByPrimaryKey(Integer id);
          public List<JournalBoardVO> getAll();
          public List<JournalBoardVO> findByJournalId(Integer journalId);
          public int updateIsDelete(JournalBoardVO VO);
}
