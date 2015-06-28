package za101g2.journalReport.model;
import java.util.*;
import java.sql.*;

public class JournalReportJDBCDAO implements JournalReportDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "HR1";
	String passwd = "tiger";

	private static final String INSERT_STMT = 
		"INSERT INTO JournalReport (memId,journalId,comments,reportDate) VALUES (?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT memId,journalId,comments,to_char(reportDate,'yyyy-mm-dd') reportDate FROM JournalReport order by journalId";
	private static final String GET_ONE_STMT = 
		"SELECT memId,journalId,comments,to_char(reportDate,'yyyy-mm-dd') reportDate FROM JournalReport where memId = ? and journalId = ?";
	private static final String DELETE = 
		"DELETE FROM JournalReport where memId = ? and journalId = ?";
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, iVO.getMemId());
			pstmt.setInt(2, iVO.getJournalId());
			pstmt.setString(3, iVO.getComments());
			pstmt.setDate(4, iVO.getReportDate());

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
	public int update(JournalReportVO uVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, uVO.getComments());
			pstmt.setDate(2, uVO.getReportDate());
			pstmt.setInt(3, uVO.getMemId());
			pstmt.setInt(4, uVO.getJournalId());
			
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
	public int delete(Integer memId, Integer journalId) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, memId);
			pstmt.setInt(2, journalId);
			
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
	public JournalReportVO findByPrimaryKey(Integer memId, Integer journalId) {

		JournalReportVO qIdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
	public List<JournalReportVO> getByJournalId(Integer journalId) {
		List<JournalReportVO> list = new ArrayList<JournalReportVO>();
		JournalReportVO fAllVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
	
	public static void main(String[] args) {

		JournalReportJDBCDAO dao = new JournalReportJDBCDAO();
		
		 //�s�W
//		 JournalReportVO insertVO = new JournalReportVO();
//		 insertVO.setMemId(4);
//		 insertVO.setJournalId(6);
//		 insertVO.setComments("暴君放黃色照片");
//		 insertVO.setReportDate(java.sql.Date.valueOf("2015-05-13"));
//		 int updateCount_insert = dao.insert(insertVO);
//		 System.out.println(updateCount_insert);
		
		 //�ק�
//		 JournalReportVO updateVO = new JournalReportVO();
//		 updateVO.setMemId(4);
//		 updateVO.setJournalId(6);
//		 updateVO.setComments("那只是黃顏色的圖片");
//		 updateVO.setReportDate(java.sql.Date.valueOf("2015-05-03"));
//		 int updateCount_update = dao.update(updateVO);
//		 System.out.println(updateCount_update);
				
		 //�R��
//		 int updateCount_delete = dao.delete(4, 6);
//		 System.out.println(updateCount_delete);

		// �d��
//		JournalReportVO qIdVO = dao.findByPrimaryKey(4, 5);
//		System.out.print(qIdVO.getMemId() + ",");
//		System.out.print(qIdVO.getJournalId() + ",");
//		System.out.print(qIdVO.getComments() + ",");
//		System.out.println(qIdVO.getReportDate() + ",");
//		System.out.println("---------------------");

		// �d��
//		List<JournalReportVO> list = dao.getAll();
//		for (JournalReportVO oneVo : list) {
//			System.out.print(oneVo.getMemId() + ",");
//			System.out.print(oneVo.getJournalId() + ",");
//			System.out.print(oneVo.getComments() + ",");
//			System.out.print(oneVo.getReportDate() + ",");
//			System.out.println();
//		}
		
		List<JournalReportVO> list2 = dao.getByJournalId(1);
		for (JournalReportVO oneVo : list2) {
			System.out.print(oneVo.getMemId() + ",");
			System.out.print(oneVo.getJournalId() + ",");
			System.out.print(oneVo.getComments() + ",");
			System.out.print(oneVo.getReportDate() + ",");
			System.out.println();
		}
	}

	@Override
	public int deleteByJournal(Integer journalId) {
		// TODO Auto-generated method stub
		return 0;
	}
}