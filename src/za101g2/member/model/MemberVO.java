package za101g2.member.model;

import java.sql.Date;
import java.util.*;

import za101g2.missingreply.model.MissingReplyVO;
import za101g2.missingreport.model.MissingReportVO;
import za101g2.orderBoard.model.OrderBoardVO;
import za101g2.pet.model.PetVO;
import za101g2.store.model.StoreVO;
import za101g2.storeBoard.model.StoreBoardVO;
import za101g2.storeBoardReport.model.StoreBoardReportVO;

public class MemberVO implements java.io.Serializable {
	private Integer id;	
	private String email;
	private String password;
	private byte[] icon;
	private String nickname;
	private String phone;
	private String lastname;
	private String firstname;
	private String sex;
	private Date registerDate;
	private String status;	
	
	public Set<MissingReplyVO> getReplies() {
		return replies;
	}
	public void setReplies(Set<MissingReplyVO> replies) {
		this.replies = replies;
	}
	public Set<MissingReportVO> getReports() {
		return reports;
	}
	public void setReports(Set<MissingReportVO> reports) {
		this.reports = reports;
	}
	public Set<PetVO> getPets() {
		return pets;
	}
	public void setPets(Set<PetVO> pets) {
		this.pets = pets;
	}
	private Set<StoreVO> stores = new HashSet<StoreVO>();
	private Set<StoreBoardVO> storeBoards = new HashSet<StoreBoardVO>();
	private Set<StoreBoardReportVO> storeBoardReports = new HashSet<StoreBoardReportVO>();
	private Set<OrderBoardVO> orderBoards  = new HashSet<OrderBoardVO>();
	private Set<MissingReplyVO> replies = new HashSet<MissingReplyVO>();
	private Set<MissingReportVO> reports = new HashSet<MissingReportVO>();
	private Set<PetVO> pets = new HashSet<PetVO>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Set<StoreVO> getStores() {
		return stores;
	}
	public void setStores(Set<StoreVO> stores) {
		this.stores = stores;
	}
	public Set<StoreBoardVO> getStoreBoards() {
		return storeBoards;
	}
	public void setStoreBoards(Set<StoreBoardVO> storeBoards) {
		this.storeBoards = storeBoards;
	}
	public Set<StoreBoardReportVO> getStoreBoardReports() {
		return storeBoardReports;
	}
	public void setStoreBoardReports(Set<StoreBoardReportVO> storeBoardReports) {
		this.storeBoardReports = storeBoardReports;
	}
	
	public Set<OrderBoardVO> getOrderBoards() {
		return orderBoards;
	}
	public void setOrderBoards(Set<OrderBoardVO> orderBoards) {
		this.orderBoards = orderBoards;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public byte[] getIcon() {
		return icon;
	}
	public void setIcon(byte[] icon) {
		this.icon = icon;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
}
