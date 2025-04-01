/*
 * Meciuri.java
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

public class Meciuri {
    
    /** Creates a new instance of jucatori */
    public static Connection conn;
    
    public final static int NOU=1;
    public final static int MODIFICAT=2;
    public final static int STERS=3;
    public final static int SINCRONIZAT=4;
    
    public String idmeci;
    public Timestamp datameci;
    public BigDecimal codstad;
    public BigDecimal etapa;
    public String campionat;
    public BigDecimal nrgolech1;
    public BigDecimal nrgolech2;
    public String orainceput;
    public String orasfarsit;
    public BigDecimal nrfault;
    public BigDecimal cartrosu;
    public BigDecimal cartgalb;
    public BigDecimal nreliminari;
    public BigDecimal nrjucschimb;
    public BigDecimal idech1;
    public BigDecimal idech2;
    
    
    public oracle.sql.ROWID idLinieBD;
    public int stare;
    
    public java.lang.String getIdmeci(){return this.idmeci;}
    public java.sql.Timestamp getDatameci(){return this.datameci;}
    public java.math.BigDecimal getCodstad(){return this.codstad;}
    public java.math.BigDecimal getEtapa(){return this.etapa;}
    public java.lang.String getCampionat(){return this.campionat;}
    public java.math.BigDecimal getNrgolech1(){return this.nrgolech1;}
    public java.math.BigDecimal getNrgolech2(){return this.nrgolech2;}
    public java.lang.String getOrainceput(){return this.orainceput;}
    public java.lang.String getOrasfarsit(){return this.orasfarsit;}
    public java.math.BigDecimal getNrfault(){return this.nrfault;}
    public java.math.BigDecimal getCartrosu(){return this.cartrosu;}
    public java.math.BigDecimal getCartgalb(){return this.cartgalb;}
    public java.math.BigDecimal getNreliminari(){return this.nreliminari;}
    public java.math.BigDecimal getNrjucschimb(){return this.nrjucschimb;}
    public java.math.BigDecimal getIdech1(){return this.idech1;}
    public java.math.BigDecimal getIdech2(){return this.idech2;}
    public oracle.sql.ROWID getIdLinieBD(){return this.idLinieBD;}
    
    /** metodele set */
    
    public void setIdmeci(java.lang.String valNoua){
        if (valNoua==null ? idmeci!=valNoua : !valNoua.equals(idmeci))
        { this.idmeci=valNoua;
          this.setStare(MODIFICAT);
        }
    }
    public void setDatameci(java.sql.Timestamp valNoua){
        if (valNoua==null ? datameci!=valNoua : !valNoua.equals(datameci)) {
            this.datameci=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
    public void setCodstad(java.math.BigDecimal valNoua){
        if (valNoua==null ? codstad!=valNoua : !valNoua.equals(codstad)) {
            this.codstad=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
    public void setEtapa(java.math.BigDecimal valNoua){
        if (valNoua==null ? etapa!=valNoua : !valNoua.equals(etapa)) {
            this.etapa=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    public void setCampionat(java.lang.String valNoua){
        if (valNoua==null ? campionat!=valNoua : !valNoua.equals(campionat)) {
            this.campionat=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    public void setNrgolech1(java.math.BigDecimal valNoua){
        if (valNoua==null ? nrgolech1!=valNoua : !valNoua.equals(nrgolech1)) {
            this.nrgolech1=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
    public void setNrgolech2(java.math.BigDecimal valNoua){
        if (valNoua==null ? nrgolech2!=valNoua : !valNoua.equals(nrgolech2)) {
            this.nrgolech2=valNoua;
            this.setStare(MODIFICAT);
        }
    }
       
    public void setOrainceput(java.lang.String valNoua){
        if (valNoua==null ? orainceput!=valNoua : !valNoua.equals(orainceput)) {
            this.orainceput=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
    public void setOrasfarsit(java.lang.String valNoua){
        if (valNoua==null ? orasfarsit!=valNoua : !valNoua.equals(orasfarsit)) {
            this.orasfarsit=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
    
    public void setNrfault(java.math.BigDecimal valNoua){
        if (valNoua==null ? nrfault!=valNoua : !valNoua.equals(nrfault)) {
            this.nrfault=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
    public void setCartrosu(java.math.BigDecimal valNoua){
        if (valNoua==null ? cartrosu!=valNoua : !valNoua.equals(cartrosu)) {
            this.cartrosu=valNoua;
            this.setStare(MODIFICAT);
        }
    }
        
    public void setCartgalb(java.math.BigDecimal valNoua){
        if (valNoua==null ? cartgalb!=valNoua : !valNoua.equals(cartgalb)) {
            this.cartgalb=valNoua;
            this.setStare(MODIFICAT);
        }
    }    
    
    public void setNreliminari(java.math.BigDecimal valNoua){
        if (valNoua==null ? nreliminari!=valNoua : !valNoua.equals(nreliminari)) {
            this.nreliminari=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
    public void setNrjucschimb(java.math.BigDecimal valNoua){
        if (valNoua==null ? nrjucschimb!=valNoua : !valNoua.equals(nrjucschimb)) {
            this.nrjucschimb=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
    public void setIdech1(java.math.BigDecimal valNoua){
        if (valNoua==null ? idech1!=valNoua : !valNoua.equals(idech1)) {
            this.idech1=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
     public void setIdech2(java.math.BigDecimal valNoua){
        if (valNoua==null ? idech2!=valNoua : !valNoua.equals(idech2)) {
            this.idech2=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    public Meciuri() {
        this.stare=NOU;
    }
    public Meciuri(oracle.sql.ROWID pIdlinieBD,String pIdmeci,Timestamp pDatameci,
    BigDecimal pCodStad,BigDecimal pEtapa,String pCampionat,BigDecimal pNrgolech1,
    BigDecimal pNrgolech2,String pOrainceput,String pOrasfarsit,BigDecimal pNrfault,
    BigDecimal pCartrosu,BigDecimal pCartgalb,BigDecimal pNreliminari,BigDecimal pNrjucschimb,
    BigDecimal pIdech1,BigDecimal pIdech2){
        this.idLinieBD=pIdlinieBD;
        this.idmeci=pIdmeci;
        this.datameci=pDatameci;
        this.codstad=pCodStad;
        this.etapa=pEtapa;
        this.campionat=pCampionat;
        this.nrgolech1=pNrgolech1;
        this.nrgolech2=pNrgolech2;
        this.orainceput=pOrainceput;
        this.orasfarsit=pOrasfarsit;
        this.nrfault=pNrfault;
        this.cartrosu=pCartrosu;
        this.cartgalb=pCartgalb;
        this.nreliminari=pNreliminari;
        this.nrjucschimb=pNrjucschimb;
        this.idech1=pIdech1;
        this.idech2=pIdech2;
        
        
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
                stmt1=conn.prepareStatement("insert into meciuri" +
                "(idmeci,datameci,codstad,etapa,campionat,nrgolech1,nrgolech2,orainceput,orasfarsit," +
                "nrfault,cartrosu,cartgalb,nreliminari,nrjucschimb,idech1,idech2) "+
                " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                stmt1.setString(1,this.idmeci);stmt1.setTimestamp(2,this.datameci);
                stmt1.setBigDecimal(3,this.codstad);stmt1.setBigDecimal(4, this.etapa);
                stmt1.setString(5, this.campionat);stmt1.setBigDecimal(6,this.nrgolech1);
                stmt1.setBigDecimal(7,this.nrgolech2);stmt1.setString(8, this.orainceput);
                stmt1.setString(9,this.orasfarsit);stmt1.setBigDecimal(10,this.nrfault);
                stmt1.setBigDecimal(11,this.cartrosu);stmt1.setBigDecimal(12,this.cartgalb);
                stmt1.setBigDecimal(13,this.nreliminari); stmt1.setBigDecimal(14,this.nrjucschimb);
                stmt1.setBigDecimal(15,this.idech1);stmt1.setBigDecimal(16,this.idech2);stmt1.execute();
                // trebuie sa obtinem rowid-ul noii inregistrari
                stmtRowid1=conn.createStatement();
                oracle.jdbc.OracleResultSet rs=(oracle.jdbc.OracleResultSet)
                stmtRowid1.executeQuery("select rowid from meciuri where idmeci="+
                this.idmeci.toString());
                rs.next();
                this.idLinieBD=rs.getROWID(1);
                rs.close();
                this.stare=SINCRONIZAT;
            }
            else if (this.stare==MODIFICAT){
                stmt1=conn.prepareStatement("update meciuri set "+
                "idmeci=?,datameci=?,codstad=?,etapa=?,campionat=?,nrgolech1=?,nrgolech2=?,orainceput=?,orasfarsit=?" +
                "nrfault=?,cartrosu=?,cartgalb=?,nreliminari=?,nrjucschimb=?,idech1=?,idech2=? where rowid=?");
                
                stmt1.setString(1,this.idmeci);stmt1.setTimestamp(2,this.datameci);
                stmt1.setBigDecimal(3,this.codstad);stmt1.setBigDecimal(4, this.etapa);
                stmt1.setString(5, this.campionat);stmt1.setBigDecimal(6,this.nrgolech1);
                stmt1.setBigDecimal(7,this.nrgolech2);stmt1.setString(8, this.orainceput);
                stmt1.setString(9,this.orasfarsit);stmt1.setBigDecimal(10,this.nrfault);
                stmt1.setBigDecimal(11,this.cartrosu);stmt1.setBigDecimal(12,this.cartgalb);
                stmt1.setBigDecimal(13,this.nreliminari); stmt1.setBigDecimal(14,this.nrjucschimb);
                stmt1.setBigDecimal(15,this.idech1);stmt1.setBigDecimal(16,this.idech2);
                
                // pentru setROWID trebuie downcasting
                ((oracle.jdbc.OraclePreparedStatement)stmt1).setROWID(8,this.idLinieBD);
                stmt1.execute();
                if (stmt1.getUpdateCount()==0)
                    throw new Exception("Inregistrarea nu mai exista in baza de date!!!!");
                this.stare=SINCRONIZAT;
            }
            else if (this.stare==STERS){
                stmt1=conn.prepareStatement("delete from meciuri where rowid=?");
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
            stmt=conn.prepareStatement("select * from meciuri where rowid=?");
            ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(1, this.idLinieBD);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()) {
                this.idmeci=rs.getString("idmeci");
                this.datameci=rs.getTimestamp("datameci");
                this.codstad=rs.getBigDecimal("codstad");
                this.etapa=rs.getBigDecimal("etapa");
                this.campionat=rs.getString("campionat");
                this.nrgolech1=rs.getBigDecimal("nrgolech1");
                this.nrgolech2=rs.getBigDecimal("nrgolech2");
                this.orainceput=rs.getString("orainceput");
                this.orasfarsit=rs.getString("orasfarsit");
                this.nrfault=rs.getBigDecimal("nrfault");
                this.cartrosu=rs.getBigDecimal("cartrosu");
                this.cartgalb=rs.getBigDecimal("cartgalb");
                this.nreliminari=rs.getBigDecimal("nreliminari");
                this.nrjucschimb=rs.getBigDecimal("nrjucschimb");
                this.idech1=rs.getBigDecimal("idech1");
                this.idech2=rs.getBigDecimal("idech2");
                
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
        String frazaSelect="Select rowid,meciuri.* from meciuri";
        if (whereFiltru!=null)
            frazaSelect +=" "+whereFiltru;
        
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(frazaSelect);
        ArrayList listaObiecte=new ArrayList();
        try{
            Meciuri mec;
            while (rs.next()) {
                mec=new Meciuri(
                ((oracle.jdbc.OracleResultSet)rs).getROWID("rowid"),
                rs.getString("idmeci"),
                rs.getTimestamp("datameci"),
                rs.getBigDecimal("codstad"),
                rs.getBigDecimal("etapa"),
                rs.getString("campionat"),
                rs.getBigDecimal("nrgolech1"),
		rs.getBigDecimal("nrgolech2"),
                rs.getString("orainceput"),
                rs.getString("orasfarsit"),
                rs.getBigDecimal("nrfault"),
                rs.getBigDecimal("cartrosu"),
                rs.getBigDecimal("cartgalb"),
                rs.getBigDecimal("nreliminari"),
                rs.getBigDecimal("nrjucschimb"),
                rs.getBigDecimal("idech1"),
                rs.getBigDecimal("idech2")
                );
                mec.stare=SINCRONIZAT;
                listaObiecte.add(mec);
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
    public String toString(){
        return this.getIdmeci();
    }
    
    //public void setNume(java.lang.String setNume) {
   // }
    
}