package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.*;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;

@RestController
public class ConductorControlador {
    private ConductorServicio conductorServicio;

    @Autowired
    public ConductorControlador(ConductorServicio conductorServicio) {
        this.conductorServicio = conductorServicio;
    }

    @GetMapping("/homeConductor")
    public ModelAndView mostrarHomeConductor(HttpSession session) throws UsuarioNoEncontradoException {
        ModelMap model = new ModelMap();
        String viewName = "home-conductor";

        // Aquí procesa la ubicación como lo necesites
        Conductor conductor = conductorServicio.obtenerConductorPorId((Integer) session.getAttribute("IDUSUARIO"));
        model.put("conductor", conductor);
        model.put("latitud",  session.getAttribute("latitud"));
        model.put("longitud", session.getAttribute("longitud"));
        // Puedes hacer más con los datos si es necesario

        return new ModelAndView(viewName, model);
    }



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
}
