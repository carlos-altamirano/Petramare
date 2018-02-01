package Modelos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {

    public Validaciones() {
    }

    public boolean validaCLABE(String campo) {
        boolean valido = false;
        try {
            if (!campo.isEmpty()) {
                campo = campo.trim();
                campo = campo.toUpperCase();
                //verificamos si se trata de una persona fisica
                //expresion regular para verificacion de RFC-persona fisica
                String patron = "[0-9]{18}";
                Pattern p = Pattern.compile(patron);
                Matcher m = p.matcher(campo);
                if (m.matches()) {
                    if (campo.equals(m.group())) {
                        valido = true;
                    }
                }
                if (valido) {
                    Integer sumatoria = 0;
                    for (int pos = 0; pos < 18; pos += 3) {
                        Integer p1 = new Integer(campo.charAt(pos) + "");
                        Integer r1 = (p1 * 3);
                        sumatoria += r1 % 10;
                        Integer p2 = new Integer(campo.charAt(pos + 1) + "");
                        Integer r2 = (p2 * 7);
                        sumatoria += r2 % 10;
                        if (pos != 15) {
                            Integer p3 = new Integer(campo.charAt(pos + 2) + "");
                            Integer r3 = (p3 * 1);
                            sumatoria += r3 % 10;
                        }
                    }
                    sumatoria = sumatoria % 10;
                    sumatoria = 10 - sumatoria;
                    sumatoria = sumatoria % 10;
                    return (new Integer(campo.charAt(17) + "").equals(sumatoria));
                } else {
                    System.out.println("La CLABE debe de ser de 18 caracteres y sólo pueden ser números.");
                }
            } else {
                System.out.println("Error:El campo está vacio.");
            }
        } catch (Exception exc) {
            System.out.println("Exception:ModeloLayOut-validaCURPCodes:" + exc.getMessage());
            valido = false;
        }

        return valido;
    }
}
