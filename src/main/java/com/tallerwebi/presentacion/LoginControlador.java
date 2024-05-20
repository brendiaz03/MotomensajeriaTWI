package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.conductor.ConductorServicioServicioImpl;
import com.tallerwebi.dominio.imagen.ImagenServicio;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.presentacion.Datos.DatosLoginConductor;
import com.tallerwebi.dominio.login.LoginServicio;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
@SessionAttributes("isUsuarioLogueado")
public class LoginControlador {

    private static LoginServicio loginServicio;
    private static ImagenServicio imagenServicio;
    private final ConductorServicio conductorServicio;
    private ViajeService viajeService;

    @Autowired
    public LoginControlador(LoginServicio _LoginServicio, ImagenServicio _imagenServicio, ConductorServicio _conductorServicio, ViajeService viajeService){
        this.loginServicio = _LoginServicio;
        this.imagenServicio = _imagenServicio;
        this.conductorServicio = _conductorServicio;
        this.viajeService = viajeService;
    }

    @RequestMapping("/home")
    public ModelAndView mostrarHome(HttpServletRequest request) throws ConductorNoEncontradoException {
        ModelMap model = new ModelMap();

        String viewName= "home";

        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
        List<Viaje> viajes = viajeService.obtenerLasSolicitudesDeViajesPendientes();
        model.put("viajes", viajes);

        Conductor conductor = new Conductor();
        model.put("isUsuarioLogueado",isUsuarioLogueado);
        if(request.getSession().getAttribute("IDUSUARIO") != null){
            conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
        }else{
            conductor = null;
        }
        model.put("conductor", conductor);
        Imagen logo = imagenServicio.getImagenByName("logo");
        model.put("logo", logo);
        Imagen user = imagenServicio.getImagenByName("user");
        model.put("user", user);
        Imagen auto = imagenServicio.getImagenByName("auto");
        model.put("auto", auto);
        Imagen fondo = imagenServicio.getImagenByName("fondo");
        model.put("fondo", fondo);
        Imagen botonPS = imagenServicio.getImagenByName("botonPS");
        model.put("botonPS", botonPS);
        return new ModelAndView(viewName,model);
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ModelAndView mostrarLogin(){
        String viewName= "login-conductor";
        ModelMap model = new ModelMap();
        model.put("datosLogin",new DatosLoginConductor());
        Imagen logo = imagenServicio.getImagenByName("logo");
        model.put("logo", logo);
        return new ModelAndView(viewName,model);
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
                model.put("error", "Usuario o clave correcta");
                return new ModelAndView("redirect:/home", model);

            }else{
                model.put("error", "Usuario o clave incorrecta");
                request.getSession().setAttribute("isUsuarioLogueado", false);
                return new ModelAndView("redirect:/login", model);

            }

    }

    @RequestMapping(path = "/cerrar-sesion", method = RequestMethod.GET)
    public ModelAndView cerrarSesion(HttpServletRequest request) throws ConductorNoEncontradoException {
        request.getSession().invalidate(); // Invalida la sesión, lo que equivale a cerrar sesión
        return mostrarHome(request);
    }

    @PostMapping("/viaje/accion")
    public ModelAndView procesarAccionViaje(@RequestParam("idViaje") Integer idViaje, @RequestParam("accion") String accion, HttpSession session) {
        Integer idConductor = (Integer) session.getAttribute("IDUSUARIO");
        ModelMap modelo = new ModelMap();
        List<Viaje> viajes = new ArrayList<>();
        String mensaje = "";

        if ("aceptar".equals(accion)) {
            viajeService.actualizarViajeConElIdDelConductorQueAceptoElViaje(idViaje, idConductor);
            viajes = viajeService.obtenerLosViajesAceptadosPorElConductor(idConductor);
            mensaje = "VIAJE ACEPTADO!";
        } else {
            mensaje = "Acción no válida";
        }

        modelo.put("viajesAceptados", viajes);
        modelo.put("mensaje", mensaje);

        return new ModelAndView("viajes", modelo);
    }

}
