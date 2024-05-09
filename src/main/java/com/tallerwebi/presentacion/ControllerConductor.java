package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.IServiceConductor;
import com.tallerwebi.dominio.imagen.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("isUsuarioLogueado")
public class ControllerConductor {

    private IServiceConductor iServiceConductor;
    private IImageService iimageService;

    @Autowired  // CON ESTO CONECTO CONTROLADOR A SERVICIO
    public ControllerConductor(IServiceConductor iServiceConductor, IImageService imageService) {

        this.iServiceConductor=iServiceConductor;
        this.iimageService=imageService;
    }

        @RequestMapping(value = "/registro-conductor")
        public ModelAndView mostrarRegistroConductor(){
        String viewName= "registro-conductor";
        ModelMap model = new ModelMap();
        model.put("conductor", new Conductor());
        model.put("message","Bienvenido");
        return new ModelAndView(viewName,model);
    }

    @PostMapping("/registro-conductor")
    public ModelAndView registrarConductor(@ModelAttribute("conductor") Conductor nuevoConductor) throws Exception {
        ModelMap model=new ModelMap();
        if(iServiceConductor.verificarDatosDeRegistro(nuevoConductor).equals("Datos cargados con exito")){
            return this.mostrarRegistroConductor();
        };

        return new ModelAndView("redirect:/home",model);
    }

//    @RequestMapping(path = "/perfil", method = RequestMethod.GET)
//    public ModelAndView irAPerfil(HttpSession session) {
//        ModelMap model = new ModelMap();
//        Boolean isUsuarioLogueado = (Boolean) session.getAttribute("isUsuarioLogueado");
//        String nombre = (String) session.getAttribute("NOMBRE");
//        String apellido = (String) session.getAttribute("APELLIDO");
//        Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");
//        System.out.println(nombre + " " + apellido + " " + idUsuario);
//
//        //Conductor conductor = iServiceConductor.obtenerConductorPorId(idUsuario);
//        Imagen logo = iimageService.getImagenByName("logo");
//        model.put("logo", logo);
//        Imagen user = iimageService.getImagenByName("userIcon");
//        model.put("user", user);
//        model.put("isUsuarioLogueado", isUsuarioLogueado);
//        model.put("nombreUsuario", nombre);
//        model.put("apellidoUsuario", apellido);
//        model.put("idUsuario", idUsuario);
//        //model.put("conductor", conductor );
//        return new ModelAndView("perfil-conductor",model);
//    }

}
