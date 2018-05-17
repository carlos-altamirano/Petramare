package mx.garante.creaxml.DAOs;

import mx.garante.creaxml.Helpers.Conexion;
import mx.garante.creaxml.Models.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuariosDAO extends Conexion {

    public List<Usuario> get(String clave_contrato) {
        List<Usuario> usuarios = new ArrayList<>();
        Connection con = this.conecta();
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        try {
            String sql = "select * from usuarios where clave_contrato = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, clave_contrato);
            rs = statement.executeQuery();
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setClave_contrato(rs.getString("clave_contrato"));
                usuario.setClave_cliente(rs.getString("clave_cliente"));
                usuario.setNombre_usuario(rs.getString("nombre_usuario"));
                usuario.setPuesto_usuario(rs.getString("puesto_usuario"));
                usuario.setTelefono_usuario(rs.getString("telefono_usuario"));
                usuario.setContacto_usuario(rs.getString("contacto_usuario"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setPassword(rs.getString("password"));
                usuario.setFecha_alta(rs.getString("fecha_alta"));
                usuario.setFecha_bloqueo(rs.getString("fecha_bloqueo"));
                usuario.setStatus(rs.getString("status"));
                usuarios.add(usuario);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconecta(con, rs, statement);
        }
        
        return usuarios;
    }
    
}
