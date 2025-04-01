/*
 * Contract.java
 *
 * Created on August 19, 2004, 5:27 PM
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

public class Contract {
    
    /** Creates a new instance of contract */
    public static Connection conn;
    
    public final static int NOU=1;
    public final static int MODIFICAT=2;
    public final static int STERS=3;
    public final static int SINCRONIZAT=4;
    
    public BigDecimal codc;
    public String codjuc;
    public Timestamp datainc_c;
    public Timestamp datarez_c;
    public BigDecimal sumac;
    public String clauzec;
    
    public oracle.sql.ROWID idLinieBD;
    public int stare;
    
    public java.math.BigDecimal getCodc(){return this.codc;}
    public java.lang.String getCodjuc(){return this.codjuc;}
    public java.sql.Timestamp getDatainc_c(){return this.datainc_c;}
    public java.sql.Timestamp getDatarez_c(){return this.datarez_c;}
    public java.math.BigDecimal getSumac(){return this.sumac;}
    public java.lang.String getClauzec(){return this.clauzec;}
    
    /** metodele set */
    
    public void setCodc(java.math.BigDecimal valNoua){
        if (valNoua==null ? codc!=valNoua : !valNoua.equals(codc))
        { this.codc=valNoua;
          this.setStare(MODIFICAT);
        }
    }
    public void setCodjuc(java.lang.String valNoua){
        if (valNoua==null ? codjuc!=valNoua : !valNoua.equals(codjuc)) {
            this.codjuc=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
    public void setDatainc_c(java.sql.Timestamp valNoua){
        if (valNoua==null ? datainc_c!=valNoua : !valNoua.equals(datainc_c)) {
            this.datainc_c=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
       public void setDatarez_c(java.sql.Timestamp valNoua){
        if (valNoua==null ? datarez_c!=valNoua : !valNoua.equals(datarez_c)) {
            this.datarez_c=valNoua;
            this.setStare(MODIFICAT);
        }
    }
       
    public void setSumac(java.math.BigDecimal valNoua){
        if (valNoua==null ? sumac!=valNoua : !valNoua.equals(sumac)) {
            this.sumac=valNoua;
            this.setStare(MODIFICAT);
        }
    }
   
       
    public void setClauzec(java.lang.String valNoua){
        if (valNoua==null ? clauzec!=valNoua : !valNoua.equals(clauzec)) {
            this.clauzec=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    

    public Contract() {
        this.stare=NOU;
    }
    public Contract(oracle.sql.ROWID pIdlinieBD,BigDecimal pCodc,String pCodjuc,
    Timestamp pDatainc_c,Timestamp pDatarez_c,BigDecimal pSumac,String pClauzec){
        this.idLinieBD=pIdlinieBD;
        this.codc=pCodc;
        this.codjuc=pCodjuc;
        this.datainc_c=pDatainc_c;
        this.datarez_c=pDatarez_c;
        this.sumac=pSumac;
        this.clauzec=pClauzec;
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
                stmt1=conn.prepareStatement("insert into contract" +
                "(codc,codjuc,datainc_c,datarez_c,sumac,clauzec) "+
                " values (?,?,?,?,?,?)");
                stmt1.setBigDecimal(1,this.codc);stmt1.setString(2,this.codjuc);
                stmt1.setTimestamp(3,this.datainc_c);stmt1.setTimestamp(4, this.datarez_c);
                stmt1.setBigDecimal(5, this.sumac);stmt1.setString(6,this.clauzec);stmt1.execute();
                
                // trebuie sa obtinem rowid-ul noii inregistrari
                
                stmtRowid1=conn.createStatement();
                oracle.jdbc.OracleResultSet rs=(oracle.jdbc.OracleResultSet)
                stmtRowid1.executeQuery("select rowid from contract where codc="+
                this.codc.toString());
                rs.next();
                this.idLinieBD=rs.getROWID(1);
                rs.close();
                this.stare=SINCRONIZAT;
            }
            else if (this.stare==MODIFICAT){
                stmt1=conn.prepareStatement("update contract set "+
                "codc=?,codjuc=?,datainc_c=?,datarez_c=?,sumac=?,clauzec=?");
                
                stmt1.setBigDecimal(1,this.codc);stmt1.setString(2,this.codjuc);
                stmt1.setTimestamp(3,this.datainc_c);stmt1.setTimestamp(4, this.datarez_c);
                stmt1.setBigDecimal(5, this.sumac);stmt1.setString(6,this.clauzec);
                        
                // pentru setROWID trebuie downcasting
                
                ((oracle.jdbc.OraclePreparedStatement)stmt1).setROWID(8,this.idLinieBD);
                stmt1.execute();
                if (stmt1.getUpdateCount()==0)
                    throw new Exception("Inregistrarea nu mai exista in baza de date!!!!");
                this.stare=SINCRONIZAT;
            }
            else if (this.stare==STERS){
                stmt1=conn.prepareStatement("delete from contract where rowid=?");
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
            stmt=conn.prepareStatement("select * from contract where rowid=?");
            ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(1, this.idLinieBD);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()) {
                this.codc=rs.getBigDecimal("codc");
                this.codjuc=rs.getString("codjuc");
                this.datainc_c=rs.getTimestamp("datainc_c");
                this.datarez_c=rs.getTimestamp("datarez_c");
                this.sumac=rs.getBigDecimal("sumac");
                this.clauzec=rs.getString("clauzec");
  
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
        String frazaSelect="Select rowid,contract.* from contract";
        if (whereFiltru!=null)
            frazaSelect +=" "+whereFiltru;
        
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(frazaSelect);
        ArrayList listaObiecte=new ArrayList();
        try{
            Contract c;
            while (rs.next()) {
                c=new Contract(
                ((oracle.jdbc.OracleResultSet)rs).getROWID("rowid"),
                rs.getBigDecimal("codc"),
                rs.getString("codjuc"),
                rs.getTimestamp("datainc_c"),
                rs.getTimestamp("datarez_c"),
                rs.getBigDecimal("sumac"),
                rs.getString("clauzec")
                 );
                c.stare=SINCRONIZAT;
                listaObiecte.add(c);
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
    public BigDecimal toBigDecimal(){
        return this.getCodc();
    }
    
    //public void setNume(java.lang.String setNume) {
   // }
    
}