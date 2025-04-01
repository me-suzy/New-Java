/*
 * TestJDBCparametrizat.java
 *
 * Created on August 26, 2004, 10:30 AM
 */

package test;
import java.sql.*;
/**
 *
 * @author  Bebe
 */
public class TestJDBCparametrizat {
    
    /** Creates a new instance of TestJDBCparametrizat */
    public TestJDBCparametrizat() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException{
        // TODO code application logic here
        Connection conn=utilitati.Utilitati.getConexiune();
       conn.setAutoCommit(false);
       PreparedStatement pstm1= conn.prepareStatement("UPDATE JUCATORI SET NUME=? WHERE CODJUC=?");
       Statement stm=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       ResultSet cursor=stm.executeQuery("SELECT * FROM JUCATORI");
 while(cursor.next())
        System.out.println("CODJUC "+cursor.getString("CODJUC")+"NUME "+cursor.getString("nume"));
pstm1.setString(1,"JUCATOR Popescu MODF3");
pstm1.setInt(2,10002);
pstm1.execute();
cursor=stm.executeQuery("select * from jucatori");
 while(cursor.next())
        System.out.println("CODJUC "+" "+cursor.getString("c" +
        "odjuc")+" "+"NUME "+cursor.getString("nume"));
conn.rollback();
 while(cursor.next())
         System.out.println("CODJUC "+" "+cursor.getString("c" +
         "odjuc")+" "+"NUME "+cursor.getString("nume"));
conn.close();

    }

}
