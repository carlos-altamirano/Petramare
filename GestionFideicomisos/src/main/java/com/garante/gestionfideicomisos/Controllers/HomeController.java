package com.garante.gestionfideicomisos.Controllers;

import com.garante.gestionfideicomisos.DAOs.ContratoDAO;
import com.garante.gestionfideicomisos.DAOs.SociosDAO;
import com.garante.gestionfideicomisos.DAOs.UsuarioAdminDAO;
import com.garante.gestionfideicomisos.DAOs.UsuarioDAO;
import com.garante.gestionfideicomisos.Helpers.Mail.GestionSendMail;
import com.garante.gestionfideicomisos.Models.Contrato;
import com.garante.gestionfideicomisos.Models.Socios;
import com.garante.gestionfideicomisos.Models.Usuario;
import com.garante.gestionfideicomisos.Models.UsuariosAdmin;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        String url = "/Home/index";
        if (request.getSession().getAttribute("sesionUser") != null) {
            String userName = (String) request.getSession().getAttribute("sesionUser");
            //String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            model.addAttribute("userName", userName);
            //if (tipoUser.equals("cliente")) {
                //url = "/Cliente/Movimientos/index";
            //} else {
                url = "/Admin/index";
            //}
        } else {
            //model.addAttribute("tipoForm", "cliente");
            model.addAttribute("tipoForm", "sofom");
        }
        return url;
    }
    
    @RequestMapping(value = "/form/login/sofom", method = RequestMethod.GET)
    public String loginClienteForm(Model model) {
        String url = "/Home/index";
        if (request.getSession().getAttribute("sesionUser") != null) {
            String userName = (String) request.getSession().getAttribute("sesionUser");
            model.addAttribute("userName", userName);
        } else {
            model.addAttribute("tipoForm", "sofom");
        }
        return url;
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginSofom(String usuario, String password, @RequestParam(value = "clave", required = false, defaultValue = "") String clave) {
        String url = "redirect:/";
        if (clave.equals("")) {
            UsuarioAdminDAO usuarioSofomDAO = new UsuarioAdminDAO();
            UsuariosAdmin sofom = usuarioSofomDAO.get(usuario, password);
            if (sofom != null) {
                HttpSession sesion = request.getSession();
                sesion.setAttribute("sesionUser", sofom.getUsuario());
                sesion.setAttribute("tipoUser", "sofom");
            } else {
                url = "redirect:/form/login/sofom?sesion=false";
            }
        } else {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario cliente = usuarioDAO.get(usuario, password, clave);
            if (cliente != null) {
                HttpSession sesion = request.getSession();
                sesion.setAttribute("sesionUser", cliente.getUsuario());
                sesion.setAttribute("tipoUser", "cliente");
            } else {
                url = "redirect:/?sesion=false";
            }
        }
        return url;
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(){
        HttpSession sesion = request.getSession();
        String url;
        String tipoUser = (String) sesion.getAttribute("tipoUser");
        sesion.removeAttribute("sesionUser");
        sesion.removeAttribute("tipoUser");
        if (tipoUser.equals("cliente")) {
            url = "redirect:/?sesion=true";
        } else {
            url = "redirect:/form/login/sofom/?sesion=true";
        }
        return url;
    }
    
    @RequestMapping(value = "/permisos", method = RequestMethod.GET)
    public String permisos(Model model) {
        String userName = (String) request.getSession().getAttribute("sesionUser");
        model.addAttribute("userName", userName);
        return "/Home/error-403";
    }

    @RequestMapping(value = "/perfil/password", method = RequestMethod.GET)
    public String perfilPassword(Model model) {
        String userName = (String) request.getSession().getAttribute("sesionUser");
        model.addAttribute("userName", userName);
        return "/Home/Perfil/Nuevacontrasena";
    }
    
    @RequestMapping(value = "/perfil/password/edit", method = RequestMethod.POST)
    public String perfilPasswordCambia(String vieja, String nueva1, String nueva2) {
        String url;
        HttpSession sesion = request.getSession();
        String userName = (String) sesion.getAttribute("sesionUser");
        String tipoUser = (String) sesion.getAttribute("tipoUser");
        
        if (nueva1.equals(nueva2)) {
            if (tipoUser.equals("cliente")) {
                url = "redirect:/perfil/password?cambio=error";
            } else {
                UsuarioAdminDAO sofomDAO = new UsuarioAdminDAO();
                final UsuariosAdmin admin = sofomDAO.get(userName, vieja);
                if (admin == null) {
                    url = "redirect:/perfil/password?cambio=error";
                } else {
                    admin.setPassword(nueva2);
                    if (sofomDAO.update(admin)) {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                GestionSendMail mail = new GestionSendMail(GestionSendMail.Tipos_cuerpo_HTML.GENERA_CONTRASENA, admin.getCorreo());
                                mail.setCuerpoNuevaContrasena(admin.getNombreUsuario(), admin.getUsuario(), admin.getPassword());
                                if (!mail.enviar()) {
                                    try {
                                        Thread.sleep(180000);
                                        mail.setCuerpoNuevaContrasena(admin.getNombreUsuario(), admin.getUsuario(), admin.getPassword());
                                        mail.enviar();
                                    } catch (InterruptedException ex) {
                                        System.out.println("Error hilo envio segundo correo "+ ex);
                                    }
                                }
                            }
                        });
                        thread.start();
                        url = "redirect:/perfil/password?cambio=ok";
                    } else {
                        url = "redirect:/perfil/password?cambio=error";
                    }
                }
            }
        } else {
            url = "redirect:/perfil/password?cambio=error";
        }
        return url;
    }
    
    @ResponseBody
    @RequestMapping(value = "/verifica/contrato", method = RequestMethod.POST)
    public String verificaContrato(String claveContrato) {
        ContratoDAO contratoDAO = new ContratoDAO();
        Contrato contrato = contratoDAO.get(claveContrato);
        SociosDAO sociosDAO = new SociosDAO();
        List<Socios> socios = sociosDAO.getAll(claveContrato);
        String res = "{ \"nombre\":\""+contrato.getNombreCliente()+"\", \"pld\": "+(contrato.getPld()?1:0);
        res += ", \"socios\":[";
        if(socios.size()>0) {
            for (Socios socio : socios) {
                res += "{\"nombre\":\""+socio.getNombre()+"\"},";
            }
            res = res.substring(0, res.length()-1);
        }
        res += "]}";
        return res;
    }
    
    @ResponseBody
    @RequestMapping(value = "/bloquea/contrato", method = RequestMethod.POST)
    public String bloqueaContrato(String claveContrato) {
        final String contratoFid = claveContrato;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ContratoDAO contratoDAO = new ContratoDAO();
                Contrato contrato = contratoDAO.get(contratoFid);
                contrato.setStatus("B");
                contratoDAO.update(contrato);
                GestionSendMail mail = new GestionSendMail(GestionSendMail.Tipos_cuerpo_HTML.OPERACIONESPLD, "contacto@garante.mx");
                mail.setCuerpoOperacionBloqueadaPLD(contratoFid);
                if (!mail.enviar()) {
                    try {
                        Thread.sleep(180000);
                        mail.enviar();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        thread.start();
        return "ok";
    }
    
}
