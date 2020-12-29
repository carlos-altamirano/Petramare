package com.reports.generareportes.DAO;

import com.reports.generareportes.Helpers.Fechas;
import com.reports.generareportes.Modelos.Contrato;
import com.reports.generareportes.Modelos.Movimiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consultas extends Conexion {
    
    public List<Movimiento> consultaMovs(String dataBase, String fechaInicio, String fechaFin) {
        List<Movimiento> movimientos = new ArrayList<>();
        Connection con = this.conecta(dataBase);
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        try {
            String sql = "SELECT lote.clave_contrato, lote.fecha_usuario_autoriza,lote.fecha_liquidacion,movimiento.clave_empleado, " +
            "movimiento.nombre_empleado, movimiento.apellidoP_empleado, movimiento.apellidoM_empleado, movimiento.curp, movimiento.rfc, movimiento.cuenta_deposito, " +
            "movimiento.fecha_ingreso, movimiento.depto_empleado, movimiento.puesto_empleado, movimiento.tipo_movimiento, movimiento.importe_liquidacion, movimiento.importe_liquidacion_mxp " +
            "from movimientos_h lote, movimientos movimiento " +
            "WHERE  lote.status = 'T' " +
            "AND lote.fecha_liquidacion=movimiento.fecha_liquidacion " +
            "and lote.clave_contrato = movimiento.clave_contrato " +
            "and lote.nombre_archivo= movimiento.nombre_archivo " +
            "AND lote.fecha_usuario_autoriza IS NOT NULL " +
            "and lote.fecha_usuario_autoriza>='"+fechaInicio+" 00:00:00.000' " +
            "and lote.fecha_usuario_autoriza<='"+fechaFin+" 23:59:59.999' " +
            "ORDER BY lote.clave_contrato,lote.fecha_usuario_autoriza,lote.fecha_liquidacion ASC;";
            statement = con.prepareStatement(sql);
            rs = statement.executeQuery();
            
            while (rs.next()) {
                Movimiento movimiento = new Movimiento();
                movimiento.setClaveContrato(rs.getString("clave_contrato"));
                movimiento.setFechaUsuarioAutoriza(rs.getString("fecha_usuario_autoriza"));
                movimiento.setFechaLiquidacion(rs.getString("fecha_liquidacion"));
                movimiento.setClaveEmpleado(rs.getString("clave_empleado"));
                movimiento.setNombreEmpleado(rs.getString("nombre_empleado"));
                movimiento.setApellidoPEmpleado(rs.getString("apellidoP_empleado"));
                movimiento.setApellidoMEmpleado(rs.getString("apellidoM_empleado"));
                movimiento.setCurp(rs.getString("curp"));
                movimiento.setRfc(rs.getString("rfc"));
                movimiento.setCuentaDeposito(rs.getString("cuenta_deposito"));
                movimiento.setFechaIngreso(rs.getString("fecha_ingreso"));
                movimiento.setDeptoEmpleado(rs.getString("depto_empleado"));
                movimiento.setPuestoEmpleado(rs.getString("puesto_empleado"));
                movimiento.setTipo(rs.getString("tipo_movimiento"));
                movimiento.setImporteLiquidacion(rs.getString("importe_liquidacion"));
                movimiento.setImporteLiquidacionMxn(rs.getString("importe_liquidacion_mxp"));
                movimientos.add(movimiento);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconecta(con, rs, statement);
        }
        return movimientos;
    }
    
    public List<Contrato> getContratos(String dataBase) {
        List<Contrato> contratos = new ArrayList<>();
        Connection conn = this.conecta(dataBase);
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "select * from contratos";
         try {
            declaracion = conn.prepareStatement(sql);
            res = declaracion.executeQuery();
            
            while (res.next()){
                Contrato contrato = new Contrato();
                contrato.setClaveContrato(res.getString("clave_contrato"));
                contrato.setNombreCliente(res.getString("nombre_cliente"));
                contrato.setCuentaOrigen(res.getString("cuenta_origen"));
                contrato.setGrupo(res.getString("grupo"));
                contrato.setDomicilioFiscal(res.getString("domicilio_fiscal"));
                contrato.setRfc(res.getString("RFC"));
                contrato.setTelefono(res.getString("telefono"));
                contrato.setCorreo(res.getString("correo"));
                contrato.setTipoHonorario(res.getString("tipo_honorario"));
                contrato.setHonorarioSinIva(res.getDouble("honorario_sin_iva"));
                contrato.setOficinas(res.getString("oficinas"));
                contrato.setFechaCaptura(Fechas.creaFechaDate(res.getString("fecha_captura")));
                contrato.setStatus(res.getString("status"));
                contrato.setSaldo(res.getDouble("saldo"));
                contrato.setIdCodes(res.getString("id_codes"));
                contrato.setEntFed(res.getString("ent_fed"));
                contrato.setCodPos(res.getString("cod_pos"));
                contratos.add(contrato);
            }
            
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconecta(conn, res, declaracion);
        }
        return contratos;
    }
    
    public List<Movimiento> getAll(String dataBase, String fecha1, String fecha2) {
        List<Movimiento> movimientos = new ArrayList<>();
        Connection con = this.conecta(dataBase);
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            String sql = "select lote.clave_contrato, lote.fecha_usuario_autoriza,lote.fecha_liquidacion,movimiento.clave_empleado, "
                    + "movimiento.nombre_empleado, movimiento.apellidoP_empleado, movimiento.apellidoM_empleado, movimiento.curp, movimiento.rfc, movimiento.cuenta_deposito, "
                    + "movimiento.fecha_ingreso, movimiento.depto_empleado, movimiento.puesto_empleado, movimiento.tipo_movimiento, movimiento.importe_liquidacion,"
                    + " movimiento.importe_liquidacion_mxp,(select c.uuid from comprobantesNomina c where c.claveContrato=movimiento.clave_contrato "
                    + " and c.rfc=movimiento.rfc and fechaNomina=SUBSTRING(CONVERT(CHAR(6), lote.fecha_liquidacion, 112),1,4)+'-' "
                    + " +SUBSTRING( CONVERT(CHAR(6), lote.fecha_liquidacion, 112),5,6)) uuid "
                    + "from movimientos movimiento,movimientos_h lote "
                    + "where "
                    + "movimiento.clave_contrato = lote.clave_contrato "
                    + "and movimiento.nombre_archivo = lote.nombre_archivo "
                    + "and movimiento.fecha_liquidacion = lote.fecha_liquidacion "
                    + "and movimiento.fecha_liquidacion >= ? and movimiento.fecha_liquidacion <= ? "
                    + "and lote.status_autoriza = 'A' "
                    + "and lote.fecha_usuario_autoriza >=? and lote.fecha_usuario_autoriza <= ? "
                    //                  + " group by movimiento.rfc "
                    + "order by movimiento.clave_contrato,movimiento.rfc,movimiento.fecha_liquidacion";

            statement = con.prepareStatement(sql);
            statement.setString(1, fecha1);
            statement.setString(2, fecha2);
            statement.setString(3, fecha1);
            statement.setString(4, fecha2);
            rs = statement.executeQuery();

            while (rs.next()) {
                Movimiento movimiento = new Movimiento();
                movimiento.setClaveContrato(rs.getString("clave_contrato"));
                movimiento.setFechaUsuarioAutoriza(rs.getString("fecha_usuario_autoriza"));
                movimiento.setFechaLiquidacion(rs.getString("fecha_liquidacion"));
                movimiento.setClaveEmpleado(rs.getString("clave_empleado"));
                movimiento.setNombreEmpleado(rs.getString("nombre_empleado"));
                movimiento.setApellidoPEmpleado(rs.getString("apellidoP_empleado"));
                movimiento.setApellidoMEmpleado(rs.getString("apellidoM_empleado"));
                movimiento.setCurp(rs.getString("curp"));
                movimiento.setRfc(rs.getString("rfc"));
                movimiento.setCuentaDeposito(rs.getString("cuenta_deposito"));
                movimiento.setFechaIngreso(rs.getString("fecha_ingreso"));
                movimiento.setDeptoEmpleado(rs.getString("depto_empleado"));
                movimiento.setPuestoEmpleado(rs.getString("puesto_empleado"));
                movimiento.setTipo(rs.getString("tipo_movimiento"));
                movimiento.setImporteLiquidacion(rs.getString("importe_liquidacion"));
                movimiento.setImporteLiquidacionMxn(rs.getString("importe_liquidacion_mxp"));
                movimiento.setUuid(rs.getString("uuid"));
                movimientos.add(movimiento);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconecta(con, rs, statement);
        }

        return movimientos;
    }
    
}
