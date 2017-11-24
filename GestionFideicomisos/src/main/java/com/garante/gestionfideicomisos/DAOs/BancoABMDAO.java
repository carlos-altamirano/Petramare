package com.garante.gestionfideicomisos.DAOs;

import com.garante.gestionfideicomisos.Helpers.Conexion;
import com.garante.gestionfideicomisos.Models.BancoABM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BancoABMDAO extends Conexion {

    /**
    * Realiza una busqueda de la tabla bancosABM atravez del campo clave
    * @param clave Integer not null
    * @return Entidad BancoABM
    */
    public BancoABM get(Integer clave) {
        BancoABM bancoABM = null;
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "select * from bancosABM where clave = ?";
         try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setInt(1, clave);
            res = declaracion.executeQuery();
            
            if (res.next()){
                bancoABM = new BancoABM();
                bancoABM.setClave(res.getInt("clave"));
                bancoABM.setBanco(res.getString("banco"));
                bancoABM.setAbreviacion(res.getString("abreviacion"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BancoABMDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(res, declaracion, conn);
        }
        return bancoABM;
    }
    
    public Integer save(BancoABM bancoABM) {
        Integer r = -1;
        
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        String sql = "insert into bancosABM values(?, ?, ?)";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setInt(1, bancoABM.getClave());
            declaracion.setString(2, bancoABM.getBanco());
            declaracion.setString(3, bancoABM.getAbreviacion());
            r = declaracion.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BancoABMDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(null, declaracion, conn);
        }
        
        return r;
    }

    public Boolean update(BancoABM bancoABM) {
        Boolean r = false;
        
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        String sql = "update bancosABM set banco = ?, abreviacion = ? where clave = ?";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, bancoABM.getBanco());
            declaracion.setString(2, bancoABM.getAbreviacion());
            declaracion.setInt(3, bancoABM.getClave());
            declaracion.executeUpdate();
            r = true;
        } catch (SQLException ex) {
            Logger.getLogger(BancoABMDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(null, declaracion, conn);
        }
        return r;
    }

    public Boolean delete(BancoABM bancoABM) {
        Boolean r = false;
        
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        String sql = "delete from bancosABM where clave = ?";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setInt(1, bancoABM.getClave());
            declaracion.executeUpdate();
            r = true;
        } catch (SQLException ex) {
            Logger.getLogger(BancoABMDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(null, declaracion, conn);
        }
        return r;
    }
}
