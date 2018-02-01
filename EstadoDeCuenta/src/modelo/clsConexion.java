package modelo;

import java.sql.*;

public class clsConexion {

    public clsConexion() {
    }

    public Connection ConectaSQLServer() throws SQLException {
        Connection lcnnConexion = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");        
            lcnnConexion = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=garante;", "AdminGDS", "Garante2018*");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
        }
        return lcnnConexion;
    }

    public boolean Desconecta(Connection lcnnConexion) {
        try {
            lcnnConexion.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
