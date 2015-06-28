package za101g2.journalReport.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JournalReportDAO implements JournalReportDAO_interface {

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
			"INSERT INTO JournalReport (memId,journalId,comments,reportDate) VALUES (?, ?, ?, SYSDATE)";
		private static final String GET_ALL_STMT = 
			"SELECT memId,journalId,comments,to_char(reportDate,'yyyy-mm-dd') reportDate FROM JournalReport order by reportDate";
		private static final String GET_ONE_STMT = 
			"SELECT memId,journalId,comments,to_char(reportDate,'yyyy-mm-dd') reportDate FROM JournalReport where memId = ? and journalId = ?";
		private static final String DELETE = 
			"DELETE FROM JournalReport where memId = ? and journalId = ?";
		private static final String DELETEBYJOURNAL = 
			"DELETE FROM JournalReport where journalId = ?";

		private static final String UPDATE = 
			"UPDATE JournalReport set comments=?, reportDate=? where memId = ? and journalId = ?";
		private static final String GET_BY_JOURNALID = 
			"SELECT * FROM JournalReport WHERE journalId = ?";
		
	@Override
	public int insert(JournalReportVO iVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, iVO.getMemId());
			pstmt.setInt(2, iVO.getJournalId());
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
	public int update(JournalReportVO uVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, uVO.getComments());
			pstmt.setDate(2, uVO.getReportDate());
			pstmt.setInt(3, uVO.getMemId());
			pstmt.setInt(4, uVO.getJournalId());
			
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
	public int delete(Integer memId, Integer journalId) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, memId);
			pstmt.setInt(2, journalId);

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
	public int deleteByJournal(Integer journalId) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETEBYJOURNAL);

			pstmt.setInt(1, journalId);

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
	public JournalReportVO findByPrimaryKey(Integer memId, Integer journalId) {

		JournalReportVO qIdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, memId);
			pstmt.setInt(2, journalId);
	
			rs = pstmt.executeQuery();

			while (rs.next()) {
				qIdVO = new JournalReportVO();
				qIdVO.setMemId(rs.getInt("memId"));
				qIdVO.setJournalId(rs.getInt("journalId"));
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
	public List<JournalReportVO> getAll() {
		List<JournalReportVO> list = new ArrayList<JournalReportVO>();
		JournalReportVO fAllVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				fAllVO = new JournalReportVO();
				fAllVO.setMemId(rs.getInt("memId"));
				fAllVO.setJournalId(rs.getInt("journalId"));
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
	
	@Override
	public List<JournalReportVO> getByJournalId(Integer journalId) {
		List<JournalReportVO> list = new ArrayList<JournalReportVO>();
		JournalReportVO fAllVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_JOURNALID);
			pstmt.setInt(1, journalId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				fAllVO = new JournalReportVO();
				fAllVO.setMemId(rs.getInt("memId"));
				fAllVO.setJournalId(rs.getInt("journalId"));
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