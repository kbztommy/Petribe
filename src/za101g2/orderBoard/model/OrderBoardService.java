package za101g2.orderBoard.model;
import java.sql.Timestamp;
import java.util.*;

import za101g2.member.model.*;
import za101g2.orderList.model.*;
import za101g2.serviceList.model.*;
import za101g2.store.model.*;
import za101g2.storeCalendar.model.*;


public class OrderBoardService {	
	
	private OrderBoardDAO_interface dao = null;
	
	public OrderBoardService(){
		dao = new OrderBoardDAO();
	}
	
	public OrderBoardVO getOne(Integer id){
		return dao.findByPrimaryKey(id);
	}
	
	public OrderBoardVO insertOrder(Integer memId,Integer storeId,String startDate,String endDate,String custAddress,
			String[] custOrderArray,Map<String,String> nameMap ,String[] petTypes,List<String> boardDayList,String[] petNames){
		Set<OrderListVO> orderListVOList = new HashSet<OrderListVO>();
		List<StoreCalendarVO> storeCalendarVOList = new ArrayList<StoreCalendarVO>();
		Integer total = 0;
		ServiceListService ServiceListSrv = new ServiceListService();
		StoreCalendarService storeCalendarSrv = new StoreCalendarService();
		MemberService memberSrv = new MemberService();
		ServiceListVO dogRequired = ServiceListSrv.getOnsaleService(storeId, "dog", "required");
		ServiceListVO catRequired = ServiceListSrv.getOnsaleService(storeId, "cat", "required");
		ServiceListVO pick = ServiceListSrv.getOnsaleService(storeId, "dog", "pick");		
		StoreService storeSrv = new StoreService();
		StoreVO otherStoreVO = storeSrv.getOneStore(storeId);
		OrderBoardVO orderBoardVO = new OrderBoardVO();
		//新增接送服務VO
		if(!custAddress.isEmpty()){
			
			for(int i =0;i<petNames.length;i++){
				OrderListVO orderListVO =new OrderListVO();
				orderListVO.setPetName(petNames[i]);
				orderListVO.setQuantitly(1);
				orderListVO.setServiceListVO(pick);
				orderListVO.setServiceDate(java.sql.Date.valueOf(startDate));
				orderListVO.setDiscount(1.0);
				orderListVO.setIsReport("n");
				orderListVO.setOrderBoardVO(orderBoardVO);
				orderListVOList.add(orderListVO);
				total += pick.getPrice();
			}
		}
		
		
		//新增住宿服務VO
		
		for(String boardDay : boardDayList){
			ServiceListVO serviceListVO=null;
			StoreCalendarVO storeCalendarVO = storeCalendarSrv.getOne(storeId, java.sql.Date.valueOf(boardDay));
			storeCalendarVOList.add(storeCalendarVO);			
			for(int i =0;i<petNames.length;i++){
				
				if("dog".equals(petTypes[i])){
					serviceListVO =dogRequired;
					total += dogRequired.getPrice();
				}
				else{
					serviceListVO = catRequired;
					total += catRequired.getPrice();
				}
				OrderListVO orderListVO =new OrderListVO();
				orderListVO.setPetName(petNames[i]);
				orderListVO.setQuantitly(1);
				orderListVO.setServiceListVO(serviceListVO);
				orderListVO.setServiceDate(java.sql.Date.valueOf(boardDay));
				orderListVO.setDiscount(1.0);
				orderListVO.setIsReport("n");
				orderListVO.setOrderBoardVO(orderBoardVO);
				orderListVOList.add(orderListVO);				
			}			
		}		
		//新增自訂服務VO
		if(custOrderArray!=null){
			for(String custOrder : custOrderArray){
				Integer serviceId =null;
				String[] orderInfo = custOrder.split("#\\$%");			
				serviceId = Integer.parseInt(orderInfo[0]);
				String petName = nameMap.get(orderInfo[1]);
				String srvDate = orderInfo[2];
				ServiceListVO serviceListVO = ServiceListSrv.getOneService(serviceId);
				OrderListVO orderListVO =new OrderListVO();
				orderListVO.setPetName(petName);
				orderListVO.setQuantitly(1);
				orderListVO.setServiceListVO(serviceListVO);
				orderListVO.setServiceDate(java.sql.Date.valueOf(srvDate));
				orderListVO.setDiscount(1.0);
				orderListVO.setIsReport("n");
				orderListVOList.add(orderListVO);
				orderListVO.setOrderBoardVO(orderBoardVO);
				total+=serviceListVO.getPrice();
			}
		}
		
		
		orderBoardVO.setMemberVO(memberSrv.getOneMember(memId));
		orderBoardVO.setStoreVO(otherStoreVO);
		orderBoardVO.setStartDate(java.sql.Timestamp.valueOf(startDate+" 12:00:00"));
		orderBoardVO.setEndDate(java.sql.Timestamp.valueOf(endDate+" 12:00:00"));
		orderBoardVO.setCustAddress(custAddress);
		orderBoardVO.setTotal(total);
		orderBoardVO.setStatus("0");
		orderBoardVO.setIsReport("n");
		orderBoardVO.setOrderLists(orderListVOList);
		orderBoardVO.setOrderDate(new java.sql.Timestamp(new Date().getTime()));
		
		orderBoardVO = dao.insertWithOrderList(orderBoardVO, storeCalendarVOList, petNames.length);
		return dao.findByPrimaryKey(orderBoardVO.getId());
	}
	
	public void update(OrderBoardVO orderBoardVO){
		dao.update(orderBoardVO);
	}
	
	public List<OrderBoardVO> getTomorrowList(Calendar calendar){
		return dao.getTomorrowList(calendar);
	}
	
	public void deleteWithCal(Integer id,List<String> boardDayList,Integer petNumber){
		Integer storeId = dao.findByPrimaryKey(id).getStoreVO().getId();
		StoreCalendarService storeCalendarSrv = new StoreCalendarService();
		List<StoreCalendarVO> storeCalendarVOList = new ArrayList<StoreCalendarVO>();
		for(String boardDay : boardDayList){			
			StoreCalendarVO storeCalendarVO = storeCalendarSrv.getOne(storeId, java.sql.Date.valueOf(boardDay));
			int curQuantitly =  storeCalendarVO.getCurQuantitly();
			storeCalendarVO.setCurQuantitly(curQuantitly-petNumber);
			storeCalendarVOList.add(storeCalendarVO);	
		}
		dao.deleteWithCal(id,storeCalendarVOList);
	}
	
	public List<OrderBoardVO> getMemberOrder(Integer memId){
		return dao.getMemberOrder(memId);
	}
	
	public List<OrderBoardVO> getStoreOrder(Integer storeId){
		return dao.getStoreOrder(storeId);
	}
	
	public List<OrderBoardVO> getStoreOrderWithStatus(Integer storeId,String status){
		return dao.getStoreOrderWithStatus(storeId, status);
	}
	
	public List<OrderBoardVO> getAllReport(){
		return dao.getAllReport();
	}
}
