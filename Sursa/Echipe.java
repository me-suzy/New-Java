/*
 * Echipe.java
 *
 * Created on August 19, 2004, 5:26 PM
 */

package Sursa;

/**
 *
 * @author  Bebe
 */
import java.sql.*;
import java.util.ArrayList;
import java.lang.String;
import utilitati.Utilitati;
import java.math.BigDecimal;
/**
 *
 * @author  Bebe
 */
public class Echipe{
    
    /** Creates a new instance of reparatii */
 
    public final static int NOU=1;
    public final static int MODIFICAT=2;
    public final static int STERS=3;
    public final static int SINCRONIZAT=4;
    
    public BigDecimal idech;
    public String numeech;
    public String culori;
    public String telefon;
    public BigDecimal nrjuc;
    public BigDecimal codpost;
    public String coddiv;
   
    protected oracle.sql.ROWID idLinieBD;
    protected int stare;
    
    public java.math.BigDecimal getIdech(){return this.idech;}
    public java.lang.String getNumeech(){return this.numeech;}
    public java.lang.String getCulori(){return this.culori;}
    public java.lang.String getTelefon(){return this.telefon;}
    public java.math.BigDecimal getNrjuc(){return this.nrjuc;}
    public java.math.BigDecimal getCodpost(){return this.codpost;}
    public java.lang.String getCoddiv(){return this.coddiv;}
    
    
    public oracle.sql.ROWID getIdLinieBD(){return this.idLinieBD;}
    
    /** metodele set */
    
