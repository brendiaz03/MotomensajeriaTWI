package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.*;
import com.tallerwebi.dominio.imagen.ImagenServicio;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.dominio.vehiculo.VehiculoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@SessionAttributes("isUsuarioLogueado")
@MultipartConfig
public class ConductorControlador {
    private ConductorServicio conductorServicio;
    private ImagenServicio iimageService;
    private VehiculoServicio vehiculoService;


    @Autowired
    public ConductorControlador(ConductorServicio conductorServicio, ImagenServicio imageService, VehiculoServicio _vehiculoService) {
        this.conductorServicio = conductorServicio;
        this.iimageService = imageService;
        this.vehiculoService = _vehiculoService;
    }

    @RequestMapping(path = "/perfil", method = RequestMethod.GET)
    public ModelAndView irAPerfil(HttpSession session) {


        ModelMap model = new ModelMap();
        Boolean isUsuarioLogueado = (Boolean) session.getAttribute("isUsuarioLogueado");
        if(isUsuarioLogueado == null) {
            return new ModelAndView("redirect:/login");
        }
        Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");
        Imagen logo = iimageService.getImagenByName("logo");
        Imagen user = iimageService.getImagenByName("user");

        model.put("isUsuarioLogueado", isUsuarioLogueado);
        model.put("logo", logo);
        model.put("user", user);

        try {
            Conductor conductor = conductorServicio.obtenerConductorPorId(idUsuario);
            Vehiculo vehiculo = conductor.getVehiculo();
            if (vehiculo!=null){
            session.setAttribute("idVehiculo", vehiculo.getId());
            model.put("vehiculo", vehiculo);
            }
            model.put("conductor", conductor);
        } catch (ConductorNoEncontradoException e) {
            model.put("mensajeError", e.getMessage());
        }

        return new ModelAndView("perfil-conductor", model);
    }

    @RequestMapping(value = "/editar", method = RequestMethod.GET)
    public ModelAndView mostrarEditarConductor(HttpSession session) {
        session.setAttribute("isEditForm", true);
        return new ModelAndView("redirect:/registro-conductor");
    }

    @RequestMapping(path = "/foto-perfil", method = RequestMethod.GET)
    public ModelAndView irAEditarFotoPerfil(HttpSession session) {
        ModelMap model = new ModelMap();
        Boolean isUsuarioLogueado = (Boolean) session.getAttribute("isUsuarioLogueado");
        if(isUsuarioLogueado == null) {
            return new ModelAndView("redirect:/login");
        }
        Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");
        Imagen logo = iimageService.getImagenByName("logo");
        Imagen user = iimageService.getImagenByName("user");

        model.put("logo", logo);
        model.put("user", user);
        model.put("isUsuarioLogueado", isUsuarioLogueado);

        try {
            Conductor conductor = conductorServicio.obtenerConductorPorId(idUsuario);
            model.put("conductor", conductor);
        } catch (ConductorNoEncontradoException e) {
            model.put("mensajeError", e.getMessage());
        }


        return new ModelAndView("foto-perfil", model);
    }



    /*@PostMapping("/editar-conductor")
    public ModelAndView editarConductor(HttpSession session, @ModelAttribute("conductor") Conductor conductorEditado) {
        Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");
        conductorEditado.setId(idUsuario);
        try {
            conductorServicio.editarConductor(conductorEditado);
            session.setAttribute("isEditForm", false);
        } catch (ConductorNoEncontradoException e) {
            return this.mostrarFormConductor(e.getMessage(), session);
        }
        return new ModelAndView("redirect:/perfil");
    }

    @PostMapping("/subir-foto")
    public ModelAndView subirFoto(@RequestParam("imagenPerfil") MultipartFile imagen, HttpSession session) {
        Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");
        try {
            this.conductorServicio.ingresarImagen(imagen, idUsuario);
            return new ModelAndView("redirect:/perfil");
        } catch (ConductorNoEncontradoException e) {
            return this.mostrarFormConductor(e.getMessage(), session);
        } catch (IOException e) {
            return this.mostrarFormConductor("Error al subir la imagen", session);
        }
    }

    @RequestMapping(value = "/borrar-cuenta", method = RequestMethod.GET)
    public ModelAndView borrarCuenta(HttpSession session) {
        try {
            conductorServicio.borrarConductor((Integer) session.getAttribute("IDUSUARIO"));
        } catch (ConductorNoEncontradoException e) {
            return this.mostrarFormConductor(e.getMessage(), session);
        }
        return new ModelAndView("redirect:/cerrar-sesion");
    }*/


}
