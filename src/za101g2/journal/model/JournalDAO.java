package za101g2.journal.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JournalDAO implements JournalDAO_interface {

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
		"INSERT INTO Journal (id,title,article,releaseDate,ediltDate,memId,status,isPublic) VALUES (journal_seq.NEXTVAL, ?, ?, SYSDATE, SYSDATE, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT id,title,article,to_char(releaseDate,'yyyy-mm-dd') releaseDate,to_char(ediltDate,'yyyy-mm-dd') ediltDate,memId,status,isPublic FROM Journal WHERE status = '0' order by releaseDate asc";
	private static final String GET_ONE_STMT = 
		"SELECT id,title,article,to_char(releaseDate,'yyyy-mm-dd') releaseDate,to_char(ediltDate,'yyyy-mm-dd') ediltDate,memId,status,isPublic FROM Journal where id = ?";
	private static final String DELETE = 
		"DELETE FROM Journal where id = ?";
	private static final String UPDATE = 
		"UPDATE Journal set title=?, article=?, memId=?, status=?, isPublic=? where id = ?";
	private static final String GET_BY_FK = "SELECT * FROM Journal WHERE memId = ? and status = '0' order by releaseDate desc";
	private static final String UPDATESTATUS = 
		"UPDATE Journal set status=? where id = ?";
	//0613柏儒新增
	private static final String GET_MORE_STMT = "select * from (select * from journal where id<? and status ='0' order by id desc) where rownum <5 ";
	private static final String GET_NEWEST_STMT ="select * from (select * from journal where status='0' order by id desc)  where rownum<5 ";
	@Override
	public int insert(JournalVO iVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, iVO.getTitle());
			pstmt.setString(2, iVO.getArticle());
			pstmt.setInt(3, iVO.getMemId());
			pstmt.setString(4, iVO.getStatus());
			pstmt.setString(5, iVO.getIsPublic());

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
	public String insertReKey(JournalVO iVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String key = null;

		try {
			
			con = ds.getConnection();
			String cols[] = {"ID"};
			pstmt = con.prepareStatement(INSERT_STMT,cols);

			pstmt.setString(1, iVO.getTitle());
			pstmt.setString(2, iVO.getArticle());
			pstmt.setInt(3, iVO.getMemId());
			pstmt.setString(4, iVO.getStatus());
			pstmt.setString(5, iVO.getIsPublic());

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
		return key;
	}
	
	@Override
	public int update(JournalVO uVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, uVO.getTitle());
			pstmt.setString(2, uVO.getArticle());
			pstmt.setInt(3, uVO.getMemId());
			pstmt.setString(4, uVO.getStatus());
			pstmt.setString(5, uVO.getIsPublic());
			pstmt.setInt(6, uVO.getId());

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
	public JournalVO findByPrimaryKey(Integer id) {

		JournalVO qIdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				qIdVO = new JournalVO();
				qIdVO.setId(rs.getInt("id"));
				qIdVO.setTitle(rs.getString("title"));
				qIdVO.setArticle(rs.getString("article"));
				qIdVO.setReleaseDate(rs.getDate("releaseDate"));
				qIdVO.setEdiltDate(rs.getDate("ediltDate"));
				qIdVO.setMemId(rs.getInt("memId"));
				qIdVO.setStatus(rs.getString("status"));
				qIdVO.setIsPublic(rs.getString("isPublic"));
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
	public List<JournalVO> getAll() {
		List<JournalVO> list = new ArrayList<JournalVO>();
		JournalVO fAllVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				fAllVO = new JournalVO();
				fAllVO.setId(rs.getInt("id"));
				fAllVO.setTitle(rs.getString("title"));
				fAllVO.setArticle(rs.getString("article"));
				fAllVO.setReleaseDate(rs.getDate("releaseDate"));
				fAllVO.setEdiltDate(rs.getDate("ediltDate"));
				fAllVO.setMemId(rs.getInt("memId"));
				fAllVO.setStatus(rs.getString("status"));
				fAllVO.setIsPublic(rs.getString("isPublic"));
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
	public List<JournalVO> findByForeginKey(Integer memId) {
		List<JournalVO> list = new ArrayList<JournalVO>();
		JournalVO fByFKVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_FK);
			pstmt.setInt(1, memId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				fByFKVO = new JournalVO();
				fByFKVO.setId(rs.getInt("id"));
				fByFKVO.setTitle(rs.getString("title"));
				fByFKVO.setArticle(rs.getString("article"));
				fByFKVO.setReleaseDate(rs.getDate("releaseDate"));
				fByFKVO.setEdiltDate(rs.getDate("ediltDate"));
				fByFKVO.setMemId(rs.getInt("memId"));
				fByFKVO.setStatus(rs.getString("status"));
				fByFKVO.setIsPublic(rs.getString("isPublic"));
				list.add(fByFKVO);
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
	public int updateStatus(JournalVO uVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATESTATUS);

			pstmt.setString(1, uVO.getStatus());
			pstmt.setInt(2, uVO.getId());
			
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
	public List<JournalVO> getMore(Integer id) {
		List<JournalVO> list = new ArrayList<JournalVO>();
		JournalVO fAllVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MORE_STMT);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				fAllVO = new JournalVO();
				fAllVO.setId(rs.getInt("id"));
				fAllVO.setTitle(rs.getString("title"));
				fAllVO.setArticle(rs.getString("article"));
				fAllVO.setReleaseDate(rs.getDate("releaseDate"));
				fAllVO.setEdiltDate(rs.getDate("ediltDate"));
				fAllVO.setMemId(rs.getInt("memId"));
				fAllVO.setStatus(rs.getString("status"));
				fAllVO.setIsPublic(rs.getString("isPublic"));
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
	public List<JournalVO> getNewest() {
		List<JournalVO> list = new ArrayList<JournalVO>();
		JournalVO fAllVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_NEWEST_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				fAllVO = new JournalVO();
				fAllVO.setId(rs.getInt("id"));
				fAllVO.setTitle(rs.getString("title"));
				fAllVO.setArticle(rs.getString("article"));
				fAllVO.setReleaseDate(rs.getDate("releaseDate"));
				fAllVO.setEdiltDate(rs.getDate("ediltDate"));
				fAllVO.setMemId(rs.getInt("memId"));
				fAllVO.setStatus(rs.getString("status"));
				fAllVO.setIsPublic(rs.getString("isPublic"));
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