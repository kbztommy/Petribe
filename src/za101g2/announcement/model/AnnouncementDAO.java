package za101g2.announcement.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AnnouncementDAO implements AnnouncementDAO_interface {

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
		"INSERT INTO Announcement (id,title,comments,releaseDate,staffId) VALUES (Announcement_seq.NEXTVAL, ?, ?, SYSDATE, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT id,title,comments,to_char(releaseDate,'yyyy-mm-dd') releaseDate,staffId FROM Announcement order by releaseDate DESC, id DESC";
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
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, iVO.getTitle());
			pstmt.setString(2, iVO.getComments());
			pstmt.setInt(3, iVO.getStaffId());

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
	public int update(AnnouncementVO uVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, uVO.getTitle());
			pstmt.setString(2, uVO.getComments());
			pstmt.setDate(3, uVO.getReleaseDate());
			pstmt.setInt(4, uVO.getStaffId());
			pstmt.setInt(5, uVO.getId());
			
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
	public AnnouncementVO findByPrimaryKey(Integer id) {

		AnnouncementVO qIdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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
	public List<AnnouncementVO> getAll() {
		List<AnnouncementVO> list = new ArrayList<AnnouncementVO>();
		AnnouncementVO fAllVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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
	public List<AnnouncementVO> findByForeginKey(Integer staffId) {
		List<AnnouncementVO> list = new ArrayList<AnnouncementVO>();
		AnnouncementVO fByStaffVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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