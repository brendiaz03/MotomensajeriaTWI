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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ConductorController {

    private ConductorService conductorService;

    @Autowired
    public ConductorController(ConductorService conductorService) {
        this.conductorService = conductorService;
    }

    @RequestMapping(path = "/agregar-vehiculo", method = RequestMethod.GET)
    public ModelAndView mostrarFormAgregarVehiculo() {
        ModelMap model = new ModelMap();
        model.put("vehiculo", new Vehiculo());
        return new ModelAndView("nuevo-vehiculo", model);
    }

    //Este es el que quiero pero no funciona PTM
    @RequestMapping(path = "/agregar-vehiculo-conductor", method = RequestMethod.POST)
    public ModelAndView agregarVehiculoAConductor(Conductor conductor, Vehiculo datosVehiculo) {
        Vehiculo vehiculoCreado = obtenerDatosDelVehiculoYCrearlo(datosVehiculo);
        Conductor conductorActual = buscarConductorPorEmail(conductor.getEmail());
        conductorActual.actualizarIdVehiculo(vehiculoCreado.getIdVehiculo());
        this.conductorService.actualizarInfoDelConductor(conductorActual);
        return new ModelAndView("nuevo-vehiculo");
    }

    //Este funciona pero no es lo que quiero

    /*
    @RequestMapping(path = "/agregar-vehiculo-dos", method = RequestMethod.POST)
    public ModelAndView agregarVehiculo(Vehiculo datosVehiculo) {
        Vehiculo vehiculo = obtenerDatosDelVehiculoYCrearlo(datosVehiculo);
        this.conductorService.guardarVehiculo(vehiculo);
        return new ModelAndView("nuevo-vehiculo");
    }*/

    private Conductor buscarConductorPorEmail(String email) {
        return this.conductorService.buscarConductorPorEmail(email);
    }

    private Vehiculo obtenerDatosDelVehiculoYCrearlo(@ModelAttribute("vehiculo") Vehiculo datosVehiculo) {
        if (datosVehiculo == null || datosVehiculo.getTipoVehiculo() == null) {
            throw new IllegalArgumentException("Datos de vehículo incompletos");
        }

        switch (datosVehiculo.getTipoVehiculo()) {
            case AUTO:
                return new Auto(datosVehiculo);
            case MOTO:
                return new Moto(datosVehiculo);
            case CAMION:
                return new Camion(datosVehiculo);
            default:
                throw new IllegalArgumentException("Tipo de vehículo desconocido");
        }
    }
}
