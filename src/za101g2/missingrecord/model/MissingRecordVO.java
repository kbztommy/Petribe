package za101g2.missingrecord.model;

import java.sql.Date;
import java.util.*;

import za101g2.pet.model.PetVO;
import za101g2.missingreply.model.MissingReplyVO;

public class MissingRecordVO implements java.io.Serializable {
	private Integer id;
	private String location;
	private Date missingDate;
	private Integer bounty;
	private String comments;
	private PetVO petVO;
	private String status;
	private Integer bountyFor;
	private byte[] missingPhoto;
	private String latlng;
	private Set<MissingReplyVO> replies = new HashSet<MissingReplyVO>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getMissingDate() {
		return missingDate;
	}
	public void setMissingDate(Date missingDate) {
		this.missingDate = missingDate;
	}
	public Integer getBounty() {
		return bounty;
	}
	public void setBounty(Integer bounty) {
		this.bounty = bounty;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public PetVO getPetVO() {
		return petVO;
	}
	public void setPetVO(PetVO petVO) {
		this.petVO = petVO;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getBountyFor() {
		return bountyFor;
	}
	public void setBountyFor(Integer bountyFor) {
		this.bountyFor = bountyFor;
	}
	public byte[] getMissingPhoto() {
		return missingPhoto;
	}
	public void setMissingPhoto(byte[] missingPhoto) {
		this.missingPhoto = missingPhoto;
	}
	public String getLatlng() {
		return latlng;
	}
	public void setLatlng(String latlng) {
		this.latlng = latlng;
	}
	public Set<MissingReplyVO> getReplies() {
		return replies;
	}
	public void setReplies(Set<MissingReplyVO> replies) {
		this.replies = replies;
	}
}  