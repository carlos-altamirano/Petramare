package mx.garante.creaxml.DAOs;

import mx.garante.creaxml.Helpers.Conexion;
import mx.garante.creaxml.Models.CompEdoCta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompEdoCtaDAO extends Conexion {

    public CompEdoCta getBy(String claveContrato, String fecha1) {
        CompEdoCta compEdoCta = null;

        Connection con = this.conecta();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            String sql = "select * from comprobantesEdoCta where claveContrato = ? and fechaEdoCta = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, claveContrato);
            statement.setString(2, fecha1.substring(0, 7));
            rs = statement.executeQuery();

            if (rs.next()) {
                compEdoCta = new CompEdoCta();
                compEdoCta.setIdComprobante(rs.getInt("idComprobante"));
                compEdoCta.setFecha(rs.getString("fecha"));
                compEdoCta.setFechaEdoCta(rs.getString("fechaEdoCta"));
                compEdoCta.setClaveContrato(rs.getString("claveContrato"));
                compEdoCta.setTotal(rs.getDouble("total"));
                compEdoCta.setFechaTimbre(rs.getString("fechaTimbre"));
                compEdoCta.setRfcProv(rs.getString("rfcProv"));
                compEdoCta.setUuid(rs.getString("uuid"));
                compEdoCta.setSelloCFD(rs.getString("selloCFD"));
                compEdoCta.setnCertificado(rs.getString("nCertificado"));
                compEdoCta.setSelloSAT(rs.getString("selloSAT"));
                CertificadosDAO certificadosDAO = new CertificadosDAO();
                compEdoCta.setCertificado(certificadosDAO.get(rs.getInt("idCertificado")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(CompEdoCtaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconecta(con, rs, statement);
        }

        return compEdoCta;
    }

    public boolean insert(CompEdoCta compEdoCta) {
        boolean res = false;

        Connection con = this.conecta();
        PreparedStatement statement = null;

        try {
            String sql = "insert into comprobantesEdoCta (fecha, fechaEdoCta, claveContrato, total, fechaTimbre, rfcProv, uuid, selloCFD, nCertificado, selloSAT, idCertificado) "+
                    "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, compEdoCta.getFecha());
            statement.setString(2, compEdoCta.getFechaEdoCta().substring(0, 7));
            statement.setString(3, compEdoCta.getClaveContrato());
            statement.setDouble(4, compEdoCta.getTotal());
            statement.setString(5, compEdoCta.getFechaTimbre());
            statement.setString(6, compEdoCta.getRfcProv());
            statement.setString(7, compEdoCta.getUuid());
            statement.setString(8, compEdoCta.getSelloCFD());
            statement.setString(9, compEdoCta.getnCertificado());
            statement.setString(10, compEdoCta.getSelloSAT());
            statement.setInt(11, compEdoCta.getCertificado().getIdCertificado());
            statement.executeUpdate();
            res = true;
        } catch (SQLException ex) {
            Logger.getLogger(CompEdoCtaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconecta(con, null, statement);
        }

        return res;
    }
    
}