package za101g2.journalAssess.model;
import java.sql.Date;

public class JournalAssessVO implements java.io.Serializable{
	private Integer journalId;
	private Integer memId;
	
	public Integer getJournalId() {
		return journalId;
	}
	public void setJournalId(Integer journalId) {
		this.journalId = journalId;
	}
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}

}
