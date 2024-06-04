package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
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
    private final ViajeServicio viajeServicio;
    private ClienteServicio clienteServicio;
    private Double latitudActual = -34.668822; // VER
    private Double longitudActual =  -58.532878; // VER


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

    @RequestMapping("/home")
    public ModelAndView mostrarHome(HttpServletRequest request) throws UsuarioNoEncontradoException {
        ModelMap model = new ModelMap();
        String viewName = "home";

        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
        Conductor conductor;
        Double distanciaAFiltrar = (Double) request.getSession().getAttribute("distancia");

        if(request.getSession().getAttribute("IDUSUARIO") != null){
           // conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
        }else{
            conductor = null;
        }

        List<DatosViaje> viajesCercanosPendientes;

        if (request.getSession().getAttribute("VEHICULO") != null) {
            viajesCercanosPendientes = this.viajeServicio.filtrarViajesPorDistanciaDelConductor(latitudActual, longitudActual, distanciaAFiltrar);
            model.put("noTieneVehiculo", false);
        } else {
            viajesCercanosPendientes = null;
            model.put("noTieneVehiculo", true);
        }

       // request.getSession().setAttribute("isPenalizado", this.viajeServicio.estaPenalizado(conductor));

        model.put("isUsuarioLogueado", isUsuarioLogueado);
        model.put("viajes", viajesCercanosPendientes);
       // model.put("conductor", conductor);
        model.put("isPenalizado", request.getSession().getAttribute("isPenalizado"));
        return new ModelAndView(viewName, model);
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

        request.getSession().invalidate();

        Conductor conductorBuscado = loginServicio.consultarUsuario(datosLoginnConductor.getUsuario(), datosLoginnConductor.getPassword());

            if (conductorBuscado != null ) {
                request.getSession().setAttribute("NOMBRE", conductorBuscado.getNombre());
                request.getSession().setAttribute("IDUSUARIO", conductorBuscado.getId());
                request.getSession().setAttribute("APELLIDO", conductorBuscado.getApellido());
                request.getSession().setAttribute("VEHICULO", conductorBuscado.getVehiculo());
                request.getSession().setAttribute("isUsuarioLogueado", true);
                request.getSession().setAttribute("isEditForm", false);
                model.put("correcto", "Usuario o clave correcta");
                return new ModelAndView("redirect:/home", model);
            }else{
                //request.getSession().setAttribute("isUsuarioLogueado", false);
                model.put("error", "Usuario o clave incorrecta");
                return new ModelAndView("redirect:/login", model);
            }
    }

    @RequestMapping(path = "/cerrar-sesion")
    public ModelAndView cerrarSesion(HttpServletRequest request) throws UsuarioNoEncontradoException {
        request.getSession().invalidate();
        return mostrarHome(request);
    }

//    @RequestMapping ("/ayuda")
//    public ModelAndView mostrarVistaAyuda(HttpServletRequest request) throws UsuarioNoEncontradoException {
//        ModelMap model = new ModelMap();
//
//        String viewName= "ayuda";
//        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
//        Conductor conductor;
//
//        if(request.getSession().getAttribute("IDUSUARIO") != null){
//            conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
//        }else{
//            conductor = null;
//        }
//        model.put("isUsuarioLogueado",isUsuarioLogueado);
//        model.put("conductor", conductor);
//        return new ModelAndView(viewName, model);
//    }
//
//    @RequestMapping("/compania")
//    public ModelAndView mostrarVistaCompania(HttpServletRequest request) throws UsuarioNoEncontradoException {
//        ModelMap model = new ModelMap();
//
//        String viewName = "compania";
//        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
//        Conductor conductor;
//
//        if(request.getSession().getAttribute("IDUSUARIO") != null){
//            conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
//        }else{
//            conductor = null;
//        }
//
//        model.put("isUsuarioLogueado",isUsuarioLogueado);
//        model.put("conductor", conductor);
//        return new ModelAndView(viewName, model);
//    }

    @RequestMapping(value = "/filtrarPorDistancia", method = RequestMethod.POST)
    public ModelAndView filtrarPorDistancia(HttpServletRequest request, @RequestParam Double distancia) throws UsuarioNoEncontradoException {
        if (distancia == null) {
            return new ModelAndView("redirect:/distanciaNoSeleccionada"); // Excepcion
        } else {
            request.getSession().setAttribute("distancia", distancia);
            return mostrarHome(request);
        }
    }
}