package com.garante.gestionfideicomisos.Controllers;

import com.garante.gestionfideicomisos.DAOs.CodigoPostalDAO;
import com.garante.gestionfideicomisos.DAOs.ContratoDAO;
import com.garante.gestionfideicomisos.DAOs.CuentaBancoDAO;
import com.garante.gestionfideicomisos.DAOs.DocumentacionDAO;
import com.garante.gestionfideicomisos.DAOs.LogPLDDAO;
import com.garante.gestionfideicomisos.DAOs.SociosDAO;
import com.garante.gestionfideicomisos.DAOs.UsuarioAdminDAO;
import com.garante.gestionfideicomisos.DAOs.UsuarioDAO;
import com.garante.gestionfideicomisos.Helpers.Log;
import com.garante.gestionfideicomisos.Helpers.Mail.GestionSendMail;
import com.garante.gestionfideicomisos.Helpers.UsuariosHelp;
import com.garante.gestionfideicomisos.Models.CodigoPostal;
import com.garante.gestionfideicomisos.Models.Contrato;
import com.garante.gestionfideicomisos.Models.CuentaBanco;
import com.garante.gestionfideicomisos.Models.Documentacion;
import com.garante.gestionfideicomisos.Models.LogPLD;
import com.garante.gestionfideicomisos.Models.Socios;
import com.garante.gestionfideicomisos.Models.Usuario;
import com.garante.gestionfideicomisos.Models.UsuariosAdmin;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "adm")
public class AdminController {

    @Autowired
    private HttpServletRequest request;

    protected void imprimeUsuario(Model model) {
        String userName = (String) request.getSession().getAttribute("sesionUser");
        model.addAttribute("userName", userName);
    }

