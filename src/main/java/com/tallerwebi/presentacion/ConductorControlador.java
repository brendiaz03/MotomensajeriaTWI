package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.*;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import com.tallerwebi.presentacion.Datos.DatosViaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ConductorControlador {
    private ConductorServicio conductorServicio;
    private ViajeServicio viajeServicio;

    @Autowired
    public ConductorControlador(ConductorServicio conductorServicio, ViajeServicio viajeServicio) {
        this.conductorServicio = conductorServicio;
        this.viajeServicio = viajeServicio;
    }

    @GetMapping("/homeConductor")
    public ModelAndView mostrarHomeConductor(HttpSession session) throws UsuarioNoEncontradoException {
        ModelMap model = new ModelMap();
        String viewName = "home-conductor";
        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) session.getAttribute("IDUSUARIO"));
        model.put("conductor", conductor);

        List<DatosViaje> viajesCercanosPendientes;

        Double distanciaAFiltrar = (Double) session.getAttribute("distancia");
        viajesCercanosPendientes = this.viajeServicio.filtrarViajesPorDistanciaDelConductor((Double)session.getAttribute("latitud"), (Double)session.getAttribute("longitud"), distanciaAFiltrar);
        session.setAttribute("isPenalizado", this.viajeServicio.estaPenalizado(conductor));
        model.put("viajes", viajesCercanosPendientes);
        return new ModelAndView(viewName, model);
    }

    @RequestMapping("/historial")
    public ModelAndView mostrarHistorial(HttpSession session) throws UsuarioNoEncontradoException {
        ModelMap model = new ModelMap();

        String viewName = "historial-viajes";
        String claveGoogleMaps = "AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg";

        Conductor conductor;

        try {
            conductor = conductorServicio.obtenerConductorPorId((Integer) session.getAttribute("IDUSUARIO"));
        } catch (UsuarioNoEncontradoException e) {
            model.put("error", "Conductor no encontrado");
            return new ModelAndView(viewName, model);
        }

        List<DatosViaje> historialViajes = this.viajeServicio.obtenerHistorialDeViajesConductor(conductor);

        model.put("clave", claveGoogleMaps);
        model.put("conductor", conductor);
        model.put("viajesObtenidos", historialViajes);
        return new ModelAndView(viewName, model);
    }

    @RequestMapping(value = "/viaje-aceptado", method = RequestMethod.GET)
    public ModelAndView AceptarViaje(HttpSession session, @RequestParam("idViaje") Integer idViaje) throws UsuarioNoEncontradoException {
        ModelMap model = new ModelMap();
        String viewName = "viaje";
        String claveGoogleMaps = "AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg";

        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) session.getAttribute("IDUSUARIO"));
        DatosViaje viaje = viajeServicio.obtenerViajeAceptadoPorId(idViaje);

//        if(viaje.getEstado() == TipoEstado.ACEPTADO) { // Va al service
//            model.put("error", "Viaje no disponible para ser aceptado");
//            return new ModelAndView("viaje", model);
//        }

        try {
            this.viajeServicio.aceptarViaje(viaje, conductor);
        } catch (Exception e) {
            model.put("error", "Error al aceptar el viaje");
            return new ModelAndView("viaje", model);
        }

        model.put("clave", claveGoogleMaps);
        model.put("conductor", conductor);
        model.put("viaje", viaje);
        return new ModelAndView(viewName, model);
    }

    @RequestMapping(value = "/viajes-en-proceso")
    public ModelAndView verViajesEnProceso(HttpSession session) {
        ModelMap model = new ModelMap();

        String viewName = "viajes-aceptados";
        Conductor conductor;

        try {
            conductor = conductorServicio.obtenerConductorPorId((Integer) session.getAttribute("IDUSUARIO"));
        } catch (UsuarioNoEncontradoException e) {
            model.put("error", "Conductor no encontrado");
            return new ModelAndView(viewName, model);
        }

        List<Viaje> viajesObtenidos = viajeServicio.obtenerViajesEnProceso(conductor);

        model.put("conductor", conductor);
        model.put("viajesObtenidos", viajesObtenidos);
        return new ModelAndView(viewName, model);
    }

    @RequestMapping(value = "/viajeAceptado", method = RequestMethod.GET)
    public ModelAndView verViaje(HttpSession session, @RequestParam("idViaje") Integer idViaje) throws UsuarioNoEncontradoException {
        ModelMap model = new ModelMap();

        String viewName = "viaje";
        String claveGoogleMaps = "AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg";
        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) session.getAttribute("IDUSUARIO"));

        DatosViaje viaje = viajeServicio.obtenerViajeAceptadoPorId(idViaje);

//        if (viaje.getEstado() != TipoEstado.CANCELADO && viaje.getEstado() != TipoEstado.TERMINADO && viaje.getEstado() != TipoEstado.DESCARTADO) {
//            model.put("error", "Viaje no disponible para ser visto");
//            return new ModelAndView(viewName, model);
//        } tiene que estar en el service

        model.put("clave", claveGoogleMaps);
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
    public ModelAndView descartarViaje(HttpSession session, @RequestParam("idViaje") Integer idViaje) throws UsuarioNoEncontradoException {
        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) session.getAttribute("IDUSUARIO"));
        this.viajeServicio.descartarViaje(idViaje, conductor);
        Boolean isPenalizado = this.viajeServicio.estaPenalizado(conductor);
        session.setAttribute("isPenalizado", isPenalizado);
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping("/detalle")
    public ModelAndView VerDetalleDelViaje(HttpSession session, @RequestParam("idViaje") Integer idViaje) throws UsuarioNoEncontradoException {
        ModelMap model = new ModelMap();

        String viewName = "detalle-viaje";
        String claveGoogleMaps = "AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg";
        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) session.getAttribute("IDUSUARIO"));

        DatosViaje viaje = viajeServicio.obtenerViajeAceptadoPorId(idViaje);

        model.put("clave", claveGoogleMaps);
        model.put("conductor", conductor);
        model.put("viaje", viaje);
        return new ModelAndView(viewName, model);
    }

    @RequestMapping("/volver-historial")
    public ModelAndView volverAlHistorial(){
        return new ModelAndView("redirect:/historial");
    }


    @RequestMapping(value = "/filtrarPorDistancia", method = RequestMethod.POST)
    public ModelAndView filtrarPorDistancia(HttpSession session, @RequestParam Double distancia) throws UsuarioNoEncontradoException {
            session.setAttribute("distancia", distancia);
            return mostrarHomeConductor(session);
    }
}
