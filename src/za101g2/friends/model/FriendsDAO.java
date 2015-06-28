package za101g2.friends.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class FriendsDAO implements FriendsDAO_interface {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_FRIEND = 
			"INSERT INTO friends  VALUES (?, ?, ?, sysdate)";
	private static final String UPDATE_FRIEND =
			"UPDATE friends SET status=?,timestamp=sysdate where memid=? and friendmemid=?";
	private static final String DELETE_FRIEND =
			"DELETE friends WHERE memid=? and friendmemid=?";
	private static final String FIND_ONES_FRIENDSLIST =
			"SELECT * FROM friends WHERE memid=? and status=?";
	private static final String FIND_ONES_BEFRIENDEDLIST =
			"SELECT * FROM friends WHERE friendmemid=? and status=?";
	private static final String FIND_HAS_APPLY =
			"SELECT * FROM friends WHERE memid=? and friendmemid=? and status=?";
	
	@Override
	public void insert(FriendsVO friendsVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		//選擇某會員加入好友。
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_FRIEND);

			pstmt.setInt(1, friendsVO.getMemId());
			pstmt.setInt(2, friendsVO.getFriendMemId());
			pstmt.setString(3, friendsVO.getStatus());
			pstmt.executeUpdate();
			

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}
		
	@Override
	public void refuse(Integer memId, Integer friendMemId) {
		//拒絕某會員加入好友。
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_FRIEND);

			pstmt.setInt(1, memId);
			pstmt.setInt(2, friendMemId);
			pstmt.executeUpdate();
			

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}

	@Override
	public void accept(FriendsVO friendsVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		//接受某會員加入好友。
		try {

			con = ds.getConnection();
			
			con.setAutoCommit(false);			
			
			//好友接受步驟之一：為該會員的好友名單新增接受之會員。
			
			pstmt = con.prepareStatement(INSERT_FRIEND);
			pstmt.setInt(1, friendsVO.getMemId());
			pstmt.setInt(2, friendsVO.getFriendMemId());
			pstmt.setString(3, friendsVO.getStatus());
			pstmt.executeUpdate();
			
			//好友接受步驟之二：為送出邀請的會員將該名單狀態改為被接受。（將用來新增的FriendsVO反過來更新即可）
		
			pstmt = con.prepareStatement(UPDATE_FRIEND);

			pstmt.setString(1, friendsVO.getStatus());
			pstmt.setInt(2, friendsVO.getFriendMemId());
			pstmt.setInt(3, friendsVO.getMemId());
			pstmt.executeUpdate();
			
			con.commit();
			con.setAutoCommit(true);

			// Handle any SQL errors
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}

	@Override
	public void delete(Integer memId, Integer friendMemId) {
		//刪除某會員為好友。
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			
			con.setAutoCommit(false);

			//先刪一邊。
			
			pstmt = con.prepareStatement(DELETE_FRIEND);

			pstmt.setInt(1, memId);
			pstmt.setInt(2, friendMemId);
			pstmt.executeUpdate();
			
			//再刪另一邊。（同樣將拿到的兩邊的會員編號反過來填即可）
			
			pstmt = con.prepareStatement(DELETE_FRIEND);

			pstmt.setInt(1, friendMemId);
			pstmt.setInt(2, memId);
			pstmt.executeUpdate();
			
			con.commit();
			con.setAutoCommit(true);
			
			// Handle any SQL errors
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}
	
	@Override
	public FriendsVO hasApplied(Integer memId, Integer friendMemId, String status) {
		FriendsVO friendsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_HAS_APPLY);
			pstmt.setInt(1, memId);
			pstmt.setInt(2, friendMemId);
			pstmt.setString(3, status);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				friendsVO = new FriendsVO();
				friendsVO.setMemId(rs.getInt("memid"));
				friendsVO.setFriendMemId(rs.getInt("friendmemid"));
				friendsVO.setStatus(rs.getString("status"));	
				friendsVO.setTimestamp(rs.getTimestamp("timestamp"));
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return friendsVO;
	}
	

	@Override
	public List<FriendsVO> findOnesFriends(Integer memId, String status) {
		//某人的好友名單。
		List<FriendsVO> list = new ArrayList<FriendsVO>();
		FriendsVO friendsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_ONES_FRIENDSLIST);
			pstmt.setInt(1, memId);
			pstmt.setString(2, status);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				friendsVO = new FriendsVO();
				friendsVO.setMemId(rs.getInt("memid"));
				friendsVO.setFriendMemId(rs.getInt("friendmemid"));
				friendsVO.setStatus(rs.getString("status"));	
				friendsVO.setTimestamp(rs.getTimestamp("timestamp"));
				list.add(friendsVO);
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public List<FriendsVO> findOnesBeFriended(Integer friendMemId, String status) {
		//某人有人加你為好友的名單。
		List<FriendsVO> list = new ArrayList<FriendsVO>();
		FriendsVO friendsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_ONES_BEFRIENDEDLIST);
			pstmt.setInt(1, friendMemId);
			pstmt.setString(2, status);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				friendsVO = new FriendsVO();
				friendsVO.setMemId(rs.getInt("memid"));
				friendsVO.setFriendMemId(rs.getInt("friendmemid"));
				friendsVO.setStatus(rs.getString("status"));
				friendsVO.setTimestamp(rs.getTimestamp("timestamp"));
				list.add(friendsVO);
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

}
