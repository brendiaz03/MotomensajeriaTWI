package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.*;
import com.tallerwebi.dominio.imagen.IImageService;
import com.tallerwebi.dominio.imagen.Imagen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

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

    @RequestMapping(value = "/registro-conductor", method = RequestMethod.GET)
    public ModelAndView mostrarRegistroConductor(String mensajeError, HttpSession session) throws ConductorNoEncontradoException {

        String viewName= "registro-conductor";
        ModelMap model = new ModelMap();
        Imagen logo = iimageService.getImagenByName("logo");
        Imagen auto = iimageService.getImagenByName("auto");
        Imagen fondo = iimageService.getImagenByName("fondo");
        Imagen botonPS = iimageService.getImagenByName("botonPS");
        Imagen user = iimageService.getImagenByName("user");

        boolean isEditForm = (session.getAttribute("isEditForm") != null) ? (boolean) session.getAttribute("isEditForm") : false;

        model.put("logo", logo);
        model.put("auto", auto);
        model.put("fondo", fondo);
        model.put("botonPS", botonPS);
        model.put("conductor", new Conductor());
        model.put("isEditForm", isEditForm);
        model.put("user", user);

        if(!isEditForm) {
            if(mensajeError != ""){
                model.put("mensajeError", mensajeError);
            }
        } else {
            Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");
            Conductor conductor = iServiceConductor.obtenerConductorPorId(idUsuario);
            model.put("conductor", conductor );
        }
        return new ModelAndView(viewName, model);
    }

    @PostMapping("/registro-conductor")
    public ModelAndView registrarConductor(@ModelAttribute("conductor") Conductor nuevoConductor, HttpSession session) throws Exception {
       try {
            if(iServiceConductor.verificarDatosDeRegistro(nuevoConductor)){
                return new ModelAndView("redirect:/vehiculo");
            }
       } catch (ConductorDuplicadoException e) {
           return this.mostrarRegistroConductor(e.getMessage(),session);
       }
        return this.mostrarRegistroConductor("Se ha producido un error en el servidor.",session);
    }

    @RequestMapping(path = "/perfil", method = RequestMethod.GET)
    public ModelAndView irAPerfil(HttpSession session) throws ConductorNoEncontradoException {
        ModelMap model = new ModelMap();
        Boolean isUsuarioLogueado = (Boolean) session.getAttribute("isUsuarioLogueado");
        String nombre = (String) session.getAttribute("NOMBRE");
        String apellido = (String) session.getAttribute("APELLIDO");
        Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");

        Conductor conductor = iServiceConductor.obtenerConductorPorId(idUsuario);


        Imagen logo = iimageService.getImagenByName("logo");
        model.put("logo", logo);
        Imagen user = iimageService.getImagenByName("user");
        model.put("user", user);
        model.put("isUsuarioLogueado", isUsuarioLogueado);
        model.put("nombreUsuario", nombre);
        model.put("apellidoUsuario", apellido);
        model.put("idUsuario", idUsuario);
        model.put("conductor", conductor );
        return new ModelAndView("perfil-conductor",model);
    }

    @RequestMapping(value = "/editar", method = RequestMethod.GET)
    public String mostrarEditarConductor(HttpSession session) {
        session.setAttribute("isEditForm", true);
        return "redirect:/registro-conductor";
    }

    @PostMapping("/editar-conductor")
    public ModelAndView editarConductor(HttpSession session, @ModelAttribute("conductor") Conductor conductorEditado) {
        Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");
        conductorEditado.setId(idUsuario);
        iServiceConductor.editarConductor(conductorEditado);
        session.setAttribute("isEditForm", false);
        return new ModelAndView("redirect:/perfil");
    }

    @RequestMapping(value="/borrar-cuenta", method = RequestMethod.GET)
    public ModelAndView borrarCuenta (HttpSession session){

        System.out.println("CABRAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println((Integer) session.getAttribute("IDUSUARIO"));

        iServiceConductor.borrarConductor((Integer) session.getAttribute("IDUSUARIO"));

        //cerrar-sesion
        return new ModelAndView("redirect:/home");
    }


//    @RequestMapping(path = "/editar", method = RequestMethod.GET)
//    public ModelAndView mostrarEditarConductor(HttpSession session) throws ConductorNoEncontradoException {
//        ModelMap model = new ModelMap();
//        Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");
//        Imagen logo = iimageService.getImagenByName("logo");
//        Imagen user = iimageService.getImagenByName("user");
//
//        Conductor conductor = iServiceConductor.obtenerConductorPorId(idUsuario);
//        model.put("logo", logo);
//        model.put("user", user);
//        model.put("idUsuario", idUsuario);
//        model.put("conductor", conductor );
//        return new ModelAndView("editar-conductor",model);
//    }
//
//    @RequestMapping(path = "/editar-conductor", method = RequestMethod.PUT)
//    public ModelAndView editarConductor(@ModelAttribute("conductor") Conductor nuevoConductor, @RequestParam("idUsuario") Integer idUsuario)  {
//
//        iServiceConductor.editarConductor(idUsuario, nuevoConductor);
//        return new ModelAndView("redirect:/perfil");
//    }

}
