package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.paquete.PaqueteServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class PaqueteControlador {

    private PaqueteServicio paqueteServicio;

    @Autowired
    public PaqueteControlador(PaqueteServicio paqueteServicio) {
        this.paqueteServicio = paqueteServicio;
    }

    @RequestMapping(value = "/guardar-paquete", method = RequestMethod.POST)
    public ModelAndView guardarPaquete(@ModelAttribute("paquete") Paquete paquete) {

        this.paqueteServicio.guardarPaquete(paquete);

        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(value = "/editar-paquete", method = RequestMethod.GET)
    public ModelAndView mostrarEditarPaquete(HttpSession session, @ModelAttribute("paqueteId") Integer paqueteId) {
        Paquete paquete = paqueteServicio.obtenerPaquetePorId(paqueteId);
        ModelAndView modelAndView = new ModelAndView("form-paquete");
        modelAndView.addObject("paquete", paquete);
        modelAndView.addObject("isEditForm", true);
        return modelAndView;
    }


}
