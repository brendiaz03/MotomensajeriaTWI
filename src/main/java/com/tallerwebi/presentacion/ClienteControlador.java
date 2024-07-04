package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteServicio;

import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
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
        Cliente cliente;
        try{
             cliente = clienteServicio.obtenerClientePorId((Integer) session.getAttribute("IDUSUARIO"));
        }catch (UsuarioNoEncontradoException e){
            model.put("mensajeError", e.getMessage() + " Por favor, vuelva a intentarlo.");
            return new ModelAndView("redirect:/*", model);
        }
            List<Viaje> viajesCancelados = this.viajeServicio.obtenerViajesCanceladosDelCliente(cliente.getId());

            if(!viajesCancelados.isEmpty()) {
                model.put("hayViajesCancelados", true);
                model.put("viajes", viajesCancelados);
            }

            model.put("cliente", cliente);
            String viewName = "homeCliente";
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
        try {
            Cliente cliente = clienteServicio.obtenerClientePorId((Integer) session.getAttribute("IDUSUARIO"));
            model.put("cliente", cliente);
            List<Viaje> viajesEnProceso = this.viajeServicio.obtenerViajesEnProcesoDelCliente((Integer) session.getAttribute("IDUSUARIO"));

            if (viajesEnProceso.isEmpty()) {
                model.put("sinEnviosEnProceso", true);
                return new ModelAndView(viewName, model);
            }
            model.put("viajesEnProceso", viajesEnProceso);
            return new ModelAndView(viewName, model);
        }catch (UsuarioNoEncontradoException e){
            model.put("mensajeError", e.getMessage() + " Por favor, vuelva a intentarlo.");
            return new ModelAndView("redirect:/*", model);
        }
    }

    @RequestMapping(value = "/cancelar-envio", method = RequestMethod.POST)
    public ModelAndView cancelarEnvio(@RequestParam Integer idViaje) {
        Viaje viaje = viajeServicio.buscarViaje(idViaje);
        viajeServicio.cancelarEnvío(viaje);
        return new ModelAndView("redirect:/envios-en-proceso");
    }

    @RequestMapping("/duplicarViajeCancelado")
    public ModelAndView duplicarViajeCancelado(@RequestParam("idViaje") Integer idViaje) {
        Viaje viajeObtenido = this.viajeServicio.obtenerViajePorId(idViaje);
        this.viajeServicio.actualizarViajeCancelado(viajeObtenido);
        this.viajeServicio.duplicarViajeCancelado(viajeObtenido);
        return new ModelAndView("redirect:/homeCliente");
    }

    @RequestMapping("/noDuplicarViaje")
    public ModelAndView noDuplicarViaje(@RequestParam("idViaje") Integer idViaje) {
        Viaje viajeObtenido = this.viajeServicio.obtenerViajePorId(idViaje);
        this.viajeServicio.noDuplicarViaje(viajeObtenido);
        return new ModelAndView("redirect:/homeCliente");
    }

    @RequestMapping("/historial-envios")
    public ModelAndView mostrarHistorialEnvios(HttpSession session) {
        ModelMap model= new ModelMap();
        String viewName = "historial-envios";
        try {

            Integer idCliente = (Integer) session.getAttribute("IDUSUARIO");
            Cliente cliente = this.clienteServicio.obtenerClientePorId(idCliente);

            List<Viaje> viajesObtenidos = this.viajeServicio.obtenerHistorialDeEnvios(idCliente);
            model.put("cliente", cliente);

            if (viajesObtenidos.isEmpty()) {
                model.put("sinEnvios", true);
                return new ModelAndView(viewName, model);
            }

            model.put("viajesObtenidos", viajesObtenidos);
            return new ModelAndView(viewName, model);
        }catch (UsuarioNoEncontradoException e){
            model.put("mensajeError", e.getMessage() + " Por favor, vuelva a intentarlo.");
            return new ModelAndView("redirect:/*", model);
        }
    }

    @RequestMapping("/detalle-envio")
    public ModelAndView mostrarDetalleDelEnvio(@RequestParam ("idViaje") Integer idViaje, HttpSession session){
        ModelMap model= new ModelMap();
        String claveGoogleMaps = "AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg";
        try {
            Cliente cliente = this.clienteServicio.obtenerClientePorId((Integer) session.getAttribute("IDUSUARIO"));

            Viaje viaje = viajeServicio.obtenerViajePorId(idViaje);

            model.put("cliente", cliente);
            model.put("viaje", viaje);
            model.put("clave", claveGoogleMaps);
            return new ModelAndView("detalle-envio", model);
        }catch (UsuarioNoEncontradoException e){
            model.put("mensajeError", e.getMessage() + " Por favor, vuelva a intentarlo.");
            return new ModelAndView("redirect:/*", model);
        }
    }
}