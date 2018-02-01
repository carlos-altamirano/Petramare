package Common;

import java.sql.*;

public class clsConexion {

    public clsConexion() {
    }

    public Connection ConectaSQLServer() {
        Connection lcnnConexion = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            lcnnConexion = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=garante;", "AdminGDS", "Garante2018*");
        } catch (SQLException sqlE) {
            System.out.println("SQLException-clsConexion" + sqlE.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Exception-clsConexion:" + e.getMessage());
        } finally {
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
