package za101g2.missingreport.model;

import java.util.*;

public interface MissingReportDAO_interface {
	public void insert(MissingReportVO missingReportVO);
	public void update(MissingReportVO missingReportVO);
	public void delete(Integer memId, Integer replyId);
	public MissingReportVO findByPrimaryKey(Integer memId, Integer replyId);
	public MissingReportVO findByReplyId(Integer replyId);
	public Set<MissingReportVO> findByMemId(Integer memId);
    public List<MissingReportVO> getAll();
}
