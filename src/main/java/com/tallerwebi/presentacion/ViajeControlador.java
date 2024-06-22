package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.paquete.PaqueteNoEncontradoException;
import com.tallerwebi.dominio.paquete.PaqueteServicio;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;

@Controller
public class ViajeControlador {

    private final ViajeServicio viajeServicio;
    private final ConductorServicio conductorServicio;
    private final ClienteServicio clienteServicio;
    private final PaqueteServicio paqueteServicio;


    @Autowired
    public ViajeControlador(ViajeServicio viajeServicio, ConductorServicio conductorServicio, ClienteServicio clienteServicio, PaqueteServicio paqueteServicio){
        this.viajeServicio = viajeServicio;
        this.conductorServicio = conductorServicio;
        this.clienteServicio = clienteServicio;
        this.paqueteServicio = paqueteServicio;
    }
    @RequestMapping("/form-viaje")
    public ModelAndView mostrarFormViaje(HttpSession session) {
        ModelMap model = new ModelMap();
        String viewName = "form-viaje";
        String claveGoogleMaps = "AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg";
        boolean isEditViaje = (session.getAttribute("isEditViaje") != null) ? (boolean) session.getAttribute("isEditViaje") : false;
        boolean isEditPackage = (session.getAttribute("isEditPackage") != null) ? (boolean) session.getAttribute("isEditPackage") : false;


        Viaje viajeEnSession = (session.getAttribute("viajeActual") != null) ? (Viaje) session.getAttribute("viajeActual") : null;
        Paquete paqueteEnSession = (session.getAttribute("paqueteActual") != null) ? (Paquete) session.getAttribute("paqueteActual") : null;
        Integer pasoActual = (session.getAttribute("pasoActual") != null) ? (Integer) session.getAttribute("pasoActual") : 1;


        model.put("isEditViaje", isEditViaje);
        model.put("isEditPackage", isEditPackage);
        model.put("clave", claveGoogleMaps);
        model.put("pasoActual", pasoActual);

        if (viajeEnSession == null || isEditViaje) {
            model.put("viaje", new Viaje());
        } else {
            model.put("viaje", viajeEnSession);
        }

        if (paqueteEnSession == null && !isEditPackage) {
            model.put("paquete", new Paquete());
        } else {
            model.put("paquete", paqueteEnSession);
        }

        return new ModelAndView(viewName, model);
    }

    @RequestMapping("/form-editar-viaje")
    public ModelAndView mostrarFormEditorViaje(HttpSession session){
        session.setAttribute("isEditViaje", true);
        session.setAttribute("pasoActual", 2);
        return new ModelAndView("redirect:/form-viaje");
    }

    @RequestMapping(value = "/editar-viaje", method = RequestMethod.POST)
    public ModelAndView editarViaje(@ModelAttribute("viaje") Viaje viaje, HttpSession session){

        session.setAttribute("isEditViaje", false);
        session.setAttribute("viajeActual", viaje);
        session.setAttribute("pasoActual", 3);
        return new ModelAndView("redirect:/form-viaje");
    }
    @RequestMapping(value = "/crear-viaje", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public ModelAndView crearViajeLocalmente(@ModelAttribute("viaje") Viaje viaje, HttpSession session){
        session.setAttribute("viajeActual", viaje);
        session.setAttribute("pasoActual", 3);

        return new ModelAndView("redirect:/form-viaje");
    }

    @RequestMapping(value = "/crear-envio")
    public ModelAndView crearViajeConPaqueteYCliente(HttpSession session) throws PaqueteNoEncontradoException {

        //CLIENTE//
        Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");
        Cliente cliente=this.clienteServicio.obtenerClientePorId(idUsuario);

        //PAQUETE//
        Paquete paqueteActual = (Paquete)session.getAttribute("paqueteActual");

        try{
            this.paqueteServicio.guardarPaquete(paqueteActual);
        } catch (PaqueteNoEncontradoException e) {
            throw new PaqueteNoEncontradoException();
        }

        Viaje viajeActual = (Viaje)session.getAttribute("viajeActual");
        this.viajeServicio.crearViaje(cliente,viajeActual,paqueteActual);
        session.setAttribute("paqueteActual", null);    //HACERLO POST PAGAR --> Paso Actual=1
        session.setAttribute("viajeActual", null);
        session.setAttribute("pasoActual", 4);

        return new ModelAndView("redirect:/homeCliente");
    }

}