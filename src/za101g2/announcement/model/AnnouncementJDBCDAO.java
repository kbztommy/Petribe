package za101g2.announcement.model;
import java.util.*;
import java.sql.*;

public class AnnouncementJDBCDAO implements AnnouncementDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "HR1";
	String passwd = "tiger";

	private static final String INSERT_STMT = 
		"INSERT INTO Announcement (id,title,comments,releaseDate,staffId) VALUES (Announcement_seq.NEXTVAL, ?, ?, SYSDATE, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT id,title,comments,to_char(releaseDate,'yyyy-mm-dd') releaseDate,staffId FROM Announcement order by id";
	private static final String GET_ONE_STMT = 
		"SELECT id,title,comments,to_char(releaseDate,'yyyy-mm-dd') releaseDate,staffId FROM Announcement where id = ?";
	private static final String DELETE = 
		"DELETE FROM Announcement where id = ?";
	private static final String UPDATE = 
		"UPDATE Announcement set title=?, comments=?, releaseDate=?, staffId=? where id = ?";
	private static final String GET_BY_STAFFID = "SELECT * FROM Announcement WHERE staffId = ?";
	
	@Override
	public int insert(AnnouncementVO iVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, iVO.getTitle());
			pstmt.setString(2, iVO.getComments());
			pstmt.setInt(3, iVO.getStaffId());

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
	public int update(AnnouncementVO uVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, uVO.getTitle());
			pstmt.setString(2, uVO.getComments());
			pstmt.setDate(3, uVO.getReleaseDate());
			pstmt.setInt(4, uVO.getStaffId());
			pstmt.setInt(5, uVO.getId());
			
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
	public AnnouncementVO findByPrimaryKey(Integer id) {

		AnnouncementVO qIdVO = null;
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
				qIdVO = new AnnouncementVO();
				qIdVO.setId(rs.getInt("id"));
				qIdVO.setTitle(rs.getString("title"));
				qIdVO.setComments(rs.getString("comments"));
				qIdVO.setReleaseDate(rs.getDate("releaseDate"));
				qIdVO.setStaffId(rs.getInt("staffId"));
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
	public List<AnnouncementVO> getAll() {
		List<AnnouncementVO> list = new ArrayList<AnnouncementVO>();
		AnnouncementVO fAllVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				fAllVO = new AnnouncementVO();
				fAllVO.setId(rs.getInt("id"));
				fAllVO.setTitle(rs.getString("title"));
				fAllVO.setComments(rs.getString("comments"));
				fAllVO.setReleaseDate(rs.getDate("releaseDate"));
				fAllVO.setStaffId(rs.getInt("staffId"));
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
	public List<AnnouncementVO> findByForeginKey(Integer staffId) {
		List<AnnouncementVO> list = new ArrayList<AnnouncementVO>();
		AnnouncementVO fByStaffVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_BY_STAFFID);
			pstmt.setInt(1, staffId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				fByStaffVO = new AnnouncementVO();
				fByStaffVO.setId(rs.getInt("id"));
				fByStaffVO.setTitle(rs.getString("title"));
				fByStaffVO.setComments(rs.getString("comments"));
				fByStaffVO.setReleaseDate(rs.getDate("releaseDate"));
				fByStaffVO.setStaffId(rs.getInt("staffId"));
				list.add(fByStaffVO);
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

		AnnouncementJDBCDAO dao = new AnnouncementJDBCDAO();
		
		 //�s�W
//		 AnnouncementVO insertVO = new AnnouncementVO();
//		 insertVO.setTitle("公告的標題");
//		 insertVO.setComments("沒什麼好公告的");
//		 insertVO.setStaffId(7); 
//		 int updateCount_insert = dao.insert(insertVO);
//		 System.out.println(updateCount_insert);
		
		 //�ק�
//		 AnnouncementVO updateVO = new AnnouncementVO();
//		 updateVO.setId(6);
//		 updateVO.setTitle("公告已改");
//		 updateVO.setComments("其實公告不能改");
//		 updateVO.setReleaseDate(java.sql.Date.valueOf("2015-05-03"));
//		 updateVO.setStaffId(2);
//		 int updateCount_update = dao.update(updateVO);
//		 System.out.println(updateCount_update);
				
		 //�R��
//		 int updateCount_delete = dao.delete(6);
//		 System.out.println(updateCount_delete);

		// �d��
//		AnnouncementVO qIdVO = dao.findByPrimaryKey(4);
//		System.out.print(qIdVO.getId() + ",");
//		System.out.print(qIdVO.getTitle() + ",");
//		System.out.print(qIdVO.getComments() + ",");
//		System.out.print(qIdVO.getReleaseDate() + ",");
//		System.out.println(qIdVO.getStaffId() + ",");
//		System.out.println("---------------------");

		// �d��
//		List<AnnouncementVO> list = dao.getAll();
//		for (AnnouncementVO oneVo : list) {
//			System.out.print(oneVo.getId() + ",");
//			System.out.print(oneVo.getTitle() + ",");
//			System.out.print(oneVo.getComments() + ",");
//			System.out.print(oneVo.getReleaseDate() + ",");
//			System.out.print(oneVo.getStaffId() + ",");
//			System.out.println();
//		}
		
		List<AnnouncementVO> list2 = dao.findByForeginKey(8);
		for (AnnouncementVO oneVo : list2) {
			System.out.print(oneVo.getId() + ",");
			System.out.print(oneVo.getTitle() + ",");
			System.out.print(oneVo.getComments() + ",");
			System.out.print(oneVo.getReleaseDate() + ",");
			System.out.print(oneVo.getStaffId() + ",");
			System.out.println();
		}
		
	}
}