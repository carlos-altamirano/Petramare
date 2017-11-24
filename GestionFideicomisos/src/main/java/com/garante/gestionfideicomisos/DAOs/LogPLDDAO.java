package com.garante.gestionfideicomisos.DAOs;

import com.garante.gestionfideicomisos.Helpers.Conexion;
import com.garante.gestionfideicomisos.Models.LogPLD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogPLDDAO extends Conexion {
    
    public Integer save(LogPLD logPLD) {
        Integer r = -1;
        
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        String sql = "insert into logPLD values(?, ?, ?)";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, logPLD.getUsuario());
            declaracion.setString(2, logPLD.getAccion());
            declaracion.setString(3, logPLD.getObservaciones());
            r = declaracion.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LogPLDDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(null, declaracion, conn);
        }
        
        return r;
    }
    
}