    @ResponseBody
    @RequestMapping(value = "/busca/pld", method = RequestMethod.POST)
    public String buscaPld(String nombre, String apellido) {
        Client client = Client.create();
        String datos = "{Apellido:'"+apellido+"', Nombre:'"+nombre+"', Usuario:'garantededesarrolloysalud', Password:'EC8E544F'}";
        WebResource webResource = client.resource("https://www.prevenciondelavado.com/listas/api/busqueda");
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, datos);
        String salida;
        if (response.getStatus() == 200) {
            salida = response.getEntity(String.class);
        } else {
            salida = "{\"Status\":\"error\", \"Message\":\"Error al realizar busqueda de cliente en PLD\"}";
        }
        return salida;
    }

    @RequestMapping(value = "/contratos", method = RequestMethod.GET)
    public String contratos(Model model,
            @RequestParam(value = "pagina", required = false, defaultValue = "0") String pagina) {
        String url;
        if (request.getSession().getAttribute("sesionUser") != null) {
            String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            if (tipoUser.equals("sofom")) {
                imprimeUsuario(model);
                ContratoDAO contratoDAO = new ContratoDAO();
                UsuarioAdminDAO usuarioAdminDAO = new UsuarioAdminDAO();
                UsuariosAdmin admin = usuarioAdminDAO.get((String) request.getSession().getAttribute("sesionUser"));
                model.addAttribute("depa", admin.getDepartamento());
                model.addAttribute("contratos", contratoDAO.getAll(pagina));
                model.addAttribute("countContratos", contratoDAO.countContratos());
                url = "/Admin/Contratos/Contratos";
            } else {
                url = "redirect:/permisos";
            }
        } else {
            url = "redirect:/?sesion=error";
        }
        return url;
    }

    @ResponseBody
    @RequestMapping(value = "/buscaClave", method = RequestMethod.POST)
    public String buscaClave(String clave) {
        String res = "no";
        ContratoDAO contratoDAO = new ContratoDAO();
        Contrato contrato = contratoDAO.get(clave);
        if (contrato != null) {
            res = "si";
        }
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/contrato/{clave}/search", method = RequestMethod.POST)
    public String contratoSearch(@PathVariable String clave, Model model) {
        ContratoDAO contratoDAO = new ContratoDAO();
        Contrato contrato = contratoDAO.get(clave);
        String res = "";
        if (contrato != null) {
            res = contrato.getNombreCliente();
        }
        return res;
    }

    @RequestMapping(value = "/contrato/new", method = RequestMethod.GET)
    public String contrato(Model model) {
        String url;
        if (request.getSession().getAttribute("sesionUser") != null) {
            String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            if (tipoUser.equals("sofom")) {
                imprimeUsuario(model);
                Contrato contrato = new Contrato();
                contrato.setPld(false);
                CuentaBancoDAO cuentaBancoDAO = new CuentaBancoDAO();
                List<CuentaBanco> cuentas = cuentaBancoDAO.getAll();
                model.addAttribute("contrato", contrato);
                model.addAttribute("cuentas", cuentas);
                model.addAttribute("fechaCaptura", new Date());
                UsuarioAdminDAO usuarioAdminDAO = new UsuarioAdminDAO();
                UsuariosAdmin admin = usuarioAdminDAO.get((String) request.getSession().getAttribute("sesionUser"));
                model.addAttribute("depa", admin.getDepartamento());
                model.addAttribute("action", "/gestionfideicomisos/adm/contrato/save");
                url = "/Admin/Contratos/NuevoContrato";
            } else {
                url = "redirect:/permisos";
            }
        } else {
            url = "redirect:/?sesion=error";
        }
        return url;
    }

    @RequestMapping(value = "/contrato/save", method = RequestMethod.POST)
    public String contratoSave(Contrato contrato, Model model) {
        String url;
        String userName = (String) request.getSession().getAttribute("sesionUser");
        if (userName != null) {
            String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            if (tipoUser.equals("sofom")) {
                ContratoDAO contratoDAO = new ContratoDAO();
                contrato.setStatus("A");
                contrato.setSaldo(0.0);
                Integer rC = contratoDAO.save(contrato);
                if (!rC.equals(-1)) {
                    Log log = new Log();
                    log.write("Save", userName, contrato.toString());
                    url = "redirect:/adm/contrato/" + contrato.getClaveContrato() + "/edit?save=ok";
                } else {
                    url = "redirect:/adm/contrato/new?save=error";
                }
            } else {
                url = "redirect:/permisos";
            }
        } else {
            url = "redirect:/?sesion=error";
        }
        return url;
    }

    @RequestMapping(value = "/contrato/update", method = RequestMethod.POST)
    public String contratoUpdate(Contrato contrato, Model model) {
        String url;
        String userName = (String) request.getSession().getAttribute("sesionUser");
        if (userName != null) {
            String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            if (tipoUser.equals("sofom")) {
                ContratoDAO contratoDAO = new ContratoDAO();
                if (contratoDAO.update(contrato)) {
                    Log log = new Log();
                    log.write("Update", userName, contrato.toString());
                    url = "redirect:/adm/contrato/" + contrato.getClaveContrato() + "/edit?update=ok";
                } else {
                    url = "redirect:/adm/contrato/" + contrato.getClaveContrato() + "/edit?update=error";
                }
            } else {
                url = "redirect:/permisos";
            }
        } else {
            url = "redirect:/?sesion=error";
        }
        return url;
    }

    @RequestMapping(value = "/contrato/{clave}/edit", method = RequestMethod.GET)
    public String contratoEdita(@PathVariable String clave, Model model) {
        String url;
        if (request.getSession().getAttribute("sesionUser") != null) {
            String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            if (tipoUser.equals("sofom")) {
                imprimeUsuario(model);
                ContratoDAO contratoDAO = new ContratoDAO();
                CuentaBancoDAO cuentaBancoDAO = new CuentaBancoDAO();
                DocumentacionDAO documentacionDAO = new DocumentacionDAO();
                SociosDAO sociosDAO = new SociosDAO();
                Contrato contrato = contratoDAO.get(clave);
                List<CuentaBanco> cuentas = cuentaBancoDAO.getAll();
                Documentacion documentacion = documentacionDAO.get(contrato.getClaveContrato());
                model.addAttribute("contrato", contrato);
                model.addAttribute("cuentas", cuentas);
                model.addAttribute("socios", sociosDAO.getAll(contrato.getClaveContrato()));
                model.addAttribute("editable", false);
                UsuarioAdminDAO usuarioAdminDAO = new UsuarioAdminDAO();
                UsuariosAdmin admin = usuarioAdminDAO.get((String) request.getSession().getAttribute("sesionUser"));
                model.addAttribute("depa", admin.getDepartamento());
                model.addAttribute("documentacion", documentacion);
                model.addAttribute("action", "/gestionfideicomisos/adm/contrato/update");
                url = "/Admin/Contratos/NuevoContrato";
            } else {
                url = "redirect:/permisos";
            }
        } else {
            url = "redirect:/?sesion=error";
        }
        return url;
    }

    @ResponseBody
    @RequestMapping(value = "/codigo/postal", method = RequestMethod.POST)
    public String searchCodigoPostal(String cp) {
        String res = "error";
        CodigoPostalDAO codigoPostalDAO = new CodigoPostalDAO();
        CodigoPostal codigoPostal = codigoPostalDAO.get(cp);
        if (codigoPostal != null) {
            res = codigoPostal.getdEstado();
        }
        return res;
    }

    @RequestMapping(value = "/usuarios", method = RequestMethod.GET)
    public String usuarios(Model model) {
        String url;
        if (request.getSession().getAttribute("sesionUser") != null) {
            String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            if (tipoUser.equals("sofom")) {
                imprimeUsuario(model);
                UsuarioAdminDAO usuarioAdminDAO = new UsuarioAdminDAO();
                List<UsuariosAdmin> usuarios = usuarioAdminDAO.getAll();
                model.addAttribute("usuarios", usuarios);
                UsuariosAdmin admin = usuarioAdminDAO.get((String) request.getSession().getAttribute("sesionUser"));
                model.addAttribute("depa", admin.getDepartamento());
                url = "/Admin/Usuarios/Usuarios";
            } else {
                url = "redirect:/permisos";
            }
        } else {
            url = "redirect:/?sesion=error";
        }
        return url;
    }

    @RequestMapping(value = "/usuario/new", method = RequestMethod.GET)
    public String usuario(Model model) {
        String url;
        if (request.getSession().getAttribute("sesionUser") != null) {
            String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            if (tipoUser.equals("sofom")) {
                UsuariosAdmin usuarioAdmin = new UsuariosAdmin();
                model.addAttribute("user", usuarioAdmin);
                imprimeUsuario(model);
                UsuarioAdminDAO usuarioAdminDAO = new UsuarioAdminDAO();
                UsuariosAdmin admin = usuarioAdminDAO.get((String) request.getSession().getAttribute("sesionUser"));
                model.addAttribute("depa", admin.getDepartamento());
                model.addAttribute("action", "/gestionfideicomisos/adm/usuario/save");
                url = "/Admin/Usuarios/NuevoUsuario";
            } else {
                url = "redirect:/permisos";
            }
        } else {
            url = "redirect:/?sesion=error";
        }
        return url;
    }

    @RequestMapping(value = "/usuario/{id:[0-9]+}/edit", method = RequestMethod.GET)
    public String usuario(@PathVariable Integer id, Model model) {
        String url;
        if (request.getSession().getAttribute("sesionUser") != null) {
            String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            if (tipoUser.equals("sofom")) {
                imprimeUsuario(model);
                UsuarioAdminDAO usuarioAdminDAO = new UsuarioAdminDAO();
                UsuariosAdmin usuarioAdmin = usuarioAdminDAO.get(id);
                UsuariosAdmin admin = usuarioAdminDAO.get((String) request.getSession().getAttribute("sesionUser"));
                model.addAttribute("depa", admin.getDepartamento());
                model.addAttribute("user", usuarioAdmin);
                model.addAttribute("action", "/gestionfideicomisos/adm/usuario/update");
                url = "/Admin/Usuarios/NuevoUsuario";
            } else {
                url = "redirect:/permisos";
            }
        } else {
            url = "redirect:/?sesion=error";
        }
        return url;
    }

    @RequestMapping(value = "/usuario/save", method = RequestMethod.POST)
    public String usuarioSave(UsuariosAdmin usuarioAdmin, Model model) {
        String url;
        String userName = (String) request.getSession().getAttribute("sesionUser");
        if (userName != null) {
            String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            if (tipoUser.equals("sofom")) {
                UsuarioAdminDAO usuarioAdminDAO = new UsuarioAdminDAO();
                UsuariosAdmin usuario = usuarioAdminDAO.get(usuarioAdmin.getUsuario());
                if (usuario != null) {
                    url = "redirect:/adm/usuario/new?save=duplicado";
                } else {
                    usuarioAdmin.setClaveUsuario(usuarioAdminDAO.getMaxClaveUsuario());
                    usuarioAdmin.setPassword(UsuariosHelp.generaContra(6));
                    Integer rU = usuarioAdminDAO.save(usuarioAdmin);
                    final UsuariosAdmin userAdm = usuarioAdmin;
                    if (rU.equals(-1)) {
                        url = "redirect:/adm/usuario/new?save=error";
                    } else {
                        Log log = new Log();
                        log.write("Save", userName, usuarioAdmin.toString());
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                GestionSendMail mail = new GestionSendMail(GestionSendMail.Tipos_cuerpo_HTML.GENERA_CONTRASENA, userAdm.getCorreo());
                                mail.setCuerpoNuevaContrasena(userAdm.getNombreUsuario(), userAdm.getUsuario(), userAdm.getPassword());
                                if (!mail.enviar()) {
                                    try {
                                        Thread.sleep(180000);
                                        mail.setCuerpoNuevaContrasena(userAdm.getNombreUsuario(), userAdm.getUsuario(), userAdm.getPassword());
                                        mail.enviar();
                                    } catch (InterruptedException ex) {
                                        System.out.println("Error al enviar segundo mail " + ex);
                                    }
                                }
                            }
                        });
                        thread.start();

                        url = "redirect:/adm/usuario/new?save=ok";
                    }
                }
            } else {
                url = "redirect:/permisos";
            }
        } else {
            url = "redirect:/?sesion=error";
        }
        return url;
    }

    @RequestMapping(value = "/usuario/update", method = RequestMethod.POST)
    public String usuarioUpdate(UsuariosAdmin usuarioAdmin, Model model) {
        String url;
        String userName = (String) request.getSession().getAttribute("sesionUser");
        if (userName != null) {
            String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            if (tipoUser.equals("sofom")) {
                UsuarioAdminDAO usuarioAdminDAO = new UsuarioAdminDAO();
                usuarioAdmin.setPassword(usuarioAdminDAO.get(Integer.parseInt(usuarioAdmin.getClaveUsuario())).getPassword());
                if (usuarioAdminDAO.update(usuarioAdmin)) {
                    Log log = new Log();
                    log.write("Update", userName, usuarioAdmin.toString());
                    url = "redirect:/adm/usuario/" + usuarioAdmin.getClaveUsuario() + "/edit?update=ok";
                } else {
                    url = "redirect:/adm/usuario/" + usuarioAdmin.getClaveUsuario() + "/edit?update=error";
                }
            } else {
                url = "redirect:/permisos";
            }
        } else {
            url = "redirect:/?sesion=error";
        }
        return url;
    }

    @RequestMapping(value = "/cuentas", method = RequestMethod.GET)
    public String cuentas(Model model) {
        String url;
        if (request.getSession().getAttribute("sesionUser") != null) {
            String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            if (tipoUser.equals("sofom")) {
                imprimeUsuario(model);
                CuentaBancoDAO cuentaBancoDAO = new CuentaBancoDAO();
                UsuarioAdminDAO usuarioAdminDAO = new UsuarioAdminDAO();
                UsuariosAdmin admin = usuarioAdminDAO.get((String) request.getSession().getAttribute("sesionUser"));
                model.addAttribute("depa", admin.getDepartamento());
                model.addAttribute("cuentas", cuentaBancoDAO.getAll());
                url = "/Admin/Cuentas/Cuentas";
            } else {
                url = "redirect:/permisos";
            }
        } else {
            url = "redirect:/?sesion=error";
        }
        return url;
    }

    @RequestMapping(value = "/cuenta/new", method = RequestMethod.GET)
    public String cuenta(Model model) {
        String url;
        if (request.getSession().getAttribute("sesionUser") != null) {
            String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            if (tipoUser.equals("sofom")) {
                imprimeUsuario(model);
                CuentaBanco cuentaBanco = new CuentaBanco();
                model.addAttribute("cuentaBanco", cuentaBanco);
                model.addAttribute("editable", true);
                UsuarioAdminDAO usuarioAdminDAO = new UsuarioAdminDAO();
                UsuariosAdmin admin = usuarioAdminDAO.get((String) request.getSession().getAttribute("sesionUser"));
                model.addAttribute("depa", admin.getDepartamento());
                model.addAttribute("action", "/gestionfideicomisos/adm/cuenta/save");
                url = "/Admin/Cuentas/NuevaCuenta";
            } else {
                url = "redirect:/permisos";
            }
        } else {
            url = "redirect:/?sesion=error";
        }
        return url;
    }

    @RequestMapping(value = "/cuenta/save", method = RequestMethod.POST)
    public String cuentaSave(CuentaBanco cuentaBanco, Model model) {
        String url;
        String userName = (String) request.getSession().getAttribute("sesionUser");
        if (userName != null) {
            String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            if (tipoUser.equals("sofom")) {
                CuentaBancoDAO cuentaBancoDAO = new CuentaBancoDAO();
                Integer rC = cuentaBancoDAO.save(cuentaBanco);
                if (rC.equals(-1)) {
                    url = "redirect:/adm/cuenta/new?save=error";
                } else {
                    Log log = new Log();
                    log.write("Save", userName, cuentaBanco.toString());
                    url = "redirect:/adm/cuenta/new?save=ok";
                }
            } else {
                url = "redirect:/permisos";
            }
        } else {
            url = "redirect:/?sesion=error";
        }
        return url;
    }

    @RequestMapping(value = "/cuenta/{nombreCuenta}/update", method = RequestMethod.POST)
    public String cuentaUpdate(CuentaBanco cuentaBanco, @PathVariable String nombreCuenta, Model model) {
        String url;
        String userName = (String) request.getSession().getAttribute("sesionUser");
        if (userName != null) {
            String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            if (tipoUser.equals("sofom")) {
                CuentaBancoDAO cuentaBancoDAO = new CuentaBancoDAO();
                if (cuentaBancoDAO.update(cuentaBanco, nombreCuenta)) {
                    Log log = new Log();
                    log.write("Save", userName, cuentaBanco.toString());
                    url = "redirect:/adm/cuenta/" + cuentaBanco.getCuentaOrigen() + "/edit?update=ok";
                } else {
                    url = "redirect:/adm/cuenta/" + nombreCuenta + "/edit?update=error";
                }
            } else {
                url = "redirect:/permisos";
            }
        } else {
            url = "redirect:/?sesion=error";
        }
        return url;
    }

    @RequestMapping(value = "/cuenta/{cuentaOrigen}/edit", method = RequestMethod.GET)
    public String cuenta(@PathVariable String cuentaOrigen, Model model) {
        String url;
        if (request.getSession().getAttribute("sesionUser") != null) {
            String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            if (tipoUser.equals("sofom")) {
                imprimeUsuario(model);
                CuentaBancoDAO cuentaBancoDAO = new CuentaBancoDAO();
                CuentaBanco cuentaBanco = cuentaBancoDAO.get(cuentaOrigen);
                model.addAttribute("cuentaBanco", cuentaBanco);
                model.addAttribute("action", "/gestionfideicomisos/adm/cuenta/" + cuentaBanco.getCuentaOrigen() + "/update");
                url = "/Admin/Cuentas/NuevaCuenta";
            } else {
                url = "redirect:/permisos";
            }
        } else {
            url = "redirect:/?sesion=error";
        }
        return url;
    }

    @RequestMapping(value = "/clientes", method = RequestMethod.GET)
    public String clientes(Model model,
            @RequestParam(value = "claveContrato", required = false) String claveContrato,
            @RequestParam(value = "usuario", required = false) String usuario,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "pagina", required = false, defaultValue = "0") String pagina) {
        String url;
        if (request.getSession().getAttribute("sesionUser") != null) {
            String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            if (tipoUser.equals("sofom")) {
                imprimeUsuario(model);
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                if (claveContrato != null) {
                    claveContrato = claveContrato.equals("") ? null : claveContrato;
                    model.addAttribute("claveContrato", claveContrato);
                }
                if (usuario != null) {
                    usuario = usuario.equals("") ? null : usuario;
                    model.addAttribute("usuario", usuario);
                }
                if (email != null) {
                    email = email.equals("") ? null : email;
                    model.addAttribute("email", email);
                }
                UsuarioAdminDAO usuarioAdminDAO = new UsuarioAdminDAO();
                UsuariosAdmin admin = usuarioAdminDAO.get((String) request.getSession().getAttribute("sesionUser"));
                model.addAttribute("depa", admin.getDepartamento());
                model.addAttribute("countClientes", usuarioDAO.countUsuarios(claveContrato, usuario, email));
                model.addAttribute("clientes", usuarioDAO.getAll(pagina, claveContrato, usuario, email));
                url = "/Admin/Clientes/Clientes";
            } else {
                url = "redirect:/permisos";
            }
        } else {
            url = "redirect:/?sesion=error";
        }
        return url;
    }

    @RequestMapping(value = "/cliente/new", method = RequestMethod.GET)
    public String cliente(Model model) {
        String url;
        if (request.getSession().getAttribute("sesionUser") != null) {
            String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            if (tipoUser.equals("sofom")) {
                Usuario usuario = new Usuario();
                ContratoDAO contratoDAO = new ContratoDAO();
                model.addAttribute("user", usuario);
                model.addAttribute("contratos", contratoDAO.getAll());
                Date fechaEnviar = new Date();
                UsuarioAdminDAO usuarioAdminDAO = new UsuarioAdminDAO();
                UsuariosAdmin admin = usuarioAdminDAO.get((String) request.getSession().getAttribute("sesionUser"));
                model.addAttribute("depa", admin.getDepartamento());
                model.addAttribute("fechaCaptura", fechaEnviar);
                model.addAttribute("fechaBloqueo", fechaEnviar);
                imprimeUsuario(model);
                model.addAttribute("action", "/gestionfideicomisos/adm/cliente/save");
                url = "/Admin/Clientes/NuevoCliente";
            } else {
                url = "redirect:/permisos";
            }
        } else {
            url = "redirect:/?sesion=error";
        }
        return url;
    }

    @RequestMapping(value = "/cliente/{claveContrato}/{userName}/edit", method = RequestMethod.GET)
    public String cliente(@PathVariable String claveContrato, @PathVariable String userName, Model model) {
        String url;
        if (request.getSession().getAttribute("sesionUser") != null) {
            String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            if (tipoUser.equals("sofom")) {
                imprimeUsuario(model);
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                Usuario cliente = usuarioDAO.get(claveContrato, userName);
                ContratoDAO contratoDAO = new ContratoDAO();
                model.addAttribute("contratos", contratoDAO.getAll());
                model.addAttribute("user", cliente);
                model.addAttribute("fechaCaptura", cliente.getFechaAlta());
                UsuarioAdminDAO usuarioAdminDAO = new UsuarioAdminDAO();
                UsuariosAdmin admin = usuarioAdminDAO.get((String) request.getSession().getAttribute("sesionUser"));
                model.addAttribute("depa", admin.getDepartamento());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
                model.addAttribute("fechaCapturaMostrar", format.format(cliente.getFechaAlta()));
                if (cliente.getFechaBloqueo() == null) {
                    model.addAttribute("fechaBloqueo", new Date());
                } else {
                    model.addAttribute("fechaBloqueo", cliente.getFechaBloqueo());
                }
                model.addAttribute("action", "/gestionfideicomisos/adm/cliente/" + claveContrato + "/" + userName + "/update");
                url = "/Admin/Clientes/NuevoCliente";
            } else {
                url = "redirect:/permisos";
            }
        } else {
            url = "redirect:/?sesion=error";
        }
        return url;
    }

    @ResponseBody
    @RequestMapping(value = "/genera/contra", method = RequestMethod.POST)
    public String nuevaContra(String tipo, String id, String username) {
        String res = "error";
        String userName = (String) request.getSession().getAttribute("sesionUser");
        if (tipo.equals("cliente")) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            ContratoDAO contratoDAO = new ContratoDAO();
            Usuario usuario = usuarioDAO.get(id, username);
            usuario.setPassword(UsuariosHelp.generaContra(8));
            if (usuarioDAO.update(usuario, id, username)) {
                Log log = new Log();
                log.write("Contraseña", userName, usuario.toString());
                final Contrato contrato = contratoDAO.get(usuario.getClaveContrato());
                final Usuario envioUser = usuario;
                Thread hilo = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GestionSendMail mail = new GestionSendMail(GestionSendMail.Tipos_cuerpo_HTML.GENERA_CONTRASENA, envioUser.getContactoUsuario());
                        mail.setCuerpoNuevoCliente(envioUser.getClaveContrato(), envioUser.getClaveCliente(), contrato.getNombreCliente(),
                                envioUser.getNombreUsuario(), envioUser.getTelefono(), envioUser.getUsuario(),
                                envioUser.getPassword(), envioUser.getContactoUsuario());
                        if (!mail.enviar()) {
                            try {
                                Thread.sleep(180000);
                                mail.setCuerpoNuevoCliente(envioUser.getClaveContrato(), envioUser.getClaveCliente(), contrato.getNombreCliente(),
                                        envioUser.getNombreUsuario(), envioUser.getTelefono(), envioUser.getUsuario(),
                                        envioUser.getPassword(), envioUser.getContactoUsuario());
                                mail.enviar();
                            } catch (InterruptedException ex) {
                                System.out.println("Error hilo envio segundo correo " + ex);
                            }
                        }
                    }
                });
                hilo.start();
                res = "ok";
            }
        } else {
            UsuarioAdminDAO usuarioAdminDAO = new UsuarioAdminDAO();
            UsuariosAdmin garante = usuarioAdminDAO.get(Integer.parseInt(id));
            garante.setPassword(UsuariosHelp.generaContra(8));
            if (usuarioAdminDAO.update(garante)) {
                Log log = new Log();
                log.write("Contraseña", userName, garante.toString());
                final UsuariosAdmin envioUserAdm = garante;
                Thread hilo = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GestionSendMail mail = new GestionSendMail(GestionSendMail.Tipos_cuerpo_HTML.GENERA_CONTRASENA, envioUserAdm.getCorreo());
                        mail.setCuerpoNuevaContrasena(envioUserAdm.getNombreUsuario(), envioUserAdm.getUsuario(), envioUserAdm.getPassword());
                        if (!mail.enviar()) {
                            try {
                                Thread.sleep(180000);
                                mail.setCuerpoNuevaContrasena(envioUserAdm.getNombreUsuario(), envioUserAdm.getUsuario(), envioUserAdm.getPassword());
                                mail.enviar();
                            } catch (InterruptedException ex) {
                                System.out.println("Error hilo envio segundo correo " + ex);
                            }
                        }
                    }
                });
                hilo.start();

                res = "ok";
            }
        }
        return res;
    }

    @RequestMapping(value = "/cliente/save", method = RequestMethod.POST)
    public String clienteSave(Usuario usuario, Model model) {
        String url;
        String userName = (String) request.getSession().getAttribute("sesionUser");
        if (userName != null) {
            String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            if (tipoUser.equals("sofom")) {
                UsuarioDAO cliente = new UsuarioDAO();

                Usuario user = cliente.get(usuario.getClaveContrato(), usuario.getUsuario());
                if (user != null) {
                    url = "redirect:/adm/cliente/new?save=duplicado";
                } else {
                    if (usuario.getStatus().equals("A")) {
                        usuario.setFechaBloqueo(null);
                    }
                    usuario.setPassword(UsuariosHelp.generaContra(8));
                    Integer rU = cliente.save(usuario);
                    if (rU.equals(-1)) {
                        url = "redirect:/adm/cliente/new?save=error";
                    } else {
                        Log log = new Log();
                        log.write("Save", userName, usuario.toString());
                        final Usuario userMail = usuario;
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                GestionSendMail mail = new GestionSendMail(GestionSendMail.Tipos_cuerpo_HTML.BIENVENIDO, userMail.getContactoUsuario());
                                ContratoDAO contratoDAO = new ContratoDAO();
                                Contrato contrato = contratoDAO.get(userMail.getClaveContrato());
                                mail.setCuerpoNuevoCliente(userMail.getClaveContrato(), userMail.getClaveCliente(), contrato.getNombreCliente(),
                                        userMail.getNombreUsuario(), userMail.getTelefono(), userMail.getUsuario(),
                                        userMail.getPassword(), userMail.getContactoUsuario());
                                if (!mail.enviar()) {
                                    try {
                                        Thread.sleep(180000);
                                        mail.setCuerpoNuevoCliente(userMail.getClaveContrato(), userMail.getClaveCliente(), contrato.getNombreCliente(),
                                                userMail.getNombreUsuario(), userMail.getTelefono(), userMail.getUsuario(),
                                                userMail.getPassword(), userMail.getContactoUsuario());
                                        mail.enviar();
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }

                            }
                        });
                        thread.start();

                        url = "redirect:/adm/cliente/new?save=ok";
                    }
                }
            } else {
                url = "redirect:/permisos";
            }
        } else {
            url = "redirect:/?sesion=error";
        }
        return url;
    }

    @RequestMapping(value = "/cliente/{claveContrato}/{userName}/update", method = RequestMethod.POST)
    public String clienteUpdate(Usuario usuario, @PathVariable String claveContrato, @PathVariable String userName, Model model) {
        String url;
        String userNameSesion = (String) request.getSession().getAttribute("sesionUser");
        if (userNameSesion != null) {
            String tipoUser = (String) request.getSession().getAttribute("tipoUser");
            if (tipoUser.equals("sofom")) {
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                if (usuario.getStatus().equals("A")) {
                    usuario.setFechaBloqueo(null);
                }
                usuario.setPassword(usuarioDAO.get(claveContrato, userName).getPassword());
                if (usuarioDAO.update(usuario, claveContrato, userName)) {
                    Log log = new Log();
                    log.write("Update", userNameSesion, usuario.toString());
                    url = "redirect:/adm/cliente/" + usuario.getClaveContrato() + "/" + usuario.getUsuario() + "/edit?update=ok";
                } else {
                    url = "redirect:/adm/cliente/" + claveContrato + "/" + userName + "/edit?update=error";
                }
            } else {
                url = "redirect:/permisos";
            }
        } else {
            url = "redirect:/?sesion=error";
        }
        return url;
    }

    @ResponseBody
    @RequestMapping(value = "/valida/usuario", method = RequestMethod.POST)
    public String validaOficialCump(String userName, String password, String obs, String contrato, String nombreEmp) {
        String res = "error";
        if (!userName.equals("") && !password.equals("") && !obs.trim().equals("")) {
            UsuarioAdminDAO usuarioDAO = new UsuarioAdminDAO();
            UsuariosAdmin sofom = usuarioDAO.get(userName, password);
            if (sofom.getDepartamento().equals("Cumplimiento")) {
                LogPLDDAO logPLDDAO = new LogPLDDAO();
                LogPLD logPLD = new LogPLD();
                logPLD.setUsuario(userName);
                logPLD.setAccion("Autorizo la actualizacion y/o creación del contrato : " + contrato + ", con el nombre de  " + nombreEmp);
                logPLD.setObservaciones(obs);
                if (logPLDDAO.save(logPLD) > 0) {
                    res = "ok";
                }
            }
        }
        return res;
    }

    @RequestMapping(value = "/cancela/cliente/pld", method = RequestMethod.GET)
    public String cancelaClientePLD(String contrato, String nombreEmp) {
        final String username = (String) request.getSession().getAttribute("sesionUser");
        final String claveContrato = contrato;
        final String nombreEmpleado = nombreEmp;
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                UsuarioAdminDAO sofomDAO = new UsuarioAdminDAO();
                String correos = sofomDAO.getCorreos("Cumplimiento");
                GestionSendMail mail = new GestionSendMail(GestionSendMail.Tipos_cuerpo_HTML.INTENTOCREARUSUARIOPLD, correos);
                mail.setCuerpoIntentoCrearClientePLD(nombreEmpleado, claveContrato, username);
                if (!mail.enviar()) {
                    try {
                        Thread.sleep(180000);
                        mail.enviar();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        hilo.start();
        return "redirect:/adm/contrato/" + contrato + "/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/valida/usuario/socios", method = RequestMethod.POST)
    public String validaOficialCumpSocio(String userName, String password, String obs, String contrato, String nombreEmp) {
        String res = "error";
        if (!userName.equals("") && !password.equals("") && !obs.trim().equals("")) {
            UsuarioAdminDAO usuarioDAO = new UsuarioAdminDAO();
            UsuariosAdmin sofom = usuarioDAO.get(userName, password);
            ContratoDAO contratoDAO = new ContratoDAO();
            if (sofom.getDepartamento().equals("Cumplimiento")) {
                LogPLDDAO logPLDDAO = new LogPLDDAO();
                LogPLD logPLD = new LogPLD();
                logPLD.setUsuario(userName);
                logPLD.setAccion("Autorizo la creación de un socio y/o representante legal en PLD del contrato : " + contrato + ", con el nombre de  " + nombreEmp);
                logPLD.setObservaciones(obs);
                if (logPLDDAO.save(logPLD) > 0) {
                    Contrato contra = contratoDAO.get(contrato);
                    contra.setPld(true);
                    if (contratoDAO.update(contra)) {
                        res = "ok";
                    }
                }
            }
        }
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/socio/add", method = RequestMethod.POST)
    public String agregaSocio(Socios socios) {
        String res = "error";
        try {
            SociosDAO sociosDAO = new SociosDAO();
            if (socios.getPorcentaje() == null) {
                socios.setPorcentaje(0.00);
            } else {
                DecimalFormat decimalFormat = new DecimalFormat("###.##");
                socios.setPorcentaje(Double.parseDouble(decimalFormat.format(socios.getPorcentaje())));
            }
            Integer id = sociosDAO.save(socios);
            res = id.toString();
        } catch (Exception e) {
            System.out.println("Error al guardar socio " + e);
        }
        return res;
    }

    @RequestMapping(value = "/socio/{id:[0-9]+}/{claveContrato}/delete", method = RequestMethod.GET)
    public String agregaSocio(@PathVariable Integer id, @PathVariable String claveContrato) {
        String res = "redirect:/adm/contrato/" + claveContrato + "/edit?update=error";
        SociosDAO sociosDAO = new SociosDAO();
        boolean eliminado = sociosDAO.delete(id);
        if (eliminado) {
            res = "redirect:/adm/contrato/" + claveContrato + "/edit?update=ok";
        }
        return res;
    }

}
