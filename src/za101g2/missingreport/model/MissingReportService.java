package za101g2.missingreport.model;

import java.util.*;

import za101g2.member.model.*;
import za101g2.missingreply.model.*;

public class MissingReportService {
	
	private MissingReportDAO_interface dao;
	
	public MissingReportService() {
		dao = new MissingReportDAO();
	}
	
	public MissingReportVO addMissingReport(Integer memId, Integer replyId,  
			String comments, java.sql.Date reportDate) {
		
		MissingReportVO missingReportVO = new MissingReportVO();
		
		MemberService memberSvc = new MemberService();
		MemberVO memberVO = memberSvc.getOneMember(memId);
		
		MissingReplyService missingReplySvc = new MissingReplyService();
		MissingReplyVO missingReplyVO = missingReplySvc.getOneMissingReply(replyId);
		
		missingReportVO.setMemberVO(memberVO);
		missingReportVO.setMissingReplyVO(missingReplyVO);
		missingReportVO.setComments(comments);
		missingReportVO.setReportDate(reportDate);
		dao.insert(missingReportVO);
		
		return missingReportVO;
	}
	
	// 預留給 Struts 2 用的
	public void addMissingReport(MissingReportVO missingReportVO) {
		dao.insert(missingReportVO);
	}
	
	public MissingReportVO updateMissingReport(String comments, java.sql.Date reportDate,  
			Integer memId, Integer replyId) {
		
		MissingReportVO missingReportVO = new MissingReportVO();
		
		MemberService memberSvc = new MemberService();
		MemberVO memberVO = memberSvc.getOneMember(memId);
		
		MissingReplyService missingReplySvc = new MissingReplyService();
		MissingReplyVO missingReplyVO = missingReplySvc.getOneMissingReply(replyId);
		
		missingReportVO.setComments(comments);
		missingReportVO.setReportDate(reportDate);
		missingReportVO.setMemberVO(memberVO);
		missingReportVO.setMissingReplyVO(missingReplyVO);		
		dao.update(missingReportVO);
		
		return dao.findByPrimaryKey(memId, replyId);
	}
	
	//預留給 Struts 2 用的
	public void updateMissingReport(MissingReportVO missingReportVO) {
		dao.update(missingReportVO);
	}

	public void deleteMissingReport(Integer memId, Integer replyId) {
		dao.delete(memId, replyId);
	}

	public MissingReportVO getOneMissingReport(Integer memId, Integer replyId) {
		return dao.findByPrimaryKey(memId, replyId);
	}
	
	public MissingReportVO findByReplyId(Integer replyId) {
		return dao.findByReplyId(replyId);
	}
	
	public Set<MissingReportVO> findByMemId(Integer memId) {
		return dao.findByMemId(memId);
	}

	public List<MissingReportVO> getAll() {
		return dao.getAll();
	}
}
