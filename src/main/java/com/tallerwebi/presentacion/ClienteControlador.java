package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.imagen.ImagenServicio;
import com.tallerwebi.dominio.paquete.Paquete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClienteControlador {

    private ClienteServicio clienteServicio;
    private ImagenServicio imagenServicio;

    @Autowired
    public ClienteControlador(ClienteServicio clienteServicio, ImagenServicio imagenServicio) {
        this.clienteServicio = clienteServicio;
        this.imagenServicio = imagenServicio;
    }

    @RequestMapping("/realizar-paquete")
    public ModelAndView mostrarFormArmarPaquete() {
        ModelMap model = new ModelMap();
        String viewName = "form-paquete";

        Imagen logo = imagenServicio.getImagenByName("logo");
        Imagen user = imagenServicio.getImagenByName("user");
        Imagen auto = imagenServicio.getImagenByName("auto");
        Imagen fondo = imagenServicio.getImagenByName("fondo");
        Imagen botonPS = imagenServicio.getImagenByName("botonPS");

        model.put("logo", logo);
        model.put("user", user);
        model.put("auto", auto);
        model.put("fondo", fondo);
        model.put("botonPS", botonPS);
        model.put("paquete", new Paquete());
        return new ModelAndView(viewName, model);
    }
}
