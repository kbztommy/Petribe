package za101g2.serviceList.model;

import java.util.*;

import za101g2.orderList.model.OrderListVO;
import za101g2.store.model.StoreVO;

public class ServiceListVO implements java.io.Serializable{

	
	private Integer id;
	private String name;
	private String Info;
	private Integer price;
	private StoreVO storeVO;
	private String isOnsale;
	private String petType;
	private String chargeType;	
	private Set<OrderListVO> orderLists = new HashSet<OrderListVO>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Set<OrderListVO> getOrderLists() {
		return orderLists;
	}
	public void setOrderLists(Set<OrderListVO> orderLists) {
		this.orderLists = orderLists;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return Info;
	}
	public void setInfo(String info) {
		Info = info;
	}
	public StoreVO getStoreVO() {
		return storeVO;
	}
	public void setStoreVO(StoreVO storeVO) {
		this.storeVO = storeVO;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public String getIsOnsale() {
		return isOnsale;
	}
	public void setIsOnsale(String isOnsale) {
		this.isOnsale = isOnsale;
	}
	public String getPetType() {
		return petType;
	}
	public void setPetType(String petType) {
		this.petType = petType;
	}
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	
	
}
