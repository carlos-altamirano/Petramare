package com.garante.gestionfideicomisos.DAOs;

import com.garante.gestionfideicomisos.Helpers.Conexion;
import com.garante.gestionfideicomisos.Helpers.Fechas;
import com.garante.gestionfideicomisos.Models.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO extends Conexion {

    /**
    * Realiza una busqueda de la tabla usuarios atravez del campo usuario y password
    * @param usuario String not null
    * @param password String not null
    * @param clave String not null
    * @return Entidad UsuariosSofom
    */
    public Usuario get(String usuario, String password, String clave) {
        Usuario cliente = null;
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "select * from usuarios where usuario = ? and password = ? and status = 'A' and clave_cliente = ?";
         try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, usuario);
            declaracion.setString(2, password);
            declaracion.setString(3, clave);
            res = declaracion.executeQuery();
            
            if (res.next()){
                cliente = new Usuario();
                cliente.setClaveContrato(res.getString("clave_contrato"));
                cliente.setClaveCliente(res.getString("clave_cliente"));
                cliente.setNombreUsuario(res.getString("nombre_usuario"));
                cliente.setPuesto(res.getString("puesto_usuario"));
                cliente.setTelefono(res.getString("telefono_usuario"));
                cliente.setContactoUsuario(res.getString("contacto_usuario"));
                cliente.setUsuario(res.getString("usuario"));
                cliente.setPassword(res.getString("password"));
                cliente.setFechaAlta(res.getDate("fecha_alta"));
                cliente.setFechaBloqueo(res.getDate("fecha_bloqueo"));
                cliente.setStatus(res.getString("status"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(res, declaracion, conn);
        }
        return cliente;
    }
    
    /**
    * Realiza una busqueda de la tabla usuarios atravez del campo usuario y clave de contrato
    * @param claveContrato String not null
    * @param usuario String not null
    * @return Entidad UsuariosSofom
    */
    public Usuario get(String claveContrato, String usuario) {
        Usuario cliente = null;
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "select * from usuarios where clave_contrato = ? and usuario = ?";
         try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, claveContrato);
            declaracion.setString(2, usuario);
            res = declaracion.executeQuery();
            
            if (res.next()){
                cliente = new Usuario();
                cliente.setClaveContrato(res.getString("clave_contrato"));
                cliente.setClaveCliente(res.getString("clave_cliente"));
                cliente.setNombreUsuario(res.getString("nombre_usuario"));
                cliente.setPuesto(res.getString("puesto_usuario"));
                cliente.setTelefono(res.getString("telefono_usuario"));
                cliente.setContactoUsuario(res.getString("contacto_usuario"));
                cliente.setUsuario(res.getString("usuario"));
                cliente.setPassword(res.getString("password"));
                cliente.setFechaAlta(Fechas.creaFechaDate(res.getString("fecha_alta")));
                String fechaBloqueo = res.getString("fecha_bloqueo");
                if (fechaBloqueo != null) {
                    cliente.setFechaBloqueo(Fechas.creaFechaDate(fechaBloqueo));
                }
                cliente.setStatus(res.getString("status"));
            }
            
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(res, declaracion, conn);
        }
        return cliente;
    }
    
    public Integer countUsuarios(String claveContrato, String usuario, String email) {
        Integer r = 0;
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "select count(*) from usuarios where clave_contrato like ? and nombre_usuario like ? and contacto_usuario like ?";
        
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, "%"+(claveContrato != null?claveContrato:"")+"%");
            declaracion.setString(2, "%"+(usuario != null?usuario:"")+"%");
            declaracion.setString(3, "%"+(email != null?email:"")+"%");
            res = declaracion.executeQuery();
            if (res.next()) {
                r = res.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    
    public List<Usuario> getAll(String pagina, String claveContrato, String usuario, String email) {
        List<Usuario> clientes = new ArrayList<>();
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "select * from usuarios ";
        
        if (claveContrato != null || usuario != null || email != null) {
            sql += " where";
            if (claveContrato != null) {
                sql += " clave_contrato like ? ";
            }
            if (usuario != null) {
                if (claveContrato != null) {
                    sql += " AND";
                }
                sql += " nombre_usuario like ?";
            }
            if (email != null) {
                if ((claveContrato != null) || (usuario != null)) {
                    sql += " AND";
                }
                sql += " contacto_usuario like ?";
            }
        }
        
        sql += " order by clave_contrato asc offset "+pagina+"0 rows fetch next 10 rows only;";
        
         try {
            declaracion = conn.prepareStatement(sql);
            int contador = 1;
            if (claveContrato != null) {
                declaracion.setString(contador, "%"+claveContrato+"%");
                contador++;
            }
            if (usuario != null) {
                declaracion.setString(contador, "%"+usuario+"%");
                contador++;
            }
            if (email != null) {
                declaracion.setString(contador, "%"+email+"%");
                contador++;
            }
            res = declaracion.executeQuery();
            
            while (res.next()){
                Usuario cliente = new Usuario();
                cliente.setClaveContrato(res.getString("clave_contrato"));
                cliente.setClaveCliente(res.getString("clave_cliente"));
                cliente.setNombreUsuario(res.getString("nombre_usuario"));
                cliente.setPuesto(res.getString("puesto_usuario"));
                cliente.setTelefono(res.getString("telefono_usuario"));
                cliente.setContactoUsuario(res.getString("contacto_usuario"));
                cliente.setUsuario(res.getString("usuario"));
                cliente.setPassword(res.getString("password"));
                cliente.setFechaAlta(res.getDate("fecha_alta"));
                cliente.setFechaBloqueo(res.getDate("fecha_bloqueo"));
                cliente.setStatus(res.getString("status"));
                clientes.add(cliente);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(res, declaracion, conn);
        }
        return clientes;
    }
    
    public Integer save(Usuario usuario) {
        Integer r = -1;
        
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        String sql = "insert into usuarios "
                + "(clave_contrato, clave_cliente, nombre_usuario, puesto_usuario, telefono_usuario, contacto_usuario,"
                + "usuario, password, fecha_alta, fecha_bloqueo, status) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, usuario.getClaveContrato());
            declaracion.setString(2, usuario.getClaveCliente());
            declaracion.setString(3, usuario.getNombreUsuario());
            declaracion.setString(4, usuario.getPuesto());
            declaracion.setString(5, usuario.getTelefono());
            declaracion.setString(6, usuario.getContactoUsuario());
            declaracion.setString(7, usuario.getUsuario());
            declaracion.setString(8, usuario.getPassword());
            declaracion.setString(9, Fechas.creaFechaString(usuario.getFechaAlta()));
            if (usuario.getFechaBloqueo() == null) {
                declaracion.setString(10, null);
            } else {
                declaracion.setString(10, Fechas.creaFechaString(usuario.getFechaBloqueo()));
            }
            declaracion.setString(11, usuario.getStatus());
            r = declaracion.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(null, declaracion, conn);
        }
        
        return r;
    }
    
    public Boolean update(Usuario usuario, String claveContrato, String userName) {
        Boolean r = false;
        
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        String sql = "update usuarios "
                + "set clave_contrato = ?, clave_cliente = ?, nombre_usuario = ?, puesto_usuario = ?, telefono_usuario = ?, "
                + "contacto_usuario = ?, usuario = ?, password = ?, fecha_alta = ?, fecha_bloqueo = ?, status = ? "
                + "where clave_contrato = ? and usuario = ?";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, usuario.getClaveContrato());
            declaracion.setString(2, usuario.getClaveCliente());
            declaracion.setString(3, usuario.getNombreUsuario());
            declaracion.setString(4, usuario.getPuesto());
            declaracion.setString(5, usuario.getTelefono());
            declaracion.setString(6, usuario.getContactoUsuario());
            declaracion.setString(7, usuario.getUsuario());
            declaracion.setString(8, usuario.getPassword());
            declaracion.setString(9, Fechas.creaFechaString(usuario.getFechaAlta()));
            if (usuario.getFechaBloqueo() == null) {
                declaracion.setString(10, null);
            } else {
                declaracion.setString(10, Fechas.creaFechaString(usuario.getFechaBloqueo()));
            }
            declaracion.setString(11, usuario.getStatus());
            declaracion.setString(12, claveContrato);
            declaracion.setString(13, userName);
            declaracion.executeUpdate();
            r = true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(null, declaracion, conn);
        }
        
        return r;
    }
    
}
