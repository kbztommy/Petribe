package za101g2.friends.model;

import java.util.List;

public interface FriendsDAO_interface {
	public void insert(FriendsVO friendsVO);				//好友通知(Friends)，你被加好友。
	public void refuse(Integer memId, Integer friendMemId);				//拒絕時進行刪除，系統需同時通知（Notify）被拒絕。
	public void accept(FriendsVO friendsVO);				//接受時設定為好友並增加好友，系統需同時通知（Notify）被接受。
	public void delete(Integer memId, Integer friendMemId);	//刪除時，系統需同時通知（Notify）被刪除。（暫）
	public FriendsVO hasApplied(Integer memId, Integer friendMemId, String status);
	public List<FriendsVO> findOnesFriends(Integer memId, String status);
	public List<FriendsVO> findOnesBeFriended(Integer friendMemId, String status);
}
