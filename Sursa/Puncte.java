/*
 * Puncte.java
 *
 * Created on 27 august 2004, 22:21
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

public class Puncte {
    
    /** Creates a new instance of puncte */
    public static Connection conn;
    
    public final static int NOU=1;
    public final static int MODIFICAT=2;
    public final static int STERS=3;
    public final static int SINCRONIZAT=4;
    
    public BigDecimal idech;
    public String sezon;
    public BigDecimal nr_mec_juc;
    public BigDecimal nr_mec_cast;
    public BigDecimal nr_mec_pier;
    public BigDecimal nr_mec_egal;
    public BigDecimal punctaj;
     
    public oracle.sql.ROWID idLinieBD;
    public int stare;
    
    public java.math.BigDecimal getIdech(){return this.idech;}
    public java.lang.String getSezon(){return this.sezon;}
    public java.math.BigDecimal getNr_mec_juc(){return this.nr_mec_juc;}
    public java.math.BigDecimal getNr_mec_cast(){return this.nr_mec_cast;}
    public java.math.BigDecimal getNr_mec_pier(){return this.nr_mec_pier;}
    public java.math.BigDecimal getNr_mec_egal(){return this.nr_mec_egal;}
    public java.math.BigDecimal getPunctaj(){return this.punctaj;}
    public oracle.sql.ROWID getIdLinieBD(){return this.idLinieBD;}
    
    /** metodele set */
    
    public void setIdech(java.math.BigDecimal valNoua){
        if (valNoua==null ? idech!=valNoua : !valNoua.equals(idech))
        { this.idech=valNoua;
          this.setStare(MODIFICAT);
        }
    }

    public void setSezon(java.lang.String valNoua){
        if (valNoua==null ? sezon!=valNoua : !valNoua.equals(sezon)) {
            this.sezon=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
    
    public void setNr_mec_juc(java.math.BigDecimal valNoua){
        if (valNoua==null ? nr_mec_juc!=valNoua : !valNoua.equals(nr_mec_juc)) {
            this.nr_mec_juc=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
    public void setNr_mec_cast(java.math.BigDecimal valNoua){
        if (valNoua==null ? nr_mec_cast!=valNoua : !valNoua.equals(nr_mec_cast)) {
            this.nr_mec_cast=valNoua;
            this.setStare(MODIFICAT);
        }
    }
        
    public void setNr_mec_pier(java.math.BigDecimal valNoua){
        if (valNoua==null ? nr_mec_pier!=valNoua : !valNoua.equals(nr_mec_pier)) {
            this.nr_mec_pier=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
    
    public void setNr_mec_egal(java.math.BigDecimal valNoua){
        if (valNoua==null ? nr_mec_egal!=valNoua : !valNoua.equals(nr_mec_egal)) {
            this.nr_mec_egal=valNoua;
            this.setStare(MODIFICAT);
        }
    } 
    public void setPunctaj(java.math.BigDecimal valNoua){
        if (valNoua==null ? punctaj!=valNoua : !valNoua.equals(punctaj)) {
            this.punctaj=valNoua;
            this.setStare(MODIFICAT);
        }
    }    
    

    public Puncte() {
        this.stare=NOU;
    }
    
    public Puncte(oracle.sql.ROWID pIdlinieBD,BigDecimal pIdech,String pSezon,BigDecimal pNr_mec_juc,
    BigDecimal pNr_mec_cast,BigDecimal pNr_mec_pier,BigDecimal pNr_mec_egal,BigDecimal pPunctaj){
        this.idLinieBD=pIdlinieBD;
        this.idech=pIdech;
        this.sezon=pSezon;
        this.nr_mec_juc=pNr_mec_juc;
        this.nr_mec_cast=pNr_mec_cast;
        this.nr_mec_pier=pNr_mec_pier;
        this.nr_mec_egal=pNr_mec_egal;
        this.punctaj=pPunctaj;
         
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
                stmt1=conn.prepareStatement("insert into puncte" +
                "(idech,sezon,nr_mec_juc,nr_mec_cast,nr_mec_pier,nr_mec_egal,punctaj)) "+
                " values (?,?,?,?,?,?,?)");
                stmt1.setBigDecimal(1,this.idech);stmt1.setString(2,this.sezon);
                stmt1.setBigDecimal(3,this.nr_mec_juc);stmt1.setBigDecimal(3,this.nr_mec_cast);
                stmt1.setBigDecimal(3,this.nr_mec_pier);stmt1.setBigDecimal(3,this.nr_mec_egal);
                stmt1.setBigDecimal(4, this.punctaj);stmt1.execute();
                 
                // trebuie sa obtinem rowid-ul noii inregistrari
                
                stmtRowid1=conn.createStatement();
                oracle.jdbc.OracleResultSet rs=(oracle.jdbc.OracleResultSet)
                stmtRowid1.executeQuery("select rowid from puncte where idech="+
                this.idech.toString());
                rs.next();
                this.idLinieBD=rs.getROWID(1);
                rs.close();
                this.stare=SINCRONIZAT;
            }
            else if (this.stare==MODIFICAT){
                stmt1=conn.prepareStatement("update puncte set "+
                "idech=?,sezon=?,nr_mec_juc=?,punctaj=? where rowid=?");
                
                stmt1.setBigDecimal(1,this.idech);stmt1.setString(2,this.sezon);
                stmt1.setBigDecimal(3,this.nr_mec_juc);stmt1.setBigDecimal(4, this.punctaj);
                               
                // pentru setROWID trebuie downcasting
                
                ((oracle.jdbc.OraclePreparedStatement)stmt1).setROWID(8,this.idLinieBD);
                stmt1.execute();
                if (stmt1.getUpdateCount()==0)
                    throw new Exception("Inregistrarea nu mai exista in baza de date!!!!");
                this.stare=SINCRONIZAT;
            }
            else if (this.stare==STERS){
                stmt1=conn.prepareStatement("delete from puncte where rowid=?");
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
            stmt=conn.prepareStatement("select * from puncte where rowid=?");
            ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(1, this.idLinieBD);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()) {
                this.idech=rs.getBigDecimal("idech");
                this.sezon=rs.getString("sezon");
                this.nr_mec_juc=rs.getBigDecimal("nr_mec_juc");
                this.nr_mec_cast=rs.getBigDecimal("nr_mec_cast");
                this.nr_mec_pier=rs.getBigDecimal("nr_mec_pier");
                this.nr_mec_egal=rs.getBigDecimal("nr_mec_egal");
                this.punctaj=rs.getBigDecimal("punctaj");
           
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
        String frazaSelect="Select rowid,puncte.* from puncte";
        if (whereFiltru!=null)
            frazaSelect +=" "+whereFiltru;
        
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(frazaSelect);
        ArrayList listaObiecte=new ArrayList();
        try{
            Puncte p;
            while (rs.next()) {
                p=new Puncte(
                ((oracle.jdbc.OracleResultSet)rs).getROWID("rowid"),
                rs.getBigDecimal("idech"),
                rs.getString("sezon"),
                rs.getBigDecimal("nr_mec_juc"),
                rs.getBigDecimal("nr_mec_cast"),
                rs.getBigDecimal("nr_mec_pier"),
                rs.getBigDecimal("nr_mec_egal"),
                rs.getBigDecimal("punctaj")
                );
                p.stare=SINCRONIZAT;
                listaObiecte.add(p);
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
        return this.getIdech();
    }
    
    //public void setNmestad(java.math.BigDecimal setNumestad) {
   // }
    
}