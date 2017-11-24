package com.garante.gestionfideicomisos.DAOs;

import com.garante.gestionfideicomisos.Helpers.Conexion;
import com.garante.gestionfideicomisos.Models.Documentacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocumentacionDAO extends Conexion {

    /**
     * Realiza una busqueda de documentacion atravez de la clave del contrato
     * @param claveContrato String clave del contrato
     * @return Entidad Documentacion
     */
    public Documentacion get(String claveContrato) {
        Documentacion documentacion = null;
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "select * from documentacion where clave_contrato = ?";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, claveContrato);
            res = declaracion.executeQuery();
            
            if (res.next()){
                documentacion = new Documentacion();
                documentacion.setIdDocumentacion(res.getInt("idDocumentacion"));
                documentacion.setCedulaidFiscal(res.getBoolean("cedulaidfiscal"));
                documentacion.setConstFirmaElect(res.getBoolean("constFirmaElect"));
                documentacion.setCompDomicilio(res.getBoolean("compDomicilio"));
                documentacion.setIdentifiOficial(res.getBoolean("identifiOficial"));
                documentacion.setCurp(res.getBoolean("curp"));
                documentacion.setPrimaReg(res.getBoolean("primaReg"));
                documentacion.setEscrituraContCliente(res.getBoolean("escrituraContCliente"));
                documentacion.setPoderesDominio(res.getBoolean("poderesDominio"));
                documentacion.setRegistroCNBV(res.getBoolean("registroCNBV"));
                documentacion.setClaveContrato(res.getString("clave_contrato"));
                documentacion.setContrato(res.getBoolean("contrato"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DocumentacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(res, declaracion, conn);
        }
        return documentacion;
    }
    
    /**
     * Realiza una busqueda de documentacion atravez del idDocumentacion
     * @param idDocumentacion Integer clave del contrato
     * @return Entidad Documentacion
     */
    public Documentacion get(Integer idDocumentacion) {
        Documentacion documentacion = null;
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "select * from documentacion where idDocumentacion = ?";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setInt(1, idDocumentacion);
            res = declaracion.executeQuery();
            
            if (res.next()){
                documentacion = new Documentacion();
                documentacion.setIdDocumentacion(res.getInt("idDocumentacion"));
                documentacion.setCedulaidFiscal(res.getBoolean("cedulaidfiscal"));
                documentacion.setConstFirmaElect(res.getBoolean("constFirmaElect"));
                documentacion.setCompDomicilio(res.getBoolean("compDomicilio"));
                documentacion.setIdentifiOficial(res.getBoolean("identifiOficial"));
                documentacion.setCurp(res.getBoolean("curp"));
                documentacion.setPrimaReg(res.getBoolean("primaReg"));
                documentacion.setEscrituraContCliente(res.getBoolean("escrituraContCliente"));
                documentacion.setPoderesDominio(res.getBoolean("poderesDominio"));
                documentacion.setRegistroCNBV(res.getBoolean("registroCNBV"));
                documentacion.setClaveContrato(res.getString("clave_contrato"));
                documentacion.setContrato(res.getBoolean("contrato"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DocumentacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(res, declaracion, conn);
        }
        return documentacion;
    }
    
    public boolean update(Documentacion documentacion) {
        boolean r = false;
        
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "update documentacion set cedulaidfiscal = ?, constFirmaElect = ?, compDomicilio = ?, identifiOficial = ?,"
                + " curp = ?, primaReg = ?, escrituraContCliente = ?, poderesDominio = ?, registroCNBV = ?, contrato = ? where idDocumentacion = ?";
        
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setBoolean(1, documentacion.getCedulaidFiscal());
            declaracion.setBoolean(2, documentacion.getConstFirmaElect());
            declaracion.setBoolean(3, documentacion.getCompDomicilio());
            declaracion.setBoolean(4, documentacion.getIdentifiOficial());
            declaracion.setBoolean(5, documentacion.getCurp());
            declaracion.setBoolean(6, documentacion.getPrimaReg());
            declaracion.setBoolean(7, documentacion.getEscrituraContCliente());
            declaracion.setBoolean(8, documentacion.getPoderesDominio());
            declaracion.setBoolean(9, documentacion.getRegistroCNBV());
            declaracion.setBoolean(10, documentacion.getContrato());
            declaracion.setInt(11, documentacion.getIdDocumentacion());
            declaracion.executeUpdate();
            r = true;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(res, declaracion, conn);
        }
        
        return r;
    }
    
}
