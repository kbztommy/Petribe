package za101g2.journalBoard.model;
import java.sql.Date;

public class JournalBoardVO implements java.io.Serializable{
	private Integer id;
	private Integer journalId;
	private Integer memId;
	private String boardMsg;
	private Date boardMsgDate;
	private String isDelete;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public String getBoardMsg() {
		return boardMsg;
	}
	public void setBoardMsg(String boardMsg) {
		this.boardMsg = boardMsg;
	}
	public Date getBoardMsgDate() {
		return boardMsgDate;
	}
	public void setBoardMsgDate(Date boardMsgDate) {
		this.boardMsgDate = boardMsgDate;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	
}
