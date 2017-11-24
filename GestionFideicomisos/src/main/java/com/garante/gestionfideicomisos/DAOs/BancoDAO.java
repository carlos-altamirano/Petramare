package com.garante.gestionfideicomisos.DAOs;

import com.garante.gestionfideicomisos.Helpers.Conexion;
import com.garante.gestionfideicomisos.Models.Banco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BancoDAO extends Conexion {
    
    /**
    * Realiza una busqueda de la tabla bancos atravez del campo clave
    * @param clave Integer not null
    * @return Entidad Banco
    */
    public Banco get(Integer clave) {
        Banco banco = null;
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "select * from bancos where clave = ?";
         try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setInt(1, clave);
            res = declaracion.executeQuery();
            
            if (res.next()){
                banco = new Banco();
                banco.setClave(res.getInt("clave"));
                banco.setBanco(res.getString("banco"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BancoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(res, declaracion, conn);
        }
        return banco;
    }
    
    public Integer save(Banco banco) {
        Integer r = -1;
        
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        String sql = "insert into bancosABM values(?, ?)";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setInt(1, banco.getClave());
            declaracion.setString(2, banco.getBanco());
            r = declaracion.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BancoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(null, declaracion, conn);
        }
        
        return r;
    }

    public Boolean update(Banco banco) {
        Boolean r = false;
        
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        String sql = "update bancos set banco = ? where clave = ?";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, banco.getBanco());
            declaracion.setInt(2, banco.getClave());
            declaracion.executeUpdate();
            r = true;
        } catch (SQLException ex) {
            Logger.getLogger(BancoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(null, declaracion, conn);
        }
        return r;
    }

    public Boolean delete(Banco banco) {
        Boolean r = false;
        
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        String sql = "delete from bancos where clave = ?";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setInt(1, banco.getClave());
            declaracion.executeUpdate();
            r = true;
        } catch (SQLException ex) {
            Logger.getLogger(BancoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(null, declaracion, conn);
        }
        return r;
    }
    
}
