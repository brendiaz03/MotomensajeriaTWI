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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ViajeControlador {

    private ViajeServicio viajeServicio;
    private List<Viaje> viajes;
    private ImagenServicio imagenServicio;
    private ConductorServicio conductorServicio;
    private HttpServletRequest request;

    @Autowired
    public ViajeControlador(ViajeServicio viajeServicio, ConductorServicio conductorServicio, ImagenServicio imagenServicio, HttpServletRequest request){
        this.viajeServicio = viajeServicio;
        this.conductorServicio = conductorServicio;
        this.imagenServicio = imagenServicio;
        this.request = request;
    }

    //viajes que serian el historial
    @RequestMapping("/viajes")
    public ModelAndView mostrarVistaViajesAceptados(HttpServletRequest request) throws ConductorNoEncontradoException {
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
        this.viajes = this.viajeServicio.obtenerLosViajesAceptadosPorElConductor(conductor.getId());
        model.put("viajesObtenidos", this.viajes);
        return new ModelAndView("viajes", model);
    }

    //una vez aceptado que te lleve al viaje con el mapa
    @RequestMapping(value = "/ver-viaje")
    public ModelAndView verVistaViaje(HttpServletRequest request, @RequestParam("idViaje") Integer idViaje) throws ConductorNoEncontradoException {
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
        model.put("idViaje", idViaje);
        return new ModelAndView("ver-viaje", model);
    }

    //una vez que se acepta va al viaje
    @RequestMapping("/aceptar")
    public ModelAndView aceptarViaje(HttpServletRequest request, @RequestParam("idViaje") Integer idViaje){
        ModelMap modelo = new ModelMap();
        Integer idConductor = (Integer) request.getSession().getAttribute("IDUSUARIO");
        this.viajeServicio.actualizarViajeConElIdDelConductorQueAceptoElViaje(idViaje, idConductor);
        modelo.put("idViaje", idViaje);
        return new ModelAndView("redirect:/ver-viaje");
    }

    //vuelve al home
    @RequestMapping("/volver")
    public ModelAndView volverAlHome(){
        return new ModelAndView("redirect:/home");
    }

    //cancela y vuelve al home
    @RequestMapping("/cancelar")
    public ModelAndView cancelarViaje(HttpServletRequest request, @RequestParam("idViaje") Integer idViaje){
        Integer idConductor = (Integer) request.getSession().getAttribute("IDUSUARIO");
        this.viajeServicio.actualizarViajeConElIdDelConductorQueAceptoElViajeYDespuesLoRechaza(idViaje, idConductor);
        return new ModelAndView("redirect:/home");
    }
}