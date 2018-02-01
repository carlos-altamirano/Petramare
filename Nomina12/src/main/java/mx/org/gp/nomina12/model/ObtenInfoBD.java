/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.org.gp.nomina12.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author luis-valerio
 */
public class ObtenInfoBD {

    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    Conexion conn = new Conexion();

    public List<MovsMesResum> obtenGrupoMovsPorRFC(Date primerDiaMes, Date ultimoDiaMes) {
        Connection connection;
        PreparedStatement statement;
        List<MovsMesResum> movimientos = new ArrayList<>();
//        long time_start, time_end;
        try {
//            time_start = System.currentTimeMillis();
            connection = conn.conectar();
            ResultSet rstSQLServer;
            String query = "   select lote.clave_contrato, rfc, sum(CAST(importe_liquidacion as float)) as importe_pagado"
                    //                    + " ,sum(CAST(importe_liquidacion_mxp as float)) as importe_pagado_mxp  "
                    + " from movimientos, movimientos_h as lote "
                    + " where movimientos.nombre_archivo = lote.nombre_archivo "
                    + " and lote.status ='T' "
                    + " and movimientos.rfc !='' "
                    + " and lote.fecha_usuario_autoriza IS NOT NULL "
                    + " and lote.fecha_usuario_autoriza>='" + formatoFecha.format(primerDiaMes) + " 00:00:00.000' "
                    + " and lote.fecha_usuario_autoriza<='" + formatoFecha.format(ultimoDiaMes) + " 23:59:59.999' "
                    + " group by rfc, lote.clave_contrato order by lote.clave_contrato;";
            statement = connection.prepareStatement(query);
            rstSQLServer = statement.executeQuery();
            while (rstSQLServer.next()) {
                MovsMesResum infoMov = new MovsMesResum();
                String rfc = rstSQLServer.getString("rfc");
                if (rfc != null && !rfc.isEmpty()) {
                    infoMov.setRfc(rfc);
                    infoMov.setContrato(rstSQLServer.getString("clave_contrato"));
//                    infoMov.setImporte(rstSQLServer.getDouble("importe_pagado"));
//                    infoMov.setImporte_mxp(rstSQLServer.getDouble("importe_pagado_mxp"));
                    movimientos.add(infoMov);
                }
            }
            conn.desconectar(rstSQLServer, statement, connection);
//            time_end = System.currentTimeMillis();
        } catch (SQLException ex) {
            movimientos = null;
            System.out.println("Exception:ObtenInfoBD:obtenGrupoMovsPorRFC()" + ex.getMessage());
        }
        return movimientos;
    }

    public MovsMesResum obtenGrupoMovsPorRFC(Date primerDiaMes, Date ultimoDiaMes, String rfc) {
        Connection connection;
        PreparedStatement statement;
        MovsMesResum infoMov = null;
//        long time_start, time_end;
        try {
//            time_start = System.currentTimeMillis();
            connection = conn.conectar();
            ResultSet rstSQLServer;
            String query = "   select lote.clave_contrato, rfc, sum(CAST(importe_liquidacion as float)) as importe_pagado"
                    //                    + " ,sum(CAST(importe_liquidacion_mxp as float)) as importe_pagado_mxp  "
                    + " from movimientos, movimientos_h as lote "
                    + " where movimientos.nombre_archivo = lote.nombre_archivo "
                    + " and lote.status ='T' "
                    + " and movimientos.rfc ='" + rfc + "' "
                    + " and lote.fecha_usuario_autoriza IS NOT NULL "
                    + " and lote.fecha_usuario_autoriza>='" + formatoFecha.format(primerDiaMes) + " 00:00:00.000' "
                    + " and lote.fecha_usuario_autoriza<='" + formatoFecha.format(ultimoDiaMes) + " 23:59:59.999' "
                    + " group by rfc, lote.clave_contrato order by lote.clave_contrato;";
            statement = connection.prepareStatement(query);
            rstSQLServer = statement.executeQuery();
            while (rstSQLServer.next()) {
                infoMov = new MovsMesResum();
                infoMov.setRfc(rfc);
                infoMov.setContrato(rstSQLServer.getString("clave_contrato"));
            }
            conn.desconectar(rstSQLServer, statement, connection);
//            time_end = System.currentTimeMillis();
        } catch (SQLException ex) {
            infoMov = null;
            System.out.println("Exception:ObtenInfoBD:obtenGrupoMovsPorRFC()" + ex.getMessage());
        }
        return infoMov;
    }
    
