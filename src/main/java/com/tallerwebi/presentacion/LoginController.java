package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.LoginService;
import com.tallerwebi.dominio.entidades.usuarios.Cliente;
import com.tallerwebi.dominio.entidades.usuarios.Conductor;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView mostrarHome() {
        return new ModelAndView("home");
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView mostrarInicio() {
        return new ModelAndView("redirect:/login-usuario");
    }

    @RequestMapping("/login-usuario")
    public ModelAndView mostrarLoginCliente() {
        ModelMap model = new ModelMap();
        model.put("cliente", new Cliente());
        return new ModelAndView("login-usuario", model);
    }

    @RequestMapping("/login-conductor")
    public ModelAndView mostrarLoginConductor() {
        ModelMap model = new ModelMap();
        model.put("conductor", new Conductor());
        return new ModelAndView("login-conductor", model);
    }

    @RequestMapping(path = "/nuevo-usuario", method = RequestMethod.GET)
    public ModelAndView mostrarFormNuevoCliente() {
        ModelMap model = new ModelMap();
        model.put("cliente", new Cliente());
        return new ModelAndView("nuevo-usuario", model);
    }

    @RequestMapping(path = "/registrar-usuario", method = RequestMethod.POST)
    public ModelAndView registrarCliente(@ModelAttribute("cliente") Cliente cliente) {
        ModelMap model = new ModelMap();
        try{
            loginService.guardarCliente(cliente);
        } catch (UsuarioExistente e){
            model.put("error", "El usuario ya existe");
            return new ModelAndView("nuevo-usuario", model);
        } catch (Exception e){
            model.put("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("nuevo-usuario", model);
        }
        return new ModelAndView("redirect:/login-usuario");
    }

    @RequestMapping(path = "/nuevo-conductor", method = RequestMethod.GET)
    public ModelAndView mostrarFormNuevoConductor() {
        ModelMap model = new ModelMap();
        model.put("conductor", new Conductor());
        return new ModelAndView("nuevo-conductor", model);
    }

    @RequestMapping(path = "/registrar-conductor", method = RequestMethod.POST)
    public ModelAndView registrarConductor(@ModelAttribute("conductor") Conductor conductor) {
        ModelMap model = new ModelMap();
        try{
            loginService.guardarConductor(conductor);
        } catch (UsuarioExistente e){
            model.put("error", "El usuario ya existe");
            return new ModelAndView("nuevo-conductor", model);
        } catch (Exception e){
            model.put("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("nuevo-conductor", model);
        }
        return new ModelAndView("redirect:/login-conductor");
    }


    @RequestMapping(path = "/validar-login-conductor", method = RequestMethod.POST)
    public ModelAndView validarLoginConductor(@ModelAttribute("conductor") Conductor conductor) {
        ModelMap model = new ModelMap();

        Conductor conductorBuscado = this.loginService.consultarConductor(conductor.getEmail(), conductor.getPassword());
        if (conductorBuscado != null) {
            return new ModelAndView("redirect:/home");
        } else {
            model.put("error", "Usuario o clave incorrecta");
        }
        return new ModelAndView("login-conductor", model);
    }

    @RequestMapping(path = "/validar-login-cliente", method = RequestMethod.POST)
    public ModelAndView validarLoginCliente(@ModelAttribute("cliente") Cliente cliente, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Cliente clienteBuscado = this.loginService.consultarCliente(cliente.getEmail(), cliente.getPassword());
        if (clienteBuscado != null) {
            request.getSession();
            return new ModelAndView("redirect:/home");
        } else {
            model.put("error", "Usuario o clave incorrecta");
        }
        return new ModelAndView("login-usuario", model);
    }
}