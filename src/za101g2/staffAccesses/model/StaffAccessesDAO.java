package za101g2.staffAccesses.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class StaffAccessesDAO implements StaffAccessesDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String DELETE_STAFF_ACCESSES = 
			"DELETE staffAccesses WHERE staffid=?";
	private static final String INSERT_STAFF_ACCESSES = 
			"INSERT INTO staffAccesses VALUES (?, ?)";
	private static final String GET_ONES_ACCESSES=
			"SELECT * FROM staffaccesses WHERE staffid=?";
	
	@Override
	public void changeAccesses(Integer id,List<Integer> accessesList) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			
			//先執行刪除
			pstmt = con.prepareStatement(DELETE_STAFF_ACCESSES);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
			//再把權限更新
			for(Integer access : accessesList){
				pstmt = con.prepareStatement(INSERT_STAFF_ACCESSES);
				pstmt.setInt(1, id);
				pstmt.setInt(2, access);
				pstmt.executeUpdate();
			}
			
			con.commit();
			con.setAutoCommit(true);
			// Handle any SQL errors
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			se.printStackTrace();
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
	public List<Integer> findAccessesById(Integer id) {
		
		List<Integer> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONES_ACCESSES);

			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt("accessesId"));
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
