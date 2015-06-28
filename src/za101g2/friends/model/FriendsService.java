package za101g2.friends.model;

import java.util.List;

public class FriendsService {
	private FriendsDAO_interface dao;
	
	public FriendsService(){
		dao = new FriendsDAO();
	}
	
	public void addFriend(Integer memId, Integer friendMemId, String status){
		FriendsVO friendsVO = new FriendsVO();
		friendsVO.setMemId(memId);
		friendsVO.setFriendMemId(friendMemId);
		friendsVO.setStatus(status);
		dao.insert(friendsVO);
	}
	
	public void refuseBeFriend(Integer memId, Integer friendMemId){
		dao.refuse(memId, friendMemId);
	}
	
	public void acceptBeFriend(Integer memId, Integer friendMemId, String status){
		FriendsVO friendsVO = new FriendsVO();
		friendsVO.setMemId(memId);
		friendsVO.setFriendMemId(friendMemId);
		friendsVO.setStatus(status);
		dao.accept(friendsVO);
	}
	
	public void deleteFriend(Integer memId, Integer friendMemId){
		dao.delete(memId, friendMemId);
	}
	
	public FriendsVO hasApplied(Integer memId, Integer friendMemId, String status){
		return dao.hasApplied(memId, friendMemId, status);
	}
	
	public List<FriendsVO> findOnesFriends(Integer memId, String status){
		return dao.findOnesFriends(memId, status);
	}
	
	public List<FriendsVO> findOnesBeFriends(Integer friendMemId, String status){
		return dao.findOnesBeFriended(friendMemId, status);
	}
	
}
