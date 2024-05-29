package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
public class AyudaControlador {

    private ConductorServicio conductorServicio;

    @Autowired
    public AyudaControlador(ConductorServicio _conductorServicio){
        conductorServicio = _conductorServicio;
    }

    @RequestMapping("/ayuda")
    public ModelAndView mostrarVistaAyuda(HttpServletRequest request) throws ConductorNoEncontradoException {
        ModelMap model = new ModelMap();

        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");

        Conductor conductor;

        model.put("isUsuarioLogueado",isUsuarioLogueado);
        if(request.getSession().getAttribute("IDUSUARIO") != null){
            conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
        }else{
            conductor = null;
        }
        model.put("conductor", conductor);
        return new ModelAndView("ayuda", model);
    }


}
