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
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@SessionAttributes("isUsuarioLogueado")
@MultipartConfig
public class ConductorControlador {
    private ConductorServicio conductorServicio;
    private VehiculoServicio vehiculoService;
    private ViajeServicio viajeServicio;
//    private Double latitudActual = -34.668822; // VER
//    private Double longitudActual =  -58.532878; // VER


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
        String claveGoogleMaps = "AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg";
        Conductor conductor = conductorServicio.obtenerConductorPorId( (Integer) session.getAttribute("IDUSUARIO"));
        model.put("conductor", conductor);
        List<DatosViaje> viajesCercanosPendientes;
        //Double distanciaAFiltrar = (Double) session.getAttribute("distancia");
        //if (conductor.getVehiculo() != null) {
            //viajesCercanosPendientes = this.viajeServicio.filtrarViajesPorDistanciaDelConductor(latitudActual, longitudActual, 100.0);
            //model.put("tieneVehiculo", false);
        //} else {
            //viajesCercanosPendientes = null;
            //model.put("tieneVehiculo", true);
        //}

        //session.setAttribute("isPenalizado", this.viajeServicio.estaPenalizado(conductor));

        model.put("clave", claveGoogleMaps);
        //model.put("viajes", viajesCercanosPendientes);
        return new ModelAndView(viewName, model);
    }


}
