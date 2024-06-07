package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.enums.TipoUsuario;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.usuario.UsuarioServicio;
import com.tallerwebi.presentacion.Datos.DatosUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    private ConductorServicio conductorServicio;
    private ClienteServicio clienteServicio;

    @Autowired
    public UsuarioControlador(UsuarioServicio usuarioServicio, ConductorServicio conductorServicio, ClienteServicio clienteServicio) {
        this.usuarioServicio = usuarioServicio;
        this.conductorServicio = conductorServicio;
        this.clienteServicio = clienteServicio;
    }

    @RequestMapping(value = "/nuevo-usuario", method = RequestMethod.GET)
    public ModelAndView mostrarForm(String mensajeError, HttpSession session) throws UsuarioNoEncontradoException {
        String viewName= "form-usuario";
        ModelMap model = new ModelMap();
        model.put("usuario", new DatosUsuario());

        model.put("isEditForm", false);

        return new ModelAndView(viewName, model);
    }

    @PostMapping("/registrar-usuario")
    public ModelAndView registrarConductor(@ModelAttribute("usuario") DatosUsuario nuevoUsuario, HttpSession session) throws Exception {
        try{
            Usuario usuario = usuarioServicio.registrarUsuario(nuevoUsuario);
            if(usuario instanceof Conductor){
                Conductor conductor = (Conductor)usuario;
                session.setAttribute("IDUSUARIO", conductor.getId());
                return new ModelAndView("redirect:/vehiculo");
            }else{
                Cliente cliente = (Cliente)usuario;
                session.setAttribute("IDUSUARIO", cliente.getId());
                return new ModelAndView("redirect:/homeCliente");
            }
        }catch(Exception e){
            e.printStackTrace();
            return this.mostrarForm("Se ha producido un error en el servidor.",session);
        }
    }

    @RequestMapping(path = "/perfil", method = RequestMethod.GET)
    public ModelAndView irAPerfil(HttpSession session) {
        ModelMap model = new ModelMap();
        try {
            if(session.getAttribute("tipoUsuario").equals(TipoUsuario.Conductor)){
                Conductor usuario = conductorServicio.obtenerConductorPorId((Integer)session.getAttribute("IDUSUARIO"));
                model.put("usuario", usuario);
            }else{
               Cliente usuario = clienteServicio.obtenerClientePorId((Integer)session.getAttribute("IDUSUARIO"));
               model.put("usuario", usuario);
            }
        } catch (UsuarioNoEncontradoException e) {
            model.put("mensajeError", e.getMessage());
        }

        Boolean isUsuarioLogueado = (Boolean) session.getAttribute("isUsuarioLogueado");
        model.put("isUsuarioLogueado", isUsuarioLogueado);
        return new ModelAndView("perfil", model);
    }

    @RequestMapping(value = "/form-editar", method = RequestMethod.GET)
    public ModelAndView mostrarEditarFormulario(HttpSession session) {
        String viewName= "form-usuario";
        ModelMap model = new ModelMap();
        try {
            if(session.getAttribute("tipoUsuario").equals(TipoUsuario.Conductor)){
                Conductor usuario = conductorServicio.obtenerConductorPorId((Integer)session.getAttribute("IDUSUARIO"));
                DatosUsuario datosUsuario = new DatosUsuario();
                model.put("usuario", datosUsuario.usuarioToDTO(usuario));
            }else{
                Cliente usuario = clienteServicio.obtenerClientePorId((Integer)session.getAttribute("IDUSUARIO"));
                DatosUsuario datosUsuario = new DatosUsuario();
                model.put("usuario", datosUsuario.usuarioToDTO(usuario));
            }
        } catch (UsuarioNoEncontradoException e) {
            model.put("mensajeError", e.getMessage());
        }
        model.put("isEditForm", true);
        return new ModelAndView(viewName, model);
    }

     @PostMapping("/editar-usuario")
    public ModelAndView editarConductor(HttpSession session, @ModelAttribute("usuario") DatosUsuario usuarioEditado) {
         ModelMap model = new ModelMap();
         usuarioEditado.setId((Integer) session.getAttribute("IDUSUARIO"));
        try {
            if(session.getAttribute("tipoUsuario").equals(TipoUsuario.Conductor)){
                Conductor conductor = usuarioEditado.toConductor();//pasar a service
                conductorServicio.editarConductor(conductor);
                session.setAttribute("isEditForm", false);
            }else{
                Cliente cliente = usuarioEditado.toCliente();//pasar a service
                clienteServicio.editarCliente(cliente);
                session.setAttribute("isEditForm", false);
            }
        } catch (UsuarioNoEncontradoException e) {
            model.put("mensajeError", e.getMessage());
        }
        return new ModelAndView("redirect:/perfil");
    }

    @RequestMapping(path = "/foto-perfil", method = RequestMethod.GET)
    public ModelAndView irAEditarFotoPerfil(HttpSession session) {
        ModelMap model = new ModelMap();
        try {
            if(session.getAttribute("tipoUsuario").equals(TipoUsuario.Conductor)){
                Conductor usuario = conductorServicio.obtenerConductorPorId((Integer)session.getAttribute("IDUSUARIO"));
                model.put("usuario", usuario);
            }else{
                Cliente usuario = clienteServicio.obtenerClientePorId((Integer)session.getAttribute("IDUSUARIO"));
                model.put("usuario", usuario);
            }
        } catch (UsuarioNoEncontradoException e) {
            model.put("mensajeError", e.getMessage());
        }
        return new ModelAndView("foto-perfil", model);
    }

    @PostMapping("/subir-foto")
    public ModelAndView subirFoto(@RequestParam("imagenPerfil") MultipartFile imagen, HttpSession session) {
        ModelMap model = new ModelMap();
        try {
            if(session.getAttribute("tipoUsuario").equals(TipoUsuario.Conductor)){
                this.conductorServicio.ingresarImagen(imagen, (Integer) session.getAttribute("IDUSUARIO"));
                return new ModelAndView("redirect:/perfil");
            }else{
                this.clienteServicio.ingresarImagen(imagen, (Integer) session.getAttribute("IDUSUARIO"));
                return new ModelAndView("redirect:/perfil");            }
        } catch (UsuarioNoEncontradoException e) {
            model.put("mensajeError", e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

//    @RequestMapping(value = "/borrar-cuenta", method = RequestMethod.GET)
//    public ModelAndView borrarCuenta(HttpSession session) {
//        try {
//            conductorServicio.borrarConductor((Integer) session.getAttribute("IDUSUARIO"));
//        } catch (UsuarioNoEncontradoException e) {
//            return this.mostrarFormConductor(e.getMessage(), session);
//        }
//        return new ModelAndView("redirect:/cerrar-sesion");
//    }

}
