package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.*;
import com.tallerwebi.dominio.imagen.IImageService;
import com.tallerwebi.dominio.imagen.Imagen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerConductor {

    private IServiceConductor iServiceConductor;
    private IImageService iimageService;

    @Autowired  // CON ESTO CONECTO CONTROLADOR A SERVICIO
    public ControllerConductor(IServiceConductor iServiceConductor, IImageService imageService) {

        this.iServiceConductor=iServiceConductor;
        this.iimageService=imageService;
    }

    @RequestMapping("/home")
    public ModelAndView mostrarHome(){
        String viewName= "home";
        ModelMap model = new ModelMap();
        Imagen logo = iimageService.getImagenByName("logo");
        model.put("logo", logo);
        Imagen auto = iimageService.getImagenByName("auto");
        model.put("auto", auto);
        Imagen fondo = iimageService.getImagenByName("fondo");
        model.put("fondo", fondo);
        Imagen botonPS = iimageService.getImagenByName("botonPS");
        model.put("botonPS", botonPS);
        return new ModelAndView(viewName,model);
    }
        @RequestMapping(value = "/registro-conductor")
        public ModelAndView mostrarRegistroConductor(String mensajeError){
        String viewName= "registro-conductor";
        ModelMap model = new ModelMap();
        model.put("conductor", new Conductor());
        if(mensajeError!=""){
            model.put("mensajeError", mensajeError);
        }
        Imagen logo = iimageService.getImagenByName("logo");
            model.put("logo", logo);
            Imagen auto = iimageService.getImagenByName("auto");
            model.put("auto", auto);
            Imagen fondo = iimageService.getImagenByName("fondo");
            model.put("fondo", fondo);
            Imagen botonPS = iimageService.getImagenByName("botonPS");
            model.put("botonPS", botonPS);
        return new ModelAndView(viewName,model);
    }

    @PostMapping("/registro-conductor")
    public ModelAndView registrarConductor(@ModelAttribute("conductor") Conductor nuevoConductor) throws Exception {
       try {
            if(iServiceConductor.verificarDatosDeRegistro(nuevoConductor)){
                return this.mostrarHome();
            }
       } catch (ConductorDuplicadoException e) {
           return this.mostrarRegistroConductor(e.getMessage());
       }
        return this.mostrarRegistroConductor("Se ha producido un error en el servidor.");
    }

}
