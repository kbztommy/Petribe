package za101g2.photo.model;
import java.util.*;
import java.sql.*;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_Photo;

public class PhotoDAO_autoGeneratedKeys implements PhotoDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "hr1";
	String passwd = "tiger";

	private static final String INSERT_STMT = "INSERT INTO PHOTO (id, name, format, photoFile, updateDate, memId) VALUES (Photo_seq.NEXTVAL, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM PHOTO ORDER BY ID";
	private static final String GET_ONE_STMT = "SELECT * FROM PHOTO WHERE ID = ?";
	private static final String DELETE = "DELETE FROM PHOTO WHERE ID = ?";
	private static final String UPDATE_NAME = "UPDATE PHOTO SET name = ? WHERE ID = ?";	
	private static final String GET_ID_BY_FK = "SELECT * FROM PHOTO WHERE memId = ?";

	@Override
	public void insert(PhotoVO photoVO) {
		String key = null;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);			
			String cols[] = {"ID"};
			pstmt = con.prepareStatement(INSERT_STMT,cols);
			
			pstmt.setString(1, photoVO.getName());
			pstmt.setString(2, photoVO.getFormat());
			pstmt.setBytes(3, photoVO.getPhotoFile());
			pstmt.setDate(4, photoVO.getUpdateDate());
			pstmt.setInt(5, photoVO.getMemId());
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rs.next()) {
				do {
					for (int i = 1; i <= columnCount; i++) {
						key = rs.getString(i);
						System.out.println("自增主鍵值(" + i + ") = " + key +"剛新增成功的ID編號");
					}
				}while (rs.next());
			} else {
				System.out.println("NO KEYS WERE GENERATED.");
			}
			rs.close();

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
		//return key;
	}

	@Override
	public void update_name(PhotoVO photoVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_NAME);

			pstmt.setString(1, photoVO.getName());
			pstmt.setInt(2, photoVO.getId());
			pstmt.executeUpdate();

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
	}

	@Override
	public void delete(Integer id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

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
	}

	@Override
	public PhotoVO findByPrimaryKey(Integer id) {

		PhotoVO photoVO = null;
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
				photoVO = new PhotoVO();
				photoVO.setId(rs.getInt("id"));
				photoVO.setName(rs.getString("name"));
				photoVO.setFormat(rs.getString("format"));
				photoVO.setPhotoFile(rs.getBytes("photoFile"));
				photoVO.setUpdateDate(rs.getDate("updateDate"));
				photoVO.setMemId(rs.getInt("memId"));
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
		return photoVO;
	}

	@Override
	public List<PhotoVO> getAll() {
		List<PhotoVO> list = new ArrayList<PhotoVO>();
		PhotoVO photoVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				photoVO = new PhotoVO();
				photoVO.setId(rs.getInt("id"));
				photoVO.setName(rs.getString("name"));
				photoVO.setFormat(rs.getString("format"));
				photoVO.setPhotoFile(rs.getBytes("photoFile"));
				photoVO.setUpdateDate(rs.getDate("updateDate"));
				photoVO.setMemId(rs.getInt("memId"));
				list.add(photoVO); 
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
	public List<PhotoVO> getAll(Map<String, String[]> map) {
		
		List<PhotoVO> list = new ArrayList<PhotoVO>();
		PhotoVO photoVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			String finalSQL = "SELECT * FROM PHOTO "
		          + jdbcUtil_CompositeQuery_Photo.get_WhereCondition(map)
		          + "ORDER BY ID";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("查詢finalSQL(by DAO) = "+finalSQL);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				photoVO = new PhotoVO();
				photoVO.setId(rs.getInt("id"));
				photoVO.setName(rs.getString("name"));
				photoVO.setFormat(rs.getString("format"));
				photoVO.setPhotoFile(rs.getBytes("photoFile"));
				photoVO.setUpdateDate(rs.getDate("updateDate"));
				photoVO.setMemId(rs.getInt("memId"));
				list.add(photoVO); // Store the row in the List
			}
	
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			
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
	public List<PhotoVO> findByForeginKey(Integer id) {
		List<PhotoVO> list = new ArrayList<PhotoVO>();
		
		PhotoVO photoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ID_BY_FK);

			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
			
				photoVO = new PhotoVO();				
				photoVO.setId(rs.getInt("id"));
				photoVO.setName(rs.getString("name"));
				photoVO.setFormat(rs.getString("format"));
				photoVO.setPhotoFile(rs.getBytes("photoFile"));
				photoVO.setUpdateDate(rs.getDate("updateDate"));
				photoVO.setMemId(rs.getInt("memId"));
				list.add(photoVO);
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			
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

		PhotoDAO_autoGeneratedKeys dao = new PhotoDAO_autoGeneratedKeys();

		// 新增
		PhotoVO photoVO1 = new PhotoVO();
		photoVO1.setName("name_p_4444");
		photoVO1.setFormat("jpg");
		photoVO1.setPhotoFile(null);
		photoVO1.setUpdateDate(java.sql.Date.valueOf("2001-01-01"));
		photoVO1.setMemId(3);
		dao.insert(photoVO1);
		
		// 查詢
		List<PhotoVO> list2 = dao.getAll();
		for (PhotoVO aPhoto : list2) {
			System.out.print(aPhoto.getId() + ",");
			System.out.print(aPhoto.getName() + ",");
			System.out.print(aPhoto.getFormat() + ",");
			System.out.print(aPhoto.getPhotoFile() + ",");
			System.out.print(aPhoto.getUpdateDate() + ",");
			System.out.print(aPhoto.getMemId() + ",");
			System.out.println();
		}
	}

	@Override
	public String insertReKey(PhotoVO photoVO) {
		// TODO Auto-generated method stub
		return null;
	}
}