package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.IRepositorioConductor;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.IServiceConductor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ConductorController {
    //se le inyectan los metodos del service, de la interfaz del service
    //aca vamos a exponer los endpoint y modelandview para que se vean las pantallas en el server

    private IServiceConductor serviceConductor;

    @Autowired
    public ConductorController(IServiceConductor _serviceConductor){
        this.serviceConductor = _serviceConductor;
    }

    @RequestMapping("/home")
    public ModelAndView irAHome() {
        ModelMap modelo = new ModelMap();
        return new ModelAndView("home");
    }

    @RequestMapping("/form-conductor")
    public ModelAndView irAFormConductor() {
        ModelMap modelo = new ModelMap();
        modelo.addAttribute("conductor", new Conductor());
        return new ModelAndView("form-conductor", modelo);
    }

    @RequestMapping(path = "/registrar-conductor", method = RequestMethod.POST)
    public ModelAndView registrarConductor(@ModelAttribute("conductor") Conductor conductor) {
        ModelMap model = new ModelMap();
        try {
            serviceConductor.verificarIngresoConductor(conductor);
    }catch (Exception e){
            model.put("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("form-conductor", model);
        }
        return new ModelAndView("redirect:/home").addObject("success", "Conductor registrado exitosamente");
    }



}

