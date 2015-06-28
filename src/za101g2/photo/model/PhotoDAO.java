package za101g2.photo.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_Photo;

public class PhotoDAO implements PhotoDAO_interface{

	private static DataSource ds = null;
	
	static{
		try{
			Context ctx = new InitialContext();
			ds =(DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		}catch(NamingException e){
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO PHOTO (id, name, format, photoFile, updateDate, memId) VALUES (Photo_seq.NEXTVAL, ?, ?, ?, SYSDATE, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM PHOTO ORDER BY ID";
	private static final String GET_ONE_STMT = "SELECT * FROM PHOTO WHERE ID = ?";
	private static final String DELETE = "DELETE FROM PHOTO WHERE ID = ?";
	private static final String UPDATE_NAME = "UPDATE PHOTO SET name = ? WHERE ID = ?";	
	private static final String GET_ID_BY_FK = "SELECT * FROM PHOTO WHERE memId = ? ORDER BY ID";
	
	@Override
	public void insert(PhotoVO photoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, photoVO.getName());
			pstmt.setString(2, photoVO.getFormat());
			pstmt.setBytes(3, photoVO.getPhotoFile());
			pstmt.setInt(4, photoVO.getMemId());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {

			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		
	}

	@Override
	public String insertReKey(PhotoVO photoVO) {
		String key = null;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
		
			con = ds.getConnection();
			String cols[] = {"ID"};
			pstmt = con.prepareStatement(INSERT_STMT,cols);

			pstmt.setString(1, photoVO.getName());
			pstmt.setString(2, photoVO.getFormat());
			pstmt.setBytes(3, photoVO.getPhotoFile());
			pstmt.setInt(4, photoVO.getMemId());
			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rs.next()) {
				do {
					for (int i = 1; i <= columnCount; i++) {
						key = rs.getString(i);
					}
				}while (rs.next());
			}
			rs.close();
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {

			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		return key;
	}
	
	@Override
	public void update_name(PhotoVO photoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_NAME);
			pstmt.setString(1, photoVO.getName());
			pstmt.setInt(2, photoVO.getId());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		
	}

	@Override
	public void delete(Integer id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
			con = ds.getConnection();
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

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
			con = ds.getConnection();
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

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}

		return list;
	}
	
	@Override
	public List<PhotoVO> findByForeginKey(Integer memId) {
		List<PhotoVO> list = new ArrayList<PhotoVO>();
		
		PhotoVO photoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ID_BY_FK);

			pstmt.setInt(1, memId);

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

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
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
			
			con = ds.getConnection();
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
	
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
