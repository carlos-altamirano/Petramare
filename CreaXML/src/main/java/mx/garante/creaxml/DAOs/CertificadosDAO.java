package mx.garante.creaxml.DAOs;

import mx.garante.creaxml.Helpers.Conexion;
import mx.garante.creaxml.Models.Certificado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CertificadosDAO extends Conexion {
    
    public Certificado get(Integer id) {
        Certificado certificado = null;
        
        Connection con = this.conecta();
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        try {
            String sql = "select * from certificados where idCertificado = ?";
            statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            rs = statement.executeQuery();
            
            if (rs.next()) {
                certificado = new Certificado();
                certificado.setIdCertificado(rs.getInt("idCertificado"));
                certificado.setnCertificado(rs.getString("nCertificado"));
                certificado.setCertificado(rs.getString("certificado"));
                certificado.setPassword(rs.getString("password"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CertificadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconecta(con, rs, statement);
        }
        
        return certificado;
    }
    
    public boolean insert(Certificado certificado){
        boolean res = false;
        Connection con = this.conecta();
        PreparedStatement statement = null;
        
        try {
            String sql = "insert into certificados (nCertificado, certificado, password) values(?, ?, ?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, certificado.getnCertificado());
            statement.setString(2, certificado.getCertificado());
            statement.setString(3, certificado.getPassword());
            statement.executeUpdate();
            res = true;
        } catch (SQLException ex) {
            Logger.getLogger(CertificadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconecta(con, null, statement);
        }
        
        return res;
    }
    
}