    public String obtenRFC_fromCURP(String curp) {
        Connection connection;
        PreparedStatement statement;
        String rfc = "";
        try {
            connection = conn.conectar();
            ResultSet rstSQLServer;
            String query = " select TOP 1 rfc from movimientos "
                    + " where curp ='" + curp + "' order by rfc desc;";
            statement = connection.prepareStatement(query);
            rstSQLServer = statement.executeQuery();
            if (rstSQLServer.next()) {
                rfc = rstSQLServer.getString("rfc");
            }
            conn.desconectar(rstSQLServer, statement, connection);
        } catch (SQLException ex) {
            rfc = "";
            System.out.println("Exception:ObtenInfoBD:obtenGrupoMovsPorRFC()" + ex.getMessage());
        }
        return rfc;
    }

//    public EmpresaCliente obtenInfoEmpresaCte(String claveContrato) {
////        clsConexion conn = new clsConexion();
//        Conexion conn = new Conexion();
//        Connection connection = null;
////        Statement statement = null;
//        PreparedStatement statement = null;
//        EmpresaCliente emp = new EmpresaCliente();
//        long time_start, time_end;
//        try {
//            time_start = System.currentTimeMillis();
//            connection = conn.conectar();
////            statement = connection.createStatement();
//            ResultSet rstSQLServer = null;
////            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
//            String query = " select rfc, clave_contrato, ent_fed from contratos where clave_contrato='" + claveContrato + "'";
//            statement = connection.prepareStatement(query);
//            rstSQLServer = statement.executeQuery();
//            if (rstSQLServer.next()) {
//                emp.setRfc(rstSQLServer.getString("rfc"));
//                emp.setClaveContrato(rstSQLServer.getString("clave_contrato"));
//                emp.setEntFed(rstSQLServer.getString("ent_fed"));
//            }
////            rstSQLServer.close();
////            statement.close();
////            if (connection != null) {
//            conn.desconectar(rstSQLServer, statement, connection);
////            }
//            time_end = System.currentTimeMillis();
//        } catch (SQLException ex) {
//            emp = null;
//            System.out.println("Exception:ObtenInfoBD:obtenInfoEmpresaCte()" + ex.getMessage());
//        }
//        return emp;
//    }
    public List<Map> obtenConceptos(String rfc, String claveContrato, Date primerDiaMes, Date ultimoDiaMes) {
//        clsConexion conn = new clsConexion();
        Connection connection = null;
//        Statement statement = null;
        PreparedStatement statement = null;
        List<Map> movimientos = new ArrayList<>();
        Map mov;
        long time_start, time_end;
        try {
            time_start = System.currentTimeMillis();
            connection = conn.conectar();
//            statement = connection.createStatement();
            ResultSet rstSQLServer = null;
            String query = " select lote.fecha_liquidacion, CAST(importe_liquidacion as float) as importe_pagado "
                    + " ,CAST(importe_liquidacion_mxp as float) as importe_pagado_mxp, tipo_movimiento "
                    + " from movimientos, movimientos_h as lote "
                    + " where "
                    + " movimientos.nombre_archivo = lote.nombre_archivo "
                    + " and lote.status ='T' "
                    + " and movimientos.clave_contrato = lote.clave_contrato"
                    + " and lote.fecha_usuario_autoriza IS NOT NULL "
                    + " and lote.fecha_usuario_autoriza>='" + formatoFecha.format(primerDiaMes) + " 00:00:00.000' "
                    + " and lote.fecha_usuario_autoriza<='" + formatoFecha.format(ultimoDiaMes) + " 23:59:59.999' "
                    + " and movimientos.rfc = '" + rfc + "' "
                    + " and movimientos.clave_contrato='" + claveContrato + "'"
                    + " order by lote.clave_contrato, lote.fecha_liquidacion ";
            statement = connection.prepareStatement(query);
            rstSQLServer = statement.executeQuery();
            while (rstSQLServer.next()) {
                mov = new HashMap();
                mov.put("fecha_liquidacion", rstSQLServer.getString("fecha_liquidacion"));
                mov.put("importe", rstSQLServer.getDouble("importe_pagado"));
                mov.put("importe_mxp", rstSQLServer.getDouble("importe_pagado_mxp"));
                mov.put("tipo_movimiento", rstSQLServer.getString("tipo_movimiento"));
                movimientos.add(mov);
            }
            if (movimientos.size() > 1) {
                System.out.println(".");
            }
//            rstSQLServer.close();
//            statement.close();
//            if (connection != null) {
            conn.desconectar(rstSQLServer, statement, connection);
//            }
            time_end = System.currentTimeMillis();
        } catch (SQLException ex) {
            movimientos = null;
            System.out.println("Exception:ObtenInfoBD:obtenConceptos()" + ex.getMessage());
        }
        return movimientos;
    }

