package za101g2.missingreply.model;

import java.util.*;

import za101g2.missingrecord.model.*;
import za101g2.member.model.*;

public class MissingReplyService {
	
	private MissingReplyDAO_interface dao;
	
	public MissingReplyService() {
		dao = new MissingReplyDAO();
	}
	
	public MissingReplyVO addMissingReply(String comments, Integer recordId,  
			Integer memId, String isRead, java.sql.Date reportDate) {
		
		MissingReplyVO missingReplyVO = new MissingReplyVO();
		
		MissingRecordService missingRecordSvc = new MissingRecordService();
		MissingRecordVO missingRecordVO = missingRecordSvc.getOneMissingRecord(recordId);
		
		MemberService memberSvc = new MemberService();
		MemberVO memberVO = memberSvc.getOneMember(memId);
		
		missingReplyVO.setComments(comments);
		missingReplyVO.setMissingRecordVO(missingRecordVO);
		missingReplyVO.setMemberVO(memberVO);
		missingReplyVO.setIsRead(isRead);
		missingReplyVO.setReportDate(reportDate);
		dao.insert(missingReplyVO);
		
		return missingReplyVO;
	}
	
	// 預留給 Struts 2 用的
	public void addMissingReply(MissingReplyVO missingReplyVO) {
		dao.insert(missingReplyVO);
	}
	
	public MissingReplyVO updateMissingReply(String comments, Integer recordId,  
			Integer memId, String isRead, java.sql.Date reportDate, Integer id) {
		
		MissingReplyVO missingReplyVO = new MissingReplyVO();
		
		MissingRecordService missingRecordSvc = new MissingRecordService();
		MissingRecordVO missingRecordVO = missingRecordSvc.getOneMissingRecord(recordId);
		
		MemberService memberSvc = new MemberService();
		MemberVO memberVO = memberSvc.getOneMember(memId);
		
		missingReplyVO.setComments(comments);
		missingReplyVO.setMissingRecordVO(missingRecordVO);
		missingReplyVO.setMemberVO(memberVO);
		missingReplyVO.setIsRead(isRead);
		missingReplyVO.setReportDate(reportDate);
		missingReplyVO.setId(id);
		dao.update(missingReplyVO);
		
		return missingReplyVO;
	}
	
	//預留給 Struts 2 用的
	public void updateMissingReply(MissingReplyVO missingReplyVO) {
		dao.update(missingReplyVO);
	}
	
	public MissingReplyVO changeIsread(Integer id, String isRead) {
		
		MissingReplyVO missingReplyVO = dao.findByPrimaryKey(id);
		missingReplyVO.setId(id);
		missingReplyVO.setIsRead(isRead);
		dao.update(missingReplyVO);
		
		return missingReplyVO;
	}

	public void deleteMissingReply(Integer id) {
		dao.delete(id);
	}
	
	public MissingReplyVO getOneMissingReply(Integer id) {
		return dao.findByPrimaryKey(id);
	}
	
	public MissingReplyVO findByRecordIdMemId(Integer recordId, Integer memId) {
		return dao.findByRecordIdMemId(recordId, memId);
	}

	public Set<MissingReplyVO> findByRecordId(Integer recordId) {
		return dao.findByRecordId(recordId);
	}
	
	public Set<MissingReplyVO> findByMemId(Integer memId) {
		return dao.findByMemId(memId);
	}

	public List<MissingReplyVO> getAll() {
		return dao.getAll();
	}
}
