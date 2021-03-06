package za101g2.journalBoard.model;
import java.util.*;
import java.sql.*;

public class JournalBoardJDBCDAO implements JournalBoardDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "HR1";
	String passwd = "tiger";

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
	private static final String GET_BY_JOURNALID = "SELECT * FROM JournalBoard WHERE journalId = ?";
	
	@Override
	public int insert(JournalBoardVO iVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, iVO.getJournalId());
			pstmt.setInt(2, iVO.getMemId());
			pstmt.setString(3, iVO.getBoardMsg());
			pstmt.setString(4, iVO.getIsDelete());

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
	public int update(JournalBoardVO uVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, uVO.getJournalId());
			pstmt.setInt(2, uVO.getMemId());
			pstmt.setString(3, uVO.getBoardMsg());
			pstmt.setDate(4, uVO.getBoardMsgDate());
			pstmt.setString(5, uVO.getIsDelete());
			pstmt.setInt(6, uVO.getId());
			
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
	public int delete(Integer id) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, id);
			
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
	public JournalBoardVO findByPrimaryKey(Integer id) {

		JournalBoardVO qIdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
	public List<JournalBoardVO> getAll() {
		List<JournalBoardVO> list = new ArrayList<JournalBoardVO>();
		JournalBoardVO fAllVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
	public List<JournalBoardVO> findByJournalId(Integer journalId) {
		List<JournalBoardVO> list = new ArrayList<JournalBoardVO>();
		JournalBoardVO journalVO = null;

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
				journalVO = new JournalBoardVO();
				journalVO.setId(rs.getInt("id"));
				journalVO.setJournalId(rs.getInt("journalId"));
				journalVO.setMemId(rs.getInt("memId"));
				journalVO.setBoardMsg(rs.getString("boardMsg"));
				journalVO.setBoardMsgDate(rs.getDate("boardMsgDate"));
				journalVO.setIsDelete(rs.getString("isDelete"));
				list.add(journalVO);
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

		JournalBoardJDBCDAO dao = new JournalBoardJDBCDAO();
		
		 //�s�W
		 JournalBoardVO insertVO = new JournalBoardVO();
		 insertVO.setJournalId(1);
		 insertVO.setMemId(6);
		 insertVO.setBoardMsg("Hi how are you");
		 insertVO.setIsDelete("n");
		 int updateCount_insert = dao.insert(insertVO);
		 System.out.println(updateCount_insert);
		
		 //�ק�
//		 JournalBoardVO updateVO = new JournalBoardVO();
//		 updateVO.setId(5);
//		 updateVO.setJournalId(3);
//		 updateVO.setMemId(4);
//		 updateVO.setBoardMsg("Hi this is test");
//		 updateVO.setBoardMsgDate(java.sql.Date.valueOf("2015-05-03"));
//		 updateVO.setIsDelete("n");
//		 int updateCount_update = dao.update(updateVO);
//		 System.out.println(updateCount_update);
				
		 //�R��
//		 int updateCount_delete = dao.delete(6);
//		 System.out.println(updateCount_delete);
//
//		// �d��
//		JournalBoardVO qIdVO = dao.findByPrimaryKey(1);
//		System.out.print(qIdVO.getId() + ",");
//		System.out.print(qIdVO.getJournalId() + ",");
//		System.out.print(qIdVO.getMemId() + ",");
//		System.out.print(qIdVO.getBoardMsg() + ",");
//		System.out.print(qIdVO.getBoardMsgDate() + ",");
//		System.out.println(qIdVO.getIsDelete() + ",");
//		System.out.println("---------------------");
//
//		// �d��
//		List<JournalBoardVO> list = dao.getAll();
//		for (JournalBoardVO oneVo : list) {
//			System.out.print(oneVo.getId() + ",");
//			System.out.print(oneVo.getJournalId() + ",");
//			System.out.print(oneVo.getMemId() + ",");
//			System.out.print(oneVo.getBoardMsg() + ",");
//			System.out.print(oneVo.getBoardMsgDate() + ",");
//			System.out.print(oneVo.getIsDelete() + ",");
//			System.out.println();
//		}
//		
//		List<JournalBoardVO> list2 = dao.findByJournalId(1);
//		for (JournalBoardVO oneVo : list2) {
//			System.out.print(oneVo.getId() + ",");
//			System.out.print(oneVo.getJournalId() + ",");
//			System.out.print(oneVo.getMemId() + ",");
//			System.out.print(oneVo.getBoardMsg() + ",");
//			System.out.print(oneVo.getBoardMsgDate() + ",");
//			System.out.print(oneVo.getIsDelete() + ",");
//			System.out.println();
//		}
		
	}

	@Override
	public int updateIsDelete(JournalBoardVO VO) {
		// TODO Auto-generated method stub
		return 0;
	}
}