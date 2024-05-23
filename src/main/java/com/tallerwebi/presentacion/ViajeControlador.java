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
public class ViajeController {

    private ViajeServicio viajeServicio;
    private ImagenServicio imagenServicio;
    private ConductorServicio conductorServicio;

    @Autowired
    public ViajeController(ViajeServicio viajeServicio, ConductorServicio conductorServicio, ImagenServicio imagenServicio){
        this.viajeServicio = viajeServicio;
        this.conductorServicio = conductorServicio;
        this.imagenServicio = imagenServicio;
    }

    //viajes que serian el historial
    @RequestMapping("/historial")
    public ModelAndView mostrarVistaViaje(HttpServletRequest request) throws ConductorNoEncontradoException {
        ModelMap model = new ModelMap();
        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
        model.put("isUsuarioLogueado",isUsuarioLogueado);

        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
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

        List<Viaje> historialViajes = this.viajeServicio.obtenerHistorialDeViajes(conductor);
        model.put("viajesObtenidos", historialViajes);
        return new ModelAndView("historial-viajes", model);
    }


    //una vez aceptado que te lleve al viaje con el mapa
    @RequestMapping(value = "/viaje-aceptado", method = RequestMethod.GET)
    public ModelAndView verVistaViaje(HttpServletRequest request, @RequestParam("idViaje") Integer idViaje) throws ConductorNoEncontradoException {
        ModelMap model = new ModelMap();

        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
        model.put("isUsuarioLogueado",isUsuarioLogueado);

        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
        model.put("conductor", conductor);

        Viaje viaje = viajeServicio.obtenerViajeAceptadoPorId(idViaje);
        viaje.setConductor(conductor);

        viajeServicio.actualizarViaje(viaje);

        model.put("idViaje", viaje.getId());
        model.put("viaje", viaje);

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

        return new ModelAndView("viaje", model);
    }

    //si quiere sus viajes en proceso
    @RequestMapping(value = "/viajes-en-proceso")
    public ModelAndView verViajesEnProceso(HttpServletRequest request) throws ConductorNoEncontradoException {
        ModelMap model = new ModelMap();

        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
        model.put("isUsuarioLogueado",isUsuarioLogueado);

        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
        model.put("conductor", conductor);

        List<Viaje> viajesObtenidos = viajeServicio.obtenerViajesEnProceso(conductor);

        model.put("viajesObtenidos", viajesObtenidos);

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

        return new ModelAndView("viajes-aceptados", model);
    }

    //volver a ver el viaje aceptado desde viajes en proceso
    @RequestMapping(value = "/viajeAceptado", method = RequestMethod.GET)
    public ModelAndView verViaje(HttpServletRequest request, @RequestParam("idViaje") Integer idViaje) throws ConductorNoEncontradoException {
        ModelMap model = new ModelMap();

        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
        model.put("isUsuarioLogueado", isUsuarioLogueado);

        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
        model.put("conductor", conductor);

        Viaje viaje = viajeServicio.obtenerViajeAceptadoPorId(idViaje);
        model.put("viaje", viaje);

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

        return new ModelAndView("viaje", model);
    }

    //vuelve al home
    @RequestMapping("/volver")
    public ModelAndView volverAlHome(){
        return new ModelAndView("redirect:/home");
    }

    //cancela el viaje poniendo el boolean en true
    @RequestMapping("/cancelar-viaje")
    public ModelAndView cancelarViaje(@RequestParam("idViaje") Integer idViaje){
        Viaje viaje = viajeServicio.obtenerViajeAceptadoPorId(idViaje);
        viaje.setCancelado(true);
        viajeServicio.actualizarViaje(viaje);
        return new ModelAndView("redirect:/home");
    }

    //termina el viaje poniendo el boolean en true
    @RequestMapping("/terminar-viaje")
    public ModelAndView terminarViaje(@RequestParam("idViaje") Integer idViaje){
        Viaje viaje = viajeServicio.obtenerViajeAceptadoPorId(idViaje);
        viaje.setTerminado(true);
        viajeServicio.actualizarViaje(viaje);
        return new ModelAndView("redirect:/home");
    }

}