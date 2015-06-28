package za101g2.storeCalendar.model;
import java.sql.Date;

import za101g2.store.model.StoreVO;

public class StoreCalendarVO implements java.io.Serializable{
	private StoreVO storeVO;
	private Date serviceDate;
	private Integer maxQuantitly;
	private Integer curQuantitly;

		
	
	public StoreVO getStoreVO() {
		return storeVO;
	}
	public void setStoreVO(StoreVO storeVO) {
		this.storeVO = storeVO;
	}
	public Date getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}
	public Integer getMaxQuantitly() {
		return maxQuantitly;
	}
	public void setMaxQuantitly(Integer maxQuantitly) {
		this.maxQuantitly = maxQuantitly;
	}
	public Integer getCurQuantitly() {
		return curQuantitly;
	}
	public void setCurQuantitly(Integer curQuantitly) {
		this.curQuantitly = curQuantitly;
	}	
	
}
