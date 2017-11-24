package com.garante.gestionfideicomisos.DAOs;

import com.garante.gestionfideicomisos.Helpers.Conexion;
import com.garante.gestionfideicomisos.Models.CuentaBanco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CuentaBancoDAO extends Conexion {

    public List<CuentaBanco> getAll() {
        List<CuentaBanco> cuentasBancos = new ArrayList<>();
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "select * from cuentas_banco";
         try {
            declaracion = conn.prepareStatement(sql);
            res = declaracion.executeQuery();
            
            while (res.next()){
                CuentaBanco cuentaBanco = new CuentaBanco();
                cuentaBanco.setCuentaOrigen(res.getString("cuenta_origen"));
                cuentaBanco.setNumCuenta(res.getString("num_cuenta"));
                cuentaBanco.setClaveCuenta(res.getString("clave_cuenta"));
                cuentaBanco.setStatus(res.getString("status"));
                cuentasBancos.add(cuentaBanco);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CuentaBancoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(res, declaracion, conn);
        }
        return cuentasBancos;
    }
    
    public CuentaBanco get(String cuentaOrigen) {
        CuentaBanco cuentaBanco = null;
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "select * from cuentas_banco where cuenta_origen = ?";
         try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, cuentaOrigen);
            res = declaracion.executeQuery();
            
            if (res.next()){
                cuentaBanco = new CuentaBanco();
                cuentaBanco.setCuentaOrigen(res.getString("cuenta_origen"));
                cuentaBanco.setNumCuenta(res.getString("num_cuenta"));
                cuentaBanco.setClaveCuenta(res.getString("clave_cuenta"));
                cuentaBanco.setStatus(res.getString("status"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CuentaBancoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(res, declaracion, conn);
        }
        return cuentaBanco;
    }
    
    public Integer save(CuentaBanco cuentaBanco) {
        Integer r = -1;
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        String sql = "insert into cuentas_banco values(?, ?, ?, ?)";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, cuentaBanco.getCuentaOrigen());
            declaracion.setString(2, cuentaBanco.getNumCuenta());
            declaracion.setString(3, cuentaBanco.getClaveCuenta());
            declaracion.setString(4, cuentaBanco.getStatus());
            r = declaracion.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CuentaBancoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(null, declaracion, conn);
        }
        return r;
    }
    
    public Boolean update(CuentaBanco cuentaBanco, String cuentaOrigen) {
        Boolean r = false;
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        String sql = "update cuentas_banco set cuenta_origen = ?, num_cuenta = ?, clave_cuenta = ?, status = ? where cuenta_origen = ?";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, cuentaBanco.getCuentaOrigen());
            declaracion.setString(2, cuentaBanco.getNumCuenta());
            declaracion.setString(3, cuentaBanco.getClaveCuenta());
            declaracion.setString(4, cuentaBanco.getStatus());
            declaracion.setString(5, cuentaOrigen);
            declaracion.executeUpdate();
            r = true;
        } catch (SQLException ex) {
            Logger.getLogger(CuentaBancoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(null, declaracion, conn);
        }
        return r;
    }
    
}
