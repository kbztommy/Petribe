package za101g2.storeCalendar.model;

import java.sql.Date;
import java.util.List;

import za101g2.store.model.*;

public class StoreCalendarService {
	private StoreCalendarDAO_interface dao=null;
	
	public StoreCalendarService(){
		dao = new StoreCalendarDAO();
	}
	
	public void insert(StoreCalendarVO storeCalendarVO){
		dao.insert(storeCalendarVO);
	}
	
	public void insertMonth(List<StoreCalendarVO> storeCalendarVOList){
		dao.insertMonth(storeCalendarVOList);
	}
	
	public List<StoreCalendarVO> getMonthCalendar(Integer id,int year,int month){
		return dao.getMonthCalendar(id,year,month);
	}
	
	public void update(Integer id,String date,Integer maxQty,Integer curQty){
		StoreCalendarVO storeCalendarVO = new StoreCalendarVO();
		StoreService StoreSrv = new StoreService();
		StoreVO storeVO = StoreSrv.getOneStore(id);
		storeCalendarVO.setStoreVO(storeVO);
		storeCalendarVO.setServiceDate(java.sql.Date.valueOf(date));
		storeCalendarVO.setMaxQuantitly(maxQty);
		storeCalendarVO.setCurQuantitly(curQty);
		dao.update(storeCalendarVO);
	}
	
	public StoreCalendarVO getOne(Integer id,Date serviceDate){
		return dao.findByPrimaryKey(id, serviceDate);
	}	
}
