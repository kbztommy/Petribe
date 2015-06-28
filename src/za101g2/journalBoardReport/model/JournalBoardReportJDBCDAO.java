package za101g2.journalBoardReport.model;
import java.util.*;
import java.sql.*;

public class JournalBoardReportJDBCDAO implements JournalBoardReportDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "HR1";
	String passwd = "tiger";

	private static final String INSERT_STMT = 
		"INSERT INTO JournalBoardReport (journalMsgId,memId,comments,reportDate) VALUES (?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT journalMsgId,memId,comments,to_char(reportDate,'yyyy-mm-dd') reportDate FROM JournalBoardReport order by journalMsgId";
	private static final String GET_ONE_STMT = 
		"SELECT journalMsgId,memId,comments,to_char(reportDate,'yyyy-mm-dd') reportDate FROM JournalBoardReport where journalMsgId = ? and memId = ?";
	private static final String DELETE = 
		"DELETE FROM JournalBoardReport where journalMsgId = ? and memId = ?";
	private static final String UPDATE = 
		"UPDATE JournalBoardReport set comments=?, reportDate=? where journalMsgId = ? and memId = ?";

	@Override
	public int insert(JournalBoardReportVO iVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, iVO.getJournalMsgId());
			pstmt.setInt(2, iVO.getMemId());
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
	public int update(JournalBoardReportVO uVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, uVO.getComments());
			pstmt.setDate(2, uVO.getReportDate());
			pstmt.setInt(3, uVO.getJournalMsgId());
			pstmt.setInt(4, uVO.getMemId());
			
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
	public int delete(Integer journalMsgId, Integer memId) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, journalMsgId);
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
	public JournalBoardReportVO findByPrimaryKey(Integer journalMsgId, Integer memId) {

		JournalBoardReportVO qIdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
	public List<JournalBoardReportVO> getAll() {
		List<JournalBoardReportVO> list = new ArrayList<JournalBoardReportVO>();
		JournalBoardReportVO fAllVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

		JournalBoardReportJDBCDAO dao = new JournalBoardReportJDBCDAO();
		
		 //�s�W
//		 JournalBoardReportVO insertVO = new JournalBoardReportVO();
//		 insertVO.setJournalMsgId(1);
//		 insertVO.setMemId(4);
//		 insertVO.setComments("�h���d��");
//		 insertVO.setReportDate(java.sql.Date.valueOf("2015-05-03"));
//		 int updateCount_insert = dao.insert(insertVO);
//		 System.out.println(updateCount_insert);
		
		 //�ק�
		 JournalBoardReportVO updateVO = new JournalBoardReportVO();
		 updateVO.setJournalMsgId(1);
		 updateVO.setMemId(4);
		 updateVO.setComments("�L�N�q���d��");
		 updateVO.setReportDate(java.sql.Date.valueOf("2015-05-03"));
		 int updateCount_update = dao.update(updateVO);
		 System.out.println(updateCount_update);
				
		 //�R��
//		 int updateCount_delete = dao.delete(3, 1);
//		 System.out.println(updateCount_delete);

		// �d��
		JournalBoardReportVO qIdVO = dao.findByPrimaryKey(2, 1);
		System.out.print(qIdVO.getJournalMsgId() + ",");
		System.out.print(qIdVO.getMemId() + ",");
		System.out.print(qIdVO.getComments() + ",");
		System.out.println(qIdVO.getReportDate() + ",");
		System.out.println("---------------------");

		// �d��
		List<JournalBoardReportVO> list = dao.getAll();
		for (JournalBoardReportVO oneVo : list) {
			System.out.print(oneVo.getJournalMsgId() + ",");
			System.out.print(oneVo.getMemId() + ",");
			System.out.print(oneVo.getComments() + ",");
			System.out.print(oneVo.getReportDate() + ",");
			System.out.println();
		}
	}
}