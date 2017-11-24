/*
 *    Creado por:                   Luis Antio Valerio Gayosso
 *    Fecha:                        21/06/2011
 *    Descripción:                  Bean : "Usuario.java" Almacena los mensajes de respuesta del usuario
 *    Responsable:                  Fernando Cardenas
 */
package Beans;

public class Message {

    private int errNum = 0;
    private String type = "";
    private String field = "";
    private String desc = "";

    public Message() {
    }

    public Message(int errNum, String type, String field, String desc) {
        this.errNum = errNum;
        this.type = type;
        this.field = field;
        this.desc = desc;
    }

    /**
     * @return Returns the desc.
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc The desc to set.
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return Returns the errNum.
     */
    public int getErrNum() {
        return errNum;
    }

    /**
     * @param errNum The errNum to set.
     */
    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    /**
     * @return Returns the field.
     */
    public String getField() {
        return field;
    }

    /**
     * @param field The field to set.
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }
}
