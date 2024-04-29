package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.ViajeService;
import com.tallerwebi.dominio.entidades.Viaje;
import com.tallerwebi.dominio.entidades.usuarios.Conductor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ViajeController {

    private ViajeService viajeService;


    @Autowired
    public ViajeController (ViajeService viajeService){
        this.viajeService=viajeService;
    }

    @RequestMapping (path="/vista-viaje", method= RequestMethod.GET)

    public ModelAndView mostrarVistaViaje (){
        ModelMap model=new ModelMap();
        model.put("viaje", new Viaje());
        return new ModelAndView("viaje", model);
    }


    @RequestMapping (path = "/calcular-distancia", method = RequestMethod.GET)
    public void calcularDistancia (Integer posicionInicial, Integer posicionFinal){

        this.viajeService.calcularDistancia(posicionInicial, posicionFinal);

    }

    @RequestMapping (path = "/buscar-conductor", method = RequestMethod.POST)
    public ModelAndView buscarConductor (Integer id){

        Conductor nuevo=this.viajeService.buscarConductor(id);
        if(nuevo==null){
            return mostrarVistaViaje();
        }else{
            return new ModelAndView("redirect:/home");
        }


    }
}