    public List obtenConceptosSinConsulta(String rfc, String claveContrato, Date primerDiaMes, Date ultimoDiaMes) {
        List movimientos = new ArrayList<>();
        Vector mov = null;
        for (int i = 0; i < 2; i++) {
            mov = new Vector();
            mov.add("2017-01-01 00:00:00.000");
            Double d = Double.parseDouble(claveContrato.substring(3, 5));
            mov.add(d);
            movimientos.add(mov);
        }
        return movimientos;
    }

    public InfoEmpleadoMes obtenInfoEmpleado(String rfc, String claveContrato, Date primerDiaMes, Date ultimoDiaMes) {
//        clsConexion conn = new clsConexion();
        Connection connection = null;
//        Statement statement = null;
        PreparedStatement statement = null;
        InfoEmpleadoMes infoEmp = new InfoEmpleadoMes();
        EmpresaCliente empresa = new EmpresaCliente();
        long time_start, time_end;
        try {
            time_start = System.currentTimeMillis();
            connection = conn.conectar();
//            statement = connection.createStatement();
            ResultSet rstSQLServer = null;
            String query = " select TOP 1 lote.fecha_liquidacion, m.nombre_empleado, m.apellidoP_empleado, "
                    + " m.apellidoM_empleado, m.tipo_movimiento, curp, m.clave_empleado, "
                    + " m.depto_empleado, m.puesto_empleado, m.cuenta_deposito, "
                    + " contratos.rfc as rfcEmpresa, contratos.ent_fed, contratos.clave_contrato "
                    + " from movimientos as m, movimientos_h as lote, contratos "
                    + " where "
                    + " m.nombre_archivo = lote.nombre_archivo "
                    + " and lote.status ='T' "
                    + " and m.clave_contrato = contratos.clave_contrato "
                    + " and m.clave_contrato = lote.clave_contrato"
                    + " and lote.fecha_usuario_autoriza IS NOT NULL "
                    + " and lote.fecha_usuario_autoriza>='" + formatoFecha.format(primerDiaMes) + " 00:00:00.000' "
                    + " and lote.fecha_usuario_autoriza<='" + formatoFecha.format(ultimoDiaMes) + " 23:59:59.999' "
                    + " and m.rfc = '" + rfc + "' "
                    + " and m.clave_contrato='" + claveContrato + "'"
                    + " order by lote.clave_contrato, lote.fecha_liquidacion DESC";
            statement = connection.prepareStatement(query);
//            String query = " EXEC infoEmpleados ?,?,?,? ";
//            statement = connection.prepareStatement(query);
//            statement.setString(1, claveContrato);
//            statement.setString(2, rfc);
//            statement.setString(3, formatoFecha.format(primerDiaMes) + " 00:00:00.000");
//            statement.setString(4, formatoFecha.format(ultimoDiaMes) + " 23:59:59.999");
            rstSQLServer = statement.executeQuery();
            if (rstSQLServer.next()) {
                infoEmp.setNombre(rstSQLServer.getString("nombre_empleado"));
                infoEmp.setApellidoPaterno(rstSQLServer.getString("apellidoP_empleado"));
                infoEmp.setApellidoMaterno(rstSQLServer.getString("apellidoM_empleado"));
                infoEmp.setTipoMovimiento(rstSQLServer.getString("tipo_movimiento"));
                infoEmp.setCurp(rstSQLServer.getString("curp"));
                infoEmp.setClaveEmpleado(rstSQLServer.getString("clave_empleado"));
                infoEmp.setDepartamento(rstSQLServer.getString("depto_empleado"));
                infoEmp.setPuesto(rstSQLServer.getString("puesto_empleado"));
                if (infoEmp.getTipoMovimiento().equals("4") || infoEmp.getTipoMovimiento().equals("5")) {
                    infoEmp.setCuentaCLABE("0");
                } else {
                    infoEmp.setCuentaCLABE(rstSQLServer.getString("cuenta_deposito"));
                }
                empresa.setRfc(rstSQLServer.getString("rfcEmpresa"));
                empresa.setEntFed(rstSQLServer.getString("ent_fed"));
                empresa.setClaveContrato(rstSQLServer.getString("clave_contrato"));
                infoEmp.setEmpresa(empresa);
            } else {
                System.out.println("****obtenInfoEmpleado():No encontro resultado:" + claveContrato + "-" + rfc);
            }
//            rstSQLServer.close();
//            statement.close();
//            if (connection != null) {
            conn.desconectar(rstSQLServer, statement, connection);
//            }
            time_end = System.currentTimeMillis();
        } catch (SQLException ex) {
            infoEmp = null;
            System.out.println("Exception:ObtenInfoBD:obtenInfoEmpleado()" + ex.getMessage());
        }
        return infoEmp;
    }

