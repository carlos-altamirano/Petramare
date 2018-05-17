package mx.garante.creaxml;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
import javax.xml.XMLConstants;

public class NameSpaceMapper extends NamespacePrefixMapper {

    private final String cfdi = "http://www.sat.gob.mx/cfd/3";
    private final String tfd = "http://www.sat.gob.mx/TimbreFiscalDigital";
    private final String edocta = "https://www.gp.com.mx/cfd/addenda/edoctasme";
    private final String nomina12 = "http://www.sat.gob.mx/nomina12";

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[]{
            XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI
        };
    }

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        String res;
        switch (namespaceUri) {
            case cfdi:
                res = "cfdi";
                break;
            case tfd:
                res = "tfd";
                break;
            case edocta: 
                res = "edocta";
                break;
            case nomina12:
                res = "nomina12";
                break;
            case XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI:
                res = "xsi";
                break;
            default:
                res = suggestion;
                break;
        }
        return res;
    }

}
