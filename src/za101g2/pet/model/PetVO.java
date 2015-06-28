package za101g2.pet.model;

import java.util.*;
import za101g2.missingrecord.model.MissingRecordVO;
import za101g2.member.model.MemberVO;

public class PetVO implements java.io.Serializable {
	private Integer id;
	private byte[] icon;
	private String name;
	private String species;
	private byte[] qrCode;
	private String status;
	private MemberVO memberVO;
	private Set<MissingRecordVO> records = new HashSet<MissingRecordVO>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public byte[] getIcon() {
		return icon;
	}
	public void setIcon(byte[] icon) {
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	public byte[] getQrCode() {
		return qrCode;
	}
	public void setQrCode(byte[] qrCode) {
		this.qrCode = qrCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public MemberVO getMemberVO() {
		return memberVO;
	}
	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
	public Set<MissingRecordVO> getRecords() {
		return records;
	}
	public void setRecords(Set<MissingRecordVO> records) {
		this.records = records;
	}
}
