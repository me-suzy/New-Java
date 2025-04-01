/*
 * PrimulTestJDBC.java
 *
 * Created on August 18, 2004, 12:38 PM
 */

/**
 *
 * @author  Bebe
 */
package Test;

import java.sql.*;
public class PrimulTestJDBC {
    public static void main(String[]args){
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection conn=
                DriverManager.getConnection("jdbc:oracle:thin:@10.10.10.5:1521:oracle","Bebe","domnita");
Statement StmtJucatori=conn.createStatement();
ResultSet CrsJucatori=StmtJucatori.executeQuery("select p.* from jucatori j");

// initializarea unor variabile de mapare
        int jMarca=0;
        String jCodjuc=null;
        String jNume=null;
        String jPrenume=null;
        String jAdresa=null;
        java.math.BigDecimal jIdech=null;
        String jCNP=null;
        String jTelmobil=null;
        String jTelacasa=null;
        String jEmail=null;
        
// parcurgerea ResultSet-ului,preluarea datelor de protected fiecare linie in variabile si afisarea lor:
        while(CrsJucatori.next())
        {jCodjuc= CrsJucatori.getString("Codjuc");
         jNume=   CrsJucatori.getString("Nume");
         jPrenume=CrsJucatori.getString("Prenume");
         jAdresa= CrsJucatori.getString("Adresa");
         jIdech=  CrsJucatori.getBigDecimal("Idech");
         jCNP=    CrsJucatori.getString("CNP");
         jTelmobil= CrsJucatori.getString("Telmobil");
         jTelacasa= CrsJucatori.getString("Telacasa");
         jEmail=   CrsJucatori.getString("Email");
         
         System.out.println(jCodjuc+""+jNume+""+jPrenume+""+jAdresa+""+jIdech+""+jCNP+""+jTelmobil+""+jTelacasa+""+jEmail);
             }
// inchiderea conexiunilor
        CrsJucatori.close();
        StmtJucatori.close();
        conn.close();
    }
    catch (SQLException e) {
        e.printStackTrace();
       }
    }     
        /** Creates a new instance of PrimulTestJDBC */
    public PrimulTestJDBC() {
    }
    
}
