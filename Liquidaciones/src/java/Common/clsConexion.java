/*
 *    Author     : Luis Antio Valerio Gayosso
 *    Fecha:                        23/02/2011
 *    Descripción:                  Controlador : "clsConexion.java" Conecta y Desconecta a la Base de Datos.
 *    Responsable:                  Carlos Altamirano
 */
package Common;

import java.sql.*;

/*
 * Clase que proporciona los métodos necesarios
 * para conectar y desconectar a una Base de Datos.
 */
public class clsConexion {

    /**
     * Creates a new instance of clsConexion
     */
    public clsConexion() {
    }

    public Connection ConectaSQLServer() throws SQLException {
        Connection lcnnConexion = null;
        String classForName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        try {
            Class.forName(classForName);
            String connectionUrl = "jdbc:sqlserver://localhost:1433;"
                    //           CONEXION EN SERVIDOR GDS de garante
                    +"databaseName=garante;user=sa;password=root;";
                    //          CONEXION EN SERVIDOR DE PRUEBAS
//                      +"databaseName=garante;user=garantePruebas;password=Garante#2016;";
            lcnnConexion = DriverManager.getConnection(connectionUrl);
        } catch (SQLException sqlE) {
            System.out.println("SQLException-clsConexion" + sqlE.getMessage());
        } catch (Exception e) {
            System.out.println("Exception-clsConexion:" + e.getMessage());
        } finally {
        }
        return lcnnConexion;
    }

    /**
     * Método para desconectar una conexión existente
     */
    public boolean Desconecta(Connection lcnnConexion) {
        try {
            lcnnConexion.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
