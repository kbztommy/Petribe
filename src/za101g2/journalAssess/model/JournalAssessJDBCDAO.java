package za101g2.journalAssess.model;
import java.util.*;
import java.sql.*;

import za101g2.journal.model.JournalVO;

public class JournalAssessJDBCDAO implements JournalAssessDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "HR1";
	String passwd = "tiger";

	private static final String INSERT_STMT = 
		"INSERT INTO JournalAssess (journalId,memId) VALUES (?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT journalId,memId FROM JournalAssess order by journalId";
	private static final String GET_ONE_STMT = 
		"SELECT journalId,memId FROM JournalAssess where journalId = ?";
	private static final String DELETE = 
		"DELETE FROM JournalAssess where journalId = ? and memId = ?";
	private static final String IS_ASSESSED = 
		"SELECT journalId,memId FROM JournalAssess where journalId = ? and memId = ?";

	
	@Override
	public int insert(JournalAssessVO iVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, iVO.getJournalId());
			pstmt.setInt(2, iVO.getMemId());
			
			updateCount = pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public int delete(Integer journalId, Integer memId) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, journalId);
			pstmt.setInt(2, memId);
			
			updateCount = pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public List<JournalAssessVO> findByPrimaryKey(Integer journalId) {
		List<JournalAssessVO> list = new ArrayList<JournalAssessVO>();
		JournalAssessVO qIdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, journalId);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				qIdVO = new JournalAssessVO();
				qIdVO.setJournalId(rs.getInt("journalId"));
				qIdVO.setMemId(rs.getInt("memId"));
				list.add(qIdVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public List<JournalAssessVO> getAll() {
		List<JournalAssessVO> list = new ArrayList<JournalAssessVO>();
		JournalAssessVO fAllVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				fAllVO = new JournalAssessVO();
				fAllVO.setJournalId(rs.getInt("journalId"));
				fAllVO.setMemId(rs.getInt("memId"));
				list.add(fAllVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public boolean judgeAssess(Integer journalId,Integer memId) {

		JournalAssessVO qIdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(IS_ASSESSED);
			
			pstmt.setInt(1, journalId);
			pstmt.setInt(2, memId);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				qIdVO = new JournalAssessVO();
				qIdVO.setJournalId(rs.getInt("journalId"));
				qIdVO.setMemId(rs.getInt("memId"));
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return qIdVO!=null;
	}

	public static void main(String[] args) {

		JournalAssessJDBCDAO dao = new JournalAssessJDBCDAO();
		
		 //新增
//		 JournalAssessVO insertVO = new JournalAssessVO();
//		 insertVO.setJournalId(4);
//		 insertVO.setMemId(1);
//		 int updateCount_insert = dao.insert(insertVO);
//		 System.out.println(updateCount_insert);
//				
//		 //刪除
//		 int updateCount_delete = dao.delete(1, 4);
//		 System.out.println(updateCount_delete);
		 
		 boolean judge = dao.judgeAssess(1, 1);
		 System.out.println(judge);
		//依ID查詢
//		List<JournalAssessVO> listById = dao.findByPrimaryKey(2);
//		for (JournalAssessVO oneVo : listById) {
//			System.out.print(oneVo.getJournalId() + ",");
//			System.out.print(oneVo.getMemId() + ",");
//			System.out.println();
//		}

		//查詢全部
//		List<JournalAssessVO> list = dao.getAll();
//		for (JournalAssessVO oneVo : list) {
//			System.out.print(oneVo.getJournalId() + ",");
//			System.out.print(oneVo.getMemId() + ",");
//			System.out.println();
//		}
	}

	@Override
	public int deleteByJournalId(Integer journalId) {
		// TODO Auto-generated method stub
		return 0;
	}
}