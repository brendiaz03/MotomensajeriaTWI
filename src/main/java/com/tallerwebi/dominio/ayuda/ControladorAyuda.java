package com.tallerwebi.dominio.ayuda;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.imagen.ImagenServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ControladorAyuda {

    private IServiceAyuda serviceAyuda;

    private ConductorServicio conductorServicio;

    private ImagenServicio imagenServicio;

    @Autowired
    public ControladorAyuda(IServiceAyuda serviceAyuda, ConductorServicio conductorServicio, ImagenServicio imagenServicio){
        this.serviceAyuda = serviceAyuda;
        this.conductorServicio = conductorServicio;
        this.imagenServicio = imagenServicio;
    }

    @RequestMapping ("/ayuda")
    public ModelAndView mostrarVistaAyuda(HttpServletRequest request) throws ConductorNoEncontradoException {
        ModelMap model = new ModelMap();

        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");

        Conductor conductor;

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
        return new ModelAndView("ayuda", model);
    }


}