    public InfoEmpleadoMes obtenInfoEmpYconceptos(String rfc, String claveContrato, Date primerDiaMes, Date ultimoDiaMes) {
        Connection connection;
        PreparedStatement statement;
        InfoEmpleadoMes infoEmp = new InfoEmpleadoMes();
        EmpresaCliente empresa = new EmpresaCliente();
        List<Movimiento> movimientos = new ArrayList<>();
        try {
            connection = conn.conectar();
            ResultSet rstSQLServer;
            String query = " select lote.fecha_liquidacion, movimientos.nombre_empleado, "
                    + " CAST(importe_liquidacion as float) as importe_pagado, "
                    + " CAST(importe_liquidacion_mxp as float) as importe_pagado_mxp, "
                    + " movimientos.apellidoP_empleado, "
                    + " movimientos.apellidoM_empleado, movimientos.tipo_movimiento, movimientos.curp, movimientos.clave_empleado, "
                    + " movimientos.depto_empleado, movimientos.puesto_empleado, movimientos.cuenta_deposito, "
                    + " contratos.rfc as rfcEmpresa, contratos.ent_fed, contratos.clave_contrato "
                    + " from movimientos, movimientos_h as lote, contratos "
                    + " where "
                    + " movimientos.nombre_archivo = lote.nombre_archivo "
                    + " and lote.status ='T' "
                    + " and movimientos.clave_contrato = contratos.clave_contrato "
                    + " and movimientos.clave_contrato = lote.clave_contrato"
                    + " and lote.fecha_usuario_autoriza IS NOT NULL "
                    + " and lote.fecha_usuario_autoriza>='" + formatoFecha.format(primerDiaMes) + " 00:00:00.000' "
                    + " and lote.fecha_usuario_autoriza<='" + formatoFecha.format(ultimoDiaMes) + " 23:59:59.999' "
                    + " and movimientos.rfc = '" + rfc + "' "
                    + " and movimientos.clave_contrato='" + claveContrato + "'"
                    + " order by lote.fecha_liquidacion DESC";
            statement = connection.prepareStatement(query);
            rstSQLServer = statement.executeQuery();
            int i = 0;
            while (rstSQLServer.next()) {
                if (i == 0) {
                    infoEmp.setNombre(rstSQLServer.getString("nombre_empleado"));
                    infoEmp.setApellidoPaterno(rstSQLServer.getString("apellidoP_empleado"));
                    infoEmp.setApellidoMaterno(rstSQLServer.getString("apellidoM_empleado"));
                    infoEmp.setTipoMovimiento(rstSQLServer.getString("tipo_movimiento"));
                    infoEmp.setCurp(rstSQLServer.getString("curp"));
                    infoEmp.setClaveEmpleado(rstSQLServer.getString("clave_empleado"));
                    infoEmp.setDepartamento(rstSQLServer.getString("depto_empleado"));
                    infoEmp.setPuesto(rstSQLServer.getString("puesto_empleado"));
                    if (infoEmp.getTipoMovimiento().equals("4") || infoEmp.getTipoMovimiento().equals("5")) {
                        infoEmp.setCuentaCLABE("0");
                    } else {
                        infoEmp.setCuentaCLABE(rstSQLServer.getString("cuenta_deposito"));
                    }
                    empresa.setRfc(rstSQLServer.getString("rfcEmpresa"));
                    empresa.setEntFed(rstSQLServer.getString("ent_fed"));
                    empresa.setClaveContrato(rstSQLServer.getString("clave_contrato"));
                    infoEmp.setEmpresa(empresa);
                }
                Movimiento mov = new Movimiento();
                mov.setFecha_liquidacion(rstSQLServer.getString("fecha_liquidacion"));
                mov.setImporte_liquidacion(rstSQLServer.getDouble("importe_pagado"));
                mov.setImporte_liquidacion_mxp(rstSQLServer.getDouble("importe_pagado_mxp"));
                mov.setTipo_movimiento(rstSQLServer.getString("tipo_movimiento"));
                movimientos.add(mov);
                i++;
            }
            infoEmp.setMovimientos(movimientos);
            conn.desconectar(rstSQLServer, statement, connection);
        } catch (SQLException ex) {
            infoEmp = null;
            System.out.println("Exception:ObtenInfoBD:obtenInfoEmpleado()" + ex.getMessage());
        }
        return infoEmp;
    }

