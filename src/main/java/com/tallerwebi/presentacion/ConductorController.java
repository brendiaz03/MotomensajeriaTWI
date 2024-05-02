package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ConductorService;
import com.tallerwebi.dominio.entidades.usuarios.Conductor;
import com.tallerwebi.dominio.entidades.vehiculos.Auto;
import com.tallerwebi.dominio.entidades.vehiculos.Camion;
import com.tallerwebi.dominio.entidades.vehiculos.Moto;
import com.tallerwebi.dominio.entidades.vehiculos.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ConductorController {

    private ConductorService conductorService;

    @Autowired
    public ConductorController(ConductorService conductorService) {
        this.conductorService = conductorService;
    }

    @RequestMapping(path = "/asociar-vehiculo", method = RequestMethod.GET)
    public ModelAndView mostrarFormAgregarVehiculo() {
        ModelMap model = new ModelMap();
        model.put("vehiculo", new Vehiculo());
        return new ModelAndView("nuevo-vehiculo", model);
    }

    //Este es el que quiero pero no funciona PTM
    @RequestMapping(path = "/agregar-vehiculo-conductor", method = RequestMethod.POST)
    public ModelAndView agregarVehiculoAConductor(@ModelAttribute Vehiculo datosVehiculo, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("EMAIL");
        Conductor conductorActual = buscarConductorPorEmail(email);
        this.conductorService.guardarVehiculo(datosVehiculo);
        conductorActual.actualizarIdVehiculo(datosVehiculo.getIdVehiculo());
        this.conductorService.actualizarInfoDelConductor(conductorActual);
        return new ModelAndView("redirect:/home");
    }

    private Conductor buscarConductorPorEmail(String email) {
        return this.conductorService.buscarConductorPorEmail(email);
    }
}
