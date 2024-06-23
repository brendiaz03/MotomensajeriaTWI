package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.mercadoPago.MercadoPagoServicio;
import com.tallerwebi.dominio.mercadoPago.MercadoPagoServicioImpl;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.paquete.PaqueteNoEncontradoException;
import com.tallerwebi.dominio.paquete.PaqueteServicio;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class ViajeControlador {

    private final ViajeServicio viajeServicio;
    private final ConductorServicio conductorServicio;
    private final ClienteServicio clienteServicio;
    private final PaqueteServicio paqueteServicio;
   // private MercadoPagoServicio mercadoPagoServicio;
    private final MercadoPagoServicioImpl mercadoPagoServicio;

    @Autowired
    public ViajeControlador(ViajeServicio viajeServicio, ConductorServicio conductorServicio, ClienteServicio clienteServicio, PaqueteServicio paqueteServicio){
        this.viajeServicio = viajeServicio;
        this.conductorServicio = conductorServicio;
        this.clienteServicio = clienteServicio;
        this.paqueteServicio = paqueteServicio;
        //this.mercadoPagoServicio = mercadoPagoServicio;
        this.mercadoPagoServicio = new MercadoPagoServicioImpl();

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
    public String crearViajeConPaqueteYCliente(HttpSession session) throws PaqueteNoEncontradoException {
        // Obtiene el cliente y el paquete actual desde la sesión
        Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");
        Cliente cliente = this.clienteServicio.obtenerClientePorId(idUsuario);
        Paquete paqueteActual = (Paquete) session.getAttribute("paqueteActual");
        Viaje viajeActual = (Viaje) session.getAttribute("viajeActual");

        this.paqueteServicio.guardarPaquete(paqueteActual);
        this.viajeServicio.crearViaje(cliente, viajeActual, paqueteActual);

        // Redirección con el precio del viaje
        return "redirect:/pagar?precio=" + viajeActual.getPrecio();
    }

    @RequestMapping(value = "/pagar")
    public String pagarViaje(@RequestParam("precio") Double precioDelViaje, RedirectAttributes redirectAttributes, HttpSession session) {
        if (precioDelViaje == null || precioDelViaje < 0) {
            redirectAttributes.addFlashAttribute("error", "Precio inválido.");
        }

        try {
            String redirectUrl = mercadoPagoServicio.pagarViajeMp(precioDelViaje);
            return "redirect:" + redirectUrl;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al procesar el pago: " + e.getMessage());
            return "redirect:/homeCliente";
        }
    }

}