package za101g2.message.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MessageDAO implements MessageDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_MESSAGE = "INSERT INTO message VALUES (?, ?, ?, sysdate, 'N')";
	private static final String MESSAGE_BE_READ = "UPDATE message SET isread='Y' WHERE memid=? and targetmemid=?";
	private static final String MESSAGE_OLD_HISTORY = "SELECT * FROM message WHERE ((memid=? and targetmemid=?) or (memid=? and targetmemid=?))AND isread='Y' order by sendtime desc";// 0616柏儒修改desc
	private static final String MESSAGE_NEW_HISTORY = "SELECT * FROM message WHERE ((memid=? and targetmemid=?) or (memid=? and targetmemid=?))AND isread='N' order by sendtime desc";
	private static final String MESSAGE_NEW = "SELECT count(*) count FROM message WHERE (targetmemid=? AND memid=?) and isread='N'";
	private static final String SENT = "SELECT * FROM message WHERE memid = ? and sendtime = (SELECT max(sendtime) FROM message WHERE memid = ?)";

	@Override
	public void insert(MessageVO messageVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_MESSAGE);

			pstmt.setInt(1, messageVO.getMemId());
			pstmt.setInt(2, messageVO.getTargetMemId());
			pstmt.setString(3, messageVO.getMsg());
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
	public void update(MessageVO messageVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(MESSAGE_BE_READ);

			pstmt.setInt(1, messageVO.getMemId());
			pstmt.setInt(2, messageVO.getTargetMemId());

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
	public List<MessageVO> messageHistory(Integer memId, Integer targetMemId) {

		List<MessageVO> list = new ArrayList<MessageVO>();
		MessageVO messageVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(MESSAGE_NEW_HISTORY);

			pstmt.setInt(1, memId);
			pstmt.setInt(2, targetMemId);
			pstmt.setInt(3, targetMemId);
			pstmt.setInt(4, memId);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				messageVO = new MessageVO();
				messageVO.setMemId(rs.getInt("memid"));
				messageVO.setTargetMemId(rs.getInt("targetmemid"));
				messageVO.setMsg(rs.getString("msg"));
				messageVO.setSentTime(rs.getTimestamp("sendtime"));
				messageVO.setIsRead(rs.getString("isread"));
				list.add(messageVO);
			}
			
			if (list.size() != 0) {

				messageVO = new MessageVO();
				messageVO.setMemId(memId);
				messageVO.setTargetMemId(targetMemId);
				messageVO.setMsg("<div class=\"text-center text-muted\"><b>《以下為新訊息》</b></div>");
				list.add(messageVO);
			}
			
			int count = list.size();
			
			pstmt = con.prepareStatement(MESSAGE_OLD_HISTORY);

			pstmt.setInt(1, memId);
			pstmt.setInt(2, targetMemId);
			pstmt.setInt(3, targetMemId);
			pstmt.setInt(4, memId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				messageVO = new MessageVO();
				messageVO.setMemId(rs.getInt("memid"));
				messageVO.setTargetMemId(rs.getInt("targetmemid"));
				messageVO.setMsg(rs.getString("msg"));
				messageVO.setSentTime(rs.getTimestamp("sendtime"));
				messageVO.setIsRead(rs.getString("isread"));
				list.add(messageVO);
				if(list.size()>=count+5){
				break;
				}
			}

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

		return list;
	}

	@Override
	public Long messageNew(Integer memId, Integer targetMemId) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long count = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(MESSAGE_NEW);

			pstmt.setInt(1, targetMemId);
			pstmt.setInt(2, memId);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getLong("count");
			}

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

		return count;
	}

	@Override
	public MessageVO sent(Integer memId) {

		MessageVO messageVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(SENT);
			pstmt.setInt(1, memId);
			pstmt.setInt(2, memId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				messageVO = new MessageVO();
				messageVO.setMemId(rs.getInt("memid"));
				messageVO.setTargetMemId(rs.getInt("targetmemid"));
				messageVO.setMsg(rs.getString("msg"));
				messageVO.setSentTime(rs.getTimestamp("sendtime"));
				messageVO.setIsRead(rs.getString("isread"));
			}

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

		return messageVO;
	}

}
