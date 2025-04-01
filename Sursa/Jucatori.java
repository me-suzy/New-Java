/*
 * Jucatori.java
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

public class Jucatori {
    
    /** Creates a new instance of jucatori */
    public static Connection conn;
    
    public final static int NOU=1;
    public final static int MODIFICAT=2;
    public final static int STERS=3;
    public final static int SINCRONIZAT=4;
    
    public String codjuc;
    public String nume;
    public String prenume;
    public String adresa;
    public BigDecimal idech;
    public String telmobil;
    public String telacasa;
    public String email;
    public String CNP;
    
    
    public oracle.sql.ROWID idLinieBD;
    protected int stare;
    
    public java.lang.String getCodjuc(){return this.codjuc;}
    public java.lang.String getNume(){return this.nume;}
    public java.lang.String getPrenume(){return this.prenume;}
    public java.lang.String getAdresa(){return this.adresa;}
    public java.math.BigDecimal getIdech(){return this.idech;}
    public java.lang.String getTelMobil(){return this.telmobil;}
    public java.lang.String getTelAcasa(){return this.telacasa;}
    public java.lang.String getEmail(){return this.email;}
    public java.lang.String getCNP(){return this.CNP;}
    public oracle.sql.ROWID getIdLinieBD(){return this.idLinieBD;}
    
    /** metodele set */
    
    public void setCodjuc(java.lang.String valNoua){
        if (valNoua==null ? codjuc!=valNoua : !valNoua.equals(codjuc))
        { this.codjuc=valNoua;
          this.setStare(MODIFICAT);
        }
    }
    public void setNume(java.lang.String valNoua){
        if (valNoua==null ? nume!=valNoua : !valNoua.equals(nume)) {
            this.nume=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
    public void setPrenume(java.lang.String valNoua){
        if (valNoua==null ? prenume!=valNoua : !valNoua.equals(prenume)) {
            this.prenume=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
    public void setAdresa(java.lang.String valNoua){
        if (valNoua==null ? adresa!=valNoua : !valNoua.equals(adresa)) {
            this.adresa=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    public void setIdech(java.math.BigDecimal valNoua){
        if (valNoua==null ? idech!=valNoua : !valNoua.equals(idech)) {
            this.idech=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    public void setTelmobil(java.lang.String valNoua){
        if (valNoua==null ? telmobil!=valNoua : !valNoua.equals(telmobil)) {
            this.telmobil=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
       public void setTelacasa(java.lang.String valNoua){
        if (valNoua==null ? telmobil!=valNoua : !valNoua.equals(telacasa)) {
            this.telacasa=valNoua;
            this.setStare(MODIFICAT);
        }
    }
       
    public void setEmail(java.lang.String valNoua){
        if (valNoua==null ? email!=valNoua : !valNoua.equals(email)) {
            this.email=valNoua;
            this.setStare(MODIFICAT);
        }
    }
    
       public void setCNP(java.lang.String valNoua){
        if (valNoua==null ? CNP!=valNoua : !valNoua.equals(CNP)) {
            this.CNP=valNoua;
            this.setStare(MODIFICAT);
        }
    }
       
    
    public Jucatori() {
        this.stare=NOU;
    }
    public Jucatori(oracle.sql.ROWID pIdlinieBD,String pCodjuc,String pNume,
    String pPrenume,String pAdresa,BigDecimal pidech,String pTelmobil,
    String pTelacasa,String pEmail,String pCNP){
        this.idLinieBD=pIdlinieBD;
        this.codjuc=pCodjuc;
        this.nume=pNume;
        this.prenume=pPrenume;
        this.adresa=pAdresa;
        this.idech=pidech;
        this.telmobil=pTelmobil;
        this.telacasa=pTelacasa;
        this.email=pEmail;
        this.CNP=pCNP;
        
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
                stmt1=conn.prepareStatement("insert into jucatori" +
                "(codjuc,nume,prenume,adresa,idech,telmobil,telacasa,email,CNP) "+
                " values (?,?,?,?,?,?,?,?,?)");
                stmt1.setString(1,this.codjuc);stmt1.setString(2,this.nume);
                stmt1.setString(3,this.prenume);stmt1.setString(4, this.adresa);
                stmt1.setBigDecimal(5, this.idech);stmt1.setString(6,this.telmobil);
                stmt1.setString(7,this.telacasa);stmt1.setString(8, this.email);
                stmt1.setString(9,this.CNP);stmt1.execute();
                // trebuie sa obtinem rowid-ul noii inregistrari
                stmtRowid1=conn.createStatement();
                oracle.jdbc.OracleResultSet rs=(oracle.jdbc.OracleResultSet)
                stmtRowid1.executeQuery("select rowid from jucatori where codjuc="+
                this.codjuc.toString());
                rs.next();
                this.idLinieBD=rs.getROWID(1);
                rs.close();
                this.stare=SINCRONIZAT;
            }
            else if (this.stare==MODIFICAT){
                stmt1=conn.prepareStatement("update jucatori set "+
                "codjuc=?,nume=?,prenume=?,idech=?,adresa=?,telmobil=?,telacasa=?,email=?,CNP=? where rowid=?");
                
                stmt1.setString(1,this.codjuc);stmt1.setString(2,this.nume);
                stmt1.setString(3,this.prenume);stmt1.setBigDecimal(4, this.idech);
                stmt1.setString(5,this.adresa);stmt1.setString(6,this.telmobil);stmt1.setString(7,this.telacasa);
                stmt1.setString(8, this.email);stmt1.setString(9,this.CNP);
                
                // pentru setROWID trebuie downcasting
                ((oracle.jdbc.OraclePreparedStatement)stmt1).setROWID(8,this.idLinieBD);
                stmt1.execute();
                if (stmt1.getUpdateCount()==0)
                    throw new Exception("Inregistrarea nu mai exista in baza de date!!!!");
                this.stare=SINCRONIZAT;
            }
            else if (this.stare==STERS){
                stmt1=conn.prepareStatement("delete from jucatori where rowid=?");
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
            stmt=conn.prepareStatement("select * from jucatori where rowid=?");
            ((oracle.jdbc.OraclePreparedStatement)stmt).setROWID(1, this.idLinieBD);
            ResultSet rs=stmt.executeQuery();
            if (rs.next()) {
                this.codjuc=rs.getString("codjuc");
                this.nume=rs.getString("nume");
                this.prenume=rs.getString("prenume");
                this.adresa=rs.getString("adresa");
                this.idech=rs.getBigDecimal("idech");
                this.telmobil=rs.getString("telmobil");
                this.telacasa=rs.getString("telacasa");
                this.email=rs.getString("email");
                this.CNP=rs.getString("CNP");
                
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
        String frazaSelect="Select rowid,jucatori.* from jucatori";
        if (whereFiltru!=null)
            frazaSelect +=" "+whereFiltru;
        
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(frazaSelect);
        ArrayList listaObiecte=new ArrayList();
        try{
            Jucatori juc;
            while (rs.next()) {
                juc=new Jucatori(
                ((oracle.jdbc.OracleResultSet)rs).getROWID("rowid"),
                rs.getString("codjuc"),
                rs.getString("nume"),
                rs.getString("prenume"),
                rs.getString("adresa"),
                rs.getBigDecimal("idech"),
                rs.getString("telmobil"),
		rs.getString("telacasa"),
                rs.getString("email"),
                rs.getString("CNP")
                );
                juc.stare=SINCRONIZAT;
                listaObiecte.add(juc);
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
        return this.getNume();
    }
    
    //public void setNume(java.lang.String setNume) {
   // }
    
}