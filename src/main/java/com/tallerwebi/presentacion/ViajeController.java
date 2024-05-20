package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.imagen.ImagenServicio;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ViajeController {

    private ViajeService viajeService;
    private List<Viaje> viajes;
    private ImagenServicio imagenServicio;
    private ConductorServicio conductorServicio;

    @Autowired
    public ViajeController(ViajeService viajeService, ConductorServicio conductorServicio, ImagenServicio imagenServicio){
        this.viajeService = viajeService;
        this.conductorServicio = conductorServicio;
        this.imagenServicio = imagenServicio;
    }

    @RequestMapping("/viajes")
    public ModelAndView mostrarVistaViaje() {
        ModelMap modelo = new ModelMap();
        this.viajes = this.viajeService.obtenerTodosLosViajesDeLaBaseDeDatos();
        modelo.put("viajesObtenidos", this.viajes);
        return new ModelAndView("viajes", modelo);
    }

    @RequestMapping("/ver-viaje")
    public ModelAndView verRecorridoDelViaje(HttpServletRequest request) throws ConductorNoEncontradoException {
        ModelMap modelo = new ModelMap();
        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
        modelo.put("conductor", conductor);
        Imagen logo = imagenServicio.getImagenByName("logo");
        modelo.put("logo", logo);
        Imagen user = imagenServicio.getImagenByName("user");
        modelo.put("user", user);
        Imagen auto = imagenServicio.getImagenByName("auto");
        modelo.put("auto", auto);
        Imagen fondo = imagenServicio.getImagenByName("fondo");
        modelo.put("fondo", fondo);
        Imagen botonPS = imagenServicio.getImagenByName("botonPS");
        modelo.put("botonPS", botonPS);
        modelo.put("isUsuarioLogueado",isUsuarioLogueado);
        return new ModelAndView("ver-viaje", modelo);
    }
}