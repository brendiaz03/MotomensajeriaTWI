package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.paquete.Paquete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class ClienteControlador {

    private ClienteServicio clienteServicio;

    @Autowired
    public ClienteControlador(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    @RequestMapping("/homeCliente")
    public ModelAndView mostrarHomeCliente(HttpSession session) {
        ModelMap model = new ModelMap();
        Cliente cliente = clienteServicio.obtenerClientePorId((Integer) session.getAttribute("IDUSUARIO"));
        model.put("cliente", cliente);
        String viewName = "home-cliente";
        this.reiniciarVariables(session);
        return new ModelAndView(viewName, model);
    }

    public void reiniciarVariables(HttpSession session){
        session.setAttribute("isEditViaje", false);
        session.setAttribute("isEditPackage", false);
        session.setAttribute("viajeActual", null);
        session.setAttribute("paqueteActual", null);
        session.setAttribute("pasoActual", 1);
    }
}
