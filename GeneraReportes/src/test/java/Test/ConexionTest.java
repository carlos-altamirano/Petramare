package Test;

import com.reports.generareportes.DAO.Consultas;
import com.reports.generareportes.Modelos.Contrato;
import com.reports.generareportes.Modelos.Movimiento;
import java.sql.SQLException;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConexionTest {
    
    //@Test
    public void contatosTest() throws SQLException {
        Consultas consultas = new Consultas();
        List<Contrato> contratos = consultas.getContratos("sofom");
        assertNotEquals(0, contratos.size());
    }
    
    //@Test
    public void movsTest() {
        Consultas consultas = new Consultas();
        List<Movimiento> movimientos = consultas.consultaMovs("garante", "2017-06-01", "2017-06-30");
        assertNotEquals(0, movimientos.size());
    }
    
}
