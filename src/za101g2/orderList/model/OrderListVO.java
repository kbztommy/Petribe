package za101g2.orderList.model;
import java.sql.Date;

import za101g2.orderBoard.model.OrderBoardVO;
import za101g2.serviceList.model.ServiceListVO;

public class OrderListVO implements java.io.Serializable{
	
	private OrderBoardVO orderBoardVO;	
	private ServiceListVO serviceListVO;
	private String petName;
	private Double discount;
	private Integer quantitly;
	private Date serviceDate;
	private String isReport;
	
	public OrderBoardVO getOrderBoardVO() {
		return orderBoardVO;
	}
	public void setOrderBoardVO(OrderBoardVO orderBoardVO) {
		this.orderBoardVO = orderBoardVO;
	}
	
	public ServiceListVO getServiceListVO() {
		return serviceListVO;
	}
	public void setServiceListVO(ServiceListVO serviceListVO) {
		this.serviceListVO = serviceListVO;
	}
	public String getPetName() {
		return petName;
	}
	public void setPetName(String petName) {
		this.petName = petName;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Integer getQuantitly() {
		return quantitly;
	}
	public void setQuantitly(Integer quantitly) {
		this.quantitly = quantitly;
	}
	public Date getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}
	public String getIsReport() {
		return isReport;
	}
	public void setIsReport(String isReport) {
		this.isReport = isReport;
	}
	
	
}