    public String obtenClaveOtroPago(String fechaLiquidacion, String tipoMovimiento) {
        String claveGenerada = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat sdfOut = new SimpleDateFormat("yyyyMMdd");
        try {

            Date date = sdf.parse(fechaLiquidacion);
            claveGenerada = "RL" + sdfOut.format(date);
            if (tipoMovimiento.equals("4")) {
                claveGenerada += "CH";
            } else {
                claveGenerada += "TF";
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return claveGenerada;
    }

    public static String obtenBanco(String cuentaClabe) {
        String banco = "";
        if (cuentaClabe != null && !cuentaClabe.isEmpty()) {
            switch (cuentaClabe.length()) {
                case 18:
//                    banco = cuentaClabe.substring(0, 3);
                    break;
                case 16:
                    banco = "002";
                    break;
                case 10:
                    banco = "012";
                    break;
                default:
                    break;
            }
        }
        return banco;
    }

    public static String obtenCuenta(String cuentaClabe) {
        String cuenta = "";
        if (cuentaClabe != null && !cuentaClabe.isEmpty() && !cuentaClabe.equals("0")) {
            return cuentaClabe;
        }
        return cuenta;
    }

    public List obtenConceptosTest(String rfc, String claveContrato, Date primerDiaMes, Date ultimoDiaMes) {
        List movimientos = new ArrayList<>();
        Vector mov = null;
        try {
            int i = 0;
            while (i < 5000) {
                mov = new Vector();
                mov.add("2017-01-30");
                mov.add("" + (i + 1000));
                movimientos.add(mov);
                i += 4000;
            }
        } catch (Exception ex) {
            movimientos = null;
            System.out.println("Exception:ObtenInfoBD:obtenConceptos()" + ex.getMessage());
        }
        return movimientos;
    }

}
