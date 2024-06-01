package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.usuario.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.imagen.ImagenServicio;
import com.tallerwebi.dominio.enums.TipoUsuario;
import com.tallerwebi.dominio.usuario.UsuarioServicio;
import com.tallerwebi.presentacion.Datos.DatosRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UsuarioControlador {

    private UsuarioServicio usuarioServicio;
    private ImagenServicio imagenServicio;
    private ConductorServicio conductorServicio;

    @Autowired
    public UsuarioControlador(UsuarioServicio usuarioServicio, ImagenServicio imagenServicio, ConductorServicio conductorServicio) {
        this.usuarioServicio = usuarioServicio;
        this.imagenServicio = imagenServicio;
        this.conductorServicio = conductorServicio;
    }

    @RequestMapping(value = "/registro-usuario", method = RequestMethod.GET)
    public ModelAndView mostrarForm(String mensajeError, HttpSession session) throws UsuarioNoEncontradoException {

        String viewName= "registro-usuario";
        ModelMap model = new ModelMap();
        Imagen logo = imagenServicio.getImagenByName("logo");
        Imagen auto = imagenServicio.getImagenByName("auto");
        Imagen fondo = imagenServicio.getImagenByName("fondo");
        Imagen botonPS = imagenServicio.getImagenByName("botonPS");
        Imagen user = imagenServicio.getImagenByName("user");

        boolean isEditForm = (session.getAttribute("isEditForm") != null) ? (boolean) session.getAttribute("isEditForm") : false;

        model.put("logo", logo);
        model.put("auto", auto);
        model.put("fondo", fondo);
        model.put("botonPS", botonPS);
        model.put("usuario", new DatosRegistro());
        model.put("isEditForm", isEditForm);
        model.put("user", user);

        if(!isEditForm) {
            if(mensajeError != ""){
                model.put("mensajeError", mensajeError);
            }
        } else {
            Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");
            Conductor conductor = conductorServicio.obtenerConductorPorId(idUsuario);
            model.put("conductor", conductor );
        }
        return new ModelAndView(viewName, model);
    }

    @PostMapping("/registrar-usuario")
    public ModelAndView registrarConductor(@ModelAttribute("usuario") DatosRegistro nuevoUsuario, HttpSession session) throws Exception {
        if(nuevoUsuario.getTipoUsuario() == TipoUsuario.CONDUCTOR){
            Conductor conductorRegistrado = usuarioServicio.registrarConductorNoDuplicado(nuevoUsuario);
            if(conductorRegistrado != null){
                session.setAttribute("IDUSUARIO", conductorRegistrado.getId());
                return new ModelAndView("redirect:/vehiculo");
            }
        } else if(nuevoUsuario.getTipoUsuario() == TipoUsuario.CLIENTE) {
            Cliente clienteRegistrado = usuarioServicio.registrarClienteNoDuplicado(nuevoUsuario);
            if (clienteRegistrado != null) {
                session.setAttribute("IDUSUARIO", clienteRegistrado.getId());
                return new ModelAndView("redirect:/home");
            }
        }
        return this.mostrarForm("Se ha producido un error en el servidor.",session);
    }

}
