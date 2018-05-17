package mx.garante.liquidaciones.Modelos;

import mx.garante.liquidaciones.Beans.Empleado;
import mx.garante.liquidaciones.Common.EnvioMail;
import mx.garante.liquidaciones.Common.clsConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModeloEmpleado {

    public void insertORupdate(List<Empleado> empleados) {
        List<Empleado> errores = empleadosBD(empleados);

        if (errores.size() > 0) {

            String html = "<h4>Hubo un error a la hora de insertar los siguientes empleados en la base de datos:</h4>\n"
                    + "    <table border=\"1\">\n"
                    + "        <thead>\n"
                    + "            <tr>\n"
                    + "                <th>Nombre</th>\n"
                    + "                <th>Apellido Paterno</th>\n"
                    + "                <th>Apellido Materno</th>\n"
                    + "                <th>RFC</th>\n"
                    + "                <th>CURP</th>\n"
                    + "            </tr>\n"
                    + "        </thead>\n"
                    + "        <tdoby>\n";
            for (Empleado emp : errores) {
                html += "<tr>\n";
                html += "<td>" + emp.getNombre() + "</td>\n";
                html += "<td>" + emp.getAppat() + "</td>\n";
                html += "<td>" + emp.getApmat() + "</td>\n";
                html += "<td>" + emp.getRfc() + "</td>\n";
                html += "<td>" + emp.getCurp() + "</td>\n";
                html += "</tr>";
            }
            html += "</tdoby>\n"
                    + "    </table>";

            if (!EnvioMail.enviaCorreo("liquidaciones@fideicomisogds.mx", "soporte@fideicomisogds.mx", "Error al insertar empleados en la base", html, "587")) {
                try {
                    Thread.sleep(180000);
                    EnvioMail.enviaCorreo("liquidaciones@fideicomisogds.mx", "soporte@fideicomisogds.mx", "Error al insertar empleados en la base", html, "587");
                } catch (InterruptedException ex) {
                    System.out.println("Exception Correo:" + ex.getMessage());
                }
            }

        }

    }

    public List<Empleado> empleadosBD(List<Empleado> empleados) {
        List<Empleado> listaErrores = new ArrayList<>();

        if (empleados != null) {
            Connection connection = null;
            PreparedStatement statement = null;
            Empleado empTemporal = null;
            clsConexion conexion = new clsConexion();

            for (Empleado empleado : empleados) {
                try {
                    connection = conexion.ConectaSQLServer();
                    empTemporal = empleado;

                    String busca = "select * from empleados where rfc = ?;";
                    statement = connection.prepareStatement(busca);
                    statement.setString(1, empleado.getRfc());

                    ResultSet rs = statement.executeQuery();

                    if (rs.next()) {
                        String actualiza = "update empleados set nombre = ?, appat = ?, apmat = ?, curp = ? where rfc = ?;";
                        statement = connection.prepareStatement(actualiza);
                        statement.setString(1, empleado.getNombre());
                        statement.setString(2, empleado.getAppat());
                        statement.setString(3, empleado.getApmat());
                        statement.setString(4, empleado.getCurp());
                        statement.setString(5, empleado.getRfc());
                        statement.executeUpdate();

                    } else {
                        String inserta = "insert into empleados values(?, ?, ?, '', '', 'B', ?, ?)";
                        statement = connection.prepareStatement(inserta);
                        statement.setString(1, empleado.getNombre());
                        statement.setString(2, empleado.getAppat());
                        statement.setString(3, empleado.getApmat());
                        statement.setString(4, empleado.getRfc());
                        statement.setString(5, empleado.getCurp());
                        statement.executeUpdate();
                    }

                } catch (Exception ex) {
                    System.out.println("Exception:empleadosBD:" + ex.getMessage());
                    listaErrores.add(empTemporal);
                } finally {
                    try {
                        if (connection != null) {
                            connection.close();
                        }
                        if (statement != null) {
                            statement.close();
                        }
                    } catch (SQLException ex) {
                        System.out.println("Error al cerrar conexion");
                    }
                }
            }
        }

        return listaErrores;
    }

    public Empleado buscaRFC(String rfc) {
        Empleado empleado = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet res = null;

        try {
            clsConexion conexion = new clsConexion();
            connection = conexion.ConectaSQLServer();
            String sql = "select * from empleados where rfc = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, rfc);

            res = statement.executeQuery();

            while (res.next()) {
                empleado = new Empleado();
                empleado.setId(res.getInt(1));
                empleado.setNombre(res.getString(2));
                empleado.setAppat(res.getString(3));
                empleado.setApmat(res.getString(4));
                empleado.setEmail(res.getString(5));
                empleado.setContra(res.getString(6));
                empleado.setEstatus(res.getString(7));
                empleado.setRfc(res.getString(8));
                empleado.setCurp(res.getString(9));
            }

        } catch (SQLException e) {
            System.out.println("Error buscaRFC(String rfc) " + e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (res != null) {
                    res.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar conexion");
            }
        }

        return empleado;
    }

    public Empleado buscaEmpleado(String usuario, String contra) {
        Empleado empleado = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet res = null;

        try {
            clsConexion conexion = new clsConexion();
            connection = conexion.ConectaSQLServer();
            String sql = "select * from empleados where rfc = ? and contra = ? and estatus = 'A'";
            statement = connection.prepareStatement(sql);
            statement.setString(1, usuario);
            statement.setString(2, contra);

            res = statement.executeQuery();

            if (res.next()) {
                empleado = new Empleado();
                empleado.setId(res.getInt(1));
                empleado.setNombre(res.getString(2));
                empleado.setAppat(res.getString(3));
                empleado.setApmat(res.getString(4));
                empleado.setEmail(res.getString(5));
                empleado.setContra(res.getString(6));
                empleado.setEstatus(res.getString(7));
                empleado.setRfc(res.getString(8));
                empleado.setCurp(res.getString(9));
            }

        } catch (SQLException e) {
            System.out.println("Error buscaEmpleado(String usuario, String contra) " + e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (res != null) {
                    res.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar conexion");
            }
        }

        return empleado;
    }

    public boolean activa(Empleado empleado) {
        boolean resG = false;

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            clsConexion conexion = new clsConexion();
            connection = conexion.ConectaSQLServer();
            String sql = "update empleados set email=?, contra=?, estatus='A' where idEmpleado = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, empleado.getEmail());
            statement.setString(2, empleado.getContra());
            statement.setInt(3, empleado.getId());
            statement.executeUpdate();
            resG = true;
        } catch (SQLException e) {
            System.out.println("Error activa(Empleado empleado) " + e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar conexion");
            }
        }

        return resG;
    }

    public boolean cambiaPassword(Empleado empleado) {
        boolean resG = false;

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            clsConexion conexion = new clsConexion();
            connection = conexion.ConectaSQLServer();
            String sql = "update empleados set contra=? where idEmpleado = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, empleado.getContra());
            statement.setInt(2, empleado.getId());
            statement.executeUpdate();
            resG = true;
        } catch (SQLException e) {
            System.out.println("Error activa(Empleado empleado) " + e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar conexion");
            }
        }

        return resG;
    }

    public Empleado busca(int id) {
        Empleado empleado = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet res = null;

        try {
            clsConexion conexion = new clsConexion();
            connection = conexion.ConectaSQLServer();
            String sql = "select * from empleados where idEmpleado = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            res = statement.executeQuery();

            while (res.next()) {
                empleado = new Empleado();
                empleado.setId(res.getInt(1));
                empleado.setNombre(res.getString(2));
                empleado.setAppat(res.getString(3));
                empleado.setApmat(res.getString(4));
                empleado.setEmail(res.getString(5));
                empleado.setContra(res.getString(6));
                empleado.setEstatus(res.getString(7));
                empleado.setRfc(res.getString(8));
                empleado.setCurp(res.getString(9));
            }

        } catch (SQLException e) {
            System.out.println("Error busca(int id) " + e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (res != null) {
                    res.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar conexion");
            }
        }

        return empleado;
    }

}
