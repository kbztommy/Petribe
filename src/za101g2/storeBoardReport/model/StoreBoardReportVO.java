package za101g2.storeBoardReport.model;
import java.sql.Date;

import za101g2.member.model.MemberVO;
import za101g2.storeBoard.model.StoreBoardVO;

public class StoreBoardReportVO implements java.io.Serializable{
	private StoreBoardVO storeBoardVO;
	private MemberVO memberVO;
	private String comments;
	private Date reportDate;
	
	public StoreBoardVO getStoreBoardVO() {
		return storeBoardVO;
	}
	public void setStoreBoardVO(StoreBoardVO storeBoardVO) {
		this.storeBoardVO = storeBoardVO;
	}
	
	public MemberVO getMemberVO() {
		return memberVO;
	}
	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
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
