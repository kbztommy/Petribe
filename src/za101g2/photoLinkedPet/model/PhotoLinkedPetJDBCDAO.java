package za101g2.photoLinkedPet.model;
import java.util.*;
import java.sql.*;

public class PhotoLinkedPetJDBCDAO implements PhotoLinkedPetDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "HR1";
	String passwd = "tiger";

	private static final String INSERT_STMT = 
		"INSERT INTO PhotoLinkedPet (photoId,petId) VALUES (?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT photoId,petId FROM PhotoLinkedPet order by petId";
	private static final String GET_BY_PHOTOID = 
		"SELECT photoId,petId FROM PhotoLinkedPet where photoId = ?";
	private static final String GET_BY_PETID = 
			"SELECT photoId,petId FROM PhotoLinkedPet where petId = ?";
	private static final String DELETE = 
		"DELETE FROM PhotoLinkedPet where photoId = ? and petId = ?";

	@Override
	public int insert(PhotoLinkedPetVO iVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, iVO.getPhotoId());
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
	public int delete(Integer photoId, Integer petId) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, photoId);
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
	public List<PhotoLinkedPetVO> listByPhotoId(Integer photoId) {
		List<PhotoLinkedPetVO> list = new ArrayList<PhotoLinkedPetVO>();
		PhotoLinkedPetVO qIdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_BY_PHOTOID);
			
			pstmt.setInt(1, photoId);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				qIdVO = new PhotoLinkedPetVO();
				qIdVO.setPhotoId(rs.getInt("photoId"));
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
	public List<PhotoLinkedPetVO> listByPetId(Integer petId) {
		List<PhotoLinkedPetVO> list = new ArrayList<PhotoLinkedPetVO>();
		PhotoLinkedPetVO qIdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_BY_PETID);
			
			pstmt.setInt(1, petId);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				qIdVO = new PhotoLinkedPetVO();
				qIdVO.setPhotoId(rs.getInt("photoId"));
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
	public List<PhotoLinkedPetVO> getAll() {
		List<PhotoLinkedPetVO> list = new ArrayList<PhotoLinkedPetVO>();
		PhotoLinkedPetVO fAllVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				fAllVO = new PhotoLinkedPetVO();
				fAllVO.setPhotoId(rs.getInt("photoId"));
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

		PhotoLinkedPetJDBCDAO dao = new PhotoLinkedPetJDBCDAO();
		
		 //新增
		 PhotoLinkedPetVO insertVO = new PhotoLinkedPetVO();
		 insertVO.setPhotoId(32);
		 insertVO.setPetId(3);
		 int updateCount_insert = dao.insert(insertVO);
		 System.out.println(updateCount_insert);
				
		 //刪除
		 int updateCount_delete = dao.delete(32, 3);
		 System.out.println(updateCount_delete);

		//依PhotoId查詢
		List<PhotoLinkedPetVO> petList = dao.listByPhotoId(1);
		for (PhotoLinkedPetVO oneVo : petList) {
			System.out.print(oneVo.getPhotoId() + ",");
			System.out.print(oneVo.getPetId() + ",");
			System.out.println();
		}
		
		//依PetId查詢
		List<PhotoLinkedPetVO> photoList = dao.listByPetId(1);
		for (PhotoLinkedPetVO oneVo : photoList) {
			System.out.print(oneVo.getPhotoId() + ",");
			System.out.print(oneVo.getPetId() + ",");
			System.out.println();
		}
		
		//查詢全部
		List<PhotoLinkedPetVO> list = dao.getAll();
		for (PhotoLinkedPetVO oneVo : list) {
			System.out.print(oneVo.getPhotoId() + ",");
			System.out.print(oneVo.getPetId() + ",");
			System.out.println();
		}
	}

	@Override
	public int deleteByPhoto(Integer photoId) {
		// TODO Auto-generated method stub
		return 0;
	}
}