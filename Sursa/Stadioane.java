/*
 * Stadioane.java
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

public class Stadioane {
    
    /** Creates a new instance of stadioane */
    public static Connection conn;
    
    public final static int NOU=1;
    public final static int MODIFICAT=2;
    public final static int STERS=3;
    public final static int SINCRONIZAT=4;
    
    public BigDecimal codstad;
    public String numestad;
    public BigDecimal capacitate;
    public BigDecimal codpost;
     
    public oracle.sql.ROWID idLinieBD;
    public int stare;
    
    public java.math.BigDecimal getCodStad(){return this.codstad;}
    public java.lang.String getNumestad(){return this.numestad;}
    public java.math.BigDecimal getCapacitate(){return this.capacitate;}
    public java.math.BigDecimal getCodPost(){return this.codpost;}
    public oracle.sql.ROWID getIdLinieBD(){return this.idLinieBD;}
    
    /** metodele set */
    
    public void setCodstad(java.math.BigDecimal valNoua){
        if (valNoua==null ? codstad!=valNoua : !valNoua.equals(codstad))
        { this.codstad=valNoua;
          this.setStare(MODIFICAT);
        }
    }

    public void setNumestad(java.lang.String valNoua){
        if (valNoua==null ? numestad!=valNoua : !valNoua.equals(numestad)) {
            this.numestad=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
    
    public void setCapacitate(java.math.BigDecimal valNoua){
        if (valNoua==null ? capacitate!=valNoua : !valNoua.equals(capacitate)) {
            this.capacitate=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
        
    public void setCodpost(java.math.BigDecimal valNoua){
        if (valNoua==null ? codpost!=valNoua : !valNoua.equals(codpost)) {
            this.codpost=valNoua;
            this.setStare(MODIFICAT);
        }
    }    
    

    public Stadioane() {
        this.stare=NOU;
    }
    
    public Stadioane(oracle.sql.ROWID pIdlinieBD,BigDecimal pCodstad,String pNumestad,
    BigDecimal pCapacitate,BigDecimal pCodpost){
        this.idLinieBD=pIdlinieBD;
        this.codstad=pCodstad;
        this.numestad=pNumestad;
        this.capacitate=pCapacitate;
        this.codpost=pCodpost;
         
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
                stmt1=conn.prepareStatement("insert into stadioane" +
                "(codstad,numestad,capacitate,copost)) "+
                " values (?,?,?,?)");
                stmt1.setBigDecimal(1,this.codstad);stmt1.setString(2,this.numestad);
                stmt1.setBigDecimal(3,this.capacitate);stmt1.setBigDecimal(4, this.codpost);stmt1.execute();
                 
                // trebuie sa obtinem rowid-ul noii inregistrari
                
                stmtRowid1=conn.createStatement();
                oracle.jdbc.OracleResultSet rs=(oracle.jdbc.OracleResultSet)
                stmtRowid1.executeQuery("select rowid from stadioane where codstad="+
                this.codstad.toString());
                rs.next();
                this.idLinieBD=rs.getROWID(1);
                rs.close();
                this.stare=SINCRONIZAT;
            }
            else if (this.stare==MODIFICAT){
                stmt1=conn.prepareStatement("update stadioane set "+
                "codstad=?,numestad=?,capacitate=?,codpost=? where rowid=?");
                
                stmt1.setBigDecimal(1,this.codstad);stmt1.setString(2,this.numestad);
                stmt1.setBigDecimal(3,this.capacitate);stmt1.setBigDecimal(4, this.codpost);
                               
                // pentru setROWID trebuie downcasting
                
                ((oracle.jdbc.OraclePreparedStatement)stmt1).setROWID(8,this.idLinieBD);
                stmt1.execute();
                if (stmt1.getUpdateCount()==0)
                    throw new Exception("Inregistrarea nu mai exista in baza de date!!!!");
                this.stare=SINCRONIZAT;
            }
            else if (this.stare==STERS){
                stmt1=conn.prepareStatement("delete from stadioane where rowid=?");
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
            stmt=conn.prepareStatement("select * from stadioane where rowid=?");
            ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(1, this.idLinieBD);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()) {
                this.codstad=rs.getBigDecimal("codstad");
                this.numestad=rs.getString("numestad");
                this.capacitate=rs.getBigDecimal("capacitate");
                this.codpost=rs.getBigDecimal("codpost");
           
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
        String frazaSelect="Select rowid,stadioane.* from stadioane";
        if (whereFiltru!=null)
            frazaSelect +=" "+whereFiltru;
        
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(frazaSelect);
        ArrayList listaObiecte=new ArrayList();
        try{
            Stadioane stad;
            while (rs.next()) {
                stad=new Stadioane(
                ((oracle.jdbc.OracleResultSet)rs).getROWID("rowid"),
                rs.getBigDecimal("codstad"),
                rs.getString("numestad"),
                rs.getBigDecimal("capacitate"),
                rs.getBigDecimal("codpost")
                );
                stad.stare=SINCRONIZAT;
                listaObiecte.add(stad);
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
        if (conexiuneNula) // daca nu am primit conexiune o inchid
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
    public BigDecimal toBigDecimal(){
        return this.getCodStad();
    }
    
    //public void setNmestad(java.math.BigDecimal setNumestad) {
   // }
    
}