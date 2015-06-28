package za101g2.missingreply.model;

import java.sql.Date;
import java.util.*;

import za101g2.missingrecord.model.MissingRecordVO;
import za101g2.missingreport.model.MissingReportVO;
import za101g2.member.model.MemberVO;

public class MissingReplyVO implements java.io.Serializable {
	private Integer id;
	private String comments;
	private MissingRecordVO missingRecordVO;
	private MemberVO memberVO;
	private String isRead;
	private Date reportDate;
	private Set<MissingReportVO> reports = new HashSet<MissingReportVO>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public MissingRecordVO getMissingRecordVO() {
		return missingRecordVO;
	}
	public void setMissingRecordVO(MissingRecordVO missingRecordVO) {
		this.missingRecordVO = missingRecordVO;
	}
	public MemberVO getMemberVO() {
		return memberVO;
	}
	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
	public String getIsRead() {
		return isRead;
	}
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public Set<MissingReportVO> getReports() {
		return reports;
	}
	public void setReports(Set<MissingReportVO> reports) {
		this.reports = reports;
	}
}
