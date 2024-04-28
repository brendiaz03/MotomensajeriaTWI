package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ViajeService;
import com.tallerwebi.dominio.entidades.Viaje;
import com.tallerwebi.dominio.entidades.usuarios.Cliente;
import com.tallerwebi.dominio.entidades.usuarios.Conductor;
import com.tallerwebi.dominio.entidades.vehiculos.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViajeController {

    private ViajeService viajeService;


    @Autowired
    public ViajeController (ViajeService viajeService){
        this.viajeService=viajeService;
    }

    @RequestMapping (path="/vista-viaje", method= RequestMethod.GET)

    public ModelAndView mostrarVistaViaje (){
        /* ModelMap model=new ModelMap();
        model.put("viaje", new Viaje());
        */
        return new ModelAndView("viaje");
    }

    @RequestMapping("/login-usuario")
    public ModelAndView mostrarLoginCliente() {
        ModelMap model = new ModelMap();
        model.put("cliente", new Cliente());
        return new ModelAndView("login-usuario", model);
    }


    @RequestMapping (path = "/calcular-distancia", method = RequestMethod.GET)
    public void calcularDistancia (Integer posicionInicial, Integer posicionFinal){

        this.viajeService.calcularDistancia(posicionInicial, posicionFinal);

    }

    @RequestMapping (path = "/buscar-conductor", method = RequestMethod.POST)
    public Conductor buscarConductor (Integer idConductor){
        return this.viajeService.buscarConductor(idConductor);
    }
}
