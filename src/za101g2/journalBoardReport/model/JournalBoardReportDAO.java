package za101g2.journalBoardReport.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JournalBoardReportDAO implements JournalBoardReportDAO_interface {

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
		"INSERT INTO JournalBoardReport (journalMsgId,memId,comments,reportDate) VALUES (?, ?, ?, SYSDATE)";
	private static final String GET_ALL_STMT = 
		"SELECT journalMsgId,memId,comments,to_char(reportDate,'yyyy-mm-dd') reportDate FROM JournalBoardReport order by journalMsgId";
	private static final String GET_ONE_STMT = 
		"SELECT journalMsgId,memId,comments,to_char(reportDate,'yyyy-mm-dd') reportDate FROM JournalBoardReport where journalMsgId = ? and memId = ?";
	private static final String DELETE = 
		"DELETE FROM JournalBoardReport where journalMsgId = ? and memId = ?";
	private static final String UPDATE = 
		"UPDATE JournalBoardReport set comments=?, reportDate=? where journalMsgId = ? and memId=?";

	@Override
	public int insert(JournalBoardReportVO iVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, iVO.getJournalMsgId());
			pstmt.setInt(2, iVO.getMemId());
			pstmt.setString(3, iVO.getComments());

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
	public int update(JournalBoardReportVO uVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, uVO.getComments());
			pstmt.setDate(2, uVO.getReportDate());
			pstmt.setInt(3, uVO.getJournalMsgId());
			pstmt.setInt(4, uVO.getMemId());
			
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
	public int delete(Integer journalMsgId, Integer memId) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, journalMsgId);
			pstmt.setInt(2, memId);

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
	public JournalBoardReportVO findByPrimaryKey(Integer journalMsgId, Integer memId) {

		JournalBoardReportVO qIdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, journalMsgId);
			pstmt.setInt(2, memId);
	
			rs = pstmt.executeQuery();

			while (rs.next()) {
				qIdVO = new JournalBoardReportVO();
				qIdVO.setJournalMsgId(rs.getInt("journalMsgId"));
				qIdVO.setMemId(rs.getInt("memId"));
				qIdVO.setComments(rs.getString("comments"));
				qIdVO.setReportDate(rs.getDate("reportDate"));
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
	public List<JournalBoardReportVO> getAll() {
		List<JournalBoardReportVO> list = new ArrayList<JournalBoardReportVO>();
		JournalBoardReportVO fAllVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				fAllVO = new JournalBoardReportVO();
				fAllVO.setJournalMsgId(rs.getInt("journalMsgId"));
				fAllVO.setMemId(rs.getInt("memId"));
				fAllVO.setComments(rs.getString("comments"));
				fAllVO.setReportDate(rs.getDate("reportDate"));
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