package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.exceptions.ConductorNoEncontradoException;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CompaniaControlador {

    private ConductorServicio conductorServicio;

    @Autowired
    public CompaniaControlador(ConductorServicio conductorServicio){
        this.conductorServicio = conductorServicio;
    }

//    @RequestMapping("/compania")
//    public ModelAndView mostrarVistaCompania(HttpServletRequest request) throws UsuarioNoEncontradoException {
//        ModelMap model = new ModelMap();
//        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
//
//        Conductor conductor;
//
//        model.put("isUsuarioLogueado",isUsuarioLogueado);
//        if(request.getSession().getAttribute("IDUSUARIO") != null){
//            conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
//        }else{
//            conductor = null;
//        }
//        model.put("conductor", conductor);
//        return new ModelAndView("compania", model);
//    }

}
