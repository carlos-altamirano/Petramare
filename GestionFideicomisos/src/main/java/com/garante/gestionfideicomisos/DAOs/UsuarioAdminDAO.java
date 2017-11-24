package com.garante.gestionfideicomisos.DAOs;

import com.garante.gestionfideicomisos.Helpers.Conexion;
import com.garante.gestionfideicomisos.Models.UsuariosAdmin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

@Component("usuarioAdminDAO")
public class UsuarioAdminDAO extends Conexion {

    /**
    * Realiza una busqueda de la tabla usuarios_admin atravez del campo usuario y password
    * @param usuario String not null
    * @param password String not null
    * @return Entidad UsuariosSofom
    */
    public UsuariosAdmin get(String usuario, String password) {
        UsuariosAdmin usuariosSofom = null;
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "select * from usuarios_admin where usuario = ? and password = ? and status = 'A' and tipo_cuenta = 'ADM'";
         try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, usuario);
            declaracion.setString(2, password);
            res = declaracion.executeQuery();
            
            if (res.next()){
                usuariosSofom = new UsuariosAdmin();
                usuariosSofom.setClaveUsuario(res.getString("clave_usuario"));
                usuariosSofom.setNombreUsuario(res.getString("nombre_usuario"));
                usuariosSofom.setDepartamento(res.getString("departamento"));
                usuariosSofom.setCorreo(res.getString("correo"));
                usuariosSofom.setUsuario(res.getString("usuario"));
                usuariosSofom.setPassword(res.getString("password"));
                usuariosSofom.setStatus(res.getString("status"));
                usuariosSofom.setTipoCuenta(res.getString("tipo_cuenta"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioAdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(res, declaracion, conn);
        }
        return usuariosSofom;
    }
    
    public String getCorreos(String departamento) {
        String correos = "";
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "SELECT correo FROM usuarios_sofom where departamento = ?";
         try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, departamento);
            res = declaracion.executeQuery();
            
            while (res.next()){
                correos += res.getString("correo") + ",";
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioAdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(res, declaracion, conn);
        }
        return correos;
    }
    
