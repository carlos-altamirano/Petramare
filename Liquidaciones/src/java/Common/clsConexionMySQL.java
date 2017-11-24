/*
 *    Author     : Luis Antio Valerio Gayosso
 *    Responsable:                  Carlos Altamirano
 */

package Common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Clase que proporciona los métodos necesarios
 * para conectar y desconectar a una Base de Datos.
 */
public class clsConexionMySQL{

    /** Creates a new instance of clsConexion */
    public clsConexionMySQL() {
    }

    /**Método para obtener una conexión a la base de datos mediante un ODBC*/
    public Connection ConectaMySQL() throws SQLException {
        Connection lcnnConexion;
        String login = "fideicomiso"; //Conexión Remota
//        String password = "FIDEICOMISO2011X"; //Conexión Remota
        String password = ""; //Conexión Remota
        String classForName = "com.mysql.jdbc.Driver";
//        String url = "jdbc:odbc:FAC"; //Conexión Local"
       String url = "jdbc:odbc:Prometeo"; //Conexión Remota
        try {
            Class.forName(classForName);
//            lcnnConexion = DriverManager.getConnection(url); //Conexión Local
            lcnnConexion = DriverManager.getConnection(url, login, password); //Conexión Remota
        } catch (Exception e) {
            System.out.println(e.toString());
            lcnnConexion = null;
        }
        return lcnnConexion;
    }

    /**Método para desconectar una conexión existente*/
    public boolean Desconecta(Connection lcnnConexion) {
        try {
            lcnnConexion.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}