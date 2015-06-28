package za101g2.storeBoard.model;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

import za101g2.member.model.MemberVO;
import za101g2.store.model.StoreVO;
import za101g2.storeBoardReport.model.StoreBoardReportVO;

public class StoreBoardVO implements java.io.Serializable{
	private Integer id;
	private StoreVO storeVO;
	private MemberVO memberVO;
	private String boardMsg;
	private Timestamp boardMsgDate;
	private String isDelete;
	
	private Set<StoreBoardReportVO> storeBoardReports = new HashSet<StoreBoardReportVO>();
	
	public Set<StoreBoardReportVO> getStoreBoardReports() {
		return storeBoardReports;
	}
	public void setStoreBoardReports(Set<StoreBoardReportVO> storeBoardReports) {
		this.storeBoardReports = storeBoardReports;
	}
	public StoreVO getStoreVO() {
		return storeVO;
	}
	public void setStoreVO(StoreVO storeVO) {
		this.storeVO = storeVO;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public MemberVO getMemberVO() {
		return memberVO;
	}
	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
	public String getBoardMsg() {
		return boardMsg;
	}
	public void setBoardMsg(String boardMsg) {
		this.boardMsg = boardMsg;
	}
	public Timestamp getBoardMsgDate() {
		return boardMsgDate;
	}
	public void setBoardMsgDate(Timestamp boardMsgDate) {
		this.boardMsgDate = boardMsgDate;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	
	
}
