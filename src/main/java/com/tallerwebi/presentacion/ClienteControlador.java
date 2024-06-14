package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteServicio;

import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import com.tallerwebi.presentacion.Datos.DatosViaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ClienteControlador {

    private ClienteServicio clienteServicio;
    private ViajeServicio viajeServicio;


    @Autowired
    public ClienteControlador(ClienteServicio clienteServicio, ViajeServicio viajeServicio) {
        this.clienteServicio = clienteServicio;
        this.viajeServicio=viajeServicio;
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

    @RequestMapping("/envios-en-proceso")
    public ModelAndView mostrarEnviosEnProceso(HttpSession session){
        ModelMap model= new ModelMap();
        String viewName= "envios-en-proceso";
        Cliente cliente = clienteServicio.obtenerClientePorId((Integer) session.getAttribute("IDUSUARIO"));
        model.put("cliente", cliente);
        List<Viaje> viajesEnProceso = this.viajeServicio.obtenerViajesEnProcesoDelCliente((Integer)session.getAttribute("IDUSUARIO"));
        model.put("viajesEnProceso", viajesEnProceso);


        return new ModelAndView(viewName, model);
    }

    @RequestMapping(value = "/cancelar-envio", method = RequestMethod.POST)
    public ModelAndView cancelarEnvio(@RequestParam Integer idViaje) {
        Viaje viaje = viajeServicio.buscarViaje(idViaje);
        viajeServicio.cancelarEnvío(viaje);
        return new ModelAndView("redirect:/envios-en-proceso");
    }

}
