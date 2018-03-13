package com.garante.gestionfideicomisos.DAOs;

import com.garante.gestionfideicomisos.Helpers.Conexion;
import com.garante.gestionfideicomisos.Models.Socios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SociosDAO extends Conexion {
    
    public Integer save(Socios socios) {
        Integer r = -1;
        
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        
        String sql = "insert into representantes (tipo, nombre, rfc, curp, nacionalidad, porcentaje, clave_contrato, apellido1, apellido2) values (?, ?, ?, ?,?, ?, ?, ?, ?);";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setInt(1, socios.getTipo());
            declaracion.setString(2, socios.getNombre());
            declaracion.setString(3, socios.getRfc());
            declaracion.setString(4, socios.getCurp());
            declaracion.setInt(5, socios.getNacionalidad());
            declaracion.setDouble(6, socios.getPorcentaje());
            declaracion.setString(7, socios.getClaveContrato());
            declaracion.setString(8, socios.getApellido1());
            declaracion.setString(9, socios.getApellido2());
            r = declaracion.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SociosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(null, declaracion, conn);
        }
        
        return r;
    }
    
    public List<Socios> getAll(String claveContrato){
        List<Socios> list = new ArrayList<>();
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "select * from representantes where clave_contrato = ?";
        
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, claveContrato);
            res = declaracion.executeQuery();
            
            while (res.next()){
                Socios socio = new Socios();
                socio.setIdRepre(res.getInt("idRepre"));
                socio.setTipo(res.getInt("tipo"));
                socio.setNombre(res.getString("nombre"));
                socio.setApellido1(res.getString("apellido1"));
                socio.setApellido2(res.getString("apellido2"));
                socio.setRfc(res.getString("rfc"));
                socio.setCurp(res.getString("curp"));
                socio.setNacionalidad(res.getInt("nacionalidad"));
                socio.setPorcentaje(res.getDouble("porcentaje"));
                socio.setClaveContrato(res.getString("clave_contrato"));
                list.add(socio);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SociosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(res, declaracion, conn);
        }
        
        return list;
    }
    
    public Boolean delete(Integer id) {
        Boolean r = false;
        
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        String sql = "delete from representantes where idRepre = ?";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setInt(1, id);
            declaracion.executeUpdate();
            r = true;
        } catch (SQLException ex) {
            Logger.getLogger(SociosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(null, declaracion, conn);
        }
        return r;
    }
    
}
