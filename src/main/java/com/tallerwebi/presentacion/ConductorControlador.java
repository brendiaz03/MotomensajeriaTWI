package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.*;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.mercadoPago.MercadoPagoServicio;
import com.tallerwebi.dominio.mercadoPago.MercadoPagoServicioImpl;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import com.tallerwebi.presentacion.Datos.DatosViaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ConductorControlador {
    private ConductorServicio conductorServicio;
    private ViajeServicio viajeServicio;
    private final MercadoPagoServicio mercadoPagoServicio;

    @Autowired
    public ConductorControlador(ConductorServicio conductorServicio, ViajeServicio viajeServicio) {
        this.conductorServicio = conductorServicio;
        this.viajeServicio = viajeServicio;
        this.mercadoPagoServicio = new MercadoPagoServicioImpl();
    }

    @GetMapping("/homeConductor")
    public ModelAndView mostrarHomeConductor(HttpSession session) throws UsuarioNoEncontradoException {
        ModelMap model = new ModelMap();
        String viewName = "home-conductor";
        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) session.getAttribute("IDUSUARIO"));
        model.put("conductor", conductor);

        List<DatosViaje> viajesCercanosPendientes;

        Double distanciaAFiltrar = (Double) session.getAttribute("distancia");
        viajesCercanosPendientes = this.viajeServicio.filtrarViajesPorDistanciaDelConductor(
                (Double)session.getAttribute("latitud"),
                (Double)session.getAttribute("longitud"),
                distanciaAFiltrar, conductor);
        model.put("viajes", viajesCercanosPendientes);
        model.put("cantidadDeViajes", viajesCercanosPendientes.size());

        if(conductor.getPenalizado()){
            model.put("isPenalizado", conductor.getPenalizado());
        }

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

        model.put("clave", claveGoogleMaps);
        model.put("conductor", conductor);
        model.put("viaje", viaje);
        return new ModelAndView(viewName, model);
    }

    @RequestMapping("/cancelar-viaje")
    public ModelAndView cancelarViaje(HttpSession session, @RequestParam("idViaje") Integer idViaje) throws UsuarioNoEncontradoException {
        DatosViaje viaje = viajeServicio.obtenerViajeAceptadoPorId(idViaje);
        viajeServicio.cancelarViaje(viaje);
        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) session.getAttribute("IDUSUARIO"));
        this.conductorServicio.estaPenalizado(conductor);
        return new ModelAndView("redirect:/homeConductor");
    }

    @RequestMapping("/terminar-viaje")
    public ModelAndView terminarViaje(@RequestParam("idViaje") Integer idViaje){
        DatosViaje viaje = viajeServicio.obtenerViajeAceptadoPorId(idViaje);
        viajeServicio.terminarViaje(viaje);
        return new ModelAndView("redirect:/homeConductor");
    }

    @RequestMapping("/volver")
    public ModelAndView volverAlHome(){
        return new ModelAndView("redirect:/ubicacion");
    }

    @RequestMapping("/descartar")
    public ModelAndView descartarViaje(HttpSession session, @RequestParam("idViaje") Integer idViaje) throws UsuarioNoEncontradoException {
        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) session.getAttribute("IDUSUARIO"));
        this.viajeServicio.duplicarViajeDescartado(this.viajeServicio.obtenerViajePorId(idViaje), conductor);
        this.conductorServicio.estaPenalizado(conductor);
        return new ModelAndView("redirect:/homeConductor");
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
            return new ModelAndView("redirect:/homeConductor");
    }

    @RequestMapping(path = "/ubicacion")
    public ModelAndView ubicacion(){
        String viewName= "ubicacion";
        return new ModelAndView(viewName);
    }

    @PostMapping("/despenalizar")
    public ModelAndView despenalizarConductor(HttpSession session, @RequestParam("conductorId") Integer conductorId) throws UsuarioNoEncontradoException {

        this.conductorServicio.despenalizarConductor(conductorServicio.obtenerConductorPorId(conductorId));

        return new ModelAndView("redirect:/homeConductor");
    }
  /*  @RequestMapping(value = "/despenalizar")
    public String despenalizarConductor(@RequestParam("montoPenalizacion") Double montoPenalizacion,
                                        RedirectAttributes redirectAttributes, HttpSession session) {

        if (montoPenalizacion == null || montoPenalizacion < 0) {
            redirectAttributes.addFlashAttribute("error", "Monto de penalización inválido.");
            return "redirect:/homeConductor";
        }

        try {
            String redirectUrl = mercadoPagoServicio.pagarPenalizacionMp(montoPenalizacion);
            return "redirect:" + redirectUrl;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al procesar el pago: " + e.getMessage());
            return "redirect:/homeConductor";
        }
    }*/

}
