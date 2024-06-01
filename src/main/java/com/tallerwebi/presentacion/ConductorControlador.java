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
        this.iimageService=imageService;
        this.vehiculoService=_vehiculoService;
    }

    @RequestMapping(path = "/perfil", method = RequestMethod.GET)
    public ModelAndView irAPerfil(HttpSession session) throws ConductorNoEncontradoException {
        ModelMap model = new ModelMap();
        Boolean isUsuarioLogueado = (Boolean) session.getAttribute("isUsuarioLogueado");
        String nombre = (String) session.getAttribute("NOMBRE");
        String apellido = (String) session.getAttribute("APELLIDO");
        Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");
        Imagen logo = iimageService.getImagenByName("logo");
        Imagen user = iimageService.getImagenByName("user");
        Conductor conductor = conductorServicio.obtenerConductorPorId(idUsuario);
        Vehiculo vehiculo = conductor.getVehiculo();

        model.put("logo", logo);
        model.put("user", user);
        model.put("isUsuarioLogueado", isUsuarioLogueado);
        model.put("nombreUsuario", nombre);
        model.put("apellidoUsuario", apellido);
        model.put("idUsuario", idUsuario);
        session.setAttribute("idVehiculo", vehiculo.getId());
        model.put("conductor", conductor );
        model.put("vehiculo", vehiculo );


        return new ModelAndView("perfil-conductor",model);
    }

    @RequestMapping(value = "/editar", method = RequestMethod.GET)
    public ModelAndView mostrarEditarConductor(HttpSession session) {
        session.setAttribute("isEditForm", true);
        return new ModelAndView("redirect:/registro-conductor") ;
    }

    @RequestMapping(path = "/foto-perfil", method = RequestMethod.GET)
    public ModelAndView irAEditarFotoPerfil(HttpSession session) throws ConductorNoEncontradoException {
        ModelMap model = new ModelMap();
        Boolean isUsuarioLogueado = (Boolean) session.getAttribute("isUsuarioLogueado");
        Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");
        Imagen logo = iimageService.getImagenByName("logo");
        Imagen user = iimageService.getImagenByName("user");
        Conductor conductor = conductorServicio.obtenerConductorPorId(idUsuario);

        model.put("logo", logo);
        model.put("user", user);
        model.put("isUsuarioLogueado", isUsuarioLogueado);
        model.put("idUsuario", idUsuario);
        model.put("conductor", conductor );
        return new ModelAndView("foto-perfil",model);
    }



    @PostMapping("/editar-conductor")
    public ModelAndView editarConductor(HttpSession session, @ModelAttribute("conductor") Conductor conductorEditado) throws ConductorNoEncontradoException {
        Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");
        conductorEditado.setId(idUsuario);
        conductorServicio.editarConductor(conductorEditado);
        session.setAttribute("isEditForm", false);
        return new ModelAndView("redirect:/perfil");
    }

    @PostMapping("/subir-foto")
    public ModelAndView subirFoto(@RequestParam("imagenPerfil") MultipartFile imagen, HttpSession session) throws ConductorNoEncontradoException, IOException {

        Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");
        this.conductorServicio.ingresarImagen(imagen,idUsuario);
        return new ModelAndView("redirect:/perfil");
    }

    @RequestMapping(value="/borrar-cuenta", method = RequestMethod.GET)
    public ModelAndView borrarCuenta (HttpSession session){

        conductorServicio.borrarConductor((Integer) session.getAttribute("IDUSUARIO"));
        return new ModelAndView("redirect:/cerrar-sesion");
    }


}
