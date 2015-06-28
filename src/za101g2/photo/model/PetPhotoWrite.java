package za101g2.photo.model;
import java.sql.*;
import java.io.*;

class PetPhotoWrite {

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
              String picName[] = {"iconPet29.jpg", "iconPet30.png"};
              int[] picPetId = {29, 30};
              int rowsUpdated = 0;
              try {
                con = DriverManager.getConnection(url, userid, passwd);
                
			for (int i = 0; i < picName.length; i++) {
				File pic = new File("WebContent/images", picName[i]); // �۹���|- picFrom
				// ������|- Ĵ�p:
				// File pic = new File("x:\\aa\\bb\\picFrom", picName);
				long flen = pic.length();
				fin = new FileInputStream(pic);
				pstmt = con
						.prepareStatement("UPDATE PET SET icon = ? WHERE ID = ?");
				pstmt.setBinaryStream(1, fin, (int) flen); // void
															// pstmt.setBinaryStream(int
															// parameterIndex,
															// InputStream x,
															// int length)
															// throws
															// SQLException
				pstmt.setInt(2, picPetId[i]);
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