package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.enums.TipoUsuario;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.presentacion.Datos.DatosLogin;
import com.tallerwebi.dominio.login.LoginServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("isUsuarioLogueado")
public class LoginControlador {

    private LoginServicio loginServicio;
    private ConductorServicio conductorServicio;
    private ClienteServicio clienteServicio;


    @Autowired
    public LoginControlador(LoginServicio loginServicio, ConductorServicio conductorServicio, ClienteServicio clienteServicio){
        this.loginServicio = loginServicio;
        this.conductorServicio = conductorServicio;
        this.clienteServicio = clienteServicio;
    }

    @RequestMapping("/")
    public ModelAndView Inicio() {
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(path = "/home")
    public ModelAndView mostrarHome(HttpSession session){
        String viewName= "home";
        return new ModelAndView(viewName);
    }


    @RequestMapping(path = "/login")
    public ModelAndView mostrarLogin(String mensajeError){
        ModelMap model = new ModelMap();
        String viewName= "login";

        if(mensajeError!=null){
            model.put("error", mensajeError);
        }
        model.put("datosLogin", new DatosLogin());
        return new ModelAndView(viewName, model);
    }

    @RequestMapping(path="/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLoginnConductor, HttpSession session) {
        ModelMap model = new ModelMap();

        Usuario usuario = loginServicio.consultarUsuario(datosLoginnConductor.getUsuario(), datosLoginnConductor.getPassword());

            if (usuario != null) {
                session.setAttribute("IDUSUARIO", usuario.getId());
                session.setAttribute("tipoUsuario", usuario.getTipoUsuario());
                session.setAttribute("isUsuarioLogueado", true);
                session.setAttribute("isEditForm", false);
                model.put("correcto", "Usuario o clave correcta");
                if(usuario.getTipoUsuario().equals(TipoUsuario.Conductor)){
                    return new ModelAndView("ubicacion", model);
                }else{
                    return new ModelAndView("redirect:/homeCliente", model);
                }
            }else{
                String mensajeError= "Usuario o clave incorrecta";
                return this.mostrarLogin(mensajeError);
            }
    }

    @RequestMapping(path = "/cerrar-sesion")
    public ModelAndView cerrarSesion(HttpSession session) throws UsuarioNoEncontradoException {
        session.invalidate();
        return mostrarHome(session);
    }

}