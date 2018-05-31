package mx.garante.creaxml.DAOs;

import mx.garante.creaxml.Helpers.Conexion;
import mx.garante.creaxml.Models.Movimiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MovimientosDAO extends Conexion {
    
    public List<String> getRFCMes(String fecha1, String fecha2) {
        List<String> rfcs = new ArrayList<>();
        Connection con = this.conecta();
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        try {
            String sql = "select rfc from movimientos " +
                    "where rfc = 'TECC890312L67' and fecha_liquidacion >= ? and fecha_liquidacion <= ? " +
                    " group by rfc";
            statement = con.prepareStatement(sql);
            statement.setString(1, fecha1);
            statement.setString(2, fecha2);
            
            rs = statement.executeQuery();
            
            while (rs.next()) {
                String rfc = rs.getString("rfc");
                rfcs.add(rfc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MovimientosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconecta(con, rs, statement);
        }
        
        return rfcs;
    }
    
    public List<Movimiento> getAll(String rfc, String fecha1, String fecha2) {
        List<Movimiento> movimientos = new ArrayList<>();
        Connection con = this.conecta();
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        try {
            String sql = "select * from movimientos "
                    + "where rfc = ? "
                    + "and fecha_liquidacion >= ? "
                    + "and fecha_liquidacion <= ? "
                    + "order by fecha_liquidacion";
            statement = con.prepareStatement(sql);
            statement.setString(1, rfc);
            statement.setString(2, fecha1);
            statement.setString(3, fecha2);
            rs = statement.executeQuery();
            
            while (rs.next()) {
                Movimiento movimiento = new Movimiento();
                movimiento.setClave_contrato(rs.getString("clave_contrato"));
                movimiento.setFecha_liquidacion(rs.getDate("fecha_liquidacion"));
                movimiento.setTipo_movimiento(rs.getString("tipo_movimiento"));
                movimiento.setClave_banco(rs.getString("clave_banco"));
                movimiento.setClave_empleado(rs.getString("clave_empleado"));
                movimiento.setTipo_moneda(rs.getString("tipo_moneda"));
                movimiento.setNombre_empleado(rs.getString("nombre_empleado"));
                movimiento.setApellidoP_empleado(rs.getString("apellidoP_empleado"));
                movimiento.setApellidoM_empleado(rs.getString("apellidoM_empleado"));
                movimiento.setImporte_liquidacion(rs.getString("importe_liquidacion"));
                movimiento.setImporte_liquidacion_mxp(rs.getString("importe_liquidacion_mxp"));
                movimiento.setCuenta_deposito(rs.getString("cuenta_deposito"));
                movimiento.setCurp(rs.getString("curp"));
                movimiento.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                movimiento.setPuesto_empleado(rs.getString("puesto_empleado"));
                movimiento.setDepto_empleado(rs.getString("depto_empleado"));
                movimiento.setEnvio_cheque(rs.getString("envio_cheque"));
                movimiento.setDestino_envio_cheque(rs.getString("destino_envio_cheque"));
                movimiento.setTel_envio_cheque(rs.getString("tel_envio_cheque"));
                movimiento.setCorreo_envio_cheque(rs.getString("correo_envio_cheque"));
                movimiento.setBanco_extranjero(rs.getString("banco_extranjero"));
                movimiento.setDom_banco_extranjero(rs.getString("dom_banco_extranjero"));
                movimiento.setPais_banco_extranjero(rs.getString("pais_banco_extranjero"));
                movimiento.setABA_BIC(rs.getString("ABA_BIC"));
                movimiento.setNombre_fidei_banco_ext(rs.getString("nombre_fidei_banco_ext"));
                movimiento.setDireccion_fidei_ext(rs.getString("direccion_fidei_ext"));
                movimiento.setPais_fidei_ext(rs.getString("pais_fidei_ext"));
                movimiento.setTel_fidei_ext(rs.getString("tel_fidei_ext"));
                movimiento.setNombre_archivo(rs.getString("nombre_archivo"));
                movimiento.setRfc(rs.getString("rfc"));
                
                movimientos.add(movimiento);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MovimientosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconecta(con, rs, statement);
        }
        
        return movimientos;
    }
    
}
