//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.10.31 at 03:26:54 PM CST 
//


package mx.sat.cfdi;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for c_TipoNomina.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="c_TipoNomina"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;whiteSpace value="collapse"/&gt;
 *     &lt;enumeration value="O"/&gt;
 *     &lt;enumeration value="E"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "c_TipoNomina", namespace = "http://www.sat.gob.mx/sitio_internet/cfd/catalogos/Nomina")
@XmlEnum
public enum CTipoNomina {

    O,
    E;

    public String value() {
        return name();
    }

    public static CTipoNomina fromValue(String v) {
        return valueOf(v);
    }

}
