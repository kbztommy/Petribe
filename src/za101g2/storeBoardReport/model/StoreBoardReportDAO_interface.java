package za101g2.storeBoardReport.model;
import java.util.*;

public interface StoreBoardReportDAO_interface {
	void insert(StoreBoardReportVO storeBoardReportVO);	
	void delete(Integer storeMsgId,Integer memId);
	StoreBoardReportVO findByPrimaryKey(Integer storeMsgId,Integer memId);
	List<StoreBoardReportVO> getAll();
}
