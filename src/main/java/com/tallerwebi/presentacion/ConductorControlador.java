package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.*;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.dominio.vehiculo.VehiculoServicio;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import com.tallerwebi.presentacion.Datos.DatosViaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@SessionAttributes("isUsuarioLogueado")
@MultipartConfig
public class ConductorControlador {
    private ConductorServicio conductorServicio;
    private VehiculoServicio vehiculoService;
    private ViajeServicio viajeServicio;
    private Double latitudActual = -34.668822; // VER
    private Double longitudActual =  -58.532878; // VER


    @Autowired
    public ConductorControlador(ConductorServicio conductorServicio, VehiculoServicio _vehiculoService, ViajeServicio _viajeServicio) {
        this.conductorServicio = conductorServicio;
        this.vehiculoService = _vehiculoService;
        this.viajeServicio = _viajeServicio;
    }

    @RequestMapping("/homeConductor")
    public ModelAndView mostrarHomeConductor(HttpSession session) throws UsuarioNoEncontradoException {
        ModelMap model = new ModelMap();
        String viewName = "home-conductor";
        Conductor conductor = conductorServicio.obtenerConductorPorId( (Integer) session.getAttribute("IDUSUARIO"));
        model.put("conductor", conductor);
//        List<DatosViaje> viajesCercanosPendientes;
//        Double distanciaAFiltrar = (Double) request.getSession().getAttribute("distancia");
//        if (conductor.getVehiculo() != null) {
//            viajesCercanosPendientes = this.viajeServicio.filtrarViajesPorDistanciaDelConductor(latitudActual, longitudActual, distanciaAFiltrar);
//            model.put("tieneVehiculo", false);
//        } else {
//            viajesCercanosPendientes = null;
//            model.put("tieneVehiculo", true);
//        }
//
//        request.getSession().setAttribute("isPenalizado", this.viajeServicio.estaPenalizado(conductor));

//        model.put("viajes", viajesCercanosPendientes);
        return new ModelAndView(viewName, model);
    }


    @RequestMapping(value = "/editar", method = RequestMethod.GET)
    public ModelAndView mostrarEditarConductor(HttpSession session) {
        session.setAttribute("isEditForm", true);
        return new ModelAndView("redirect:/registro-conductor");
    }

    @RequestMapping(path = "/foto-perfil", method = RequestMethod.GET)
    public ModelAndView irAEditarFotoPerfil(HttpSession session) {
        ModelMap model = new ModelMap();
        Boolean isUsuarioLogueado = (Boolean) session.getAttribute("isUsuarioLogueado");
        if(isUsuarioLogueado == null) {
            return new ModelAndView("redirect:/login");
        }
        Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");

        model.put("isUsuarioLogueado", isUsuarioLogueado);

        try {
            Conductor conductor = conductorServicio.obtenerConductorPorId(idUsuario);
            model.put("conductor", conductor);
        } catch (UsuarioNoEncontradoException e) {
            model.put("mensajeError", e.getMessage());
        }


        return new ModelAndView("foto-perfil", model);
    }



    /*@PostMapping("/editar-conductor")
    public ModelAndView editarConductor(HttpSession session, @ModelAttribute("conductor") Conductor conductorEditado) {
        Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");
        conductorEditado.setId(idUsuario);
        try {
            conductorServicio.editarConductor(conductorEditado);
            session.setAttribute("isEditForm", false);
        } catch (UsuarioNoEncontradoException e) {
            return this.mostrarFormConductor(e.getMessage(), session);
        }
        return new ModelAndView("redirect:/perfil");
    }

    @PostMapping("/subir-foto")
    public ModelAndView subirFoto(@RequestParam("imagenPerfil") MultipartFile imagen, HttpSession session) {
        Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");
        try {
            this.conductorServicio.ingresarImagen(imagen, idUsuario);
            return new ModelAndView("redirect:/perfil");
        } catch (UsuarioNoEncontradoException e) {
            return this.mostrarFormConductor(e.getMessage(), session);
        } catch (IOException e) {
            return this.mostrarFormConductor("Error al subir la imagen", session);
        }
    }

    @RequestMapping(value = "/borrar-cuenta", method = RequestMethod.GET)
    public ModelAndView borrarCuenta(HttpSession session) {
        try {
            conductorServicio.borrarConductor((Integer) session.getAttribute("IDUSUARIO"));
        } catch (UsuarioNoEncontradoException e) {
            return this.mostrarFormConductor(e.getMessage(), session);
        }
        return new ModelAndView("redirect:/cerrar-sesion");
    }*/


}
