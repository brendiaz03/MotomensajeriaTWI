package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.paquete.PaqueteServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
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

    @RequestMapping(value = "/crear-paquete", method = RequestMethod.POST)
    public ModelAndView guardarPaquete(@ModelAttribute("paquete") Paquete paquete) {

        this.paqueteServicio.guardarPaquete(paquete);

        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(value = "/editar-paquete", method = RequestMethod.POST)
    public ModelAndView editarPaquete(@ModelAttribute("paquete") Paquete paquete, HttpSession session) {

        this.paqueteServicio.editarPaquete(paquete);

        session.setAttribute("isEditPackage", false);

        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(value = "/mostrar-form-paquete", method = RequestMethod.GET)
    public ModelAndView mostrarFormPaquete(HttpSession session, @ModelAttribute("paqueteId") Integer paqueteId) {

        ModelMap model = new ModelMap();

        Boolean isEditPackage = (session.getAttribute("isEditPackage") != null) ? (Boolean) session.getAttribute("isEditPackage") : false;

        model.put("isEditPackage", isEditPackage);

        if(isEditPackage){

            Paquete paquete = this.paqueteServicio.obtenerPaquetePorId(paqueteId);

            model.put("paquete", paquete);

        } else{
            model.put("paquete", new Paquete());
        }

        return new ModelAndView("form-paquete");

    }

    @RequestMapping(value = "/mostrar-editar-paquete", method = RequestMethod.GET)
    public ModelAndView mostrarEditarPaquete(HttpSession session, @ModelAttribute("paqueteId") Integer paqueteId) {

        session.setAttribute("isEditPackage", true);

        return new ModelAndView("redirect:/mostrar-form-paquete");

    }



}
