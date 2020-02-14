package mx.garante.creaxml.DAOs;

import mx.garante.creaxml.Helpers.Conexion;
import mx.garante.creaxml.Models.EdoCta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenericEdoCtaDAO extends Conexion {

    public List<EdoCta> getEdoCta(String clave_contrato, String fecha1, String fecha2) {
        
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        
        List<EdoCta> edoCtas = new ArrayList<>();
        
        Connection con = this.conecta();
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        try {
            String sql = "select * from EC_" + clave_contrato + " "
                    + "where "
                    + "fecha >= ? "
                    + "and fecha <= ? "
                    + "order by fecha";
            statement = con.prepareStatement(sql);
//            statement.setString(1, fecha1);
//            statement.setString(2, fecha2);
            statement.setDate(1, new java.sql.Date(new Date().getTime()));//format.parse(fecha1).getTime())); 
            statement.setDate(2, new java.sql.Date(new Date().getTime()));//format.parse(fecha2).getTime())); 
            rs = statement.executeQuery();

            while (rs.next()) {
                EdoCta edoCta = new EdoCta();
                edoCta.setFecha(rs.getDate("fecha"));
                edoCta.setConcepto(rs.getString("concepto"));
                edoCta.setObservaciones(rs.getString("observaciones"));
                edoCta.setCargo(rs.getDouble("cargo"));
                edoCta.setAbono(rs.getDouble("abono"));
                edoCta.setSaldo(rs.getDouble("saldo"));
                edoCta.setUsuario_genera(rs.getString("usuario_genera"));
                edoCta.setNombre_archivo(rs.getString("nombre_archivo"));
                edoCtas.add(edoCta);
            }
            
        } catch (SQLException ex) {//| ParseException ex) {
            Logger.getLogger(GenericEdoCtaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconecta(con, rs, statement);
        }
        
        return edoCtas;
    }
    
}
