package za101g2.photo.model;
import java.sql.*;
import java.io.*;

class PhotoAlbumWrite {

        static {
            try {
                 Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            } catch (Exception e) {
                 e.printStackTrace();
            }
        }

        public static void main(String argv[]) {
              Connection con = null;
              PreparedStatement pstmt = null;
              InputStream fin = null;
              String url = "jdbc:oracle:thin:@localhost:1521:XE";
              String userid = "hr1";
              String passwd = "123456";           
              String picName[] = {"j11.jpg", "j12.jpg", "j13.jpg", "j14.jpg", "j21.jpg", "j22.jpg", "j31.jpg", "j32.jpg", "j33.jpg", "4-1.jpg", "4-2.jpg", "4-3.jpg", "4-4.jpg", "4-5.jpg", "4-6.jpg", "4-7.jpg", "5-1.jpg", "5-2.jpg", "5-3.jpg", "5-4.jpg", "5-5.jpg", "5-6.jpg", "coco001.jpg", "coco002.jpg", "coco003.jpg", "coco004.jpg", "coco005.jpg", "coco006.jpg", "coco007.jpg", "coco008.jpg", "coco009.jpg", "coco010.jpg", "haohao001.jpg", "haohao002.jpg", "haohao003.jpg", "haohao004.jpg", "haohao005.jpg", "haohao006.jpg"};
              
              int[] picMemId = {1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1,1};
              int rowsUpdated = 0;
              try {
                con = DriverManager.getConnection(url, userid, passwd);
                
			for (int i = 0; i < picName.length; i++) {
				File pic = new File("WebContent/images", picName[i]); // �۹���|- picFrom
				// ������|- Ĵ�p:
				// File pic = new File("x:\\aa\\bb\\picFrom", picName);
				long flen = pic.length();
				String fileName = pic.getName();
				int dotPos = fileName.indexOf('.');
				String fName = fileName.substring(0, dotPos);
				String format = fileName.substring(dotPos + 1);
				fin = new FileInputStream(pic);
				pstmt = con
						.prepareStatement("insert into Photo (id, name, format, photoFile, updateDate, memId)  values(Photo_seq.NEXTVAL, ?, ?, ?, SYSDATE, ?)");
				pstmt.setString(1, fName);
				pstmt.setString(2, format);
				pstmt.setBinaryStream(3, fin, (int) flen); // void
															// pstmt.setBinaryStream(int
															// parameterIndex,
															// InputStream x,
															// int length)
															// throws
															// SQLException
				pstmt.setInt(4, picMemId[i]);
				rowsUpdated += pstmt.executeUpdate();

			}
			
                System.out.print("Changed " + rowsUpdated);

                if (1 == rowsUpdated)
                    System.out.println(" row.");
                else
                    System.out.println(" rows.");

                fin.close();
                pstmt.close();

              } catch (Exception e) {
                    e.printStackTrace();
              } finally {
                    try {
                      con.close();
                    } catch (SQLException e) {
                    }
             }
              
      }
}