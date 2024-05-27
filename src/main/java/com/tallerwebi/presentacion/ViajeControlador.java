package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.imagen.ImagenServicio;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ViajeControlador {

    private ViajeServicio viajeServicio;
    private ImagenServicio imagenServicio;
    private ConductorServicio conductorServicio;

    @Autowired
    public ViajeControlador(ViajeServicio viajeServicio, ConductorServicio conductorServicio, ImagenServicio imagenServicio){
        this.viajeServicio = viajeServicio;
        this.conductorServicio = conductorServicio;
        this.imagenServicio = imagenServicio;
    }

    @RequestMapping("/historial")
    public ModelAndView mostrarHistorial(HttpServletRequest request) throws ConductorNoEncontradoException {
        ModelMap model = new ModelMap();

        String viewName = "historial-viajes";
        Imagen logo = imagenServicio.getImagenByName("logo");
        Imagen user = imagenServicio.getImagenByName("user");
        Imagen auto = imagenServicio.getImagenByName("auto");
        Imagen fondo = imagenServicio.getImagenByName("fondo");
        Imagen botonPS = imagenServicio.getImagenByName("botonPS");
        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
        List<Viaje> historialViajes = this.viajeServicio.obtenerHistorialDeViajes(conductor);

        model.put("logo", logo);
        model.put("user", user);
        model.put("auto", auto);
        model.put("fondo", fondo);
        model.put("botonPS", botonPS);
        model.put("isUsuarioLogueado",isUsuarioLogueado);
        model.put("conductor", conductor);
        model.put("viajesObtenidos", historialViajes);
        return new ModelAndView(viewName, model);
    }

    @RequestMapping(value = "/viaje-aceptado", method = RequestMethod.GET)
    public ModelAndView AceptarViaje(HttpServletRequest request, @RequestParam("idViaje") Integer idViaje) throws ConductorNoEncontradoException {
        ModelMap model = new ModelMap();

        String viewName = "viaje";
        String claveGoogleMaps = "AIzaSyBylV7--oH5ZaWIdNS5n0bU59LFNN5zEso";
        Imagen logo = imagenServicio.getImagenByName("logo");
        Imagen user = imagenServicio.getImagenByName("user");
        Imagen auto = imagenServicio.getImagenByName("auto");
        Imagen fondo = imagenServicio.getImagenByName("fondo");
        Imagen botonPS = imagenServicio.getImagenByName("botonPS");
        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
        Viaje viaje = viajeServicio.obtenerViajeAceptadoPorId(idViaje);
        this.viajeServicio.asginarConductorAlViaje(viaje, conductor);

        model.put("clave",claveGoogleMaps);
        model.put("logo", logo);
        model.put("user", user);
        model.put("auto", auto);
        model.put("fondo", fondo);
        model.put("botonPS", botonPS);
        model.put("isUsuarioLogueado",isUsuarioLogueado);
        model.put("conductor", conductor);
        model.put("idViaje", viaje.getId());
        model.put("viaje", viaje);
        return new ModelAndView(viewName, model);
    }

    @RequestMapping(value = "/viajes-en-proceso")
    public ModelAndView verViajesEnProceso(HttpServletRequest request) throws ConductorNoEncontradoException {
        ModelMap model = new ModelMap();

        String viewName = "viajes-aceptados";
        Imagen logo = imagenServicio.getImagenByName("logo");
        Imagen user = imagenServicio.getImagenByName("user");
        Imagen auto = imagenServicio.getImagenByName("auto");
        Imagen fondo = imagenServicio.getImagenByName("fondo");
        Imagen botonPS = imagenServicio.getImagenByName("botonPS");
        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
        List<Viaje> viajesObtenidos = viajeServicio.obtenerViajesEnProceso(conductor);

        model.put("logo", logo);
        model.put("user", user);
        model.put("auto", auto);
        model.put("fondo", fondo);
        model.put("botonPS", botonPS);
        model.put("isUsuarioLogueado",isUsuarioLogueado);
        model.put("conductor", conductor);
        model.put("viajesObtenidos", viajesObtenidos);
        return new ModelAndView(viewName, model);
    }

    @RequestMapping(value = "/viajeAceptado", method = RequestMethod.GET)
    public ModelAndView verViaje(HttpServletRequest request, @RequestParam("idViaje") Integer idViaje) throws ConductorNoEncontradoException {
        ModelMap model = new ModelMap();

        String viewName = "viaje";
        String claveGoogleMaps = "AIzaSyBylV7--oH5ZaWIdNS5n0bU59LFNN5zEso";
        Imagen logo = imagenServicio.getImagenByName("logo");
        Imagen user = imagenServicio.getImagenByName("user");
        Imagen auto = imagenServicio.getImagenByName("auto");
        Imagen fondo = imagenServicio.getImagenByName("fondo");
        Imagen botonPS = imagenServicio.getImagenByName("botonPS");
        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
        Viaje viaje = viajeServicio.obtenerViajeAceptadoPorId(idViaje);

        model.put("clave", claveGoogleMaps);
        model.put("logo", logo);
        model.put("user", user);
        model.put("auto", auto);
        model.put("fondo", fondo);
        model.put("botonPS", botonPS);
        model.put("isUsuarioLogueado", isUsuarioLogueado);
        model.put("conductor", conductor);
        model.put("viaje", viaje);
        return new ModelAndView(viewName, model);
    }

    @RequestMapping("/cancelar-viaje")
    public ModelAndView cancelarViaje(@RequestParam("idViaje") Integer idViaje){
        Viaje viaje = viajeServicio.obtenerViajeAceptadoPorId(idViaje);

        viaje.setCancelado(true);
        viajeServicio.actualizarViaje(viaje);

        return new ModelAndView("redirect:/home");
    }

    @RequestMapping("/terminar-viaje")
    public ModelAndView terminarViaje(@RequestParam("idViaje") Integer idViaje){
        Viaje viaje = viajeServicio.obtenerViajeAceptadoPorId(idViaje);

        viaje.setTerminado(true);
        viajeServicio.actualizarViaje(viaje);

        return new ModelAndView("redirect:/home");
    }

    @RequestMapping("/volver")
    public ModelAndView volverAlHome(){
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping("/descartar")
    public ModelAndView descartarViaje(HttpServletRequest request, @RequestParam("idViaje") Integer idViaje) throws ConductorNoEncontradoException {
        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));

        this.viajeServicio.descartarViaje(idViaje, conductor);
        Boolean isPenalizado = this.viajeServicio.estaPenalizado(conductor);
        request.getSession().setAttribute("isPenalizado", isPenalizado);

        return new ModelAndView("redirect:/home");
    }
}