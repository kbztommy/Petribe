package za101g2.journalLinkedPet.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JournalLinkedPetDAO implements JournalLinkedPetDAO_interface {

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
			"INSERT INTO JournalLinkedPet (journalId,petId) VALUES (?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT journalId,petId FROM JournalLinkedPet order by petId";
		private static final String GET_ONE_STMT = 
			"SELECT journalId,petId FROM JournalLinkedPet where petId = ? order by journalId desc";
		private static final String DELETE = 
			"DELETE FROM JournalLinkedPet where journalId = ? and petId = ?";
		private static final String DELETEBYJOURNAL = 
			"DELETE FROM JournalLinkedPet where journalId = ?";
		private static final String GET_BY_JOURNALID = 
			"SELECT journalId,petId FROM JournalLinkedPet where journalId = ?";
		
	@Override
	public int insert(JournalLinkedPetVO iVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, iVO.getJournalId());
			pstmt.setInt(2, iVO.getPetId());

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
	public int delete(Integer journalId, Integer petId) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, journalId);
			pstmt.setInt(2, petId);

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
	public int deleteByJournal(Integer journalId) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETEBYJOURNAL);

			pstmt.setInt(1, journalId);

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
	public List<JournalLinkedPetVO> findByPrimaryKey(Integer petId) {
		List<JournalLinkedPetVO> list = new ArrayList<JournalLinkedPetVO>();
		JournalLinkedPetVO qIdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, petId);
	
			rs = pstmt.executeQuery();

			while (rs.next()) {
				qIdVO = new JournalLinkedPetVO();
				qIdVO.setJournalId(rs.getInt("journalId"));
				qIdVO.setPetId(rs.getInt("petId"));
				list.add(qIdVO);
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
	public List<JournalLinkedPetVO> findByJournalId(Integer journalId) {
		List<JournalLinkedPetVO> list = new ArrayList<JournalLinkedPetVO>();
		JournalLinkedPetVO qIdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_JOURNALID);

			pstmt.setInt(1, journalId);
	
			rs = pstmt.executeQuery();

			while (rs.next()) {
				qIdVO = new JournalLinkedPetVO();
				qIdVO.setJournalId(rs.getInt("journalId"));
				qIdVO.setPetId(rs.getInt("petId"));
				list.add(qIdVO);
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
	public List<JournalLinkedPetVO> getAll() {
		List<JournalLinkedPetVO> list = new ArrayList<JournalLinkedPetVO>();
		JournalLinkedPetVO fAllVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				fAllVO = new JournalLinkedPetVO();
				fAllVO.setJournalId(rs.getInt("journalId"));
				fAllVO.setPetId(rs.getInt("petId"));
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
}