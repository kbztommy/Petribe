package za101g2.store.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

import za101g2.member.model.MemberVO;
import za101g2.orderBoard.model.OrderBoardVO;
import za101g2.serviceList.model.ServiceListVO;
import za101g2.storeBoard.model.StoreBoardVO;
import za101g2.storeCalendar.model.StoreCalendarVO;
import za101g2.storePhoto.model.StorePhotoVO;

public class StoreVO implements java.io.Serializable{
	private Integer id;
	private String name;
	private String address;
	private MemberVO memberVO;
	private String info;
	private Integer speciesLimit;
	private Timestamp applyDate; 
	private String comments;
	private String status;
	private String siteReport;
	private Timestamp reportDate;
	private Integer maxQuantitly;
	private byte[] icon;
	private Set<OrderBoardVO> orderBoards = new HashSet<OrderBoardVO>();
	private Set<ServiceListVO> serviceLists = new HashSet<ServiceListVO>();
	private Set<StorePhotoVO> storePhotos = new HashSet<StorePhotoVO>();
	private Set<StoreCalendarVO> storeCalendars = new HashSet<StoreCalendarVO>();
	private Set<StoreBoardVO> storeBoards = new HashSet<StoreBoardVO>();
	
	public Set<OrderBoardVO> getOrderBoards() {
		return orderBoards;
	}
	public void setOrderBoards(Set<OrderBoardVO> orderBoards) {
		this.orderBoards = orderBoards;
	}
	public Set<ServiceListVO> getServiceLists() {
		return serviceLists;
	}
	public void setServiceLists(Set<ServiceListVO> serviceLists) {
		this.serviceLists = serviceLists;
	}
	public Set<StorePhotoVO> getStorePhotos() {
		return storePhotos;
	}
	public void setStorePhotos(Set<StorePhotoVO> storePhotos) {
		this.storePhotos = storePhotos;
	}
	public Set<StoreCalendarVO> getStoreCalendars() {
		return storeCalendars;
	}
	public void setStoreCalendars(Set<StoreCalendarVO> storeCalendars) {
		this.storeCalendars = storeCalendars;
	}
	public Set<StoreBoardVO> getStoreBoards() {
		return storeBoards;
	}
	public void setStoreBoards(Set<StoreBoardVO> storeBoards) {
		this.storeBoards = storeBoards;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public MemberVO getMemberVO() {
		return memberVO;
	}
	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Integer getSpeciesLimit() {
		return speciesLimit;
	}
	public void setSpeciesLimit(Integer speciesLimit) {
		this.speciesLimit = speciesLimit;
	}
	public Timestamp getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Timestamp applyDate) {
		this.applyDate = applyDate;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSiteReport() {
		return siteReport;
	}
	public void setSiteReport(String siteReport) {
		this.siteReport = siteReport;
	}
	public Timestamp getReportDate() {
		return reportDate;
	}
	public void setReportDate(Timestamp reportDate) {
		this.reportDate = reportDate;
	}
	public Integer getMaxQuantitly() {
		return maxQuantitly;
	}
	public void setMaxQuantitly(Integer maxQuantitly) {
		this.maxQuantitly = maxQuantitly;
	}
	public byte[] getIcon() {
		return icon;
	}
	public void setIcon(byte[] icon) {
		this.icon = icon;
	}
	
	
}