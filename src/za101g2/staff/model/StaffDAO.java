package za101g2.staff.model;

import javax.naming.*;
import javax.sql.*;

import java.sql.*;
import java.util.*;

public class StaffDAO implements StaffDAO_interface {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STAFF = 
			"INSERT INTO staff  VALUES (staff_id_seq.NEXTVAL,? , DBMS_RANDOM.STRING( 'X', 10 ), ?, ?)";
	private static final String UPDATE_STAFF_NAME =
			"UPDATE staff set name=? where id=?";
	private static final String UPDATE_STAFF_STATUS =
			"UPDATE staff set status=? where id=?";
	private static final String UPDATE_STAFF_PASSWORD =
			"UPDATE staff set password=? where id=?";
	private static final String GET_ONE =
			"SELECT * FROM staff where id=?";
	private static final String GET_COUNT_BY_EMAIL =
			"SELECT  count(*) count FROM staff where email=?";
	private static final String GET_ALL =
			"SELECT * FROM staff order by id";
	private static final String GET_SOME_BY_KEYWORD =
			"SELECT * FROM staff WHERE email LIKE ? OR name LIKE ? OR id=? order by id";
	private static final String LOGIN = 
			"SELECT * FROM staff WHERE email=? and password=?";
	
	@Override
	public String insertStaff(StaffVO staffVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] keys = {"id"};
		String key = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STAFF, keys);
			
			pstmt.setString(1, staffVO.getEmail());
			pstmt.setString(2, staffVO.getName());
			pstmt.setString(3, staffVO.getStatus());

			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			
			while(rs.next()){
				key = rs.getString(1);
			}
			
			return key;
			

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
		
	}
	@Override
	public void updateStaffName(Integer id, String name) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STAFF_NAME);

			pstmt.setString(1, name);
			pstmt.setInt(2, id);

			pstmt.executeUpdate();

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
		
	}
	@Override
	public void updateStaffStatus(Integer id, String status) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STAFF_STATUS);

			pstmt.setString(1, status);
			pstmt.setInt(2, id);

			pstmt.executeUpdate();

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
		
	}
	
	@Override
	public void updateStaffPassword(Integer id, String password) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STAFF_PASSWORD);

			pstmt.setString(1, password);
			pstmt.setInt(2, id);

			pstmt.executeUpdate();

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
		
	}
	
	@Override
	public StaffVO findByPrimaryKey(Integer id) {
		StaffVO staffVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);

			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				staffVO = new StaffVO();
				staffVO.setId(rs.getInt("id"));
				staffVO.setEmail(rs.getString("email"));
				staffVO.setPassword(rs.getString("password"));
				staffVO.setName(rs.getString("name"));
				staffVO.setStatus(rs.getString("status"));			
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
		return staffVO;
	}
	
	@Override
	public Long findByEmail(String email) {
		Long count = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_COUNT_BY_EMAIL);

			pstmt.setString(1, email);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				count = rs.getLong("count");		
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
		return count;
	}
	
	@Override
	public List<StaffVO> getAll() {
		
		List<StaffVO> list = new ArrayList<StaffVO>();
		StaffVO staffVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				staffVO = new StaffVO();
				staffVO.setId(rs.getInt("id"));
				staffVO.setEmail(rs.getString("email"));
				staffVO.setPassword(rs.getString("password"));
				staffVO.setName(rs.getString("name"));
				staffVO.setStatus(rs.getString("status"));	
				list.add(staffVO);
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
	public List<StaffVO> getSome(String keyword) {
		List<StaffVO> list = new ArrayList<StaffVO>();
		StaffVO staffVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String idFormat = "^[0-9]+$";

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_SOME_BY_KEYWORD);
			pstmt.setString(1, '%'+ keyword + '%');
			pstmt.setString(2, '%'+ keyword + '%');
			if(!keyword.matches(idFormat)){
				pstmt.setNull(3, java.sql.Types.INTEGER);
			} else {
				pstmt.setInt(3, Integer.parseInt(keyword));
			}
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				staffVO = new StaffVO();
				staffVO.setId(rs.getInt("id"));
				staffVO.setEmail(rs.getString("email"));
				staffVO.setPassword(rs.getString("password"));
				staffVO.setName(rs.getString("name"));
				staffVO.setStatus(rs.getString("status"));	
				list.add(staffVO);
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
	public StaffVO login(String email, String password) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StaffVO staffVO = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(LOGIN);

			pstmt.setString(1, email);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			while(rs.next()){
				staffVO = new StaffVO();
				staffVO.setId(rs.getInt("id"));
				staffVO.setEmail(rs.getString("email"));
				staffVO.setPassword(rs.getString("password"));
				staffVO.setName(rs.getString("name"));
				staffVO.setStatus(rs.getString("status"));
			}

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
		return staffVO;
	}
	
}
