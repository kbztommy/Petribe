package za101g2.store.model;

import java.sql.Timestamp;
import java.util.*;

import org.hibernate.sql.Insert;

import za101g2.member.model.*;
import za101g2.serviceList.model.*;
import za101g2.storeCalendar.model.*;

public class StoreService {
	private StoreDAO_interface dao;
	
	public StoreService(){
		dao = new StoreDAO();
	}
	
	public void Insert(String name, String address, MemberVO memberVO, String info, Integer speciesLimit, String comments, Integer maxQuantitly){
		StoreVO storeVO = new StoreVO();
		storeVO.setName(name);
		storeVO.setAddress(address);
		storeVO.setMemberVO(memberVO);
		storeVO.setInfo(info);
		storeVO.setSpeciesLimit(speciesLimit);
		storeVO.setComments(comments);
		storeVO.setMaxQuantitly(maxQuantitly);
		dao.insert(storeVO);
	}
	
	public List<StoreVO> getAll() {
		return dao.getAll();
	}
	
	public List<StoreVO> getApplyAll() {
		return dao.getApplyAll();
	}
	
	public StoreVO getOneStore(Integer id){		
		return dao.findByPrimaryKey(id);
	}
	
	public StoreVO getStoreByFK(Integer id){
		return dao.findByForeginKey(id);
	}
	
	public void Update(StoreVO storeVO){
		dao.update(storeVO);
	}
	
	public void rankUp(StoreVO storeVO){
		dao.rankUp(storeVO);
	}
	
	public List<Integer> getAllIdbyCity(String city) {
		return dao.getAllIdbyCity(city);
	}
	
	public Map<String,Object> getStoreInfoMap(Integer id){
		
		Map<String,Object> storeInfoMap = new HashMap<String,Object>();
		MemberService memberSrv = new MemberService();
		ServiceListService serviceListSrv = new ServiceListService();
		StoreCalendarService storeCalendarSrv = new StoreCalendarService();
		Calendar calendar = GregorianCalendar.getInstance();
		int curMonth = calendar.get(Calendar.MONTH);
		int curYear = calendar.get(Calendar.YEAR);
		int nextMonth = (curMonth+1)%12;
		int nextYear = curMonth+1<12?curYear:curYear+1;
		int afterNextMonth = (curMonth+2)%12;
		int afterNextYear = curMonth+2<12?curYear:curYear+1;
		
		
		StoreVO otherStoreVO = dao.findByPrimaryKey(id);
		storeInfoMap.put("otherStoreVO", otherStoreVO);
		storeInfoMap.put("otherMemberVO", memberSrv.getOneMember(otherStoreVO.getMemberVO().getId()));
		
		ServiceListVO pickSrv =serviceListSrv.getService(id, "dog", "pick");
		if(pickSrv!=null && "y".equals(pickSrv.getIsOnsale())){
			storeInfoMap.put("pickSrv", pickSrv);
		}
		
		ServiceListVO requiredDogSrv = serviceListSrv.getService(id, "dog", "required");		
		if(requiredDogSrv!=null && requiredDogSrv.getIsOnsale().equals("y")){			
			List<ServiceListVO> custDogSrvList = serviceListSrv.getCustService(id, "dog");
			storeInfoMap.put("requiredDogSrv", requiredDogSrv);			
			storeInfoMap.put("custDogSrvList", custDogSrvList);
		}
		
		ServiceListVO requiredCatSrv = serviceListSrv.getService(otherStoreVO.getMemberVO().getId(), "cat", "required");
		if(requiredCatSrv!=null && requiredCatSrv.getIsOnsale().equals("y")){			
			List<ServiceListVO> custCatSrvList = serviceListSrv.getCustService(id, "cat");
			storeInfoMap.put("requiredCatSrv", requiredCatSrv);			
			storeInfoMap.put("custCatSrvList", custCatSrvList);
		}
		
		List<StoreCalendarVO> curStoreCalendar = storeCalendarSrv.getMonthCalendar(otherStoreVO.getMemberVO().getId(),curYear,curMonth+1);
		List<StoreCalendarVO> nextStoreCalendar = storeCalendarSrv.getMonthCalendar(otherStoreVO.getMemberVO().getId(),nextYear,nextMonth+1);
		List<StoreCalendarVO> afterNextStoreCalendar = storeCalendarSrv.getMonthCalendar(otherStoreVO.getMemberVO().getId(),afterNextYear,afterNextMonth+1);
		storeInfoMap.put("curStoreCalendar",curStoreCalendar);
		storeInfoMap.put("nextStoreCalendar",nextStoreCalendar);
		storeInfoMap.put("afterNextStoreCalendar",afterNextStoreCalendar);
		
		return storeInfoMap;
	}
	
	public List<HashMap<String,Object>> searchStoreVOList(String city,String petType){
			
		city = city.substring(0,2);
		ServiceListService serviceListSrv = new ServiceListService();
		MemberService memberSrv = new MemberService();
		List<Integer> storeIdListPet = null;
		List<Integer> storeIdListCity = null;
		List<StoreVO> storeVOList= null;
		if(!petType.isEmpty()){
			
			storeIdListPet = serviceListSrv.storeIdRequired(petType);			
		}else{
			storeIdListPet=serviceListSrv.getAllStoreId();
		}
		
		storeIdListCity = dao.getAllIdbyCity(city);
		//兩集合的交集
		storeIdListCity.retainAll(storeIdListPet);
		
		List<HashMap<String,Object>> storeList= new ArrayList<HashMap<String,Object>>();
		
		for(Integer storeId : storeIdListCity){
			HashMap<String,Object> Store= new HashMap<String,Object>();
			StoreVO storeVO= dao.findByPrimaryKey(storeId);
			MemberVO memberVO = memberSrv.getOneMember(storeVO.getMemberVO().getId());
			String memberNickname = memberVO.getNickname();
			Integer price = serviceListSrv.getMinPrice(storeId);
			Store.put("storeVO", storeVO);
			Store.put("memberNickname", memberNickname);
			Store.put("price", price);
			storeList.add(Store);
		}			
		
		return storeList;
	}
}
