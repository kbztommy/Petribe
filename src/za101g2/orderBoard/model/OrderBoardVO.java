package za101g2.orderBoard.model;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import za101g2.member.model.MemberVO;
import za101g2.onService.model.OnServiceVO;
import za101g2.orderList.model.OrderListVO;
import za101g2.store.model.StoreVO;

public class OrderBoardVO implements java.io.Serializable{	
	
	private Integer id ;
	private Timestamp orderDate;
	private Timestamp startDate;
	private Timestamp endDate;
	private Integer total;
	private MemberVO memberVO;
	private StoreVO storeVO;
	private Integer storeAsStar;
	private String storeAssess;
	private Integer memAsStar;
	private String memAssess;
	private String status;
	private String isReport;
	private String comments;
	private Timestamp reportDate;
	private String refundAcc;
	private Set<OrderListVO> orderLists = new HashSet<OrderListVO>();		
	
	public Set<OnServiceVO> getOnServices() {
		return onServices;
	}
	public void setOnServices(Set<OnServiceVO> onServices) {
		this.onServices = onServices;
	}
	private Set<OnServiceVO> onServices = new HashSet<OnServiceVO>();
	public Set<OrderListVO> getOrderLists() {
		return orderLists;
	}
	public void setOrderLists(Set<OrderListVO> orderLists) {
		this.orderLists = orderLists;
	}
	public Timestamp getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}
	private String custAddress;
	
	public String getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}

	
	public MemberVO getMemberVO() {
		return memberVO;
	}
	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
	public StoreVO getStoreVO() {
		return storeVO;
	}
	public void setStoreVO(StoreVO storeVO) {
		this.storeVO = storeVO;
	}
	public Integer getStoreAsStar() {
		return storeAsStar;
	}
	public void setStoreAsStar(Integer storeAsStar) {
		this.storeAsStar = storeAsStar;
	}
	public String getStoreAssess() {
		return storeAssess;
	}
	public void setStoreAssess(String storeAssess) {
		this.storeAssess = storeAssess;
	}
	public Integer getMemAsStar() {
		return memAsStar;
	}
	public void setMemAsStar(Integer memAsStar) {
		this.memAsStar = memAsStar;
	}
	public String getMemAssess() {
		return memAssess;
	}
	public void setMemAssess(String memAssess) {
		this.memAssess = memAssess;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsReport() {
		return isReport;
	}
	public void setIsReport(String isReport) {
		this.isReport = isReport;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Timestamp getReportDate() {
		return reportDate;
	}
	public void setReportDate(Timestamp reportDate) {
		this.reportDate = reportDate;
	}
	public String getRefundAcc() {
		return refundAcc;
	}
	public void setRefundAcc(String refundAcc) {
		this.refundAcc = refundAcc;
	}	

}
