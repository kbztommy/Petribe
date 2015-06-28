package za101g2.journalReport.model;
import java.util.*;

public interface JournalReportDAO_interface {
          public int insert(JournalReportVO VO);
          public int update(JournalReportVO VO);
          public int delete(Integer memId,Integer journalId);
          public int deleteByJournal(Integer journalId);
          public JournalReportVO findByPrimaryKey(Integer memId,Integer journalId);
          public List<JournalReportVO> getAll();
          public List<JournalReportVO> getByJournalId(Integer journalId);
}
