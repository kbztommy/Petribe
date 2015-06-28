package za101g2.journalAssess.model;
import java.util.*;

public interface JournalAssessDAO_interface {
          public int insert(JournalAssessVO VO);
          public int delete(Integer journalId,Integer memId);
          public int deleteByJournalId(Integer journalId);
          public List<JournalAssessVO> findByPrimaryKey(Integer journalId);
          public List<JournalAssessVO> getAll();
          public boolean judgeAssess(Integer journalId,Integer memId);
}
