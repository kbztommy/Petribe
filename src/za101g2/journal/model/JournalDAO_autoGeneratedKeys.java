package za101g2.journal.model;
import java.util.*;
import java.sql.*;

public class JournalDAO_autoGeneratedKeys implements JournalDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "hr1";
	String passwd = "tiger";

	private static final String INSERT_STMT = 
		"INSERT INTO Journal (id,title,article,releaseDate,ediltDate,memId,status,isPublic) VALUES (journal_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT id,title,article,to_char(releaseDate,'yyyy-mm-dd') releaseDate,to_char(ediltDate,'yyyy-mm-dd') ediltDate,memId,status,isPublic FROM Journal order by id";
	private static final String GET_ONE_STMT = 
		"SELECT id,title,article,to_char(releaseDate,'yyyy-mm-dd') releaseDate,to_char(ediltDate,'yyyy-mm-dd') ediltDate,memId,status,isPublic FROM Journal where id = ?";
	private static final String DELETE = 
		"DELETE FROM Journal where id = ?";
	private static final String UPDATE = 
		"UPDATE Journal set title=?, article=?, releaseDate=?, ediltDate=?, memId=?, status=?, isPublic=? where id = ?";
	private static final String GET_BY_FK = "SELECT * FROM Journal WHERE memId = ?";
	
	@Override
	public int insert(JournalVO iVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);			
			String cols[] = {"ID"};
			pstmt = con.prepareStatement(INSERT_STMT,cols);
			
			pstmt.setString(1, iVO.getTitle());
			pstmt.setString(2, iVO.getArticle());
			pstmt.setDate(3, iVO.getReleaseDate());
			pstmt.setDate(4, iVO.getEdiltDate());
			pstmt.setInt(5, iVO.getMemId());
			pstmt.setString(6, iVO.getStatus());
			pstmt.setString(7, iVO.getIsPublic());

			updateCount = pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rs.next()) {
				do {
					for (int i = 1; i <= columnCount; i++) {
						String key = rs.getString(i);
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
		return updateCount;
	}

	@Override
	public int update(JournalVO uVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, uVO.getTitle());
			pstmt.setString(2, uVO.getArticle());
			pstmt.setDate(3, uVO.getReleaseDate());
			pstmt.setDate(4, uVO.getEdiltDate());
			pstmt.setInt(5, uVO.getMemId());
			pstmt.setString(6, uVO.getStatus());
			pstmt.setString(7, uVO.getIsPublic());
			pstmt.setInt(8, uVO.getId());

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
	public JournalVO findByPrimaryKey(Integer id) {

		JournalVO qIdVO = null;
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
	public List<JournalVO> getAll() {
		List<JournalVO> list = new ArrayList<JournalVO>();
		JournalVO fAllVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
	public List<JournalVO> findByForeginKey(Integer memId) {
		List<JournalVO> list = new ArrayList<JournalVO>();
		JournalVO fByFKVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

		JournalDAO_autoGeneratedKeys dao = new JournalDAO_autoGeneratedKeys();

		 //�s�W
		 JournalVO insertVO = new JournalVO();
		 insertVO.setTitle("我是新文章");
		 insertVO.setArticle("Hi everybody");
		 insertVO.setReleaseDate(java.sql.Date.valueOf("2015-05-03"));
		 insertVO.setEdiltDate(java.sql.Date.valueOf("2015-05-03"));
		 insertVO.setMemId(new Integer(1));
		 insertVO.setStatus("正常");
		 insertVO.setIsPublic("y");
		 int updateCount_insert = dao.insert(insertVO);
		 System.out.println(updateCount_insert);
		
		 //�ק�
//		 JournalVO updateVO = new JournalVO();
//		 updateVO.setId(4);
//		 updateVO.setTitle("�ڤ��O��I��");
//		 updateVO.setArticle("Your mother's gone motherFucker");
//		 updateVO.setReleaseDate(java.sql.Date.valueOf("2014-04-01"));
//		 updateVO.setEdiltDate(java.sql.Date.valueOf("2014-04-01"));
//		 updateVO.setMemId(new Integer(1));
//		 updateVO.setStatus("�Q���|");
//		 updateVO.setIsPublic("y");
//		 int updateCount_update = dao.update(updateVO);
//		 System.out.println(updateCount_update);
				
		 //�R��
//		 int updateCount_delete = dao.delete(4);
//		 System.out.println(updateCount_delete);

		// �d��
		JournalVO qIdVO = dao.findByPrimaryKey(1);
		System.out.print(qIdVO.getId() + ",");
		System.out.print(qIdVO.getTitle() + ",");
		System.out.print(qIdVO.getArticle() + ",");
		System.out.print(qIdVO.getReleaseDate() + ",");
		System.out.print(qIdVO.getEdiltDate() + ",");
		System.out.print(qIdVO.getMemId() + ",");
		System.out.print(qIdVO.getStatus() + ",");
		System.out.println(qIdVO.getIsPublic());
		System.out.println("---------------------");

		// �d��
		List<JournalVO> list = dao.getAll();
		for (JournalVO oneVo : list) {
			System.out.print(oneVo.getId() + ",");
			System.out.print(oneVo.getTitle() + ",");
			System.out.print(oneVo.getArticle() + ",");
			System.out.print(oneVo.getReleaseDate() + ",");
			System.out.print(oneVo.getEdiltDate() + ",");
			System.out.print(oneVo.getMemId() + ",");
			System.out.print(oneVo.getStatus() + ",");
			System.out.print(oneVo.getIsPublic());
			System.out.println();
		}
	}

	@Override
	public String insertReKey(JournalVO VO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateStatus(JournalVO VO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<JournalVO> getMore(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JournalVO> getNewest() {
		// TODO Auto-generated method stub
		return null;
	}
}