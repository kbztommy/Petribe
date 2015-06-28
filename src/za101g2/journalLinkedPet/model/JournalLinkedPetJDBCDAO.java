package za101g2.journalLinkedPet.model;
import java.util.*;
import java.sql.*;

public class JournalLinkedPetJDBCDAO implements JournalLinkedPetDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "HR1";
	String passwd = "tiger";

	private static final String INSERT_STMT = 
		"INSERT INTO JournalLinkedPet (journalId,petId) VALUES (?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT journalId,petId FROM JournalLinkedPet order by petId";
	private static final String GET_ONE_STMT = 
		"SELECT journalId,petId FROM JournalLinkedPet where petId = ?";
	private static final String DELETE = 
		"DELETE FROM JournalLinkedPet where journalId = ? and petId = ?";

	@Override
	public int insert(JournalLinkedPetVO iVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, iVO.getJournalId());
			pstmt.setInt(2, iVO.getPetId());
			
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
	public int delete(Integer journalId, Integer petId) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, journalId);
			pstmt.setInt(2, petId);
			
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
	public List<JournalLinkedPetVO> findByPrimaryKey(Integer journalId) {
		List<JournalLinkedPetVO> list = new ArrayList<JournalLinkedPetVO>();
		JournalLinkedPetVO qIdVO = null;
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
				qIdVO = new JournalLinkedPetVO();
				qIdVO.setJournalId(rs.getInt("journalId"));
				qIdVO.setPetId(rs.getInt("petId"));
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
	public List<JournalLinkedPetVO> getAll() {
		List<JournalLinkedPetVO> list = new ArrayList<JournalLinkedPetVO>();
		JournalLinkedPetVO fAllVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				fAllVO = new JournalLinkedPetVO();
				fAllVO.setJournalId(rs.getInt("journalId"));
				fAllVO.setPetId(rs.getInt("petId"));
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

		JournalLinkedPetJDBCDAO dao = new JournalLinkedPetJDBCDAO();
		
		 //新增
		 JournalLinkedPetVO insertVO = new JournalLinkedPetVO();
		 insertVO.setJournalId(7);
		 insertVO.setPetId(3);
		 int updateCount_insert = dao.insert(insertVO);
		 System.out.println(updateCount_insert);
				
		 //刪除
		 int updateCount_delete = dao.delete(7, 3);
		 System.out.println(updateCount_delete);

		//依ID查詢
		List<JournalLinkedPetVO> listByPetId = dao.findByPrimaryKey(1);
		for (JournalLinkedPetVO oneVo : listByPetId) {
			System.out.print(oneVo.getJournalId() + ",");
			System.out.print(oneVo.getPetId() + ",");
			System.out.println();
		}

		//查詢全部
		List<JournalLinkedPetVO> list = dao.getAll();
		for (JournalLinkedPetVO oneVo : list) {
			System.out.print(oneVo.getJournalId() + ",");
			System.out.print(oneVo.getPetId() + ",");
			System.out.println();
		}
	}

	@Override
	public int deleteByJournal(Integer journalId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<JournalLinkedPetVO> findByJournalId(Integer journalId) {
		// TODO Auto-generated method stub
		return null;
	}
}