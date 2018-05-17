package mx.garante.creaxml.DAOs;

import mx.garante.creaxml.Helpers.Conexion;
import mx.garante.creaxml.Models.CompNomina;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompNominaDAO extends Conexion {

    public CompNomina getBy(String rfc, String fecha1) {
        CompNomina compNomina = null;

        Connection con = this.conecta();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            String sql = "select * from comprobantesNomina where rfc = ? and fechaNomina = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, rfc);
            statement.setString(2, fecha1.substring(0, 7));
            rs = statement.executeQuery();

            if (rs.next()) {
                compNomina = new CompNomina();
                compNomina.setIdComprobante(rs.getInt("idComprobante"));
                compNomina.setFecha(rs.getString("fecha"));
                compNomina.setFechaNomina(rs.getString("fechaNomina"));
                compNomina.setClaveContrato(rs.getString("claveContrato"));
                compNomina.setRfc(rs.getString("rfc"));
                compNomina.setTotal(rs.getDouble("total"));
                compNomina.setFechaTimbre(rs.getString("fechaTimbre"));
                compNomina.setRfcProv(rs.getString("rfcProv"));
                compNomina.setUuid(rs.getString("uuid"));
                compNomina.setSelloCFD(rs.getString("selloCFD"));
                compNomina.setnCertificado(rs.getString("nCertificado"));
                compNomina.setSelloSAT(rs.getString("selloSAT"));
                CertificadosDAO certificadosDAO = new CertificadosDAO();
                compNomina.setCertificado(certificadosDAO.get(rs.getInt("idCertificado")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(CompEdoCtaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconecta(con, rs, statement);
        }

        return compNomina;
    }

    public boolean insert(CompNomina compNomina) {
        boolean res = false;

        Connection con = this.conecta();
        PreparedStatement statement = null;

        try {
            String sql = "insert into comprobantesNomina (fecha, fechaNomina, claveContrato, rfc, total, fechaTimbre, rfcProv, uuid, selloCFD, nCertificado, selloSAT, idCertificado) "+
                    "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, compNomina.getFecha());
            statement.setString(2, compNomina.getFechaNomina().substring(0, 7));
            statement.setString(3, compNomina.getClaveContrato());
            statement.setString(4, compNomina.getRfc());
            statement.setDouble(5, compNomina.getTotal());
            statement.setString(6, compNomina.getFechaTimbre());
            statement.setString(7, compNomina.getRfcProv());
            statement.setString(8, compNomina.getUuid());
            statement.setString(9, compNomina.getSelloCFD());
            statement.setString(10, compNomina.getnCertificado());
            statement.setString(11, compNomina.getSelloSAT());
            statement.setInt(12, compNomina.getCertificado().getIdCertificado());
            statement.executeUpdate();
            res = true;
        } catch (SQLException ex) {
            Logger.getLogger(CompNominaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconecta(con, null, statement);
        }

        return res;
    }

}