    public void setIdech(java.math.BigDecimal valNoua){
       if (valNoua==null ? idech!=valNoua : !valNoua.equals(idech))
       { this.idech=valNoua;
         this.setStare(MODIFICAT);
       }
    }
    public void setNumeech(java.lang.String valNoua){
        if (valNoua==null ? numeech!=valNoua : !valNoua.equals(numeech))
        {
        this.numeech=valNoua;
        this.setStare(MODIFICAT);
        }
    }
    public void setCulori(java.lang.String valNoua){
        if (valNoua==null ? culori!=valNoua : !valNoua.equals(culori))
        {
            this.culori=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    public void setTelefon(java.lang.String valNoua){
        if (valNoua==null ? telefon!=valNoua : !valNoua.equals(telefon))
        {
            this.telefon=valNoua;
            this.setStare(MODIFICAT);
        } 
    }
    public void setNrjuc(java.math.BigDecimal valNoua){
        if (valNoua==null ? nrjuc!=valNoua : !valNoua.equals(nrjuc))
        {
            this.nrjuc=valNoua;
            this.setStare(MODIFICAT);
        } 
    }
    public void setCodpost(java.math.BigDecimal valNoua){
        if (valNoua==null ? codpost!=valNoua : !valNoua.equals(codpost))
        {
            this.codpost=valNoua;
            this.setStare(MODIFICAT);
        } 
    }
    public void setCoddiv(java.lang.String valNoua){
        if (valNoua==null ? coddiv!=valNoua : !valNoua.equals(coddiv))
        {
            this.coddiv=valNoua;
            this.setStare(MODIFICAT);
        } 
    }
 public Echipe() {
        this.stare=NOU;
    }
    public Echipe(oracle.sql.ROWID pIdlinieBD, BigDecimal pIdech, String pNumeech,
                    String pCulori,String pTelefon,BigDecimal pNrjuc,BigDecimal pCodpost,String pCoddiv)
    {
        this.idLinieBD=pIdlinieBD;
        this.idech=pIdech;
        this.numeech=pNumeech;
        this.culori=pCulori;
        this.telefon=pTelefon;
        this.nrjuc=pNrjuc;
        this.codpost=pCodpost;
        this.coddiv=pCoddiv;

    }
    
    public String toString(){
        return this.numeech;
    }
    
    public int  getStare(){return this.stare;}
    
    public void setStare(int stareNoua){
       if (this.stare==NOU && stareNoua==MODIFICAT)
           return;
       else
           this.stare=stareNoua;
    }
    public void salveaza(Connection conn) throws Exception{
        if(this.stare==SINCRONIZAT)
            return;
        boolean conexiuneNula=false;
        if(conn==null)
        {
         conn=Utilitati.getConexiune();
         conexiuneNula=true;
        }
        PreparedStatement stmt=null;
        Statement stmtRowid=null;
    try{
      if(this.stare==NOU){
          stmt=conn.prepareStatement("insert into echipe"+"(idech,numeech,culori,telefon," +
          "nrjuc,codpost,coddiv)"+
          "values(?,?,?,?,?,?,?)");
       stmt.setBigDecimal(1, this.idech); stmt.setString(2, this.numeech);
       stmt.setString(3,this.culori); stmt.setString(4,this.telefon);
       stmt.setBigDecimal(5,this.nrjuc); stmt.setBigDecimal(6,this.codpost);
       stmt.setString(7,this.coddiv); 
       stmt.execute();
       //trebuie sa obtinem Rowidul noii inregistrari
       stmtRowid=conn.createStatement();
       oracle.jdbc.OracleResultSet rs=(oracle.jdbc.OracleResultSet)
       stmtRowid.executeQuery("select rowid from echipe where idech="+this.idech);//.toString());
       rs.next();
       this.idLinieBD=rs.getROWID(1);
       rs.close();
       this.stare=SINCRONIZAT;
       }
      else if (this.stare==MODIFICAT){
          stmt=conn.prepareStatement("update echipe set "+"idech=?,numeech=?,culori=?,telefon=?," +
          "nrjuc=?,codpost=?,coddiv=?");
          stmt.setBigDecimal(1, this.idech); stmt.setString(2, this.numeech);
          stmt.setString(3,this.culori); stmt.setString(4,this.telefon);
          stmt.setBigDecimal(5,this.nrjuc); stmt.setBigDecimal(6,this.codpost);
          stmt.setString(7,this.coddiv); 
          //pentru setRowid trebuie downcasting 
          ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(8,this.idLinieBD);
          stmt.execute();
          if(stmt.getUpdateCount()==0) throw new Exception ("Inregistrarea nu mai exista in baza de date!!!!!");
          this.stare=SINCRONIZAT;
      }
      else if (this.stare==STERS) {
       stmt=conn.prepareStatement("delete from echipe where rowid=?");
       ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(1,this.idLinieBD);
       stmt.execute();
       this.stare=SINCRONIZAT;
      }
      stmt.close();
      }
    catch(Exception e){if (stmt!=null)
        //e.printStackTrace();
                        stmt.close();
                       if (conexiuneNula) // daca nu am primit conexiune o inchid}
                           conn.close();
                         if(stmtRowid !=null) 
                             stmt.close();
                       throw e;
    }                 
    if (conexiuneNula) //daca nu am primit conexiune o inchid
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
           stmt=conn.prepareStatement("select * from echipe where rowid=?");
           ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(1, this.idLinieBD);
           ResultSet rs=stmt.executeQuery();
           if (rs.next())
           {
               this.idech=rs.getBigDecimal("idech");
               this.numeech=rs.getString("numeech");
               this.culori=rs.getString("culori");
               this.telefon=rs.getString("telefon");
               this.nrjuc=rs.getBigDecimal("nrjuc");
               this.codpost=rs.getBigDecimal("codpost");
               this.coddiv=rs.getString("coddiv");
             
           }
           else //inregistrarea nu mai este in bd
               throw new Exception ("inregistrarea nu este in bd");
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
       if(conn==null) 
       {
           conn=Utilitati.getConexiune();
           conexiuneNula=true;
       }
        String frazaSelect="Select rowid,echipe.* from echipe";
        if (whereFiltru!=null)
            frazaSelect +=" "+whereFiltru;
        
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(frazaSelect);
        ArrayList listaObiecte=new ArrayList();
      try{
          Echipe ec;
          while (rs.next())
          {
              ec=new Echipe(
              ((oracle.jdbc.OracleResultSet)rs).getROWID("rowid"),
              rs.getBigDecimal("idech"),
              rs.getString("numeech"),
              rs.getString("culori"), 
              rs.getString("telefon"),
              rs.getBigDecimal("nrjuc"),
              rs.getBigDecimal("codpost"),
              rs.getString("coddiv")
              );              
              ec.stare=SINCRONIZAT;
              listaObiecte.add(ec);
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
public BigDecimal toBigDecimal(){
    return this.getIdech();
}
}
