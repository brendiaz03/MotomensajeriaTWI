package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.enums.TipoUsuario;
import com.tallerwebi.dominio.exceptions.UsuarioDuplicadoException;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.usuario.UsuarioServicio;
import com.tallerwebi.presentacion.Datos.DatosUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.model.IModel;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class UsuarioControlador {

    private UsuarioServicio usuarioServicio;

    @Autowired
    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @RequestMapping(value = "/nuevo-usuario", method = RequestMethod.GET)
    public ModelAndView mostrarForm(DatosUsuario actual, String mensajeError, HttpSession session) {

        String viewName = "form-usuario";

        ModelMap model = new ModelMap();

        model.put("isEditForm", false);

        if (mensajeError == null || mensajeError.isEmpty()) {
            model.put("usuario", new DatosUsuario());
            model.put("mensajeError", null);
        } else {
            model.put("usuario", actual);
            model.put("mensajeError", mensajeError);
        }

        return new ModelAndView(viewName, model);
    }

    @PostMapping("/registrar-usuario")
    public ModelAndView registrarConductor(@ModelAttribute("usuario") DatosUsuario nuevoUsuario, HttpSession session) {
        ModelMap model = new ModelMap();
        try {
            Usuario usuario = usuarioServicio.registrarUsuario(nuevoUsuario);
            if (usuario instanceof Conductor) {
                Conductor conductor = (Conductor) usuario;
                session.setAttribute("IDUSUARIO", conductor.getId());
                session.setAttribute("isEditForm", false);
                return new ModelAndView("redirect:/form-vehiculo", model);
            } else {
                Cliente cliente = (Cliente) usuario;
                session.setAttribute("IDUSUARIO", cliente.getId());
                return new ModelAndView("redirect:/home?registroExitoso=true");
            }
        } catch (UsuarioDuplicadoException e) {
            return this.mostrarForm(nuevoUsuario, e.getMessage(), session);
        }
    }

    @RequestMapping(path = "/perfil", method = RequestMethod.GET)
    public ModelAndView irAPerfil(HttpSession session) {
        ModelMap model = new ModelMap();
        try {
            Usuario usuario = usuarioServicio.obtenerUsuarioPorId((Integer) session.getAttribute("IDUSUARIO"));
            model.put("usuario", usuario);
            if (usuario.getTipoUsuario().equals(TipoUsuario.Conductor)) {
                Conductor conductor = (Conductor) usuario;
                if (conductor.getVehiculo() != null) {
                    model.put("noVehiculo", false);
                    return new ModelAndView("perfil", model);
                } else {
                    model.put("noVehiculo", true);
                }
            }
        } catch (Exception e) {
            model.put("mensajeError", e.getMessage() + " Por favor, vuelva a intentarlo.");
            return new ModelAndView("redirect:/*", model);
        }
        return new ModelAndView("perfil", model);
    }

    @RequestMapping(value = "/form-editar", method = RequestMethod.GET)
    public ModelAndView mostrarEditarFormulario(HttpSession session) {
        String viewName = "form-usuario";
        ModelMap model = new ModelMap();
        try {
            Usuario usuario = usuarioServicio.obtenerUsuarioPorId((Integer) session.getAttribute("IDUSUARIO"));
            DatosUsuario datosUsuario = new DatosUsuario();
            model.put("usuario", datosUsuario.usuarioToDTO(usuario));
            model.put("isEditForm", true);
        } catch (UsuarioNoEncontradoException e) {
            model.put("mensajeError", e.getMessage() + " Por favor, vuelva a intentarlo.");
            return new ModelAndView("redirect:/*", model);
        }
        return new ModelAndView(viewName, model);
    }

    @PostMapping("/editar-usuario")
    public ModelAndView editarConductor(HttpSession session, @ModelAttribute("usuario") DatosUsuario usuarioEditado) {
        usuarioEditado.setId((Integer) session.getAttribute("IDUSUARIO"));
        try {
            usuarioServicio.actualizarUsuario(usuarioEditado, (TipoUsuario) session.getAttribute("tipoUsuario"));
            session.setAttribute("isEditForm", false);
            return new ModelAndView("redirect:/perfil");
        } catch (UsuarioNoEncontradoException e) {
            e.printStackTrace();
            return this.mostrarEditarFormulario(session);
        }
    }

    @RequestMapping(path = "/foto-perfil", method = RequestMethod.GET)
    public ModelAndView irAEditarFotoPerfil(HttpSession session) {
        String viewName = "foto-perfil";
        ModelMap model = new ModelMap();
        try {
            Usuario usuario = usuarioServicio.obtenerUsuarioPorId((Integer) session.getAttribute("IDUSUARIO"));
            model.put("usuario", usuario);
        } catch (UsuarioNoEncontradoException e) {
            model.put("mensajeError", e.getMessage() + " Por favor, vuelva a intentarlo.");
            return new ModelAndView("redirect:/*", model);
        }
        return new ModelAndView(viewName, model);
    }

    @PostMapping("/subir-foto")
    public ModelAndView subirFoto(@RequestParam("imagenPerfil") MultipartFile imagen, HttpSession session) {
        ModelMap model = new ModelMap();
        try {
            usuarioServicio.ingresarImagen(imagen, (Integer) session.getAttribute("IDUSUARIO"));
            return new ModelAndView("redirect:/perfil");
        } catch (UsuarioNoEncontradoException e) {
            model.put("mensajeError", e.getMessage());
            return new ModelAndView("redirect:/foto-perfil");
        }
    }

    @RequestMapping(value = "/borrar-cuenta", method = RequestMethod.GET)
    public ModelAndView borrarCuenta(HttpSession session) {
        ModelMap model = new ModelMap();
        try {
            usuarioServicio.borrarCuenta((Integer) session.getAttribute("IDUSUARIO"));
        } catch (UsuarioNoEncontradoException e) {
            model.put("mensajeError", e.getMessage() + " Por favor, vuelva a intentarlo.");
            return new ModelAndView("redirect:/*", model);
        }
        return new ModelAndView("redirect:/cerrar-sesion", model);
    }

}
