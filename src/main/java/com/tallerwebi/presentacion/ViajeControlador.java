package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.enums.TipoEstado;
import com.tallerwebi.dominio.paquete.PaqueteNoEncontradoException;
import com.tallerwebi.dominio.paquete.PaqueteServicio;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import com.tallerwebi.presentacion.Datos.DatosViaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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

        if (viajeEnSession == null && !isEditViaje) {
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
        this.viajeServicio.actualizarViaje(viaje);
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
        //Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");
        //Cliente cliente=this.clienteServicio.obtenerClientePorId(idUsuario);

        //PAQUETE//
        Paquete paqueteActual = (Paquete)session.getAttribute("paqueteActual");

        try{
            this.paqueteServicio.guardarPaquete(paqueteActual);
        } catch (PaqueteNoEncontradoException e) {
            throw new PaqueteNoEncontradoException();
        }

        Viaje viajeActual = (Viaje)session.getAttribute("viajeActual");
        this.viajeServicio.crearViaje(null,viajeActual,paqueteActual);
        session.setAttribute("paqueteActual", null);    //HACERLO POST PAGAR --> Paso Actual=1
        session.setAttribute("viajeActual", null);
        session.setAttribute("pasoActual", 4);

        return new ModelAndView("redirect:/homeCliente");
    }

    @RequestMapping("/historial")
    public ModelAndView mostrarHistorial(HttpServletRequest request) throws UsuarioNoEncontradoException {
        ModelMap model = new ModelMap();

        String viewName = "historial-viajes";
        String claveGoogleMaps = "AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg";
        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");

        Conductor conductor;

        if(isUsuarioLogueado == null) {
            return new ModelAndView("redirect:/login");
        }

        try {
            conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
        } catch (UsuarioNoEncontradoException e) {
            model.put("error", "Conductor no encontrado");
            return new ModelAndView(viewName, model);
        }

        List<DatosViaje> historialViajes = this.viajeServicio.obtenerHistorialDeViajes(conductor);

        if(historialViajes == null || historialViajes.isEmpty()) {
            model.put("sinViajes", "No hay viajes en el historial");
        }

        model.put("clave", claveGoogleMaps);
        model.put("isUsuarioLogueado",isUsuarioLogueado);
        model.put("conductor", conductor);
        model.put("viajesObtenidos", historialViajes);
        return new ModelAndView(viewName, model);
    }

    @RequestMapping(value = "/viaje-aceptado", method = RequestMethod.GET)
    public ModelAndView AceptarViaje(HttpServletRequest request, @RequestParam("idViaje") Integer idViaje) throws UsuarioNoEncontradoException {
        ModelMap model = new ModelMap();

        String viewName = "viaje";
        String claveGoogleMaps = "AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg";

        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));

        if(idViaje == null || idViaje <= 0) {
            model.put("error", "ID de viaje inválido");
            return new ModelAndView("viaje", model);
        }

        DatosViaje viaje = viajeServicio.obtenerViajeAceptadoPorId(idViaje);

        if(viaje == null) {
            model.put("error", "Viaje no encontrado");
            return new ModelAndView("viaje", model);
        }

        if(viaje.getEstado() == TipoEstado.ACEPTADO) { // Va al service
            model.put("error", "Viaje no disponible para ser aceptado");
            return new ModelAndView("viaje", model);
        }

        try {
            this.viajeServicio.aceptarViaje(viaje, conductor);
        } catch (Exception e) {
            model.put("error", "Error al aceptar el viaje");
            return new ModelAndView("viaje", model);
        }

        model.put("clave", claveGoogleMaps);
        model.put("isUsuarioLogueado",isUsuarioLogueado);
        model.put("conductor", conductor);
        model.put("idViaje", viaje.getIdViaje());
        model.put("viaje", viaje);
        return new ModelAndView(viewName, model);
    }

    @RequestMapping(value = "/viajes-en-proceso")
    public ModelAndView verViajesEnProceso(HttpServletRequest request) {
        ModelMap model = new ModelMap();

        String viewName = "viajes-aceptados";
        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
        Conductor conductor;

        if(isUsuarioLogueado == null) {
            return new ModelAndView("redirect:/login");
        }

        try {
            conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
        } catch (UsuarioNoEncontradoException e) {
            model.put("error", "Conductor no encontrado");
            return new ModelAndView(viewName, model);
        }

        List<Viaje> viajesObtenidos = viajeServicio.obtenerViajesEnProceso(conductor);

        if(viajesObtenidos == null || viajesObtenidos.isEmpty()) {
            model.put("sinViajes", "No hay viajes en proceso");
        }

        model.put("isUsuarioLogueado",isUsuarioLogueado);
        model.put("conductor", conductor);
        model.put("viajesObtenidos", viajesObtenidos);
        return new ModelAndView(viewName, model);
    }

    @RequestMapping(value = "/viajeAceptado", method = RequestMethod.GET)
    public ModelAndView verViaje(HttpServletRequest request, @RequestParam("idViaje") Integer idViaje) throws UsuarioNoEncontradoException {
        ModelMap model = new ModelMap();

        String viewName = "viaje";
        String claveGoogleMaps = "AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg";
        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));

        /*if (idViaje == null || idViaje <= 0) {
            model.put("error", "ID de viaje inválido");
            return new ModelAndView(viewName, model);
        }*/

        DatosViaje viaje = viajeServicio.obtenerViajeAceptadoPorId(idViaje);
