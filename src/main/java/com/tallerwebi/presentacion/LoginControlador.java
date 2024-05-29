package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
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
    private ViajeServicio viajeServicio;

    @Autowired
    public LoginControlador(LoginServicio _LoginServicio, ConductorServicio _conductorServicio, ViajeServicio viajeServicio){
        this.loginServicio = _LoginServicio;
        this.conductorServicio = _conductorServicio;
        this.viajeServicio = viajeServicio;
    }

    @RequestMapping("/")
    public ModelAndView Inicio() {
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping("/home")
    public ModelAndView mostrarHome(HttpServletRequest request) throws ConductorNoEncontradoException {
        ModelMap model = new ModelMap();
        String viewName = "home";

        Double latitudActual = -34.69549; // VER
        Double longitudActual = -58.529661; // VER
        Double distanciaAFiltrar = 5.0; // VER

        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
        List<DatosViaje> viajesCercanosPendientes = this.viajeServicio.filtrarViajesPorDistanciaDelConductor(latitudActual, longitudActual, distanciaAFiltrar);
        Conductor conductor;

        if(request.getSession().getAttribute("IDUSUARIO") != null){
            conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
        }else{
            conductor = null;
        }

        request.getSession().setAttribute("isPenalizado", this.viajeServicio.estaPenalizado(conductor));

        model.put("isUsuarioLogueado", isUsuarioLogueado);
        model.put("viajes", viajesCercanosPendientes);
        model.put("conductor", conductor);
        model.put("isPenalizado", request.getSession().getAttribute("isPenalizado"));
        return new ModelAndView(viewName, model);
    }

    @RequestMapping(path = "/login")
    public ModelAndView mostrarLogin(){
        ModelMap model = new ModelMap();
        String viewName= "login-conductor";
        model.put("datosLogin", new DatosLoginConductor());
        return new ModelAndView(viewName, model);
    }

    @RequestMapping(path="/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLoginConductor datosLoginnConductor, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Conductor conductorBuscado = loginServicio.consultarUsuario(datosLoginnConductor.getUsuario(), datosLoginnConductor.getPassword());
            if (conductorBuscado != null) {
                request.getSession().setAttribute("NOMBRE", conductorBuscado.getNombre());
                request.getSession().setAttribute("IDUSUARIO", conductorBuscado.getId());
                request.getSession().setAttribute("APELLIDO", conductorBuscado.getApellido());
                request.getSession().setAttribute("isUsuarioLogueado", true);
                request.getSession().setAttribute("isEditForm", false);
                model.put("correcto", "Usuario o clave correcta");
                return new ModelAndView("redirect:/home", model);
            }else{
                request.getSession().setAttribute("isUsuarioLogueado", false);
                model.put("error", "Usuario o clave incorrecta");
                return new ModelAndView("redirect:/login", model);
            }
    }

    @RequestMapping(path = "/cerrar-sesion")
    public ModelAndView cerrarSesion(HttpServletRequest request) throws ConductorNoEncontradoException {
        request.getSession().invalidate();
        return mostrarHome(request);
    }


}