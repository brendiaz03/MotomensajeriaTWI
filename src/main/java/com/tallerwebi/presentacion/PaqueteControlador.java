package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.imagen.ImagenServicio;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.paquete.PaqueteNoEncontradoException;
import com.tallerwebi.dominio.paquete.PaqueteServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class PaqueteControlador {

    private PaqueteServicio paqueteServicio;

    private ImagenServicio imagenServicio;


    @Autowired
    public PaqueteControlador(PaqueteServicio paqueteServicio, ImagenServicio imagenServicio) {
        this.paqueteServicio = paqueteServicio;
        this.imagenServicio = imagenServicio;
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
    public ModelAndView mostrarFormPaquete(HttpSession session, @RequestParam(value = "paqueteId", required = false) Integer paqueteId) throws PaqueteNoEncontradoException {

        ModelMap model = new ModelMap();

        String view = "form-paquete";

        Boolean isEditPackage = (session.getAttribute("isEditPackage") != null) ? (Boolean) session.getAttribute("isEditPackage") : false;

        Imagen logo = imagenServicio.getImagenByName("logo");

        Imagen fondo = imagenServicio.getImagenByName("fondo");

        model.put("isEditPackage", isEditPackage);
        model.put("logo", logo);
        model.put("fondo", fondo);

        if(isEditPackage){

            Paquete paquete = this.paqueteServicio.obtenerPaquetePorId(paqueteId);

            model.put("paquete", paquete);

        } else{
            model.put("paquete", new Paquete());
        }

        return new ModelAndView(view, model);

    }

    @RequestMapping(value = "/mostrar-editar-paquete", method = RequestMethod.GET)
    public ModelAndView mostrarEditarPaquete(HttpSession session,  @RequestParam(value = "paqueteId", required = false) Integer paqueteId) {

        session.setAttribute("isEditPackage", true);

        return new ModelAndView("redirect:/mostrar-form-paquete");

    }



}
