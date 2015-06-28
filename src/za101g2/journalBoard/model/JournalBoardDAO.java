package za101g2.journalBoard.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JournalBoardDAO implements JournalBoardDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = 
		"INSERT INTO JournalBoard (id,journalId,memId,boardMsg,boardMsgDate,isDelete) VALUES (journalBoard_seq.NEXTVAL, ?, ?, ?, SYSDATE, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT id,journalId,memId,boardMsg,to_char(boardMsgDate,'yyyy-mm-dd') boardMsgDate,isDelete FROM JournalBoard order by id";
	private static final String GET_ONE_STMT = 
		"SELECT id,journalId,memId,boardMsg,to_char(boardMsgDate,'yyyy-mm-dd') boardMsgDate,isDelete FROM JournalBoard where id = ?";
	private static final String DELETE = 
		"DELETE FROM JournalBoard where id = ?";
	private static final String UPDATE = 
		"UPDATE JournalBoard set journalId=?, memId=?, boardMsg=?, boardMsgDate=?, isDelete=? where id = ?";
	private static final String GET_BY_JOURNALID = "SELECT * FROM JournalBoard WHERE journalId = ? order by boardMsgDate desc" ;
	private static final String UPDATEISDELETE = 
		"UPDATE JournalBoard set isDelete=? where id = ?";
	
	@Override
	public int insert(JournalBoardVO iVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, iVO.getJournalId());
			pstmt.setInt(2, iVO.getMemId());
			pstmt.setString(3, iVO.getBoardMsg());
			pstmt.setString(4, iVO.getIsDelete());

			updateCount = pstmt.executeUpdate();

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
		return updateCount;
	}

	@Override
	public int update(JournalBoardVO uVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, uVO.getJournalId());
			pstmt.setInt(2, uVO.getMemId());
			pstmt.setString(3, uVO.getBoardMsg());
			pstmt.setDate(4, uVO.getBoardMsgDate());
			pstmt.setString(5, uVO.getIsDelete());
			pstmt.setInt(6, uVO.getId());

			updateCount = pstmt.executeUpdate();

			// Handle any driver errors
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
		return updateCount;
	}

	@Override
	public int updateIsDelete(JournalBoardVO uVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATEISDELETE);

			pstmt.setString(1, uVO.getIsDelete());
			pstmt.setInt(2, uVO.getId());

			updateCount = pstmt.executeUpdate();

			// Handle any driver errors
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
		return updateCount;
	}
	
	@Override
	public int delete(Integer id) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, id);

			updateCount = pstmt.executeUpdate();

			// Handle any driver errors
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
		return updateCount;
	}

	@Override
	public JournalBoardVO findByPrimaryKey(Integer id) {

		JournalBoardVO qIdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				qIdVO = new JournalBoardVO();
				qIdVO.setId(rs.getInt("id"));
				qIdVO.setJournalId(rs.getInt("journalId"));
				qIdVO.setMemId(rs.getInt("memId"));
				qIdVO.setBoardMsg(rs.getString("boardMsg"));
				qIdVO.setBoardMsgDate(rs.getDate("boardMsgDate"));
				qIdVO.setIsDelete(rs.getString("isDelete"));
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
		return qIdVO;
	}

	@Override
	public List<JournalBoardVO> getAll() {
		List<JournalBoardVO> list = new ArrayList<JournalBoardVO>();
		JournalBoardVO fAllVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				fAllVO = new JournalBoardVO();
				fAllVO.setId(rs.getInt("id"));
				fAllVO.setJournalId(rs.getInt("journalId"));
				fAllVO.setMemId(rs.getInt("memId"));
				fAllVO.setBoardMsg(rs.getString("boardMsg"));
				fAllVO.setBoardMsgDate(rs.getDate("boardMsgDate"));
				fAllVO.setIsDelete(rs.getString("isDelete"));
				list.add(fAllVO);
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
	public List<JournalBoardVO> findByJournalId(Integer journalId) {
		List<JournalBoardVO> list = new ArrayList<JournalBoardVO>();
		JournalBoardVO fAllVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_JOURNALID);
			pstmt.setInt(1, journalId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				fAllVO = new JournalBoardVO();
				fAllVO.setId(rs.getInt("id"));
				fAllVO.setJournalId(rs.getInt("journalId"));
				fAllVO.setMemId(rs.getInt("memId"));
				fAllVO.setBoardMsg(rs.getString("boardMsg"));
				fAllVO.setBoardMsgDate(rs.getDate("boardMsgDate"));
				fAllVO.setIsDelete(rs.getString("isDelete"));
				list.add(fAllVO);
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