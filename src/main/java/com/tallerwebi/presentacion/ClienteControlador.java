package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.paquete.Paquete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClienteControlador {

    private ClienteServicio clienteServicio;

    @Autowired
    public ClienteControlador(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    @RequestMapping("/homeCliente")
    public ModelAndView mostrarHomeCliente() {
        String viewName = "home-cliente";
        return new ModelAndView(viewName);
    }
}
