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

    @RequestMapping(value = "/form-editar-paquete")
    public ModelAndView mostrarFormEditorPaquete(HttpSession session) {
        session.setAttribute("isEditPackage", true);
        session.setAttribute("pasoActual", 1);
        return new ModelAndView("redirect:/form-viaje");
    }

    @RequestMapping(value = "/crear-paquete", method = RequestMethod.POST)
    public ModelAndView guardarPaqueteLocalmente(@ModelAttribute("paquete") Paquete paquete, HttpSession session) {
        session.setAttribute("paqueteActual", paquete);
        session.setAttribute("pasoActual", 2); // Actualizar pasoActual a 2
        return new ModelAndView("redirect:/form-viaje");
    }

    @RequestMapping(value = "/editar-paquete")
    public ModelAndView editarPaquete(@ModelAttribute("paquete") Paquete paquete, HttpSession session) {
        session.setAttribute("isEditPackage", false);
        session.setAttribute("paqueteActual", paquete);
        session.setAttribute("pasoActual", 3);
        return new ModelAndView("redirect:/form-viaje");
    }
}
