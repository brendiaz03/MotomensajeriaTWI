package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Datos.DatosViaje;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.dominio.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ViajeController {

    private ViajeService viajeService;
    List<Viaje> viajesObtenidos = new ArrayList<>();

    @Autowired
    public ViajeController(ViajeService viajeService){
        this.viajeService = viajeService;
    }

    public ViajeController(){

    }

    @RequestMapping("/viajes")
    public ModelAndView mostrarVistaViaje() {
        ModelMap modelo = new ModelMap();

        //Trabajar con DatosViaje
        //Joinear para traer el nombre del cliente.
        //En este codigo traigo todos los viajes existentes, pero despues tengo que traer los que el conductor acepta.

        //Este Integer debe ser session de IdCliente cuando se loguea
        Integer idCliente = 1;
        List<DatosViaje> viajesObtenidos = this.viajeService.obtenerLosViajesAceptadosPorElConductor(idCliente);

        //this.viajesObtenidos = this.viajeService.obtenerViajes();

        //this.viajesObtenidos = this.viajeService.obtenerLosViajesAceptadosPorElConductorConLosDatos(idCliente);
        modelo.put("viajesObtenidos", viajesObtenidos);
        return new ModelAndView("viajes", modelo);
    }

    public ResponseEntity<Viaje> obtenerDatosDelViaje(Integer idPaquete) {
        Viaje viaje = this.viajeService.obtenerViajePorIdPaquete(idPaquete);
        return new ResponseEntity<>(viaje, HttpStatus.OK);
    }
}