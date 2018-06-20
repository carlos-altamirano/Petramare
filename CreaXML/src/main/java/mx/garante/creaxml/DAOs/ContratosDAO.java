package mx.garante.creaxml.DAOs;

import mx.garante.creaxml.Helpers.Conexion;
import mx.garante.creaxml.Models.Contrato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContratosDAO extends Conexion {
    
    public Contrato get(String clave_contrato) {
        Contrato contrato = null;
        Connection con = this.conecta();
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        try {
            String sql = "select * from contratos where clave_contrato = ? and status = 'A'";
            statement = con.prepareStatement(sql);
            statement.setString(1, clave_contrato);
            rs = statement.executeQuery();
            
            if (rs.next()) {
                contrato = new Contrato();
                contrato.setClave_contrato(rs.getString("clave_contrato"));
                contrato.setNombre_cliente(rs.getString("nombre_cliente"));
                contrato.setCuenta_origen(rs.getString("cuenta_origen"));
                contrato.setGrupo(rs.getString("grupo"));
                contrato.setDomicilio_fiscal(rs.getString("domicilio_fiscal"));
                contrato.setRFC(rs.getString("RFC"));
                contrato.setTelefono(rs.getString("telefono"));
                contrato.setCorreo(rs.getString("correo"));
                contrato.setTipo_honorario(rs.getString("tipo_honorario"));
                contrato.setHonorario_sin_iva(rs.getFloat("honorario_sin_iva"));
                contrato.setOficinas(rs.getString("oficinas"));
                contrato.setFecha_captura(rs.getDate("fecha_captura"));
                contrato.setStatus(rs.getString("status"));
                contrato.setSaldo(rs.getFloat("saldo"));
                contrato.setId_codes(rs.getString("id_codes"));
                contrato.setEnt_fed(rs.getString("ent_fed"));
                contrato.setCod_pos(rs.getString("cod_pos"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ContratosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconecta(con, rs, statement);
        }
        
        return contrato;
    }
    
    public List<Contrato> getAll() {
        List<Contrato> contratos = new ArrayList<>();
        Connection con = this.conecta();
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        try {
            String sql = "select TOP(1) * from contratos where status = 'A'";
            statement = con.prepareStatement(sql);
            rs = statement.executeQuery();
            
            while (rs.next()) {
                Contrato contrato = new Contrato();
                contrato.setClave_contrato(rs.getString("clave_contrato"));
                contrato.setNombre_cliente(rs.getString("nombre_cliente"));
                contrato.setCuenta_origen(rs.getString("cuenta_origen"));
                contrato.setGrupo(rs.getString("grupo"));
                contrato.setDomicilio_fiscal(rs.getString("domicilio_fiscal"));
                contrato.setRFC(rs.getString("RFC"));
                contrato.setTelefono(rs.getString("telefono"));
                contrato.setCorreo(rs.getString("correo"));
                contrato.setTipo_honorario(rs.getString("tipo_honorario"));
                contrato.setHonorario_sin_iva(rs.getFloat("honorario_sin_iva"));
                contrato.setOficinas(rs.getString("oficinas"));
                contrato.setFecha_captura(rs.getDate("fecha_captura"));
                contrato.setStatus(rs.getString("status"));
                contrato.setSaldo(rs.getFloat("saldo"));
                contrato.setId_codes(rs.getString("id_codes"));
                contrato.setEnt_fed(rs.getString("ent_fed"));
                contrato.setCod_pos(rs.getString("cod_pos"));
                contratos.add(contrato);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ContratosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconecta(con, rs, statement);
        }
        
        return contratos;
    }
    
}
