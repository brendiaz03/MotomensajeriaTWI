package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.IServiceConductor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerConductor {

    private IServiceConductor iServiceConductor;

    @Autowired  // CON ESTO CONECTO CONTROLADOR A SERVICIO
    public ControllerConductor(IServiceConductor iServiceConductor) {
        this.iServiceConductor=iServiceConductor;
    }

    @RequestMapping("/home")
    public ModelAndView mostrarHome(){
        String viewName= "home";
        ModelMap model = new ModelMap();
        return new ModelAndView(viewName,model);
    }
        @RequestMapping("/registro-conductor")
        public ModelAndView mostrarRegistroConductor(){
        String viewName= "registro-conductor";
        ModelMap model = new ModelMap();
        model.put("conductor", new Conductor());
        model.put("message","Bienvenido");
        return new ModelAndView(viewName,model);
    }

//    public ModelAndView obtenerDatosDelFormulario(Conductor nuevoConductor) throws Exception {
//        iServiceConductor.verificarDatosDeRegistro(nuevoConductor);
//
//        return new ModelAndView("redirect:/home");
//    }

    @PostMapping("/registro-conductor")
    public ModelAndView registrarConductor(@ModelAttribute("conductor") Conductor nuevoConductor) throws Exception {
        ModelMap model=new ModelMap();
        if(iServiceConductor.verificarDatosDeRegistro(nuevoConductor).equals("Datos cargados con exito")){
            return this.mostrarRegistroConductor();
        };

        return new ModelAndView("redirect:/home",model);
    }

//    public ModelAndView registrarConductor(Conductor nuevoConductor, org.springframework.mock.web.MockHttpServletRequest request) {
//
//
//    }
}