    /**
    * Realiza una busqueda de la tabla usuarios_admin atravez del campo usuario y password
    * @param id String not null
    * @return Entidad UsuariosSofom
    */
    public UsuariosAdmin get(Integer id) {
        UsuariosAdmin usuariosSofom = null;
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "select * from usuarios_admin where clave_usuario = ?";
         try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setInt(1, id);
            res = declaracion.executeQuery();
            
            if (res.next()){
                usuariosSofom = new UsuariosAdmin();
                usuariosSofom.setClaveUsuario(res.getString("clave_usuario"));
                usuariosSofom.setNombreUsuario(res.getString("nombre_usuario"));
                usuariosSofom.setDepartamento(res.getString("departamento"));
                usuariosSofom.setCorreo(res.getString("correo"));
                usuariosSofom.setUsuario(res.getString("usuario"));
                usuariosSofom.setPassword(res.getString("password"));
                usuariosSofom.setStatus(res.getString("status"));
                usuariosSofom.setTipoCuenta(res.getString("tipo_cuenta"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioAdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(res, declaracion, conn);
        }
        return usuariosSofom;
    }
    
    /**
    * Realiza una busqueda de la tabla usuarios_admin atravez del campo usuario
    * @param usuario String not null
    * @return Entidad UsuariosSofom
    */
    public UsuariosAdmin get(String usuario) {
        UsuariosAdmin usuariosSofom = null;
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "select * from usuarios_admin where usuario = ?";
         try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, usuario);
            res = declaracion.executeQuery();
            
            if (res.next()){
                usuariosSofom = new UsuariosAdmin();
                usuariosSofom.setClaveUsuario(res.getString("clave_usuario"));
                usuariosSofom.setNombreUsuario(res.getString("nombre_usuario"));
                usuariosSofom.setDepartamento(res.getString("departamento"));
                usuariosSofom.setCorreo(res.getString("correo"));
                usuariosSofom.setUsuario(res.getString("usuario"));
                usuariosSofom.setPassword(res.getString("password"));
                usuariosSofom.setStatus(res.getString("status"));
                usuariosSofom.setTipoCuenta(res.getString("tipo_cuenta"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioAdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(res, declaracion, conn);
        }
        return usuariosSofom;
    }
    
    public List<UsuariosAdmin> getAll() {
        List<UsuariosAdmin> usuariosSofom = new ArrayList<>();
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "select * from usuarios_admin";
         try {
            declaracion = conn.prepareStatement(sql);
            res = declaracion.executeQuery();
            
            while (res.next()){
                UsuariosAdmin usuarioSofom = new UsuariosAdmin();
                usuarioSofom.setClaveUsuario(res.getString("clave_usuario"));
                usuarioSofom.setNombreUsuario(res.getString("nombre_usuario"));
                usuarioSofom.setDepartamento(res.getString("departamento"));
                usuarioSofom.setCorreo(res.getString("correo"));
                usuarioSofom.setUsuario(res.getString("usuario"));
                usuarioSofom.setPassword(res.getString("password"));
                usuarioSofom.setStatus(res.getString("status"));
                usuarioSofom.setTipoCuenta(res.getString("tipo_cuenta"));
                usuariosSofom.add(usuarioSofom);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioAdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(res, declaracion, conn);
        }
        return usuariosSofom;
    }
    
    public Integer save(UsuariosAdmin usuariosSofom) {
        Integer r = -1;
        
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        String sql = "insert into usuarios_admin values(?, ?, ?, ?, ?, ?, ? ,?)";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, usuariosSofom.getClaveUsuario());
            declaracion.setString(2, usuariosSofom.getNombreUsuario());
            declaracion.setString(3, usuariosSofom.getDepartamento());
            declaracion.setString(4, usuariosSofom.getCorreo());
            declaracion.setString(5, usuariosSofom.getUsuario());
            declaracion.setString(6, usuariosSofom.getPassword());
            declaracion.setString(7, usuariosSofom.getStatus());
            declaracion.setString(8, usuariosSofom.getTipoCuenta());
            r = declaracion.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioAdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(null, declaracion, conn);
        }
        
        return r;
    }

    public Boolean update(UsuariosAdmin usuariosSofom) {
        Boolean r = false;
        
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        String sql = "update usuarios_admin set nombre_usuario = ?, departamento = ?, correo = ?, usuario = ?, password = ?, "
                + " status = ?, tipo_cuenta = ? where clave_usuario = ?";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, usuariosSofom.getNombreUsuario());
            declaracion.setString(2, usuariosSofom.getDepartamento());
            declaracion.setString(3, usuariosSofom.getCorreo());
            declaracion.setString(4, usuariosSofom.getUsuario());
            declaracion.setString(5, usuariosSofom.getPassword());
            declaracion.setString(6, usuariosSofom.getStatus());
            declaracion.setString(7, usuariosSofom.getTipoCuenta());
            declaracion.setString(8, usuariosSofom.getClaveUsuario());
            declaracion.executeUpdate();
            r = true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioAdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(null, declaracion, conn);
        }
        return r;
    }

    public Boolean delete(UsuariosAdmin usuariosSofom) {
        Boolean r = false;
        
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        String sql = "delete from usuarios_admin where clave_usuario = ?";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, usuariosSofom.getClaveUsuario());
            declaracion.executeUpdate();
            r = true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioAdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(null, declaracion, conn);
        }
        return r;
    }
    
    public String getMaxClaveUsuario() {
        String r = "0";
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "select MAX(clave_usuario)+1 as numero from usuarios_admin";
        try {
            declaracion = conn.prepareStatement(sql);
            res = declaracion.executeQuery();
            if (res.next()) {
                r = res.getString("numero");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioAdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(res, declaracion, conn);
        }
        return r;
    }
    
}
