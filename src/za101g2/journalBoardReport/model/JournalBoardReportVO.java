package za101g2.journalBoardReport.model;
import java.sql.Date;

public class JournalBoardReportVO implements java.io.Serializable{
	private Integer journalMsgId;
	private Integer memId;
	private String comments;
	private Date reportDate;
	
	public Integer getJournalMsgId() {
		return journalMsgId;
	}
	public void setJournalMsgId(Integer journalMsgId) {
		this.journalMsgId = journalMsgId;
	}
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
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
