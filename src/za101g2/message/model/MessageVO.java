package za101g2.message.model;

public class MessageVO implements java.io.Serializable {
	
	private	Integer memId;
	private	Integer targetMemId;
	private String msg;
	private java.sql.Timestamp sendTime;
	private String isRead;
	
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	public Integer getTargetMemId() {
		return targetMemId;
	}
	public void setTargetMemId(Integer targetMemId) {
		this.targetMemId = targetMemId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public java.sql.Timestamp getSendTime() {
		return sendTime;
	}
	public void setSentTime(java.sql.Timestamp sendTime) {
		this.sendTime = sendTime;
	}
	public String getIsRead() {
		return isRead;
	}
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	
	
}
