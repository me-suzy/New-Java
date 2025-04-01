/*
 * TestPersistenta.java
 *
 * Created on August 3, 2004, 10:20 AM
 */

package test;
import utilitati.Utilitati;
import java.sql.*;
import java.util.*;
/**
 *
 * @author  Bebe
 */
public class TestPersistenta {
    
    
    /** Creates a new instance of TestPersistenta */
    public TestPersistenta() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws Exception{
        // TODO code application logic here
       Connection conn=Utilitati.getConexiune();
       //instantierea unui jucator
       conn.setAutoCommit(false);
       Sursa.Jucatori jucator= new Sursa.Jucatori();
       jucator.setCodjuc(new java.lang.String("55555"));
       jucator.setNume("JUCATOR");
       jucator.setPrenume("JUCATOR");
       jucator.setAdresa("Sfhgfdhgfd");
       jucator.setIdech(new java.math.BigDecimal("55555"));
       jucator.setCNP("12414143");
       jucator.setTelmobil("0742921212");
       jucator.setTelacasa("0234292212");
       jucator.setEmail("hj@hgf.com");
       jucator.salveaza(conn);
       List jucatori=Sursa.Jucatori.getObjects(conn,null);
       Iterator itr=jucatori.iterator();
       while (itr.hasNext())
           System.out.println(itr.next());
       conn.close();
       
    }
    
    
    
}
