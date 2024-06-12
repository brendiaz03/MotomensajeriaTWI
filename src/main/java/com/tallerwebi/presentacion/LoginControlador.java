package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import com.tallerwebi.presentacion.Datos.DatosLoginConductor;
import com.tallerwebi.dominio.login.LoginServicio;
import com.tallerwebi.presentacion.Datos.DatosViaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@SessionAttributes("isUsuarioLogueado")
public class LoginControlador {

    private static LoginServicio loginServicio;
    private final ConductorServicio conductorServicio;
    private final ViajeServicio viajeServicio;
    private ClienteServicio clienteServicio;


    @Autowired
    public LoginControlador(LoginServicio _LoginServicio, ConductorServicio _conductorServicio, ViajeServicio viajeServicio, ClienteServicio clienteServicio){
        loginServicio = _LoginServicio;
        this.conductorServicio = _conductorServicio;
        this.viajeServicio = viajeServicio;
        this.clienteServicio = clienteServicio;
    }

    @RequestMapping("/")
    public ModelAndView Inicio() {
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(path = "/home")
    public ModelAndView mostrarHome(HttpServletRequest request){
        String viewName= "home";
        return new ModelAndView(viewName);
    }


    @RequestMapping(path = "/login")
    public ModelAndView mostrarLogin(){
        ModelMap model = new ModelMap();
        String viewName= "login";

        model.put("datosLogin", new DatosLoginConductor());

        return new ModelAndView(viewName, model);
    }

    @RequestMapping(path="/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLoginConductor datosLoginnConductor, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuario = loginServicio.consultarUsuario(datosLoginnConductor.getUsuario(), datosLoginnConductor.getPassword());

            if (usuario != null) {
                request.getSession().setAttribute("IDUSUARIO", usuario.getId());
                request.getSession().setAttribute("tipoUsuario", usuario.getTipoUsuario());
                request.getSession().setAttribute("isUsuarioLogueado", true);
                request.getSession().setAttribute("isEditForm", false);
                model.put("correcto", "Usuario o clave correcta");
                return new ModelAndView("ubicacion", model);
            }else{
                model.put("error", "Usuario o clave incorrecta");
                return new ModelAndView("redirect:/login", model);
            }
    }

    @RequestMapping(path = "/cerrar-sesion")
    public ModelAndView cerrarSesion(HttpServletRequest request) throws UsuarioNoEncontradoException {
        request.getSession().invalidate();
        return mostrarHome(request);
    }

//    @RequestMapping(value = "/filtrarPorDistancia", method = RequestMethod.POST)
//    public ModelAndView filtrarPorDistancia(HttpServletRequest request, @RequestParam Double distancia) throws UsuarioNoEncontradoException {
//        if (distancia == null) {
//            return new ModelAndView("redirect:/distanciaNoSeleccionada"); // Excepcion
//        } else {
//            request.getSession().setAttribute("distancia", distancia);
//            return mostrarHome(request);
//        }
//    }
}