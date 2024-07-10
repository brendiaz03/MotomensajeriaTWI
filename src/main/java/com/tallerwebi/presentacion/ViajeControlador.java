package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.exceptions.*;
import com.tallerwebi.dominio.enums.TipoEstado;
import com.tallerwebi.dominio.mercadoPago.MercadoPagoServicio;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.paquete.PaqueteServicio;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class ViajeControlador {

    private final ViajeServicio viajeServicio;
    private final ClienteServicio clienteServicio;
    private final PaqueteServicio paqueteServicio;
    private final MercadoPagoServicio mercadoPagoServicio;

    @Autowired
    public ViajeControlador(ViajeServicio viajeServicio, ClienteServicio clienteServicio, PaqueteServicio paqueteServicio, MercadoPagoServicio mercadoPagoServicio){
        this.viajeServicio = viajeServicio;
        this.clienteServicio = clienteServicio;
        this.paqueteServicio = paqueteServicio;
        this.mercadoPagoServicio = mercadoPagoServicio;
        
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
        ModelMap model = new ModelMap();

        try {
            if (viaje == null) {
                throw new ViajeNoEncontradoException("Viaje no encontrado");
            }

            session.setAttribute("isEditViaje", false);
            session.setAttribute("viajeActual", viaje);
            session.setAttribute("pasoActual", 3);
            return new ModelAndView("redirect:/form-viaje");
        } catch (ViajeNoEncontradoException e) {
            model.put("mensajeError", e.getMessage());
            return new ModelAndView("redirect:/*", model);
        }
    }

    @RequestMapping(value = "/crear-viaje", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public ModelAndView crearViajeLocalmente(@ModelAttribute("viaje") Viaje viaje, HttpSession session){
        ModelMap model = new ModelMap();

        try {
            if (viaje == null) {
                throw new ViajeNoEncontradoException("Viaje no encontrado");
            }

            session.setAttribute("viajeActual", viaje);
            session.setAttribute("pasoActual", 3);
            return new ModelAndView("redirect:/form-viaje");
        } catch (ViajeNoEncontradoException e) {
            model.put("mensajeError", e.getMessage());
            return new ModelAndView("redirect:/*", model);
        }
    }

    @RequestMapping(value = "/crear-envio")
    public ModelAndView crearViajeConPaqueteYCliente(HttpSession session) {
        ModelMap model = new ModelMap();
        Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");
        Paquete paqueteActual = (Paquete) session.getAttribute("paqueteActual");
        Viaje viajeActual = (Viaje) session.getAttribute("viajeActual");

        try {
            Cliente cliente = this.clienteServicio.obtenerClientePorId(idUsuario);
            this.paqueteServicio.guardarPaquete(paqueteActual);
            this.viajeServicio.crearViaje(cliente, viajeActual, paqueteActual);
            return new ModelAndView("redirect:/pagar?precio=" + viajeActual.getPrecio());
        } catch (PaqueteNoEncontradoException | ViajeNoEncontradoException | ClienteNoEncontradoException | PrecioInvalidoException | UsuarioNoEncontradoException e) {
            model.put("mensajeError", e.getMessage());
            return new ModelAndView("redirect:/*", model);
        }
    }

    @RequestMapping(value = "/pagar")
    public ModelAndView pagarViaje(@RequestParam("precio") Double precioDelViaje, HttpSession session) {
        ModelMap model = new ModelMap();

        try {

            if (precioDelViaje == null || precioDelViaje <= 0) {
                throw new PrecioInvalidoException("Valor Invalido");
            }

            String redirectUrl = mercadoPagoServicio.pagarViajeMp(precioDelViaje);
            Viaje viajeObtenido = (Viaje) session.getAttribute("viajeActual");
            Viaje viaje = this.viajeServicio.obtenerViajePorId(viajeObtenido.getId());
            viaje.setEstado(TipoEstado.PENDIENTE);
            this.viajeServicio.actualizarViaje(viaje);
            return new ModelAndView("redirect:" + redirectUrl);
        } catch (ViajeNoEncontradoException | PrecioInvalidoException | IOException e) {
            model.put("mensajeError", e.getMessage());
            return new ModelAndView("redirect:/*", model);
        }
    }
}