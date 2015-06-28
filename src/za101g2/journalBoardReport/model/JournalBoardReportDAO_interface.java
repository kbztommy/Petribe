package za101g2.journalBoardReport.model;
import java.util.*;

public interface JournalBoardReportDAO_interface {
          public int insert(JournalBoardReportVO VO);
          public int update(JournalBoardReportVO VO);
          public int delete(Integer journalMsgId,Integer memId);
          public JournalBoardReportVO findByPrimaryKey(Integer journalMsgId,Integer memId);
          public List<JournalBoardReportVO> getAll();
}
