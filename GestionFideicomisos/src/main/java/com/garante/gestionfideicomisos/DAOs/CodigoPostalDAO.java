package com.garante.gestionfideicomisos.DAOs;

import com.garante.gestionfideicomisos.Helpers.Conexion;
import com.garante.gestionfideicomisos.Models.CodigoPostal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CodigoPostalDAO extends Conexion {
    
    /**
     * Realiza la busqueda por el codigo postal
     * @param cp String Not null
     * @return Entidad CodigoPostal
     */
    public CodigoPostal get(String cp) {
        CodigoPostal codigoPostal = null;
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "select * from codigos_postales where d_codigo = ?";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, cp);
            res = declaracion.executeQuery();
            
            if (res.next()){
                codigoPostal = new CodigoPostal();
                codigoPostal.setId(res.getInt("id"));
                codigoPostal.setdAsenta(res.getString("d_asenta"));
                codigoPostal.setdTipoAsenta(res.getString("d_tipo_asenta"));
                codigoPostal.setdMnpio(res.getString("D_mnpio"));
                codigoPostal.setdEstado(res.getString("d_estado"));
                codigoPostal.setdCiudad(res.getString("d_ciudad"));
                codigoPostal.setdCP(res.getString("d_CP"));
                codigoPostal.setcEstado(res.getString("c_estado"));
                codigoPostal.setcOficina(res.getString("c_oficina"));
                codigoPostal.setcCP(res.getString("c_CP"));
                codigoPostal.setcTipoAsenta(res.getString("c_tipo_asenta"));
                codigoPostal.setcMnpio(res.getString("c_mnpio"));
                codigoPostal.setIdAsentaCpcons(res.getString("id_asenta_cpcons"));
                codigoPostal.setdZona(res.getString("d_zona"));
                codigoPostal.setcCveCiudad(res.getString("c_cve_ciudad"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CodigoPostalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(null, declaracion, conn);
        }
        return codigoPostal;
    }
    
    public Integer save(CodigoPostal codigoPostal) {
        Integer r = -1;
        
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        String sql = "insert into codigos_postales (d_codigo,d_asenta,d_tipo_asenta,D_mnpio,d_estado,d_ciudad,d_CP,c_estado,c_oficina,c_CP,c_tipo_asenta,c_mnpio,id_asenta_cpcons,d_zona,c_cve_ciudad) "
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, codigoPostal.getdCodigo());
            declaracion.setString(2, codigoPostal.getdAsenta());
            declaracion.setString(3, codigoPostal.getdTipoAsenta());
            declaracion.setString(4, codigoPostal.getdMnpio());
            declaracion.setString(5, codigoPostal.getdEstado());
            declaracion.setString(6, codigoPostal.getdCiudad());
            declaracion.setString(7, codigoPostal.getdCP());
            declaracion.setString(8, codigoPostal.getcEstado());
            declaracion.setString(9, codigoPostal.getcOficina());
            declaracion.setString(10, codigoPostal.getcCP());
            declaracion.setString(11, codigoPostal.getcTipoAsenta());
            declaracion.setString(12, codigoPostal.getcMnpio());
            declaracion.setString(13, codigoPostal.getIdAsentaCpcons());
            declaracion.setString(14, codigoPostal.getdZona());
            declaracion.setString(15, codigoPostal.getcCveCiudad());
            r = declaracion.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CodigoPostalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(null, declaracion, conn);
        }
        
        return r;
    }
    
}
