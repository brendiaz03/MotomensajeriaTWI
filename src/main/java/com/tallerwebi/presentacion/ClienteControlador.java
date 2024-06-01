package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.imagen.ImagenServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    /*@RequestMapping("/form-cliente")
    public ModelAndView homeCliente() {
        ModelMap model = new ModelMap();
        String viewName = "form-cliente";

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
        return new ModelAndView(viewName, model);
    }

    @PostMapping("/registrar-cliente")
    public ModelAndView registrarCliente(@ModelAttribute("cliente") Cliente cliente) {
        this.clienteServicio.registrarClienteNoDuplicado(cliente);
        return new ModelAndView("redirect:/home-cliente");
    }*/
}
