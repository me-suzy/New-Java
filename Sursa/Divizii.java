/*
 * Divizii.java
 *
 * Created on 27 iunie 2004, 22:21
 */

package Sursa;

/**
 *
 * @author  Bebe
 */
import java.sql.*;
import java.util.ArrayList;
import java.math.BigDecimal;
import utilitati.Utilitati;

public class Divizii {
    
    /** Creates a new instance of divizii */
    public static Connection conn;
    
    public final static int NOU=1;
    public final static int MODIFICAT=2;
    public final static int STERS=3;
    public final static int SINCRONIZAT=4;
    
    public String coddiv;
    public String numediv;
    
    public oracle.sql.ROWID idLinieBD;
    public int stare;
    
    public java.lang.String getCoddiv(){return this.coddiv;}
    public java.lang.String getNumediv(){return this.numediv;}
    
    /** metodele set */
    
    public void setCoddiv(java.lang.String valNoua){
        if (valNoua==null ? coddiv!=valNoua : !valNoua.equals(coddiv))
        { this.coddiv=valNoua;
          this.setStare(MODIFICAT);
        }
    }
    public void setNumediv(java.lang.String valNoua){
        if (valNoua==null ? numediv!=valNoua : !valNoua.equals(numediv)) {
            this.numediv=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    

    public Divizii() {
        this.stare=NOU;
    }
    public Divizii(oracle.sql.ROWID pIdlinieBD,String pCoddiv,String pNumediv){
        this.idLinieBD=pIdlinieBD;
        this.coddiv=pCoddiv;
        this.numediv=pNumediv;
     
    }
    
    public String toString(){
        return this.coddiv;
    }
    public int  getStare(){return this.stare;}
    
    public void setStare(int stareNoua){
        if (this.stare==NOU && stareNoua==MODIFICAT)
            return;
        else
            this.stare=stareNoua;
    }
    public void salveaza(Connection conn) throws Exception{
        if (this.stare==SINCRONIZAT)
            return;
        
        boolean conexiuneNula=false;
        if (conn==null)
        {conn=utilitati.Utilitati.getConexiune();
         conexiuneNula=true;
        }
        PreparedStatement stmt1=null;
        Statement stmtRowid1=null;
        try{
            if (this.stare==NOU){
                stmt1=conn.prepareStatement("insert into divizii" +
                "(coddiv,numediv) "+
                " values (?,?)");
                stmt1.setString(1,this.coddiv);stmt1.setString(2,this.numediv);stmt1.execute();
                
                // trebuie sa obtinem rowid-ul noii inregistrari
                
                stmtRowid1=conn.createStatement();
                oracle.jdbc.OracleResultSet rs=(oracle.jdbc.OracleResultSet)
                stmtRowid1.executeQuery("select rowid from divizii where coddiv="+
                this.coddiv.toString());
                rs.next();
                this.idLinieBD=rs.getROWID(1);
                rs.close();
                this.stare=SINCRONIZAT;
            }
            else if (this.stare==MODIFICAT){
                stmt1=conn.prepareStatement("update divizii set "+
                "coddiv=?,numediv=?");
                
                stmt1.setString(1,this.coddiv);stmt1.setString(2,this.numediv);
                                    
                // pentru setROWID trebuie downcasting
                
                ((oracle.jdbc.OraclePreparedStatement)stmt1).setROWID(8,this.idLinieBD);
                stmt1.execute();
                if (stmt1.getUpdateCount()==0)
                    throw new Exception("Inregistrarea nu mai exista in baza de date!!!!");
                this.stare=SINCRONIZAT;
            }
            else if (this.stare==STERS){
                stmt1=conn.prepareStatement("delete from divizii where rowid=?");
                ((oracle.jdbc.OraclePreparedStatement)stmt1).setROWID(1,this.idLinieBD);
                stmt1.execute();
                this.stare=SINCRONIZAT;
                
            }
            stmt1.close();
            
        }
        catch(Exception e){if (stmt1!=null)
            stmt1.close();
        if (conexiuneNula) // daca nu am primit conexiunea o inchid
            conn.close();
        if (stmtRowid1 !=null)
            stmt1.close();
        throw e;
        }
        if (conexiuneNula) // daca nu am primit conexiunea o inchid
            conn.close();
    }
    public void refresh(Connection conn) throws Exception{
        if(this.idLinieBD==null)
            return;
        boolean conexiuneNula=false;
        if(conn==null)
        {conn=Utilitati.getConexiune();
         conexiuneNula=true;
        }
        PreparedStatement stmt=null;
        try{
            stmt=conn.prepareStatement("select * from divizii where rowid=?");
            ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(1, this.idLinieBD);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()) {
                this.coddiv=rs.getString("coddiv");
                this.numediv=rs.getString("numediv");
              
            }
            
            else //inregistrarea nu mai este in bd
                
                throw new Exception("inregistrarea nu este in bd");
            this.stare=SINCRONIZAT;
            stmt.close();
            if(conexiuneNula) //daca nu am primit o conexiune o inchid
                conn.close();
        }
        catch(Exception e){
            if (stmt!=null)
                stmt.close();
            if (conexiuneNula) //
                conn.close();
            throw e;//trimitte erroarea blocului apelant
        }
    }
    
    public static ArrayList getObjects(Connection conn,String whereFiltru) throws Exception{
        boolean conexiuneNula=false;
        if(conn==null) {
            conn=Utilitati.getConexiune();
            conexiuneNula=true;
        }
        String frazaSelect="Select rowid,divizii.* from divizii";
        if (whereFiltru!=null)
            frazaSelect +=" "+whereFiltru;
        
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(frazaSelect);
        ArrayList listaObiecte=new ArrayList();
        try{
            Divizii d;
            while (rs.next()) {
                d=new Divizii(
                ((oracle.jdbc.OracleResultSet)rs).getROWID("rowid"),
                rs.getString("coddiv"),
                rs.getString("numediv")
                 );
                d.stare=SINCRONIZAT;
                listaObiecte.add(d);
            }
            stmt.close();
        }
        catch(Exception e) {
            if (stmt!=null)
                stmt.close();
            if (conexiuneNula)//daca nu am primt conexiune o inchid
                conn.close();
            throw e;
        }
        if (conexiuneNula) // daca nu am primit conexiune o inhid
            conn.close();
        return listaObiecte;
    }
    public static void initConnection(Connection c) throws Exception{
        
        // if c este null initializam o conexiune
        if (c==null){
            conn=utilitati.Utilitati.getConexiune();
        }
        else
            conn=c;
    }
   // public String toString(){
     //   return this.getCoddiv();
   // }
    
    //public void setCoddiv(java.lang.String setCoddiv) {
   // }
    
}