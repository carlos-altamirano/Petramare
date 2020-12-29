package mx.garante.liquidaciones.Beans;

import com.google.gson.Gson;

public class Empleado {

    private Integer id;
    private String nombre;
    private String appat;
    private String apmat;
    private String rfc;
    private String curp;
    private String contra;
    private String email;
    private String estatus;
    boolean autentificado = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAppat() {
        return appat;
    }

    public void setAppat(String appat) {
        this.appat = appat;
    }

    public String getApmat() {
        return apmat;
    }

    public void setApmat(String apmat) {
        this.apmat = apmat;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public boolean isAutentificado() {
        return autentificado;
    }
    public void setAutentificado(boolean autentificado) {
        this.autentificado = autentificado;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        Empleado emp = new Empleado();
        emp.setId(id);
        emp.setNombre(nombre);
        emp.setAppat(appat);
        emp.setApmat(apmat);
        emp.setCurp(curp);
        emp.setContra(contra);
        emp.setEstatus(estatus);
        emp.setRfc(rfc);
        emp.setEmail(email);

        return gson.toJson(emp);
    }

}
