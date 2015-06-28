package za101g2.friends.model;

public class FriendsVO implements java.io.Serializable{
	private Integer memId;
	private Integer friendMemId;
	private String status;
	private java.sql.Timestamp timestamp;
	
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	public Integer getFriendMemId() {
		return friendMemId;
	}
	public void setFriendMemId(Integer friendMemId) {
		this.friendMemId = friendMemId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
	public java.sql.Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(java.sql.Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
