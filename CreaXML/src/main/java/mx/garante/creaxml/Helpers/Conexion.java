package mx.garante.creaxml.Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    protected Connection conecta() {
        Connection conexion = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            conexion = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=garante;", "AdminGDS", "Garante2018*");
            conexion = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=garante;", "SA", "nrivApet3FiiK4k9");
            } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    }
    
    protected void desconecta(Connection conexion, ResultSet resultSet, PreparedStatement preparedStatement) {
        try {
            if (conexion != null) {
                conexion.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
