package com.garante.gestionfideicomisos.Controllers;

import com.garante.gestionfideicomisos.Models.Movimiento;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "cte")
public class ClienteController {
    
    @Autowired
    private HttpServletRequest request;
    
    protected void imprimeUsuario(Model model) {
        String userName = (String) request.getSession().getAttribute("sesionUser");
        model.addAttribute("userName", userName);
    }
    
    @RequestMapping(value = "/liquidacion/manual", method = RequestMethod.GET)
    public String liquidacionManual(Model model) {
        String url;
        if (request.getSession().getAttribute("sesionUser") != null) {
            String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            if (tipoUser.equals("cliente")) {
                imprimeUsuario(model);
                Movimiento movimiento = new Movimiento();
                model.addAttribute("movimiento", movimiento);
                model.addAttribute("monedas", new ArrayList<>());
                model.addAttribute("bancos", new ArrayList<>());
                url = "/Cliente/Movimientos/Manual";
            } else {
                url = "redirect:/permisos";
            }
        } else {
            url = "redirect:/?sesion=error";
        }
        return url;
    }
    
}
