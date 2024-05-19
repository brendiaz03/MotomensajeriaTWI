package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.dominio.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ViajeController {

    private ViajeService viajeService;
    private List<Viaje> viajes;

    @Autowired
    public ViajeController(ViajeService viajeService){
        this.viajeService = viajeService;
    }

    @RequestMapping("/viajes")
    public ModelAndView mostrarVistaViaje() {
        ModelMap modelo = new ModelMap();
        this.viajes = this.viajeService.obtenerTodosLosViajesDeLaBaseDeDatos();
        modelo.put("viajesObtenidos", this.viajes);
        return new ModelAndView("viajes", modelo);
    }
}