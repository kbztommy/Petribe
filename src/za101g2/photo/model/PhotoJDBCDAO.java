package za101g2.photo.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_Photo;
import za101g2.photo.model.PhotoDAO_interface;
import za101g2.photo.model.PhotoVO;

public class PhotoJDBCDAO implements PhotoDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "hr1";
	String passwd = "tiger";


	private static final String INSERT_STMT = "INSERT INTO PHOTO (id, name, format, photoFile, updateDate, memId) VALUES (Photo_seq.NEXTVAL, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM PHOTO ORDER BY ID";
	private static final String GET_ONE_STMT = "SELECT * FROM PHOTO WHERE ID = ?";
	private static final String DELETE = "DELETE FROM PHOTO WHERE ID = ?";
	private static final String UPDATE_NAME = "UPDATE PHOTO SET name = ? WHERE ID = ?";	
	private static final String GET_BY_MEMID = "SELECT * FROM PHOTO WHERE memId = ?";
	
	@Override
	public void insert(PhotoVO photoVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, photoVO.getName());
			pstmt.setString(2, photoVO.getFormat());
			pstmt.setBytes(3, photoVO.getPhotoFile());
			pstmt.setDate(4, photoVO.getUpdateDate());
			pstmt.setInt(5, photoVO.getMemId());
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {			
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
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
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
			pstmt = con.prepareStatement(GET_BY_MEMID);

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
	
	public static void main(String[] args) {

		PhotoJDBCDAO dao = new PhotoJDBCDAO();

		// 新增
//		PhotoVO photoVO1 = new PhotoVO();
//		photoVO1.setName("name_p_4444");
//		photoVO1.setFormat("jpg");
//		photoVO1.setPhotoFile(null);
//		photoVO1.setUpdateDate(java.sql.Date.valueOf("2001-01-01"));
//		photoVO1.setMemId(3);
//		dao.insert(photoVO1);

		// 修改
//		PhotoVO photoVO2 = new PhotoVO();
//		photoVO2.setId(23);
//		photoVO2.setName("name_5555");
//		dao.update_name(photoVO2);

		// 刪除
//		dao.delete(23);

		// 查詢
		
//		PhotoVO photoVO3 = dao.findByPrimaryKey(23);
//		System.out.print(photoVO3.getId() + ",");
//		System.out.print(photoVO3.getName() + ",");
//		System.out.print(photoVO3.getFormat() + ",");
//		System.out.print(photoVO3.getPhotoFile() + ",");
//		System.out.print(photoVO3.getUpdateDate() + ",");
//		System.out.print(photoVO3.getMemId() + ",");
//		System.out.println("---------------------");
		
		//用FK查詢
		List<PhotoVO> list1 = dao.findByForeginKey(2);
		for (PhotoVO aPhoto : list1) {
		System.out.print(aPhoto.getId() + ",");
		System.out.print(aPhoto.getName() + ",");
		System.out.print(aPhoto.getFormat() + ",");
		System.out.print(aPhoto.getPhotoFile() + ",");
		System.out.print(aPhoto.getUpdateDate() + ",");
		System.out.print(aPhoto.getMemId() + ",");
		System.out.println("---------------------"+list1.size());
		}
		
		// 查詢
//		List<PhotoVO> list2 = dao.getAll();
//		for (PhotoVO aPhoto : list2) {
//			System.out.print(aPhoto.getId() + ",");
//			System.out.print(aPhoto.getName() + ",");
//			System.out.print(aPhoto.getFormat() + ",");
//			System.out.print(aPhoto.getPhotoFile() + ",");
//			System.out.print(aPhoto.getUpdateDate() + ",");
//			System.out.print(aPhoto.getMemId() + ",");
//			System.out.println();
//		}
	}

	@Override
	public String insertReKey(PhotoVO photoVO) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
