package com.garante.gestionfideicomisos.DAOs;

import com.garante.gestionfideicomisos.Helpers.Conexion;
import com.garante.gestionfideicomisos.Helpers.Fechas;
import com.garante.gestionfideicomisos.Models.Contrato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContratoDAO  extends Conexion {
    
    /**
    * Realiza una busqueda de la tabla contratos atravez del campo clave
    * @param claveContrato String not null
    * @return Entidad Contrato
    */
    public Contrato get(String claveContrato) {
        Contrato contrato = null;
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "select * from contratos where clave_contrato = ?";
         try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, claveContrato);
            res = declaracion.executeQuery();
            
            if (res.next()){
                contrato = new Contrato();
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
                contrato.setPld(res.getBoolean("pld"));
                contrato.setTipoPersona(res.getInt("tipoPersona"));
                contrato.setCurp(res.getString("curp"));
            }
            
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(ContratoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(res, declaracion, conn);
        }
        return contrato;
    }
    
    public List<Contrato> getAll() {
        List<Contrato> contratos = new ArrayList<>();
        Connection conn = this.conectar();
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
                contrato.setFechaCaptura(res.getDate("fecha_captura"));
                contrato.setStatus(res.getString("status"));
                contrato.setSaldo(res.getDouble("saldo"));
                contrato.setIdCodes(res.getString("id_codes"));
                contrato.setEntFed(res.getString("ent_fed"));
                contrato.setCodPos(res.getString("cod_pos"));
                contrato.setPld(res.getBoolean("pld"));
                contrato.setTipoPersona(res.getInt("tipoPersona"));
                contrato.setCurp(res.getString("curp"));
                contratos.add(contrato);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ContratoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(res, declaracion, conn);
        }
        return contratos;
    }
    
    public List<Contrato> getAll(String pagina) {
        List<Contrato> contratos = new ArrayList<>();
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "select * from contratos order by clave_contrato asc offset "+pagina+"0 rows fetch next 10 rows only;";
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
                contrato.setFechaCaptura(res.getDate("fecha_captura"));
                contrato.setStatus(res.getString("status"));
                contrato.setSaldo(res.getDouble("saldo"));
                contrato.setIdCodes(res.getString("id_codes"));
                contrato.setEntFed(res.getString("ent_fed"));
                contrato.setCodPos(res.getString("cod_pos"));
                contrato.setPld(res.getBoolean("pld"));
                contrato.setTipoPersona(res.getInt("tipoPersona"));
                contrato.setCurp(res.getString("curp"));
                contratos.add(contrato);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ContratoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(res, declaracion, conn);
        }
        return contratos;
    }
    
    public Integer countContratos() {
        Integer contratos = 0;
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        ResultSet res = null;
        String sql = "select count(*) from contratos";
         try {
            declaracion = conn.prepareStatement(sql);
            res = declaracion.executeQuery();
            
            if (res.next()){
                contratos = res.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ContratoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(res, declaracion, conn);
        }
        return contratos;
    }
    
    public Integer save(Contrato contrato) {
        Integer r = -1;
        
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        String sql = "insert into contratos values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, contrato.getClaveContrato());
            declaracion.setString(2, contrato.getNombreCliente());
            declaracion.setString(3, contrato.getCuentaOrigen());
            declaracion.setString(4, contrato.getGrupo());
            declaracion.setString(5, contrato.getDomicilioFiscal());
            declaracion.setString(6, contrato.getRfc());
            declaracion.setString(7, contrato.getTelefono());
            declaracion.setString(8, contrato.getCorreo());
            declaracion.setString(9, contrato.getTipoHonorario());
            declaracion.setDouble(10, contrato.getHonorarioSinIva());
            declaracion.setString(11, contrato.getOficinas());
            declaracion.setString(12, Fechas.creaFechaString(contrato.getFechaCaptura()));
            declaracion.setString(13, contrato.getStatus());
            declaracion.setDouble(14, contrato.getSaldo());
            declaracion.setString(15, contrato.getIdCodes());
            declaracion.setString(16, contrato.getEntFed());
            declaracion.setString(17, contrato.getCodPos());
            declaracion.setBoolean(18, contrato.getPld());
            declaracion.setInt(19, contrato.getTipoPersona());
            declaracion.setString(20, contrato.getCurp());
            r = declaracion.executeUpdate();
            if (r != -1) {
                String sql2 = "CREATE TABLE EC_"+contrato.getClaveContrato()+"("+
                            " fecha DATETIME NOT NULL, " +
                            " concepto VARCHAR(40) NOT NULL, " +
                            " observaciones VARCHAR(50) DEFAULT '', " +
                            " cargo DECIMAL(13,2) NOT NULL DEFAULT 0.0, " +
                            " abono DECIMAL(13,2) NOT NULL DEFAULT 0.0, " +
                            " saldo DECIMAL(13,2) NOT NULL, " +
                            " usuario_genera VARCHAR(50) NOT NULL DEFAULT 'DEFAULT', " +
                            " nombre_archivo VARCHAR(100) NOT NULL DEFAULT ''" + 
                        ") ";
                declaracion = conn.prepareStatement(sql2);
                declaracion.executeUpdate();
                
                String sql3 = "insert into documentacion (clave_contrato) values(?)";
                declaracion = conn.prepareStatement(sql3);
                declaracion.setString(1, contrato.getClaveContrato());
                declaracion.executeUpdate();
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContratoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(null, declaracion, conn);
        }
        
        return r;
    }

    public Boolean update(Contrato contrato) {
        Boolean r = false;
        
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        String sql = "update contratos set nombre_cliente = ?, cuenta_origen = ?, grupo = ?, domicilio_fiscal = ?,"
                + " RFC = ?, telefono = ?, correo = ?, tipo_honorario = ?, honorario_sin_iva = ?, oficinas = ?,"
                + " fecha_captura = ?, status = ?, saldo = ?, id_codes = ?, ent_fed = ?, cod_pos = ?,"
                + " pld = ?, tipoPersona = ?, curp = ? where clave_contrato = ?";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, contrato.getNombreCliente());
            declaracion.setString(2, contrato.getCuentaOrigen());
            declaracion.setString(3, contrato.getGrupo());
            declaracion.setString(4, contrato.getDomicilioFiscal());
            declaracion.setString(5, contrato.getRfc());
            declaracion.setString(6, contrato.getTelefono());
            declaracion.setString(7, contrato.getCorreo());
            declaracion.setString(8, contrato.getTipoHonorario());
            declaracion.setDouble(9, contrato.getHonorarioSinIva());
            declaracion.setString(10, contrato.getOficinas());
            declaracion.setString(11, Fechas.creaFechaString(contrato.getFechaCaptura()));
            declaracion.setString(12, contrato.getStatus());
            declaracion.setDouble(13, contrato.getSaldo());
            declaracion.setString(14, contrato.getIdCodes());
            declaracion.setString(15, contrato.getEntFed());
            declaracion.setString(16, contrato.getCodPos());
            declaracion.setBoolean(17, contrato.getPld());
            declaracion.setInt(18, contrato.getTipoPersona());
            declaracion.setString(19, contrato.getCurp());
            declaracion.setString(20, contrato.getClaveContrato());
            declaracion.executeUpdate();
            r = true;
        } catch (SQLException ex) {
            Logger.getLogger(ContratoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(null, declaracion, conn);
        }
        return r;
    }

    public Boolean delete(Contrato contrato) {
        Boolean r = false;
        
        Connection conn = this.conectar();
        PreparedStatement declaracion = null;
        String sql = "delete from contratos where clave_contrato = ?";
        try {
            declaracion = conn.prepareStatement(sql);
            declaracion.setString(1, contrato.getClaveContrato());
            declaracion.executeUpdate();
            r = true;
        } catch (SQLException ex) {
            Logger.getLogger(ContratoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar(null, declaracion, conn);
        }
        return r;
    }
    
}
