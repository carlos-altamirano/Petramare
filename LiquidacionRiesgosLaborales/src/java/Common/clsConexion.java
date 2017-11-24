/*
 *    Creado por:                   Luis Antio Valerio Gayosso
 *    Fecha:                        21/06/2011
 *    Descripción:                  Conexión : "clsConexion.java" Conecta y desconecta a la BD
 *    Responsable:                  Carlos Altamirano
 */
package Common;

import java.sql.*;
/*
 * Clase que proporciona los metodos necesarios
 * para conectar y desconectar a la base de datos sofom
 */

public class clsConexion {

    /**
     * Creates a new instance of clsConexion
     */
    public clsConexion() {
    }

    public Connection ConectaSQLServer() {
        Connection lcnnConexion = null;
        String classForName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        try {
            Class.forName(classForName);
            String connectionUrl = "jdbc:sqlserver://localhost:1433;"
//                              CONEXION EN SERVIDOR ANT EN BD ANIMATE
                    +"databaseName=garante;user=sa;password=root;";
                    //          CONEXION EN LOCALHOST ANIMATE
//                    +"databaseName=garante;user=garantePruebas;password=Garante#2016;";
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
     * Metodo para desconectar una conexion
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
