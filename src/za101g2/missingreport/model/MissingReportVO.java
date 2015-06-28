package za101g2.missingreport.model;

import java.sql.Date;
import za101g2.missingreply.model.MissingReplyVO;
import za101g2.member.model.MemberVO;

public class MissingReportVO implements java.io.Serializable {
	private MemberVO memberVO;
	private MissingReplyVO missingReplyVO;
	private String comments;
	private Date reportDate;
	
	public MemberVO getMemberVO() {
		return memberVO;
	}
	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
	public MissingReplyVO getMissingReplyVO() {
		return missingReplyVO;
	}
	public void setMissingReplyVO(MissingReplyVO missingReplyVO) {
		this.missingReplyVO = missingReplyVO;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}	
}
