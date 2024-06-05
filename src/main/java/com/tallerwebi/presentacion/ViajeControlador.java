package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.usuario.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.imagen.ImagenServicio;
import com.tallerwebi.dominio.enums.TipoEstado;
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
    private final ImagenServicio imagenServicio;
    private final ConductorServicio conductorServicio;
    private final ClienteServicio clienteServicio;

    @Autowired
    public ViajeControlador(ViajeServicio viajeServicio, ConductorServicio conductorServicio, ImagenServicio imagenServicio, ClienteServicio clienteServicio){
        this.viajeServicio = viajeServicio;
        this.conductorServicio = conductorServicio;
        this.imagenServicio = imagenServicio;
        this.clienteServicio = clienteServicio;
    }
    @RequestMapping("/form-viaje")
    public ModelAndView mostrarFormViaje(HttpSession session, @ModelAttribute Viaje viaje){
        ModelMap model = new ModelMap();
        String viewName = "form-viaje";
        String claveGoogleMaps = "AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg";
        Imagen logo = imagenServicio.getImagenByName("logo");
        Imagen user = imagenServicio.getImagenByName("user");
        Imagen auto = imagenServicio.getImagenByName("auto");
        Imagen fondo = imagenServicio.getImagenByName("fondo");
        Imagen botonPS = imagenServicio.getImagenByName("botonPS");

        boolean isEditViaje = (session.getAttribute("isEditViaje") != null) ? (boolean) session.getAttribute("isEditViaje") : false;

        model.put("logo", logo);
        model.put("user", user);
        model.put("auto", auto);
        model.put("fondo", fondo);
        model.put("botonPS", botonPS);
        model.put("viaje", new DatosViaje());
        model.put("clave", claveGoogleMaps);
        model.put("isEditViaje", isEditViaje);



        if (isEditViaje){
            Viaje viajeEdit= this.viajeServicio.buscarViaje(viaje.getId());
            model.put("viajeEdit", viajeEdit);
        }
        return new ModelAndView(viewName, model);
    }

    @RequestMapping("/form-viaje-editor")
    public ModelAndView mostrarFormEditorViaje(HttpSession session){

        session.setAttribute("isEditViaje", true);
        return new ModelAndView("redirect:/form-viaje");
    }


    @RequestMapping(value = "/crear-viaje", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public ModelAndView crearViajeConPaqueteYCliente(@ModelAttribute("viaje") Viaje viaje){

        this.viajeServicio.crearViaje(null, viaje, null);

        return new ModelAndView("redirect:/form-viaje");
    }

    @RequestMapping(value = "/editar-viaje", method = RequestMethod.POST)
    public ModelAndView editarViaje(@ModelAttribute("viaje") Viaje viaje, HttpServletRequest request){

        this.viajeServicio.actualizarViaje(viaje);
        return new ModelAndView("redirect:/form-viaje");
    }
    @RequestMapping("/historial")
    public ModelAndView mostrarHistorial(HttpServletRequest request) throws UsuarioNoEncontradoException {
        ModelMap model = new ModelMap();

        String viewName = "historial-viajes";
        String claveGoogleMaps = "AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg";
        Imagen logo = imagenServicio.getImagenByName("logo");
        Imagen user = imagenServicio.getImagenByName("user");
        Imagen auto = imagenServicio.getImagenByName("auto");
        Imagen fondo = imagenServicio.getImagenByName("fondo");
        Imagen botonPS = imagenServicio.getImagenByName("botonPS");
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
        model.put("logo", logo);
        model.put("user", user);
        model.put("auto", auto);
        model.put("fondo", fondo);
        model.put("botonPS", botonPS);
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
        Imagen logo = imagenServicio.getImagenByName("logo");
        Imagen user = imagenServicio.getImagenByName("user");
        Imagen auto = imagenServicio.getImagenByName("auto");
        Imagen fondo = imagenServicio.getImagenByName("fondo");
        Imagen botonPS = imagenServicio.getImagenByName("botonPS");
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
        model.put("logo", logo);
        model.put("user", user);
        model.put("auto", auto);
        model.put("fondo", fondo);
        model.put("botonPS", botonPS);
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
        Imagen logo = imagenServicio.getImagenByName("logo");
        Imagen user = imagenServicio.getImagenByName("user");
        Imagen auto = imagenServicio.getImagenByName("auto");
        Imagen fondo = imagenServicio.getImagenByName("fondo");
        Imagen botonPS = imagenServicio.getImagenByName("botonPS");
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

        model.put("logo", logo);
        model.put("user", user);
        model.put("auto", auto);
        model.put("fondo", fondo);
        model.put("botonPS", botonPS);
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
        Imagen logo = imagenServicio.getImagenByName("logo");
        Imagen user = imagenServicio.getImagenByName("user");
        Imagen auto = imagenServicio.getImagenByName("auto");
        Imagen fondo = imagenServicio.getImagenByName("fondo");
        Imagen botonPS = imagenServicio.getImagenByName("botonPS");
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
        model.put("logo", logo);
        model.put("user", user);
        model.put("auto", auto);
        model.put("fondo", fondo);
        model.put("botonPS", botonPS);
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
        Imagen logo = imagenServicio.getImagenByName("logo");
        Imagen user = imagenServicio.getImagenByName("user");
        Imagen auto = imagenServicio.getImagenByName("auto");
        Imagen fondo = imagenServicio.getImagenByName("fondo");
        Imagen botonPS = imagenServicio.getImagenByName("botonPS");
        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));

        DatosViaje viaje = viajeServicio.obtenerViajeAceptadoPorId(idViaje);

        model.put("clave", claveGoogleMaps);
        model.put("logo", logo);
        model.put("user", user);
        model.put("auto", auto);
        model.put("fondo", fondo);
        model.put("botonPS", botonPS);
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