package mx.org.gp.nomina12.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbcp.BasicDataSource;

public class Conexion {

    private final String user, password, driver, url;
    BasicDataSource basicDataSource = new BasicDataSource();

    public Conexion() {
        this.user = "AdminGDS";
        this.password = "Garante2018*";
        this.driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        this.url = "jdbc:sqlserver://localhost:1433;databaseName=garante;";
        basicDataSource.setDriverClassName(driver);
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        basicDataSource.setMaxActive(50);
        basicDataSource.setInitialSize(50);
//        basicDataSource.setMaxOpenPreparedStatements(100);
    }

    public Connection conectar() {
//        Connection conn = null;
//        try {
////            Class.forName(driver);
//            conn = DriverManager.getConnection(url, user, password);
//        } catch (SQLException ex) {
//            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
//        }
        Connection conn = null;
        try {
            conn = basicDataSource.getConnection();
            // realización de la consulta
        } catch (Exception e) {
            // tratamiento de error
            System.out.println("Error:" + e.getMessage());
        }
        return conn;
    }

    public void desconectar(ResultSet res, PreparedStatement declaracion, Connection conn) {
        try {
            res.close();
            declaracion.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("SQLExcpetion:" + ex.getMessage());
        } catch (Exception e) {
            System.out.println("Excpetion:" + e.getMessage());
        }
    }

}