/*
        if (viaje == null) {
            model.put("error", "Viaje no encontrado");
            return new ModelAndView(viewName, model);
        }

        if (viaje.getEstado() != TipoEstado.CANCELADO && viaje.getEstado() != TipoEstado.TERMINADO && viaje.getEstado() != TipoEstado.DESCARTADO) {
            model.put("error", "Viaje no disponible para ser visto");
            return new ModelAndView(viewName, model);
        }*/

        model.put("clave", claveGoogleMaps);
        model.put("isUsuarioLogueado", isUsuarioLogueado);
        model.put("conductor", conductor);
        model.put("viaje", viaje);
        return new ModelAndView(viewName, model);
    }

    @RequestMapping("/cancelar-viaje")
    public ModelAndView cancelarViaje(@RequestParam("idViaje") Integer idViaje){
        DatosViaje viaje = viajeServicio.obtenerViajeAceptadoPorId(idViaje);

        viajeServicio.cancelarViaje(viaje);

        return new ModelAndView("redirect:/home");
    }

    @RequestMapping("/terminar-viaje")
    public ModelAndView terminarViaje(@RequestParam("idViaje") Integer idViaje){
        DatosViaje viaje = viajeServicio.obtenerViajeAceptadoPorId(idViaje);

        viajeServicio.terminarViaje(viaje);

        return new ModelAndView("redirect:/home");
    }

    @RequestMapping("/volver")
    public ModelAndView volverAlHome(){
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping("/descartar")
    public ModelAndView descartarViaje(HttpServletRequest request, @RequestParam("idViaje") Integer idViaje) throws UsuarioNoEncontradoException {
        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));

        this.viajeServicio.descartarViaje(idViaje, conductor);
        Boolean isPenalizado = this.viajeServicio.estaPenalizado(conductor);
        request.getSession().setAttribute("isPenalizado", isPenalizado);

        return new ModelAndView("redirect:/home");
    }

    @RequestMapping("/detalle")
    public ModelAndView VerDetalleDelViaje(HttpServletRequest request, @RequestParam("idViaje") Integer idViaje) throws UsuarioNoEncontradoException {
        ModelMap model = new ModelMap();

        String viewName = "detalle-viaje";
        String claveGoogleMaps = "AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg";
        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));

        DatosViaje viaje = viajeServicio.obtenerViajeAceptadoPorId(idViaje);

        model.put("clave", claveGoogleMaps);
        model.put("isUsuarioLogueado",isUsuarioLogueado);
        model.put("conductor", conductor);
        model.put("idViaje", viaje.getIdViaje());
        model.put("viaje", viaje);
        return new ModelAndView(viewName, model);
    }

    @RequestMapping("/volver-historial")
    public ModelAndView volverAlHistorial(){
        return new ModelAndView("redirect:/historial");
    }

}