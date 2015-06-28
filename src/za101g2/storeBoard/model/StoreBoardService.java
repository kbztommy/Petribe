package za101g2.storeBoard.model;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import za101g2.member.model.MemberService;
import za101g2.store.model.StoreService;

public class StoreBoardService {
	private StoreBoardDAO_interface dao = null;
	
	public StoreBoardService(){
		dao = new StoreBoardDAO();
	}
	public StoreBoardVO insert(Integer memberId,Integer storeId,String comments){		
		StoreService storeSrv = new StoreService();
		MemberService memberSrv = new MemberService();
    	StoreBoardVO storeBoardVO = new StoreBoardVO();
    	storeBoardVO.setBoardMsg(comments);
    	storeBoardVO.setIsDelete("n");
    	storeBoardVO.setBoardMsgDate(new java.sql.Timestamp(new Date().getTime()));
    	storeBoardVO.setMemberVO(memberSrv.getOneMember(memberId));
    	storeBoardVO.setStoreVO(storeSrv.getOneStore(storeId));    	
		return dao.insert(storeBoardVO);
	}
	public void update(StoreBoardVO storeBoardVO){
		dao.update(storeBoardVO);
	}
	public void delete(Integer id){
		dao.delete(id);
	}
	public StoreBoardVO findByPrimaryKey(Integer id){
		return dao.findByPrimaryKey(id);
	}
	public List<StoreBoardVO> getAll(){
		return dao.getAll();
	}
	
	public List<StoreBoardVO> getUndelete(Integer storeId){
		return dao.getUndelete(storeId);
	}
	
	public List<StoreBoardVO> getMoreUndelete(Integer storeId,Integer from,Integer count){
		return dao.getMoreUndelete(storeId, from, count);
	}
	
}
